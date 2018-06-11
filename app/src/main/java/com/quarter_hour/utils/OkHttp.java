package com.quarter_hour.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Forget on 2018/04/20.
 */

public class OkHttp {
    private static volatile OkHttp instance;
    private Retrofit retrofit;
    private OkHttp(){
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    public static OkHttp getInstance(){
        if (instance == null){
            synchronized (OkHttp.class){
                if (null == instance){
                    instance = new OkHttp();
                }
            }
        }
        return instance;
    }
    public Apiservice getservice(){
        return retrofit.create(Apiservice.class);
    }
}
