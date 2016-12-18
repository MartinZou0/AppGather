package com.appgather.sdk;

import android.app.Activity;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.appgather.entity.API_Login;
import com.appgather.entity.API_Register;
import com.appgather.entity.ResultData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static String url="http://www.appsgather.com/public/index.php/api" ;
    public static class SendObject {
        String act;
        Object content;
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

    public static void Login(API_Login Send, final Login_Ret ret, final Activity activity){
        SendObject SO = new SendObject();
        SO.act="Login";
        SO.content = Send;
        OkHttpClient mOkHttpClient=new OkHttpClient();
        Log.d("xyz",JSON.toJSONString(SO));
        RequestBody body = RequestBody.create(json, JSON.toJSONString(SO));
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zzz",e.getMessage());
                String str="网络异常";
                ret.ret(0,str);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String a=response.body().string();
                Log.d("zzz","response="+a);
                ResultData resultData=new ResultData();
                resultData= JSON.parseObject(a,ResultData.class);
                if(resultData.getStatus().equals("1"))
                {
                    ret.ret(200,resultData.getInfo());
                }
                else
                {
                   ret.ret(0,resultData.getInfo());
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
                Log.d("zzz",e.getMessage());
                String str="网络异常";
                ret.ret(0,str);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String str=response.body().string();
                Log.d("xyz",str);
                ResultData resultData= JSONObject.parseObject(str,ResultData.class);
                if(resultData.getStatus().equals("1"))
                {
                    ret.ret(200,resultData.getInfo());
                }
                else
                {
                    ret.ret(0,resultData.getInfo());
                }
            }

        });
    }
    public static void Register(String Username, String Password,String telPhone, Login_Ret ret, Activity activity) {
        Register(new API_Register(Username,telPhone,Password),ret,activity);
    }
    public static  String unescapeUnicode(String str){
        StringBuffer b=new StringBuffer();
        Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
        while(m.find())
            b.append((char)Integer.parseInt(m.group(1),16));
        return b.toString();
    }
}
