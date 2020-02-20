package com.example.epidemicsituation.net;

import com.example.epidemicsituation.bean.PerTraReqInfo;
import com.example.epidemicsituation.bean.PersonalTraInfo;
import com.example.epidemicsituation.bean.RealTimeLocation;
import com.example.epidemicsituation.bean.RiskInfo;
import com.example.epidemicsituation.entity.LoginUserCallback;
import com.example.epidemicsituation.entity.LoginUserPost;
import com.example.epidemicsituation.entity.PoisArea;
import com.example.epidemicsituation.entity.RegisterUserCallback;
import com.example.epidemicsituation.entity.RegisterUserPost;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    String BASE_URL = "http://39.98.41.126:3619/";

    String POIS_AREA_URL = "https://oss.mapmiao.com";

    /**
     * 用户登录接口
     * @param loginUserBody 请求参数 实体类参照 entity.LoginUserPost
     * @return 登录回调类
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("user/login")
    Observable<LoginUserCallback>  loginUserPost(@Body RequestBody loginUserBody);

    /**
     * 用户注册接口
     * @param registerUserBody  请求参数 实体类参照 entity.RegisterUserPost
     * @return RegisterUserCallback 注册回调类
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("user/register")
    Observable<RegisterUserCallback> registerUserPost(@Body RequestBody registerUserBody);




    @GET("https://oss.mapmiao.com/others/ncov/data.json?timestamp=999999999999999")
    Observable<ResponseBody> getPoisArea();

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("/core/listSickdata")
    Observable<PersonalTraInfo> getPersonalTraInfo(@Body PerTraReqInfo personalTraInfo);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("/core/risk")
    Observable<RiskInfo> uploadLocationInfo(@Body RealTimeLocation realTimeLocation);
}
