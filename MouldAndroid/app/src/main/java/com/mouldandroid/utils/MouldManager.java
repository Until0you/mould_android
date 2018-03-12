package com.mouldandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mouldandroid.entity.User;

/**
 * Created by Administrator on 2018/1/11.
 */

public class MouldManager {

    private static Context context;
    private static Gson gson;
    private static final String USER_TOKEN = "userToken";

    public MouldManager(Context context){
        super();
        this.context = context;
        gson = new Gson();
    }

    public SharedPreferences getPreferences(){
        //MODE_PRIVATE：为默认操作模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
        return context.getSharedPreferences("start_date", Context.MODE_PRIVATE);
    }

    /**
     * 	得到用户存储的帐号
     * @return
     */
    public String getUserAccount(){
        return getPreferences().getString(USER_TOKEN, "");
    }

    /**
     * @param userToken
     * 			存储用户存储的帐号
     * @return
     */
    public boolean setUserAccount(String userToken){
        return getEditor().putString(USER_TOKEN, userToken).commit();
    }

    public boolean emptyUserAccount(String userToken){
        return getEditor().putString(USER_TOKEN,userToken).clear().commit();
    }

    public Editor getEditor(){
        return getPreferences().edit();
    }

    public User getUser(){
        User user = null;
        String startinfo = getPreferences().getString("user","");
        if (!TextUtils.isEmpty(startinfo)){
            user = gson.fromJson(startinfo,User.class);
        }
        return user;
    }

    //保存
    public boolean setUser(User user){
        String userinfo = "";
        if (null != user){
            userinfo = gson.toJson(user,User.class);
        }
        return getEditor().putString("user",userinfo).commit();
    }

    //清除
    public boolean emptyUser(User user){
        String userinfo = "";
        if (null != user){
            if (null != user){
                userinfo = gson.toJson(user,User.class);
            }
        }
        return getEditor().putString("user",userinfo).clear().commit();
    }
}
