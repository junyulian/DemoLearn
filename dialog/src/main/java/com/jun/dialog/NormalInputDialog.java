package com.jun.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalInputDialog extends DialogFragment {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String CONTENT = "content";
    public static final String TEXTHINT = "textHint";
    public static final String NEGATIVE = "negative";
    public static final String POSITIVE = "positive";
    public static final String OUTSIDEENABLE = "outClose";

    @BindView(R2.id.tv_title)
    TextView tv_title;

    @BindView(R2.id.tv_des)
    TextView tv_des;

    @BindView(R2.id.et_content)
    EditText et_content;

    @BindView(R2.id.btn_cancel)
    Button btn_cancel;

    @BindView(R2.id.btn_confirm)
    Button btn_confirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_normal_input,null);
        ButterKnife.bind(this,view);

        initData();

        return view;
    }
    //处理传入弹框的参数
    private void initData(){
        Bundle bundle = getArguments();//接收给弹框设置的值
        if(bundle != null){
            String title = bundle.getString(TITLE);
            if(!TextUtils.isEmpty(title)){
                tv_title.setText(title);
            }

            String des = bundle.getString(DESCRIPTION);
            if(!TextUtils.isEmpty(des)){//描述如果没有就不显示  有才显示
                tv_des.setVisibility(View.VISIBLE);
                tv_des.setText(des);
            }

            String hint = bundle.getString(TEXTHINT);
            if(!TextUtils.isEmpty(hint)){
                et_content.setHint(hint);
            }

            String content = bundle.getString(CONTENT);
            if(!TextUtils.isEmpty(content)){
                et_content.setText(content);
            }

            String cancel = bundle.getString(NEGATIVE);
            if(!TextUtils.isEmpty(cancel)){
                btn_cancel.setText(cancel);
            }

            String confirm = bundle.getString(POSITIVE);
            if(!TextUtils.isEmpty(confirm)){
                btn_confirm.setText(confirm);
            }

            Boolean outCloseable = bundle.getBoolean(OUTSIDEENABLE);
            if(outCloseable != null){//如果不设置，默认不能外部关闭
                setCancelable(outCloseable);
            }
        }
    }

    public static class Builder{
        private NormalInputDialog dialog;
        private String title;
        private String des;
        private String content;
        private String hint;
        private String cancelText;
        private String okText;
        private boolean outCloseable;//Boolean 无初始化是null  boolean无初始化才是false

        public Builder(){}

        /**
         * 设置输入框 提示文字
         * @param hint
         * @return
         */
        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        /**
         * 设置取消按钮 文字
         * @param cancelText
         * @return
         */
        public Builder setCancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        /**
         * 设置确认按钮 文字
         * @param okText
         * @return
         */
        public Builder setOkText(String okText) {
            this.okText = okText;
            return this;
        }

        /**
         * 设置弹框标题
         * @param title
         * @return
         */
        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        /**
         * 设置输入框前面的描述  如果不设置，隐藏
         * @param des
         * @return
         */
        public Builder setDes(String des) {
            this.des = des;
            return this;
        }

        /**
         * 设置输入框内容
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * 设置外部是否可关闭  默认不可关闭
         * @param outCloseable
         * @return
         */
        public Builder setOutClose(Boolean outCloseable){
            this.outCloseable = outCloseable;
            return this;
        }

        public NormalInputDialog creat(){
            dialog = new NormalInputDialog();
            Bundle bundle = new Bundle();
            bundle.putString(TITLE,title);
            bundle.putString(DESCRIPTION,des);
            bundle.putString(CONTENT,content);
            bundle.putString(TEXTHINT,hint);
            bundle.putString(NEGATIVE,cancelText);
            bundle.putString(POSITIVE,okText);
            bundle.putBoolean(OUTSIDEENABLE,outCloseable);
            dialog.setArguments(bundle);//给弹框设置值
            return dialog;
        }
    }


    /**
     * 取消  按钮监听接口
     */
    public interface OnNegativeClickListener{
        void click();
    }

    /**
     * 确定 按钮监听接口
     */
    public interface OnPositiveClickListener{
        void click(String content);
    }
    private OnNegativeClickListener negativeClickListener;

    /**
     * 设置取消按钮监听
     * @param negativeClickListener
     * @return
     */
    public NormalInputDialog setOnNegativeClickListener(OnNegativeClickListener negativeClickListener){
        this.negativeClickListener = negativeClickListener;
        return this;
    }
    private OnPositiveClickListener positiveClickListener;
    /**
     * 设置确定按钮监听
     * @param positiveClickListener
     * @return
     */
    public NormalInputDialog setOnPositiveClickListener(OnPositiveClickListener positiveClickListener){
        this.positiveClickListener = positiveClickListener;
        return this;
    }


    @OnClick({R2.id.btn_confirm,R2.id.btn_cancel})
    public void click(View view){
        int id = view.getId();
        if (id == R.id.btn_confirm) {
            if (positiveClickListener != null) {
                String content = et_content.getText().toString().trim();
                positiveClickListener.click(content);
            }
        } else if (id == R.id.btn_cancel) {
            if (negativeClickListener != null) {
                negativeClickListener.click();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置window弹框透明，布局文件的圆角才能展示

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout((int)(dm.widthPixels*0.8),ViewGroup.LayoutParams.WRAP_CONTENT);//设置边距

        //setCancelable(false);//默认不可外部点击关闭
    }
}
