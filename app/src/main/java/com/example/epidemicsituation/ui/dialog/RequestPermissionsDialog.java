package com.example.epidemicsituation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.epidemicsituation.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description: 首次登录向用户说明权限申请 对话框
 * @author: ODM
 * @date: 2020/2/14
 */
public class RequestPermissionsDialog extends Dialog {


    @BindView(R.id.btn_agree_dialog_request_permission)
    Button btnAgreeRequestPermission;
    @BindView(R.id.btn_disagree_dialog_request_permission)
    Button btnDisagreeRequestPermission;

    public RequestPermissionsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_request_permission);
    }

    @OnClick({R.id.btn_agree_dialog_request_permission, R.id.btn_disagree_dialog_request_permission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_agree_dialog_request_permission:

                break;
            case R.id.btn_disagree_dialog_request_permission:

                break;
            default:
                break;
        }
    }
}
