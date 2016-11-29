package com.appgather.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.appgather.R;
import com.appgather.fragment.VpSimpleFragment;
import com.appgather.view.BounceScrollView;
import com.appgather.view.SlideMenu;
import com.appgather.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainInterfaceActivity extends AppCompatActivity {

    private DrawerLayout mdrawerLayout;
    private ImageView mbutton;
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas = Arrays.asList("工作", "学习", "娱乐");

    private ViewPagerIndicator mIndicator;
    private BounceScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
        getSupportActionBar().hide();
        initView();
        initDatas();
        initTab();
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
        mScrollView = (BounceScrollView) findViewById(R.id.id_scrollview);
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mbutton= (ImageView) findViewById(R.id.title_bar_menu_btn);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdrawerLayout.openDrawer(Gravity.LEFT);//打开抽屉函数，LEFT/RIGHT为预先定义的位置上是否有布局，没有将报错
            }
        });
        mdrawerLayout=(DrawerLayout) findViewById(R.id.id_drawerlayout);
        //
        //DrawerLayout监听器
        //
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
}
