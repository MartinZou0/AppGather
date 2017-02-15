package com.appgather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.appgather.R;
import com.appgather.adapter.AppManageAdapter;
import com.appgather.entity.AppMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinghua on 2017/2/14.
 */

public class AppManageActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView appManage_listView;

    private ImageView iv_back;

    private AppManageAdapter adapter;

    private List<AppMsg> mDate;

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
        setDate();
        adapter=new AppManageAdapter(this,mDate);
        appManage_listView.setAdapter(adapter);
    }

    private void setDate() {
        mDate=new ArrayList<AppMsg>();
        mDate.add(new AppMsg("饿了么","http://www.baidu.com"));
        mDate.add(new AppMsg("美团","http://www.baidu.com"));
        mDate.add(new AppMsg("百度外卖","http://www.baidu.com"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
        }
    }
}
