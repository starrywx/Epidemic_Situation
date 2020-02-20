package com.example.epidemicsituation;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.Utils.CrashCatchUtil;

/**
 * @description: Application 实现类
 * @author: ODM
 * @date: 2020/2/17
 */
public class App extends Application {

    public App(){
        super();
    }

    /**
     * 可能导致内存泄漏的全局静态Context
     */
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initCrashCatchHandler();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化App崩溃异常捕捉
     * 指定运行版本：release
     */
    private void initCrashCatchHandler(){
        if(! AppUtils.isAppDebug()) {
            CrashCatchUtil.getInstance().setCrashHandler(new CrashCatchUtil.CrashHandler() {
                @Override
                public void handlerException(Thread t, Throwable e) {
                    //app 在后台运行发生了崩溃，关闭所有Activity，bindService的服务被销毁
                    //杀掉进程，执行重启
                    if(! AppUtils.isAppForeground()) {
                        ActivityUtils.finishAllActivities();
                        AppUtils.relaunchApp(true);
                    }
                }
            });
        }
    }
}
