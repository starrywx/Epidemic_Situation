package com.example.epidemicsituation.ui.RegisterLogin;

import com.blankj.utilcode.util.GsonUtils;
import com.example.epidemicsituation.entity.LoginUserPost;
import com.example.epidemicsituation.entity.RegisterUserPost;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @description: 注册登录模块 P层
 * @author: ODM
 * @date: 2020/2/14
 */
public class RegisterLoginPresenter implements RegisterLoginContract.Presenter {

    @Override
    public void login(String userName, String password) {
        LoginUserPost loginUserPost = new LoginUserPost();
        loginUserPost.setUser(new LoginUserPost.UserBean(userName,password));
        String body = GsonUtils.toJson(loginUserPost);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);
        //调用Retrofit
    }

    @Override
    public void register(String userName, String password) {
        RegisterUserPost registerUserPost = new RegisterUserPost();
        registerUserPost.setUser(new RegisterUserPost.UserBean(userName ,password));
        String body = GsonUtils.toJson(registerUserPost);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);

        //调用Retrofit
    }
}
