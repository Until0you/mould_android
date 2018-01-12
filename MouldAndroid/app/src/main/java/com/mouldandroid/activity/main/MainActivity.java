package com.mouldandroid.activity.main;

import com.mouldandroid.R;
import com.mouldandroid.activity.base.BaseActivity;
import com.mouldandroid.entity.BannerEntity;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.LogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
//        RequestParams params = new RequestParams("http://app.hzz.com/homepage");
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
                LogUtils.e("---->>>   " + response.body().getBanner().get(0).getImg());
            }

            @Override
            public void onFailure(Call<BannerEntity> call, Throwable t) {

            }
        });
    }
}
