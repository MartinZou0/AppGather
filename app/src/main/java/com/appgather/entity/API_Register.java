package com.appgather.entity;

/**
 * Created by qinghua on 2016/11/29.
 */

public class API_Register {
    private String Username;//用户名
    private  String Password;//密码
    private String VerifyCode;//验证码
    private String MsgID;

    public String getUsername() {

        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getVerifyCode() {
        return VerifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        VerifyCode = verifyCode;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }
}
