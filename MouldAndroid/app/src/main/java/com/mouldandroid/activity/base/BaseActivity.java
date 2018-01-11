package com.mouldandroid.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public View rootView;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = LayoutInflater.from(this).inflate(setView(), null);
        setContentView(rootView);
        mUnbinder = ButterKnife.bind(this);
        initData();
    }

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int setView();
    /** 初始化控件 */
    public void initView(){};
    /** 初始化数据 */
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
