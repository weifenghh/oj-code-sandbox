package com.yupi.ojcodesandbox.security;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/10 14:47
 * @Package com.yupi.ojcodesandbox.security
 * @Version 1.0
 * @Since 1.0
 */
public class TestSecurityManager {

    public static void main(String[] args) {
        System.setSecurityManager(new MySecurityManager());

//        List<String> strings = FileUtil.readLines(new File("D:\\yupi\\OJ\\code\\oj-code-sandbox\\src\\main\\resources\\application.yml"), StandardCharsets.UTF_8);
//        System.out.println(strings);
        FileUtil.writeString("aa","aaa", Charset.defaultCharset());
    }

}
