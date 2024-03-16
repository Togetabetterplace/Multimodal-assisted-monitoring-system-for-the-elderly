package com.ohd_project.ohd_project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * &#064;Author：ZichenTian
 * &#064;Date：2023/12/10
 * &#064;Description：在线护工实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "online_caregiver")
@AllArgsConstructor
@NoArgsConstructor
public class Online_Caregiver extends Model<Online_Caregiver> {
    @TableId
    private String caregiverId;
    @TableField
    private String name;
    @TableField
    private String responsibleGroupId;
    @TableField
    private int state;
}
