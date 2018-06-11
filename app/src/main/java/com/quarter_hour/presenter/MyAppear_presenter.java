package com.quarter_hour.presenter;

import android.content.Context;

import com.quarter_hour.inter.Appear_view;
import com.quarter_hour.modle.Appear_modle;

import java.util.Map;

public class MyAppear_presenter implements Appear_presenter {
    Appear_view appear_view;
    Context context;
    Appear_modle appear_modle;

    public MyAppear_presenter(Appear_view appear_view, Context context) {
        this.appear_view = appear_view;
        this.context = context;
    }

    @Override
    public void onRevice(Object o) {
        appear_view.onSuccess(o);
    }
    public void newAppear(Map<String, String> map) {
        appear_modle = new Appear_modle(context,this);
        appear_modle.getAppear(map);
    }

    /**
     * 释放
     */
    public void shifang() {
        if (appear_view!=null){
            appear_view = null;
        }
    }
}
