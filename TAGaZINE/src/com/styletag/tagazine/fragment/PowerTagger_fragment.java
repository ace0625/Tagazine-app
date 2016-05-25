package com.styletag.tagazine.fragment;

import java.util.ArrayList;

import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.PowerTagger_Item;
import com.styletag.tagazine.adapters.Powertagger_Adapter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

public class PowerTagger_fragment extends android.support.v4.app.Fragment {
	Main_Activity activity;
	GridView powertaggergridview; //파워태거 그리드뷰
	Powertagger_Adapter powertaggeradapter; //파워태거 어댑터
	ArrayList<PowerTagger_Item> tagger_data; //파워태거 데이터
//	public int who_calls;

	@Override
	public void onAttach(Activity activity) {
		this.activity = (Main_Activity)activity;
		super.onAttach(activity);
		
	}

	
	@Override
	public void onResume() {
		sliding();
		super.onResume();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.powertagger_fragment, null);
		powertaggergridview = (GridView)v.findViewById(R.id.powertagger_gridview);
		tagger_data = new ArrayList<PowerTagger_Item>();
		Mylog.v("Power tagger Fragment Create view");
		tagger_data.add(new PowerTagger_Item(R.drawable.model05, "Adam")); //데이터 입력
		
		powertaggeradapter = new Powertagger_Adapter(activity, tagger_data);
		powertaggergridview.setAdapter(powertaggeradapter);
		return v;
	}

	public void sliding() {
//		Toast.makeText(activity, "여기", 0).show();
		Mylog.v("Sliding back");
		activity.slidingview.toggleMenu();
	}
	
}
