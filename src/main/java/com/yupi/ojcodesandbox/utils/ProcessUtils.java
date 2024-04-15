package com.yupi.ojcodesandbox.utils;

import cn.hutool.core.util.StrUtil;
import com.yupi.ojcodesandbox.model.ExecuteMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/8 14:38
 * @Package com.yupi.ojcodesandbox.utils
 * @Version 1.0
 * @Since 1.0
 */

/**
 * 打印进程执行结果信息工具类
 */
public class ProcessUtils {

    public static ExecuteMessage runProcessAndGetMessage(Process runProcess,String opName){
        ExecuteMessage executeMessage = new ExecuteMessage();
        try{
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            //等待程序执行，获取错误码
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            //正常退出
            if(exitValue == 0){
                System.out.println(opName + "成功");
                //分批获取进程的输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                //逐行读取
                String compileOutputLine;
                while((compileOutputLine = bufferedReader.readLine()) != null){
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList,"\n"));
            }else{
                //异常退出
                System.out.println(opName + "失败，错误码：" + exitValue);
                //分批获取进程的输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                //逐行读取
                String compileOutputLine;
                while((compileOutputLine = bufferedReader.readLine()) != null){
                    outputStrList.add(compileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(outputStrList,"\n"));
                //分批获取进程的输出
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                List<String> errorOutputStrList = new ArrayList<>();
                //逐行读取
                String errorCompileOutputLine;
                while((errorCompileOutputLine = bufferedReader.readLine()) != null){
                    errorOutputStrList.add(errorCompileOutputLine);
                }
                executeMessage.setMessage(StringUtils.join(errorOutputStrList,"\n"));
            }
            stopWatch.stop();
            long time = stopWatch.getLastTaskTimeMillis();
            executeMessage.setTime(time);
        }catch(Exception e){
            e.printStackTrace();
        }


        return executeMessage;
    }

    /**
     * 交互式进程获取信息
     * @param runProcess
     * @param opName
     * @return
     */
    public static ExecuteMessage runInteractProcessAndGetMessage(Process runProcess, String opName,String args){

        ExecuteMessage executeMessage = new ExecuteMessage();
        try{
            InputStream inputStream = runProcess.getInputStream();
            OutputStream outputStream = runProcess.getOutputStream();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] s = args.split(" ");
            String join = StrUtil.join("\n",s) + "\n";
            outputStreamWriter.write(join);
            outputStreamWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder compileStringBuilder = new StringBuilder();
            String compileOutputLine;
            while((compileOutputLine = bufferedReader.readLine()) != null){
                compileStringBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileStringBuilder.toString());
            inputStream.close();
            outputStream.close();
            outputStreamWriter.close();
            runProcess.destroy();
        }catch(Exception e){
            e.printStackTrace();
        }
        return executeMessage;
    }
}
