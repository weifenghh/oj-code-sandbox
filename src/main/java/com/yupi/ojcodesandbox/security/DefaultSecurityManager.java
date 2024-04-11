package com.yupi.ojcodesandbox.security;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/10 14:21
 * @Package com.yupi.ojcodesandbox.security
 * @Version 1.0
 * @Since 1.0
 */

import java.io.FileDescriptor;
import java.security.Permission;

/**
 *
 */
public class DefaultSecurityManager extends SecurityManager{
    @Override
    public void checkExec(String cmd) {
        super.checkExec(cmd);
    }

    @Override
    public void checkRead(FileDescriptor fd) {
        super.checkRead(fd);
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        super.checkWrite(fd);
    }

    @Override
    public void checkDelete(String file) {
        super.checkDelete(file);
    }

    @Override
    public void checkConnect(String host, int port) {
        super.checkConnect(host, port);
    }

    @Override
    public void checkPermission(Permission perm) {
        System.out.println("默认不做任何设置");
        super.checkPermission(perm);
    }
}
