package com.mouldandroid.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mouldandroid.R;
import com.mouldandroid.activity.webActivity.HomeDialogWeb;
import com.mouldandroid.base.BaseFragments;
import com.mouldandroid.custom.diglog.HomeDialog;
import com.mouldandroid.entity.BannerEntity;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/17.
 *
 * 首页
 */

public class HomeFragment extends BaseFragments{

    @BindView(R.id.bga_banner)
    public BGABanner bga_banner;
    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;

    private BannerEntity bannerEntity;
    private Context context;

    @Override
    public int getLayoutResId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        context = getActivity();
        iv_back.setVisibility(View.GONE);
        iv_save.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        postData();
    }

    private void postData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        Call<BannerEntity> call = apiServer.getBannerUrl();
        call.enqueue(new Callback<BannerEntity>() {
            @Override
            public void onResponse(Call<BannerEntity> call, Response<BannerEntity> response) {
                bannerEntity = response.body();

                bga_banner.setAdapter(new BGABanner.Adapter<ImageView,String>() {
                    @Override
                    public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                        Glide.with(getActivity())
                                .load(model)
                                .placeholder(R.mipmap.holder)
                                .error(R.mipmap.holder)
                                .centerCrop()
                                .dontAnimate()
                                .into(itemView);
                    }
                });
                ArrayList list = new ArrayList();
                if (null != response.body()){
                    for (int i = 0; i<response.body().getBanner().size();i++){
                        list.add(response.body().getBanner().get(i).getBanner_img());
                    }
                    bga_banner.setData(list,null);
                }
                //banner图片的点击事件
                bga_banner.setDelegate(new BGABanner.Delegate<ImageView,String>(){
                    @Override
                    public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
//                        ToastUtil.showToast(getActivity(),"点击了" + position +"图");
                        if (null != bannerEntity && null != bannerEntity.getActivity()){
                            setDialog(bannerEntity.getActivity().getCover());
                        }
                    }
                });
            }
            @Override
            public void onFailure(Call<BannerEntity> call, Throwable t) {}
        });
    }


    private void setDialog(String url){
        HomeDialog.Builder builder = new HomeDialog.Builder(context);
        builder.setImage(url);
        builder.setImageOnClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent(context, HomeDialogWeb.class);
                intent.putExtra("url",bannerEntity.getActivity().getUrl());
                startActivity(intent);
            }
        });
        builder.setCancelOnClick(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
