package com.jun.demolearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jun.demolearn.R;
import com.jun.demolearn.base.BaseActivity;
import com.jun.dialog.AlarmInfoDialog;
import com.jun.dialog.NormalConfirmActivityDialog;
import com.jun.dialog.NormalConfirmDefDialog;
import com.jun.dialog.NormalConfirmDialog;
import com.jun.dialog.NormalConfirmFragmentDialog;
import com.jun.dialog.NormalConfirmPopDialog;
import com.jun.dialog.NormalInputDialog;
import com.jun.dialog.TopDialog;
import com.jun.dialog.util.BottomBialog;
import com.jun.utils.common.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 各种对话框的入口 activity
 */
public class DialogEnterActivity extends BaseActivity {

    @BindView(R.id.tv_data)
    TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_dialog);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_fragment_dialog,R.id.btn_normal_dialog,
            R.id.btn_normal_pop_dialog,R.id.btn_normal_define_dialog,
            R.id.btn_normal_activity_dialog,R.id.btn_normal_input_dialog,
            R.id.btn_push_alarm_dialog,R.id.btn_top_dialog,
            R.id.btn_bottom_dialog})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_fragment_dialog:
                showNormalConfirmFragmentDialog();
                break;
            case R.id.btn_normal_dialog:
                showNormalConfirmDialog();
                break;
            case R.id.btn_normal_pop_dialog:
                showNormalPopDialog();
                break;
            case R.id.btn_normal_define_dialog:
                showDefineDialog();
                break;
            case R.id.btn_normal_activity_dialog:
                showActivityDialog();
                break;
            case R.id.btn_normal_input_dialog:
                showNormalInputDialog();
                break;
            case R.id.btn_push_alarm_dialog:
                showPushAlarmDialog();
                break;
            case R.id.btn_top_dialog:
                showTopDialog();
                break;
            case R.id.btn_bottom_dialog:
                showBottomDialog();
                break;
            default:
                break;
        }
    }

    private void showBottomDialog() {
        BottomBialog.Builder builder = new BottomBialog.Builder();
        builder.setContent("暮然回首,那人却在灯火阑珊处");
        BottomBialog dialog = builder.create();
        dialog.show(getSupportFragmentManager(),"");
    }

    private void showTopDialog() {
        TopDialog.Builder builder = new TopDialog.Builder();
        builder.setContent("开启新的世界");
        TopDialog dialog = builder.create();
        dialog.show(getSupportFragmentManager(),"");
    }

    private void showPushAlarmDialog() {
        String alarmInfo =  "{\"gpsStatus\":false,\"messageTypeId\":1,\"deviceAlarmId\":675899349,\"imei\":\"866551032760683\",\"deviceName\":\"760683-有线 南汇工程 全顺\",\"alarmTypeBean\":{\"alarmTypeId\":12,\"name\":\"超速报警\"},\"alarmTime\":1582793180000,\"isRead\":false,\"reminderTime\":60}";

        Bundle bundle = new Bundle();
        bundle.putString("alarmInfo",alarmInfo);
        AlarmInfoDialog dialog = AlarmInfoDialog.newInstance();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(),"alarm");

    }

    private void showNormalInputDialog(){
//        NormalInputDialog dialog = new NormalInputDialog();
//        dialog.show(getSupportFragmentManager(),"ha");
        NormalInputDialog.Builder builder = new NormalInputDialog.Builder();
        builder.setDes("住址");
        builder.setHint("this is your place");
        builder.setContent("北海不归客");
        builder.setCancelText("取消");

        NormalInputDialog dialog = builder.creat();
        dialog.setOnNegativeClickListener(new NormalInputDialog.OnNegativeClickListener() {
            @Override
            public void click() {
                LogUtil.e("-------取消");

            }
        }).setOnPositiveClickListener(new NormalInputDialog.OnPositiveClickListener() {
            @Override
            public void click(String content) {
                LogUtil.e("-------确认:"+content);
                tv_data.setText(content);
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(),"this");
    }

    private void showActivityDialog() {
//        Intent intent = new Intent(this, NormalConfirmActivityDialog.class);
//        startActivity(intent);
          moveTo(NormalConfirmActivityDialog.class);
    }

    private void showDefineDialog(){
        NormalConfirmDefDialog dialog = new NormalConfirmDefDialog(DialogEnterActivity.this);
        dialog.builder().setTitle("tips").setMsg("this is a story").setOnOKClickListener(new NormalConfirmDefDialog.OnOKClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
            }
        }).show();
    }

    private void showNormalPopDialog(){

        View parentView = LayoutInflater.from(DialogEnterActivity.this).inflate(R.layout.activity_main, null);
        NormalConfirmPopDialog dialog = new NormalConfirmPopDialog(DialogEnterActivity.this);
        dialog.setOnOKClickListener(new NormalConfirmPopDialog.OnOKClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
            }
        }).setOnCancelClickListener(new NormalConfirmPopDialog.OnCancelClickListener() {
            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setTitle("zy").setMsg("this is a msg");

        dialog.showAtCenter(parentView);
    }



    private void showNormalConfirmDialog(){
        NormalConfirmDialog.Builder builder = new NormalConfirmDialog.Builder(this);
        NormalConfirmDialog dialog = builder.create();
        builder.setTitle("world");
        builder.setMessage("this is a new world, pls try");
        builder.setOnOKClickListener(new NormalConfirmDialog.OnOKClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showNormalConfirmFragmentDialog(){

        NormalConfirmFragmentDialog dialog = NormalConfirmFragmentDialog.newInstance();
        dialog.setOKClickListener(new NormalConfirmFragmentDialog.OnOKClickListener() {
            @Override
            public void onOkClick() {
                dialog.dismiss();
            }
        }).setCancelClickListener(new NormalConfirmFragmentDialog.OnCancelClickListener(){

            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setCancelable(true);

        dialog.show(getSupportFragmentManager(),"first");

    }

}
