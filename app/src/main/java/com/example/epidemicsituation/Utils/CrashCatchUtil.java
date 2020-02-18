package com.example.epidemicsituation.Utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @description: 异常捕获工具类
 * @author: ODM
 * @date: 2020/2/17
 */
public class CrashCatchUtil {

        private CrashHandler mCrashHandler;

        private static CrashCatchUtil mInstance;

        private CrashCatchUtil(){

        }

        public static CrashCatchUtil getInstance(){
            if(mInstance == null){
                synchronized (CrashCatchUtil.class){
                    if(mInstance == null){
                        mInstance = new CrashCatchUtil();
                    }
                }
            }

            return mInstance;
        }

        public static void init(CrashHandler crashHandler){
            getInstance().setCrashHandler(crashHandler);
        }

        public void setCrashHandler(CrashHandler crashHandler){

            mCrashHandler = crashHandler;
            //主线程异常拦截
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (;;) {
                        try {
                            Looper.loop();
                        } catch (Throwable e) {
                            if (mCrashHandler != null) {
                                //处理异常
                                mCrashHandler.handlerException(Looper.getMainLooper().getThread(), e);
                            }
                        }
                    }
                }
            });

            //所有线程异常拦截，由于主线程的异常都被我们catch住了，所以下面的代码拦截到的都是子线程的异常
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    if(mCrashHandler!=null){
                        //处理异常
                        mCrashHandler.handlerException(t,e);
                    }
                }
            });


        }

        public interface CrashHandler{
            void handlerException(Thread t,Throwable e);
        }


    }
