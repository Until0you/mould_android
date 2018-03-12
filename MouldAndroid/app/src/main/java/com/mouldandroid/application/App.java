package com.mouldandroid.application;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;

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
//        Config.DEBUG = true;
//        UMShareAPI.get(this);
    }

    /** 各个平台的配置，建议放在全局Application或者程序入口  */
    {
        PlatformConfig.setWeixin("wx4b2c4b427850aa68","e38f228cd6071d664fd0945f4f8fdd1c");
        PlatformConfig.setQQZone("1105506045","STDCZSqe5Ay6n49M");
        PlatformConfig.setSinaWeibo("","","");
    }
}
