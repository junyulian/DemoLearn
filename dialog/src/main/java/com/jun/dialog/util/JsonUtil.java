package com.jun.dialog.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.jun.dialog.bean.PushInfoBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * json对象和字符串转换工具
 */
public class JsonUtil {

    /**
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    public static String toJsonStr(Object obj){
        String res = "";
        try {
            Gson gson = new Gson();
            res = gson.toJson(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * json字符串转换成对象
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJsonStr(String str, Type type){
        T  t = null;
        try {
            Gson gson = new Gson();
            t =  gson.fromJson(str,type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    /**
     * json字符串转换成对象
     * @param jsonStr
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJsonStr(String jsonStr,Class<T> type){
        T  t = null;
        try {
            Gson gson = new Gson();
            t =  gson.fromJson(jsonStr,type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 得到json文件内容
     * @param context
     * @param fileName
     * @return
     */
    public static String getAsstesJson(Context context,String fileName){
        StringBuilder strBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                strBuilder.append(line.trim());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return strBuilder.toString();
    }


    public static void main(String[] args){
        String alarmInfo =  "{\"gpsStatus\":false,\"messageTypeId\":1,\"deviceAlarmId\":675899349,\"imei\":\"866551032760683\",\"deviceName\":\"760683-有线 南汇工程 全顺\",\"alarmTypeBean\":{\"alarmTypeId\":12,\"name\":\"超速报警\"},\"alarmTime\":1582793180000,\"isRead\":false,\"reminderTime\":60}";

        PushInfoBean bean = fromJsonStr(alarmInfo,PushInfoBean.class);
        System.out.println("------bean:"+bean.getDeviceName());

        String res = toJsonStr(bean);
        System.out.println("------beanStr:"+res);
    }



    /*单例
    //私有化构造函数
    private JsonUtil(){}
    //定义一个静态枚举类
    private enum SingleEnum{
        INSTANCE;//创建一个枚举对象，该对象天生为单例
        private JsonUtil util;
        //私有化枚举的构造函数
        private SingleEnum(){
            util = new JsonUtil();
        }
        public JsonUtil getInstance(){
            return util;
        }
    }
    //对外暴露一个获取JsonUtil对象的静态方法
    public static JsonUtil getInstance(){
        return SingleEnum.INSTANCE.getInstance();
    }
    */
}
