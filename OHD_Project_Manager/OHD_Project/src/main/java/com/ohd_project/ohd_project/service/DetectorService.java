package com.ohd_project.ohd_project.service;

// com.ohd_project.ohd_project.service.DetectorService.java

import com.baomidou.mybatisplus.extension.service.IService;
import com.ohd_project.ohd_project.entity.Detector;
import org.springframework.stereotype.Service;

@Service
public interface DetectorService extends IService<Detector> {

    void insertNewDetector(String threadID, String roomNumber, int category, String source, int state);

    void delDetector(String roomNumber, int Category, String Source);
}
