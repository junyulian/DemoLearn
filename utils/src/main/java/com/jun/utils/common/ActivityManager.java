package com.jun.utils.common;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Application注册activity的生命周期监听回调方法registerActivityLifecycleCallbacks
 * 此方法将监听到的activiy传入ActivityManager进行管理
 */
public class ActivityManager {

    private ActivityManager(){}
    private static class ActivityManagerInstance{
        private static final ActivityManager INSTANCE = new ActivityManager();
    }
    /**
     * 静态内部类单例
     * ActivityManager类被装载时并不会立即实例化
     * 只有调用getInstance方法，才会装载SingletonInstance类，从而完成对象的实例化
     * 因为类的静态属性只会在第一次加载类的时候初始化，也就保证了ActivityManagerInstance中的对象只会被实例化一次
     * 并且这个过程也是线程安全的。
     * @return
     */
    public static ActivityManager getInstance(){
        return ActivityManagerInstance.INSTANCE;
    }


    private WeakReference<AppCompatActivity> sCurrentActivityWeakRef;
    /**
     * 获取当前activity
     * @return
     */
    public AppCompatActivity getCurrentActivity(){
        AppCompatActivity currentActivity = null;
        if(sCurrentActivityWeakRef != null){
           currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    /**
     * 将当前activiy 传入ActivityManager管理
     * @param activity
     */
    public void setCurrentActivity(AppCompatActivity activity){
        if(activity == null){
            return;
        }
        sCurrentActivityWeakRef = new WeakReference<AppCompatActivity>(activity);
    }

}
