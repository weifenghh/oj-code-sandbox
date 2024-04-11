package com.yupi.ojcodesandbox.unsafe;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/9 14:39
 * @Package com.yupi.ojcodesandbox.unsafe
 * @Version 1.0
 * @Since 1.0
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * 写文件，向程序植入危险程序
 */
public class WriteFileError {

    public static void main(String[] args) throws IOException {
        String userDir = System.getProperty("user.dir");
        String filePath = userDir + File.separator + "src/main/resources/木马程序.bat";
        String errorProgram = "java -version 2>&1";
        Files.write(Paths.get(filePath), Arrays.asList(errorProgram));
        System.out.println("危险程序");
    }

}
