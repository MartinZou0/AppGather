package com.appgather.entity;

import java.io.Serializable;

/**
 * Created by qinghua on 2016/11/30.
 */

public class AppMsg implements Serializable {
    public AppMsg() {
    }

    private String appnamae;
    private  String appUrl;

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
}
