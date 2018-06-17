package com.appgather.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.appgather.R;
import com.appgather.application.MyApplication;
import com.appgather.entity.Classify;
import com.appgather.fragment.VpSimpleFragment;
import com.appgather.view.BounceScrollView;
import com.appgather.view.ViewPagerIndicator;
import java.util.ArrayList;
import java.util.List;

//正在编程
public class MainInterfaceActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mdrawerLayout;
    private TextView tv_personalcentre,tv_appManage,tv_appStore;
    private ImageView mbutton;
    private ImageView iv_addItem;
    private List<String> mDatas = new ArrayList<String>();
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;

    private ViewPagerIndicator mIndicator;
    private BounceScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
        getSupportActionBar().hide();
        initView();

    }


    private void initView() {
        tv_appManage= (TextView) findViewById(R.id.tv_appManage);
        tv_appManage.setOnClickListener(this);
        tv_appStore= (TextView) findViewById(R.id.tv_appStore);
        tv_appStore.setOnClickListener(this);
        tv_personalcentre= (TextView) findViewById(R.id.tv_personalcenter);
        tv_personalcentre.setOnClickListener(this);
        mScrollView = (BounceScrollView) findViewById(R.id.id_scrollview);
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mbutton= (ImageView) findViewById(R.id.title_bar_menu_btn);
        iv_addItem= (ImageView) findViewById(R.id.addItem);
        iv_addItem.setOnClickListener(this);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdrawerLayout.openDrawer(Gravity.LEFT);//打开抽屉函数，LEFT/RIGHT为预先定义的位置上是否有布局，没有将报错
            }
        });
        mdrawerLayout=(DrawerLayout) findViewById(R.id.id_drawerlayout);
        //DrawerLayout监听器
        mdrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerSlide(View arg0, float arg1) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initItemDate();
        initDatas();
        initTab();
    }

    //初始化标签页信息
    private void initItemDate() {
        int size= MyApplication.getClassifies().size();
        mDatas.clear();
        if(size>0)
        {
            for(int i=0;i<size;i++){
                if(MyApplication.getClassifies().get(i).isSelect())
                {
                    mDatas.add(MyApplication.getClassifies().get(i).getClassName());
                }
            }
        }
    }

    /**
     * 初始化标题
     */
    private void initTab() {
        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager,mScrollView,0);
        //设置关联的图片旋转，根据需要设置，效果不是很好
        //mScrollView.setRotatImageView(mRotatImageView);
    }

    //初始化Tab页中的Fragment
    private void initDatas()
    {
        //list清空
        mTabContents.clear();
        for (Classify data : MyApplication.getClassifies())
        {
            if(data.isSelect()){
                VpSimpleFragment fragment = VpSimpleFragment.newInstance(data.getType()+"");
                mTabContents.add(fragment);
            }

        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position)
            {
                return mTabContents.get(position);
            }
        };
    }



    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId())
        {
            case R.id.tv_personalcenter:
                intent=new Intent(MainInterfaceActivity.this,PersonalCentreActivity.class);
                startActivity(intent);
                break;
            case R.id.addItem:
                customItem();
                break;
            case R.id.tv_appManage:
                intent=new Intent(this,AppManageActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_appStore:
                intent=new Intent(this,AppStoreActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void customItem() {
        Intent intent1=new Intent();
        intent1.setClass(this,CustomItemsActivity.class);
        startActivityForResult(intent1,1);
    }

     /*
    * 如果你想在Activity中得到新打开Activity关闭后返回的数据，
    * 你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity，
    * 新的Activity关闭后会向前面的Activity传回数据，为了得到传回的数据，你必须在前面的Activity中重写onActivityResult(int requestCode, int resultCode,Intent data)方法：
    * */

    //应用更改,改动信息接收方
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1)
        {
            mDatas.clear();
            List<Classify>  resultDate= (List<Classify>) data.getSerializableExtra("SelectItem");
            int index=resultDate.size();
            for(int i=0;i<index;i++)
            {
                mDatas.add(resultDate.get(i).getClassName());
            }
            initDatas();
            initTab();
        }

    }

}
