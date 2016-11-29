package com.appgather.sdk;

/**
 * Created by zspmh on 2016-11-29.
 */

public class API {
    public interface Login_Ret {
        void ret(int Ret, String Msg);
    }

    public static class Login_Send {
        String Username;
        String Password;
        public Login_Send(String _Username, String _Password) {
            Username = _Username;
            Password = _Password;
        }
    }

    public static void Login(String Username, String Password, Login_Ret ret) {
        System.out.println(Username);
        System.out.println(Password);
        ret.ret(0,"");
    }
    public static void Login(Login_Send send, Login_Ret ret) {
        Login(send.Username,send.Password,ret);
    }
}
