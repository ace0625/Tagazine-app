package com.styletag.tagazine.adapters;

import java.util.ArrayList;

import com.styletag.tagazine.activity.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HotStreetFashion_Adapter extends BaseAdapter {
	Context context;
	ArrayList<HotStreetFashion_Item> hotstreet_data;
	
	public HotStreetFashion_Adapter(Context context, ArrayList<HotStreetFashion_Item> hotstreet_data)
	{
		this.context = context;
		this.hotstreet_data = hotstreet_data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hotstreet_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return hotstreet_data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class Holder
	{
		ImageView hotstreetimg;
	}
	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		HotStreetFashion_Item item = hotstreet_data.get(position);
		Holder holder = null;
		if(cView == null)
		{
			cView = View.inflate(context, R.layout.hotstreetfashion_item, null);
			holder = new Holder();
			holder.hotstreetimg = (ImageView)cView.findViewById(R.id.hotstreetfashion_itemimg);
			cView.setTag(holder);
		}
		else
		{
			holder = (Holder)cView.getTag();
		}
		holder.hotstreetimg.setImageResource(item.getImage());
		
		return cView;
	}

}
