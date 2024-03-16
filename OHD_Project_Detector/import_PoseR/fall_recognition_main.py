import os
import cv2
import time
import torch
import argparse
import numpy as np
import threading

# 导入自定义模块
from import_PoseR.Detection.Utils import ResizePadding
from import_PoseR.CameraLoader import CamLoader, CamLoader_Q
from import_PoseR.DetectorLoader import TinyYOLOv3_onecls
from import_PoseR.PoseEstimateLoader import SPPE_FastPose
from import_PoseR.fn import draw_single
from import_PoseR.Track.Tracker import Detection, Tracker
from import_PoseR.ActionsEstLoader import TSSTG

# 视频源，可以是摄像头、视频文件路径或者视频文件的索引
# source = '../Data/test_video/test7.mp4'
# source = '../Data/falldata/Home/Videos/video (2).avi'  # hard detect
# source = '../Data/falldata/Home/Videos/video (1).avi'
# source = "D:/sopython_project_ALL/B-Ch/import_PoseR/TestData/down1.mp4"
source = 0  # 默认为摄像头

# 全局变量，用于存储预处理函数
global resize_fn


# 预处理函数，用于处理摄像头或视频帧
def preproc(images):
    global resize_fn
    images = resize_fn(images)
    images = cv2.cvtColor(images, cv2.COLOR_BGR2RGB)
    return images


# 将关键点转换为包含所有关键点（x，y）的边界框
def kpt2bbox(kpt, ex = 20):
    return np.array((kpt[:, 0].min() - ex, kpt[:, 1].min() - ex,
                     kpt[:, 0].max() + ex, kpt[:, 1].max() + ex))


