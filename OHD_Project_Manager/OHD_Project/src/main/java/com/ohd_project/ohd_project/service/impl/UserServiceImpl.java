package com.ohd_project.ohd_project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ohd_project.ohd_project.mapper.UserMapper;
import com.ohd_project.ohd_project.entity.User;
import com.ohd_project.ohd_project.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户服务实现类（UserServiceImpl）继承自 MyBatis-Plus 的 ServiceImpl，实现了 UserService 接口。
 * 该类提供了用户的注册、登录、删除、编辑等功能。
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 获取所有用户信息。
     *
     * @return 包含所有用户信息的列表
     */
    @Override
    public List<User> getAllUsers() {
        return baseMapper.getAllUsers();
    }

    /**
     * 检查用户登录状态，根据用户名和密码进行验证。
     *
     * @param name     用户名
     * @param password 密码
     * @return 登录状态代码（"101"：登录成功，"102"：密码错误，"103"：用户不存在）
     */
    @Override
    public String checkUserByName(String name, String password) {
        System.out.println("查询的用户是：" + name);
        System.out.println("密码是：" + password);
        User user = baseMapper.getUserByName(name, password);
        if (user != null) {
            if (Objects.equals(user.getPassword(), password)) {
                return "101";  // 登录成功
            } else {
                return "102";  // 密码错误
            }
        } else {
            return "103";  // 用户不存在
        }
    }

    /**
     * 根据用户名和密码获取用户信息。
     *
     * @param name     用户名
     * @param password 密码
     * @return 用户对象
     */
    public User getUserByName(String name, String password) {
        return baseMapper.getUserByName(name, password);
    }

    /**
     * 根据用户ID查找用户。
     *
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public User findUserById(String id) {
        return baseMapper.selectById(id);
    }

    /**
     * 插入新用户。
     *
     * @param user 待插入的用户对象
     */
    @Override
    public void insertUser(User user) {
        baseMapper.insertUser(user);
    }

}
