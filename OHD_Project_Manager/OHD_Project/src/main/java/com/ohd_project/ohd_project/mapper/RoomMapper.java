package com.ohd_project.ohd_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.Room;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper extends BaseMapper<Room> {

    @Select("SELECT * from room WHERE place=#{place};")
    Room getRoomByPlace(String place);//匹配用户

    @Select("SELECT * from room;")
    List<Room> getAllRooms();

    @Insert("INSERT INTO room (place, description) " +
            "VALUES (#{place}, #{description});")
    void insertRoom(Room room);
}