package com.quarter_hour.presenter;

import android.content.Context;

import com.quarter_hour.inter.Register_view;
import com.quarter_hour.modle.Register_modle;

import java.util.Map;

public class MyRegister_presenter implements Register_presenter {
    Register_view register_view;
    Context context;
    Register_modle register_modle;

    public MyRegister_presenter(Register_view register_view, Context context) {
        this.register_view = register_view;
        this.context = context;
    }

    @Override
    public void onRevice(Object o) {
        register_view.onSuccess(o);
    }

    public void newRegister(Map<String, String> map) {
        register_modle = new Register_modle(context,this);
        register_modle.getRegister(map);
    }

    /**
     * 释放
     */
    public void shifang() {
        if (register_view!=null){
            register_view = null;
        }
    }
}
