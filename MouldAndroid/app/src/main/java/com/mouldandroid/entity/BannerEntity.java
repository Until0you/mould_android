package com.mouldandroid.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class BannerEntity {

    private String status;
    private List<BannerList> banner;
    private String new_skin;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BannerList> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerList> banner) {
        this.banner = banner;
    }

    public String getNew_skin() {
        return new_skin;
    }

    public void setNew_skin(String new_skin) {
        this.new_skin = new_skin;
    }

    public static class BannerList{
        private String img;
        private String banner_type;
        private String uri;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBanner_type() {
            return banner_type;
        }

        public void setBanner_type(String banner_type) {
            this.banner_type = banner_type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

}
