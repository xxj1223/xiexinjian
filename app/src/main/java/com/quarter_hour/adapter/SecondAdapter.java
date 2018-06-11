package com.quarter_hour.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quarter_hour.R;
import com.quarter_hour.bean.DianZanBean;
import com.quarter_hour.bean.Second_DataBean;
import com.quarter_hour.inter.DianZan_view;
import com.quarter_hour.presenter.MyDianZan_presenter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SecondAdapter extends RecyclerView.Adapter implements DianZan_view {
    private boolean isMenuOpen = false;
    private Context context;
    private List<Second_DataBean> list;
    MyDianZan_presenter myDianZan_presenter;
    //分享的网址
    String url = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2755166084,3804723034&fm=27&gp=0.jpg";
    private String title;
    private float price;
    private String icon;
    private String pid;

    public SecondAdapter(Context context, List<Second_DataBean> list) {
        this.context = context;
        this.list = list;
        myDianZan_presenter = new MyDianZan_presenter(this);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.episode_list,null);
        Second_Holder second_holder = new Second_Holder(view);
        return second_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Second_Holder second_holder = (Second_Holder) holder;
        //进行赋值
        String nickname = (String) list.get(position).getUser().getNickname();
        String imgUrls = (String) list.get(position).getUser().getIcon();
        ((Second_Holder) holder).second_name.setText(nickname);
        if (nickname==null){
            ((Second_Holder) holder).second_name.setText("@敷衍");
        }
        //判断img
        if (imgUrls != null){
            Glide.with(context).load(imgUrls).into(((Second_Holder) holder).second_img);
        }else {
            ((Second_Holder) holder).second_img.setImageResource(R.drawable.bear);
        }

        ((Second_Holder) holder).second_time.setText(list.get(position).getCreateTime());
        ((Second_Holder) holder).second_title.setText("\u3000\u3000"+list.get(position).getContent());

        //加号动态效果
        ((Second_Holder) holder).second_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (isMenuOpen){
                   ((Second_Holder) holder).second_jia.setImageResource(R.drawable.jian);
                   ObjectAnimator rotate = ObjectAnimator.ofFloat( ((Second_Holder) holder).second_jia, "rotation", 0, 180).setDuration(300);
                   rotate.start();
                   //平移动画
                   ObjectAnimator translation1 = ObjectAnimator.ofFloat( ((Second_Holder) holder).pl_img, "translationX", 0, -120).setDuration(500);
                   translation1.start();
                   ObjectAnimator alpha1 = ObjectAnimator.ofFloat(((Second_Holder) holder).pl_img,"alpha",0f,1f);
                   alpha1.start();
                   ((Second_Holder) holder).pl_img.setVisibility(View.VISIBLE);

                   ObjectAnimator translation2 = ObjectAnimator.ofFloat( ((Second_Holder) holder).fx_img, "translationX", 0, -220).setDuration(400);
                   translation2.start();
                   ObjectAnimator alpha2 = ObjectAnimator.ofFloat(((Second_Holder) holder).fx_img,"alpha",0f,1f);
                   alpha2.start();
                   ((Second_Holder) holder).fx_img.setVisibility(View.VISIBLE);

                   ObjectAnimator translation3 = ObjectAnimator.ofFloat( ((Second_Holder) holder).dz_img, "translationX", 0, -310).setDuration(300);
                   translation3.start();
                   ((Second_Holder) holder).dz_img.setVisibility(View.VISIBLE);

                   isMenuOpen = false;

               }else {
                   ((Second_Holder) holder).second_jia.setImageResource(R.drawable.jia);

                   ObjectAnimator t1 = ObjectAnimator.ofFloat( ((Second_Holder) holder).pl_img, "translationX", -120, 0);
                   t1.setDuration(300);
                   t1.start();
                   ObjectAnimator a1 = ObjectAnimator.ofFloat(((Second_Holder) holder).pl_img,"alpha",1f,0f);
                   a1.start();


                   ObjectAnimator t2 = ObjectAnimator.ofFloat( ((Second_Holder) holder).fx_img, "translationX", -220, 0);
                   t2.setDuration(400);
                   t2.start();
                   ObjectAnimator alpha2 = ObjectAnimator.ofFloat(((Second_Holder) holder).fx_img,"alpha",1f,0f);
                   alpha2.start();


                   ObjectAnimator t3 = ObjectAnimator.ofFloat( ((Second_Holder) holder).dz_img, "translationX", -310, 0);
                   t3.setDuration(500);
                   t3.start();

                   ((Second_Holder) holder).dz_img.setVisibility(View.GONE);
                   ((Second_Holder) holder).fx_img.setVisibility(View.GONE);
                   isMenuOpen = true;
               }
               //点赞
               ((Second_Holder) holder).dz_img.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if (!isMenuOpen){
                           ((Second_Holder) holder).dz_img.setImageResource(R.drawable.dz2);
                           Toast.makeText(context, "已点赞", Toast.LENGTH_SHORT).show();
                           //map集合拼接参数
                           int jid = list.get(position).getJid();
                           Map<String,String> map = new HashMap<>();
                           map.put("uid","13850");
                           map.put("jid",String.valueOf(jid));
                           map.put("token","D47DC75915E48F2D19F787FF90A41C39");
                           map.put("source","android");
                           map.put("appVersion","1");
                           myDianZan_presenter.newWork(map);
                           //isMenuOpen = true;
                           if (false){
                               ((Second_Holder) holder).dz_img.setImageResource(R.drawable.dz2);
                               Toast.makeText(context, "已点过赞", Toast.LENGTH_SHORT).show();
                           }
                       }
                   }
               });
               //分享
                ((Second_Holder) holder).fx_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        share();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     *成功
     * @param o
     */
    @Override
    public void onSuccess(Object o) {
        DianZanBean dianZanBean = (DianZanBean) o;
        Toast.makeText(context, dianZanBean.getMsg(), Toast.LENGTH_SHORT).show();

    }

    /**
     * 失败
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {

    }

    public class Second_Holder extends RecyclerView.ViewHolder {

        private CircleImageView second_img;
        private TextView second_name;
        private TextView second_time;
        private TextView second_title;
        private ImageView second_jia;
        private ImageView fx_img;
        private ImageView pl_img;
        private ImageView dz_img;
        public Second_Holder(View itemView) {
            super(itemView);
            second_img = itemView.findViewById(R.id.second_img);
            second_name = itemView.findViewById(R.id.second_name);
            second_time = itemView.findViewById(R.id.second_time);
            second_title = itemView.findViewById(R.id.second_title);
            second_jia = itemView.findViewById(R.id.second_jia);
            fx_img = itemView.findViewById(R.id.fx_img);
            pl_img = itemView.findViewById(R.id.pl_img);
            dz_img = itemView.findViewById(R.id.dz_img);
        }
    }
    //QQ分享
    public void share() {
        UMImage thumb =  new UMImage(context,R.drawable.umeng_socialize_qq);
        UMWeb web = new UMWeb(url);
        web.setTitle("敷衍");//标题
        web.setThumb(thumb);
        web.setDescription("神奇的物种~二哈");//描述
        new ShareAction((Activity) context).withMedia(web).withText(title).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
    }
    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(context,"成功 了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(context,"失 败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(context,"取消了",Toast.LENGTH_LONG).show();

        }
    };
}
