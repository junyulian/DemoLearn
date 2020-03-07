package com.jun.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.jun.dialog.bean.PushInfoBean;
import com.jun.dialog.util.JsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AlarmInfoDialog extends AppCompatDialogFragment {

    @BindView(R2.id.tv_alarm_name)
    TextView tv_alarm_name;
    @BindView(R2.id.tv_device_name)
    TextView tv_device_name;
    @BindView(R2.id.tv_alarm_time)
    TextView tv_alarm_time;
    @BindView(R2.id.tv_device_imei)
    TextView tv_device_imei;
    @BindView(R2.id.tv_device_addr)
    TextView tv_device_addr;

    public static AlarmInfoDialog newInstance(){
        AlarmInfoDialog dialog = new AlarmInfoDialog();
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_push_alarm_info,null);
        ButterKnife.bind(this,view);
        Bundle bundle = getArguments();
        try {
            if(bundle != null){
                String alarmInfo = bundle.getString("alarmInfo");
                PushInfoBean bean = JsonUtil.fromJsonStr(alarmInfo,PushInfoBean.class);
                tv_alarm_name.setText(bean.getAlarmTypeBean().getName());
                tv_device_name.setText(bean.getDeviceName());
                tv_alarm_time.setText("2020-03-04 17:00");//TODO
                tv_device_imei.setText(bean.getImei());
                tv_device_addr.setText(bean.getAddress());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout((int)(dm.widthPixels*0.8),ViewGroup.LayoutParams.WRAP_CONTENT);
        setCancelable(false);
    }


    @OnClick({R2.id.ib_close,R2.id.btn_known})
    public void onViewClicked(View view){
        int id = view.getId();
        if (id == R.id.ib_close || id == R.id.btn_known) {
            if (listerner != null) {
                listerner.click();
            }
            dismiss();
        }
    }



    private OnClickListerner listerner;
    public interface OnClickListerner{
        void click();
    }
    public void setOnClickListerer(OnClickListerner listerer){
        this.listerner = listerer;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(listerner != null){
            listerner.click();
        }
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroyView() {
        if(listerner != null){
            listerner.click();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
