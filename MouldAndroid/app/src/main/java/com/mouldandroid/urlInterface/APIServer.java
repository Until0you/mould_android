package com.mouldandroid.urlInterface;

import com.mouldandroid.entity.BannerEntity;
import com.mouldandroid.entity.CodeBean;
import com.mouldandroid.entity.Message;
import com.mouldandroid.entity.StartEntity;
import com.mouldandroid.entity.TheArticle;
import com.mouldandroid.entity.User;
import com.mouldandroid.entity.UserBean;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by Administrator on 2018/1/12.
 *
 * 网络请求接口
 */

public interface APIServer {

    public static final String Base_Url = "http://app.hzz.com/";

    /** 广告页 */
    @Streaming
    @POST("startup")
    Call<StartEntity> getStartUrl(
            @Query("ad") String ad);

    /** 首页Banner */
    @POST("homepage")
    Call<BannerEntity> getBannerUrl();

    /** 注册 */
    @POST("register")
    Observable<UserBean> register(
            @Query("mobile") String mobile,
            @Query("smsscode") String smsscode,
            @Query("password") String password,
            @Query("invite_code") String invite_code
    );

    /** 获取验证码 */
    @POST("get_smsscode")
    Observable<CodeBean> getSms(
            @Query("token") String token,
            @Query("mobile") String mobile,
            @Query("captcha") String captcha,
            @Query("type") String type
    );

    /** 登录 */
    @POST("login")
    Observable<UserBean> loging(
            @Query("mobile") String mobile,
            @Query("password") String password
    );

    /** 短信登录 */
    @POST("login")
    Observable<UserBean> logingSms(
            @Query("mobile") String mobile,
            @Query("smsscode") String smsscode
    );

    /** 个人信息 */
    @POST("userinfo")
    Observable<User> getUserInfo(
            @Query("token") String token
    );

    /** 保存个人信息 */
    @POST("save_userinfo")
    Observable<String> saveUserinfo(
            @Query("name") String name,
            @Query("gender") String gender,
            @Query("age") String age,
            @Query("city") String city,
            @Query("intro") String intro
    );

    /** 消息列表 */
    @POST("messages")
    Observable<Message> getMessageList(
            @Query("token") String token
    );

    /** 消息内容 */
    @POST("message")
    Observable<String> getMessageContent(
            @Query("token") String token,
            @Query("msg_id") String msg_id
    );

    /**
     *  新闻列表
     * @param cateid 分类ID
     * @param page   页数
     * @return
     */
    @POST("news_list")
    Observable<TheArticle> getPulldown(
            @Query("cateid") String cateid,
            @Query("page") String page
    );

    /**
     * 判断手机号码是否存在
     * @param mobile
     * @return
     */
    @POST("check_mobile")
    Observable<CodeBean> checkMobile(
            @Query("mobile") String mobile
    );

    /**
     * 判断短信验证码是否正确
     * @param mobile
     * @param smsscode
     * @param forget
     * @return
     */
    @POST("check_smsscode")
    Observable<CodeBean> check_smsscode(
            @Query("mobile") String mobile,
            @Query("smsscode") String smsscode,
            @Query("type") String forget
    );

    /**
     * 更换新密码
     * @param mobile
     * @param smsscode
     * @param password
     * @return
     */
    @POST("forget_password")
    Observable<CodeBean> forgetPassword(
            @Query("mobile") String mobile,
            @Query("smsscode") String smsscode,
            @Query("password") String password
    );

    /**
     * 上传头像
     * @param
     * @param imgfile
     * @return
     */
    @Multipart
//    @POST("upload_head")
    @POST("uploadFile")
    Observable<String> uploadHead(
//            @Field("token") String token,
            @Part MultipartBody.Part imgfile
    );

    /**
     * 内容列表的金融列表请求
     * @param page
     * @return
     */
    @POST("")
    Observable<String> financial(
            @Query("page") String page
    );


    @POST("")
    Observable<String> financialTwo(
            @Query("page") String page
    );
}
