package com.appgather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appgather.R;
import com.appgather.entity.AppMsg;

/**
 * Created by qinghua on 2016/11/30.
 */

public class AppDetailViewActivity extends Activity {
    private WebView appWebView;
    private AppMsg appMsg=new AppMsg();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailappmainview);
        getDate();
        initView();
    }

    /**
     * 实现返回上一个网页
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && appWebView.canGoBack()) {
            appWebView.goBack();// 返回前一个页面
            return true;
        }
        else {
            finish();
            return  false;
        }
    }


    private void getDate() {
        Intent intent=getIntent();
        appMsg= (AppMsg) intent.getSerializableExtra("app");
    }

    private void initView() {
        appWebView= (WebView) findViewById(R.id.detailmapView_app);
        WebSettings webSettings = appWebView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        appWebView.loadUrl(appMsg.getAppUrl());
        final String str=appMsg.getAppnamae();
        appWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
}
