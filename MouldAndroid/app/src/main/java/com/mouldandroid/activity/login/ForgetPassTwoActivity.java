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
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.custom.view.TimeButton;
import com.mouldandroid.entity.CodeBean;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/6.
 * 忘记密码第二步
 */

public class ForgetPassTwoActivity extends BaseActivity{

    private Context context;
    private String mobile,smsCode;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.but_forget_two_next)
    public Button but_forget_two_next;
    @BindView(R.id.et_forget_two_mobile)
    public EditText et_forget_two_mobile;
    @BindView(R.id.tb_forget_code)
    public TimeButton tb_forget_code;

    @Override
    protected int setView() {
        return R.layout.forget_passtwo_layout;
    }

    @Override
    protected void initView() {
        context = ForgetPassTwoActivity.this;

        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("找回密码");

        et_forget_two_mobile.addTextChangedListener(watcher);
    }

    @Override
    protected void initData() {
        mobile = getIntent().getStringExtra("mobile");
    }

    /**
     * 获取短信验证码
     */
    private void getSmsCode(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.getSms(android_ID,mobile,"ased","forget")
                .subscribeOn(Schedulers.newThread())          //请求在新的线程中执行
                .observeOn(Schedulers.io())                     //请求完成后在io线程中执行

                .observeOn(AndroidSchedulers.mainThread())      //最后在主线程中执行
                .subscribe(new Subscriber<CodeBean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {  //请求失败
                        ToastUtil.showToast(context,"短信发送失败");
                    }
                    @Override
                    public void onNext(CodeBean codeBean) {
                        //请求成功
                        if (TextUtils.equals(codeBean.getStatus(),"true") && TextUtils.equals(codeBean.getCode(),"0")){
                            ToastUtil.showToast(context,"短信发送成功");
                        }else {
                            ToastUtil.showToast(context,"短信发送失败");
                        }
                    }
                });
    }

    /**
     * 请求数据
     */
    private void postData(){
        smsCode = et_forget_two_mobile.getText().toString().trim();
        if (TextUtils.isEmpty(smsCode)){
            ToastUtil.showToast(context,"请输入验证码");
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.check_smsscode(mobile,smsCode,"forget")
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CodeBean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(context,"验证失败");
                    }
                    @Override
                    public void onNext(CodeBean codeBean) {
//                        LogUtils.e("---------------------->>> " + codeBean);
                        if (TextUtils.equals(codeBean.getStatus(),"true")){
                            Intent intent = new Intent(context,ForgetPassThreeActivity.class);
                            intent.putExtra("mobile",mobile);
                            intent.putExtra("smsCode",smsCode);
                            startActivity(intent);
                            finish();
                        }else{
                            ToastUtil.showToast(context,"验证失败");
                        }
                    }
                });
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            String code = et_forget_two_mobile.getText().toString().trim();
            if (code.length() == 6){
                but_forget_two_next.setEnabled(true);
            }else {
                but_forget_two_next.setEnabled(false);
            }
        }
    };

    @OnClick({R.id.iv_back,R.id.but_forget_two_next,R.id.tb_forget_code})
    public void ForgetTwoOnClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.but_forget_two_next:  //点击下一步
                postData();
                break;
            case R.id.tb_forget_code:      //验证码
                getSmsCode();
                break;
        }
    }
}
