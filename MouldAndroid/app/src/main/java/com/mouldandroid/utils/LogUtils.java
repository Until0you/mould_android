package com.mouldandroid.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/7/14.
 */

public  class LogUtils {

    public static void e(Object object){
        Log.e("output", String.valueOf(object));
    }

    public static void i(Object object){
        Log.i("output", String.valueOf(object));
    }

    public static void d(Object object){
        Log.d("output", String.valueOf(object));
    }

    public static void v(Object object){
        Log.v("output", String.valueOf(object));
    }

    public static void w(Object object){
        Log.w("output", String.valueOf(object));
    }

}
