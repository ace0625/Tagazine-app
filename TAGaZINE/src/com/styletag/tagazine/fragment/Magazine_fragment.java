package com.styletag.tagazine.fragment;

import java.util.ArrayList;

import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.activity.R.id;
import com.styletag.tagazine.activity.R.layout;
import com.styletag.tagazine.adapters.Magazine_main_Adapter;
import com.styletag.tagazine.adapters.Magazine_main_Item;

import android.app.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class Magazine_fragment extends android.support.v4.app.Fragment {

	Activity activity;
	GridView magazineGridView;
	Magazine_main_Adapter magazinemainadapter;
	ArrayList<Magazine_main_Item> magazine_data;
	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.magazine_fragment, null);
		magazineGridView = (GridView)v.findViewById(R.id.gvMagazinepage);
		
		magazine_data = new ArrayList<Magazine_main_Item>();
		Mylog.v("magazine fragment");
		
		magazine_data.add(new Magazine_main_Item(R.drawable.bt_like_on, "2013.12", "Vol.1"));
		
		magazinemainadapter = new Magazine_main_Adapter(activity, magazine_data);
		magazineGridView.setAdapter(magazinemainadapter);
		return v;
	}
}
