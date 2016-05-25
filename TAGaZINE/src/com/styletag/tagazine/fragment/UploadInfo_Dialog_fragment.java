package com.styletag.tagazine.fragment;


import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.TagItem;
import com.styletag.tagazine.views.UploadTagView;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class UploadInfo_Dialog_fragment extends android.support.v4.app.DialogFragment {
	Activity activity;
	AutoCompleteTextView upload_brand;
	EditText upload_product;
	EditText upload_price;
	EditText upload_address;
	FragmentManager fragmentmanager;
	SearchMap_fragment searchmapfragment; //지도검색 프래그먼트
	TagItem item; //태그 아이템
	UploadTagView view; //업로드 뷰

	void callMap()
	{
		Mylog.v("call map");
		fragmentmanager = getActivity().getSupportFragmentManager();
		searchmapfragment = new SearchMap_fragment();
		
		searchmapfragment.setUploadInfo_Dialog_fragment(this);
		
		searchmapfragment.setMapinfo(item);
		searchmapfragment.show(fragmentmanager, "map");
//		FragmentTransaction ft = fragmentmanager.beginTransaction();
//		ft.add(R.id.upload_dialog_layout, searchmapfragment);
//		ft.commit();
		
	}

	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btn_upload_map:
				callMap();
				break;
			case R.id.btn_uploaddialog_ok:
				Mylog.v("okay button");
				view.setItemOk();
				item.setBrand(upload_brand.getText().toString());
				item.setProduct(upload_product.getText().toString());
				item.setPrice(upload_price.getText().toString());
				item.setAddress(upload_address.getText().toString());
				UploadInfo_Dialog_fragment.this.getDialog().cancel();
				
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
	public void onStart() {
		Window window = getDialog().getWindow();
		WindowManager.LayoutParams windowParams = window.getAttributes();
		windowParams.dimAmount = 0.50f;
		windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		window.setAttributes(windowParams);
		super.onStart();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(com.styletag.tagazine.activity.R.layout.upload_enterinfo_fragment, null);
		getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
		getDialog().setCanceledOnTouchOutside(true);
		
		
		upload_brand = (AutoCompleteTextView)v.findViewById(R.id.et_upload_brand);
		upload_product = (EditText)v.findViewById(R.id.et_upload_product);
		upload_price = (EditText)v.findViewById(R.id.et_upload_price);
		upload_address = (EditText)v.findViewById(R.id.et_upload_address);
		v.findViewById(R.id.btn_upload_map).setOnClickListener(bHandler);
		v.findViewById(R.id.btn_uploaddialog_ok).setOnClickListener(bHandler);
		return v;
	}
	
	public void setTagItem(TagItem item)
	{
		this.item = item;
	
		
	}
	
	public void setUpLoadTagView(UploadTagView view)
	{
		this.view = view;
	}
     
}
