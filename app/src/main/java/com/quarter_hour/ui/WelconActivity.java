package com.quarter_hour.ui;

import android.content.Intent;
import android.os.Handler;

import com.quarter_hour.R;
import com.quarter_hour.base.BaseActivity;


public class WelconActivity extends BaseActivity {
    @Override
    public void intView() {
        setContentView(R.layout.activity_welcon);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void intUtils() {

    }

    @Override
    public void intData() {

    }
}
