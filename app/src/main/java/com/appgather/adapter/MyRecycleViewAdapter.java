package com.appgather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appgather.R;
import com.appgather.entity.AppMsg;
import com.appgather.entity.CustomItemMsg;
import com.appgather.viewHolder.NoSelectTitleViewHolder;
import com.appgather.viewHolder.NoSelectViewHolder;
import com.appgather.viewHolder.SelectTitleViewHolder;
import com.appgather.viewHolder.SelectViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qinghua on 2017/2/13.
 */

public class MyRecycleViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CustomItemMsg> SelectList=new ArrayList<CustomItemMsg>();

    private List<CustomItemMsg> NoSelectList=new ArrayList<CustomItemMsg>();

    private LayoutInflater inflater;

    private Context mContext;

    private Map<Integer,Integer> Realposition=new HashMap<Integer, Integer>();

    private List<Integer> typeList=new ArrayList<Integer>();

    public MyRecycleViewAdapter( Context context) {
        mContext=context;
        inflater=LayoutInflater.from(context);

    }

    public  void setDate(List<CustomItemMsg> SelectList , List<CustomItemMsg> NoSelectList)
    {
        Realposition.clear();
        typeList.clear();
        this.SelectList = SelectList;
        this.NoSelectList = NoSelectList;
        Realposition.put(3,0);
        typeList.add(3);
        Realposition.put(1,1);
        for(int i=0;i<SelectList.size();i++)
        {
            typeList.add(1);
        }
        Realposition.put(4,1+SelectList.size());
        typeList.add(4);
        Realposition.put(2,2+SelectList.size());
        for(int i=0;i<NoSelectList.size();i++)
        {
            typeList.add(2);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==1)
        {
            view = inflater.inflate(R.layout.select_item,parent, false);
            SelectViewHolder selectViewHolder=new SelectViewHolder(view);
            return selectViewHolder;
        }
        else if(viewType==2)
        {
            view = inflater.inflate(R.layout.noselect_item,parent, false);
            NoSelectViewHolder noSelectViewHolder=new NoSelectViewHolder(view);
            return noSelectViewHolder;
        }

        else if(viewType==3)
        {
            view = inflater.inflate(R.layout.select_item_title,parent, false);
            SelectTitleViewHolder selectTitleViewHolder=new SelectTitleViewHolder(view);
            return selectTitleViewHolder;
        }

        else if(viewType==4)
        {
            view = inflater.inflate(R.layout.noselect_item_title,parent, false);
            NoSelectTitleViewHolder noselectTitleViewHolder=new NoSelectTitleViewHolder(view);
            return noselectTitleViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int index=position;
        if(holder instanceof SelectViewHolder) {
            SelectViewHolder selectViewHolder=new SelectViewHolder(holder.itemView);
            selectViewHolder.tv_app_name.setText(SelectList.get(index-1).getItem_namae());
            selectViewHolder.plus_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("zqh",NoSelectList.size()+","+SelectList.size());
                    NoSelectList.add(SelectList.get(index-1));
                    SelectList.remove(index-1);
                    Log.d("zqh",NoSelectList.size()+","+SelectList.size());
                    setDate(SelectList ,NoSelectList);
                    notifyDataSetChanged();
                }
            });
        } else if (holder instanceof  NoSelectViewHolder){
            final NoSelectViewHolder noSelectViewHolder=new NoSelectViewHolder(holder.itemView);
            noSelectViewHolder.tv_app_name.setText(NoSelectList.get(index-2-SelectList.size()).getItem_namae());
            noSelectViewHolder.add_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("xyz",""+index);
                    SelectList.add(NoSelectList.get(index-2-SelectList.size()));
                    NoSelectList.remove(index-1-SelectList.size());
                    setDate(SelectList ,NoSelectList);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
        {
            return 3;
        }
        else if(position>0&&position<Realposition.get(4)){
            return 1;
        }
        else if(position==Realposition.get(4)){
            return 4;
        }
        else {
            return 2;
        }

    }

    public List<CustomItemMsg> getSelectList() {
        return SelectList;
    }
}
