package com.quarter_hour.modle;

import android.content.Context;

import com.quarter_hour.bean.LoginBean;
import com.quarter_hour.bean.Message;
import com.quarter_hour.presenter.Login_presenter;
import com.quarter_hour.utils.Apiservice;
import com.quarter_hour.utils.OkHttp;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class Login_modle {
    private Context context;
    private Login_presenter login_presenter;

    public Login_modle(Context context, Login_presenter login_presenter) {
        this.context = context;
        this.login_presenter = login_presenter;
    }
    public void getLogin(Map<String,String> map){
        Apiservice apiservice = OkHttp.getInstance().getservice();
        apiservice.Login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Message<LoginBean>>() {
                    @Override
                    public void onNext(Message<LoginBean> loginBeanMessage) {
                        login_presenter.onRevice(loginBeanMessage);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
