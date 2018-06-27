package com.appgather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.appgather.application.MyApplication;


//已经完成
public class SharedPreferenceUtil {

    private static String FILENAME="appdate";

    /**
     * CommitDate该方法是一个有返回值的同步的提交方式，true表示数据保存成功，false表示数据保存失败
     */
    public static boolean CommitData(String key,String date)
    {
        SharedPreferences sp= MyApplication.getContext().getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,date);
        return editor.commit();
    }

    /**
     *ApplyDate:是一个异步操作的保存数据的方法
     */
    public static void ApplyDate(String key,String date)
    {
        SharedPreferences sp= MyApplication.getContext().getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,date);
        editor.apply();
    }

    /*
    *获取数据
     */
    public static String getData(String key)
    {
        SharedPreferences sp= MyApplication.getContext().getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        //获得的是
        String str=sp.getString(key,"");
        Log.d("zsy",str);
        if(!str.isEmpty()){
            return str;
        }
        else {
            return null;
        }
    }
}
