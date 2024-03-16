package com.ohd_project.ohd_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ohd_project.ohd_project.entity.Path;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Resource
public interface PathMapper extends BaseMapper<Path> {
    @Select("SELECT * from Path where disc = #{disc};")
    Path getByDisc(String disc);
}
