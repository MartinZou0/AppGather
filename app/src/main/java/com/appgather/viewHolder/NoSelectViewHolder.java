package com.appgather.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appgather.R;


public class NoSelectViewHolder extends CustomItemHolder {

    public ImageView add_item;//添加按钮

    public ImageView app_logo;//应用logo

    public TextView tv_app_name;//应用的名称

    public RelativeLayout rl_noSelect;

    public NoSelectViewHolder(View itemView) {
        super(itemView);
        add_item= (ImageView) itemView.findViewById(R.id.add_item);
        app_logo= (ImageView) itemView.findViewById(R.id.app_logo);
        tv_app_name= (TextView) itemView.findViewById(R.id.app_name);
        rl_noSelect= (RelativeLayout) itemView.findViewById(R.id.rl_noSelect);
        //用来加载未选择项目的布局
    }
}
