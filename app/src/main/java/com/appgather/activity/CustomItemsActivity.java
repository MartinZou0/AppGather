package com.appgather.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.appgather.R;
import com.appgather.adapter.MyRecycleViewAdapter;
import com.appgather.application.MyApplication;
import com.appgather.entity.Classify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinghua on 2017/2/13.
 */

public class CustomItemsActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Classify> selectDate;

    private List<Classify> noselectDate;

    private MyRecycleViewAdapter adapter;

    private RecyclerView recyclerView;

    private ImageView iv_back;//返回键

    private TextView tv_ok;//完成键

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customitemsactivity);
        initView();

    }




    /*
    *设置数据
     */
    private void setDate() {

        for(Classify classify:MyApplication.getClassifies())
        {
            if(classify.isSelect())
            {
                selectDate.add(classify);
            }
            else{
                noselectDate.add(classify);
            }
        }

    }

    private void initView() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        recyclerView= (RecyclerView) findViewById(R.id.recycleView_item);
        selectDate=new ArrayList<Classify>();
        noselectDate=new ArrayList<Classify>();
        setDate();
        adapter=new MyRecycleViewAdapter(this);
        adapter.setDate(selectDate,noselectDate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ok:
                MyApplication.setClassifies(adapter.getSelectList());
                MyApplication.getClassifies().addAll(adapter.getNoSelectList());
                MyApplication.saveClassfyMsg();
                finish();
                break;
        }
    }


}
