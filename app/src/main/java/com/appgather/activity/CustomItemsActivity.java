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


//修改标签页活动，由RecycleVIew组成组成
public class CustomItemsActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Classify> selectData;

    private List<Classify> noselectData;

    private MyRecycleViewAdapter adapter;// RecycleVIew适配器

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


    private void initView() {
        //实例化各个部件
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(this);
        recyclerView= (RecyclerView) findViewById(R.id.recycleView_item);
        selectData=new ArrayList<Classify>();
        noselectData=new ArrayList<Classify>();
        //设置现有的添加分类和已添加分类情况
        setData();
        //传入本活动的上下文
        adapter=new MyRecycleViewAdapter(this);
        adapter.setData(selectData,noselectData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置为垂直布局
        recyclerView.setAdapter(adapter);
        //装在适配器
    }


    /*
    *获取已添加分类和未添加分类信息
     */
    private void setData() {
        //遍历分类数据
        //如果isSelect为true,则添加到已添加分组
        //否则就添加到未添加分组
        for(Classify classify:MyApplication.getClassifies())
        {
            if(classify.isSelect())
            {
                selectData.add(classify);
            }
            else{
                noselectData.add(classify);
            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_ok:{
               //点击OK之后才进行更新操作
                //以更新后的为准
                MyApplication.setClassifies(adapter.getSelectList());
                MyApplication.getClassifies().addAll(adapter.getNoSelectList());
                //保存更改
                MyApplication.saveClassfyMsg_result();
                finish();
                break;}
        }
    }


}
