package com.jun.utils.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AppConfig {

    private static AppConfig config;//单例
    private Application app;//同项目注册app同类型，都为android.app.Application

    private AppConfig(){

    }

    /**
     * 单例  懒汉式
     * @return
     */
    public static AppConfig getInstance(){
        if(config == null){
            synchronized (AppConfig.class){
                if(config == null){
                    config = new AppConfig();
                }
            }
        }
        return config;
    }

    public void init(Application app){
        this.app = app;
        initActivityLifecycleManage();//activity 生命周期监听

    }

    /**
     * Activity生命周期监听
     */
    private void initActivityLifecycleManage() {
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                if(activity instanceof AppCompatActivity){//将activity传入manager管理
                    AppCompatActivity appCompatActivity = (AppCompatActivity)activity;
                    ActivityManager.getInstance().setCurrentActivity(appCompatActivity);
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

}
