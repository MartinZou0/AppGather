package com.appgather.entity;

import java.io.Serializable;

/**
 * Created by qinghua on 2017/3/21.
 * 应用信息实体类
 */


//已完成
public class Apps implements Serializable{
    private int CategoryID;//分类ID
    private String  AppName;//应用名
    private String url;//应用访问地址
    private  boolean isondisplay;//是否在显示

    public Apps()
    {

    }

    public Apps(int categoryID, String appName, String url) {
        //初始值默认为显示
        CategoryID = categoryID;
        AppName = appName;
        this.url = url;
        this.isondisplay=true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public boolean getIsondisplay() {
        return isondisplay;
    }

    public void setIsOnDisplay(boolean isondisplay) {
        this.isondisplay = isondisplay;
    }

    @Override
    public String toString() {
        return "Apps{" +
                "CategoryID=" + CategoryID +
                ", AppName='" + AppName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
