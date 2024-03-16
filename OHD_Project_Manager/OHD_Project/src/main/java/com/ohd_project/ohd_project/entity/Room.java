package com.ohd_project.ohd_project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "room")
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    String place;
    String description;
}
