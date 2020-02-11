package com.example.epidemicsituation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.epidemicsituation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertDialog extends Dialog {
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

    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert);
        ButterKnife.bind(this);
        //根据后台传入数据给tv赋值和设置不同级别的颜色
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
