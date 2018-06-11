package com.quarter_hour.presenter;

import android.content.Context;
import android.view.View;

import com.quarter_hour.inter.DianZan_view;
import com.quarter_hour.modle.DianZan;

import java.util.Map;

public class MyDianZan_presenter implements DianZan_presenter {
    DianZan_view dianZan_view;
    DianZan dianZan;

    public MyDianZan_presenter(DianZan_view dianZan_view) {
        this.dianZan_view = dianZan_view;
        if (dianZan_view!=null){
            dianZan_view = null;
        }
    }


    @Override
    public void onRevice(Object o) {
        dianZan_view.onSuccess(o);
    }

    public void newWork(Map<String, String> map) {
        dianZan = new DianZan(this);
        dianZan.getDianZan(map);
    }
}
