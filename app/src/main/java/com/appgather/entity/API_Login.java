package com.appgather.entity;

/**
 * Created by qinghua on 2016/11/29.
 */

public class API_Login {
    private String Username;//用户名
    private  String Password;//密码

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public API_Login(String username, String password) {
        Username = username;
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }
}