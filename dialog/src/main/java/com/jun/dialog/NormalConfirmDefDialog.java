package com.jun.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  普通确认对话框
 *  完全自定义对话框，
 *  内部创建dialog对象，setContentView实现
 */
public class NormalConfirmDefDialog {

    private Context context;
    private Dialog dialog;

    @BindView(R2.id.tv_title)
    TextView tv_title;

    @BindView(R2.id.tv_msg)
    TextView tv_msg;

    public NormalConfirmDefDialog(Context context){
        this.context = context;
        dialog = new Dialog(context);
    }

    public NormalConfirmDefDialog builder(){
        initWindow();
        initView();
        return this;
    }

    /**
     * 弹框标题
     * @param title
     * @return
     */
    public NormalConfirmDefDialog setTitle(String title){
        if(tv_title != null && !TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
        return this;
    }

    /**
     * 弹框内容
     * @param msg
     * @return
     */
    public NormalConfirmDefDialog setMsg(String msg){
        if(tv_msg != null && !TextUtils.isEmpty(msg)){
            tv_msg.setText(msg);
        }
        return this;
    }

    /**
     * 弹框 展示
     */
    public void show(){
        if(!dialog.isShowing()){
            dialog.show();
        }
    }

    /**
     * 弹框 消失
     */
    public void dismiss(){
        dialog.dismiss();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_confirm,null);
        ButterKnife.bind(this,view);
        dialog.setContentView(view);
    }

    /**
     * 窗口设置
     * 弹框位置 宽度  背景
     */
    private void initWindow(){
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕尺寸
        WindowManager.LayoutParams lp = window.getAttributes();
        window.getDecorView().setPadding(0,0,0,0);//配合宽度的设置
        lp.width = (int) (d.widthPixels * 0.8); //宽度为屏幕80% 需配合padding设置
        lp.gravity = Gravity.CENTER;  //中央居中
        window.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(false);
    }


    /**
     * 取消和确定按钮点击事件
     * @param view
     */
    @OnClick({R2.id.btn_cancel,R2.id.btn_confirm})
    public void click(View view){
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            if (cancelListener != null) {
                cancelListener.onCancelClick();
            }
        } else if (id == R.id.btn_confirm) {
            if (oKListener != null) {
                oKListener.onOkClick();
            }
        }
    }


    private NormalConfirmDefDialog.OnOKClickListener oKListener;
    public interface OnOKClickListener{
        void onOkClick();
    }

    /**
     * 确定按钮点击监听
     * @param oKListener
     * @return
     */
    public NormalConfirmDefDialog setOnOKClickListener(NormalConfirmDefDialog.OnOKClickListener oKListener){
        this.oKListener = oKListener;
        return this;
    }


    private NormalConfirmDefDialog.OnCancelClickListener cancelListener;
    public interface OnCancelClickListener{
        void onCancelClick();
    }
    /**
     * 取消按钮点击监听
     * @param cancelListener
     * @return
     */
    public NormalConfirmDefDialog setOnCancelClickListener(NormalConfirmDefDialog.OnCancelClickListener cancelListener){
        this.cancelListener = cancelListener;
        return this;
    }


}
