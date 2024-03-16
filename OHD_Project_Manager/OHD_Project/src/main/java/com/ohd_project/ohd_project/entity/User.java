package com.ohd_project.ohd_project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * &#064;Author：ZichenTian
 * &#064;Date：2023/12/10
 * &#064;Description：用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends Model<User> {
    @TableId
    private String id;
    @TableField
    private String name;
    @TableField
    private String gender;
    @TableField
    private int age;
    @TableField
    private String phone;
    @TableField
    private String emergencyContact;
    @TableField
    private String emergencyContactPerson;
    @TableField
    private int groupId;
    @TableField
    private String conditions;
    @TableField
    private String roomNumber;
    @TableField
    private String password;
}
