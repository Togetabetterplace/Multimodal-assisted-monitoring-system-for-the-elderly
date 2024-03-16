package com.ohd_project.ohd_project.controller;

import com.ohd_project.ohd_project.entity.Caregiver;
import com.ohd_project.ohd_project.entity.ResGroup;
import com.ohd_project.ohd_project.mapper.CaregiverMapper;
import com.ohd_project.ohd_project.mapper.ResGroupMapper;
import com.ohd_project.ohd_project.service.impl.CaregiverServiceImpl;
import com.ohd_project.ohd_project.service.impl.OnlineCaregiverServiceImpl;
import com.ohd_project.ohd_project.service.impl.ResGroupServiceImpl;
import com.ohd_project.ohd_project.utils.ResultVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * 护工相关操作的控制器类
 */
@Controller
@RequestMapping("/caregivers")
public class CaregiverController {

    @Resource
    private CaregiverServiceImpl caregiverService;

    @Resource
    private CaregiverMapper caregiverMapper;

    @Resource
    private OnlineCaregiverServiceImpl onlineCaregiverService;

    @Resource
    private ResGroupMapper resGroupMapper;

    @Resource
    private ResGroupServiceImpl resGroupService;

    /**
     * 映射到护工登录页面
     */
    @RequestMapping("/login")
    public String login() {
        return "CaregiverLogin";
    }

    /**
     * 映射到护工主页
     */
    @RequestMapping("/caregiverMain")
    public String getMain() {
        return "caregiverMain";
    }

    /**
     * 映射到护工个人中心页面
     */
    @RequestMapping("/caregiverData")
    public String caregiverData() {
        return "CaregiverData";
    }

    /**
     * 映射到护工注册页面
     */
    @RequestMapping("/register")
    public String register() {
        return "CaregiverRegister";
    }

    /**
     * 映射到数据页面
     */
    @RequestMapping("/data")
    public String getDate() {
        return "Data";
    }

    /**
     * 处理护工登录请求
     *
     * @param formData 包含登录信息的请求体
     * @param session  HttpSession对象，用于存储登录状态
     * @return 返回登录结果的ResultVO对象
     */
    @RequestMapping(value = "/caregiverlogin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO loginCaregiver(@RequestBody Map<String, String> formData, HttpSession session) {
        // 从请求体中获取护工姓名和密码
        String name = formData.get("name");
        String password = formData.get("password");
        // 调用服务层方法验证护工信息
        String key = caregiverService.checkCaregiverByName(name, password);

        // 输出验证结果
        System.out.println(key);

        // 在Session中设置护工信息
        session.setAttribute("Caregiver", new Caregiver());

        // 根据验证结果返回相应的ResultVO对象
        if (Objects.equals(key, "101")) {
            Caregiver caregiver = caregiverService.getCaregiverByName(name, password);
            String caregiverId = caregiver.getCaregiverId();
            String responsibleGroupId = caregiver.getResponsibleGroupId();
            if (caregiverId != null && responsibleGroupId != null) {
                System.out.println("护工-" + name + "上线了");
                onlineCaregiverService.updateOnlineCaregiver(caregiverId, 1);
            }
            return new ResultVO(1001, "登陆成功", caregiver);
        } else if (Objects.equals(key, "102")) {
            return new ResultVO(1002, "密码错误", null);
        } else {
            return new ResultVO(1003, "护工不存在", null);
        }
    }

    /**
     * 处理护工注册请求
     *
     * @param caregiverDetails 包含护工详细信息的请求体
     * @return 返回注册结果的ResultVO对象
     */
    @RequestMapping(value = "/caregiverregister", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO registerCaregiver(@RequestBody Map<String, String> caregiverDetails) {
        try {
            // 从请求体中获取护工详细信息并创建护工对象
            Caregiver caregiver = new Caregiver(
                    caregiverDetails.get("caregiverId"),
                    caregiverDetails.get("name"),
                    caregiverDetails.get("gender"),
                    Integer.parseInt(caregiverDetails.get("age")),
                    caregiverDetails.get("phone"),
                    caregiverDetails.get("responsibleGroupId"),
                    caregiverDetails.get("password")
            );
            System.out.println(caregiverDetails.get("caregiverId"));
            // 调用服务层方法保存护工信息
            caregiverService.insertCaregiver(caregiver);
            onlineCaregiverService.insertOnlineCaregiver(caregiverDetails.get("caregiverId"),
                    caregiverDetails.get("name"), caregiverDetails.get("responsibleGroupId"));

            if (resGroupMapper.getResGroupById(caregiverDetails.get("responsibleGroupId")) == null) {
                ResGroup resGroup = new ResGroup(caregiverDetails.get("responsibleGroupId"),
                        caregiverDetails.get("caregiverId"), 0);
                resGroupService.insertResGroup(resGroup);
            }
            // 注册成功的返回
            return new ResultVO(1001, "注册成功", null);
        } catch (Exception e) {
            // 处理异常，可以根据需要返回不同的错误信息
            e.printStackTrace();
            return new ResultVO(500, "注册失败，发生异常", null);
        }
    }

    /**
     * 处理护工登出请求
     *
     * @param formData 包含护工ID的请求体
     * @return 返回登出结果的ResultVO对象
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO logout(@RequestBody Map<String, String> formData) {
        // 调用服务层方法删除在线护工信息
        onlineCaregiverService.updateOnlineCaregiver(formData.get("caregiverId"), 0);
        // 返回登出成功的ResultVO对象
        return new ResultVO(1001, "登出成功", null);
    }

    /**
     * 根据护工ID查找护工信息
     *
     * @param caregiverId 护工ID
     * @return 返回护工对象
     */
    public Caregiver findCaregiverById(String caregiverId) {
        return caregiverService.findCaregiverById(caregiverId);
    }


    /**
     * 处理护工编辑请求
     *
     * @return 返回修改结果的ResultVO对象
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO editInfo(@RequestBody Map<String, String> formData) {
        String id = formData.get("caregiverId");
        System.out.println("id: " + id);
        Caregiver caregiver = findCaregiverById(id);
        System.out.println("编辑护工姓名为：" + caregiver.getName());
        int flag = 0;
        if (formData.get("name") != null) {
            caregiver.setName(formData.get("name"));
            caregiverMapper.myUpdateById(caregiver);
            flag = 1;
        } else if (formData.get("age") != null) {
            caregiver.setAge(Integer.parseInt(formData.get("age")));
            caregiverMapper.myUpdateById(caregiver);
            flag = 1;
        } else if (formData.get("gender") != null) {
            caregiver.setGender(formData.get("gender"));
            caregiverMapper.myUpdateById(caregiver);
            flag = 1;
        } else if (formData.get("phone") != null) {
            caregiver.setPhone(formData.get("phone"));
            caregiverMapper.myUpdateById(caregiver);
            flag = 1;
        } else if (formData.get("group") != null) {
            caregiver.setResponsibleGroupId(formData.get("group"));
            caregiverMapper.myUpdateById(caregiver);
            flag = 1;
        }
        if (flag == 1) {
            return new ResultVO(1001, "编辑成功", null);
        } else {
            return new ResultVO(500, "编辑失败", null);
        }
    }

}
