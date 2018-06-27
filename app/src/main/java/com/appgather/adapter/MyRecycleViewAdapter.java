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


//进行设置，正在理解
    //此适配器用于应用分类的显示
public class MyRecycleViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //选择加上未选择等于总项目数
    private List<Classify> SelectList=new ArrayList<Classify>();

    private List<Classify> NoSelectList=new ArrayList<Classify>();

    private LayoutInflater inflater;

    private Context mContext;

    private Map<Integer,Integer> Realposition=new HashMap<Integer, Integer>();//它的作用其实是进行一个每种类型的开始位置

    private List<Integer> typeList=new ArrayList<Integer>();

//构造函数，初始化适配器
    public MyRecycleViewAdapter( Context context) {
        mContext=context;
        inflater=LayoutInflater.from(context);

    }


    //传入现在已添加分类列表和未添加分类列表
    public  void setData(List<Classify> SelectList , List<Classify> NoSelectList)
    {
        //绝对位置清0
        Realposition.clear();
        typeList.clear();
        this.SelectList = SelectList;
        this.NoSelectList = NoSelectList;
        //3其实就是对应的类型3，为已添加分类标题
        Realposition.put(3,0);//记录的开始位置
        typeList.add(3);
        Realposition.put(1,1);
        for(int i=0;i<SelectList.size();i++)
        {
            //有多少项就添加多少个1
            typeList.add(1);
        }
        //未添加标题的位置跟已添加分类的数目有关
        Realposition.put(4,1+SelectList.size());
        typeList.add(4);
        //未添加分类项的开始位置，前面还有一个标题
        Realposition.put(2,2+SelectList.size());
        for(int i=0;i<NoSelectList.size();i++)
        {
            typeList.add(2);
        }
    }

    //这个方法主要生成为每个Item inflater出一个View，但是该方法返回的是一个ViewHolder。
    // 该方法把View直接封装在ViewHolder中，然后我们面向的是ViewHolder这个实例，
    //参数一：ViewGroup parent，是指RecycleView的布局
    //参数二：int viewType，是指Item的属性，
    // 该属性是在public int getItemViewType(int position)中进行设置获取的。
    //创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==1)
        {
            //加载的已选择项目布局
            view = inflater.inflate(R.layout.select_item,parent, false);
            //用自定义的ViewHoleder装载item的view
            SelectViewHolder selectViewHolder=new SelectViewHolder(view);
            return selectViewHolder;
        }
        else if(viewType==2)
        {
            //加载未选择项目布局
            view = inflater.inflate(R.layout.noselect_item,parent, false);
            NoSelectViewHolder noSelectViewHolder=new NoSelectViewHolder(view);
            return noSelectViewHolder;
        }

        else if(viewType==3)
        {
            //加载选择项目标题，显示标题
            view = inflater.inflate(R.layout.select_item_title,parent, false);
            SelectTitleViewHolder selectTitleViewHolder=new SelectTitleViewHolder(view);
            return selectTitleViewHolder;
        }

        else if(viewType==4)
        {
            //加载未选择项目标题，显示标题
            view = inflater.inflate(R.layout.noselect_item_title,parent, false);
            NoSelectTitleViewHolder noselectTitleViewHolder=new NoSelectTitleViewHolder(view);
            return noselectTitleViewHolder;
        }

        return null;
    }


    //将数据绑定至VIewHolder
    //根据返回的不同类型ViewHolder绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
        //此属性就是一个常量，一旦初始化就不可再被赋值。
        final int index=position;
        //如果是已选择项目
        if(holder instanceof SelectViewHolder) {
            //设置事件，设定显示内容
            //理应是从零开始，但是需要显示一个标题栏所以需要减1
            SelectViewHolder selectViewHolder=new SelectViewHolder(holder.itemView);
            selectViewHolder.tv_app_name.setText(SelectList.get(index-1).getClassName());
            //点击减号事件，也就是将其放置到未选择分类里
            selectViewHolder.plus_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //将其置于未添加分类
                    SelectList.get(index-1).setSelect(false);
                    NoSelectList.add(SelectList.get(index-1));
                    //从选择分类中去除
                    SelectList.remove(index-1);
                    setData(SelectList ,NoSelectList);
                    //notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
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
                    setData(SelectList ,NoSelectList);
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

        //如果匹配的是没有显示的标题栏
        else if(holder instanceof  NoSelectTitleViewHolder)
        {
            //
            NoSelectTitleViewHolder noSelectTitleViewHolder=new NoSelectTitleViewHolder(holder.itemView);
            //新建项目
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
        //新建所显示的对话框
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
                    //新建标签页分类
                    Classify new_classify=new Classify();
                    new_classify.setClassName(et_name.getText().toString());
                    //按照顺序进行定义对应type
                    new_classify.setType(SelectList.size()+NoSelectList.size()+1);
                    Log.d("zqh",SelectList.size()+NoSelectList.size()+1+"");
                    //默认未不显示
                    new_classify.setSelect(false);
                    NoSelectList.add(new_classify);
                    setData(SelectList,NoSelectList);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(MyApplication.getContext(),"新建的标签页名不能为空",Toast.LENGTH_SHORT).show();
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
                setData(SelectList,NoSelectList);
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
