package com.quarter_hour.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.OvershootInterpolator;

import com.quarter_hour.R;
import com.quarter_hour.adapter.SecondAdapter;
import com.quarter_hour.base.BaseFragment;
import com.quarter_hour.bean.Second_DataBean;
import com.quarter_hour.inter.Second_view;
import com.quarter_hour.presenter.MySecond_Presenter;
import com.quarter_hour.ui.MainActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class An_Episode_02 extends BaseFragment implements Second_view<List<Second_DataBean>> {

    private RecyclerView episode_lv;
    private SecondAdapter secondAdapter;
    private MySecond_Presenter mySecond_presenter;
    private SmartRefreshLayout swip;
    //定义一个大集合存放数据
    private List<Second_DataBean> lists = new ArrayList<>();
    //页数
    private int page=0;
    private String token = "D47DC75915E48F2D19F787FF90A41C39";
    private String source="android";
    private int appVersion =1;
    @Override
    protected int bindLayout() {
        return R.layout.an_episode_02;
    }

    @Override
    protected void initView() {
        episode_lv = findViewById(R.id.episode_lv);
        swip = findViewById(R.id.swip);
    }

    @Override
    protected void initData() {
        mySecond_presenter = new MySecond_Presenter(this,getActivity());
        Map<String,String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("token",token);
        map.put("source",source);
        map.put("appVersion", String.valueOf(appVersion));
        mySecond_presenter.newWork(map);
        //刷新
        swip.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                lists.clear();
                refreshlayout.finishRefresh(2000);
                secondAdapter.notifyDataSetChanged();
            }
        });
        //加载
        swip.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                Map<String,String> map = new HashMap<>();
                map.put("page", String.valueOf(page));
                map.put("token",token);
                map.put("source",source);
                map.put("appVersion", String.valueOf(appVersion));
                mySecond_presenter.newWork(map);
                refreshlayout.finishLoadmore(2000);
            }
        });
        episode_lv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        episode_lv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //recylv的动画
        episode_lv.setItemAnimator(new SlideInLeftAnimator());
        episode_lv.getItemAnimator().setAddDuration(1000);
        episode_lv.getItemAnimator().setRemoveDuration(1000);
        episode_lv.getItemAnimator().setMoveDuration(1000);
        episode_lv.getItemAnimator().setChangeDuration(1000);
        SlideInLeftAnimator animator = new SlideInLeftAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        episode_lv.setItemAnimator(animator);
        //适配器
        secondAdapter = new SecondAdapter(getActivity(),lists);
        episode_lv.setAdapter(new AlphaInAnimationAdapter(secondAdapter));
        //加载动画
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(secondAdapter);
        alphaAdapter.setDuration(1500);
        episode_lv.setAdapter(alphaAdapter);
    }

    @Override
    protected void bindEvent() {

    }

    /**
     * 成功
     * @param second_dataBeans
     */
    @Override
    public void onSuccess(List<Second_DataBean> second_dataBeans) {
        if (second_dataBeans != null){
            lists.clear();
            lists.addAll(second_dataBeans);
            secondAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 失败
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mySecond_presenter.destory();
    }
}
