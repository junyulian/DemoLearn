package com.jun.demolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jun.demolearn.dialog.NormalConfirmFragmentDialog;

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


    @OnClick({R.id.btn_fragment_dialog})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_fragment_dialog:
                showNormalConfirmFragmentDialog();
                break;
            default:
                break;
        }
    }

    private void showNormalConfirmFragmentDialog(){

        NormalConfirmFragmentDialog dialog = new NormalConfirmFragmentDialog();
        dialog.show(getSupportFragmentManager(),"first");

    }


}
