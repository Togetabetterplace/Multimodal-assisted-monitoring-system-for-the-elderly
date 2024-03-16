package com.ohd_project.ohd_project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ohd_project.ohd_project.controller.DetectorController;
import com.ohd_project.ohd_project.entity.Detector;
import com.ohd_project.ohd_project.mapper.DetectorMapper;
import com.ohd_project.ohd_project.service.DetectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DetectorServiceTest {

    @Autowired
    private DetectorService detectorService;

    @Autowired
    private DetectorMapper detectorMapper;

    @Autowired
    private DetectorController detectorController;

    @Test
    public void DetectorTest() {
        assertNotNull(this.detectorService);
        assertNotNull(this.detectorMapper);
        String Thread_Id = detectorController.generateThreadId();
        QueryWrapper<Detector> detectorQueryWrapper = Wrappers.query();
        int count = Math.toIntExact(detectorMapper.selectCount(detectorQueryWrapper));
        System.out.println("Number of detectors: " + count);

        Detector detector = new Detector("121", 1, "rtsp://webcam/mjpg/camera_1", Thread_Id);
        String thread_ID = detector.getThreadID();
        String room_number = detector.getRoomNumber();
        int category = detector.getCategory();
        String source = detector.getSource();
        int state = detector.getState();
        detectorService.insertNewDetector(thread_ID,room_number,category,source,0);
    }
}
