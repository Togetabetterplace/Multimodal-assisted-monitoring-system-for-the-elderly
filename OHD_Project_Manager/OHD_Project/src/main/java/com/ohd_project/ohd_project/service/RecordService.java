package com.ohd_project.ohd_project.service;

// com.ohd_project.ohd_project.service.RecordService.java

import com.baomidou.mybatisplus.extension.service.IService;
import com.ohd_project.ohd_project.entity.Record;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RecordService extends IService<Record> {
    List<Record> getRecordsByCategory(int category);

    List<Record> getAllRecords();

}
