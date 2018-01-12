package com.mouldandroid.activity.logo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mouldandroid.R;
import com.mouldandroid.activity.base.BaseActivity;
import com.mouldandroid.activity.main.MainActivity;

import org.xutils.image.ImageOptions;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/5.
 */

public class StartActivity extends BaseActivity{

    @BindView(R.id.start_icon)
    public ImageView start_icon;

//    @BindView(R.id.start_gif)
//    public PowerImageView start_gif;

    private ImageOptions options;
    private long duration;
    private final int MESSAGE_SUCCESS = 1;

    @Override
    protected int setView() {
        return R.layout.start_layout;
    }

    @Override
    protected void initData() {

        String img_url = getIntent().getStringExtra("img_url");
        options = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.logo) // 加载中默认显示图片
                .setFailureDrawableId(R.mipmap.logo) // 加载失败后默认显示图片
                .setUseMemCache(false)
                .build();
//        x.image().bind(start_gif,img_url,options);
//        Glide.with(this).load(img_url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(start_icon);
        Glide.with(this)
                .load(img_url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
                        GifDrawable drawable = (GifDrawable) resource;
                        GifDecoder decoder = drawable.getDecoder();
                        for (int i = 0; i < drawable.getFrameCount(); i++) {
                            duration += decoder.getDelay(i);
                        }
                        //发送延时消息，通知动画结束
                        handler.sendEmptyMessageDelayed(MESSAGE_SUCCESS,
                                duration);
                        return false;
                    }
                })
                .into(start_icon);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_SUCCESS:
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
