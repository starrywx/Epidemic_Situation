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

public class SuspiciousDialog extends Dialog {
    @BindView(R.id.dialog_suspicious_imv_delete)
    ImageView dialogSuspiciousImvDelete;

    public SuspiciousDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_suspicious);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dialog_suspicious_imv_delete)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.dialog_suspicious_imv_delete) {
            cancel();
        }
    }
}
