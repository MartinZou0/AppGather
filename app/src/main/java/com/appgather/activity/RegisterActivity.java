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
import com.appgather.entity.API_Register;
import com.appgather.view.TextWatcherForJudge;


//注册活动，因交互问题，该功能无法正常眼石
public class RegisterActivity extends AppCompatActivity {

    private EditText et_registername;
    private EditText et_registertel;
    private EditText et_registerpassword;
    private TextWatcher textWatcherForName;
    private TextWatcher textWatcherForPassword;
    private TextWatcher getTextWatcherForTel;
    private Button btn_register;

    private API_Register register;//存放注册信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.register);
        initView();
        editViewListenInput();//监听编辑框是否都输入完全
    }

    /**
     * 初始化
     */

    private void initView() {
        register=new API_Register();
        btn_register=(Button)findViewById(R.id.btn_register);
        et_registername=(EditText)findViewById(R.id.et_registername);
        et_registertel=(EditText)findViewById(R.id.et_registertel);
        et_registerpassword=(EditText)findViewById(R.id.et_registerpassword);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogForSend();
            }
        });
    }

    /*
    * 弹出Dialog询问是否发送验证码
    * */
    private void alertDialogForSend(){
        setRegister();
        final String tel=""+et_registertel.getText();
        AlertDialog.Builder senddialog =new AlertDialog.Builder(RegisterActivity.this);
        senddialog.setTitle("确认手机号码");
        senddialog.setMessage("我们将发送验证码短信到这个号码:\n+86  "+tel);
        senddialog.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent=new Intent(RegisterActivity.this,SecuritycodeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("API_Register",register);
                intent.putExtras(bundle);

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

    /**
     * 获取用户输入的信息
     */
    private void setRegister() {
        register.setNickName(et_registername.getText().toString());
        register.setPassword(et_registerpassword.getText().toString());
        register.setUsername(et_registertel.getText().toString());
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
