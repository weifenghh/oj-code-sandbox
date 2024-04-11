package com.yupi.oj.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 玉米排骨汤
 * @Date 2024/1/14 18:20
 * @Package com.yupi.oj.judge.codesandbox.model
 * @Version 1.0
 * @Since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {

    private List<String> inputList;
    private String code;
    private String language;

}
