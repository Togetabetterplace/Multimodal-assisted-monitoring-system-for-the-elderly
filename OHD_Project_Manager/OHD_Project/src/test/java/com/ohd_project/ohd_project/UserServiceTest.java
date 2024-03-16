package com.ohd_project.ohd_project;

import com.ohd_project.ohd_project.entity.User;
import com.ohd_project.ohd_project.mapper.UserMapper;
import com.ohd_project.ohd_project.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class) // 使用Spring支持的JUnit5扩展
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void UserTest() {
//        User user = userMapper.getUserByName("张三");
//        System.out.println(user.getPassword());
//        System.out.println(user.getName());
//
//        String ans = userService.getUserByName("张三","8881");
//        System.out.println(ans);

    }
}
