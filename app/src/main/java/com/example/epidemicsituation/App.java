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

    /**
     * 服务是否启动中
     */
    private static boolean isStartingService;

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
        /*CrashCatchUtil.getInstance().setCrashHandler(new CrashCatchUtil.CrashHandler() {
            @Override
            public void handlerException(Thread t, Throwable e) {
                 //发生了未catch的异常，执行重启
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                if (LaunchIntent != null) {
                    LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(LaunchIntent);
                }

            }
        });*/

    }

    public static boolean isIsStartingService() {
        return isStartingService;
    }

    public static void setIsStartingService(boolean isStartingService) {
        App.isStartingService = isStartingService;
    }
}
