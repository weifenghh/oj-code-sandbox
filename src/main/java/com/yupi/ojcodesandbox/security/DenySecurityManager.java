package com.yupi.ojcodesandbox.security;

import java.security.Permission;

/**
 * @Author 玉米排骨汤
 * @Date 2024/4/10 14:36
 * @Package com.yupi.ojcodesandbox.security
 * @Version 1.0
 * @Since 1.0
 */
public class DenySecurityManager extends SecurityManager{

    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限异常：" + perm.toString());
    }
}
