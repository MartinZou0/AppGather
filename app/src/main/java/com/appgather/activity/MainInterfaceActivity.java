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
import com.appgather.entity.CustomItemMsg;
import com.appgather.fragment.VpSimpleFragment;
import com.appgather.view.BounceScrollView;
import com.appgather.view.SlideMenu;
import com.appgather.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainInterfaceActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mdrawerLayout;
    private TextView tv_personalcentre,tv_appManage;
    private ImageView mbutton;
    private ImageView iv_addItem;
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    //private List<String> mDatas = Arrays.asList("工作", "学习", "娱乐");
    private List<String> mDatas = new ArrayList<String>();
    private ViewPagerIndicator mIndicator;
    private BounceScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
        getSupportActionBar().hide();
        initView();
        initItemDate();
        initDatas();
        initTab();
    }

    private void initItemDate() {
        mDatas.add("工作");
        mDatas.add("学习");
        mDatas.add("娱乐");
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

    private void initDatas()
    {
        mTabContents.clear();
        for (String data : mDatas)
        {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);
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

    private void initView() {
        tv_appManage= (TextView) findViewById(R.id.tv_appManage);
        tv_appManage.setOnClickListener(this);
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
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.tv_personalcenter:
                Intent intent=new Intent(MainInterfaceActivity.this,PersonalCentreActivity.class);
                startActivity(intent);
                break;
            case R.id.addItem:
                customItem();
                break;
            case R.id.tv_appManage:
                Intent intent1=new Intent(this,AppManageActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void customItem() {
        Intent intent1=new Intent();
        intent1.setClass(this,CustomItemsActivity.class);
        startActivityForResult(intent1,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1)
        {
            Log.d("zqh",""+1);
            mDatas.clear();
            List<CustomItemMsg>  resultDate= (List<CustomItemMsg>) data.getSerializableExtra("SelectItem");
            int index=resultDate.size();
            for(int i=0;i<index;i++)
            {

                Log.d("zqh",""+resultDate.get(i).getItem_namae());
                mDatas.add(resultDate.get(i).getItem_namae());
            }
            initDatas();
            initTab();
        }

    }
}
