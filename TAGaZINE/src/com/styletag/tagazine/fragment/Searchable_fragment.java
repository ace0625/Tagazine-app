package com.styletag.tagazine.fragment;

import com.styletag.tagazine.activity.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

public class Searchable_fragment extends android.support.v4.app.Fragment {
	Activity activity;
	FragmentManager fragmentmanager = null;
	FragmentTransaction ft = null;
	
	AutoCompleteTextView searchBox;
	
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btn_search_cancel:
				getActivity().getSupportFragmentManager().beginTransaction().remove(Searchable_fragment.this).commit();
			
				break;
			}
			
		}
	};
	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.searchpage, null);
		searchBox = (AutoCompleteTextView)v.findViewById(R.id.searchedittext);
		v.findViewById(R.id.btn_search_cancel).setOnClickListener(bHandler);
		
		searchBox.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == event.KEYCODE_ENTER)
				{
					
					return true;
				}
				return false;
			}
		});
		
		return v;
	}
	
}
