package com.appgather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.appgather.R;
import com.appgather.adapter.AppManageAdapter;
import com.appgather.application.MyApplication;
import com.appgather.entity.Apps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinghua on 2017/2/14.
 */


//正在完成
//采用ListView显示应用管理
public class AppManageActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView appManage_listView;

    private ImageView iv_back;

    private AppManageAdapter adapter;

    private List<Apps> mappData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.appmanage_layout);
        initView();
    }

    private void initView() {
        appManage_listView= (ListView) findViewById(R.id.lv_appManage);
        iv_back= (ImageView) findViewById(R.id.back);
        iv_back.setOnClickListener(this);
        setData();
        adapter=new AppManageAdapter(this,mappData);
        appManage_listView.setAdapter(adapter);
    }

    private void setData() {
        //获取现有的应用列表信息
        mappData=new ArrayList<Apps>();
        for(Apps appsdata:MyApplication.getApps()){
            mappData.add(appsdata);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                //进行保存操作
                MyApplication.setApps(adapter.getAppList());
                MyApplication.saveAppsdata_result();
                Toast.makeText(this,"应用修改已完成",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
