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
import com.mouldandroid.entity.CodeBean;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.PhoneUtils;
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
 * 忘记密码界面
 */

public class ForgetPassActivity extends BaseActivity{

    private Context context;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.but_forget_next)
    public Button but_forget_next;
    @BindView(R.id.et_forget_one_mobile)
    public EditText et_forget_one_mobile;

    @Override
    protected int setView() {
        return R.layout.forget_pass_layout;
    }

    @Override
    protected void initView() {
        context = ForgetPassActivity.this;

        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("找回密码");

        et_forget_one_mobile.addTextChangedListener(watcher);
    }

    @Override
    protected void initData() {

    }

    private void postData(){
        final String mobile = et_forget_one_mobile.getText().toString().trim();

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
        apiServer.checkMobile(mobile)
                .subscribeOn(Schedulers.newThread())          //请求在新的线程中执行
                .observeOn(Schedulers.io())                     //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())      //最后在主线程中执行
                .subscribe(new Subscriber<CodeBean>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {  //请求失败
                        ToastUtil.showToast(context,"请求失败");
                    }
                    @Override
                    public void onNext(CodeBean codeBean) {
                        //请求成功
                        if (TextUtils.equals(codeBean.getStatus(),"true")){
                            Intent intent = new Intent(context,ForgetPassTwoActivity.class);
                            intent.putExtra("mobile",mobile);
                            startActivity(intent);
                        }else {
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
            String mobile = et_forget_one_mobile.getText().toString().trim();
            if (!TextUtils.isEmpty(mobile) && PhoneUtils.isCellphone(mobile)){
                but_forget_next.setEnabled(true);
            }else {
                but_forget_next.setEnabled(false);
            }
        }
    };

    @OnClick({R.id.iv_back,R.id.but_forget_next})
    public void forgetOnClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.but_forget_next:
                postData();
                break;
        }
    }
}
