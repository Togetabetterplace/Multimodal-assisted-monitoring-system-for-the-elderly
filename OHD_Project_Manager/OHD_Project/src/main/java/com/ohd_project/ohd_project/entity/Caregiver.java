package com.ohd_project.ohd_project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

/**
 * &#064;Author：ZichenTian
 * &#064;Date：2023/12/10
 * &#064;Description：护工实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "caregiver")
@AllArgsConstructor
@NoArgsConstructor
public class Caregiver extends Model<Caregiver> {
    @TableId
    private String caregiverId;
    @TableField
    private String name;
    @TableField
    private String gender;
    @TableField
    private int age;
    @TableField
    private String phone;
    @TableField
    private String responsibleGroupId;
    @TableField
    private String password;
}
