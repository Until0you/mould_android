package com.mouldandroid.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mouldandroid.utils.MouldManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public View rootView;
    private Unbinder mUnbinder;
    protected static String android_ID;
    protected MouldManager mouldManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mouldManager = new MouldManager(this);
        android_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        rootView = LayoutInflater.from(this).inflate(setView(), null);
        setContentView(rootView);
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int setView();
    /** 初始化控件 */
    protected abstract void initView();
    /** 初始化数据 */
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    /**
     * 点击EditText外的地方软键盘隐藏
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                //第一种隐藏方式
                hideSoftinput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     * @param v
     * @param event
     *MoneyRecordActivity.java
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 多种隐藏软键盘方式中的一种
     * @param token
     *TODO
     *MoneyRecordActivity.java
     */
    public void hideSoftinput(IBinder token){
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
