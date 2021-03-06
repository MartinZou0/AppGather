package com.appgather.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.appgather.R;
import com.appgather.view.CustomDialog;



//自定义dialog

public class DlgLoading {
	private CustomDialog dialog = null;
	private LayoutInflater mInflater;
	private TextView txtContent;

	public DlgLoading(Context context) {
		//获取上下文并将布局文件转换为VIew对象
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		init(context);
	}

	private void init(Context context) {
		View dlgView = mInflater.inflate(R.layout.dlg_loading, null);
		txtContent = (TextView) dlgView
				.findViewById(R.id.dlg_loading_txtContent);
		dialog = new CustomDialog(context)
				.setContentView(dlgView, Gravity.CENTER)
				.setCanceledOnTouchOutside(false).setCancelable(true);
	}

	public void show(String content) {
		txtContent.setText(content);
		show();
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	public CustomDialog getDialog() {
		return dialog;
	}

	public TextView getTxtContent() {
		return txtContent;
	}

}
