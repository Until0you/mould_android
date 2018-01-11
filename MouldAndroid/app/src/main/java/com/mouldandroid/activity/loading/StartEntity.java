package com.mouldandroid.activity.loading;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/11.
 *
 * 广告页实体类
 */

public class StartEntity implements Serializable {

    private String ad_id;
    private String ad_type;
    private String ad_img;
    private String ad_url;
    private String ad_timer;

    public StartEntity() {}

    public StartEntity(String ad_type, String ad_img, String ad_url, String ad_timer) {
        this.ad_type = ad_type;
        this.ad_img = ad_img;
        this.ad_url = ad_url;
        this.ad_timer = ad_timer;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_type() {
        return ad_type;
    }

    public void setAd_type(String ad_type) {
        this.ad_type = ad_type;
    }

    public String getAd_img() {
        return ad_img;
    }

    public void setAd_img(String ad_img) {
        this.ad_img = ad_img;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getAd_timer() {
        return ad_timer;
    }

    public void setAd_timer(String ad_timer) {
        this.ad_timer = ad_timer;
    }
}
