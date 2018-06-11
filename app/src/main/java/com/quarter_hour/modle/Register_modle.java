package com.quarter_hour.modle;

import android.content.Context;

import com.quarter_hour.bean.LoginBean;
import com.quarter_hour.bean.Message;
import com.quarter_hour.presenter.Register_presenter;
import com.quarter_hour.utils.Apiservice;
import com.quarter_hour.utils.OkHttp;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class Register_modle {
    private Context context;
    private Register_presenter register_presenter;

    public Register_modle(Context context, Register_presenter register_presenter) {
        this.context = context;
        this.register_presenter = register_presenter;
    }
    public void getRegister(Map<String,String> map){
        Apiservice apiservice = OkHttp.getInstance().getservice();
        apiservice.Register(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Message<LoginBean>>() {
                    @Override
                    public void onNext(Message<LoginBean> loginBeanMessage) {
                        register_presenter.onRevice(loginBeanMessage);
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
