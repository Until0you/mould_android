package com.mouldandroid.activity.login;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.activity.myinfo.UserAgreementActivity;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.custom.view.TimeButton;
import com.mouldandroid.entity.CodeBean;
import com.mouldandroid.entity.UserBean;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.PhoneUtils;
import com.mouldandroid.utils.ToastUtil;

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
 * 注册界面
 */

public class RegisteredActivity extends BaseActivity implements View.OnClickListener{

    private Context context;
    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.but_registered)
    public Button but_registered;
    @BindView(R.id.et_registered_account)
    public EditText et_registered_account;
    @BindView(R.id.et_registered_password)
    public EditText et_registered_password;
    @BindView(R.id.tv_user_agreement)
    public TextView tv_user_agreement;
    @BindView(R.id.registered_obtain_code)
    public TimeButton registered_obtain_code;
    @BindView(R.id.registered_validation_code)
    public EditText registered_validation_code;
    @BindView(R.id.tv_registered_convert_loging)
    public TextView tv_registered_convert_loging;

    @Override
    protected int setView() {
        return R.layout.registered_layout;
    }

    @Override
    protected void initView() {
        context = RegisteredActivity.this;

        iv_back.setOnClickListener(this);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("注册");

        but_registered.setOnClickListener(this);
        tv_user_agreement.setOnClickListener(this);
        registered_obtain_code.setOnClickListener(this);
        tv_registered_convert_loging.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    /**
     * 注册
     */
    private void postRegister(){
        String mobile = et_registered_account.getText().toString().trim();
        String smsscode = registered_validation_code.getText().toString().trim();
        String password = et_registered_password.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)){
            ToastUtil.showToast(context,"请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast(context,"请输入密码");
            return;
        }
        if (TextUtils.isEmpty(smsscode)){
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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //新的配置
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.register(mobile,smsscode,password,null)
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
                    public void onError(Throwable e) {//请求失败
                        ToastUtil.showToast(context,"注册失败");
                    }
                    @Override
                    public void onNext(UserBean userBean) {//请求成功
                        if (!TextUtils.isEmpty(userBean.getAccess_token())){
                            ToastUtil.showToast(context,"注册成功");
                        }else {
                            ToastUtil.showToast(context,"注册失败！！！");
                        }
                    }
                });
    }

    /**
     * 获取短信验证码
     */
    private void getSmsscode(){
        String mobile = et_registered_account.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)){
            ToastUtil.showToast(context,"请输入手机号码");
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
        apiServer.getSms(android_ID,mobile,"ased","reg")
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CodeBean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(context,"短信发送失败");
                    }
                    @Override
                    public void onNext(CodeBean codeBean) {
                        if (TextUtils.equals(codeBean.getStatus(),"true") && TextUtils.equals(codeBean.getCode(),"0")){
                            ToastUtil.showToast(context,"短信发送成功");
                        }else {
                            ToastUtil.showToast(context,"短信发送失败");
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.but_registered:
                postRegister();
                break;
            case R.id.tv_user_agreement:   //用户协议
                Intent it_agreement = new Intent(context, UserAgreementActivity.class);
                startActivity(it_agreement);
                break;
            case R.id.registered_obtain_code:   //获取验证码
                registered_obtain_code.setLenght(5*1000);
                getSmsscode();
                break;
            case R.id.tv_registered_convert_loging:
                finish();
                break;
        }
    }
}
