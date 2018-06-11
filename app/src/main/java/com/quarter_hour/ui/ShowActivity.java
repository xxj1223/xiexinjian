package com.quarter_hour.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quarter_hour.R;
import com.quarter_hour.base.BaseActivity;
import com.quarter_hour.ev.Event_login;
import com.quarter_hour.ev.Events;
import com.quarter_hour.fragment.An_Episode_02;
import com.quarter_hour.fragment.Photos_04;
import com.quarter_hour.fragment.Recommend_01;
import com.quarter_hour.fragment.Video_03;
import com.quarter_hour.ui.view.MyClickButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, RadioGroup.OnCheckedChangeListener {
    private DrawerLayout ll;
    private RelativeLayout right;
    private NavigationView left;
    private boolean isDrawer=false;
    private FrameLayout fragment_container;
    private RadioGroup group;
    private List<Fragment> list;
    private Recommend_01 recommend_01;
    private An_Episode_02 an_episode_02;
    private Video_03 video_03;
    private Photos_04 photos_04;
    private FragmentManager manager;
    private MyClickButton myClickButton;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                ll.setVisibility(View.VISIBLE);
            }
        }
    };
    private NavigationView nav_view;
    private CircleImageView qq_xiao;
    private TextView show_head;
    private ImageView update_xiu;
    private CircleImageView qq_img;
    private TextView qq_name;
    //保存
    private SharedPreferences allname;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void intView() {
        setContentView(R.layout.activity_show);
        ll = findViewById(R.id.ll);
        qq_xiao = findViewById(R.id.qq_xiao);
        nav_view = findViewById(R.id.nav_view);
        fragment_container = findViewById(R.id.fragment_container);
        group = findViewById(R.id.group);
        myClickButton = findViewById(R.id.myClickButton);
        show_head = findViewById(R.id.show_head);
        update_xiu = findViewById(R.id.update_xiu);
        qq_img = findViewById(R.id.qq_img);
        qq_name = findViewById(R.id.qq_name);
        //qq
        allname = this.getSharedPreferences("all_name", MODE_PRIVATE);
        editor = allname.edit();

        boolean check = allname.getBoolean("check", false);
        if (check){
            int icon = allname.getInt("dl_img", R.drawable.oneleft);
            qq_img.setImageResource(icon);
            qq_xiao.setImageResource(icon);
            String mobile = allname.getString("dl_name", "");
            String dl_pass = allname.getString("dl_pass", "");
            qq_name.setText(mobile);
            editor.putBoolean("qqcheck",false);
            editor.commit();
        }else {
            boolean qqcheck = allname.getBoolean("qqcheck", false);
            if (qqcheck){
                String qq_names = allname.getString("qq_name","");
                String qq_icons = allname.getString("qq_img","");
                qq_name.setText(qq_names);
                Glide.with(this).load(qq_icons).into(qq_img);
                Glide.with(this).load(qq_icons).into(qq_xiao);
                editor.putBoolean("check",false);
                editor.commit();
            }
        }
        //登录页面
        qq_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowActivity.this,LoginActivity.class));
            }
        });
        //修改
        update_xiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowActivity.this,CreationActivity.class));
            }
        });

    }
    @Override
    public void intUtils() {

    }

    @Override
    public void intData() {
        list = new ArrayList<>();
        recommend_01 = new Recommend_01();
        an_episode_02 = new An_Episode_02();
        video_03 = new Video_03();
        photos_04 = new Photos_04();
        list.add(recommend_01);
        list.add(an_episode_02);
        list.add(video_03);
        list.add(photos_04);
        //默认显示第一个
        smartFragmentReplace(R.id.fragment_container,list.get(0));
        group.setOnCheckedChangeListener(this);
        qq_xiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.openDrawer(nav_view);
            }
        });
        //设置夜间模式
        myClickButton.setOnMbClickListener(new MyClickButton.OnMClickListener() {
            @Override
            public void onClick(boolean isRight) {
                if (isRight){
                    Toast.makeText(ShowActivity.this,"夜间",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ShowActivity.this,"日间",Toast.LENGTH_SHORT).show();
                }

            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(ShowActivity.this);
        progressDialog.setMessage("正在加载中，请稍等......");
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setCancelable(false);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    progressDialog.dismiss();
                    handler.sendEmptyMessageDelayed(0,0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ll);
        right = findViewById(R.id.right);
        left = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(isDrawer){
                    return left.dispatchTouchEvent(motionEvent);
                }else{
                    return false;
                }
            }
        });
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                right.layout(left.getRight(), 0, left.getRight() + display.getWidth(), display.getHeight());
            }
            @Override
            public void onDrawerOpened(View drawerView) {

            }
            @Override
            public void onDrawerClosed(View drawerView) {

            }
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ll);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            //我的关注
            Toast.makeText(this, "我的关注", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            //我的收藏
            Toast.makeText(this, "我的收藏", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {
            //搜索好友
            Toast.makeText(this, "搜索好友", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            //消息通知
            Toast.makeText(this, "消息通知", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_wen) {
            //文件
            Toast.makeText(this, "文件", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_she) {
            //设置
            Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.ll);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.btn_1:
                smartFragmentReplace(R.id.fragment_container,list.get(0));
                show_head.setText("推荐");
                break;
            case R.id.btn_2:
                smartFragmentReplace(R.id.fragment_container,list.get(1));
                show_head.setText("段子");
                break;
            case R.id.btn_3:
                smartFragmentReplace(R.id.fragment_container,list.get(2));
                show_head.setText("视频");
                break;
            case R.id.btn_4:
                smartFragmentReplace(R.id.fragment_container,list.get(3));
                show_head.setText("趣图");
                break;
        }
    }

    /**
     * qq登录接收
     * @param events
     */
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onQQjieshou(Events events){
        Log.i("HHH","-----------"+events.getQq_name());
        qq_name.setText(events.getQq_name());
        Glide.with(this).load(events.getIconurl()).into(qq_img);
        Glide.with(this).load(events.getIconurl()).into(qq_xiao);
    }

    /**
     * 手机号登录接收
     */
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onRevice(Event_login event_login){
        qq_name.setText(event_login.getName());
        Glide.with(this).load(event_login.getIcon()).into(qq_img);
        Glide.with(this).load(event_login.getIcon()).into(qq_xiao);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
