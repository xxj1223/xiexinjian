package com.quarter_hour.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
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
import com.quarter_hour.ev.Event_login;
import com.quarter_hour.ev.Event_register;
import com.quarter_hour.inter.Login_view;
import com.quarter_hour.presenter.MyLogin_presenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Phone_loginActivity extends BaseActivity implements View.OnClickListener,Login_view {
    private ImageView back_img;
    private TextView text_register;
    private EditText login_name;
    private EditText login_pass;
    private Button btn_login;
    private TextView login_wj;
    private TextView visitor_login;
    MyLogin_presenter myLogin_presenter;
    public static final String MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    public static final String PASSWORD = "^[a-zA-Z0-9]{6,20}$";
    private SharedPreferences all_name;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        all_name = getSharedPreferences("all_name", MODE_PRIVATE);
        editor = all_name.edit();
    }

    @Override
    public void intView() {
        setContentView(R.layout.activity_phone_login);
        back_img = findViewById(R.id.back_img);
        text_register = findViewById(R.id.text_register);
        login_name = findViewById(R.id.login_name);
        login_pass = findViewById(R.id.login_pass);
        btn_login = findViewById(R.id.btn_login);
        login_wj = findViewById(R.id.login_wj);
        visitor_login = findViewById(R.id.visitor_login);
        myLogin_presenter = new MyLogin_presenter(this,Phone_loginActivity.this);
    }

    @Override
    public void intData() {
        back_img.setOnClickListener(this);
        text_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        login_wj.setOnClickListener(this);
        visitor_login.setOnClickListener(this);
    }

    @Override
    public void intUtils() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                //返回
            case R.id.back_img:
                finish();
                break;
                //注册账号
            case R.id.text_register:
                startActivity(new Intent(Phone_loginActivity.this,RegisterActivity.class));
                overridePendingTransition(R.anim.in, R.anim.out);
                finish();
                break;
                //登录
            case R.id.btn_login:
                String mobile = login_name.getText().toString();
                String pass = login_pass.getText().toString();
                boolean mol = Pattern.matches(MOBILE, mobile);
                boolean pwd = Pattern.matches(PASSWORD, pass);
                if (mol){
                    if (!pwd){
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }else {
                        //保存
                        editor.putInt("dl_img", R.drawable.oneleft);
                        editor.putString("dl_name", mobile);
                        editor.putString("dl_pass", pass);
                        editor.putBoolean("check", true);
                        editor.commit();
                        //传值到主页面
                        Event_login event_login = new Event_login();
                        event_login.setName(mobile);
                        event_login.setIcon(R.drawable.oneleft);
                        EventBus.getDefault().postSticky(event_login);
                        //拼接参数
                        Map<String,String> map = new HashMap<>();
                        map.put("mobile",mobile);
                        map.put("password",pass);
                        myLogin_presenter.newLogin(map);
                    }
                }else {
                    Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                }
                break;
                //忘记密码
            case R.id.login_wj:
                Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
                //游客登录
            case R.id.visitor_login:
                startActivity(new Intent(Phone_loginActivity.this,ShowActivity.class));
                finish();
                break;
        }
    }

    /**
     * 接收注册页面的值
     */
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onRevice(Event_register event_register){
        login_name.setText(event_register.getName());
        login_pass.setText(event_register.getPass());
    }

    /**
     * 成功
     * @param o
     */
    @Override
    public void onSuccess(Object o) {
        Message<LoginBean> login = (Message<LoginBean>) o;
        if (Integer.parseInt(login.getCode()) == 0){
            //跳转到数据展示页面
            startActivity(new Intent(Phone_loginActivity.this,ShowActivity.class));
        }
        String msg = login.getMsg();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //保存uid token appkey appsecret
        int uid = login.getData().getUid();
        String token = login.getData().getToken();
        String appkey = login.getData().getAppkey();
        String appsecret = login.getData().getAppsecret();
        editor.putString("appkey",appkey);
        editor.putString("appsecret",appsecret);
        editor.putInt("uid", uid);
        editor.putString("token", token);
        editor.commit();
    }

    /**
     *
     * 失败
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        myLogin_presenter.shifang();
    }
}
