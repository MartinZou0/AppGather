package com.appgather.entity;

import java.util.Date;


//配置信息
public class ConfigMsg {

    private int CategoryID;//分类ID
    private int UserID;//用户ID
    private String AppName;//app名
    private String AppDetail;//app描述
    private String SymbolName;//app内部名
    private String Note;//备注
    private String Icon;//图标
    private int VersionID;//主版本号
    private Date Time;//添加时间

    public ConfigMsg()
    {

    }

    public ConfigMsg(int categoryID, int userID, String appName, String appDetail,
                     String symbolName, String note, String icon, int versionID, Date time) {
        CategoryID = categoryID;
        UserID = userID;
        AppName = appName;
        AppDetail = appDetail;
        SymbolName = symbolName;
        Note = note;
        Icon = icon;
        VersionID = versionID;
        Time = time;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppDetail() {
        return AppDetail;
    }

    public void setAppDetail(String appDetail) {
        AppDetail = appDetail;
    }

    public String getSymbolName() {
        return SymbolName;
    }

    public void setSymbolName(String symbolName) {
        SymbolName = symbolName;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public int getVersionID() {
        return VersionID;
    }

    public void setVersionID(int versionID) {
        VersionID = versionID;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }
}
