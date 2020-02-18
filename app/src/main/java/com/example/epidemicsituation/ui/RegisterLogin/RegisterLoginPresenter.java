package com.example.epidemicsituation.ui.RegisterLogin;
import android.os.Build;
import androidx.annotation.Keep;
import androidx.annotation.RequiresApi;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.Base.BasePresent;
import com.example.epidemicsituation.entity.LoginUserCallback;
import com.example.epidemicsituation.entity.LoginUserPost;
import com.example.epidemicsituation.entity.RegisterUserCallback;
import com.example.epidemicsituation.entity.RegisterUserPost;
import com.example.epidemicsituation.net.RetrofitManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * @description: 注册登录模块 P层
 * @author: ODM
 * @date: 2020/2/14
 */
public class RegisterLoginPresenter   implements BasePresent<RegisterLoginActivity> ,RegisterLoginContract.Presenter {


    private Disposable mDisposable;

    /**
     * View 层引用
     */
    private RegisterLoginContract.View mView;

    /**
     * 构造方法
     */
    public RegisterLoginPresenter(){
        super();
    }


    @Override
    public void attachView(RegisterLoginActivity view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void login(String userName, String password) {

        LoginUserPost loginUserPost = new LoginUserPost(userName , password);

        String body = GsonUtils.toJson(loginUserPost);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);
        //调用Retrofit
        RetrofitManager.getInstance()
                .getApiService()
                .loginUserPost(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginUserCallback>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(LoginUserCallback loginUserCallback) {
                        if("-1".equals(loginUserCallback.getCode())) {
                            mView.loginFailed(loginUserCallback.getMessage());
                        } else {
                            mView.loginSuccess();

                            //持久化 用户登录回调 Header 中的 Authorization ,供其他功能使用

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.loginFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

    @Override
    public void register(String userName, String password) {
        RegisterUserPost registerUserPost = new RegisterUserPost(userName , password);
        String body = GsonUtils.toJson(registerUserPost);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);
        //调用Retrofit
        RetrofitManager.getInstance()
                .getApiService()
                .registerUserPost(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterUserCallback>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RegisterUserCallback registerUserCallback) {
                        if(registerUserCallback.getCode().equals("-1") ) {
                            mView.registerFailed(registerUserCallback.getMessage());
                        }else {
                            mView.registerSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.registerFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }




}
