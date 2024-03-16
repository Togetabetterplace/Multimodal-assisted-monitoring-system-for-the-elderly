package com.ohd_project.ohd_project.service.impl;

// com.ohd_project.ohd_project.service.impl.DetectorServiceImpl.java

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohd_project.ohd_project.mapper.DetectorMapper;
import com.ohd_project.ohd_project.entity.Detector;
import com.ohd_project.ohd_project.service.DetectorService;
import org.springframework.stereotype.Service;

/*
 * 插入新的Detector
 */
@Service
public class DetectorServiceImpl extends ServiceImpl<DetectorMapper, Detector> implements DetectorService {

    @Override
    public void insertNewDetector(String threadID, String roomNumber, int category, String source, int state) {
        baseMapper.insertNewDetector(threadID, roomNumber, category, source, state);
    }

    @Override
    public void delDetector(String roomNumber, int Category, String Source) {
        baseMapper.delDetector(roomNumber, Source, Category);
    }
}
