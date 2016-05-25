package com.styletag.tagazine.adapters;

import java.util.ArrayList;

import com.styletag.tagazine.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Powertagger_Adapter extends BaseAdapter {

	Context context;
	ArrayList<PowerTagger_Item> tagger_data;
	
	public Powertagger_Adapter(Context context, ArrayList<PowerTagger_Item> tagger_data ) {
		this.context = context;
		this.tagger_data = tagger_data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tagger_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tagger_data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class Holder
	{
		ImageView taggerimg;
		TextView taggername;
	}
	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		PowerTagger_Item item = tagger_data.get(position);
		Holder holder = null;
		if(cView == null)
		{
			cView = View.inflate(context, R.layout.powertagger_item, null);
			holder = new Holder();
			holder.taggerimg  = (ImageView)cView.findViewById(R.id.powertagger_itemimage);
			holder.taggername = (TextView)cView.findViewById(R.id.powertagger_itemName);
			cView.setTag(holder);
		}
		else
		{
			holder = (Holder)cView.getTag();
		}
//		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), item.getImage());
//		holder.taggerimg.setImageBitmap(bitmap);
		holder.taggerimg.setImageResource(item.getImage());
		holder.taggername.setText(item.getName());
		return cView;
	}


}
