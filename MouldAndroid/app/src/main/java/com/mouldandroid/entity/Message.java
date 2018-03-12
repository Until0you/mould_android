package com.mouldandroid.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */

public class Message {

    private String status;

    private List<MessageData> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MessageData> getData() {
        return data;
    }

    public void setData(List<MessageData> data) {
        this.data = data;
    }

    public static class MessageData{

        private String id;
        private String title;
        private String readed;
        private String cover;
        private String summary;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getReaded() {
            return readed;
        }
        public void setReaded(String readed) {
            this.readed = readed;
        }
        public String getCover() {
            return cover;
        }
        public void setCover(String cover) {
            this.cover = cover;
        }
        public String getSummary() {
            return summary;
        }
        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
