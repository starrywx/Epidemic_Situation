package com.example.epidemicsituation.ui.RegisterLogin;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.Base.BaseActivity;
import com.example.epidemicsituation.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.tv_phone_register)
    TextView tvPhoneRegister;
    @BindView(R.id.tv_verification)
    TextView tvVerification;
    @BindView(R.id.tv_password_register)
    TextView tvPasswordRegister;
    @BindView(R.id.iv_register_login)
    ImageView ivBackground;
    @BindView(R.id.tv_get_verification)
    TextView tvGetVerification;
    @BindView(R.id.underLine_et_verification)
    View underLineEtVerification;
    @BindView(R.id.rl_container_card_register)
    RelativeLayout rlContainerCardRegister;

    Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        ButterKnife.bind(this);
        //进入页面，默认 登录指示器为选中状态
        cardLogin.setVisibility(View.VISIBLE);
        tvIndicatorLogin.setTextColor(getResources().getColor(R.color.indicator_login));
        onIndicatorAnim(tvIndicatorLogin, true);

        initViewsOnClickEvent();
        observeEditText();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDisposable != null) {
            mDisposable.dispose();
        }
    }

    private void initViewsOnClickEvent() {
        onTvIndicatorLoginClicked();
        onTvIndicatorRegisterClicked();
        onLoginButtonClicked();
        onRegisterButtonClicked();
        onTvGetVerificationClicked();
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
                //显示登录卡片，隐藏注册卡片
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
                //注册指示器变色，放大
                tvIndicatorRegister.setTextColor(getResources().getColor(R.color.indicator_register));
                onIndicatorAnim(tvIndicatorRegister, true);
                //登录指示器变灰色，缩小
                tvIndicatorLogin.setTextColor(getResources().getColor(R.color.indicator_login_normal));
                onIndicatorAnim(tvIndicatorLogin, false);
                //显示注册卡片，隐藏登录卡片
                cardRegister.setVisibility(View.VISIBLE);
                cardLogin.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 登录按钮的点击事件
     */
    @OnClick(R.id.btn_login)
    public void onLoginButtonClicked() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("触发登录事件");
            }
        });
    }

    /**
     * 注册按钮的点击事件
     */
    @OnClick(R.id.btn_register)
    public void onRegisterButtonClicked() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("触发注册事件");
            }
        });
    }

    /**
     * 传入 View ，执行放大动画或缩小动画
     * @param view
     * @param zoom
     */
    private void onIndicatorAnim(View view, boolean zoom) {
        if (zoom) {
            ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.8f);
            ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.8f);
            scaleXAnim.setDuration(300).start();
            scaleYAnim.setDuration(300).start();
        } else {
            ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 1.8f, 1f);
            ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1.8f, 1f);
            scaleXAnim.setDuration(300).start();
            scaleYAnim.setDuration(300).start();
        }
    }

    /**
     *  点击获取验证码事件监听
     */
    @OnClick(R.id.tv_get_verification)
    public void onTvGetVerificationClicked() {
        tvGetVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始倒计时 60s
                tvGetVerification.setTextColor(getResources().getColor(R.color.disable_get_verification_disable));
                tvGetVerification.setEnabled(false);//倒计时中不可再次点击
                //从0开始发射61个数字为：0-59依次输出，延时0s执行，每1s发射一次。
                 mDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                String content = (59 - aLong) + " 秒后再试";
                                tvGetVerification.setText(content);
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                //倒计时完毕置为可点击状态
                                tvGetVerification.setEnabled(true);
                                tvGetVerification.setText("获取验证码");
                                tvGetVerification.setTextColor(getResources().getColor(R.color.able_get_verification_disable));
                            }
                        })
                        .subscribe();

            }
        });
    }

    /**
     *  监听 输入框事件
     */
    private void observeEditText(){
        /*
         * 验证码输入框焦点监听事件
         */
        etVerification.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //动态获取输入框底线View当前的布局参数
                RelativeLayout.LayoutParams params =(RelativeLayout.LayoutParams) underLineEtVerification.getLayoutParams();
                params.width = ConvertUtils.dp2px(250f); // 底线的宽强制设成与布局相同的250dp
                if(hasFocus) {
                    //获取到焦点：底线变色
                    underLineEtVerification.setBackgroundColor(getResources().getColor(R.color.editText_underline_chosen));
                    params.height =ConvertUtils.dp2px(2f);// 底线的高强制设成2dp
                } else {
                    //失去焦点：底线变灰色
                    underLineEtVerification.setBackgroundColor(getResources().getColor(R.color.editText_underline_normal));
                    params.height =ConvertUtils.dp2px(1f);// 底线的高强制设成 1dp
                }
                //动态设置宽高给 底线
                underLineEtVerification.setLayoutParams(params);
            }
        });
    }
}
