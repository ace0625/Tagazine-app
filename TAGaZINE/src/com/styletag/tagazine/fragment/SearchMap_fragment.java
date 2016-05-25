package com.styletag.tagazine.fragment;

import java.util.ArrayList;
import java.util.List;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapMarkerItem2;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapData.FindAllPOIListenerCallback;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.TagItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;

public class SearchMap_fragment extends DialogFragment {
	Activity activity;
	AutoCompleteTextView serachText;
	TMapView tmap = null;
	TMapPoint point = null;
	TMapGpsManager tmapManager = null;
	TMapData tmapData = null;
	Location location = null;
	LocationManager locationManager = null;
	Geocoder coder;

	UploadInfo_Dialog_fragment upLoadDialogfragment;
	Upload_CategorySelection_fragment categoryfragment;
	TagItem item;
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btn_map_back:
				SearchMap_fragment.this.getDialog().cancel();
				break;
//			case R.id.btn_map:
////				categoryfragment.setCategoryInfo(item);
//				findA();
//				break;
			}
			
		}
	};
	
	public void setUploadInfo_Dialog_fragment(UploadInfo_Dialog_fragment upLoadDialogfragment){
		this.upLoadDialogfragment = upLoadDialogfragment;
	}
	@Override
	public void onStart() {
		Window window = getDialog().getWindow();
		WindowManager.LayoutParams windowParams = window.getAttributes();
		windowParams.dimAmount = 0;
//		windowParams.flags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
		window.setAttributes(windowParams);
		super.onStart();
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
		v = inflater.inflate(R.layout.searchmap_fragment, null);
		getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		getDialog().getWindow().setBackgroundDrawableResource(Color.TRANSPARENT);
		serachText = (AutoCompleteTextView)v.findViewById(R.id.mapSearch_et);
		
		serachText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == event.KEYCODE_ENTER)
				{
					findA();
					return true;
				}
				return false;
			}
		});
		v.findViewById(R.id.btn_map_back).setOnClickListener(bHandler);
//		v.findViewById(R.id.btn_map_ok).setOnClickListener(bHandler);
		
		tmapData = new TMapData();
		tmap = (TMapView)v.findViewById(R.id.tMap);
		getActivity();
		locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		boolean gpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(location != null)
		{
			point = new TMapPoint(location.getLatitude(), location.getLongitude());
		}
		new Thread()
		{
			public void run()
			{
				tmap.setSKPMapApiKey("20e0098a-451b-3a54-af4f-dba319e1d211");
				tmap.setLanguage(tmap.LANGUAGE_KOREAN);
				tmap.setLanguage(tmap.LANGUAGE_ENGLISH);
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setUpMap();
						
					}
				});
			}
			
		}.start();
		return v;
	}
	String getAddress(String id){
		String str = id.substring(4);
		int index = Integer.parseInt(str);
		Mylog.v("id: "+index);
		Mylog.v("address : " + addressList.get(index));
		return addressList.get(index);
	}
	
	
	ArrayList<String> addressList = new ArrayList<String>();
	void findA()
	{
		tmapData.findAllPOI(serachText.getText().toString(), 20,
	
						new FindAllPOIListenerCallback() {
					
					@Override
					public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {
						idx = 0;
						addressList.clear();
						for(int i=0; i<poiItem.size(); i++)
							{
								TMapPOIItem item = poiItem.get(i);
								
								Mylog.v("ID: " +item.id+
										"Name : " + item.getPOIName().toString() + 
										", " + "Address: "+ item.getPOIAddress().toString() + ", "+ "Point: "+item.getPOIPoint().toString());
//								setMarker(item.getPOIPoint().getLatitude(), item.getPOIPoint().getLongitude(), item.getPOIName(), i);
								
								addressList.add(item.getPOIAddress());
								setMarker(item);
							}
						tmap.invalidate();
						}
					
				});	
	}
	
	int idx = 0;
	void setMarker(TMapPOIItem pItem)
	{
		Mylog.v("addmarker");
		TMapPoint point2 = tmap.getCenterPoint();
		point2.setLatitude(pItem.getPOIPoint().getLatitude());
		point2.setLongitude(pItem.getPOIPoint().getLongitude());
		TMapMarkerItem item = new TMapMarkerItem();
		item.setTMapPoint(point2);
		item.setPosition(0.3f, 1f);
	
		Bitmap bitmap1 =  BitmapFactory.decodeResource(getResources(), R.drawable.bt_add_gps_on);
		item.setIcon(bitmap1);
//		Bitmap bLeft = BitmapFactory.decodeResource(getResources(),R.drawable.bt_add_gps_on);
		Bitmap bRight = BitmapFactory.decodeResource(getResources(),R.drawable.bt_add_gps_off);
		item.setCalloutRightButtonImage(bRight);
		item.setCalloutTitle(pItem.getPOIName());
		item.setCanShowCallout(true);
		
		item.setID("item" + idx);
		
		tmap.addMarkerItem("item" + idx, item);
		idx++;
	}
	
