package com.example.epidemicsituation.net;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @description: Header补充Cookies OKHTTP 拦截器
 * @author: ODM
 * @date: 2020/2/18
 */
public class AddCookiesInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Set<String> cookiesSet = SPUtils.getInstance().getStringSet("USER_COOKIES"); //获取本地持久化的CookiesSet
        if(! cookiesSet.isEmpty()) {
            HashSet<String> preferences = (HashSet<String>) cookiesSet; //转换成HashSet
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}