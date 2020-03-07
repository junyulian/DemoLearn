package com.jun.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * PopupWindow是阻塞式对话框，只有在退出操作时候程序才会继续运行
 * PopupWindow可以根据自由确定自身位置
 * 1. 位置按照有无偏移分，可以分为偏移和无偏移两种
 * 2. 位置按照参照物的不同，可以分为相对于某个控件（Anchor锚）和相对于父控件
 * showAsDropDown(View anchor)：相对某个控件的位置（正左下方），无偏移
 * showAsDropDown(View anchor, int xoff, int yoff)：相对某个控件的位置（正下方），有偏移
 * showAtLocation(View parent, int gravity, int x, int y)：相对于父控件的位置(例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
 */
public class NormalConfirmPopDialog extends PopupWindow {

    private Context context;

    @BindView(R2.id.tv_title)
    TextView tv_title;

    @BindView(R2.id.tv_msg)
    TextView tv_msg;

    public NormalConfirmPopDialog(Context context){
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        initView();
        initWindow();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_confirm,null);
        setContentView(view);
        ButterKnife.bind(this,view);
    }

    private void initWindow(){
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置window弹框透明，布局文件的圆角才能展示
        DisplayMetrics d = context.getResources().getDisplayMetrics();//获取屏幕尺寸
        this.setWidth((int) (d.widthPixels * 0.8)); //宽度为屏幕80%
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(false);//外部点击不可关闭
        this.update();

        //处理点击外部区域可关闭问题
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!isOutsideTouchable()) {
                    View mView = getContentView();
                    if (null != mView)
                        mView.dispatchTouchEvent(motionEvent);
                }
                return isFocusable() && !isOutsideTouchable();
            }

        });

    }

    public void showAtCenter(View view){
        showAtLocation(view,Gravity.CENTER,0,0);
    }

    /**
     *  设置标题
     * @param title
     * @return
     */
    public NormalConfirmPopDialog setTitle(String title){
        tv_title.setText(title);
        return this;
    }

    /**
     * 设置内容
     * @param msg
     * @return
     */
    public NormalConfirmPopDialog setMsg(String msg){
        tv_msg.setText(msg);
        return this;
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


    private NormalConfirmPopDialog.OnOKClickListener oKListener;
    public interface OnOKClickListener{
        void onOkClick();
    }

    /**
     * 确定按钮点击监听
     * @param oKListener
     * @return
     */
    public NormalConfirmPopDialog setOnOKClickListener(NormalConfirmPopDialog.OnOKClickListener oKListener){
        this.oKListener = oKListener;
        return this;
    }


    private NormalConfirmPopDialog.OnCancelClickListener cancelListener;
    public interface OnCancelClickListener{
        void onCancelClick();
    }
    /**
     * 取消按钮点击监听
     * @param cancelListener
     * @return
     */
    public NormalConfirmPopDialog setOnCancelClickListener(NormalConfirmPopDialog.OnCancelClickListener cancelListener){
        this.cancelListener = cancelListener;
        return this;
    }
}
