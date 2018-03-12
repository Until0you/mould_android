package com.mouldandroid.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class BannerEntity {

    private String status;
    private List<BannerList> banner;
    private BannerDialog activity;
    private String new_skin;

    public BannerDialog getActivity() {
        return activity;
    }

    public void setActivity(BannerDialog activity) {
        this.activity = activity;
    }

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
        private String banner_id;
        private String banner_img;
        private String type;
        private String uri;
        private String order_no;
        private String status;

        public String getBanner_id() {
            return banner_id;
        }

        public void setBanner_id(String banner_id) {
            this.banner_id = banner_id;
        }

        public String getBanner_img() {
            return banner_img;
        }

        public void setBanner_img(String banner_img) {
            this.banner_img = banner_img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class BannerDialog{
        private String cover;
        private String url;
        private String act_id;
        private String starttime;
        private String endtime;
        private String status;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAct_id() {
            return act_id;
        }

        public void setAct_id(String act_id) {
            this.act_id = act_id;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
