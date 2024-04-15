package com.yupi.ojcodesandbox;

import com.yupi.ojcodesandbox.model.ExecuteCodeRequest;
import com.yupi.ojcodesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/15 12:24
 * @Package com.yupi.ojcodesandbox
 * @Version 1.0
 * @Since 1.0
 */

/**
 * java原生代码沙箱实现
 */

@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
