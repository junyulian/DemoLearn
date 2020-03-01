package com.jun.demolearn.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jun.demolearn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalConfirmActivityDialog extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_msg)
    TextView tv_msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_normal_confirm);
        //隐藏标题栏
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.hide();
        }
        ButterKnife.bind(this);
        initWindow();
    }

    private void initWindow() {
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics d = getResources().getDisplayMetrics();//获取屏幕尺寸
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (d.widthPixels * 0.8); //宽度为屏幕80%
        lp.gravity = Gravity.CENTER;  //中央居中
        window.setAttributes(lp);
    }



    /**
     * 取消和确定按钮点击事件
     * @param view
     */
    @OnClick({R.id.btn_cancel,R.id.btn_confirm})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_cancel:
            case R.id.btn_confirm:
                finish();
                break;
            default:
                break;
        }
    }


}
