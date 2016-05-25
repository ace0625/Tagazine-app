package com.styletag.tagazine.fragment;

import java.util.ArrayList;

import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.HotStreetFashion_Adapter;
import com.styletag.tagazine.adapters.HotStreetFashion_Item;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class HotStreetFashion_fragment extends android.support.v4.app.Fragment {
	Main_Activity activity;
	GridView hotstreefashiongridview;
	HotStreetFashion_Adapter hotstreetfashionadapter;
	ArrayList<HotStreetFashion_Item> hotstreet_data;
	@Override
	public void onAttach(Activity activity) {
		this.activity = (Main_Activity)activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.hotstreetfashion_fragment, null);
		hotstreefashiongridview = (GridView)v.findViewById(R.id.hotStreetFashion_gridview);
		Mylog.v("Hot Street Fashion");
		
		hotstreet_data = new ArrayList<HotStreetFashion_Item>();
		hotstreet_data.add(new HotStreetFashion_Item(R.drawable.model05));
		
		hotstreetfashionadapter = new HotStreetFashion_Adapter(activity, hotstreet_data);
		hotstreefashiongridview.setAdapter(hotstreetfashionadapter);
		return v;
	}

	@Override
	public void onResume() {
		sliding();
		super.onResume();
	}

	public void sliding() {

		activity.slidingview.toggleMenu();
	}
}
