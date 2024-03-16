package com.ohd_project.ohd_project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohd_project.ohd_project.entity.Online_Caregiver;
import com.ohd_project.ohd_project.mapper.OnlineCaregiverMapper;
import com.ohd_project.ohd_project.service.OnlineCaregiverService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineCaregiverServiceImpl extends ServiceImpl<OnlineCaregiverMapper, Online_Caregiver> implements OnlineCaregiverService {
    public Online_Caregiver findOnlineCaregiverById(String caregiver_id) {
        return baseMapper.findOnlineCaregiverById(caregiver_id);
    }

    public void insertOnlineCaregiver(String caregiverId, String name, String responsibleGroupId) {
        baseMapper.insertOnlineCaregiver(caregiverId, name, responsibleGroupId);
    }

    public void updateOnlineCaregiver(String caregiverId, int state) {
        if (state == 1) {
            baseMapper.updateOnlineCaregiver(caregiverId);
        } else {
            baseMapper.Anti_updateOnlineCaregiver(caregiverId);
        }
    }

    public void deleteOnlineCaregiver(String caregiverId) {
        baseMapper.deleteOnlineCaregiver(caregiverId);
    }

    public List<Online_Caregiver> getAllOnlineCaregiver() {
        return baseMapper.getAllOnlineCaregiver();
    }
}
