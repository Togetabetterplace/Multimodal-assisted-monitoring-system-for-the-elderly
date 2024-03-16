package com.ohd_project.ohd_project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ohd_project.ohd_project.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService extends IService<User>{
    List<User> getAllUsers();

    String checkUserByName(String name, String password);

    User getUserByName(String name, String password);

    User findUserById(String id);

    void insertUser(User user);
}
