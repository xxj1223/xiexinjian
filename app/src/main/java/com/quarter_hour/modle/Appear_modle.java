package com.quarter_hour.modle;

import android.content.Context;

import com.quarter_hour.bean.AppearBean;
import com.quarter_hour.bean.Message;
import com.quarter_hour.presenter.Appear_presenter;
import com.quarter_hour.utils.Apiservice;
import com.quarter_hour.utils.OkHttp;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class Appear_modle {
    private Context context;
    private Appear_presenter appear_presenter;

    public Appear_modle(Context context, Appear_presenter appear_presenter) {
        this.context = context;
        this.appear_presenter = appear_presenter;
    }
    public void getAppear(Map<String,String> map){
        Apiservice apiservice = OkHttp.getInstance().getservice();
        apiservice.Appear(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<AppearBean>() {

                    @Override
                    public void onNext(AppearBean appearBean) {
                        appear_presenter.onRevice(appearBean);
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
