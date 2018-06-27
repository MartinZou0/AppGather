package com.appgather.entity;

import java.io.Serializable;



//注册实体
    //已编程
public class API_Register implements Serializable {
    private String Username;//用户名即手机号
    private  String Password;//密码
    private String VerifyCode;//验证码
    private String  nickName;//昵称
    private String MsgID;

    public API_Register(String username, String password, String nickName) {
        Username = username;
        Password = password;
        this.nickName = nickName;
    }

    public API_Register() {
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
