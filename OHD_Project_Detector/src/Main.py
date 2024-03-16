from threading import Event
from time import sleep
from PyQt5.QtCore import QTime
from Detector_Main import Fall_Emotion_Detector, Voice_Detector
from SQL_Manager import Project_Getter_DB_Manager, Project_Deleter_DB_Manager

"""
- 主函数，开启后实现如下功能：
- 1.实时更新检测器，开启新添加的检测器(0:新增，1:开启，2:弃用)
- 2.实时检测各个房间异常情况
- 3.关闭已经弃用的检测器(java端设置为2)
"""


def main():
    DB_Getter = Project_Getter_DB_Manager()  # 初始化Getter
    DB_Deleter = Project_Deleter_DB_Manager()  # 初始化Deleter
    active_detectors = {}  # 用于存储活动中的检测器线程
    while True:
        New_Detectors = DB_Getter.Get_Detector()  # 获取新出现的检测器
        Old_Detectors = DB_Getter.Get_Anti_Detector()  # 获取弃用的检测器
        DB_Deleter.Deleter_record()  # 删除之前的临时记录
        print("新获取检测器：", New_Detectors)
        if New_Detectors is not None:
            for New_Detector in New_Detectors:
                detector_id = New_Detector[0]
                if New_Detector[2] == 1:  # 未打开的检测器(动作和表情识别)
                    if detector_id not in active_detectors:
                        event = Event()
                        FE_Detector = Fall_Emotion_Detector(New_Detector[1], detector_id, event, New_Detector[3])
                        FE_Detector.start()  # 开启线程
                        active_detectors[detector_id] = FE_Detector
                elif New_Detector[2] == 2:  # 未打开的检测器(声音)
                    if detector_id not in active_detectors:
                        event = Event()
                        V_Detector = Voice_Detector(New_Detector[1], detector_id, event, New_Detector[3])
                        V_Detector.start()
                        active_detectors[detector_id] = V_Detector

        if Old_Detectors is not None:
            for Old_Detector in Old_Detectors:
                detector_id = Old_Detector[0]
                if detector_id in active_detectors:
                    active_detectors[detector_id].stop()  # 你需要在相应的检测器类中实现 stop 方法
                    del active_detectors[detector_id]
        sleep(5)


if __name__ == "__main__":
    main()
