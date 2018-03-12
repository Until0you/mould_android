package com.mouldandroid.custom.diglog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mouldandroid.R;

/**
 * Created by Administrator on 2018/2/1.
 */

public class HomeDialog extends Dialog{
    public HomeDialog(@NonNull Context context) {
        super(context);
    }
    public HomeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{
        private Context context;
        private String img_url;
        private ImageView iv_home_dialog,iv_home_cancel;
        private DialogInterface.OnClickListener imageClickListener;
        private DialogInterface.OnClickListener cancelClickListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setImage(String img_url){
            this.img_url = img_url;
            return this;
        }

        /**
         * 设置按钮的资源和监听器
         */
        public Builder setImageOnClick(DialogInterface.OnClickListener listener) {
            this.imageClickListener = listener;
            return this;
        }

        public Builder setCancelOnClick(DialogInterface.OnClickListener listener) {
            this.cancelClickListener = listener;
            return this;
        }

        public HomeDialog create(){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //实例化自定义主题的对话框
            final HomeDialog dialog = new HomeDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.home_dialog,null);
            dialog.addContentView(layout,new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //设置点击对话框外不会失效 true为会
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            //设置取消,确认按钮
            iv_home_dialog = (ImageView) layout.findViewById(R.id.iv_home_dialog);
            iv_home_cancel = (ImageView) layout.findViewById(R.id.iv_home_cancel);

            Glide.with(context)
                    .load(img_url)
                    .placeholder(R.mipmap.holder)
                    .error(R.mipmap.holder)
                    .centerCrop()
                    .dontAnimate()
                    .into(iv_home_dialog);

            //设置取消按钮的的点击事件
            if (null != cancelClickListener){
                iv_home_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }

            if (null != imageClickListener){
                iv_home_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageClickListener.onClick(dialog,DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }
            dialog.setContentView(layout);
            return dialog;
        }

    }

}
