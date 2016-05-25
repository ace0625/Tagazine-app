package com.styletag.tagazine.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styletag.tagazine.activity.Magazine_detail_Activity;
import com.styletag.tagazine.activity.R;

public class Magazine_main_Adapter extends BaseAdapter {
	Context context;
	ArrayList<Magazine_main_Item> magazine_data;
	
	private static final int MAGAZINE_2013_12 = 0;
	public Magazine_main_Adapter(Context context, ArrayList<Magazine_main_Item> magazine_data)
	{
		this.context = context;
		this.magazine_data = magazine_data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return magazine_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return magazine_data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class Holder
	{
		ImageView magazineimg;
		TextView magazinedate;
		TextView magazievolnum;
	}
	@Override
	public View getView(final int position, View cView, ViewGroup parent) {
		Holder holder = null;
		Magazine_main_Item item = magazine_data.get(position);
		if(cView == null)
		{
			cView = View.inflate(context, R.layout.magazine_item, null);
			holder = new Holder();
			holder.magazineimg = (ImageView)cView.findViewById(R.id.magazine_mainImage);
			holder.magazinedate = (TextView)cView.findViewById(R.id.magazine_mainDatetv);
			holder.magazievolnum = (TextView)cView.findViewById(R.id.magazine_mainVoltv);
			cView.setTag(holder);
		}
		else
		{
			holder = (Holder)cView.getTag();
		}
		holder.magazineimg.setImageResource(item.getImage());
		holder.magazinedate.setText(item.getDate());
		holder.magazievolnum.setText(item.getVolnum());
		
		cView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch(position)
				{
				case MAGAZINE_2013_12:
					Intent intent = new Intent(context, Magazine_detail_Activity.class);
					context.startActivity(intent);
					break;
				}
				
			}
		});
		return cView;
	}

}
