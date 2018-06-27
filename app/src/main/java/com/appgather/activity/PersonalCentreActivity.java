package com.appgather.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.appgather.R;



//已完成，个人中心的管理
public class PersonalCentreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinformation);
        getSupportActionBar().hide();
    }
}
