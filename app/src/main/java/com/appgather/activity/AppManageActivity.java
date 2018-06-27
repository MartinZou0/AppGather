package com.appgather.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.appgather.R;
import com.appgather.adapter.AppManageAdapter;
import com.appgather.application.MyApplication;
import com.appgather.entity.Apps;
import com.appgather.entity.Classify;

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

    private int appposition;
    private int classifyposition;

    private List<Classify> classifies=new ArrayList<Classify>();
    //用来装载应用列表



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
        mappData=new ArrayList<Apps>();
        iv_back.setOnClickListener(this);
        setData();
        adapter=new AppManageAdapter(this,mappData);
        appManage_listView.setAdapter(adapter);
        appManage_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // parent相当于listview  Y适配器的一个指针，可以通过它来获得Y里装着的一切东西，再通俗点就是说告诉你，你点的是Y，不是X - -、
                // view是你点b item的view的句柄，就是你可以用这个view，来获得b里的控件的id后操作控件
                // position是b在Y适配器里的位置（生成listview时，适配器一个一个的做item，然后把他们按顺序排好队，在放到listview里，意思就是这个b是第position号做好的）
                //id是b在listview Y里的第几行的位置（很明显是第2行），大部分时候position和id的值是一样的，
                //短按弹出对话框，询问是否关闭
                appposition=position;
                //处于未显示状态
                Switch aswitch=adapter.getSwitch(position);
                if(aswitch.isChecked()){
                showCloseDialog("注意","你要关闭该应用显示吗？",aswitch);
                }else{
                    showOpenDialog("开启应用显示，选择分类",aswitch);
                }


                
            }
        });
    }

    private void showOpenDialog(String title, final Switch aswitch) {
        List<String> classifynamelist=new ArrayList<String>();
        //遍历整个应用列表，获取当前的列表信息
        //需要考虑那个页面是否显示
        //如果那个分页无法显示，那么就不会出现在当前列表里面

        classifies.clear();
        for(Classify classify:MyApplication.getClassifies()){
            classifies.add(classify);
        }
        for(int i=0;i<classifies.size();i++){
            Classify classify=classifies.get(i);
            if(classify.isSelect()){
                classifynamelist.add(classify.getClassName());
            }

        }
        String[] strings=new String[classifynamelist.size()];
        //转化未String数组
        classifynamelist.toArray(strings);
        AlertDialog.Builder builder=new AlertDialog.Builder(AppManageActivity.this);
        builder.setTitle(title);
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int classycode=classifies.get(which).getType();
                String classifyname=classifies.get(which).getClassName();
                mappData.get(appposition).setCategoryID(classycode);
                mappData.get(appposition).setIsOnDisplay(true);
                dialog.dismiss();
                Toast.makeText(AppManageActivity.this, "应用已在"+classifyname+"分类显示", Toast.LENGTH_SHORT).show();
                aswitch.setChecked(true);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AppManageActivity.this,"你取消更改",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    private void showCloseDialog(String title, String message, final Switch aswitch) {
        /* 创建对话框 */
        List<String> classifynamelist=new ArrayList<String>();
        //遍历整个应用列表，获取当前的列表信息
        for(Classify classify:MyApplication.getClassifies()){
            classifies.add(classify);
            classifynamelist.add(classify.getClassName());
        }
        String[] strings=new String[classifynamelist.size()];
        classifynamelist.toArray(strings);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                     mappData.get(appposition).setIsOnDisplay(false);
                    dialog.dismiss();
                    Toast.makeText(AppManageActivity.this, "你已关闭该应用", Toast.LENGTH_SHORT).show();
                    aswitch.setChecked(false);
                    mappData.get(appposition).setIsOnDisplay(false);


                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AppManageActivity.this,"你取消关闭该应用",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.create().show();

    }

    private void setData() {
        //获取现有的应用列表信息
        for(Apps appsdata:MyApplication.getApps()){
            mappData.add(appsdata);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            //退出及保存
            case R.id.back:
                //进行保存操作
                MyApplication.setApps(adapter.getAppList());
                MyApplication.saveAppsdata_result();
                Toast.makeText(this,"应用修改已完成",Toast.LENGTH_SHORT).show();

                AppStoreActivity.appStoreinstance.finish();
                finish();
                break;
        }
    }


    //改写返回键事件

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            MyApplication.setApps(adapter.getAppList());
            MyApplication.saveAppsdata_result();
            Toast.makeText(this,"应用修改已完成",Toast.LENGTH_SHORT).show();
            AppStoreActivity.appStoreinstance.finish();
            finish();
        }
        return true;
    }


}
