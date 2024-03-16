package com.ohd_project.ohd_project.mapper;

// com.ohd_project.ohd_project.mapper.RecordMapper.java

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecordMapper extends BaseMapper<Record> {
//    @Select("SELECT * FROM record WHERE category = #{category}")
    List<Record> getRecordsByCategory(int category);//获取临时记录表中的异常记录

    List<Record> getAllRecords();
}
