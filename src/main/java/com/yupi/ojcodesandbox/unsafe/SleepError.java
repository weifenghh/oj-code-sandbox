package com.yupi.ojcodesandbox.unsafe;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/9 14:03
 * @Package com.yupi.ojcodesandbox
 * @Version 1.0
 * @Since 1.0
 */

/**
 * 无线睡眠（阻塞程序执行）
 */
public class SleepError {

    public static void main(String[] args) throws InterruptedException {
        long ONE_HOUR = 60 * 60 * 1000L;
        Thread.sleep(ONE_HOUR);
        System.out.println("睡完了");
    }

}
