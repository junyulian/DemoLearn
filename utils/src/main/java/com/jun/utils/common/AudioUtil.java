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


    /**
     * 监听系统模式播放音频
     * 不循环
     * @param context
     * @param rawId
     * @return
     */
    public static Ringtone playAudio(Context context,int rawId){
        return playAudio(context,rawId,false);
    }

    /**
     * 监听系统模式播放音频
     * 静音和振动模式不播放，Ringtone为null
     * @param context
     * @param rawId  音频文件id
     * @param isLoop 循环播放
     * @return
     */
    public static Ringtone playAudio(Context context,int rawId,boolean isLoop){
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        final int ringerMode = audioManager.getRingerMode();
        switch (ringerMode){
            case AudioManager.RINGER_MODE_NORMAL:
                return playSound(context,rawId,isLoop);
            case AudioManager.RINGER_MODE_SILENT:
            case AudioManager.RINGER_MODE_VIBRATE:
                return null;

        }
        return null;
    }


    /**
     * 播放音频 不循环
     * @param context
     * @param rawId
     * @return
     */
    public static Ringtone playSound(Context context,int rawId){
        return playSound(context,rawId,false);
    }

    /**
     * 停止播放  非循环播放的音频
     * @param ringtone
     */
    public static void stopSound(Ringtone ringtone){
        stopSound(ringtone,false);
    }

    /**
     * 播放音频
     * @param context
     * @param rawId  文件资源id
     * @param isLoop 是否循环播放
     * @return
     */
    public static Ringtone playSound(Context context,int rawId, boolean isLoop){
        String uri = "android.resource://" + context.getPackageName() + "/" +rawId;
        Uri no = Uri.parse(uri);
        Ringtone mRingtone = RingtoneManager.getRingtone(context.getApplicationContext(), no);
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
        return mRingtone;
    }

    /**
     * 停止播放
     * @param ringtone 播放器
     * @param isLoop  是否循环
     */
    public static void stopSound(Ringtone ringtone,boolean isLoop){
        if(ringtone != null && ringtone.isPlaying()){
            if(isLoop){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ringtone.setLooping(false);//28 及以上直接循环
                } else{
                    Class<Ringtone> clazz = Ringtone.class;
                    try {
                        Field audio = clazz.getDeclaredField("mLocalPlayer");
                        audio.setAccessible(true);
                        MediaPlayer target = (MediaPlayer) audio.get(ringtone);
                        target.setLooping(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            ringtone.stop();
        }
    }

}
