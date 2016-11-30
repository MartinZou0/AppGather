package com.appgather.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.appgather.R;
import com.appgather.activity.AppDetailViewActivity;
import com.appgather.adapter.AppViewAdapter;
import com.appgather.entity.AppMsg;

public class VpSimpleFragment extends Fragment
{
	public static final String BUNDLE_TITLE = "title";
	private String mTitle = "DefaultValue";
	private List<AppMsg> AppURL=new ArrayList<AppMsg>();
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
		AppURL.add(new AppMsg("百度","http://www.baidu.com"));
		//AppURL.add("http://www.baidu.com");
		appViewAdapter=new AppViewAdapter(getContext(),AppURL);
		appListView.setAdapter(appViewAdapter);

		setOnclick();//为applistview设置监听
		return appListView;
	}

	private void setOnclick() {
		appListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent=new Intent();
				intent.setClass(getContext(), AppDetailViewActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("app",AppURL.get(i));
				intent.putExtras(bundle);
				startActivity(intent);
				//showPopupWindow(view ,i);
				return true;
			}
		});
	}

	public static VpSimpleFragment newInstance(String title)
	{
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_TITLE, title);
		VpSimpleFragment fragment = new VpSimpleFragment();
		fragment.setArguments(bundle);
		return fragment;
	}


	private void showPopupWindow(View view,int i) {

		final  int index=i;
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(getContext()).inflate(
				R.layout.appnametip_window, null);

		final PopupWindow popupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("mengdd", "onTouch : ");
				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});
		popupWindow.showAsDropDown(view,view.getWidth(),-view.getHeight());
	}
}
