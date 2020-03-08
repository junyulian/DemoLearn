package com.jun.demolearn;

import androidx.annotation.Nullable;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.jun.demolearn.activity.DialogEnterActivity;
import com.jun.demolearn.activity.MediaActivity;
import com.jun.demolearn.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;


import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 对话框
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getPermissions();
    }

    @OnClick({R.id.btn_dialog,R.id.btn_media})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_dialog:
                moveTo(DialogEnterActivity.class);
                break;
            case R.id.btn_media:
                moveTo(MediaActivity.class);
                break;
            default:
                break;
        }
    }

    private void getPermissions(){

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if(aBoolean){
                            //申请的权限全部充许

                        }else {
                            //有一个权限不充放
                        }
                    }
                });



    }
}
