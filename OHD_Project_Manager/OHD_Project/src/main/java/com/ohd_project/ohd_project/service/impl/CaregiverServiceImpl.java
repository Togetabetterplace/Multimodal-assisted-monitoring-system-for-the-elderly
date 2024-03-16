package com.ohd_project.ohd_project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohd_project.ohd_project.mapper.CaregiverMapper;
import com.ohd_project.ohd_project.entity.Caregiver;
import com.ohd_project.ohd_project.service.CaregiverService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 护工服务实现类（CaregiverServiceImpl）继承自 MyBatis-Plus 的 ServiceImpl，实现了 CaregiverService 接口。
 * 该类提供了护工的注册、登录、编辑、删除以及查找当前在线护工等功能。
 */
@Service
public class CaregiverServiceImpl extends ServiceImpl<CaregiverMapper, Caregiver> implements CaregiverService {

    /**
     * 检查护工登录状态，根据护工用户名和密码进行验证。
     *
     * @param name     护工用户名
     * @param password 护工密码
     * @return 登录状态代码（"101"：登录成功，"102"：密码错误，"103"：护工不存在）
     */
    public String checkCaregiverByName(String name, String password) {
        System.out.println("查询的护工是：" + name);
        System.out.println("密码是：" + password);
        Caregiver caregiver = baseMapper.getCaregiverByName(name, password);
        if (caregiver != null) {
            if (Objects.equals(caregiver.getPassword(), password)) {
                return "101";  // 登录成功
            } else {
                return "102";  // 密码错误
            }
        } else {
            return "103";  // 护工不存在
        }
    }

    /**
     * 根据护工用户名和密码获取护工信息。
     *
     * @param name     护工用户名
     * @param password 护工密码
     * @return 护工对象
     */
    public Caregiver getCaregiverByName(String name, String password) {
        return baseMapper.getCaregiverByName(name, password);
    }

    /**
     * 根据护工ID查找护工。
     *
     * @param caregiver_id 护工ID
     * @return 护工对象
     */
    public Caregiver findCaregiverById(String caregiver_id) {
        return baseMapper.findCaregiverById(caregiver_id);
    }

    /**
     * 插入新护工。
     *
     * @param caregiver 待插入的护工对象
     */
    public void insertCaregiver(Caregiver caregiver) {
        baseMapper.insertCaregiver(caregiver);
    }
}
