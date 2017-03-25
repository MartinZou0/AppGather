package com.appgather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.appgather.R;
import com.appgather.entity.Apps;

/**
 * Created by qinghua on 2016/11/23.
 * 显示功能块的适配器
 */

public class AppViewAdapter extends BaseAdapter{
    private List<Apps> urllist;
    private LayoutInflater inflater;
    public AppViewAdapter(Context context,List<Apps> list)
    {
        urllist=list;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return urllist.size();
    }

    @Override
    public Object getItem(int i) {
        return urllist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold=null;
        if(view==null)
        {
            viewHold=new ViewHold();
            view=inflater.inflate(R.layout.appviewitem,null);
            viewHold.tv_appName= (TextView) view.findViewById(R.id.tv_appName);
            viewHold.m_webView= (WebView) view.findViewById(R.id.mapView_app);
            view.setTag(viewHold);
        }
        else
        {
            viewHold= (ViewHold) view.getTag();
        }

        WebSettings webSettings = viewHold.m_webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        viewHold.tv_appName.setText(urllist.get(i).getAppName());
        viewHold.m_webView.loadUrl(urllist.get(i).getUrl());
        viewHold.m_webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        return view;
    }

    class ViewHold{
        WebView m_webView;
        TextView tv_appName;
    };
}
