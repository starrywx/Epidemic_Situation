package com.example.epidemicsituation.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.SPUtils;
import com.example.epidemicsituation.Constants;
import com.example.epidemicsituation.ui.RegisterLogin.RegisterLoginActivity;
import com.example.epidemicsituation.ui.map.MapActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 启动页
 * 功能：展示Logo，app名 ，路由
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable mDisposable = Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                if("".equals(SPUtils.getInstance().getString(Constants.USER_AUTHORIZATION))){
                    //没有登录信息 持久化,直接跳转登录注册页,否则去地图页
                    startActivity(RegisterLoginActivity.class);
                } else {
                    startActivity(MapActivity.class);
                }

            }
        });

    }

    public void startActivity(Class<?> cls){
        startActivity(new Intent(this, cls));
        finish();
    }
}
