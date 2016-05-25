package com.styletag.tagazine.fragment;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import com.facebook.widget.ProfilePictureView;
import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.MypageAdapter;
import com.styletag.tagazine.adapters.StaggeredAdapter;
import com.styletag.tagazine.json.Item_UserInfo;
import com.styletag.tagazine.json.JSONParser;
import com.styletag.tagazine.utils.NetManager;
import com.styletag.tagazine.views.StaggeredGridView;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyPage_fragment extends android.support.v4.app.Fragment {

	ProgressDialog pDialog;
	Activity activity;
	
	ProfilePictureView profilePictureView;
	TextView tvusername;
	TextView tvuserlocation;
	TextView mystylecounttv;
	TextView favoritecounttv;
	TextView mystyleonoff;
	TextView favoriteonoff;
	
	LinearLayout mypagestyletab;
	LinearLayout mypagefavoritestyletab;
	
	StaggeredGridView mypageGridview;
	MypageAdapter mypageAdapter;
	FragmentManager fragmentmanager;
	
	
	
	View.OnClickListener bHandler = new View.OnClickListener() {
//		magazinetabonoff.setBackgroundResource(R.drawable.main_tabbar_off);
//		feedtaponoff.setBackgroundResource(R.drawable.main_tabbar_on);
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.mypage_userimg:
				break;
			case R.id.btnLogout:
				break;
			case R.id.mypage_mystyletab:
				mystyleonoff.setBackgroundResource(R.drawable.main_tabbar_on);
				favoriteonoff.setBackgroundResource(R.drawable.main_tabbar_off);
				break;
			case R.id.mypage_favoritestyletab:
				favoriteonoff.setBackgroundResource(R.drawable.main_tabbar_on);
				mystyleonoff.setBackgroundResource(R.drawable.main_tabbar_off);
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
	public void onPause() {
//		MyPage_fragment.this.getActivity().finish();
		fragmentmanager = ((Main_Activity)activity).getSupportFragmentManager();
		FragmentTransaction ft = fragmentmanager.beginTransaction();
		ft.remove(MyPage_fragment.this);
		ft.commit();
		super.onPause();
	}
	

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStop() {
		fragmentmanager = ((Main_Activity)activity).getSupportFragmentManager();
		FragmentTransaction ft = fragmentmanager.beginTransaction();
		ft.remove(MyPage_fragment.this);
		ft.commit();
		super.onPause();
		super.onStop();
	}

	public String getUserId()
	{

		SharedPreferences sp = activity.getSharedPreferences("tagazine", 0);
		String id = sp.getString("userid", "");
		Mylog.v("userid: "+ id);
		return id;
	}
	public String getUserName()
	{

		SharedPreferences sp = activity.getSharedPreferences("tagazine", 0);
		String name = sp.getString("name", "");
		Mylog.v("userName: "+ name);
		return name;
	}
	public String getUserLocale()
	{

		SharedPreferences sp = activity.getSharedPreferences("tagazine", 0);
		String location = sp.getString("locale", "");
		Mylog.v("userlocation: "+ location);
		return location;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.mypage_fragment, null);
		Mylog.v("Mypage here");
//		userimg = (ImageView)v.findViewById(R.id.mypage_userimg);
		
		profilePictureView = (ProfilePictureView)v.findViewById(R.id.mypage_userimg);
		profilePictureView.setOnClickListener(bHandler);
		tvusername = (TextView)v.findViewById(R.id.mypage_Nametv);
		tvuserlocation = (TextView)v.findViewById(R.id.mypage_userLocationtv);
		v.findViewById(R.id.btnLogout).setOnClickListener(bHandler);
		mystylecounttv = (TextView)v.findViewById(R.id.mypage_mystylecounttv);
		favoritecounttv = (TextView)v.findViewById(R.id.mypage_favoritecounttv);
		mystyleonoff = (TextView)v.findViewById(R.id.mypage_mystyle_onoff);
		favoriteonoff = (TextView)v.findViewById(R.id.mypage_myfavorite_onoff);
		
		mypagestyletab = (LinearLayout)v.findViewById(R.id.mypage_mystyletab);
		mypagestyletab.setOnClickListener(bHandler);
		mypagefavoritestyletab = (LinearLayout)v.findViewById(R.id.mypage_favoritestyletab);
		mypagefavoritestyletab.setOnClickListener(bHandler);
		
		mypageGridview = (StaggeredGridView)v.findViewById(R.id.mypagegridview);
		
		profilePictureView.setProfileId(getUserId());
		tvusername.setText(getUserName());
		tvuserlocation.setText(getUserLocale());
		
		
		int margin = getResources().getDimensionPixelSize(R.dimen.main_stylefeed_margin);
		mypageGridview.setItemMargin(margin); // set the GridView margin
		mypageGridview.setPadding(10, 12, 10, 0); // have the margin on the sides as well 
		mypageAdapter = new MypageAdapter();
		mypageGridview.setAdapter(mypageAdapter);
		mypageAdapter.notifyDataSetChanged();
		
		return v;
	}
	
	
	class ViewMineThread extends Thread
	{
		
		public void run()
		{
			Mylog.v("run");
			String url = "http://tagazine.nkr1545.cloulu.com/viewmine";
			
			HttpClient client = null;
			HttpPost request = null;
			HttpResponse response = null;
			
			BufferedReader br = null;
			StringBuilder sb = null;
			byte[] brr = null;
			int code = 0;
			Message msg = handler.obtainMessage();
			
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("user_id", getUserId()));
			
			UrlEncodedFormEntity entity = null;
			try {
				Mylog.v("run2");
				entity = new UrlEncodedFormEntity(params);
				client = NetManager.getHttpClient();
				request = NetManager.getPost(url);
				request.setEntity(entity); //post 인경우
				response = client.execute(request);
				code = response.getStatusLine().getStatusCode();
				Mylog.v("code: " +code);
				switch(code)
				{
				case 200:

					String data = IOUtils.toString(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
					Mylog.v("data: "+data);
//					userinfo_data = JSONParser.userinfoParser(data);
//					Mylog.v("product data: " + product_data.size());
					msg.what = 999;
					break;
				}
			} catch (Exception e) {
				Mylog.v("UserinfoThread error : " +e);
			}
			handler.sendMessage(msg);
		}
	}
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			if(pDialog != null)
			{
				pDialog.cancel();
			}
		
			switch(msg.what)
			{
			case 999:
//				adapter.setData(product_data);
//				adapter.notifyDataSetChanged();
				break;
				
			}
			super.handleMessage(msg);
		}
	};
	
}
