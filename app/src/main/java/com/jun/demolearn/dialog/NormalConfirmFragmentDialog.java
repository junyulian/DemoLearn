package com.jun.demolearn.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jun.demolearn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalConfirmFragmentDialog extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_msg)
    TextView tv_msg;

    private static final String TITLE = "title";
    private static final String MESSAGE = "message";


    private NormalConfirmFragmentDialog(){

    }

    public static NormalConfirmFragmentDialog newInstance(){
        NormalConfirmFragmentDialog dialog = new NormalConfirmFragmentDialog();
        return  dialog;
    }

    /**
     * 设置弹框内容
     * @param msg 提示内容
     * @return
     */
    public static NormalConfirmFragmentDialog newInstance(String msg){
        NormalConfirmFragmentDialog dialog = newInstance("",msg);
        return  dialog;
    }

    /**
     * 设置弹框标题和内容
     * @param title 提示标题
     * @param msg 提示内容
     * @return
     */
    public static NormalConfirmFragmentDialog newInstance(String title,String msg){
        NormalConfirmFragmentDialog dialog = new NormalConfirmFragmentDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putString(MESSAGE,msg);
        dialog.setArguments(bundle);//给弹框设置值
        return  dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_normal_confirm,null);
        ButterKnife.bind(this,view);

        Bundle bundle = getArguments();//接收给弹框设置的值
        if(bundle != null){
            String title = bundle.getString(TITLE);
            String msg = bundle.getString(MESSAGE);
            if(!TextUtils.isEmpty(title)){
                tv_title.setText(title);
            }
            if(!TextUtils.isEmpty(msg)){
                tv_msg.setText(msg);
            }
        }

        return view;
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


    private OnOKClickListener oKListener;
    public interface OnOKClickListener{
        void onOkClick();
    }

    /**
     * 确定按钮点击监听
     * @param oKListener
     * @return
     */
    public NormalConfirmFragmentDialog setOKClickListener(OnOKClickListener oKListener){
        this.oKListener = oKListener;
        return this;
    }


    private OnCancelClickListener cancelListener;
    public interface OnCancelClickListener{
        void onCancelClick();
    }
    /**
     * 取消按钮点击监听
     * @param cancelListener
     * @return
     */
    public NormalConfirmFragmentDialog setCancelClickListener(OnCancelClickListener cancelListener){
        this.cancelListener = cancelListener;
        return this;
    }
}
