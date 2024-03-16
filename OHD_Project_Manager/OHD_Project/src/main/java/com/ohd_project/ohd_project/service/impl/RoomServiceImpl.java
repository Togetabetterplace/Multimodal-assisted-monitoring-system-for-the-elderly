package com.ohd_project.ohd_project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ohd_project.ohd_project.entity.Room;
import com.ohd_project.ohd_project.mapper.RoomMapper;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> {

    public void insertRoom(Room room) {
        baseMapper.insertRoom(room);
    }
}
