package com.mouldandroid.activity.myinfo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.entity.User;
import com.mouldandroid.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/18.
 *  设置
 */

public class MySettingsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.my_settings_invite)
    public RelativeLayout my_settings_invite;  //邀请好友
    @BindView(R.id.my_settings_help)
    public RelativeLayout my_settings_help;     //帮助
    @BindView(R.id.my_settings_about)
    public RelativeLayout my_settings_about;    //关于
    @BindView(R.id.my_exit_login)
    public RelativeLayout my_exit_login;        //退出登录

    private Context context;
    private User user;

    @Override
    protected int setView() {
        return R.layout.my_settings_layout;
    }

    @Override
    protected void initView() {
        context = MySettingsActivity.this;

        iv_back.setOnClickListener(this);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("设置");

        my_settings_invite.setOnClickListener(this);
        my_settings_help.setOnClickListener(this);
        my_settings_about.setOnClickListener(this);
        my_exit_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        user = mouldManager.getUser();
    }

    private void initShareDialog(){
        UMImage image = new UMImage(context,R.mipmap.ic_launcher);
        UMWeb web = new UMWeb("http://www.baidu.com");  //链接
        web.setTitle("Mould");
        web.setThumb(image);
        web.setDescription("测试分享");
        new ShareAction(MySettingsActivity.this)
                .withMedia(web)
                .setDisplayList(
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showToast(context, "分享成功");
        }
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtil.showToast(context, "分享失败");
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//			ToastUtil.showToast(context, "分享已取消");
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.my_settings_invite:   //邀请好友
                initShareDialog();
                break;
            case R.id.my_settings_help:   //帮助
                Intent it_help = new Intent(context,HelpActivity.class);
                startActivity(it_help);
                break;
            case R.id.my_settings_about:  //关于
                Intent it_about = new Intent(context,MyAboutActivity.class);
                startActivity(it_about);
                break;
            case R.id.my_exit_login:    //退出登录
                if (null != user){
                    mouldManager.emptyUser(user);
                    mouldManager.emptyUserAccount(mouldManager.getUserAccount());
                }
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
