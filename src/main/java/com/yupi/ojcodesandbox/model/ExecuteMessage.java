package com.yupi.ojcodesandbox.model;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/8 14:39
 * @Package com.yupi.ojcodesandbox.model
 * @Version 1.0
 * @Since 1.0
 */

import lombok.Data;

/**
 * 进程执行信息
 */
@Data
public class ExecuteMessage {

    private Integer exitValue;
    private String message;
    private String errorMessage;
    private Long time;

}
