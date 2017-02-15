package com.appgather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.appgather.R;
import com.appgather.view.TextWatcherForJudge;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_registername;
    private EditText et_registertel;
    private EditText et_registerpassword;

    private TextWatcher textWatcherForName;
    private TextWatcher textWatcherForPassword;
    private TextWatcher getTextWatcherForTel;
    private Button btn_register;
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
        setContentView(R.layout.register);

        btn_register=(Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogForSend();
            }
        });
        et_registername=(EditText)findViewById(R.id.et_registername);
        et_registertel=(EditText)findViewById(R.id.et_registertel);
        et_registerpassword=(EditText)findViewById(R.id.et_registerpassword);

        editViewListenInput();//监听编辑框是否都输入完全
    }
    /*
    * 弹出Dialog询问是否发送验证码
    * */
    private void alertDialogForSend(){
        final String tel=""+et_registertel.getText();
        AlertDialog.Builder senddialog =new AlertDialog.Builder(RegisterActivity.this);
        senddialog.setTitle("确认手机号码");
        senddialog.setMessage("我们将发送验证码短信到这个号码:\n+86  "+tel);
        senddialog.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent=new Intent(RegisterActivity.this,SecuritycodeActivity.class);
                intent.putExtra("extra_usertel",tel);//向填写验证码界面发送手机号-键为extra_usertel,值为tel
                startActivity(intent);
            }
        });
        senddialog.setNegativeButton("取消",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        senddialog.show();
    }

    //
    //监听文本框用户名和密码是否有输入
    private void editViewListenInput() {
        textWatcherForName = new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && et_registerpassword.getText().toString().length() != 0&&et_registertel.getText().toString().length() != 0) {
                    btn_register.setEnabled(true);
                } else {
                    btn_register.setEnabled(false);
                }
            }
        };
        et_registername.addTextChangedListener(textWatcherForName);//为注册昵称监听输入

        textWatcherForPassword = new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && et_registername.getText().toString().length() != 0&&et_registertel.getText().toString().length() != 0) {
                    btn_register.setEnabled(true);
                } else {
                    btn_register.setEnabled(false);
                }
            }
        };
        et_registerpassword.addTextChangedListener(textWatcherForPassword);//为注册密码监听输入

        getTextWatcherForTel = new TextWatcherForJudge() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && et_registername.getText().toString().length() != 0&&et_registerpassword.getText().toString().length() != 0) {
                    btn_register.setEnabled(true);
                } else {
                    btn_register.setEnabled(false);
                }
            }
        };
        et_registertel.addTextChangedListener(getTextWatcherForTel);//为注册密码监听输入

    }
}
