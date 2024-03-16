package com.ohd_project.ohd_project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/**
 * &#064;Author：ZichenTian
 * &#064;Date：2023/12/10
 * &#064;Description：记录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "record")
@AllArgsConstructor
@NoArgsConstructor
public class Record extends Model<Record>{
    @TableId
    private String ID;
    @TableField
    private LocalDateTime time;
    @TableField
    private int category;
    @TableField
    private String event;
    @TableField
    private String place;
}
