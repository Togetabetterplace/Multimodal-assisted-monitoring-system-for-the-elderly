package com.ohd_project.ohd_project.service;

// com.ohd_project.ohd_project.service.RecordService.java

import com.baomidou.mybatisplus.extension.service.IService;
import com.ohd_project.ohd_project.entity.Long_time_record;
import com.ohd_project.ohd_project.entity.Record;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface LongTimeRecordService extends IService<Long_time_record> {
    List<Long_time_record> getLRecordsByCategory(int category);

    /*
     * 1.获取一段时间内(比如3s)异常检测记录，大于阈值则表明产生异常，报警
     * 2.对长期异常检测记录提取后生成监护日志
     */

    List<Long_time_record> getAllLRecords();

    void MyInsert(Long_time_record longTimeRecord);
}
