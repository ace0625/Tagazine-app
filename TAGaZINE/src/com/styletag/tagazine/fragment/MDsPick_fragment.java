package com.styletag.tagazine.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.MDsPick_Adapter;
import com.styletag.tagazine.adapters.MDsPick_Item;

public class MDsPick_fragment extends android.support.v4.app.Fragment {
	Main_Activity activity;
	GridView mdspickgridview;
	MDsPick_Adapter mdpickadapter;
	ArrayList<MDsPick_Item> mdpick_data;
	
	@Override
	public void onAttach(Activity activity) {
		this.activity = (Main_Activity)activity;
		super.onAttach(activity);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.mdspick_fragment, null);
		mdspickgridview = (GridView)v.findViewById(R.id.mdspick_gridview);
		mdpick_data = new ArrayList<MDsPick_Item>();
		Mylog.v("MD's pick fragment");
		mdpick_data.add(new MDsPick_Item(R.drawable.img_mdpick_01));
		mdpick_data.add(new MDsPick_Item(R.drawable.img_mdpick_02));
		mdpick_data.add(new MDsPick_Item(R.drawable.img_mdpick_03));
		mdpick_data.add(new MDsPick_Item(R.drawable.img_mdpick_04));
		mdpick_data.add(new MDsPick_Item(R.drawable.img_mdpick_05));
		mdpick_data.add(new MDsPick_Item(R.drawable.img_mdpick_06));
		
		mdpickadapter = new MDsPick_Adapter(activity, mdpick_data);
		mdspickgridview.setAdapter(mdpickadapter);
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
