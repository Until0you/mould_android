package com.mouldandroid.activity.myActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.mouldandroid.R;
import com.mouldandroid.activity.myinfo.IntroActivity;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.custom.diglog.ActionSheetDialog;
import com.mouldandroid.custom.diglog.ActionSheetDialog.OnSheetItemClickListener;
import com.mouldandroid.custom.diglog.ActionSheetDialog.SheetItemColor;
import com.mouldandroid.custom.view.RoundImageView;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.LogUtils;
import com.mouldandroid.utils.PhotoUtil;
import com.mouldandroid.utils.PickerUtils;
import com.mouldandroid.utils.ToastUtil;
import com.mouldandroid.utils.UriPathUtils;
import com.mouldandroid.utils.myretrofitutils.StringConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MyPersonalInfo extends BaseActivity{

    private Context context;
    private File filePhoto;
    private String path;
    private static final int REQUEST_LIST_CODE = 0;
    private static final int REQUEST_CAMERA_CODE = 1;

    @BindView(R.id.iv_back_text)
    public ImageView iv_back_text;
    @BindView(R.id.tv_save_text)
    public TextView tv_save_text;
    @BindView(R.id.tv_title_name_text)
    public TextView tv_title_name_text;
    @BindView(R.id.rl_persona_phone_code)
    public RelativeLayout rl_persona_phone_code;
    @BindView(R.id.et_persona_name)
    public EditText et_persona_name;
    @BindView(R.id.rl_persona_gender)
    public RelativeLayout rl_persona_gender;
    @BindView(R.id.rl_persona_age)
    public RelativeLayout rl_persona_age;
    @BindView(R.id.rl_persona_city)
    public RelativeLayout rl_persona_city;
    @BindView(R.id.rl_persona_intro)
    public RelativeLayout rl_persona_intro;
    @BindView(R.id.tv_persona_gender)
    public TextView tv_persona_gender;
    @BindView(R.id.tv_persona_age)
    public TextView tv_persona_age;
    @BindView(R.id.tv_persona_city)
    public TextView tv_persona_city;
    @BindView(R.id.tv_persona_intro)
    public TextView tv_persona_intro;
    @BindView(R.id.my_personal_head)
    public RelativeLayout my_personal_head;
    @BindView(R.id.user_head_portrait)
    public RoundImageView user_head_portrait;
    @BindView(R.id.tv_my_nick)
    public TextView tv_my_nick;

    private OptionsPickerView pvOptions;
    private TimePickerView pvTime;
    ArrayList<String> options1Items = new ArrayList<>();
    private boolean isJsonData = false;
    private String name,gender,age,city,intro;
    private final static int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 2;

    @Override
    protected int setView() {
        return R.layout.my_persona_info;
    }

    @Override
    protected void initView() {
        context = MyPersonalInfo.this;
        tv_save_text.setText("保存");
        tv_title_name_text.setText("账户信息");
        et_persona_name.setSelection(et_persona_name.length());
        et_persona_name.addTextChangedListener(watcher);
    }

    @Override
    protected void initData() {
        options1Items.add("保密");
        options1Items.add("男");
        options1Items.add("女");
        pvTime = PickerUtils.initTimePicker(context,tv_persona_age);  //日期选择器
        pvOptions = PickerUtils.initOptionPicker(context,options1Items,tv_persona_gender);   //设置性别选择器;

    }

    /**
     * 请求服务器保存用户信息
     */
    private void postSaveUserInfo(){
        name = et_persona_name.getText().toString().trim();
        gender = tv_persona_gender.getText().toString().trim();
        age = tv_persona_age.getText().toString().trim();
        city = tv_persona_city.getText().toString().trim();
        intro = tv_persona_intro.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.saveUserinfo(name,gender,age,city,intro)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(context,"获取数据失败");
                    }
                    @Override
                    public void onNext(String str) {
                        LogUtils.e("保存信息返回------------>>>" + str);
//                        finish();
                    }
                });
    }

    /**
     * EditText改变监听
     */
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }
        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };

    @OnClick({R.id.iv_back_text,R.id.tv_save_text,R.id.rl_persona_gender,
            R.id.rl_persona_age,R.id.rl_persona_city,R.id.rl_persona_intro,R.id.rl_persona_phone_code,R.id.my_personal_head})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back_text:
                finish();
                break;
            case R.id.tv_save_text:     //保存
                postSaveUserInfo();
                break;
            case R.id.rl_persona_gender:  //性别
                pvOptions.show();
                break;
            case R.id.rl_persona_age:  //年龄
                pvTime.show();
                break;
            case R.id.rl_persona_city:  //城市
                //解析省市区数据并弹出选择器
                PickerUtils.newThread(context,tv_persona_city);
                break;
            case R.id.rl_persona_intro:  //个人简介
                intro = tv_persona_intro.getText().toString().trim();
                Intent it_intro = new Intent(context, IntroActivity.class);
                it_intro.putExtra("text",intro);
                startActivityForResult(it_intro,REQUEST_CODE);
                break;
            case R.id.rl_persona_phone_code:       //更换手机号码

                break;
            case R.id.my_personal_head:     //更换头像
                initSelectPopWindow();
                break;
        }
    }

    /**
     * 初始化点击头像选择对话框
     */
    private void initSelectPopWindow() {
        ActionSheetDialog mDialog = new ActionSheetDialog(this).builder();
        mDialog.setTitle("选择");
        mDialog.setCancelable(false);
        mDialog.addSheetItem("拍照", SheetItemColor.Blue, new OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
//                PhotoUtil.photograph(MyPersonalInfo.this);


            }
        }).addSheetItem("从相册选取", SheetItemColor.Blue, new OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                PhotoUtil.selectPictureFromAlbum(MyPersonalInfo.this);
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE){
//            if (resultCode == RESULT_CODE){
//                tv_persona_intro.setText(data.getExtras().getString("data"));
//            }
//        }
        if (resultCode == PhotoUtil.NONE) {
            return;
        }
        // 拍照
        if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            // 设置文件保存路径这里放在跟目录下
//            File picture = null;
//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//                picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
//                if (!picture.exists()) {
//                    picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
//                }
//            } else {
//                picture = new File(this.getFilesDir() + PhotoUtil.imageName);
//                if (!picture.exists()) {
//                    picture = new File(MyPersonalInfo.this.getFilesDir() + PhotoUtil.imageName);
//                }
//            }
//
//            path = PhotoUtil.getPath(this);// 生成一个地址用于存放剪辑后的图片
//            if (TextUtils.isEmpty(path)) {
//                LogUtils.e("随机生成的用于存放剪辑后的图片的地址失败");
//                return;
//            }
//            Uri imageUri = UriPathUtils.getUri(this, path);
//            PhotoUtil.startPhotoZoom(MyPersonalInfo.this, Uri.fromFile(picture), PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH, imageUri);
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PhotoUtil.PHOTOZOOM) {

            path = PhotoUtil.getPath(this);// 生成一个地址用于存放剪辑后的图片
            if (TextUtils.isEmpty(path)) {
                LogUtils.e("随机生成的用于存放剪辑后的图片的地址失败");
                return;
            }
            Uri imageUri = UriPathUtils.getUri(this, path);
            PhotoUtil.startPhotoZoom(MyPersonalInfo.this, data.getData(), PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH, imageUri);
        }
        // 处理结果
        if (requestCode == PhotoUtil.PHOTORESOULT) {
            /**
             * 在这里处理剪辑结果，可以获取缩略图，获取剪辑图片的地址。得到这些信息可以选则用于上传图片等等操作
             * */
            postData(path);
            /**
             * 如，根据path获取剪辑后的图片
             */
            Bitmap bitmap = PhotoUtil.convertToBitmap(path,PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH);
            if(bitmap != null){
//                tv2.setText(bitmap.getHeight()+"x"+bitmap.getWidth()+"图");
//                clipImg.setImageBitmap(bitmap);
            }

            Bitmap bitmap2 = PhotoUtil.convertToBitmap(path,120, 120);
            if(bitmap2 != null){
//                tv1.setText(bitmap2.getHeight()+"x"+bitmap2.getWidth()+"图");
//                smallImg.setImageBitmap(bitmap2);
                user_head_portrait.setImageBitmap(bitmap2);
            }

//			Bundle extras = data.getExtras();
//			if (extras != null) {
//				Bitmap photo = extras.getParcelable("data");
//				ByteArrayOutputStream stream = new ByteArrayOutputStream();
//				photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
//				InputStream isBm = new ByteArrayInputStream(stream.toByteArray());
//			}

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
//                BAFLogger.e(TAG,e.getMessage());
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
//            BAFLogger.e(TAG,e.getMessage());
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps==null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

    private void postData(String bitmap_post){
//        File file = compressImage(bitmap_post);
        File file = new File(bitmap_post);
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);

        //  图片参数
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imgfile", file.getName(), requestFile);
//        //  token
        RequestBody phoneBody = RequestBody.create(MediaType.parse("multipart/form-data"), mouldManager.getUserAccount());

//        apiServer.uploadHead(android_ID,body)
        apiServer.uploadHead(body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("错误信息：" + e.getMessage());
                        ToastUtil.showToast(context,"获取数据失败");
                    }
                    @Override
                    public void onNext(String message) {
//                        ToastUtil.showToast(context,"获取数据成功");
                        LogUtils.e("------------------->>>>   "+message.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
