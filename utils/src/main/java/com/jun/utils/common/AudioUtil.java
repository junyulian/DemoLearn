package com.jun.utils.common;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * 音频播放工具
 * 新建资源目录 res/raw
 */
public class AudioUtil {

    private  Ringtone mRingtone;


    //单例
    //私有化构造函数
    private AudioUtil(){}
    //定义一个静态枚举类
    private enum SingleEnum{
        INSTANCE;//创建一个枚举对象，该对象天生为单例
        private AudioUtil util;
        //私有化枚举的构造函数
        private SingleEnum(){
            util = new AudioUtil();
        }
        public AudioUtil getInstance(){
            return util;
        }
    }
    //对外暴露一个获取AudioUtil对象的静态方法
    public static AudioUtil getInstance(){
        return SingleEnum.INSTANCE.getInstance();
    }


    /**
     * 监听系统模式播放音频
     * 不循环
     * @param context
     * @param rawId
     * @return
     */
    public  void playAudio(Context context,int rawId){
        playAudio(context,rawId,false);
    }

    /**
     * 监听系统模式播放音频
     * 静音和振动模式不播放，Ringtone为null
     * @param context
     * @param rawId  音频文件id
     * @param isLoop 循环播放
     * @return
     */
    public  void playAudio(Context context,int rawId,boolean isLoop){
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        final int ringerMode = audioManager.getRingerMode();
        switch (ringerMode){
            case AudioManager.RINGER_MODE_NORMAL:
                playSound(context,rawId,isLoop);
                break;
            case AudioManager.RINGER_MODE_SILENT:
            case AudioManager.RINGER_MODE_VIBRATE:
                break;

        }
    }


    /**
     * 播放音频 不循环
     * @param context
     * @param rawId
     * @return
     */
    public  void playSound(Context context,int rawId){
        playSound(context,rawId,false);
    }

    /**
     * 停止播放  非循环播放的音频
     */
    public  void stopSound(){
        stopSound(false);
    }

    /**
     * 播放音频
     * @param context
     * @param rawId  文件资源id
     * @param isLoop 是否循环播放
     * @return
     */
    public  void playSound(Context context,int rawId, boolean isLoop){
        String uri = "android.resource://" + context.getPackageName() + "/" +rawId;
        Uri no = Uri.parse(uri);
        if(mRingtone != null){
            stopSound(true);
        }
        mRingtone = RingtoneManager.getRingtone(context.getApplicationContext(), no);
        if(isLoop){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mRingtone.setLooping(true);//28 及以上直接循环
            } else{
                Class<Ringtone> clazz = Ringtone.class;
                try {
                    Field audio = clazz.getDeclaredField("mLocalPlayer");
                    audio.setAccessible(true);
                    MediaPlayer target = (MediaPlayer) audio.get(mRingtone);
                    target.setLooping(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        mRingtone.play();
    }

    /**
     * 停止播放
     * @param isLoop  是否循环
     */
    public  void stopSound(boolean isLoop){
        if(mRingtone != null && mRingtone.isPlaying()){
            if(isLoop){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    mRingtone.setLooping(false);//28 及以上直接循环
                } else{
                    Class<Ringtone> clazz = Ringtone.class;
                    try {
                        Field audio = clazz.getDeclaredField("mLocalPlayer");
                        audio.setAccessible(true);
                        MediaPlayer target = (MediaPlayer) audio.get(mRingtone);
                        target.setLooping(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mRingtone.stop();
        }
    }

}
