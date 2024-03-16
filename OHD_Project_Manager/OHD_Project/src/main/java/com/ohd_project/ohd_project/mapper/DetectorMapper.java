package com.ohd_project.ohd_project.mapper;

// com.ohd_project.ohd_project.mapper.DetectorMapper.java

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.Caregiver;
import com.ohd_project.ohd_project.entity.Detector;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Source;

@Mapper
@Repository
public interface DetectorMapper extends BaseMapper<Detector> {
//    @Insert("INSERT INTO detector(thread_ID, room_number, category, source, state)" +
//            " VALUES (#{thread_ID},#{room_number},#{category},#{source},#{state})")
    void insertNewDetector(@Param("threadID") String threadID,
                           @Param("roomNumber") String roomNumber,
                           @Param("category") int category,
                           @Param("source") String source,
                           @Param("state") int state);
    ;


    @Delete("DELETE from detector where roomNumber=#{roomNumber} and " +
            "source = #{Source} and category = #{category}")
    void delDetector(String roomNumber,String Source,int Caregiver);
}
