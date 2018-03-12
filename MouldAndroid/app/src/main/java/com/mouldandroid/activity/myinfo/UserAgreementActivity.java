package com.mouldandroid.activity.myinfo;

import android.content.Context;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.utils.ConstValues;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/22.
 * 用户协议
 */

public class UserAgreementActivity extends BaseActivity implements View.OnClickListener{

    private Context context;
    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.webview_loading)
    public WebView webview_loading;
    @BindView(R.id.loading_progressbar)
    public ProgressBar loading_progressbar;


    @Override
    protected int setView() {
        return R.layout.webview_layout;
    }

    @Override
    protected void initView() {
        iv_back.setOnClickListener(this);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("用户协议");

        webview_loading.getSettings().setJavaScriptEnabled(true);
        webview_loading.loadUrl(ConstValues.CSYM + "service.html");
        webview_loading.setWebChromeClient(client);
        webview_loading.setWebViewClient(client2); // 不调用浏览器
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private WebChromeClient client = new WebChromeClient(){
        public void onProgressChanged(WebView view, int newProgress) {
            loading_progressbar.setProgress(newProgress);
            if(newProgress >= 100){
                loading_progressbar.setVisibility(View.GONE);
            }
        }
    };

    private WebViewClient client2 = new WebViewClient(){
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        };
    };
}
