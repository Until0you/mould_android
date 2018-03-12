package com.mouldandroid.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mouldandroid.R;
import com.mouldandroid.activity.login.LoginActivity;
import com.mouldandroid.activity.message.MessageActivity;
import com.mouldandroid.activity.myActivity.MyPersonalInfo;
import com.mouldandroid.activity.myinfo.MySettingsActivity;
import com.mouldandroid.base.BaseFragments;
import com.mouldandroid.entity.User;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/17.
 */

public class MyFragment extends BaseFragments implements View.OnClickListener{

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.my_settings)
    public RelativeLayout my_settings;
    @BindView(R.id.my_personal_info)
    public RelativeLayout my_personal_info;
    @BindView(R.id.tv_my_nick)
    public TextView tv_my_nick;

    @BindView(R.id.user_head_portrait)
    public ImageView user_head_portrait;
    @BindView(R.id.my_loing)
    public RelativeLayout my_loing;

    private User user;
    private Context context;

    @Override
    public int getLayoutResId() {
        return R.layout.my_fragment;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        context = getActivity();

        iv_back.setImageResource(R.mipmap.icon_dialogue);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("我的");

        iv_back.setOnClickListener(this);
        my_loing.setOnClickListener(this);
        my_settings.setOnClickListener(this);
        my_personal_info.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        user = mouldManager.getUser();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_personal_info:     //头像
                Intent it_persona = new Intent(context, MyPersonalInfo.class);
                startActivity(it_persona);
                break;
            case R.id.my_settings:          //设置
                Intent intent_settings = new Intent(context, MySettingsActivity.class);
                startActivity(intent_settings);
                break;
            case R.id.iv_back:          //消息列表
                Intent it_message = new Intent(context, MessageActivity.class);
                startActivity(it_message);
                break;
            case R.id.my_loing:
                Intent intent_login = new Intent(context, LoginActivity.class);
                startActivity(intent_login);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        user = mouldManager.getUser();
        if (null != user && null != user.getData()){
            tv_my_nick.setText(user.getData().getNick());       //设置昵称
            Glide.with(context).load(user.getData().getHeadimg()).into(user_head_portrait);     //设置头像
        }else {
            tv_my_nick.setText("未登录");
            user_head_portrait.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
