package com.appgather.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appgather.R;
import com.appgather.activity.AppManageActivity;
import com.appgather.application.MyApplication;
import com.appgather.entity.Apps;
import com.appgather.entity.Classify;

import java.util.List;

/**
 * Created by qinghua on 2017/2/14.
 */
//应用的现实列表

public class AppManageAdapter extends BaseAdapter {
    private int position;
    private List<Apps> appList;
    private LayoutInflater inflater;

    public AppManageAdapter(Context context,List<Apps> appList) {
        this.appList = appList;
        inflater=LayoutInflater.from(context);
    }


    public List<Apps> getAppList() {
        return appList;
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int i) {
        return appList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        position=i;
        ViewHolder viewHolder=null;
        //采用ViewHolder优化
        if(view==null)
        {
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.appmanage_item,null);
            //实例化控件
            viewHolder.iv_appLogo= (ImageView) view.findViewById(R.id.appmanage_logo);
            viewHolder.tv_appName= (TextView) view.findViewById(R.id.Appmanage_name);
            viewHolder.appswitch=(Switch)view.findViewById(R.id.appswitch);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.tv_appName.setText(appList.get(i).getAppName());
        viewHolder.appswitch.setChecked(appList.get(i).getIsondisplay());
        //设置滑动switch事件
        viewHolder.appswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //b表示当前状态
                if(b){
                    appList.get(position).setIsOnDisplay(true);
                    //弹出对话框，并要求用户选择要显示的分类
                    AlertDialog.Builder builder=new AlertDialog.Builder(MyApplication.getContext());
                    builder.setTitle("请选择该应用显示标签页");

                    for(Classify classify:MyApplication.getClassifies()){

                    }
                    Toast.makeText(MyApplication.getContext(),"应用已开启",Toast.LENGTH_SHORT).show();
                }else{
                    appList.get(position).setIsOnDisplay(false);
                    Toast.makeText(MyApplication.getContext(),"应用已关闭关闭",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    class ViewHolder
    {
        private ImageView iv_appLogo;
        private TextView tv_appName;
        private Switch appswitch;
    }
}
