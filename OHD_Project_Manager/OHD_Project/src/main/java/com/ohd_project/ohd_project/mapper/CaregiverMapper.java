package com.ohd_project.ohd_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.Caregiver;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverMapper extends BaseMapper<Caregiver> {
    Caregiver getCaregiverByName(String name, String password);
    Caregiver findCaregiverById(String caregiverId);
    @Update("UPDATE caregiver SET name = #{name},age = #{age},gender = #{gender}," +
            "phone = #{phone},responsibleGroupId=#{responsibleGroupId} where caregiverId=#{caregiverId};")
    void myUpdateById(Caregiver caregiver);

    @Insert("INSERT INTO caregiver (caregiverId, name, gender, age, phone, responsibleGroupId, password) " +
            "VALUES (#{caregiverId}, #{name}, #{gender}, #{age}, #{phone}, #{responsibleGroupId}, #{password})")
    void insertCaregiver(Caregiver caregiver);
}
