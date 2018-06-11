package com.quarter_hour.modle;

import android.content.Context;

import com.quarter_hour.bean.DianZanBean;
import com.quarter_hour.presenter.DianZan_presenter;
import com.quarter_hour.presenter.MyDianZan_presenter;
import com.quarter_hour.utils.Apiservice;
import com.quarter_hour.utils.OkHttp;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class DianZan {
    private DianZan_presenter dianZan_presenter;

    public DianZan(DianZan_presenter dianZan_presenter) {
        this.dianZan_presenter = dianZan_presenter;
    }


    public void getDianZan(Map<String,String> map){
        Apiservice apiservice = OkHttp.getInstance().getservice();
        apiservice.DianZan(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<DianZanBean>() {
                    @Override
                    public void onNext(DianZanBean dianZanBean) {

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
