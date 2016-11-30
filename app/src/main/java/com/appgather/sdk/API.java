package com.appgather.sdk;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.appgather.entity.API_Login;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * Created by zspmh on 2016-11-29.
 */
public class API {
    private static String url="http://appsgather.com/api.php" ;
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

    public static void Login(API_Login Send, final Login_Ret ret){
        SendObject SO = new SendObject();
        SO.act="Login";
        SO.content = Send;
        OkHttpClient mOkHttpClient=new OkHttpClient();
        Log.d("xyz",SO.act);
        Log.d("xyz",JSON.toJSONString(SO.content));
        Log.d("xyz",JSON.toJSONString(SO));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(SO));
       // RequestBody formBody = new FormBody.Builder()
                //.add("", JSON.toJSONString(SO))
               // .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ret.ret(200,e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                ret.ret(0,str);
            }

        });
    }
    public static void Login(String Username, String Password, Login_Ret ret) {
        Login(new API_Login(Username,Password),ret);
    }

}
