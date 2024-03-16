package com.ohd_project.ohd_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.Long_time_record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LongTimeRecordMapper extends BaseMapper<Long_time_record> {
    //    @Select("SELECT * FROM record WHERE category = #{category}")
    List<Long_time_record> getLRecordsByCategory(int category);//获取临时记录表中的异常记录

    List<Long_time_record> getAllLRecords();

    @Insert("INSERT into long_time_record (ID, time, category, event, place)values " +
            "(#{ID},#{time},#{category},#{event},#{place});")
    void MyInsert(Long_time_record longTimeRecord);

}
