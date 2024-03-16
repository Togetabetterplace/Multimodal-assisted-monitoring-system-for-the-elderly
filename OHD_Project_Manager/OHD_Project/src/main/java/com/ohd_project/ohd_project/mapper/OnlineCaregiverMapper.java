package com.ohd_project.ohd_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.Caregiver;
import com.ohd_project.ohd_project.entity.Online_Caregiver;
import com.ohd_project.ohd_project.entity.User;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Resource
public interface OnlineCaregiverMapper extends BaseMapper<Online_Caregiver> {
    @Select("SELECT * from online_caregiver WHERE caregiverId = #{caregiverId};")
    Online_Caregiver findOnlineCaregiverById(String caregiver_id);

    @Update("UPDATE online_caregiver set state = 1 where caregiverId = #{caregiverId};")
    void updateOnlineCaregiver(@Param("caregiverId") String caregiverId);
    @Update("UPDATE online_caregiver set state = 0 where caregiverId = #{caregiverId};")
    void Anti_updateOnlineCaregiver(@Param("caregiverId") String caregiverId);

    @Insert("INSERT INTO online_caregiver (caregiverId,name, responsibleGroupId,state) " +
            "values (#{caregiverId},#{name},#{responsibleGroupId},0);")
    void insertOnlineCaregiver(@Param("caregiverId") String caregiverId,
                               @Param("name") String name,
                               @Param("responsibleGroupId") String responsibleGroupId);

    @Delete("DELETE from online_caregiver where caregiverId = #{caregiverId};")
    void deleteOnlineCaregiver(String onlineCaregiverId);

    @Select("SELECT * from online_caregiver where state = 1;")
    List<Online_Caregiver> getAllOnlineCaregiver();
}
