package com.mouldandroid.activity.loading;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mouldandroid.activity.logo.LogoActvity;


/**
 * Created by Administrator on 2018/1/5.
 */

public class Loading extends AppCompatActivity {

    private static final int LOADING_FIRST_TAG = 0;
    private static final int LOADING_DELAY_TIME = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

   private void initData(){
       handler.sendEmptyMessageDelayed(LOADING_FIRST_TAG,LOADING_DELAY_TIME);
   }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LOADING_FIRST_TAG){
                Intent intent = new Intent(Loading.this,LogoActvity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
