package com.jun.demolearn.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.jun.demolearn.R;
import com.jun.demolearn.base.BaseActivity;
import com.jun.utils.common.AudioUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_start,R.id.btn_stop,R.id.btn_start_loop,R.id.btn_stop_loop})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_start:
                AudioUtil.getInstance().playSound(this,R.raw.sarilang);
                break;
            case R.id.btn_stop:
                AudioUtil.getInstance().stopSound();
                break;
            case R.id.btn_start_loop:
                AudioUtil.getInstance().playSound(this,R.raw.long_alarm,true);
                break;
            case R.id.btn_stop_loop:
                AudioUtil.getInstance().stopSound(true);
                break;
        }
    }
}
