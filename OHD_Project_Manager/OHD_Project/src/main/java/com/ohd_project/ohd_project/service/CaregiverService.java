package com.ohd_project.ohd_project.service;

// com.ohd_project.ohd_project.service.CaregiverService.java

import com.baomidou.mybatisplus.extension.service.IService;
import com.ohd_project.ohd_project.entity.Caregiver;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface CaregiverService extends IService<Caregiver> {
    String checkCaregiverByName(String name, String password);

    Caregiver getCaregiverByName(String name, String password);

    void insertCaregiver(Caregiver caregiver);
}
