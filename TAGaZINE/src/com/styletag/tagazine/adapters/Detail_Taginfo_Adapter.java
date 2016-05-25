package com.styletag.tagazine.adapters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.fragment.Main_StyleFeed_Detail2_fragment;

public class Detail_Taginfo_Adapter extends BaseAdapter {
	Context context;
	int layout;
	ArrayList<TagItem> taginfo_data;
	FragmentManager fragmentmanager = null;
	
	public Detail_Taginfo_Adapter(Context context, int layout, ArrayList<TagItem> taginfo_data)
	{
		this.context = context;
		this.layout = layout;
		this.taginfo_data = taginfo_data;
	}
	public void setTaginfoData(ArrayList<TagItem> taginfo_data)
	{
		this.taginfo_data = taginfo_data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return taginfo_data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return taginfo_data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class Holder
	{
		ImageView taginfo_img;
		TextView taginfo_text;
	}
	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		final TagItem item = taginfo_data.get(position);
		Holder holder = null;
		if(cView == null)
		{
			cView = View.inflate(context, R.layout.main_stylefeed_detail_taginfo, null);
			holder = new Holder();
			holder.taginfo_img = (ImageView)cView.findViewById(R.id.detail_taginfo_imgage1);
			holder.taginfo_text = (TextView)cView.findViewById(R.id.detail_taginfo_text);
			cView.setTag(holder);
		}
		else
		{
			holder = (Holder)cView.getTag();
		}
		holder.taginfo_img.setImageResource(item.getType());
		Mylog.v("type : " +item.getType());
		switch(item.getType())
		{
		case 0:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_01);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 1:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_02);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 2:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_03);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 3:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_04);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 4:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_05);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 5:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_06);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 6:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_07);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 7:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_08);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 8:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_09);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 9:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_10);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 10:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_11);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 11:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_12);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 12:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_13);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 13:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_14);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
		case 14:
			holder.taginfo_img.setImageResource(R.drawable.txt_tag_icon_15);
			holder.taginfo_text.setText(taginfo_data.get(position).getBrand());
			break;
			
		}
		
		cView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showTaginfo(item);
			
			}
		});
		return cView;
	}
	
	public void showTaginfo(TagItem item)
	{
		Main_StyleFeed_Detail2_fragment mainStyleFeed_detail2_fragment;
		fragmentmanager = ((Main_Activity)context).getSupportFragmentManager();
		mainStyleFeed_detail2_fragment = new Main_StyleFeed_Detail2_fragment();
		mainStyleFeed_detail2_fragment.setTagDetailData(item);
		FragmentTransaction ft = fragmentmanager.beginTransaction();
		ft.add(R.id.main_stylefeed_detail_layout, mainStyleFeed_detail2_fragment);
		ft.commit();
	}

}
