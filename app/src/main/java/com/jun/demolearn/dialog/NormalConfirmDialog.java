package com.jun.demolearn.dialog;

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
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.jun.demolearn.R;

/**
 *  普通确认对话框
 *  继承dialog,内部类构造方法创建dialog对象，setContentView实现
 */
public class NormalConfirmDialog extends Dialog {

    public NormalConfirmDialog(@NonNull Context context) {
        super(context);
    }

    public NormalConfirmDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NormalConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder{
        private Context context;
        private NormalConfirmDialog dialog;

        TextView tv_title;
        TextView tv_msg;
        Button btn_cancel;
        Button btn_confirm;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setTitle(String title){
            if(tv_title != null && !TextUtils.isEmpty(title)){
                tv_title.setText(title);
            }
            return this;
        }

        public Builder setMessage(String msg){
            if(tv_msg != null && !TextUtils.isEmpty(msg)){
                tv_msg.setText(msg);
            }
            return this;
        }

        public NormalConfirmDialog create(){
            dialog = new NormalConfirmDialog(context);
            initView();
            initWindow();
            initListener();
            return dialog;
        }

        private void initView(){
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_confirm,null);
            dialog.setContentView(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_msg = view.findViewById(R.id.tv_msg);
            btn_cancel = view.findViewById(R.id.btn_cancel);
            btn_confirm = view.findViewById(R.id.btn_confirm);
        }


        private NormalConfirmDialog.OnOKClickListener oKListener;
        /**
         * 确定按钮点击监听
         * @param oKListener
         * @return
         */
        public Builder setOnOKClickListener(NormalConfirmDialog.OnOKClickListener oKListener){
            this.oKListener = oKListener;
            return this;
        }


        private NormalConfirmDialog.OnCancelClickListener cancelListener;
        /**
         * 取消按钮点击监听
         * @param cancelListener
         * @return
         */
        public Builder setOnCancelClickListener(NormalConfirmDialog.OnCancelClickListener cancelListener){
            this.cancelListener = cancelListener;
            return this;
        }


        /**
         * 取消和确定按钮点击事件
         * @param
         */
        private void initListener(){
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cancelListener != null){
                        cancelListener.onCancelClick();
                    }
                }
            });
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(oKListener != null){
                        oKListener.onOkClick();
                    }
                }
            });
        }


        private void initWindow(){
            Window window = dialog.getWindow();
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

    }


    public interface OnOKClickListener{
        void onOkClick();
    }
    public interface OnCancelClickListener{
        void onCancelClick();
    }
}
