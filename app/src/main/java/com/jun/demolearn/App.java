package com.jun.demolearn;

import android.app.Application;
import android.os.Handler;

import com.jun.utils.common.AppConfig;
import com.jun.utils.common.CrashHandler;


public class App extends Application {

    private static App app;
    private static int mainThreadId;//主线程id
    private static Handler mHandler;//处理需运行在主线程任务

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mainThreadId = android.os.Process.myPid();//主线程id
        mHandler = new Handler();//运行在主线程
        AppConfig.getInstance().init(app);//初始化app的全局配置
        CrashHandler.getInstance().init(this);//崩溃日志
    }

    /**
     * 主线程id
     * @return
     */
    public static int getMainThreadId(){
        return mainThreadId;
    }

    /**
     * 此handler处理需运行在主线程的任务
     * @return
     */
    public static Handler getHandler(){
        return mHandler;
    }

}
