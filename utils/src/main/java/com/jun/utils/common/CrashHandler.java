package com.jun.utils.common;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";
    //崩溃日志目录
    private static final String crash_path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator + "wujun"+File.separator + "log"+File.separator + "crash";
    //程序的Context对象
    private Context mContext;
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //单例
    private CrashHandler (){}
    private enum SingleEnum{
        INSTANCE;//创建一个枚举对象，该对象天生为单例
        private CrashHandler crashHandler;
        //私有化枚举的构造函数
        private SingleEnum(){
            crashHandler = new CrashHandler();
        }
        public CrashHandler getInstance(){
            return crashHandler;
        }
    }
    //对外暴露一个获取JsonUtil对象的静态方法
    public static CrashHandler getInstance(){
        Log.e(TAG,"------456初始化日志工具");
        return SingleEnum.INSTANCE.getInstance();
    }

    public void init(Context context){
        Log.e(TAG,"------123初始化日志工具");
        //crash_path = context.getExternalFilesDir(null).getAbsolutePath()+ File.separator+"log"+File.separator+"crash";
        this.mContext = context;
        //获取系统默认的UncaughtException处理器
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        Log.e(TAG,"------初始化日志工具");
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        LogUtil.e("有崩溃信息");
        if (hasPermission()){
            LogUtil.e(TAG,"闪退信息 有权限 存储");
            String crashFileName = saveInfo2File(throwable);
            LogUtil.e("--------crashFileName:"+crashFileName);
            // 3. 缓存崩溃日志文件
            cacheCrashFile(crashFileName);
            // 4.删除旧的日志文件
            deleteOutTimeFiles();
        } else {
            Log.e(TAG,"闪退信息 fileName 没有权限");
        }
        // 系统默认处理
        mDefaultHandler.uncaughtException(thread, throwable);
    }

    /**
     * 判断是否有读写权限
     */
    private boolean hasPermission(){
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
     *
     * @param ex
     * @return
     */
    private String saveInfo2File(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        sb.append(obtainExceptionInfo(ex));
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File dir = new File(crash_path);
            // 先删除之前的异常信息
            if (dir.exists()) {
                deleteDir(dir);
            }
            // 再从新创建文件夹
            if (!dir.exists()) {
                dir.mkdirs();//多级目录  使用mkdirs方法， 如果用mkdir会创建失败，后面会报No such file or directory
            }
            try {
                fileName = dir.toString()
                        + File.separator
                        + getAssignTime("yyyy-MM-dd HH:mm") + ".txt";
                LogUtil.e("-------fileName:"+fileName);
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     *
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);
        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" + Build.PRODUCT);
        map.put("MOBLE_INFO", getMobileInfo());
        return map;
    }

    /**
     * Cell phone information
     *
     * @return
     */
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取系统未捕捉的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return true;
    }

    /**
     * 返回当前日期根据格式
     **/
    private String getAssignTime(String dateFormatStr) {
        DateFormat dataFormat = new SimpleDateFormat(dateFormatStr);
        long currentTime = System.currentTimeMillis();
        return dataFormat.format(currentTime);
    }

    /**
     * 缓存崩溃日志文件
     *
     * @param fileName
     */
    private void cacheCrashFile(String fileName) {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME", fileName).commit();
    }


    /**
     * 返回需要的文件格式 这里只返回txt格式的文件
     */
    private static FileFilter fileFilter = new FileFilter() {
        public boolean accept(File file) {
            String tmp = file.getName().toLowerCase();
            if (tmp.endsWith(".txt")) {
                return true;
            }
            return false;
        }
    };

    /**
     * 删除过期多余的文件，只保留最新的5个文件
     */
    public void deleteOutTimeFiles() {

        File cashFile = new File(crash_path);
        //通过fileFileter过滤器来获取parentFile路径下的想要类型的子文件
        File[] files = cashFile.listFiles(fileFilter);
        List<File> fileList = new ArrayList<>();
        if (files != null && files.length > 5) {
            for (File file : files) {
                fileList.add(file);
            }
            //通过重写Comparator的实现类FileComparator来实现按文件创建时间排序。
            Collections.sort(fileList, new FileComparator());
            //LogUtils.e("崩溃日志信息："+new Gson().toJson(fileList));
            //需要保存的文件過濾出來
            List<File> saveList = new ArrayList<>();
            for (int i = 0; i < fileList.size(); i++) {
                if (i<5){
                    saveList.add(fileList.get(i));
                }
            }
            fileList.removeAll(saveList);
            deleteFiles(fileList);
        }
    }

    /**
     * 时间规则 最新的文件在上面
     */
    private static class FileComparator implements Comparator<File> {
        public int compare(File file1, File file2) {
            if(file1.lastModified() < file2.lastModified()) {
                return 1;
            }else {
                return -1;
            }
        }
    }

    /**
     * 刪除文件
     * @param files
     */
    private static void deleteFiles(List<File> files){
        if (files != null && !files.isEmpty()){
            for (File file : files) {
                file.delete();
            }
        }
    }

}
