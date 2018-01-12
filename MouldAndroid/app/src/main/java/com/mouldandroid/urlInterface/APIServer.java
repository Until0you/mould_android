package com.mouldandroid.urlInterface;

import com.mouldandroid.entity.BannerEntity;
import com.mouldandroid.entity.StartEntity;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by Administrator on 2018/1/12.
 */

public interface APIServer {

    @Streaming
    @POST("startup")
    Call<StartEntity> getStartUrl(@Query("ad") String ad);

    @POST("homepage")
    Call<BannerEntity> getBannerUrl();
}
