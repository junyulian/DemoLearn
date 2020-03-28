package com.jun.demolearn.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.jun.demolearn.R;
import com.jun.demolearn.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AndroidGroupViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_coordinator})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_coordinator:
                moveTo(CoordinatorActivity.class);
                break;
        }
    }
}
