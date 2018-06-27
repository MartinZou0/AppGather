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

/*
* Application类是为了那些需要保存全局变量设计的基本类，
* 你可以在AndroidManifest.xml的<application>标签中进行自己的实现，这样的结果是：当你的application或者包被建立的时候将引起那个类被建立。
理解：就是说application是用来保存全局变量的，并且是在package创建的时候就跟着存在了。
所以当我们需要创建全局变量的时候，不需 要再像j2se那样需要创建public权限的static变量，而直接在application中去实现。
只需要调用Context的getApplicationContext或者Activity的getApplication方法来获得一个application对象，再做出相应 的处理。
* */


/*对于应用信息以及基本分类信息*/
public class MyApplication extends Application {

    private static Context mContext;
    private static List<Apps> apps;//应用集合
    private static List<Classify> classifies;//分类集合
    private static String APPS="apps";
    private static String CLASSIFY="classify";

    //get，setter方法
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

    //程序的入口
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        getAppsMsg();
        getClassfyMsg();
        ToString();
    }


    private  void getAppsMsg()
    {
        apps=new ArrayList<Apps>();
        String data= SharedPreferenceUtil.getData(APPS);
        if(data!=null)
        {
            apps.addAll(JSONObject.parseArray(data,Apps.class));
            Log.d("zqh","APPS数据读取成功");
        }
        else {
            saveAppMsg();
            Log.d("zqh","APPS访问数据不存在");
            //回调过来
            getAppsMsg();
        }
    }

    /*
    *获取分类标签数据
    */
    private void getClassfyMsg()
    {
        classifies=new ArrayList<Classify>();
        String data= SharedPreferenceUtil.getData(CLASSIFY);
        if(data!=null)
        {
            classifies.addAll(JSONObject.parseArray(data,Classify.class));
            Log.d("zqh","分类标签数据读取成功");
        }
        else {
            saveClassifyMsg();
            data= SharedPreferenceUtil.getData(CLASSIFY);
            classifies.addAll(JSONObject.parseArray(data,Classify.class));
        }
    }

    /*
    *保存分类标签的信息
     */
    public static void saveClassfyMsg_result()
    {
        String data= JSON.toJSONString(classifies);
        if(SharedPreferenceUtil.CommitData(CLASSIFY,data))
        {
            Log.d("zqh","分类标签保存成功！");
        }else{
            Log.d("zqh","分类标签保存失败！");
        }

    }


    /*
      *创建默认的分类标签数据
     */
    private void saveClassifyMsg()
    {
        List<Classify> classifies=new ArrayList<Classify>();
        classifies.add(new Classify("工作",1,true));
        classifies.add(new Classify("学习",2,true));
        classifies.add(new Classify("娱乐",3,true));
        classifies.add(new Classify("社交",4,true));
        classifies.add(new Classify("阅读",5,true));
        classifies.add(new Classify("购物",6,true));
        String str=JSON.toJSONString(classifies);
        if(SharedPreferenceUtil.CommitData("classify",str)) {
            Log.d("zqh","分类标签数据保存成功");
        }
        else{
            Log.d("zqh","分类标签数据保存失败");
        }
    }

    /*
    *加载默认的APP
    */
    private void saveAppMsg()
    {
        List<Apps> apps=new ArrayList<Apps>();
        //apps.add(new Apps(1,"百度","https://www.baidu.com"));
        apps.add(new Apps(1,"快递查询","file:///android_asset/kuaidi/index.html"));
        apps.add(new Apps(2,"测试应用","file:///android_asset/test/index.html"));
       apps.add(new Apps(3,"百度","https://www.baidu.com"));
       //尽量完成动态加载
        //apps.add(new Apps(4,"日历","file:///data/data/com.appgather/calendar/calendar.html"));
        //生成Json字符串格式
        String str=JSON.toJSONString(apps);
        if(SharedPreferenceUtil.CommitData("apps",str)) {
            Log.d("zqh","Apps数据保存成功");
        }
        else{
            Log.d("zqh","Apps数据保存失败");
        }
    }


    public static void saveAppsdata_result(){
        String data= JSON.toJSONString(apps);
        if(SharedPreferenceUtil.CommitData(APPS,data))
        {
            Log.d("zsy","应用更改保存成功保存成功！");
        }else{
            Log.d("zsy","应用更改保存失败保存失败！");
        }

    }
}
