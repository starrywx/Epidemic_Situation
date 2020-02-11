package com.example.epidemicsituation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.epidemicsituation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogOutDialog extends Dialog {
    @BindView(R.id.dialog_log_out_cancel)
    TextView dialogLogOutCancel;
    @BindView(R.id.dialog_log_out_sure)
    TextView dialogLogOutSure;

    public LogOutDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_log_out);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.dialog_log_out_cancel, R.id.dialog_log_out_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_log_out_cancel:
                cancel();
                break;
            case R.id.dialog_log_out_sure:
                //退出登录

                break;
                default:
                    break;
        }
    }
}
