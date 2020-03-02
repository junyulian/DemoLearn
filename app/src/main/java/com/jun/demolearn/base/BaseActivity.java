package com.jun.demolearn.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 跳转到指定的activity
     * @param clazz
     */
    public void moveTo(Class<?> clazz){
        moveTo(clazz,false);
    }

    /**
     * 跳转到指定的activity
     * @param clazz activity类名
     * @param isFinish 是否销毁当前activity
     */
    public void moveTo(Class<?> clazz, boolean isFinish){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        if(isFinish){
            this.finish();
        }
    }
}
