create database Project_OHD_re;
use Project_OHD_re;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE user,caregiver,detector,online_caregiver,room,record,long_time_record;


DROP TABLE IF EXISTS user;
create table user
(
    id_number                char(11) NOT NULL primary key,
    name                     char(255)    DEFAULT NULL,
    gender                   char(20)     DEFAULT NULL,
    age                      smallint     DEFAULT NULL,
    phone                    char(11)     DEFAULT NULL,
    emergency_contact        char(11)     DEFAULT NULL,
    emergency_contact_person char(255)    DEFAULT NULL,
    group_id                 int(11)      DEFAULT NULL,
    conditions               varchar(255) DEFAULT NULL,
    room_number              char(255)    DEFAULT NULL,
    password                 char(255)    DEFAULT NULL,
    KEY `fk_ugroup_id` (group_id),
    CONSTRAINT `fk_ugroup_id` FOREIGN KEY (group_id) REFERENCES res_group (group_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS caregiver;
create table caregiver
(
    caregiver_id          char(11) NOT NULL primary key ,
    name                 char(255) DEFAULT NULL,
    gender               char(20)  DEFAULT NULL,
    age                  smallint  DEFAULT NULL,
    phone                char(11)  DEFAULT NULL,
    responsible_group_id int(11)   DEFAULT NULL,
    password             char(255) DEFAULT NULL,
    KEY `fk_res_group_id` (responsible_group_id),
    CONSTRAINT `fk_res_group_id` FOREIGN KEY (responsible_group_id) REFERENCES res_group (group_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS res_group;
create table res_group
(
    group_id      char(11) NOT NULL primary key,
    caregiver_id  char(11) DEFAULT NULL,
    old_number   int     DEFAULT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8;

alter table caregiver drop constraint `fk_res_group_id`;
alter table user drop constraint `fk_ugroup_id`;

DROP TABLE IF EXISTS detector;
create table detector
(
    thread_ID    char(11) NOT NULL primary key,
    room_number char(255)    DEFAULT NULL,
    category    tinyint      DEFAULT NULL,
    source      varchar(255) DEFAULT NULL,
    state       smallint     DEFAULT 0,
    KEY `fk_room_number` (room_number),
    constraint `fk_room_number` foreign key (room_number) references room (place)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS record;
create table record
(
    ID        char(11) NOT NULL primary key,
    time     datetime     DEFAULT NULL,
    category tinyint      DEFAULT NULL,
    event    varchar(255) DEFAULT NULL,
    place    varchar(255) DEFAULT NULL,
    KEY `fk_place1` (place),
    constraint `fk_place1` foreign key (place) references room (place)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS room;
create table room
(
    place       varchar(255) NOT NULL primary key,
    description varchar(255) DEFAULT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS long_time_record;
create table long_time_record
(
    ID        char(11) NOT NULL primary key,
    time     datetime     DEFAULT NULL,
    category tinyint      DEFAULT NULL,
    event    varchar(255) DEFAULT NULL,
    place    varchar(255) DEFAULT NULL,
    KEY `fk_place` (place),
    constraint `fk_place` foreign key (place) references room (place)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS online_caregiver;
create table online_caregiver
(
    caregiver_id          char(11) primary key,
    responsible_group_id char(20)  DEFAULT NULL,
    name                 char(255) DEFAULT NULL,
    KEY `fk_caregiver_id` (caregiver_id),
    constraint `fk_caregiver_id` foreign key (caregiver_id) references caregiver (caregiver_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

alter table online_caregiver drop constraint `fk_caregiver_id`;

insert into detector (thread_ID, room_number, category, source, state)
values ('101', '122', 1, '0', 0);

alter table detector
    modify column source varchar(50);

select *
from online_caregiver;
delete
from online_caregiver
where caregiver_id = 101;

insert into online_caregiver (caregiver_id, responsible_group_id, name)
values (101, 4, '刘五');
insert into online_caregiver (caregiver_id, responsible_group_id, name)
values (102, 4, '刘六');
insert into online_caregiver (caregiver_id, responsible_group_id, name)
values (103, 8, '李华');
insert into online_caregiver (caregiver_id, responsible_group_id, name)
values (104, 8, '王二');
insert into online_caregiver (caregiver_id, responsible_group_id, name)
values (105, 8, '赵四');


insert into record (ID, time, category, event, place)
values (1, '2023-12-19 12:00:00', 1, 'Fall', '144');
insert into record (ID, time, category, event, place)
values (2, '2023-12-19 12:04:00', 1, 'Fall', '124');
insert into record (ID, time, category, event, place)
values (3, '2023-12-19 12:12:10', 1, 'Angry', '148');
insert into record (ID, time, category, event, place)
values (4, '2023-12-19 14:10:00', 1, 'Pain', '124');
insert into record (ID, time, category, event, place)
values (5, '2023-12-19 16:50:00', 1, 'Sad face', '144');

insert into caregiver (caregiver_id, name, gender, age, phone, responsible_group_id, password)
values (11291, 'Lily', '女', 45, 18329942348, 4, 8881);
insert into caregiver (caregiver_id, name, gender, age, phone, responsible_group_id, password)
values (12292, 'Mike', '男', 35, 18329942348, 4, 8281);
insert into caregiver (caregiver_id, name, gender, age, phone, responsible_group_id, password)
values (12293, 'Dog', '男', 35, 18329942348, 4, 8821);
insert into caregiver (caregiver_id, name, gender, age, phone, responsible_group_id, password)
values (12295, 'Jon', '女', 35, 18329942348, 4, 8881);
insert into caregiver (caregiver_id, name, gender, age, phone, responsible_group_id, password)
values (12299, 'Le', '男', 35, 18329942348, 4, 8881);

insert into user (id_number, name, gender, age, phone, emergency_contact, emergency_contact_person, group_id,
                  conditions, room_number, password)
values (933, 'jim', '男', 79, '19928811899', '19984842838', 'K', 3, '...', 133, '8881');
insert into user (id_number, name, gender, age, phone, emergency_contact, emergency_contact_person, group_id,
                  conditions, room_number, password)
values (952, 'jim', '男', 71, '19928811899', '19984842838', 'K', 3, '...', 133, '8881');
insert into user (id_number, name, gender, age, phone, emergency_contact, emergency_contact_person, group_id,
                  conditions, room_number, password)
values (181, 'jim', '男', 84, '19928811899', '19984842838', 'K', 5, '...', 133, '8881');
insert into user (id_number, name, gender, age, phone, emergency_contact, emergency_contact_person, group_id,
                  conditions, room_number, password)
values (153, 'jim', '男', 78, '19928811899', '19984842838', 'K', 4, '...', 133, '8881');
insert into user (id_number, name, gender, age, phone, emergency_contact, emergency_contact_person, group_id,
                  conditions, room_number, password)
values (328, 'jim', '男', 88, '19928811899', '19984842838', 'K', 1, '...', 144, '8881');
insert into user (id_number, name, gender, age, phone, emergency_contact, emergency_contact_person, group_id,
                  conditions, room_number, password)
values (327, 'jim', '男', 92, '19928811899', '19984842838', 'K', 1, '...', 144, '8881');

insert into res_group values (1,1,0);


select responsible_group_id, name, count(*)
from caregiver
where gender = '男'
group by responsible_group_id
order by responsible_group_id desc;


select responsible_group_id, count(*), max(user.age), min(user.age), avg(user.age)
from caregiver
         join user on responsible_group_id = group_id
group by responsible_group_id
order by avg(user.age) desc;

select responsible_group_id, name
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
        IF NEW.group_id NOT IN (SELECT responsible_group_id FROM caregiver) THEN
            SIGNAL SQLSTATE '81023' SET MESSAGE_TEXT = "用户组不存在";
        ELSE
            IF NEW.gender NOT IN ('男', '女') THEN
                SIGNAL SQLSTATE '81069' SET MESSAGE_TEXT = "性别填写错误";
            ELSE
                IF NEW.age > 200 THEN
                    SIGNAL SQLSTATE '81099' SET MESSAGE_TEXT = "年龄填写错误";
                ELSE
                    IF NEW.phone IS NULL AND NEW.password IS NULL AND
                       NEW.emergency_contact_person IS NULL AND NEW.emergency_contact IS NULL THEN
                        SIGNAL SQLSTATE '81000' SET MESSAGE_TEXT = "信息不能为空";
                    ELSE
                        IF NEW.room_number NOT IN (SELECT place FROM room) THEN
                            INSERT INTO room (place, description) VALUES (NEW.room_number, 'user room');
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
        IF NEW.responsible_group_id IS NULL THEN
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
                        IF NEW.responsible_group_id NOT IN (SELECT group_id FROM res_group) THEN
                            INSERT INTO res_group (group_id, caregiver_id, old_number) VALUES (NEW.responsible_group_id, NEW.caregiver_id, 0);
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
        if NEW.room_number not in (select place from room) then
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