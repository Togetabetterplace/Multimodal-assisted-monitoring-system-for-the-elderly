package com.ohd_project.ohd_project.service.impl;

// com.ohd_project.ohd_project.service.impl.RecordServiceImpl.java

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohd_project.ohd_project.entity.Long_time_record;
import com.ohd_project.ohd_project.entity.Record;
import com.ohd_project.ohd_project.mapper.LongTimeRecordMapper;
import com.ohd_project.ohd_project.service.LongTimeRecordService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LongTimeRecordServiceImpl extends ServiceImpl<LongTimeRecordMapper, Long_time_record> implements LongTimeRecordService {

    /*
     * 获取一段时间内(长期,如一周)异常检测记录，用于生成监护日志
     */
    @Override
    public List<Long_time_record> getLRecordsByCategory(int category) {
        return baseMapper.getLRecordsByCategory(category);
    }
    public List<Long_time_record> getAllLRecords(){
        return baseMapper.getAllLRecords();
    }

    @Override
    public void MyInsert(Long_time_record longTimeRecord) {
        baseMapper.MyInsert(longTimeRecord);
    }

}
