package com.ohd_project.ohd_project.mapper;

import org.apache.ibatis.annotations.Insert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.ResGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResGroupMapper extends BaseMapper<ResGroup> {

    @Select("SELECT * from res_group WHERE groupId=#{groupId};")
    ResGroup getResGroupById(String groupId);//匹配用户

    @Select("SELECT * from res_group;")
    List<ResGroup> getAllResGroups();

    @Insert("INSERT INTO res_group (groupId, caregiverId, oldNumber) " +
            "VALUES (#{groupId}, #{caregiverId}, #{oldNumber})")
    void insertResGroup(ResGroup resGroup);
    @Update("UPDATE res_group SET oldNumber=oldNumber+1 where groupId=#{groupId};")
    void UpdateNumber(int groupId);
}