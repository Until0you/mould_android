package com.mouldandroid.activity.logo;

import android.widget.ImageView;

import com.mouldandroid.R;
import com.mouldandroid.activity.base.BaseActivity;
import com.mouldandroid.activity.loading.StartEntity;
import com.mouldandroid.custom.myview.PowerImageView;
import com.mouldandroid.utils.LogUtils;
import com.mouldandroid.utils.SharedPreferencesUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/5.
 */

public class StartActivity extends BaseActivity{

//    @BindView(R.id.start_icon)
//    public ImageView start_icon;

    @BindView(R.id.start_gif)
    public PowerImageView start_gif;

    private SharedPreferencesUtils spUtils;
    private ImageOptions options;
    private StartEntity startEntity;

    @Override
    protected int setView() {
        return R.layout.start_layout;
    }

    @Override
    protected void initData() {
        spUtils = new SharedPreferencesUtils(this);
        options = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.logo) // 加载中默认显示图片
                .setFailureDrawableId(R.mipmap.logo) // 加载失败后默认显示图片
                .setUseMemCache(false)
                .build();
        LogUtils.e("<-------------->  " + spUtils.getStartEntity().getAd_img());
        x.image().bind(start_gif,spUtils.getStartEntity().getAd_img(),options);
//        spUtils.emptyStartEntity(startEntity);  //清空StartEntity
    }
}
