package com.appgather.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.appgather.R;

/**
 * Created by qinghua on 2017/4/11.
 */

public class AppStoreActivity extends AppCompatActivity {

    private WebView wv_appstore;
    private static String appStore_url="http://store.appsgather.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstore_layout);
        initView();
        setWebView();
    }



    private void initView() {
        wv_appstore= (WebView) findViewById(R.id.wv_appstore);

    }

    private void setWebView() {

        WebSettings setting=wv_appstore.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        setting.setJavaScriptEnabled(true);
        //设置可以访问文件
        setting.setAllowFileAccess(true);
        //设置支持缩放
        setting.setBuiltInZoomControls(true);
        wv_appstore.loadUrl(appStore_url);
        wv_appstore.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv_appstore.loadUrl(url);
                return true;
            }
        });

    }
}
