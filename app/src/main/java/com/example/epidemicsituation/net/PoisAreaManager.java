package com.example.epidemicsituation.net;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PoisAreaManager {

    private static PoisAreaManager poisAreaManager;
    private Retrofit retrofit;
    private ApiService service;
    private static int DEFAULT_TIME_OUT = 8;

    /**
     * 超时时间，默认为8秒
     * 有需要就用SP存储
     */
    private static int timeoutTime = DEFAULT_TIME_OUT;
    /**
     * 服务器ip地址
     */
    private static String baseUrl = ApiService.POIS_AREA_URL;
    public final static HashMap<String, List<Cookie>> COOKIE_STORE = new HashMap<>();

    private PoisAreaManager(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                        COOKIE_STORE.put(httpUrl.host(),list);
                    }
                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                        List<Cookie> cookies = COOKIE_STORE.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .retryOnConnectionFailure(true)
                .connectTimeout(timeoutTime, TimeUnit.SECONDS)
                .writeTimeout(timeoutTime,TimeUnit.SECONDS)
                .readTimeout(timeoutTime,TimeUnit.SECONDS);

        //创建Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    /**
     * 获取网络管理的manager
     * @return 该单例类
     */
    public static PoisAreaManager getInstance(){
        if(poisAreaManager == null){
            synchronized (Object.class){
                if(poisAreaManager == null){
                    poisAreaManager = new PoisAreaManager();
                }
            }
        }
        return poisAreaManager;
    }

    /**
     * 获取访问http的service
     * @return HttpService
     */
    public ApiService getApiService() {
        return service;
    }
}
