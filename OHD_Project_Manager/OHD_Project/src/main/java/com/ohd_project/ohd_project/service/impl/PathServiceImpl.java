package com.ohd_project.ohd_project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohd_project.ohd_project.entity.Path;
import com.ohd_project.ohd_project.mapper.PathMapper;
import org.springframework.stereotype.Service;

@Service
public class PathServiceImpl extends ServiceImpl<PathMapper, Path> {
    public void Update(Path path){
        baseMapper.updateById(path);
    }
    public void Insert(Path path){
        baseMapper.insert(path);
    }

    public Path getByDisc(String disc){
        return baseMapper.getByDisc(disc);
    }
}
