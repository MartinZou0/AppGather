package com.appgather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appgather.R;
import com.appgather.entity.Apps;

import java.util.List;

/**
 * Created by qinghua on 2017/2/14.
 */

public class AppManageAdapter extends BaseAdapter {

    private List<Apps> appList;
    private LayoutInflater inflater;

    public AppManageAdapter(Context context,List<Apps> appList) {
        this.appList = appList;
        inflater=LayoutInflater.from(context);
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
        ViewHolder viewHolder=null;
        if(view==null)
        {
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.appmanage_item,null);
            viewHolder.iv_appLogo= (ImageView) view.findViewById(R.id.appmanage_logo);
            viewHolder.tv_appName= (TextView) view.findViewById(R.id.Appmanage_name);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.tv_appName.setText(appList.get(i).getAppName());
        return view;
    }

    class ViewHolder
    {
        private ImageView iv_appLogo;
        private TextView tv_appName;
    }
}
