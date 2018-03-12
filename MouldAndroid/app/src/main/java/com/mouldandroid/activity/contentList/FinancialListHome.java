package com.mouldandroid.activity.contentList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/1.
 */

public class FinancialListHome extends BaseActivity{

    private Context context;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.rl_financial_list_one)
    public RelativeLayout rl_financial_list_one;
    @BindView(R.id.rl_financial_list_two)
    public RelativeLayout rl_financial_list_two;

    @Override
    protected int setView() {
        return R.layout.financial_layout;
    }

    @Override
    protected void initView() {
        context = FinancialListHome.this;

        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("金融列表");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back,R.id.rl_financial_list_one,R.id.rl_financial_list_two})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_financial_list_one:
                Intent intent = new Intent(context,FinancialListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_financial_list_two:
                Intent intent1 = new Intent(context,FinancialListTwoActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
