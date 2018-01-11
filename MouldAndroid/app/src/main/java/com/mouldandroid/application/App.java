package com.mouldandroid.application;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

import java.util.Map;

/**
 * Created by Administrator on 2018/1/5.
 */

public class App extends Application {

    // 用于存放倒计时时间
    public static Map<String, Long> map;
    public Context context;
    private String login_tag;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        x.Ext.init(this); // XUtils初始化
    }
}
