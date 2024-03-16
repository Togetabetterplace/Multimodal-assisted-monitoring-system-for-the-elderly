// UserController.java

package com.ohd_project.ohd_project.controller;

import com.ohd_project.ohd_project.entity.Caregiver;
import com.ohd_project.ohd_project.entity.Room;
import com.ohd_project.ohd_project.entity.User;
import com.ohd_project.ohd_project.mapper.ResGroupMapper;
import com.ohd_project.ohd_project.mapper.RoomMapper;
import com.ohd_project.ohd_project.mapper.UserMapper;
import com.ohd_project.ohd_project.service.UserService;
import com.ohd_project.ohd_project.service.impl.RoomServiceImpl;
import com.ohd_project.ohd_project.utils.ResultVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * 用户管理控制器类
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private RoomServiceImpl roomService;

    @Resource
    private ResGroupMapper resGroupMapper;

    /**
     * 获取所有用户列表
     *
     * @param model 数据模型，用于传递用户列表到视图
     * @return 返回用户列表视图的 Thymeleaf 模板名称
     */
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "userList"; // 假设你有一个名为 "userList.html" 的 Thymeleaf 模板
    }

    /**
     * 映射到用户登录界面
     */
    @RequestMapping("/login")
    public String login() {
        return "UserLogin";
    }

    /**
     * 映射到数据页面
     */
    @RequestMapping("/data")
    public String getDate() {
        return "Data";
    }

    /**
     * 映射到用户主页
     */
    @RequestMapping("/userMain")
    public String getMain() {
        return "userMain";
    }

    /**
     * 映射到用户个人中心界面
     */
    @RequestMapping("/userData")
    public String userData() {
        return "UserData";
    }

    /**
     * 映射到用户注册界面
     */
    @RequestMapping("/register")
    public String register() {
        return "UserRegister";
    }

    /**
     * 处理用户登录请求
     *
     * @param formData 包含用户登录信息的请求体
     * @param session  HttpSession 用于存储登录用户实体
     * @return 返回登录结果的 ResultVO 对象
     */
    @RequestMapping(value = "/userlogin", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO loginUser(@RequestBody Map<String, String> formData, HttpSession session) {
        String name = formData.get("name");
        String password = formData.get("password");
        String key = userService.checkUserByName(name, password);

        // 在登录成功后将用户实体存储在 HttpSession 中
        session.setAttribute("User", new User());

        if (Objects.equals(key, "101")) {
            User user = userService.getUserByName(name, password);
            return new ResultVO(1001, "登陆成功", user);
        } else if (Objects.equals(key, "102")) {
            return new ResultVO(1002, "密码错误", null);
        } else {
            return new ResultVO(1003, "用户不存在", null);
        }
    }
    /**
     * 处理用户注册请求
     *
     * @param requestBody 包含用户注册信息的请求体
     * @return 返回注册结果的 ResultVO 对象
     */
    @RequestMapping(value = "/userregister", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO registerUser(@RequestBody Map<String, String> requestBody) {
        try {
            // 解析请求体参数
            String id = requestBody.get("id");
            String name = requestBody.get("name");
            String password = requestBody.get("password");
            String gender = requestBody.get("gender");
            int age = Integer.parseInt(requestBody.get("age"));
            String phone = requestBody.get("phone");
            String emergencyContact = requestBody.get("emergencyContact");
            String emergencyContactPerson = requestBody.get("emergencyContactPerson");
            int groupId = Integer.parseInt(requestBody.get("groupId"));
            String conditions = requestBody.get("conditions");
            String roomNumber = requestBody.get("roomNumber");

            // 创建 User 对象并保存到数据库
            User user = new User(id, name, gender, age, phone, emergencyContact,
                    emergencyContactPerson, groupId, conditions, roomNumber, password);
            userService.insertUser(user);
            if (roomMapper.getRoomByPlace(roomNumber) == null) {
                System.out.println("新加入房间：" + roomNumber);
                Room room = new Room(roomNumber, "room");
                roomService.insertRoom(room);
            }
            resGroupMapper.UpdateNumber(groupId);

            // 注册成功的返回
            return new ResultVO(1001, "注册成功", null);
        } catch (Exception e) {
            // 处理异常，可以根据需要返回不同的错误信息
            e.printStackTrace();
            return new ResultVO(500, "注册失败，发生异常", null);
        }
    }

    /**
     * 处理护工注册请求
     *
     * @return 返回修改结果的ResultVO对象
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultVO editInfo(@RequestBody Map<String, String> formData) {
        String id = formData.get("id");
        User user = userService.findUserById(id);
        int flag = 0;
        if (formData.get("name") != null) {
            user.setName(formData.get("name"));
            userMapper.myUpdateById(user);
            flag = 1;
        } else if (formData.get("age") != null) {
            user.setAge(Integer.parseInt(formData.get("age")));
            userMapper.myUpdateById(user);
            flag = 1;
        } else if (formData.get("gender") != null) {
            user.setGender(formData.get("gender"));
            userMapper.myUpdateById(user);
            flag = 1;
        } else if (formData.get("phone") != null) {
            user.setPhone(formData.get("phone"));
            userMapper.myUpdateById(user);
            flag = 1;
        } else if (formData.get("group") != null) {
            user.setGroupId(Integer.parseInt(formData.get("group")));
            userMapper.myUpdateById(user);
            flag = 1;
        } else if (formData.get("ec") != null) {
            user.setEmergencyContact(formData.get("ec"));
            userMapper.myUpdateById(user);
            flag = 1;
        } else if (formData.get("ecp") != null) {
            user.setEmergencyContactPerson(formData.get("ecp"));
            userMapper.myUpdateById(user);
            flag = 1;
        } else if (formData.get("condition") != null) {
            user.setConditions(formData.get("condition"));
            userMapper.myUpdateById(user);
            flag = 1;
        } else if (formData.get("roomNumber") != null) {
            user.setRoomNumber(formData.get("roomNumber"));
            userMapper.myUpdateById(user);
            flag = 1;
        }
        if (flag == 1) {
            return new ResultVO(1001, "编辑成功", null);
        } else {
            return new ResultVO(500, "编辑失败", null);
        }
    }

}

