package com.yupi.ojcodesandbox.unsafe;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/9 14:52
 * @Package com.yupi.ojcodesandbox.unsafe
 * @Version 1.0
 * @Since 1.0
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 植入木马，运行程序
 */
public class RunFileError {

    public static void main(String[] args) throws InterruptedException, IOException {
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + File.separator + "src/main/resources/木马程序.bat";
        Process process = Runtime.getRuntime().exec(filePath);
        process.waitFor();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String compileOutputLine;
        while((compileOutputLine = bufferedReader.readLine()) != null){
            System.out.println(compileOutputLine);
        }

        System.out.println("执行异常程序成功");
    }

}
