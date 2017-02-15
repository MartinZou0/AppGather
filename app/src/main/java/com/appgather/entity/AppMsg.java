package com.appgather.entity;

import java.io.Serializable;

/**
 * Created by qinghua on 2016/11/30.
 */

public class AppMsg implements Serializable {

    private String appnamae;
    private  String appUrl;
    private  int type;
    public AppMsg() {
    }

    public AppMsg(String appnamae, String appUrl) {
        this.appnamae = appnamae;
        this.appUrl = appUrl;
    }

    public String getAppnamae() {
        return appnamae;
    }

    public void setAppnamae(String appnamae) {
        this.appnamae = appnamae;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
