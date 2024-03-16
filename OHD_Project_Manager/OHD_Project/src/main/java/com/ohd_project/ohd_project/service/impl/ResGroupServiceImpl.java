package com.ohd_project.ohd_project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ohd_project.ohd_project.entity.ResGroup;
import com.ohd_project.ohd_project.mapper.ResGroupMapper;
import org.springframework.stereotype.Service;

@Service
public class ResGroupServiceImpl extends ServiceImpl<ResGroupMapper, ResGroup> {

    public void insertResGroup(ResGroup resGroup) {
        baseMapper.insertResGroup(resGroup);
    }
}