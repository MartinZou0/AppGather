package com.appgather.entity;

/**
 * Created by qinghua on 2016/11/30.
 */

public class API_Result {
   private  String ret;
    private String mes;

    public API_Result(String ret, String mes) {
        this.ret = ret;
        this.mes = mes;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
