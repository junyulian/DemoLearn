package com.jun.dialog.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jun.dialog.R;
import com.jun.dialog.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomBialog extends DialogFragment {
    /**
     * 静态内部类单例
     * TopDialog类被装载时并不会立即实例化
     * 只有调用getInstance方法，才会装载SingletonInstance类，从而完成对象的实例化
     * 因为类的静态属性只会在第一次加载类的时候初始化，也就保证了SingletonInstance中的对象只会被实例化一次
     * 并且这个过程也是线程安全的。
     * @return
     */
    private BottomBialog(){}
    private static class SingletonInstance{
        private static final BottomBialog INSTANCE = new BottomBialog();
    }
    public static BottomBialog getInstance(){
        return BottomBialog.SingletonInstance.INSTANCE;
    }

    private final static String CONTENT = "content";

    @BindView(R2.id.tv_content)
    TextView tv_content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_bottom,null);
        ButterKnife.bind(this,view);
        initData();
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();//接收给弹框设置的值
        if(bundle != null){

            String content = bundle.getString(CONTENT);
            if(!TextUtils.isEmpty(content)){
                tv_content.setText(content);
            }

        }
    }


    public static class Builder{

        private String content;

        /**
         * 设置输入框内容
         * @param content
         * @return
         */
        public BottomBialog.Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public BottomBialog create(){
            BottomBialog dialog = BottomBialog.getInstance();
            Bundle bundle = new Bundle();
            bundle.putString(CONTENT,content);
            dialog.setArguments(bundle);
            return dialog;
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.getAttributes().gravity = Gravity.BOTTOM;
        window.setWindowAnimations(R.style.dialog_bottom_anim);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        setCancelable(true);


        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置window弹框透明，布局文件的圆角才能展示
        /*
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout((int)(dm.widthPixels*0.8),ViewGroup.LayoutParams.WRAP_CONTENT);//设置边距
        */
    }


}
