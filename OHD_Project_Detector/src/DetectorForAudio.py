import argparse
import functools
import threading
import time
import numpy as np
import soundcard as sc
from import_VoiceR.macls.predict import MAClsPredictor
from import_VoiceR.macls.utils.utils import add_arguments, print_arguments
from src.SQL_Manager import Project_Setter_DB_Manager

global Data

"""
- 此模块用于检测音频信号
- 输入为音频源，线程ID，检测位置(房间号)，传入的数据库操作者对象
- 当产生异常音频时，写入数据库异常音频记录
"""


def infer_thread(infer_len, predictor, samplerate, ID, room_number, DB_Manager, event):
    global Data
    s = time.time()
    while True:
        if len(Data) < infer_len:
            continue
        # 截取最新的音频数据
        seg_data = Data[-infer_len:]
        d = np.concatenate(seg_data)
        # 删除旧的音频数据
        del Data[:len(Data) - infer_len]
        label, score = predictor.predict(audio_data = d, sample_rate = samplerate)
        # 将特定标签写入数据库
        if label == "gun_shot":
            DB_Manager.Set_abnormal_record(label, ID, room_number)
        elif label == "siren":
            DB_Manager.Set_abnormal_record(label, ID, room_number)
        elif event.is_set():
            break
        print(f'{int(time.time() - s)}s 预测结果标签为：{label}，得分：{score}')


def V_Detector(source, ID, room_number, DB_Manager, event):
    DB_Manager.Set_Detector_state_on(ID)
    """
    路径修改：add_arg（1，4）改成绝对路径，ecapa_tdnn模型中label_list改绝对路径
    主函数
    """
    # if __name__ == "__main__":
    # ID, room_number = "122", "101"
    # DB_Manager = Project_Setter_DB_Manager()
    global Data
    # 定义命令行参数
    parser = argparse.ArgumentParser(description = __doc__)
    add_arg = functools.partial(add_arguments, argparser = parser)
    add_arg('configs', str, r'E:/Project/OHD_Project/OHD_Project_Detector/import_VoiceR/EcapaTdnn_model/configs/ecapa_tdnn.yml', '配置文件')
    add_arg('use_gpu', bool, True, '是否使用GPU预测')
    add_arg('record_seconds', float, 3, '每次录音长度')
    add_arg('model_path', str, r'E:/Project/OHD_Project/OHD_Project_Detector/import_VoiceR/EcapaTdnn_model/models/EcapaTdnn_Fbank/best_model',
            '导出的预测模型文件路径')
    args = parser.parse_args()
    print_arguments(args = args)

    # 获取识别器
    predictor = MAClsPredictor(configs = args.configs, model_path = args.model_path, use_gpu = args.use_gpu)
    Data = []
    # 获取默认麦克风
    print("Source: ", source)

    if source == '0':
        default_mic = sc.default_microphone()
    else:
        default_mic = sc.get_microphone(source)  # default_microphone()
    # 录音采样率
    samplerate = 16000
    # 录音块大小
    numframes = 1024
    # 模型输入长度
    infer_len = int(samplerate * args.record_seconds / numframes)
    thread = threading.Thread(target = infer_thread, args = (infer_len, predictor, samplerate, ID, room_number, DB_Manager, event))
    thread.start()

    with default_mic.recorder(samplerate = samplerate, channels = 1) as mic:
        while True:
            data = mic.record(numframes = numframes)
            Data.append(data)


"""
音频当前label如下：

air_conditioner
car_horn
children_playing
dog_bark
drilling
engine_idling
gun_shot
jackhammer
siren
street_music
"""
