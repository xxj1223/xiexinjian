package com.quarter_hour.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quarter_hour.R;
import com.quarter_hour.base.BaseActivity;
import com.quarter_hour.ev.Events;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back_img;
    private TextView weixinlogin_img;
    private TextView qqlogin_img;
    private TextView text_login;
    private Tencent mTencent;
    private UserInfo mUserInfo;
    private BaseUiListener mIUiListener;
    private static final String APP_I = "1105602574";
    //轻量级存储
    private SharedPreferences allname;
    private SharedPreferences.Editor editor;
    @Override
    public void intView() {
        setContentView(R.layout.activity_login);
        back_img = findViewById(R.id.back_img);
        weixinlogin_img = findViewById(R.id.weixinlogin_img);
        qqlogin_img = findViewById(R.id.qqlogin_img);
        text_login = findViewById(R.id.text_login);
        mTencent = Tencent.createInstance(APP_I, getApplicationContext());
    }

    @Override
    public void intData() {
        back_img.setOnClickListener(this);
        weixinlogin_img.setOnClickListener(this);
        qqlogin_img.setOnClickListener(this);
        text_login.setOnClickListener(this);
        allname = getSharedPreferences("all_name", MODE_PRIVATE);
        editor = allname.edit();
    }

    @Override
    public void intUtils() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.weixinlogin_img:
                Toast.makeText(this, "微信登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.qqlogin_img:
                mTencent.login(LoginActivity.this, "all", new BaseUiListener());
                break;
            case R.id.text_login:
                startActivity(new Intent(LoginActivity.this,Phone_loginActivity.class));
                overridePendingTransition(R.anim.in, R.anim.out);
                break;
        }
    }
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            JSONObject obj = (JSONObject) response;
            try {
                final String openID = obj.getString("openid");
                final String accessToken = obj.getString("access_token");
                final String expires = obj.getString("expires_in");

                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e("tostring", response.toString());
                        if (response==null){
                            return;
                        }
                        try{
                            JSONObject jo = (JSONObject) response;
                            String name = jo.getString("nickname");
                            String img = jo.getString("figureurl_1");
                            //赋值
                            Events events = new Events();
                            events.setQq_name(name);
                            events.setIconurl(img);
                            Log.i("HHH", "-----------------"+name);
                            EventBus.getDefault().postSticky(events);
                            //保存
                            editor.putString("qq_name",name);
                            editor.putString("qq_img",img);
                            editor.putBoolean("qqcheck",true);
                            editor.putBoolean("check",false);
                            editor.commit();

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        finish();
                        Log.e("ssssss", "登录成功" + response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e("ssssss", "登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e("ssssss", "登录取消");
                    }
                });

                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //qq登录回调
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
