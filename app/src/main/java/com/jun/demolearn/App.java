package com.jun.demolearn;

import android.app.Application;

import com.jun.utils.common.AppConfig;



public class App extends Application {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AppConfig.getInstance().init(app);//初始化app的全局配置

    }
}
