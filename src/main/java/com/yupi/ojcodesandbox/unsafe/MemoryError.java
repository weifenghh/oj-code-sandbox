package com.yupi.ojcodesandbox.unsafe;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/9 14:03
 * @Package com.yupi.ojcodesandbox
 * @Version 1.0
 * @Since 1.0
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 无线占用内存（浪费系统内存）
 */
public class MemoryError {

    public static void main(String[] args) throws InterruptedException {
        List<byte[]> bytes = new ArrayList<>();
        while(true){
            bytes.add(new byte[10000]);
        }
    }

}
