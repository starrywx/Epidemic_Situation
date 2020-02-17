package com.example.epidemicsituation.net;

import com.example.epidemicsituation.entity.LoginUserPost;
import com.example.epidemicsituation.entity.PoisArea;
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
    String BASE_URL = "";

    String POIS_AREA_URL = "https://oss.mapmiao.com";

    /**
     * 用户登录接口
     * @param loginUserBody 请求参数 实体类参照 entity.LoginUserPost
     * @return 未定，暂用请求实体类替代
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("/user/login")
    Observable<LoginUserPost>  loginUserPost(@Body RequestBody loginUserBody);

    /**
     * 用户注册接口
     * @param registerUserBody  请求参数 实体类参照 entity.RegisterUserPost
     * @return 未定，暂用请求实体类替代
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("/user/register")
    Observable<RegisterUserPost> registerUserPost(@Body RequestBody registerUserBody);


    @GET("https://oss.mapmiao.com/others/ncov/data.json?timestamp=999999999999999")
    Observable<ResponseBody> getPoisArea();
}
