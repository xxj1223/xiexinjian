package com.quarter_hour.presenter;

import android.content.Context;

import com.quarter_hour.bean.Second_DataBean;
import com.quarter_hour.inter.Second_view;
import com.quarter_hour.modle.Second_Modle;

import java.util.List;
import java.util.Map;

public class MySecond_Presenter implements Second_Presenter<List<Second_DataBean>> {
    Second_view second_view;
    Context context;
    Second_Modle second_modle;

    public MySecond_Presenter(Second_view second_view, Context context) {
        this.second_view = second_view;
        this.context = context;
    }

    @Override
    public void OnRevice(List<Second_DataBean> second_dataBeans) {
        second_view.onSuccess(second_dataBeans);
    }

    public void newWork(Map<String, String> map) {
        second_modle = new Second_Modle(context,this);
        second_modle.getHuoqu(map);
    }
    //释放,不然会内存泄漏
    public void destory() {
        if (second_view!=null){
            second_view = null;
        }
    }
}
