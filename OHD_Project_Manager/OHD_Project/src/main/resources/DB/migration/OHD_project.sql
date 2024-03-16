-- 创建数据库 Project_OHD_re
create database Project_OHD_re;

-- 设置外键检查为0
SET FOREIGN_KEY_CHECKS = 0;

-- 删除已存在的表
DROP TABLE IF EXISTS user,caregiver,detector,online_caregiver,room,record,long_time_record;

-- 创建用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id                     char(11) NOT NULL primary key,
    name                   char(255)    DEFAULT NULL,
    gender                 char(20)     DEFAULT NULL,
    age                    smallint     DEFAULT NULL,
    phone                  char(11)     DEFAULT NULL,
    emergencyContact       char(11)     DEFAULT NULL,
    emergencyContactPerson char(255)    DEFAULT NULL,
    groupId                int(11)      DEFAULT NULL,
    conditions             varchar(255) DEFAULT NULL,
    roomNumber             char(255)    DEFAULT NULL,
    password               char(255)    DEFAULT NULL,
    KEY `fk_ugroup_id` (groupId),
    CONSTRAINT `fk_ugroup_id` FOREIGN KEY (groupId) REFERENCES res_group (groupId)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8;

-- 创建照护者表
DROP TABLE IF EXISTS caregiver;
CREATE TABLE caregiver
(
    caregiverId        char(11) NOT NULL primary key,
    name               char(255) DEFAULT NULL,
    gender             char(20)  DEFAULT NULL,
    age                smallint  DEFAULT NULL,
    phone              char(11)  DEFAULT NULL,
    responsibleGroupId int(11)   DEFAULT NULL,
    password           char(255) DEFAULT NULL,
    KEY `fk_res_group_id` (responsibleGroupId),
    CONSTRAINT `fk_res_group_id` FOREIGN KEY (responsibleGroupId) REFERENCES res_group (groupId)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8;

-- 创建资源组表
DROP TABLE IF EXISTS res_group;
CREATE TABLE res_group
(
    groupId     char(11) NOT NULL primary key,
    caregiverId char(11) DEFAULT NULL,
    oldNumber   int      DEFAULT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8;

-- 修改外键
ALTER TABLE caregiver
    DROP CONSTRAINT `fk_res_group_id`;
ALTER TABLE user
    DROP CONSTRAINT `fk_ugroup_id`;

-- 创建检测器表
DROP TABLE IF EXISTS detector;
CREATE TABLE detector
(
    threadID   char(11) NOT NULL primary key,
    roomNumber char(255)    DEFAULT NULL,
    category   tinyint      DEFAULT NULL,
    source     varchar(255) DEFAULT NULL,
    state      smallint     DEFAULT 0,
    KEY `fk_room_number` (roomNumber),
    CONSTRAINT `fk_room_number` FOREIGN KEY (roomNumber) REFERENCES room (place)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

-- 创建记录表
DROP TABLE IF EXISTS record;
CREATE TABLE record
(
    ID       char(11) NOT NULL primary key,
    time     datetime     DEFAULT NULL,
    category tinyint      DEFAULT NULL,
    event    varchar(255) DEFAULT NULL,
    place    varchar(255) DEFAULT NULL,
    KEY `fk_place1` (place),
    CONSTRAINT `fk_place1` FOREIGN KEY (place) REFERENCES room (place)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

-- 创建房间表
DROP TABLE IF EXISTS room;
CREATE TABLE room
(
    place       varchar(255) NOT NULL primary key,
    description varchar(255) DEFAULT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

-- 创建长时记录表
DROP TABLE IF EXISTS long_time_record;
CREATE TABLE long_time_record
(
    ID       char(11) NOT NULL primary key,
    time     datetime     DEFAULT NULL,
    category tinyint      DEFAULT NULL,
    event    varchar(255) DEFAULT NULL,
    place    varchar(255) DEFAULT NULL,
    KEY `fk_place` (place),
    CONSTRAINT `fk_place` FOREIGN KEY (place) REFERENCES room (place)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

-- 创建在线照护者表
DROP TABLE IF EXISTS online_caregiver;
CREATE TABLE online_caregiver
(
    caregiverId        char(11) primary key,
    responsibleGroupId char(20)  DEFAULT NULL,
    name               char(255) DEFAULT NULL,
    KEY `fk_caregiver_id` (caregiverId),
    CONSTRAINT `fk_caregiver_id` FOREIGN KEY (caregiverId) REFERENCES caregiver (caregiverId)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

alter table online_caregiver
    drop constraint `fk_caregiver_id`;

insert into detector (threadID, roomNumber, category, source, state)
values ('101', '122', 1, '0', 0);

alter table detector
    modify column source varchar(50);

select *
from online_caregiver;
delete
from online_caregiver
where caregiverId = 101;

insert into res_group
values (1, 1, 0);


select responsibleGroupId, name, count(*)
from caregiver
where gender = '男'
group by responsibleGroupId
order by responsibleGroupId desc;


select responsibleGroupId, count(*), max(user.age), min(user.age), avg(user.age)
from caregiver
         join user on responsibleGroupId = groupId
group by responsibleGroupId
order by avg(user.age) desc;

select responsibleGroupId, name
from caregiver
where gender = '男'
order by name desc;

DROP TRIGGER IF EXISTS user_insert_trigger;
CREATE TRIGGER user_insert_trigger
    BEFORE INSERT
    ON user
    FOR EACH ROW
BEGIN
    IF NEW.name IS NULL THEN
        SIGNAL SQLSTATE '81022' SET MESSAGE_TEXT = "姓名为空";
    ELSE
        IF NEW.groupId NOT IN (SELECT responsibleGroupId FROM caregiver) THEN
            SIGNAL SQLSTATE '81023' SET MESSAGE_TEXT = "用户组不存在";
        ELSE
            IF NEW.gender NOT IN ('男', '女') THEN
                SIGNAL SQLSTATE '81069' SET MESSAGE_TEXT = "性别填写错误";
            ELSE
                IF NEW.age > 200 THEN
                    SIGNAL SQLSTATE '81099' SET MESSAGE_TEXT = "年龄填写错误";
                ELSE
                    IF NEW.phone IS NULL AND NEW.password IS NULL AND
                       NEW.emergencyContactPerson IS NULL AND NEW.emergencyContact IS NULL THEN
                        SIGNAL SQLSTATE '81000' SET MESSAGE_TEXT = "信息不能为空";
                    ELSE
                        IF NEW.roomNumber NOT IN (SELECT place FROM room) THEN
                            INSERT INTO room (place, description) VALUES (NEW.roomNumber, 'user room');
                        END IF;
                    END IF;
                END IF;
            END IF;
        END IF;
    END IF;
END;


drop trigger if exists caregiver_insert_trigger;
CREATE TRIGGER caregiver_insert_trigger
    BEFORE INSERT
    ON caregiver
    FOR EACH ROW
BEGIN
    IF NEW.name IS NULL THEN
        SIGNAL SQLSTATE '81022' SET MESSAGE_TEXT = "姓名为空";
    ELSE
        IF NEW.responsibleGroupId IS NULL THEN
            SIGNAL SQLSTATE '81022' SET MESSAGE_TEXT = "未填写负责组编号";
        ELSE
            IF NEW.gender NOT IN ('男', '女') THEN
                SIGNAL SQLSTATE '81069' SET MESSAGE_TEXT = "性别填写错误";
            ELSE
                IF NEW.age > 200 THEN
                    SIGNAL SQLSTATE '81099' SET MESSAGE_TEXT = "年龄填写错误";
                ELSE
                    IF NEW.phone IS NULL AND NEW.password IS NULL THEN
                        SIGNAL SQLSTATE '81000' SET MESSAGE_TEXT = "密码不能为空";
                    ELSE
                        IF NEW.responsibleGroupId NOT IN (SELECT groupId FROM res_group) THEN
                            INSERT INTO res_group (groupId, caregiverId, oldNumber)
                            VALUES (NEW.responsibleGroupId, NEW.caregiverId, 0);
                        END IF;
                    END IF;
                END IF;
            END IF;
        END IF;
    END IF;
END;


drop trigger if exists detector_insert_trigger;
create trigger detector_insert_trigger
    before insert
    on detector
    for each row
begin
    if NEW.category IS NULL then
        signal sqlstate '81022'
            set message_text = "类别为空";
        if NEW.roomNumber not in (select place from room) then
            signal sqlstate '81023'
                set message_text = "房间不存在";
            if NEW.category != 1 and NEW.category != 2 then
                signal sqlstate '81044'
                    set message_text = "类别填写错误";
                if NEW.source is null then
                    signal sqlstate '81000'
                        set message_text = "检测器源不能为空";
                end if;
            end if;
        end if;
    end if;
end;