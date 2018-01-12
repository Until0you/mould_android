package com.mouldandroid.activity.logo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mouldandroid.R;
import com.mouldandroid.entity.StartEntity;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.SharedPreferencesUtils;

import org.xutils.image.ImageOptions;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private String img_url = "";

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

//        postData();

        retrofitPost();

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
        Intent intent = new Intent(LogoActvity.this,StartActivity.class);
        intent.putExtra("img_url",img_url);
        startActivity(intent);
        finish();
    }

    private void retrofitPost(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        Call<StartEntity> call = apiServer.getStartUrl("gif");
        call.enqueue(new retrofit2.Callback<StartEntity>() {
            @Override
            public void onResponse(Call<StartEntity> call, Response<StartEntity> response) {
                img_url = response.body().getAd_img();
            }

            @Override
            public void onFailure(Call<StartEntity> call, Throwable t) {

            }
        });

    }
}
