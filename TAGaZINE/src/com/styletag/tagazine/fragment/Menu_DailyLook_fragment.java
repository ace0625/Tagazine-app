package com.styletag.tagazine.fragment;

import java.util.ArrayList;

import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.StaggeredAdapter;
import com.styletag.tagazine.json.Item_MenuList;
import com.styletag.tagazine.views.StaggeredGridView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Menu_DailyLook_fragment extends Fragment {
	Main_Activity activity;
	StaggeredGridView staggeredgridview;
	StaggeredAdapter dailyLook_adapter;
	ProgressDialog pDialog = null;
	ArrayList<Item_MenuList> menu_data = new ArrayList<Item_MenuList>();
	@Override
	public void onAttach(Activity activity) {
		this.activity = (Main_Activity)activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.main_stylefeed_fragment, null);
		
		pDialog = ProgressDialog.show(getActivity(), "", "loading...");
		
		staggeredgridview = (StaggeredGridView) v.findViewById(R.id.staggeredGridView1);
		int margin = getResources().getDimensionPixelSize(R.dimen.main_stylefeed_margin);
		staggeredgridview.setItemMargin(margin); // set the GridView margin
		staggeredgridview.setPadding(10, 12, 10, 0); // have the margin on the sides as well 
//		dailyLook_adapter = new StaggeredAdapter(activity, R.id.main_stylefeed_item_img, menu_data);
		staggeredgridview.setAdapter(dailyLook_adapter);
		dailyLook_adapter.notifyDataSetChanged();
		return v;
	}

	
}
