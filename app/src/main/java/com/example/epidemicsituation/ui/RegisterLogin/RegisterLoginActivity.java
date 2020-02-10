package com.example.epidemicsituation.ui.RegisterLogin;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.Base.BaseActivity;
import com.example.epidemicsituation.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterLoginActivity extends BaseActivity {


    @BindView(R.id.tv_indicator_login)
    TextView tvIndicatorLogin;
    @BindView(R.id.tv_indicator_register)
    TextView tvIndicatorRegister;
    @BindView(R.id.tv_phone_login)
    TextView tvPhoneLogin;
    @BindView(R.id.et_phone_login)
    MaterialEditText etPhoneLogin;
    @BindView(R.id.tv_password_login)
    TextView tvPasswordLogin;
    @BindView(R.id.et_password_login)
    MaterialEditText etPasswordLogin;
    @BindView(R.id.rl_container_card_login)
    RelativeLayout rlContainerCardLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.card_login)
    CardView cardLogin;
    @BindView(R.id.et_phone_register)
    MaterialEditText etPhoneRegister;
    @BindView(R.id.et_verification)
    MaterialEditText etVerification;
    @BindView(R.id.et_password_register)
    MaterialEditText etPasswordRegister;
    @BindView(R.id.et_password_register_confirm)
    MaterialEditText etPasswordRegisterConfirm;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.card_register)
    CardView cardRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        ButterKnife.bind(this);
        //进入页面，默认 登录指示器为选中状态
        tvIndicatorLogin.setTextColor(getResources().getColor(R.color.indicator_login));
        onIndicatorAnim(tvIndicatorLogin, true);
        initViewsOnClickEvent();
    }

    private void initViewsOnClickEvent() {
        onTvIndicatorLoginClicked();
        onTvIndicatorRegisterClicked();
        onLoginButtonClicked();
        onRegisterButtonClicked();
    }

    /**
     * 登录 指示器的点击事件
     */
    @OnClick(R.id.tv_indicator_login)
    public void onTvIndicatorLoginClicked() {
        tvIndicatorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录指示器变色，放大
                tvIndicatorLogin.setTextColor(getResources().getColor(R.color.indicator_login));
                onIndicatorAnim(tvIndicatorLogin, true);
                //注册指示器变灰色，缩小
                tvIndicatorRegister.setTextColor(getResources().getColor(R.color.indicator_register_normal));
                onIndicatorAnim(tvIndicatorRegister, false);
                cardLogin.setVisibility(View.VISIBLE);
                cardRegister.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 注册 指示器的点击事件
     */
    @OnClick(R.id.tv_indicator_register)
    public void onTvIndicatorRegisterClicked() {

        tvIndicatorRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录指示器变色，放大
                tvIndicatorRegister.setTextColor(getResources().getColor(R.color.indicator_register));
                onIndicatorAnim(tvIndicatorRegister, true);
                //注册指示器变灰色，缩小
                tvIndicatorLogin.setTextColor(getResources().getColor(R.color.indicator_login_normal));
                onIndicatorAnim(tvIndicatorLogin, false);
                cardRegister.setVisibility(View.VISIBLE);
                cardLogin.setVisibility(View.GONE);

            }
        });
    }

    @OnClick(R.id.btn_login)
    public void onLoginButtonClicked() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录按钮的点击事件
                ToastUtils.showShort("登录");
            }
        });
    }
    @OnClick(R.id.btn_register)
    public void onRegisterButtonClicked() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("注册");
            }
        });
    }

    private void onIndicatorAnim(View view, boolean zoom) {
        if (zoom) {
            ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, 2f);
            ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1f, 2f);
            scaleXAnim.setDuration(500).start();
            scaleYAnim.setDuration(500).start();
        } else {
            ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 2f, 1f);
            ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 2f, 1f);
            scaleXAnim.setDuration(500).start();
            scaleYAnim.setDuration(500).start();
        }

    }


}
