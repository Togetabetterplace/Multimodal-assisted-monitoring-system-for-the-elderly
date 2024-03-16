package com.ohd_project.ohd_project.controller;

import com.ohd_project.ohd_project.entity.Online_Caregiver;
import com.ohd_project.ohd_project.service.OnlineCaregiverService;
import com.ohd_project.ohd_project.utils.ResultVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/OnlineCaregivers")
public class OnlineCaregiverController {

    @Resource
    private OnlineCaregiverService onlineCaregiverService;

    /**
     * 获取在线护工列表
     * @return 在线护工列表信息
     */
    @GetMapping("/onlineList")
    public ResultVO getRecordsByCategory() {

         List<Online_Caregiver> onlineCaregiverList = onlineCaregiverService.getAllOnlineCaregiver();

        return new ResultVO(1001, "在线护工列表", onlineCaregiverList);
    }
}