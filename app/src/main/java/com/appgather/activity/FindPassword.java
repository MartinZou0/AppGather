package com.appgather.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.appgather.R;
public class FindPassword extends AppCompatActivity {

    private Button btn_sendpassword;
    private EditText et_phonenumber;
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
        setContentView(R.layout.activity_find_password);
        initView();
        setBtn_sendpassword();
    }
    private void initView(){
        btn_sendpassword=(Button)findViewById(R.id.btn_sendpassword);
        et_phonenumber=(EditText)findViewById(R.id.et_phonenumber);
    }
    private void setBtn_sendpassword(){
        btn_sendpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FindPassword.this,LoginActivity.class);
                intent.putExtra("extra_phonenumber",et_phonenumber.getText().toString());
                startActivity(intent);
            }
        });
    }
}
