package com.ohd_project.ohd_project.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结果视图对象（ResultVO）用于封装返回给前端的结果信息。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    // 返回结果的状态码
    private int code;
    // 返回结果的消息描述
    private String msg;
    // 返回的数据对象
    private Object data;
}
