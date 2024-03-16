package com.ohd_project.ohd_project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "res_group")
@AllArgsConstructor
@NoArgsConstructor
public class ResGroup {
    String groupId;
    String caregiverId;
    int oldNumber;
}
