package com.appgather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.appgather.R;

public class SecuritycodeActivity extends AppCompatActivity {
    private TextView tv_usertel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securitycode);
        tv_usertel=(TextView)findViewById(R.id.tv_usertel);
        Intent intent=getIntent();
        String usertel=intent.getStringExtra("extra_usertel");
        tv_usertel.setText("+86  "+usertel);

    }
}