//	Bitmap bitmap = null;
//	void setMarker(double latitude, double longitude, String name, int i)
//	{
//		TMapPoint point = tmap.getCenterPoint();
//		TMapMarkerItem mItem = new TMapMarkerItem();
//		point.setLatitude(latitude);
//		point.setLongitude(longitude);
//		mItem.setTMapPoint(point);
////		mItem.setPosition((float)latitude, (float)longitude);
//		mItem.setName(name);
//		mItem.setCalloutTitle(name);
//		mItem.setCanShowCallout(true);
//		Mylog.v("setmarker");
//		mItem.setVisible(View.VISIBLE);
//		Bitmap bLeft = BitmapFactory.decodeResource(getResources(),R.drawable.bt_add_gps_on);
//		Bitmap bRight = BitmapFactory.decodeResource(getResources(),R.drawable.bt_add_gps_off);
//		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bt_add_gps_on);
//		mItem.setCalloutLeftImage(bLeft);
//		mItem.setCalloutRightButtonImage(bRight);
////		mItem.setCalloutTitle("서울대");
//		mItem.setCalloutSubTitle("subtitle");
////		mItem.setCanShowCallout(true);
//
//		mItem.setIcon(bitmap);
//		mItem.setID("item" + i);
//		tmap.addMarkerItem("item" + i, mItem);
//		
//	}
	void setUpMap()
	{
		
//		tmap.setCompassMode(true);
		tmap.setTrackingMode(true);
//		tmap.setSightVisible(true);
		tmap.setOnClickListenerCallBack(clickCallBack); //지도가 클릭 되었을때 호출할 콜백리스너
		tmap.setZoomLevel(10);
//		tmap.setOnCalloutRightButtonClickListener(rightCallBack);	
		
	tmap.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
			
			@Override
			public void onCalloutRightButton(TMapMarkerItem item) {
				Mylog.v("item id : " +item.getID());
				String address = getAddress(item.getID());
				Mylog.v("address 1111 : " +address);
				Mylog.v("address upLoadDialogfragment : " +upLoadDialogfragment);
				Mylog.v("address upLoadDialogfragment.upload_address : " +upLoadDialogfragment.upload_address);
				int idx = address.indexOf("null");
				address = address.substring(0, idx);
				upLoadDialogfragment.upload_address.setText(address);
//				upLoadDialogfragment.upload_address.setText(getAddress(item.getID().toString()));
				SearchMap_fragment.this.getDialog().cancel();
			}
		});
	}
	void doFindAddress(String name)
	{
		List<Address> list = null;  //내가간 위치 흔적남기기
		try {
			list = coder.getFromLocationName(name, 5);
			StringBuilder sb = new StringBuilder();
			int cnt = 0;
			for(Address address : list)
			{
				cnt = address.getMaxAddressLineIndex() + 1;
				String temp = "";
				for (int i=0; i<cnt; i++)
				{
					temp += address.getAddressLine(i) + " ";
				}
				sb.append(address.getFeatureName() + " " + temp + "\n");
			}
			serachText.setText(sb.toString());
			Mylog.v(sb.toString());
		} catch (Exception e) {
			Mylog.v("find error: " +e);
		}
	}
	
	TMapView.OnClickListenerCallback clickCallBack = new TMapView.OnClickListenerCallback() {
		
		@Override
		public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arg0,
				ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
			return false;
		}
		
		@Override
		public boolean onPressEvent(ArrayList<TMapMarkerItem> markers,
				ArrayList<TMapPOIItem> pois, TMapPoint tpoint, PointF pointf) {
			Mylog.v("Latitude : " +tpoint.getLatitude() + "Longitude : " +tpoint.getLongitude());
			Mylog.v("Point x : " +pointf.x + "Point y : " +pointf.y);
			if(markers != null)
			{
				for(TMapMarkerItem item : markers)
				{
					Mylog.v("item title: " +item.getCalloutTitle());
				}
			}
			if(pois != null)
			{
				for(TMapPOIItem item : pois)
				{
					Mylog.v("item name" + item.name + "Distance: " +item.distance);
					
				}
			}
			return true;
		}
	};
	void setTmapLocation(TMapPoint point)
	{
		tmap.setLocationPoint(point.getLongitude(), point.getLatitude());
		tmap.setCenterPoint(point.getLatitude(), point.getLongitude());
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bt_add_gps_on);
		tmap.setIcon(bitmap);
		tmap.setIconVisibility(true);
	}
	
	LocationListener lListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			point = new TMapPoint(location.getLatitude(), location.getLongitude());
			setTmapLocation(point);
			locationManager.removeUpdates(lListener);
		}
	};
	
	public void setMapinfo(TagItem item)
	{
		this.item = item;
		
	}
	
}
