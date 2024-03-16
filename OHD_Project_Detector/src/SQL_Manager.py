import time
import pymysql

"""
- 数据库操作，将异常报警记录写入数据库
- 创建一个监控数据库的线程，每隔固定时间查询一次异常报警记录(3s左右)
- 定义数据接口，可以用java来写界面，具有视频流源，用户信息，报警等功能
- 用python代码来处理视频流数据，并将检测结果写入数据库，java端读取后进行判定是否存在异常情况
"""


class Project_OHD_DB_Manager:  # 初始化数据库操作类
    def __init__(self):
        self.mydb = pymysql.connect(  # 连接数据库
            host = "[host]",
            user = "[user]",
            password = "[password]",
            database = "[databasename]")
        self.mycursor = self.mydb.cursor()


class Project_Setter_DB_Manager(Project_OHD_DB_Manager):
    def __init__(self):
        super().__init__()

    def Set_abnormal_record(self, abnormal, detector_ID, room_number):
        # 获取当前时间并转成Str
        time_value = time.time()
        time_string = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(time_value))

        # 生成唯一的 ID
        nid = self.generate_unique_id(detector_ID)

        # 写入临时异常记录表(用于报警)
        sql = "INSERT INTO record (ID, time, category, event, place) VALUES (%s, %s, %s, %s, %s);"
        val = (f"{detector_ID}_{nid}", time_string, 1, abnormal, room_number)
        self.mycursor.execute(sql, val)
        self.mydb.commit()

    def generate_unique_id(self, detector_ID):
        # 查询现有的 ID 列表
        existing_ids = self.get_existing_ids()

        # 生成唯一的 ID
        nid = 1
        while f"{detector_ID}_{nid}" in existing_ids:
            nid += 1
        return nid

    def get_existing_ids(self):
        # 查询现有的 ID 列表的逻辑
        # 你需要实现一个查询数据库的代码，获取所有已存在的 ID，并返回一个列表
        # 以下是一个示例，你需要根据你的数据库结构修改这部分代码
        sql = "SELECT ID FROM record;"
        self.mycursor.execute(sql)
        existing_ids = [row[0] for row in self.mycursor.fetchall()]
        return existing_ids

    def Set_Detector_state_on(self, detectorID):  # Update 检测器的状态为on
        self.mycursor.execute(f"Update detector Set state= 1 where threadID = {detectorID};")
        self.mydb.commit()

    def Set_Detector_state_off(self, detectorID):  # Update 检测器的状态为off(已经开过但后来关闭)
        self.mycursor.execute(f"Update detector Set state= 2 where threadID = {detectorID};")
        self.mydb.commit()


class Project_Getter_DB_Manager(Project_OHD_DB_Manager):  # 获取状态为关的检测器
    def __init__(self):
        super().__init__()

    def Get_Detector(self):  # 将新添加的状态为0的检测器数据找出，提交到Main函数中开启检测器
        self.mycursor.execute("Select * from detector where state = 0;")
        # self.mydb.commit()
        new_detector = self.mycursor.fetchall()
        if len(new_detector) is not None:
            print("================")
            return new_detector
        else:
            return None

    def Get_Anti_Detector(self):  # 将新添加的状态为2的检测器数据找出，提交到Main函数中开启检测器
        self.mycursor.execute("Select * from detector where state = 2;")
        # self.mydb.commit()
        old_detector = self.mycursor.fetchall()
        if len(old_detector) is not None:
            print("================")
            return old_detector
        else:
            return None


class Project_Deleter_DB_Manager(Project_OHD_DB_Manager):  # 删除一段时间内的临时记录表
    def __init__(self):
        super().__init__()

    def Deleter_record(self):
        # 由于报警端会将收集一段时间内的异常记录，并根据异常记录的频率来判定是否出现异常情况
        # 因此每隔一段时间需要将临时数据库清空
        self.mycursor.execute("Delete from record where category = 1;")
        self.mydb.commit()
        print("+=====================================================+")
        print("|                    已重置Record                     |")
        print("+=====================================================+")
