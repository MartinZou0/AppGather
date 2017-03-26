package com.appgather.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.appgather.R;
import com.appgather.application.MyApplication;
import com.appgather.entity.Classify;
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

    private List<Classify> SelectList=new ArrayList<Classify>();

    private List<Classify> NoSelectList=new ArrayList<Classify>();

    private LayoutInflater inflater;

    private Context mContext;

    private Map<Integer,Integer> Realposition=new HashMap<Integer, Integer>();

    private List<Integer> typeList=new ArrayList<Integer>();


    public MyRecycleViewAdapter( Context context) {
        mContext=context;
        inflater=LayoutInflater.from(context);

    }

    public  void setDate(List<Classify> SelectList , List<Classify> NoSelectList)
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int index=position;
        if(holder instanceof SelectViewHolder) {
            SelectViewHolder selectViewHolder=new SelectViewHolder(holder.itemView);
            selectViewHolder.tv_app_name.setText(SelectList.get(index-1).getClassName());
            selectViewHolder.plus_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectList.get(index-1).setSelect(false);
                    NoSelectList.add(SelectList.get(index-1));
                    SelectList.remove(index-1);
                    setDate(SelectList ,NoSelectList);
                    notifyDataSetChanged();
                }
            });
            selectViewHolder.rl_select.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    show_deleteDialog("select",index-1);
                    return true;
                }
            });

        } else if (holder instanceof  NoSelectViewHolder){
            final NoSelectViewHolder noSelectViewHolder=new NoSelectViewHolder(holder.itemView);
            noSelectViewHolder.tv_app_name.setText(NoSelectList.get(index-2-SelectList.size()).getClassName());
            noSelectViewHolder.add_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoSelectList.get(index-2-SelectList.size()).setSelect(true);
                    SelectList.add(NoSelectList.get(index-2-SelectList.size()));
                    NoSelectList.remove(index-1-SelectList.size());
                    setDate(SelectList ,NoSelectList);
                    notifyDataSetChanged();
                }
            });

            noSelectViewHolder.rl_noSelect.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    show_deleteDialog("noselect",index-2-SelectList.size());
                    return true;
                }
            });
        }

        else if(holder instanceof  NoSelectTitleViewHolder)
        {
            NoSelectTitleViewHolder noSelectTitleViewHolder=new NoSelectTitleViewHolder(holder.itemView);
            noSelectTitleViewHolder.iv_xinjian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlterDialog();
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

    public List<Classify> getSelectList() {
        return SelectList;
    }

    public List<Classify> getNoSelectList() {
        return NoSelectList;
    }

    private void showAlterDialog()
    {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.showalterdialog_xinjian,null);
        final EditText et_name;
        Button btn_xinjian,btn_quxiao;
        btn_xinjian= (Button) view.findViewById(R.id.btn_xinjian);
        btn_quxiao= (Button) view.findViewById(R.id.btn_quxiao);
        et_name= (EditText) view.findViewById(R.id.et_name);
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        dialog.show();

        btn_xinjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_name.getText().toString().isEmpty())
                {
                    Classify new_classify=new Classify();
                    new_classify.setClassName(et_name.getText().toString());
                    new_classify.setType(SelectList.size()+NoSelectList.size()+1);
                    Log.d("zqh",SelectList.size()+NoSelectList.size()+1+"");
                    new_classify.setSelect(false);
                    NoSelectList.add(new_classify);
                    setDate(SelectList,NoSelectList);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(MyApplication.getContext(),"新建的类名不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 显示删除AlterDialog
     */
    private void show_deleteDialog(final String type, final int position){

        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle("确认删除这个分类吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(type.equals("select"))
                {
                    SelectList.remove(position);
                }
                else{
                    NoSelectList.remove(position);
                }
                setDate(SelectList,NoSelectList);
                notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        final AlertDialog dialog=builder.create();
        dialog.show();


    }
}
