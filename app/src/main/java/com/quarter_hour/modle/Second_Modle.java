package com.quarter_hour.modle;

import android.content.Context;
import android.util.Log;

import com.quarter_hour.bean.Message;
import com.quarter_hour.bean.Second_DataBean;
import com.quarter_hour.presenter.Second_Presenter;
import com.quarter_hour.utils.Apiservice;
import com.quarter_hour.utils.OkHttp;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class Second_Modle {
    private Context context;
    private Second_Presenter second_presenter;

    public Second_Modle(Context context, Second_Presenter second_presenter) {
        this.context = context;
        this.second_presenter = second_presenter;
    }
    public void getHuoqu(Map<String,String> map) {
        //网络请求
        Apiservice apiservice = OkHttp.getInstance().getservice();
        apiservice.Huoqu(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Message<List<Second_DataBean>>>() {

                    @Override
                    public void onNext(Message<List<Second_DataBean>> listMessage) {
                        List<Second_DataBean> data = listMessage.getData();
                        second_presenter.OnRevice(data);
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
