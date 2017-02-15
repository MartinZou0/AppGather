package com.appgather.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.appgather.R;

public class SecuritycodeActivity extends AppCompatActivity {
    private TextView tv_usertel;

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
        setContentView(R.layout.activity_securitycode);
        tv_usertel=(TextView)findViewById(R.id.tv_usertel);
        Intent intent=getIntent();
        String extra_usertel=intent.getStringExtra("extra_usertel");
        tv_usertel.setText("+86  "+extra_usertel);

    }
}
