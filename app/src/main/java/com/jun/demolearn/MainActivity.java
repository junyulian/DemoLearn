package com.jun.demolearn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jun.demolearn.activity.DialogEnterActivity;
import com.jun.demolearn.dialog.NormalConfirmActivityDialog;
import com.jun.demolearn.dialog.NormalConfirmDefDialog;
import com.jun.demolearn.dialog.NormalConfirmDialog;
import com.jun.demolearn.dialog.NormalConfirmFragmentDialog;
import com.jun.demolearn.dialog.NormalConfirmPopDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 对话框
 */
public class MainActivity extends AppCompatActivity {

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
                Intent intent = new Intent(this, DialogEnterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
