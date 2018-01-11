package com.mouldandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mouldandroid.activity.loading.StartEntity;

/**
 * Created by Administrator on 2018/1/11.
 */

public class SharedPreferencesUtils {

    private static Context context;
    private static Gson gson;

    public SharedPreferencesUtils(Context context){
        super();
        this.context = context;
        gson = new Gson();
    }

    public SharedPreferences getPreferences(){
        //MODE_PRIVATE：为默认操作模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
        return context.getSharedPreferences("start_date", Context.MODE_PRIVATE);
    }

    public Editor getEditor(){
        return getPreferences().edit();
    }

    public StartEntity getStartEntity(){
        StartEntity startEntity = null;
        String startinfo = getPreferences().getString("start_url","");
        if (!TextUtils.isEmpty(startinfo)){
            startEntity = gson.fromJson(startinfo,StartEntity.class);
        }
        return startEntity;
    }

    public boolean setStartEntity(StartEntity startEntity){
        String startinfo = "";
        if (null != startEntity){
            startinfo = gson.toJson(startEntity,StartEntity.class);
        }
        return getEditor().putString("start_url",startinfo).commit();
    }

    //清除
    public boolean emptyStartEntity(StartEntity startEntity){
        String startinfo = "";
        if (null != startEntity){
            if (null != startEntity){
                startinfo = gson.toJson(startEntity,StartEntity.class);
            }
        }
        return getEditor().putString("start_url",startinfo).clear().commit();
    }
}
