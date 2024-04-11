package com.yupi.oj.judge.codesandbox.model;

import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author 玉米排骨汤
 * @Date 2024/1/14 18:24
 * @Package com.yupi.oj.judge.codesandbox.model
 * @Version 1.0
 * @Since 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    /**
     * 输出用例
     */
    private List<String> outputList;
    /**
     * 接口信息
     */
    private String message;
    /**
     * 执行状态
     */
    private Integer status;
    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

}
