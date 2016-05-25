package com.styletag.tagazine.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Main_list_Adapter extends BaseAdapter {
	Context context;
	ArrayList<Main_list_item> main_data;
	
//	public Main_list_Adapter(Context context, ArrayList<Main_list_item> main_Ldata)
//	{
//		this.context = context;
//		this.main_data = main_data;
//	}
//	public Main_list_Adapter(Activity activity,
//			ArrayList<Main_list_item> main_data2) {
//		// TODO Auto-generated constructor stub
//	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return main_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return main_data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		
		return cView;
	}

}
