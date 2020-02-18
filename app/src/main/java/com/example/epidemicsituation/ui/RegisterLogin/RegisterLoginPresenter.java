package com.example.epidemicsituation.ui.RegisterLogin;

import android.os.Build;

import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.Base.BasePresent;
import com.example.epidemicsituation.BuildConfig;
import com.example.epidemicsituation.Utils.KeepAliveBackgroundUtil;
import com.example.epidemicsituation.entity.LoginUserCallback;
import com.example.epidemicsituation.entity.LoginUserPost;
import com.example.epidemicsituation.entity.RegisterUserCallback;
import com.example.epidemicsituation.entity.RegisterUserPost;
import com.example.epidemicsituation.net.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @description: 注册登录模块 P层
 * @author: ODM
 * @date: 2020/2/14
 */
public class RegisterLoginPresenter   implements BasePresent<RegisterLoginActivity> ,RegisterLoginContract.Presenter {



    /**
     * View 层引用
     */
    private RegisterLoginContract.View mView;

    /**
     * 构造方法
     */
    public RegisterLoginPresenter(){
        super();
    }


    @Override
    public void attachView(RegisterLoginActivity view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void login(String userName, String password) {

        LoginUserPost loginUserPost = new LoginUserPost(userName , password);

        String body = GsonUtils.toJson(loginUserPost);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);
        //调用Retrofit
        RetrofitManager.getInstance()
                .getApiService()
                .loginUserPost(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginUserCallback>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginUserCallback loginUserCallback) {
                        LogUtils.d(GsonUtils.toJson(loginUserCallback));
                        if("-1".equals(loginUserCallback.getCode())) {
                            LogUtils.e(loginUserCallback.getMessage());
                            mView.loginFailed(loginUserCallback.getMessage());
                        } else {
                            mView.loginSuccess();

                            //持久化 用户登录回调 Header 中的 Authorization ,供其他功能使用

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loginFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });




    }

    @Override
    public void register(String userName, String password) {
        RegisterUserPost registerUserPost = new RegisterUserPost(userName , password);
        String body = GsonUtils.toJson(registerUserPost);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);
        //调用Retrofit
        RetrofitManager.getInstance()
                .getApiService()
                .registerUserPost(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterUserCallback>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RegisterUserCallback registerUserCallback) {
                        if(registerUserCallback.getCode().equals("-1") ) {
                            mView.registerFailed(registerUserCallback.getMessage());
                        }else {
                            mView.registerSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.registerFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void doKeepAliveBackground(){
        //大于Android 6.0 执行弹窗申请电池白名单优化
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            requestBatteryWhiteList();
        }
        //跳转不同手机厂商系统的后台应用管理页面
        showBackgroundSettingPage();

     }

    /**
     * 设置后台电池白名单优化
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestBatteryWhiteList(){
        if(KeepAliveBackgroundUtil.getInstance().isIgnoringBatteryOptimizations()) {
            ToastUtils.showShort("您已在电池优化白名单中");
        } else {
            KeepAliveBackgroundUtil.getInstance()
                    .requestIgnoreBatteryOptimizations();
        }
    }

    private void showBackgroundSettingPage(){
        if(KeepAliveBackgroundUtil.getInstance().isXiaomi()) {
            //前往小米应用设置保活
            KeepAliveBackgroundUtil.getInstance().goXiaomiSetting();
            ToastUtils.showLong("请将应用加入手机自启动名单");
        }else if(KeepAliveBackgroundUtil.getInstance().isHuawei()) {
            //前往华为应用设置保活
            KeepAliveBackgroundUtil.getInstance().goHuaweiSetting();
        }else if(KeepAliveBackgroundUtil.getInstance().isOPPO()) {
            //前往Oppo应用设置
            KeepAliveBackgroundUtil.getInstance().goOPPOSetting();
        }else if(KeepAliveBackgroundUtil.getInstance().isVIVO()) {
            //前往Vivo应用设置
            KeepAliveBackgroundUtil.getInstance().goVIVOSetting();
        }else if (KeepAliveBackgroundUtil.getInstance().isSamsung()){
            //前往三星应用设置
            KeepAliveBackgroundUtil.getInstance().goSamsungSetting();
        }else if(KeepAliveBackgroundUtil.getInstance().isMeizu()) {
            //前往魅族应用设置
            KeepAliveBackgroundUtil.getInstance().goMeizuSetting();
        }else if (KeepAliveBackgroundUtil.getInstance().isSmartisan()) {
            //前往锤子应用设置
            KeepAliveBackgroundUtil.getInstance().goSmartisanSetting();
        }else if(KeepAliveBackgroundUtil.getInstance().isLeTV()) {
            //前往乐视应用设置
            KeepAliveBackgroundUtil.getInstance().goLetvSetting();
        }

    }

}
