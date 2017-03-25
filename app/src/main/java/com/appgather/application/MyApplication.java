package com.appgather.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.appgather.entity.Apps;
import com.appgather.entity.Classify;
import com.appgather.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinghua on 2017/3/17.
 */

public class MyApplication extends Application {

    private static Context mContext;
    private static List<Apps> apps;//应用集合
    private static List<Classify> classifies;//分类集合
    private static String APPS="apps";
    private static String CLASSIFY="classify";
    @Override
    public void onCreate(){
        mContext = getApplicationContext();
        getAppsMsg();
        getClassfyMsg();
        ToString();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setApps(List<Apps> apps) {
        MyApplication.apps = apps;
    }

    public static List<Apps> getApps() {
        return apps;
    }

    public static List<Classify> getClassifies() {
        return classifies;
    }

    public static void setClassifies(List<Classify> classifies) {
        MyApplication.classifies = classifies;
    }

    public static void ToString()
    {
        for(Apps app:apps) {
            Log.d("zqh",app+"");
        }

        for(Classify classify:classifies) {
            Log.d("zqh",classify+"");
        }
    }
    private  void getAppsMsg()
    {
        apps=new ArrayList<Apps>();
        String data= SharedPreferenceUtil.getDate(APPS);
        if(data!=null)
        {
            apps.addAll(JSONObject.parseArray(data,Apps.class));
            Log.d("zqh","APPS数据读取成功");
        }
        else {
            Log.d("zqh","APPS访问数据不存在");
        }
    }

    /*
    *获取分类标签数据
    */
    private void getClassfyMsg()
    {
        classifies=new ArrayList<Classify>();
        String data= SharedPreferenceUtil.getDate(CLASSIFY);
        if(data!=null)
        {
            classifies.addAll(JSONObject.parseArray(data,Classify.class));
            Log.d("zqh","分类标签数据读取成功");
        }
        else {
            Log.d("zqh","分类标签访问数据不存在");
        }
    }

    /*
    *保存分类标签的信息
     */
    public static void saveClassfyMsg()
    {
        String data= JSON.toJSONString(classifies);
        if(SharedPreferenceUtil.CommitDate(CLASSIFY,data))
        {
            Log.d("zqh","分类标签保存成功！");
        }else{
            Log.d("zqh","分类标签保存失败！");
        }

    }

}
