package com.yupi.ojcodesandbox;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.WordTree;
import cn.hutool.extra.tokenizer.Word;
import com.yupi.ojcodesandbox.model.ExecuteCodeRequest;
import com.yupi.ojcodesandbox.model.ExecuteCodeResponse;
import com.yupi.ojcodesandbox.model.ExecuteMessage;
import com.yupi.ojcodesandbox.model.JudgeInfo;
import com.yupi.ojcodesandbox.security.DefaultSecurityManager;
import com.yupi.ojcodesandbox.utils.ProcessUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/7 19:30
 * @Package com.yupi.ojcodesandbox
 * @Version 1.0
 * @Since 1.0
 */
public class JavaNativeCodeSandbox implements CodeSandbox{
    /**
     * 1. 把用户的代码保存为文件
     * 2. 编译代码，得到class文件
     * 3. 执行代码，得到结果
     * 4. 收集整理 输出结果
     * 5.文件清理
     * 6. 错误处理，提升程序健壮性
     */

    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";
    private static final String MY_SECURITY_MANAGER = "MySecurityManager";
    private static final long TIME_OUT = 5000L;
    private static final String SECURITY_MANAGER_PATH = "D:\\yupi\\OJ\\code\\oj-code-sandbox\\src\\main\\resources\\security";
    private static final List<String> blackList = Arrays.asList("Files","exec");
    private static final WordTree wordTree;
    //初始化字典树，使用更少的空间存储更多的敏感词汇，实现更高效的敏感词查找
    static{
        //检查是否包含黑名单中的敏感词
        //将黑名单挂载到wordTree下，便于后续的匹配
        wordTree = new WordTree();
        wordTree.addWords(blackList);
    }

    public static void main(String[] args) {
        JavaNativeCodeSandbox javaNativeCodeSandbox = new JavaNativeCodeSandbox();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Arrays.asList("1 2","1 3"));
//        String code = ResourceUtil.readStr("testCode/simpleComputeArgs/Main.java", StandardCharsets.UTF_8);
//        String code = ResourceUtil.readStr("testCode/unsafe/SleepError.java", StandardCharsets.UTF_8);
        String code = ResourceUtil.readStr("testCode/unsafe/RunFileError.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
        System.out.println("hello");
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        //用户发送输入用例，代码，语言，沙箱进行判断
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        FoundWord foundWord = wordTree.matchWord(code);
        if(foundWord != null){
            System.out.println(foundWord.getFoundWord());
            return null;
        }

        StopWatch stopWatch = new StopWatch();
        //1.把用户的代码保存为文件按
        //1.1获取系统当前用户所在的工作目录
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        //1.2 判断全局代码目录是否存在，没有则新建
        if(!FileUtil.exist(globalCodePathName)){
            FileUtil.mkdir(globalCodePathName);
        }
        //1.3 把用户的代码隔离存放
        //存放代码的父目录
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        //存放代码的实际文件
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        File userCodeFile = FileUtil.writeString(code,userCodePath, StandardCharsets.UTF_8);

        //2. 编译代码 得到class文件
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");
            System.out.println(executeMessage);
        } catch (Exception e) {
            return getErrorResponse(e);
        }

        //3. 执行代码，获取结果
        ArrayList<ExecuteMessage> executeMessageList = new ArrayList<>();
        for(String inputArgs : inputList){
            //-Xmx256m:jvm参数  用来分配最大堆内存
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s;%s -Djava.security.manager=%s Main %s",userCodeParentPath,SECURITY_MANAGER_PATH,MY_SECURITY_MANAGER,inputArgs);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                //超时控制，另起一个监控线程，当监控线程醒来，发现程序还再执行（超时），直接杀死进程
                new Thread(() -> {
                    try {
                        Thread.sleep(TIME_OUT);
                        runProcess.destroy();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                executeMessageList.add(executeMessage);
//                ExecuteMessage executeMessage = ProcessUtils.runInteractProcessAndGetMessage(runProcess, "运行", inputArgs);
                System.out.println(executeMessage);
            } catch (Exception e) {
                return getErrorResponse(e);
            }
        }

        //4. 收集整理输出结果
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        ArrayList<String> outputList = new ArrayList<>();
        long maxTime = 0;
        for (ExecuteMessage executeMessage : executeMessageList){
            String errorMessage = executeMessage.getErrorMessage();
            //执行中存在错误
            if(StrUtil.isNotBlank(errorMessage)){
                executeCodeResponse.setMessage(errorMessage);
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            if(time != null){
                maxTime = Math.max(maxTime,time);
            }
        }
        //全部正常运行完成
        if(outputList.size() == executeMessageList.size()){
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTimeLimit(maxTime);
        //要借助第三方库来获取内存占用
//        judgeInfo.setMemoryLimit();

        executeCodeResponse.setJudgeInfo(judgeInfo);

        //5. 文件清理
        if(userCodeFile.getParentFile() != null){
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("删除" + (del ? "成功" : "失败"));
        }


        //6.错误处理，提升程序健壮性


        return executeCodeResponse;
    }


    private ExecuteCodeResponse getErrorResponse(Throwable e){
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        // 1:程序执行真诚   2:代码沙箱内部错误   3:代码执行错误
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }


}
