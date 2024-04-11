package com.yupi.ojcodesandbox.unsafe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/9 14:30
 * @Package com.yupi.ojcodesandbox.unsafe
 * @Version 1.0
 * @Since 1.0
 */

/**
 * 读文件，获取程序信息
 */
public class ReadFileError {

    public static void main(String[] args) throws IOException {
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + File.separator  + "src/main/resources/application.yml";
        List<String> allLines = Files.readAllLines(Paths.get(filePath));
        System.out.println(String.join("\n",allLines));
    }

}
