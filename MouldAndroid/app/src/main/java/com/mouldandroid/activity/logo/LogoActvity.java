package com.mouldandroid.activity.logo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mouldandroid.R;
import com.mouldandroid.activity.loading.StartEntity;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.SharedPreferencesUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Administrator on 2018/1/5.
 */

public class LogoActvity extends AppCompatActivity implements Animation.AnimationListener {

    private ImageView logo_icon;

    private Animation animation;
    private static final int SUCCESS = 0;
    private StartEntity startEntity;
    private Gson gson;
    private SharedPreferencesUtils spUtils;
    private ImageOptions options;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        setContentView(R.layout.logo_layout);
        initView();
        initData();
    }

    private void initView(){
        logo_icon = findViewById(R.id.logo_icon);
    }

    private void initData() {
        spUtils = new SharedPreferencesUtils(this);
        postData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                animation = new ScaleAnimation(1.0f, 1.2f, 1.0f,
                        1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(3000);
                animation.setFillAfter(true);
                animation.setAnimationListener(LogoActvity.this);
                handler.sendEmptyMessage(SUCCESS);
            }
        }).start();
    }

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    logo_icon.startAnimation(animation);   //有问题
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation) {}
    @Override
    public void onAnimationEnd(Animation animation) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //结束之后的操作
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }

    private void postData(){
        RequestParams params = new RequestParams(ConstValues.STARTUP);
        params.addBodyParameter("ad","gif");
        x.http().post(params,new MyCallBack());
    }

    private class MyCallBack implements Callback.CommonCallback<String>{
        @Override
        public void onSuccess(String s) {
            if (!TextUtils.isEmpty(s)){
                gsonDispose(s);
            }
        }
        @Override
        public void onError(Throwable throwable, boolean b) {}
        @Override
        public void onCancelled(CancelledException e) {}
        @Override
        public void onFinished() {}
    }

    //处理解析
    private void gsonDispose(String result) {
        gson = new Gson();
        startEntity = gson.fromJson(result, StartEntity.class);
        spUtils.setStartEntity(startEntity);   //保存到SharedPreferences
    }

    private void postDataTwo(){

    }

}
