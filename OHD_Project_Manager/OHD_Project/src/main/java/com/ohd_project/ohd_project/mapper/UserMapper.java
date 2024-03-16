package com.ohd_project.ohd_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.Caregiver;
import com.ohd_project.ohd_project.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    //    @Select("SELECT * from user WHERE name = #{name} and password = #{password};")
    User getUserByName(String name, String password);//匹配用户

    //    @Select("SELECT * from user;")
    List<User> getAllUsers();

    @Update("UPDATE user SET name = #{name},age = #{age},gender = #{gender}," +
            "phone = #{phone},GroupId=#{GroupId},emergencyContact=#{emerencyContact}," +
            "emergencyContactPerson = #{emergencyContactPerson},conditions = #{conditions}," +
            "roomNumber= #{roomNumber}  where id=#{id};")
    void myUpdateById(User user);

    @Insert("INSERT INTO user (id, name, gender, age, phone, emergencyContact, emergencyContactPerson, " +
            "groupId, conditions, roomNumber, password) " +
            "VALUES (#{id}, #{name}, #{gender}, #{age}, #{phone}, #{emergencyContact}, #{emergencyContactPerson}, " +
            "#{groupId}, #{conditions}, #{roomNumber}, #{password});")
    void insertUser(User user);
}
