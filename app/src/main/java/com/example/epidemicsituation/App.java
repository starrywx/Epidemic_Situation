package com.example.epidemicsituation;

import android.app.Application;
import android.content.Context;

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
    }

    public static Context getContext() {
        return context;
    }
}
