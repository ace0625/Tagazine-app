package com.styletag.tagazine.fragment;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

import com.styletag.tagazine.activity.ImageCall_Activity;
import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.TagItem;
import com.styletag.tagazine.utils.NetManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Main_StyleFeed_Detail2_fragment extends Fragment {
	Activity activity;
	FragmentManager fragmentmanager = null;
	ImageView detail_tag;
	TextView brandtv, producttv, pricetv, placetv;
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.detail_detail_btn_cancel:
				fragmentmanager = ((Main_Activity)activity).getSupportFragmentManager();
				FragmentTransaction ft = fragmentmanager.beginTransaction();
				ft.remove(Main_StyleFeed_Detail2_fragment.this);
				ft.commit();
				break;
			}
			
		}
	};
	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}
	TagItem item;
	public void setTagDetailData(TagItem item)
	{
		Mylog.v("item = " +item.getType());
		this.item = item;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.main_stylefeed_detail_taginfo_detail, null);
		v.findViewById(R.id.detail_detail_btn_cancel).setOnClickListener(bHandler);
		detail_tag = (ImageView)v.findViewById(R.id.detail_detail_tagimg);
		brandtv = (TextView)v.findViewById(R.id.detail_detail_brand_tv);
		producttv = (TextView)v.findViewById(R.id.detail_detail_product_tv);
		pricetv = (TextView)v.findViewById(R.id.detail_detail_price_tv);
		placetv = (TextView)v.findViewById(R.id.detail_detail_place_tv);
		
		
		switch(item.getType())
		{
		case 0:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 1:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 2:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 3:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 4:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 5:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 6:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 7:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 8:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 9:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 10:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 11:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 12:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 13:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
		case 14:
			detail_tag.setImageResource(R.drawable.bt_tag_icon_01);
			break;
			
		}
		brandtv.setText(item.getBrand());
		producttv.setText(item.getProduct());
		pricetv.setText(item.getPrice());
		placetv.setText(item.getAddress());
		return v;
	}
	

}