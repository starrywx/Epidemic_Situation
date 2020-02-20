package com.example.epidemicsituation.ui.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.epidemicsituation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealTimeRiskDialogActivity extends AppCompatActivity {

    @BindView(R.id.dialog_real_time_risk_imv_delete)
    ImageView dialogRealTimeRiskImvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_risk_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dialog_real_time_risk_imv_delete)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.dialog_real_time_risk_imv_delete) {
            finish();
        }
    }
}
