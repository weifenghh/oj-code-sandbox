package com.yupi.ojcodesandbox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/7 18:49
 * @Package com.yupi.ojcodesandbox
 * @Version 1.0
 * @Since 1.0
 */

@RestController("/")
public class HelloController {

    @GetMapping("/health")
    public String healthCheck(){
        return "ok";
    }

}
