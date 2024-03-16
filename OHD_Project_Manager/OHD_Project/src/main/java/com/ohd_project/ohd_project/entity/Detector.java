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

/**
 * &;Author：ZichenTian
 * &;Date：2023/12/10
 * &;Description：检测器实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "detector")
@AllArgsConstructor
@NoArgsConstructor
public class Detector extends Model<Detector> {
    @TableId(type = IdType.AUTO)
    private String threadID;
    @TableField
    private String roomNumber;
    @TableField
    private int category;
    @TableField
    private String source;
    @TableField
    private int state;
    // 添加无参构造函数和带参构造函数

    public Detector(String roomNumber, int category, String source, String ThreadID) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.source = source;
        // 自动生成3位数字字符串作为thread_ID
        this.threadID = ThreadID;
        // 设置state为0
        this.state = 0;

    }
}

