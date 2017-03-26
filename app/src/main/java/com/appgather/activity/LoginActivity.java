package com.appgather.activity;


import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.appgather.R;
import com.appgather.entity.Apps;
import com.appgather.entity.Classify;
import com.appgather.sdk.API;
import com.appgather.util.MD5;
import com.appgather.util.SharedPreferenceUtil;
import com.appgather.view.TextWatcherForJudge;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_loginname;
    private EditText et_loginpassword;
    private TextWatcher textWatcherForName;
    private TextWatcher textWatcherForPassword;
    private Button btn_login;
    private Button btn_register;
    private Button btn_forgetpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.login);
        getSupportActionBar().hide();
        initView();
    }


    private void initView() {
        et_loginpassword = (EditText) findViewById(R.id.et_loginpassword);
        et_loginname = (EditText) findViewById(R.id.et_loginname);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register=(Button)findViewById(R.id.btn_forregister);
        btn_forgetpassword=(Button)findViewById(R.id.btn_forgetpassword);
        btnListen();//按钮事件监听
        editviewFocus();//编辑框焦点事件监听
        editViewListenInput();//编辑框输入监听
        setEt_usertel();//设置从找回密码页面传来的手机号
    }

    //按钮监听器
    //
    private void btnListen(){
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_forgetpassword.setOnClickListener(this);
    }
    //
    //输入框焦点监听
    private void editviewFocus() {

        et_loginname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
            }
        });
        et_loginpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
            }
        });
    }

    //监听文本框用户名和密码是否有输入
    private void editViewListenInput() {
        textWatcherForName = new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && et_loginpassword.getText().toString().length() != 0) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }
            }
        };
        et_loginname.addTextChangedListener(textWatcherForName);

        textWatcherForPassword = new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && et_loginname.getText().toString().length() != 0) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }
            }
        };
        et_loginpassword.addTextChangedListener(textWatcherForPassword);

    }

    @Override()
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_forregister:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);break;
            case R.id.btn_login:
                Login();
                break;

            case R.id.btn_forgetpassword:
                Intent intent3=new Intent(LoginActivity.this,FindPassword.class);
                startActivity(intent3);
            default:break;
        }
    }

    /**
     * 登陆验证
     */
    private void Login() {
        Intent intent2=new Intent(LoginActivity.this,MainInterfaceActivity.class);
        startActivity(intent2);
        API.Login(et_loginname.getText().toString(), MD5.digest(et_loginpassword.getText().toString().getBytes()), new API.Login_Ret() {
            @Override
            public void ret(int Ret, final String Msg) {
                if(Ret==0)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,Msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if(Ret==200)
                {
                    //saveAppMsg();
                    //saveClassifyMsg();
                    Intent intent2=new Intent(LoginActivity.this,MainInterfaceActivity.class);
                    startActivity(intent2);
                    /*
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(LoginActivity.this,Msg,Toast.LENGTH_SHORT).show();

                        }
                    });*/

                }
            }
        },this);
    }
    private void setEt_usertel(){
        Intent intent=getIntent();//获得从找回密码页面传来的手机号
        String extra_phonenumber=intent.getStringExtra("extra_phonenumber");
        et_loginname.setText(extra_phonenumber);
    }

    /*
    *保存APP数据
     */
    private void saveAppMsg()
    {
        List<Apps> apps=new ArrayList<Apps>();
        apps.add(new Apps(1,"快递查询","file:///android_asset/kuaidi/index.html"));
        apps.add(new Apps(2,"测试应用","file:///android_asset/test/index.html"));
        apps.add(new Apps(3,"百度","https://www.baidu.com"));
        String str=JSON.toJSONString(apps);
       if(SharedPreferenceUtil.CommitDate("apps",str)) {
           Log.d("zqh","Apps数据保存成功");
       }
       else{
           Log.d("zqh","Apps数据保存失败");
       }
    }

    /*
      *保存分类标签数据
     */
    private void saveClassifyMsg()
    {
        List<Classify> classifies=new ArrayList<Classify>();
        classifies.add(new Classify("工作",1,true));
        classifies.add(new Classify("学习",2,true));
        classifies.add(new Classify("娱乐",3,true));
        classifies.add(new Classify("社交",4,true));
        classifies.add(new Classify("阅读",5,true));
        classifies.add(new Classify("购物",6,true));
        String str=JSON.toJSONString(classifies);
        if(SharedPreferenceUtil.CommitDate("classify",str)) {
            Log.d("zqh","分类标签数据保存成功");
        }
        else{
            Log.d("zqh","分类标签数据保存失败");
        }
    }
}
