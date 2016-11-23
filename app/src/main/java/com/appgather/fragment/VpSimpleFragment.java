package com.appgather.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.appgather.adapter.AppViewAdapter;

public class VpSimpleFragment extends Fragment
{
	public static final String BUNDLE_TITLE = "title";
	private String mTitle = "DefaultValue";
	private List<String> AppURL=new ArrayList<String>();
	private AppViewAdapter appViewAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		Bundle arguments = getArguments();
		if (arguments != null)
		{
			mTitle = arguments.getString(BUNDLE_TITLE);
		}
		ListView appListView=new ListView(getActivity());
		AppURL.clear();
		AppURL.add("http://www.baidu.com");
		AppURL.add("http://www.sina.com");

		appViewAdapter=new AppViewAdapter(getContext(),AppURL);
		appListView.setAdapter(appViewAdapter);
/*
		TextView tv = new TextView(getActivity());
		tv.setText(mTitle);
		tv.setGravity(Gravity.CENTER);
*/
		return appListView;
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
