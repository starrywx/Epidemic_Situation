package com.example.epidemicsituation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.epidemicsituation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealTimeRiskDialog extends Dialog {
    @BindView(R.id.dialog_real_time_risk_imv_delete)
    ImageView dialogRealTimeRiskImvDelete;

    public RealTimeRiskDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_real_time_risk);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dialog_real_time_risk_imv_delete)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_real_time_risk_imv_delete:
                cancel();
                break;
            default:
                break;
        }
    }
}
