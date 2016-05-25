package com.styletag.tagazine.adapters;

import java.util.ArrayList;

import com.styletag.tagazine.activity.R;


import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MDsPick_Adapter extends BaseAdapter {
	Context context;
	ArrayList<MDsPick_Item> mdpick_data;
	
	public MDsPick_Adapter(Context context, ArrayList<MDsPick_Item> mdpick_data) {
		this.context = context;
		this.mdpick_data = mdpick_data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdpick_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mdpick_data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class Holder
	{
		ImageView mdpickimg;
	}
	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		MDsPick_Item item = mdpick_data.get(position);
		Holder holder = null;
		if(cView == null)
		{
			cView = View.inflate(context, R.layout.mdspick_item, null);
			holder = new Holder();
			holder.mdpickimg = (ImageView)cView.findViewById(R.id.mdspick_image);
			cView.setTag(holder);
		}
		else
		{
			holder = (Holder)cView.getTag();
		}
		holder.mdpickimg.setImageResource(item.getImage());
		
		return cView;
		
	}

}
