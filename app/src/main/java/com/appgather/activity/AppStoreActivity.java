package com.appgather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.appgather.R;
import com.appgather.application.MyApplication;
import com.appgather.entity.Apps;
import com.appgather.util.DlgLoading;
import com.appgather.util.UnZIp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//应用商店活动
public class AppStoreActivity extends AppCompatActivity {

    //用于关闭的静态活动实例
    public static AppStoreActivity appStoreinstance;


    //用来记录下载的文件路径及文件名
    private String appPathUrl;
    private String appname;

    private WebView wv_appstore;
    private static String appStore_url="http://120.78.160.156/app_store-master/public_html/index/index/index.html";
    private String PATH = null;
    private DlgLoading mloadingDialog;
    //新建对话框，以防等待
    public  Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Toast.makeText(AppStoreActivity.this,"下载完成，前往设置",Toast.LENGTH_SHORT).show();
                //wv_appstore.loadUrl("file:///data/data/com.appgather/calendar/calendar.html");
                mloadingDialog.dismiss();

                //追加新的应用
                MyApplication.getApps().add(new Apps(1,appname,appPathUrl));
                //保存应用数据
                MyApplication.saveAppsdata_result();
               Intent intent=new Intent(AppStoreActivity.this,AppManageActivity.class);
               //intent.putExtra("app",new Apps(1,appname,appPathUrl));
               startActivity(intent);

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstore_layout);
        PATH = "/data/data/"+getPackageName()+"/";

        //初始化实例，便于销毁
        appStoreinstance=this;
        wv_appstore= (WebView) findViewById(R.id.wv_appstore);
        mloadingDialog=new DlgLoading(this);
        setWebView();
    };


     //可退回页面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK&&wv_appstore.canGoBack()){
            wv_appstore.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setWebView() {

        wv_appstore.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wv_appstore.loadUrl(url);
                return true;
            }
        });

        wv_appstore.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Toast.makeText(AppStoreActivity.this,"正在下载",Toast.LENGTH_SHORT).show();
                //开启下载线程
                new Thread(new DownLoadThread(url, PATH)).start();

                mloadingDialog.show();
            }
        });

        //设置WebView属性，能够执行Javascript脚本
        wv_appstore.getSettings().setJavaScriptEnabled(true);
        //设置可以访问文件
        wv_appstore.getSettings().setAllowFileAccess(true);
        //设置支持缩放
        wv_appstore.getSettings().setBuiltInZoomControls(true);
        wv_appstore.loadUrl(appStore_url);
    }

    class DownLoadThread implements Runnable {

        private String dlUrl;
        private String PATH;
        public DownLoadThread(String dlUrl, String PATH) {
            this.dlUrl= dlUrl;
            this.PATH = PATH;
        }

        public void run(){
            String fileName = getConnection(dlUrl, PATH);
            UnZIp.unZip(PATH, PATH+fileName);
            File file = new File(PATH+fileName);
            //删除压缩包
            file.delete();
            appname=fileName.substring(0,fileName.length()-4);
            //获取路径，想办法加入到文件当中去
            appPathUrl="file://"+PATH+appname+"/"+appname+".html";
            Log.d("zsyurl",appPathUrl);
            Message msg = new Message();
            msg.what = 1;
            myHandler.sendMessage(msg);
        }

        /**
         *从网页上下载zip
         * @param urlPath 下载地址
         * @param downLoadPath 下载路径
         */
        public String getConnection(String urlPath, String downLoadPath){
            String fileName = null;
            try {
                URL url = new URL(urlPath);
                //得到connection对象。
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置请求方式
                connection.setRequestMethod("GET");
                //连接
                connection.connect();

                //得到响应码
                int responseCode = connection.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    //得到响应流
                    InputStream inputStream = connection.getInputStream();
                    File dir = new File(downLoadPath);
                    if(!dir.exists()){
                        dir.mkdirs();
                        Log.d("file", "no exists");
                    }
                    else
                        Log.d("file", " exists");

                    fileName = connection.getHeaderField("Content-Disposition");
                    Log.d("url",fileName);
                    String[] dirs = fileName.split("/");
                    File file = new File(dir, dirs[2]);
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buf = new byte[1024*20];
                    //将内容读到byte数组中，同时返回读入的个数
                    //write方法将一个指定范围的Byte数组写入数据流
                    int len = -1;
                    while ((len = inputStream.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    return dirs[2];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //还需要再完成一个方法，对与app列表的修改

}