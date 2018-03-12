package com.mouldandroid.activity.webActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/2/1.
 */

public class HomeDialogWeb extends BaseActivity{

    private String url;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.home_dialog_progressBar)
    public ProgressBar home_dialog_progressBar;
    @BindView(R.id.home_dialog_webView)
    public WebView home_dialog_webView;

    @Override
    protected int setView() {
        return R.layout.home_dialog_web;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Bundle bun = getIntent().getExtras();
        url = bun.getString("url");
        home_dialog_webView.getSettings().setJavaScriptEnabled(true);
        home_dialog_webView.loadUrl(url);
        home_dialog_webView.setWebChromeClient(client);
        home_dialog_webView.setWebViewClient(client2); // 不调用浏览器
    }

    private WebChromeClient client = new WebChromeClient(){
        public void onProgressChanged(WebView view, int newProgress) {
           if (null != home_dialog_progressBar){
               home_dialog_progressBar.setProgress(newProgress);
               if(newProgress >= 100){
                home_dialog_progressBar.setVisibility(View.GONE);
               }
           }
        };

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            tv_title_name.setText(title);
        }
    };

    private WebViewClient client2 = new WebViewClient(){
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        };
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
