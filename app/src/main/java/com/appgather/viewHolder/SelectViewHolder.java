package com.appgather.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appgather.R;

/**
 * Created by qinghua on 2017/2/13.
 */

public class SelectViewHolder extends CustomItemHolder {

    public ImageView plus_item;//移除按钮

    public ImageView app_logo;//应用logo

    public TextView  tv_app_name;//应用的名称

    public RelativeLayout rl_select;

    public SelectViewHolder(View itemView) {
        super(itemView);
        //第一个减号
        plus_item= (ImageView) itemView.findViewById(R.id.plus_item);
        app_logo= (ImageView) itemView.findViewById(R.id.app_logo);
        tv_app_name= (TextView) itemView.findViewById(R.id.app_name);
        rl_select= (RelativeLayout) itemView.findViewById(R.id.rl_select);
        //用这个来进行已选择项目的装载
    }
}
