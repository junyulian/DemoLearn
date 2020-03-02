package com.jun.demolearn.utils;

import android.os.Handler;

import com.jun.demolearn.App;

/**
 * 线程处理工具类
 */
public class ThreadUtil {

    /**
     * 主线程id
     * @return
     */
    public static int getMainThreadId(){
        return App.getMainThreadId();
    }

    /**
     * 此handler处理需运行在主线程的任务
     * @return
     */
    public static Handler getHandler(){
        return App.getHandler();
    }

    /**
     * 是否在主线程运行
     * @return
     */
    public static boolean isRunOnUIThread(){
        int myTid = android.os.Process.myTid();
        if(myTid == getMainThreadId()){
            return true;
        }
        return false;
    }

    public static void runOnUiThread(Runnable runnable){
        if(isRunOnUIThread()){
            runnable.run();
        }else {
            getHandler().post(runnable);
        }
    }

}
