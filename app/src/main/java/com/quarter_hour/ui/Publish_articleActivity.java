package com.quarter_hour.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quarter_hour.R;
import com.quarter_hour.base.BaseActivity;
import com.quarter_hour.bean.AppearBean;
import com.quarter_hour.inter.Appear_view;
import com.quarter_hour.presenter.MyAppear_presenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Publish_articleActivity extends BaseActivity implements View.OnClickListener,Appear_view {
    private TextView cancel;
    private TextView text_appear;
    private TextView edit_appear;
    private ImageView tinajia_img;
    private LinearLayout btn_TA;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    MyAppear_presenter myAppear_presenter;
    @Override
    public void intView() {
        setContentView(R.layout.activity_publish_article);
        cancel = findViewById(R.id.cancel);
        text_appear = findViewById(R.id.text_appear);
        edit_appear = findViewById(R.id.edit_appear);
        tinajia_img = findViewById(R.id.tinajia_img);
        btn_TA = findViewById(R.id.btn_TA);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        myAppear_presenter = new MyAppear_presenter(this,this);
    }

    @Override
    public void intData() {
        cancel.setOnClickListener(this);
        text_appear.setOnClickListener(this);
        tinajia_img.setOnClickListener(this);
        btn_TA.setOnClickListener(this);
    }

    @Override
    public void intUtils() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
                //发表文章
            case R.id.text_appear:
                String content = edit_appear.getText().toString();
                Map<String,String> map = new HashMap<>();
                map.put("uid","13850");
                map.put("token","D47DC75915E48F2D19F787FF90A41C39");
                map.put("content",content);
                map.put("source","android");
                map.put("appVersion","1");
                myAppear_presenter.newAppear(map);
                finish();
                break;
                //添加照片
            case R.id.tinajia_img:
                showChoosePicDialog();
                break;
                //@别人
            case R.id.btn_TA:
                Toast.makeText(this, "@TA", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.t1);
        builder.setTitle("添加图片");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bitmap photo = null;
        Bundle extras = data.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            if (img1==null){
                img1.setVisibility(View.VISIBLE);
                img1.setImageBitmap(photo);
            }
            if (img1 !=null || img2==null){
                img2.setVisibility(View.VISIBLE);
                img2.setImageBitmap(photo);
            }
            //   uploadPic(photo);
        }

    }

    /**
     * 成功
     * @param o
     */
    @Override
    public void onSuccess(Object o) {
        AppearBean appearBean = (AppearBean) o;
        String msg = appearBean.getMsg();
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
        myAppear_presenter.shifang();
    }
}
