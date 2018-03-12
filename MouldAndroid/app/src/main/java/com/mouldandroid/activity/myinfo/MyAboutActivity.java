package com.mouldandroid.activity.myinfo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/18.
 * 关于界面
 */

public class MyAboutActivity extends BaseActivity implements View.OnClickListener{

    private Context context;
    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.my_about_score)
    public RelativeLayout my_about_score;
    @BindView(R.id.about_agreement)
    public RelativeLayout about_agreement;

    @Override
    protected int setView() {
        return R.layout.my_about_layout;
    }

    @Override
    protected void initView() {
        context = MyAboutActivity.this;
        iv_back.setOnClickListener(this);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("关于");

        about_agreement.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.about_agreement:
                Intent it_agreement = new Intent(context, UserAgreementActivity.class);
                startActivity(it_agreement);
                break;
        }
    }
}
