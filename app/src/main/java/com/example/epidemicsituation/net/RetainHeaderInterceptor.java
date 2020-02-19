package com.example.epidemicsituation.net;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.example.epidemicsituation.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @description: Header,Cookies 持久化 Okhttp拦截器
 * @author: ODM
 * @date: 2020/2/18
 */
public class RetainHeaderInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();

        if (!SPUtils.getInstance().getString(Constants.USER_AUTHORIZATION,"").equals("")) {
            String authorization = SPUtils.getInstance().getString(Constants.USER_AUTHORIZATION);
            builder.addHeader("Authorization", authorization);
        }

        Request request = builder.build();

        Response originalResponse = chain.proceed(request);

/*        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));
            for (String cookie : cookies) {
                LogUtils.d(cookie); //循环打印
            }
            SPUtils.getInstance().put("USER_COOKIES", cookies); //用SharedPreferences 存储Cookies
        }*/

        //持久化响应报文中的 Authorization 属性
        if( originalResponse.header("Authorization") != null) {
            String authorization = originalResponse.header("Authorization");
            if (authorization != null) {
                Log.d("authorization" , authorization);
                SPUtils.getInstance().put(Constants.USER_AUTHORIZATION,authorization);
            }

        }

        return originalResponse;
    }
}
