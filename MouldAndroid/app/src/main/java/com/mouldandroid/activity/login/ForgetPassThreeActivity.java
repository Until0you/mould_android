package com.mouldandroid.activity.login;

import android.content.Context;
import android.text.TextUtils;
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
import com.mouldandroid.utils.ToastUtil;
import com.mouldandroid.utils.myretrofitutils.StringConverterFactory;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/6.
 * 找回密码第三步
 */

public class ForgetPassThreeActivity extends BaseActivity{

    private Context context;
    private String mobile,smsCode,password;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.but_forget_three_next)
    public Button but_forget_three_next;
    @BindView(R.id.et_forget_three_mobile)
    public EditText et_forget_three_mobile;

    @Override
    protected int setView() {
        return R.layout.forget_passthree_layout;
    }

    @Override
    protected void initView() {
        context = ForgetPassThreeActivity.this;

        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("重置密码");
    }

    @Override
    protected void initData() {
        mobile = getIntent().getStringExtra("mobile");
        smsCode = getIntent().getStringExtra("smsCode");

    }

    private void postData(){
        password = et_forget_three_mobile.getText().toString().trim();
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast(context,"请输入新密码");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.forgetPassword(mobile,smsCode,password)
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
//                        LogUtils.e("--------------->>> "+codeBean);
                        if (TextUtils.equals(codeBean.getStatus(),"true")){
                            ToastUtil.showToast(context,"重置成功");
                            finish();
                        }else {
                            ToastUtil.showToast(context,"重置失败");
                        }
                    }
                });
    }

    @OnClick({R.id.iv_back,R.id.but_forget_three_next})
    public void OnClickForgetThree(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.but_forget_three_next:
                postData();
                break;
        }
    }


}
