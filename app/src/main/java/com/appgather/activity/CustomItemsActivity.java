package com.appgather.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.appgather.R;
import com.appgather.adapter.MyRecycleViewAdapter;
import com.appgather.entity.AppMsg;
import com.appgather.entity.CustomItemMsg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinghua on 2017/2/13.
 */

public class CustomItemsActivity extends AppCompatActivity implements View.OnClickListener {

    private List<CustomItemMsg> selectDate;

    private List<CustomItemMsg> noselectDate;

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

            CustomItemMsg appMsg=new CustomItemMsg("工作",1);
            selectDate.add(appMsg);

            CustomItemMsg appMsg1=new CustomItemMsg("学习",1);
            selectDate.add(appMsg1);

            CustomItemMsg appMsg2=new CustomItemMsg("娱乐",1);
            selectDate.add(appMsg2);

            CustomItemMsg appMsg3=new CustomItemMsg("社交",2);
            noselectDate.add(appMsg3);

            CustomItemMsg appMsg4=new CustomItemMsg("阅读",2);
            noselectDate.add(appMsg4);

            CustomItemMsg appMsg5=new CustomItemMsg("购物",2);
            noselectDate.add(appMsg5);

    }

    private void initView() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        recyclerView= (RecyclerView) findViewById(R.id.recycleView_item);
        selectDate=new ArrayList<CustomItemMsg>();
        noselectDate=new ArrayList<CustomItemMsg>();
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
                Intent intent1=new Intent();
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("SelectItem", (Serializable) adapter.getSelectList());
                intent1.putExtras(bundle1);
                CustomItemsActivity.this.setResult(1,intent1);
                CustomItemsActivity.this.finish();
                break;
        }
    }
}
