package com.mouldandroid.activity.message;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/25.
 * 消息内容
 */

public class MessageContent extends BaseActivity implements View.OnClickListener{

    private Context context;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.msg_content_title)
    public TextView msg_content_title;
    @BindView(R.id.msg_content_tv)
    public TextView msg_content_tv;

    private String title,content;

    @Override
    protected int setView() {
        return R.layout.message_content_layout;
    }

    @Override
    protected void initView() {
        iv_back.setOnClickListener(this);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("消息内容");
    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        msg_content_title.setText(title);
        msg_content_tv.setText(content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
