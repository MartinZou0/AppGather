package com.appgather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.appgather.R;
import com.appgather.entity.API_Register;
import com.appgather.sdk.API;
import com.appgather.util.DlgLoading;
import com.appgather.util.MD5;
import com.appgather.view.TextWatcherForJudge;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;
import okhttp3.MediaType;
import static com.appgather.R.id.et_registername;

public class SecuritycodeActivity extends AppCompatActivity {
    private API_Register mregister=new API_Register();
    private TextView tv_usertel;
    private EditText et_SecurityCode;
    private TextWatcher getTextWatcherForTel;
    private API_Register register;
    private String APPKEY="19d0dd727bd31";
    private String APPSECRET="0d27b9eb2adea0707844d81a62e8edae";
    private DlgLoading dlgLoading;//加载对话框
    public static final MediaType json=MediaType.parse("application/json; charset=utf-8");
    /**
     * 短信验证监听器
     */
    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //验证码验证成功后的操作
                    SetRegieterInfo();
                    RegisterhttpRequest();
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Log.d("zqh","获取验证码成功");

                    //获取验证码成功
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SecuritycodeActivity.this,"验证码输入错误",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    };

    /**
     * 注册网络请求
     */
    private void RegisterhttpRequest() {
        SetRegieterInfo();
        API.Register(mregister, new API.Login_Ret() {
            @Override
            public void ret(int Ret, final String Msg) {
                if(Ret==0)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dlgLoading.dismiss();
                            Toast.makeText(SecuritycodeActivity.this,Msg,Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else if(Ret==200)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dlgLoading.dismiss();
                            Toast.makeText(SecuritycodeActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        },SecuritycodeActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securitycode);
        initSms();
        getRegisterMsg();
        initView();

    }

    /**
     * 初始化sms
     */
    private void initSms() {
        SMSSDK.initSDK(this,APPKEY,APPSECRET);
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    /**(
     * 初始化
     */
    private void initView() {
        dlgLoading=new DlgLoading(SecuritycodeActivity.this);
        et_SecurityCode= (EditText) findViewById(R.id.et_SecurityCode);
        tv_usertel=(TextView)findViewById(R.id.tv_usertel);
        tv_usertel.setText(register.getUsername());
        ObtainSecurityCode();
        textChangeListens();

    }

    /**
     * 监听文本变化
     */
    private void textChangeListens() {
        et_SecurityCode.addTextChangedListener(new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(i==3)
               {
                   SMSSDK.submitVerificationCode("86",register.getUsername(),et_SecurityCode.getText().toString());
                   dlgLoading.show("正在验证中...");
               }
            }
        });

        getTextWatcherForTel = new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };
    }

    /**
     * 获取验证码
     */
    private void ObtainSecurityCode() {
        SMSSDK.getVerificationCode("86",tv_usertel.getText().toString(),new OnSendMessageHandler() {
            @Override
            public boolean onSendMessage(String s, String s1) {
                Log.d("xyz",s+"  "+s1);
                return false;
            }
        });
    }

    /**
     * 获取传递过来的注册信息
     */
    private void getRegisterMsg() {
        register=new API_Register();
        Intent intent=getIntent();
        register= (API_Register) intent.getSerializableExtra("API_Register");
    }

    private void SetRegieterInfo() {
        mregister.setUsername(register.getUsername());
        mregister.setNickName(register.getNickName());
        mregister.setPassword(MD5.digest(register.getPassword().getBytes()));
    }
}
