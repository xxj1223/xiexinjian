package com.quarter_hour.ui.view;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HUADONG extends NavigationView {
    public HUADONG(Context context) {
        super(context);
        //this.setFocusable(true);//允许获取上层焦点
    }

    public HUADONG(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HUADONG(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写这个方法，并且在方法里面请求所有的父控件都不要拦截他的事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean isFocused() {
        return super.isFocused();
    }
}
