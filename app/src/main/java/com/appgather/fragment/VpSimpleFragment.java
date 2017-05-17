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
			mTitle = arguments.getString(BUNDLE_TITLE);
		}
		appListView=new ListView(getActivity());
		AppURL.clear();
		setApps(mTitle);

		appViewAdapter=new AppViewAdapter(getContext(),AppURL);
		appListView.setAdapter(appViewAdapter);

		setOnclick();//为applistview设置监听
		return appListView;
	}

	private void setApps(String mType) {
		int size= MyApplication.getApps().size();
		for(int i=0;i<size;i++)
		{
			if(mType.equals(MyApplication.getApps().get(i).getCategoryID()+"")){
				AppURL.add(MyApplication.getApps().get(i));
			}
		}
	}

	private void setOnclick() {

	}

	public static VpSimpleFragment newInstance(String title)
	{
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_TITLE, title);
		VpSimpleFragment fragment = new VpSimpleFragment();
		fragment.setArguments(bundle);
		return fragment;
	}


}
