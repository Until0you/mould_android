package com.mouldandroid.activity.myinfo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.activity.myActivity.MyPersonalInfo;
import com.mouldandroid.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/26.
 *
 * 个人简介
 */

public class IntroActivity extends BaseActivity implements View.OnClickListener{

    private Context context;

    @BindView(R.id.iv_back_text)
    public ImageView iv_back_text;
    @BindView(R.id.tv_save_text)
    public TextView tv_save_text;
    @BindView(R.id.tv_title_name_text)
    public TextView tv_title_name_text;
    @BindView(R.id.et_intro_text)
    public EditText et_intro_text;

    private String text;

    @Override
    protected int setView() {
        return R.layout.intro_layout;
    }

    @Override
    protected void initView() {
        tv_save_text.setText("完成");
        tv_title_name_text.setText("个人简介");
        iv_back_text.setOnClickListener(this);
        tv_save_text.setOnClickListener(this);

        text = getIntent().getStringExtra("text");
        et_intro_text.setText(text);
        et_intro_text.setSelection(et_intro_text.length());
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back_text:
                finish();
                break;
            case R.id.tv_save_text:     //完成
                Intent intent = new Intent();
                intent.putExtra("data",et_intro_text.getText().toString().trim());
                setResult(MyPersonalInfo.RESULT_CODE,intent);
                finish();
                break;
        }
    }


}
