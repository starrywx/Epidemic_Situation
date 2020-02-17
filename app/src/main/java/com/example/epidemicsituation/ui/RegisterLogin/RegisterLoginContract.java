package com.example.epidemicsituation.ui.RegisterLogin;

/**
 * @description: 注册登录模块 契约类
 * @author: ODM
 * @date: 2020/2/14
 */

public class RegisterLoginContract  {

     interface View{
         /**
          *  登录成功
          */
        void loginSuccess();

         /**
          * 登录失败
          */
         void loginFailed();

         /**
          *  注册成功
          */
         void registerSuccess();

         /**
          * 注册失败
          */
        void registerFailed();
    }


     interface Presenter {

         /**
          * 登录
          * @param userName
          * @param password
          */
        void login(String userName , String password);

         /**
          *  注册
          * @param userName
          * @param password
          */
        void register(String userName ,String password);

    }

     interface Repository{

    }

}
