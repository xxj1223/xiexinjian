package com.quarter_hour.utils;


import com.quarter_hour.bean.AppearBean;
import com.quarter_hour.bean.DianZanBean;
import com.quarter_hour.bean.LoginBean;
import com.quarter_hour.bean.Message;
import com.quarter_hour.bean.Second_DataBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Forget on 2018/04/20.
 */

public interface Apiservice {
    //获取段子
    //https://www.zhaoapi.cn/quarter/getJokes?page=1&token=D47DC75915E48F2D19F787FF90A41C39&source=android&appVersion=1
    @GET("quarter/getJokes")
    Flowable<Message<List<Second_DataBean>>> Huoqu(@QueryMap Map<String,String> map);
    //点赞
    //https://www.zhaoapi.cn/quarter/jokePraise?uid=13850&jid=2725&token=D47DC75915E48F2D19F787FF90A41C39&source=android&appVersion=1
    @GET("quarter/jokePraise")
    Flowable<DianZanBean> DianZan(@QueryMap Map<String,String> map);

    //注册
    //https://www.zhaoapi.cn/quarter/register?mobile=18301597122&password=971223&regType=0
    @GET("quarter/register")
    Flowable<Message<LoginBean>> Register(@QueryMap Map<String,String> map);

    //登录
    //https://www.zhaoapi.cn/user/login?mobile=18301597122&password=971223
    @GET("user/login")
    Flowable<Message<LoginBean>> Login(@QueryMap Map<String,String> map);
    //发表
    //https://www.zhaoapi.cn/quarter/publishJoke?uid=13850&token=D47DC75915E48F2D19F787FF90A41C39&content=?&source=android&appVersion=1
    @GET("quarter/publishJoke")
    Flowable<AppearBean> Appear(@QueryMap Map<String,String> map);
}
