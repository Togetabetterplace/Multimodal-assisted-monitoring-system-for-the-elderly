from src.DetectorForAudio import V_Detector
from src.DetectorForVideo import EF_Detector
import threading
from SQL_Manager import Project_Setter_DB_Manager

"""
- 检测器主模块，初始化多线程的两种检测器
"""


class Detector_Main(threading.Thread):
    def __init__(self, room_number, source, ID, event):
        super().__init__()
        self.source = source  # 检测器源(视频源/音频源)
        self.ID = ID  # 检测器ID
        self.room_number = room_number
        self.event = event

    def stop(self):
        self.event.set()


class Fall_Emotion_Detector(Detector_Main):  # Detector_Main的子类，表示异常动作和表情检测器
    def __init__(self, room_number, ID, event, source = 0):
        super().__init__(room_number, source, ID, event)
        self.frame = None  # 实时图片
        self.DB = Project_Setter_DB_Manager()  # 数据库操作类

    def run(self):
        print("Thread-" + str(self.ID) + "开始检测异常动作")
        try:
            self.DB.Set_Detector_state_on(self.ID)  # Update 检测器状态
            EF_Detector(self.source, self.ID, self.room_number, self.DB, self.event)
        except Exception as re:
            print(re)
            print("Thread-" + str(self.ID) + "停止检测异常动作")
            self.DB.Set_Detector_state_off(self.ID)  # Update 检测器状态


class Voice_Detector(Detector_Main):  # Detector_Main的子类，表示异常声音检测器
    def __init__(self, room_number, ID, event, source = 0):
        super().__init__(room_number, source, ID, event)
        self.DB = Project_Setter_DB_Manager()  # 数据库操作类

    def run(self):
        print("Thread-" + str(self.ID) + "开始检测异常语音")
        try:
            self.DB.Set_Detector_state_on(self.ID)  # Update 检测器状态
            V_Detector(self.source, self.ID, self.room_number, self.DB,self.event)
        except Exception as re:
            print(re)
            print("Thread-" + str(self.ID) + "停止检测异常语音")
            self.DB.Set_Detector_state_off(self.ID)  # Update 检测器状态
