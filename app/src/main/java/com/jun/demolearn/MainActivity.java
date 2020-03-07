package com.jun.demolearn;

import androidx.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.jun.demolearn.activity.DialogEnterActivity;
import com.jun.demolearn.base.BaseActivity;


import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 对话框
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_dialog})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_dialog:
                moveTo(DialogEnterActivity.class);
                break;
            default:
                break;
        }
    }
}
