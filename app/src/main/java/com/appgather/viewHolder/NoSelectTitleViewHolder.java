package com.appgather.viewHolder;

import android.view.View;
import android.widget.ImageView;

import com.appgather.R;

/**
 * Created by qinghua on 2017/2/13.
 */

//未选择viewholder
public class NoSelectTitleViewHolder extends CustomItemHolder {

    public ImageView iv_xinjian;//新建按钮
    public NoSelectTitleViewHolder(View itemView) {
        super(itemView);
        iv_xinjian= (ImageView) itemView.findViewById(R.id.iv_xinjian);
    }
}
