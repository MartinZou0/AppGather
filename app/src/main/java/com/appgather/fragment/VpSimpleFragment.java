package com.appgather.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.appgather.adapter.AppViewAdapter;
import com.appgather.application.MyApplication;
import com.appgather.entity.Apps;

//已完成
public class VpSimpleFragment extends Fragment
{
	public static final String BUNDLE_TITLE = "title";
	private String mTitle = "DefaultValue";
	private List<Apps> AppURL=new ArrayList<Apps>();
	private AppViewAdapter appViewAdapter;
	private ListView appListView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Bundle arguments = getArguments();
		if (arguments != null)
		{
			//获取到标题
			mTitle = arguments.getString(BUNDLE_TITLE);
		}
		appListView=new ListView(getActivity());
		AppURL.clear();
		setApps(mTitle);
		//获得要本页面显示的应用
		//getContext()：这个是View类中提供的方法，在继承了View的类中才可以调用，
		// 返回的是当前View运行在哪个Activity Context中。

		appViewAdapter=new AppViewAdapter(getContext(),AppURL);
		appListView.setAdapter(appViewAdapter);

		setOnclick();//为applistview设置监听
		return appListView;
	}

	//mtype的定义为这个标签页的标号，选择在那个标签页进行显示
	//每个Fragment其实也就对应着一个Type，所以便利已有的已有的应用集
	//所有定义为这个标签页显示的必须要
	private void setApps(String mType) {

		//获取已经加载的应用
		int size= MyApplication.getApps().size();
		for(int i=0;i<size;i++)
		{
			if(mType.equals(MyApplication.getApps().get(i).getCategoryID()+"")){
				if(MyApplication.getApps().get(i).getIsondisplay()){
					AppURL.add(MyApplication.getApps().get(i));
				}

			}
		}
	}

	//点击事件
	private void setOnclick() {

	}

	public static VpSimpleFragment newInstance(String title)
	{
		//创建Fragment
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_TITLE, title);
		VpSimpleFragment fragment = new VpSimpleFragment();
		fragment.setArguments(bundle);
		return fragment;
		//利用此种方式新建Fragment
	}


}
