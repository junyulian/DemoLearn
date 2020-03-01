package com.jun.demolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_fragment_dialog,R.id.btn_normal_dialog,
            R.id.btn_normal_pop_dialog,R.id.btn_normal_define_dialog,
            R.id.btn_normal_activity_dialog})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_fragment_dialog:
                showNormalConfirmFragmentDialog();
                break;
            case R.id.btn_normal_dialog:
                showNormalConfirmDialog();
                break;
            case R.id.btn_normal_pop_dialog:
                showNormalPopDialog();
                break;
            case R.id.btn_normal_define_dialog:
                showDefineDialog();
                break;
            case R.id.btn_normal_activity_dialog:
                showActivityDialog();
                break;
            default:
                break;
        }
    }

    private void showActivityDialog() {
        Intent intent = new Intent(this,NormalConfirmActivityDialog.class);
        startActivity(intent);
    }

    private void showDefineDialog(){
        NormalConfirmDefDialog dialog = new NormalConfirmDefDialog(MainActivity.this);
        dialog.builder().setTitle("tips").setMsg("this is a story").setOnOKClickListener(new NormalConfirmDefDialog.OnOKClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
            }
        }).show();
    }

    private void showNormalPopDialog(){

        View parentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        NormalConfirmPopDialog dialog = new NormalConfirmPopDialog(MainActivity.this);
        dialog.setOnOKClickListener(new NormalConfirmPopDialog.OnOKClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
            }
        }).setOnCancelClickListener(new NormalConfirmPopDialog.OnCancelClickListener() {
            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setTitle("zy").setMsg("this is a msg");

        dialog.showAtCenter(parentView);
    }



    private void showNormalConfirmDialog(){
        NormalConfirmDialog dialog = new NormalConfirmDialog(MainActivity.this,"hello","new world want to see you again");
        dialog.setOnCancelClickListener(new NormalConfirmDialog.OnCancelClickListener() {
            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setOnOKClickListener(new NormalConfirmDialog.OnOKClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
            }
        }).setCancelable(false);
        dialog.show();
    }

    private void showNormalConfirmFragmentDialog(){

        NormalConfirmFragmentDialog dialog = NormalConfirmFragmentDialog.newInstance();
        dialog.setOKClickListener(new NormalConfirmFragmentDialog.OnOKClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
            }
        }).setCancelClickListener(new NormalConfirmFragmentDialog.OnCancelClickListener(){

            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setCancelable(true);

        dialog.show(getSupportFragmentManager(),"first");

    }


}
