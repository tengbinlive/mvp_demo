/*
 * Copyright (c) 2012, Gewara Corporation, All Rights Reserved
 */
package com.boqii.plant.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

/**
 * 处理进程类工具.
 */
public class ProcessUtil {

    private ProcessUtil() {
    }

    /**
     * @return true(是主进程) false(不是主进程)
     * @description 判断是否是主进程
     * @author Andy.fang
     * @createDate 2014-5-21
     */
    public static boolean isCurMainProcess(Context context) {
        boolean isMainProcess = false;
        int pid = Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid && !appProcess.processName.contains(":")) {
                isMainProcess = true;
                break;
            }
        }
        return isMainProcess;
    }

    /**
     * @return 所有进程名称的数组
     * @description 返回当前进程的名称（一个APP有多多个进程情况）
     * @author Andy.fang
     * @createDate 2014-5-21
     */
    public static String[] getCurProcessName(Context context) {
        String[] processName = new String[5];
        int pid = Process.myPid(), i = 0;
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName[i] = appProcess.processName;
                i++;
            }
        }
        return processName;
    }
}
