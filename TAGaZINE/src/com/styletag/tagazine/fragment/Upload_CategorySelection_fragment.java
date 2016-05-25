package com.styletag.tagazine.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.TagItem;

public class Upload_CategorySelection_fragment extends Fragment {
	Activity activity;
	public EditText upload_styletip;
	TagItem item;
	public String category;
	Button daily, modern, vintage, unique, chic, celeb;
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btn_upload_cateory_dailyLook:
				category = "Daily Look";
				daily.setBackgroundResource(R.drawable.bt_write_category_on_02);
				
				modern.setBackgroundResource(R.drawable.bt_write_category_off_02);
				vintage.setBackgroundResource(R.drawable.bt_write_category_off_02);
				unique.setBackgroundResource(R.drawable.bt_write_category_off_02);
				chic.setBackgroundResource(R.drawable.bt_write_category_off_02);
				celeb.setBackgroundResource(R.drawable.bt_write_category_off_02);
				break;
			case R.id.btn_upload_cateory_modernLook:
				category = "Modern Look";
				modern.setBackgroundResource(R.drawable.bt_write_category_on_02);
				
	
				daily.setBackgroundResource(R.drawable.bt_write_category_off_02);
				vintage.setBackgroundResource(R.drawable.bt_write_category_off_02);
				unique.setBackgroundResource(R.drawable.bt_write_category_off_02);
				chic.setBackgroundResource(R.drawable.bt_write_category_off_02);
				celeb.setBackgroundResource(R.drawable.bt_write_category_off_02);
				break;
			case R.id.btn_upload_cateory_vintageLook:
				category = "Vintage Look";
				vintage.setBackgroundResource(R.drawable.bt_write_category_on_02);
						
				daily.setBackgroundResource(R.drawable.bt_write_category_off_02);
				modern.setBackgroundResource(R.drawable.bt_write_category_off_02);
				unique.setBackgroundResource(R.drawable.bt_write_category_off_02);
				chic.setBackgroundResource(R.drawable.bt_write_category_off_02);
				celeb.setBackgroundResource(R.drawable.bt_write_category_off_02);
				break;
			case R.id.btn_upload_cateory_uniqueLook:
				category = "Unique Look";
				unique.setBackgroundResource(R.drawable.bt_write_category_on_02);
				
				daily.setBackgroundResource(R.drawable.bt_write_category_off_02);
				modern.setBackgroundResource(R.drawable.bt_write_category_off_02);
				vintage.setBackgroundResource(R.drawable.bt_write_category_off_02);
				chic.setBackgroundResource(R.drawable.bt_write_category_off_02);
				celeb.setBackgroundResource(R.drawable.bt_write_category_off_02);
				break;
			case R.id.btn_upload_cateory_chicLook:
				category = "Chic Look";
				chic.setBackgroundResource(R.drawable.bt_write_category_on_02);
				
				daily.setBackgroundResource(R.drawable.bt_write_category_off_02);
				modern.setBackgroundResource(R.drawable.bt_write_category_off_02);
				vintage.setBackgroundResource(R.drawable.bt_write_category_off_02);
				unique.setBackgroundResource(R.drawable.bt_write_category_off_02);
				celeb.setBackgroundResource(R.drawable.bt_write_category_off_02);
				break;
			case R.id.btn_upload_cateory_celebLook:
				category = "Celeb Look";
				celeb.setBackgroundResource(R.drawable.bt_write_category_on_02);
				
				daily.setBackgroundResource(R.drawable.bt_write_category_off_02);
				modern.setBackgroundResource(R.drawable.bt_write_category_off_02);
				vintage.setBackgroundResource(R.drawable.bt_write_category_off_02);
				unique.setBackgroundResource(R.drawable.bt_write_category_off_02);
				chic.setBackgroundResource(R.drawable.bt_write_category_off_02);
				break;
				
			}
			Mylog.v(category);
			
		}
	};
	public void setCategoryInfo(TagItem item)
	{
		this.item = item;
//		item.setCategory(category);
		
	}
	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.upload_categoryselection_fragment, null);
		upload_styletip = (EditText)v.findViewById(R.id.et_upload_styletip);
	
		daily = (Button)v.findViewById((R.id.btn_upload_cateory_dailyLook));
		modern = (Button)v.findViewById((R.id.btn_upload_cateory_modernLook));
		vintage = (Button)v.findViewById((R.id.btn_upload_cateory_vintageLook));
		unique = (Button)v.findViewById((R.id.btn_upload_cateory_uniqueLook));
		chic = (Button)v.findViewById((R.id.btn_upload_cateory_chicLook));
		celeb = (Button)v.findViewById((R.id.btn_upload_cateory_celebLook));
		
		daily.setOnClickListener(bHandler);
		modern.setOnClickListener(bHandler);
		vintage.setOnClickListener(bHandler);
		unique.setOnClickListener(bHandler);
		chic.setOnClickListener(bHandler);
		celeb.setOnClickListener(bHandler);
		
		return v;
	}
	
	

}
