package com.quarter_hour.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quarter_hour.R;
import com.quarter_hour.base.BaseActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreationActivity extends BaseActivity {
    private TextView text_cancle;
    private CircleImageView video_img;
    private ImageView intension_img;
    private int VIDEO_WITH_CAMERA = 0X123;
    @Override
    public void intView() {
        setContentView(R.layout.activity_creation);
        text_cancle = findViewById(R.id.text_cancel);
        video_img = findViewById(R.id.video_img);
        intension_img = findViewById(R.id.intension_img);
    }

    @Override
    public void intData() {
        //返回
        text_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发布视频
        video_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                //设置视频录制的最长时间
                intent.putExtra (MediaStore.EXTRA_DURATION_LIMIT,30);
                //设置视频录制的画质
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult (intent, VIDEO_WITH_CAMERA);
            }
        });
        //发布段子
        intension_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreationActivity.this,Publish_articleActivity.class));
            }
        });
    }

    @Override
    public void intUtils() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_WITH_CAMERA){
                Uri uri = data.getData();
                Log.e("TAG", "onActivityResult: " + uri.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
