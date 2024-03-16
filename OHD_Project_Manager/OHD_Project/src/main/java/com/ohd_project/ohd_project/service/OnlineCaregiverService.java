package com.ohd_project.ohd_project.service;

// com.ohd_project.ohd_project.service.CaregiverService.java

import com.baomidou.mybatisplus.extension.service.IService;
import com.ohd_project.ohd_project.entity.Online_Caregiver;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public interface OnlineCaregiverService extends IService<Online_Caregiver> {
    @Override
    default Online_Caregiver getById(Serializable id) {
        return IService.super.getById(id);
    }

    Online_Caregiver findOnlineCaregiverById(String caregiver_id);

    void insertOnlineCaregiver(String caregiver_id,String name,String responsible_group_id);

    void deleteOnlineCaregiver(String online_caregiver_id);

    List<Online_Caregiver> getAllOnlineCaregiver();

    //    String checkCaregiver(String caregiverName);
    //    String CaregiverLoginCheck(Caregiver loginCaregiver, HttpSession session);
}