# 主函数，实现实时人体摔倒检测和跟踪
def fall_main(flag, camera_lock):
    # global camera_lock
    global resize_fn
    # 解析命令行参数
    par = argparse.ArgumentParser(description = 'Human Fall Detection Demo.')
    par.add_argument('-C', '--camera', default = source,
                     help = 'Source of camera or video file path.')
    par.add_argument('--detection_input_size', type = int, default = 384,
                     help = 'Size of input in detection model in square must be divisible by 32 (int).')
    par.add_argument('--pose_input_size', type = str, default = '224x160',
                     help = 'Size of input in pose model must be divisible by 32 (h, w)')
    par.add_argument('--pose_backbone', type = str, default = 'resnet50',
                     help = 'Backbone model for SPPE FastPose model.')
    par.add_argument('--show_detected', default = False, action = 'store_true',
                     help = 'Show all bounding box from detection.')
    par.add_argument('--show_skeleton', default = True, action = 'store_true',
                     help = 'Show skeleton pose.')
    par.add_argument('--save_out', type = str, default = '',
                     help = 'Save display to video file.')
    par.add_argument('--device', type = str, default = 'cuda',
                     help = 'Device to run model on cpu or cuda.')
    args = par.parse_args()

    device = args.device

    # 检测模型
    inp_dets = args.detection_input_size
    detect_model = TinyYOLOv3_onecls(inp_dets, device = device)

    # 姿势模型
    inp_pose = args.pose_input_size.split('x')
    inp_pose = (int(inp_pose[0]), int(inp_pose[1]))
    pose_model = SPPE_FastPose(args.pose_backbone, inp_pose[0], inp_pose[1], device = device)

    # 跟踪器
    max_age = 30
    tracker = Tracker(max_age = max_age, n_init = 3)

    # 动作估计模型
    action_model = TSSTG()

    # 预处理函数
    resize_fn = ResizePadding(inp_dets, inp_dets)

    # 摄像头或视频源
    cam_source = args.camera
    if type(cam_source) is str and os.path.isfile(cam_source):
        # 使用带有队列的加载线程处理视频文件
        cam = CamLoader_Q(cam_source, queue_size = 1000, preprocess = preproc).start()
    else:
        # 使用常规线程加载处理摄像头
        cam = CamLoader(cam_source, preprocess = preproc).start()

    while True:
        with camera_lock:
            # 是否保存视频输出
            outvid = False
            if args.save_out != '':
                outvid = True
                codec = cv2.VideoWriter_fourcc(*'MJPG')
                writer = cv2.VideoWriter(args.save_out, codec, 30, (inp_dets * 2, inp_dets * 2))

            fps_time = 0
            f = 0
            # while cam.grabbed():
            f += 1
            frame = cam.getitem()
            print(frame.shape)
            image = frame.copy()

            # 使用检测模型在帧中检测人体边界框
            detected = detect_model.detect(frame, need_resize = False, expand_bb = 10)

            # 使用卡尔曼滤波器从先前帧的信息中预测当前帧的每个跟踪边界框
            tracker.predict()

            # 合并两个源的预测边界框
            for track in tracker.tracks:
                det = torch.tensor([track.to_tlbr().tolist() + [0.5, 1.0, 0.0]], dtype = torch.float32)
                detected = torch.cat([detected, det], dim = 0) if detected is not None else det

            detections = []  # 用于跟踪的 Detection 对象列表
            if detected is not None:
                # detected = non_max_suppression(detected[None, :], 0.45, 0.2)[0]
                # 预测每个边界框的关键点骨架姿态
                poses = pose_model.predict(frame, detected[:, 0:4], detected[:, 4])

                # 创建 Detections 对象
                detections = [Detection(kpt2bbox(ps['keypoints'].numpy()), np.concatenate((ps['keypoints'].numpy(), ps['kp_score'].numpy()), axis = 1),
                                        ps['kp_score'].mean().numpy()) for ps in poses]

                # 可视化
                if args.show_detected:
                    for bb in detected[:, 0:5]:
                        frame = cv2.rectangle(frame, (bb[0], bb[1]), (bb[2], bb[3]), (0, 0, 255), 1)

            # 使用当前帧和上一帧的每个跟踪信息更新跟踪
            tracker.update(detections)

            # 预测每个跟踪的动作
            for i, track in enumerate(tracker.tracks):
                if not track.is_confirmed():
                    continue
                track_id = track.track_id
                bbox = track.to_tlbr().astype(int)
                center = track.get_center().astype(int)

                action = 'pending..'
                clr = (0, 255, 0)

                # 使用 30 帧时间步长进行预测
                if len(track.keypoints_list) == 30:
                    pts = np.array(track.keypoints_list, dtype = np.float32)
                    out = action_model.predict(pts, frame.shape[:2])
                    action_name = action_model.class_names[out[0].argmax()]
                    action = '{}: {:.2f}%'.format(action_name, out[0].max() * 100)
                    if action_name == 'Fall Down':
                        clr = (255, 0, 0)
                        flag = 0
                    elif action_name == 'Lying Down':
                        clr = (255, 200, 0)

                # 可视化
                if track.time_since_update == 0:
                    if args.show_skeleton:
                        frame = draw_single(frame, track.keypoints_list[-1])
                    frame = cv2.rectangle(frame, (bbox[0], bbox[1]), (bbox[2], bbox[3]), (0, 255, 0), 1)
                    frame = cv2.putText(frame, str(track_id), (center[0], center[1]), cv2.FONT_HERSHEY_COMPLEX, 0.4, (255, 0, 0), 2)
                    frame = cv2.putText(frame, action, (bbox[0] + 5, bbox[1] + 15), cv2.FONT_HERSHEY_COMPLEX, 0.4, clr, 1)

            # 显示帧
            frame = cv2.resize(frame, (0, 0), fx = 2., fy = 2.)
            frame = cv2.putText(frame, '%d, FPS: %f' % (f, 1.0 / (time.time() - fps_time)), (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 1)
            frame = frame[:, :, ::-1]
            fps_time = time.time()

            if outvid:
                writer.write(frame)

            cv2.imshow('frame', frame)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    # 释放资源
    cam.stop()
    if outvid:
        writer.release()
    cv2.destroyAllWindows()
