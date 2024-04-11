
/**
 * @Author 玉米排骨汤
 * @Date 2024/4/10 14:21
 * @Package com.yupi.ojcodesandbox.security
 * @Version 1.0
 * @Since 1.0
 */

/**
 *
 */
public class MySecurityManager extends SecurityManager{
    //检测程序是否可执行文件
    @Override
    public void checkExec(String cmd) {
        throw new SecurityException("checkExec 权限异常：" + cmd);
    }
    //检测程序是否允许读文件
    @Override
    public void checkRead(String file) {
//        System.out.println(file);
//        if(file.contains("D:\\yupi\\OJ\\code\\oj-code-sandbox"));
//        throw new SecurityException("checkRead权限异常：" + file);
    }
    //检测程序是否允许写文件
    @Override
    public void checkWrite(String file) {
//        throw new SecurityException("checkWrite权限异常：" + file);
    }
    //检测程序是否允许删除文件
    @Override
    public void checkDelete(String file) {
//        throw new SecurityException("checkDelete 权限异常：" + file);
    }
    //检测程序是否允许连接网路
    @Override
    public void checkConnect(String host, int port) {
//        throw new SecurityException("checkConnect 权限异常：" + host + ":" + port);
    }

}
