package com.appgather.sdk;

import android.app.Activity;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.appgather.entity.API_Login;
import com.appgather.entity.API_Register;
import com.appgather.entity.API_ResultData;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * Created by zspmh on 2016-11-29.
 */
public class API {
    private static final MediaType json=MediaType.parse("application/json; charset=utf-8");
    //private static String url="http://www.appsgather.com/public/index.php/api" ;
    //http://120.78.160.156/app_store-master/public_html/index.php/api
    private static String url="http://120.78.160.156/app_store-master/public_html/index.php/api" ;
    public static class SendObject {
        String act;
        Object content;
        //发送内容应该是用户的账号和密码
        public String getAct() {
            return act;
        }
        public Object getContent() {
            return content;
        }
    }
    public interface Login_Ret {
        void ret(int Ret, String Msg);
    }

    //实际调用的登录验证函数


    /**
     *
     * @param Send 用户的账号信息实体，作为发送对象
     * @param ret 实现接口函数
     * @param activity 活动
     */
    public static void Login(API_Login Send, final Login_Ret ret, final Activity activity){
        SendObject SO = new SendObject();
        SO.act="Login";
        SO.content = Send;
        OkHttpClient mOkHttpClient=new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON.toJSONString(SO));
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //超时或没有网络连接
            //注意：这里是后台线程！
            @Override
            public void onFailure(Call call, IOException e) {
                String str="网络异常";
                ret.ret(0,str);
            }

            //成功
            //注意：这里是后台线程！
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String a=response.body().string();
                API_ResultData resultData=new API_ResultData();
                resultData= JSON.parseObject(a,API_ResultData.class);
                if(resultData.getStatus().equals("1"))
                {
                    ret.ret(200,"登录成功");
                }
                else if(resultData.getStatus().equals("0"))
                {
                    ret.ret(0,"密码错误");
                }
            }

        });
    }
    public static void Login(String Username, String Password, Login_Ret ret, Activity activity) {
        Login(new API_Login(Username,Password),ret,activity);
    }
    public static void Register(API_Register Send, final Login_Ret ret, final Activity activity){
        SendObject SO = new SendObject();
        SO.act="Register";
        SO.content = Send;
        OkHttpClient mOkHttpClient=new OkHttpClient();
        Log.d("xyz",JSON.toJSONString(SO));
        RequestBody body = RequestBody.create(json, JSON.toJSONString(SO));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {



            @Override
            public void onFailure(Call call, IOException e) {
                String str="网络异常";
                ret.ret(0,str);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String str=response.body().string();
                Log.d("zsy",str);
                API_ResultData resultData= JSONObject.parseObject(str,API_ResultData.class);
                if(resultData.getStatus().equals("1"))
                {
                    ret.ret(200,"注册成功");
                }
                else if(resultData.getStatus().equals("20"))
                {
                    ret.ret(0,"用户名已被注册");
                }
                else if(resultData.getStatus().equals("30"))
                {
                    ret.ret(0,"密码格式错误");
                }
            }

        });
    }
    public static void Register(String Username, String Password,String telPhone, Login_Ret ret, Activity activity) {
        Register(new API_Register(Username,telPhone,Password),ret,activity);
    }
}
