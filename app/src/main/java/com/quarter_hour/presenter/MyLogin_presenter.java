package com.quarter_hour.presenter;

import android.content.Context;

import com.quarter_hour.inter.Login_view;
import com.quarter_hour.modle.Login_modle;

import java.util.Map;

public class MyLogin_presenter implements Login_presenter {
    Login_view login_view;
    Context context;
    Login_modle login_modle;

    public MyLogin_presenter(Login_view login_view, Context context) {
        this.login_view = login_view;
        this.context = context;
    }

    @Override
    public void onRevice(Object o) {
        login_view.onSuccess(o);
    }

    public void newLogin(Map<String, String> map) {
        login_modle = new Login_modle(context,this);
        login_modle.getLogin(map);
    }

    /**
     * 释放
     */
    public void shifang() {
        if (login_view!=null){
            login_view = null;
        }
    }
}
