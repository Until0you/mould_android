package com.mouldandroid.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.activity.contentList.FinancialListActivity;
import com.mouldandroid.base.BaseFragments;
import com.mouldandroid.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17.
 */

public class ProblemFragment extends BaseFragments{

//    @BindView(R.id.problem_shimmer_rv)
//    public ShimmerRecyclerView problem_shimmer_rv;

    private Context context;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.rl_content_list)
    public RelativeLayout rl_content_list;
    @BindView(R.id.rl_graphic_list)
    public RelativeLayout rl_graphic_list;
    @BindView(R.id.rl_the_chart)
    public RelativeLayout rl_the_chart;
    @BindView(R.id.rl_video)
    public RelativeLayout rl_video;
    @BindView(R.id.rl_page)
    public RelativeLayout rl_page;

    @Override
    public int getLayoutResId() {
        return R.layout.problem_fragment;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        context = getActivity();
//        problem_shimmer_rv.showShimmerAdapter();

        iv_back.setVisibility(View.GONE);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("组件");

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rl_content_list,R.id.rl_graphic_list,R.id.rl_the_chart,R.id.rl_video,R.id.rl_page})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_content_list:
//                ToastUtil.showToast(context,"点击了内容列表");
                Intent it_content_list = new Intent(context, FinancialListActivity.class);
                startActivity(it_content_list);
                break;
            case R.id.rl_graphic_list:
                ToastUtil.showToast(context,"点击了图片列表");
                break;
            case R.id.rl_the_chart:
                ToastUtil.showToast(context,"点击了图表");
                break;
            case R.id.rl_video:
                ToastUtil.showToast(context,"点击了视频");
                break;
            case R.id.rl_page:
                ToastUtil.showToast(context,"点击了单页");
                break;
        }
    }
}
