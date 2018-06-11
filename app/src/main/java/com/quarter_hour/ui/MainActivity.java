package com.quarter_hour.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quarter_hour.R;
import com.quarter_hour.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private RelativeLayout re;

    @Override
    public void intView() {
        setContentView(R.layout.activity_main);
        re = findViewById(R.id.re);
    }

    @Override
    public void intUtils() {

    }

    @Override
    public void intData() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(re, "alpha",1f);
        animator.setDuration(1000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(MainActivity.this,ShowActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
