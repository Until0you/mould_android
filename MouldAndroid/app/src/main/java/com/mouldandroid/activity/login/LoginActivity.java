package com.mouldandroid.activity.login;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.custom.view.TimeButton;
import com.mouldandroid.entity.User;
import com.mouldandroid.entity.UserBean;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.PhoneUtils;
import com.mouldandroid.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/19.
 *
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Context context;
    /** 切换登录 */
    private boolean isLoging = true;

    @BindView(R.id.iv_login_qq)
    public ImageView iv_login_qq;
    @BindView(R.id.iv_login_wechat)
    public ImageView iv_login_wechat;
    @BindView(R.id.iv_login_sina)
    public ImageView iv_login_sina;

    @BindView(R.id.iv_back_text)
    public ImageView iv_back_text;
    @BindView(R.id.tv_title_name_text)
    public TextView tv_title_name_text;
    @BindView(R.id.tv_save_text)
    public TextView tv_save_text;
    @BindView(R.id.tv_loging_agreement)
    public TextView tv_loging_agreement;
    @BindView(R.id.tv_convert_loging)
    public TextView tv_convert_loging;
    @BindView(R.id.ed_loging_password)
    public EditText ed_loging_password;
    @BindView(R.id.rl_loging_code)
    public RelativeLayout rl_loging_code;
    @BindView(R.id.ed_phone_code)
    public EditText ed_phone_code;
    @BindView(R.id.but_loging)
    public Button but_loging;
    @BindView(R.id.registered_validation_code)
    public EditText registered_validation_code;
    @BindView(R.id.registered_obtain_code)
    public TimeButton registered_obtain_code;

    @Override
    protected int setView() {
        return R.layout.login_layout;
    }

    @Override
    protected void initView() {
        context = LoginActivity.this;

        iv_login_qq.setOnClickListener(this);
        iv_login_wechat.setOnClickListener(this);
        iv_login_sina.setOnClickListener(this);

        iv_back_text.setOnClickListener(this);
        tv_title_name_text.setText("登录");
        tv_save_text.setText("注册");
        tv_save_text.setOnClickListener(this);
        tv_loging_agreement.setOnClickListener(this);
        tv_convert_loging.setOnClickListener(this);
        but_loging.setOnClickListener(this);

        registered_obtain_code.setFocusable(false);
        ed_phone_code.addTextChangedListener(watcher);
    }

    @Override
    protected void initData() {

    }

    private void postData(){
        String mobile = ed_phone_code.getText().toString().trim();
        String password = ed_loging_password.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)){
            ToastUtil.showToast(context,"请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast(context,"请输入密码");
            return;
        }
        if (!PhoneUtils.isCellphone(mobile)){
            ToastUtil.showToast(context,"请输入正确的手机号码");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.loging(mobile,password)
                .subscribeOn(Schedulers.newThread())          //请求在新的线程中执行
                .observeOn(Schedulers.io())                     //请求完成后在io线程中执行
                .doOnNext(new Action1<UserBean>() {
                    @Override
                    public void call(UserBean userBean) {            //保存用户信息到本地
                        mouldManager.setUserAccount(userBean.getAccess_token());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())      //最后在主线程中执行
                .subscribe(new Subscriber<UserBean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {  //请求失败
                        ToastUtil.showToast(context,"登录失败！！！");
                    }
                    @Override
                    public void onNext(UserBean userBean) {
                        //请求成功
                        if (!TextUtils.isEmpty(userBean.getAccess_token())){
                            ToastUtil.showToast(context,"登录成功");
                            getUserInfo();
                        }else {
                            ToastUtil.showToast(context,"登录失败！！！");
                        }
                    }
                });
    }

    /**
     * 短信快捷登录
     */
    private void postDataSms(){
        String mobile = ed_phone_code.getText().toString().trim();
        String smscode = registered_validation_code.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)){
            ToastUtil.showToast(context,"请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(smscode)){
            ToastUtil.showToast(context,"请输入验证码");
            return;
        }
        if (!PhoneUtils.isCellphone(mobile)){
            ToastUtil.showToast(context,"请输入正确的手机号码");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.logingSms(mobile,smscode)
                .subscribeOn(Schedulers.newThread())          //请求在新的线程中执行
                .observeOn(Schedulers.io())                     //请求完成后在io线程中执行
                .doOnNext(new Action1<UserBean>() {
                    @Override
                    public void call(UserBean userBean) {            //保存用户信息到本地
                        mouldManager.setUserAccount(userBean.getAccess_token());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())      //最后在主线程中执行
                .subscribe(new Subscriber<UserBean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {  //请求失败
                        ToastUtil.showToast(context,"登录失败！！！");
                    }
                    @Override
                    public void onNext(UserBean userBean) {
                        //请求成功
                        if (!TextUtils.isEmpty(userBean.getAccess_token())){
                            ToastUtil.showToast(context,"登录成功");
                            getUserInfo();
                        }else {
                            ToastUtil.showToast(context,"登录失败！！！");
                        }
                    }
                });
    }

    /**
     * 获取用户个人信息
     */
    private void getUserInfo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
//        apiServer.getUserInfo(mouldManager.getUserAccount())
        apiServer.getUserInfo(android_ID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mouldManager.setUser(user);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(context,"获取数据失败");
                    }
                    @Override
                    public void onNext(User user) {
                        ToastUtil.showToast(context,"获取数据成功");
                        finish();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back_text:
                finish();
                break;
            case R.id.but_loging:       //点击登录
                if (isLoging){
                    postData();  // 密码登录
                }else {
                    postDataSms();
                }
                break;
            case R.id.tv_save_text:         //注册
                Intent intent = new Intent(context,RegisteredActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_login_qq:      //  QQ登录
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.iv_login_wechat:      //微信
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.iv_login_sina:      //微博
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, authListener);
                break;
            case R.id.tv_loging_agreement:         //忘记密码
                Intent it_forgetpass = new Intent(context,ForgetPassActivity.class);
                startActivity(it_forgetpass);
//                Intent it_agreement = new Intent(context, UserAgreementActivity.class);   //用户协议
//                startActivity(it_agreement);
                break;
            case R.id.tv_convert_loging:        //切换登录方式
                convertLoging();
                break;
        }
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            String mobile = ed_phone_code.getText().toString().trim();
            if (!TextUtils.isEmpty(mobile) && PhoneUtils.isCellphone(mobile)){
                registered_obtain_code.setEnabled(true);
            }else {
                registered_obtain_code.setEnabled(false);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {}
        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            ToastUtil.showToast(context,"成功了");
        }
        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showToast(context,"失败"+t.getMessage());
        }
        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.showToast(context,"取消了");
        }
    };

    private void convertLoging(){
        if (isLoging){
            isLoging = false;
            tv_convert_loging.setText("密码登录");
            rl_loging_code.setVisibility(View.VISIBLE);
            ed_loging_password.setVisibility(View.GONE);
        }else {
            isLoging = true;
            tv_convert_loging.setText("短信快捷登录");
            rl_loging_code.setVisibility(View.GONE);
            ed_loging_password.setVisibility(View.VISIBLE);
        }
    }
}
