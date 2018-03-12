package com.mouldandroid.entity;

/**
 * Created by Administrator on 2018/1/24.
 */

public class User {

    private String status;

    private UserData data;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public static class UserData{
        /** 昵称 */
        private String nick;
        /** 性别 */
        private String sex;
        /** 头像地址 */
        private String headimg;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }
    }
}
