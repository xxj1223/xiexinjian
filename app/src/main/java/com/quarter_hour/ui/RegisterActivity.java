package com.quarter_hour.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quarter_hour.R;
import com.quarter_hour.base.BaseActivity;
import com.quarter_hour.bean.LoginBean;
import com.quarter_hour.bean.Message;
import com.quarter_hour.ev.Event_register;
import com.quarter_hour.inter.Register_view;
import com.quarter_hour.presenter.MyRegister_presenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,Register_view {
    private ImageView register_img;
    private TextView register_back;
    private EditText register_name;
    private EditText register_pass;
    private Button btn_register;
    private TextView visitor_register;
    MyRegister_presenter myRegister_presenter;
    public static final String MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    public static final String PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    @Override
    public void intView() {
        setContentView(R.layout.activity_register);
        register_img = findViewById(R.id.register_img);
        register_back = findViewById(R.id.register_back);
        register_name = findViewById(R.id.register_name);
        register_pass = findViewById(R.id.register_pass);
        btn_register = findViewById(R.id.btn_register);
        visitor_register = findViewById(R.id.visitor_register);
    }

    @Override
    public void intData() {
        register_img.setOnClickListener(this);
        register_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        visitor_register.setOnClickListener(this);
        myRegister_presenter = new MyRegister_presenter(this,RegisterActivity.this);
    }

    @Override
    public void intUtils() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                //返回
            case R.id.register_img:
                startActivity(new Intent(RegisterActivity.this,Phone_loginActivity.class));
                overridePendingTransition(R.anim.out_fan, R.anim.in_fan);
                finish();
                break;
                //已有账号
            case R.id.register_back:
                startActivity(new Intent(RegisterActivity.this,Phone_loginActivity.class));
                overridePendingTransition(R.anim.out_fan, R.anim.in_fan);
                finish();
                break;
                //注册
            case R.id.btn_register:
                String mobile = register_name.getText().toString();
                String pass = register_pass.getText().toString();
                boolean mol = Pattern.matches(MOBILE, mobile);
                boolean pwd = Pattern.matches(PASSWORD, pass);
                if (mol){
                    if (!pwd){
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }else {
                        //传值到登录页面
                        Event_register event_register = new Event_register();
                        event_register.setName(mobile);
                        event_register.setPass(pass);
                        EventBus.getDefault().postSticky(event_register);
                        //拼接参数
                        Map<String,String> map = new HashMap<>();
                        map.put("mobile",mobile);
                        map.put("password",pass);
                        map.put("regType","0");
                        myRegister_presenter.newRegister(map);
                    }
                }else {
                    Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                }
                break;
                //游客登录
            case R.id.visitor_register:
                startActivity(new Intent(RegisterActivity.this,ShowActivity.class));
                finish();
                break;
        }
    }

    /**
     * 成功
     * @param o
     */
    @Override
    public void onSuccess(Object o) {
        Message<LoginBean> register = (Message<LoginBean>) o;
        if (Integer.parseInt(register.getCode()) == 0){
            //跳转到数据登录页面
            startActivity(new Intent(RegisterActivity.this,Phone_loginActivity.class));
            overridePendingTransition(R.anim.out_fan, R.anim.in_fan);
        }
        String msg = register.getMsg();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 失败
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRegister_presenter.shifang();
    }
}
