package com.jun.demolearn.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jun.demolearn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalConfirmDialog extends Dialog {

    private Context context;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_msg)
    TextView tv_msg;

    private String title;
    private String msg;

    public NormalConfirmDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NormalConfirmDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected NormalConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    /**
     * 设置内容
     * @param context
     * @param msg 弹框内容
     */
    public NormalConfirmDialog(Context context, String msg){
        this(context, "", msg);
    }

    /**
     * 设置 标题和内容
     * @param context
     * @param title 弹框标题
     * @param msg 弹框内容
     */
    public NormalConfirmDialog(Context context, String title, String msg){
        super(context);
        this.title = title;
        this.msg = msg;
        init(context);
    }

    private void init(Context context){
        this.context = context;
        /*初始化在此处没问题
        initView();
        initWindow();
         */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initView initWindow初始化也可以放在此处
        initView();
        initWindow();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_confirm,null);
        setContentView(view);
        ButterKnife.bind(this,view);

        if(!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
        if(!TextUtils.isEmpty(msg)){
            tv_msg.setText(msg);
        }
    }

    private void initWindow(){
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置window弹框透明，布局文件的圆角才能展示

        //方法一  可行
        /*
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout((int)(dm.widthPixels*0.8), ViewGroup.LayoutParams.WRAP_CONTENT);//设置边距
         */
        //方法二 可行
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕尺寸
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
                if(cancelListener != null){
                    cancelListener.onCancelClick();
                }
                break;
            case R.id.btn_confirm:
                if(oKListener != null){
                    oKListener.onOkClick();
                }
                break;
            default:
                break;
        }
    }


    private NormalConfirmDialog.OnOKClickListener oKListener;
    public interface OnOKClickListener{
        void onOkClick();
    }

    /**
     * 确定按钮点击监听
     * @param oKListener
     * @return
     */
    public NormalConfirmDialog setOnOKClickListener(NormalConfirmDialog.OnOKClickListener oKListener){
        this.oKListener = oKListener;
        return this;
    }


    private NormalConfirmDialog.OnCancelClickListener cancelListener;
    public interface OnCancelClickListener{
        void onCancelClick();
    }
    /**
     * 取消按钮点击监听
     * @param cancelListener
     * @return
     */
    public NormalConfirmDialog setOnCancelClickListener(NormalConfirmDialog.OnCancelClickListener cancelListener){
        this.cancelListener = cancelListener;
        return this;
    }

}
