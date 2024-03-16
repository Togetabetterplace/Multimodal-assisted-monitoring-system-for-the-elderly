package com.ohd_project.ohd_project.service.impl;

// com.ohd_project.ohd_project.service.impl.RecordServiceImpl.java

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohd_project.ohd_project.entity.Long_time_record;
import com.ohd_project.ohd_project.mapper.LongTimeRecordMapper;
import com.ohd_project.ohd_project.mapper.RecordMapper;
import com.ohd_project.ohd_project.entity.Record;
import com.ohd_project.ohd_project.service.RecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class   RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    /*
     * 1.获取一段时间内(比如3s)异常检测记录，大于阈值则表明产生异常，报警
     * 2.对长期异常检测记录提取后生成监护日志
     */

    @Resource
    LongTimeRecordMapper longTimeRecordMapper;
    @Override
    public List<Record> getRecordsByCategory(int category) {
        return baseMapper.getRecordsByCategory(category);
    }

    @Override
    public List<Record> getAllRecords(){
        return baseMapper.getAllRecords();
    }

    public String generateId() {
        String newId;
        do {
            newId = generateRandomId();
        } while (IdAlreadyExists(newId));
        return newId;
    }

    private String generateRandomId() {
        // 在这里实现生成3位数字字符串的逻辑，例如使用随机数或其他方式
        // 返回生成的thread_ID
        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;
        return String.valueOf(randomNumber);
    }

    private boolean IdAlreadyExists(String newId) {
        // 在这里添加检查数据库中是否已存在相同thread_ID的逻辑
        // 使用 QueryWrapper 创建查询条件

        QueryWrapper<Long_time_record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ID", newId);

        // 调用 MyBatis Plus 提供的 count 方法统计符合条件的记录数

        int count = Math.toIntExact(longTimeRecordMapper.selectCount(queryWrapper));

        // 如果存在相同的 thread_ID，则返回 true；否则返回 false
        return count > 0;
    }
}
