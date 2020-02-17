package com.example.epidemicsituation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.epidemicsituation.R;
import com.example.epidemicsituation.Utils.TimeChangeUtil;
import com.example.epidemicsituation.bean.AlertInfo;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertDialog extends Dialog {

    private static long standardTime;
    private AlertInfo.DataBean dataBean;
    @BindView(R.id.dialog_alert_tv_time)
    TextView dialogAlertTvTime;
    @BindView(R.id.dialog_alert_imv_delete)
    ImageView dialogAlertImvDelete;
    @BindView(R.id.dialog_alert_tv_title)
    TextView dialogAlertTvTitle;
    @BindView(R.id.dialog_alert_tv_probability)
    TextView dialogAlertTvProbability;
    @BindView(R.id.dialog_alert_tv_suggestion)
    TextView dialogAlertTvSuggestion;

    public AlertDialog(@NonNull Context context, int themeResId, AlertInfo.DataBean dataBean) {
        super(context, themeResId);
        this.dataBean=dataBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert);
        ButterKnife.bind(this);
        //根据后台传入数据给tv赋值和设置不同级别的颜色
        dialogAlertTvProbability.setText((int)dataBean.getValue());
        dialogAlertTvTime.setText(dataBean.getDate());
        if(dataBean.getValue()<0.50){
            dialogAlertTvTitle.setTextColor(Color.parseColor("#2DBF30"));
            dialogAlertTvProbability.setTextColor(Color.parseColor("#10AD13"));
            dialogAlertTvSuggestion.setText("感染风险较低");
            dialogAlertTvSuggestion.setTextColor(Color.parseColor("#2DBF30"));
        }else if(dataBean.getValue()<0.70){
            dialogAlertTvTitle.setTextColor(Color.parseColor("#FA8048"));
            dialogAlertTvProbability.setTextColor(Color.parseColor("#E44B04"));
            dialogAlertTvSuggestion.setText("感染风险中等，请尽量避免去人群密集的地方");
            dialogAlertTvSuggestion.setTextColor(Color.parseColor("#FA8048"));
        }else{
            dialogAlertTvTitle.setTextColor(Color.parseColor("#EA6F6F"));
            dialogAlertTvProbability.setTextColor(Color.parseColor("#EB1F1F"));
            dialogAlertTvSuggestion.setText("感染风险极高，请自行在家隔离");
            dialogAlertTvSuggestion.setTextColor(Color.parseColor("#EA6F6F"));
        }
    }

    @OnClick(R.id.dialog_alert_imv_delete)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.dialog_alert_imv_delete:
                cancel();
                break;
            default:
                break;
        }
    }
}
