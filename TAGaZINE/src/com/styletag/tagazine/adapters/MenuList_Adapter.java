package com.styletag.tagazine.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.styletag.tagazine.activity.LogIn_Activity;
import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.fragment.HotStreetFashion_fragment;

import com.styletag.tagazine.fragment.MDsPick_fragment;
import com.styletag.tagazine.fragment.Main_Stylefeed_fragment;
import com.styletag.tagazine.fragment.MyPage_fragment;

import com.styletag.tagazine.fragment.PowerTagger_fragment;


public class MenuList_Adapter extends BaseAdapter {
	ArrayList<MenuList_Item> data;
	Context context;
	int layout;
	Main_Activity activity;
	android.support.v4.app.FragmentManager fragmentmanager = null; 
	
//	Login_fragment longinfragment;
	private static final int LOG_IN = 0;
	private static final int RECOMMEND = 1; //section 1
	private static final int POWER_TAGGER = 2;
	private static final int MD_PICK = 3;
	private static final int HOT_STREAT_FASHION = 4;
	private static final int STYLE_LOOK = 5; //section 2
	private static final int DAILY_LOOK = 6;
	private static final int MODERN_LOOK = 7;
	private static final int VINTAGE_LOOK = 8;
	private static final int UNIQUE_LOOK = 9;
	private static final int CHIC_LOOK = 10;
	private static final int CELEB_LOOK = 11;
	private static final int BRAND = 12; //section 3
	private static final int ABERCROMBIE = 13;
	private static final int A_LAND = 14;
	private static final int ZARA = 15;
	private static final int FOREVER21 = 16;
	private static final int HnM = 17;
	private static final int EIGHT_SECONDS = 18;
	
	
	Main_Stylefeed_fragment mainFlagment = null;
	public MenuList_Adapter(Main_Stylefeed_fragment mainFlagment, Context context, int layout, ArrayList<MenuList_Item> data)
	{
		this.context = context;
		this.layout = layout;
		this.data = data;
		this.mainFlagment = mainFlagment;
	}
	public MenuList_Adapter(Context context, ArrayList<MenuList_Item> data)
	{
		this.context = context;
		this.data = data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View cView, ViewGroup parent) {
		final MenuList_Item item = data.get(position);
		switch(item.getType())
		{
		case 0: //user 
			cView = View.inflate(context, R.layout.menu_list_user, null);
//			ImageView userimg = (ImageView)cView.findViewById(R.id.imgUserImage);
			ProfilePictureView profilePictureView = (ProfilePictureView)cView.findViewById(R.id.friendProfilePicture);
			TextView userName = (TextView)cView.findViewById(R.id.tvUserName);
			TextView userLocation = (TextView)cView.findViewById(R.id.tvUserLocation);
			
//			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);
//			userimg.setImageBitmap(bitmap);
			profilePictureView.setProfileId(item.getUserid());
			userName.setText(item.getTitle());
			userLocation.setText(item.getSub());
			
			break;
		case 1: //section bar
			cView = View.inflate(context, R.layout.menu_list_header, null);
			TextView titleheader = (TextView)cView.findViewById(R.id.tvheader);
			titleheader.setText(item.getTitle());
			break;
		case 2: //sublists
			cView = View.inflate(context, R.layout.menu_list_item, null);
			TextView sublist = (TextView)cView.findViewById(R.id.tvsubtitle);
			sublist.setText(item.getTitle());
			break;
				
		}
	
		cView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int type = item.getType();
				switch(type){
				case 0 :
//					String userId = item.getUserid();
					if(loginCheck().equals("logged"))
					{
						Mylog.v("mypage call");
						myPageCall();
					}
					else
					{
						Intent login = new Intent(context, LogIn_Activity.class);
						context.startActivity(login);
					}
//					if(userId.equals("")){
//	//					facebookLogin();
//						Intent login = new Intent(context, LogIn_Activity.class);
//						context.startActivity(login);
//					}else{
//						// logout
//						
//					}
				case 2 :
					Main_Activity activity = (Main_Activity)context;
					activity.mainstylefeedfragment.getCategory(item.getTitle());
					sliding();
					
					break;
				}
//				switch(position)
//				{
//				case LOG_IN:
////					facebookLogin();
//					Intent login = new Intent(context, LogIn_Activity.class);
//					context.startActivity(login);
//					break;
//				case RECOMMEND:
//					v.setOnClickListener(null);
//					break;
//				case POWER_TAGGER:
//					powerTaggerCall();
//					break;
//				case MD_PICK:
//					mdsPickCall();
//					break;
//				case HOT_STREAT_FASHION:
//					hotStreetFashionCall();
//					break;
//				case STYLE_LOOK:
//					v.setOnClickListener(null);
//					break;
//				case DAILY_LOOK:
//					dailyLookCall();
//					break;
//				case MODERN_LOOK:
//					moderLookCall();
//					break;
//				case VINTAGE_LOOK:
//					vintageLookCall();
//					break;
//				case UNIQUE_LOOK:
//					uniqueLookCall();
//					break;
//				case CHIC_LOOK:
//					chicLookCall();
//					break;
//				case CELEB_LOOK:
//					celebLookCall();
//					break;
//				case BRAND:
//					v.setOnClickListener(null);
//					break;
//				case ABERCROMBIE:
//					break;
//				case A_LAND:
//					break;
//				case ZARA:
//					break;
//				case FOREVER21:
//					break;
//				case HnM:
//					break;
//				case EIGHT_SECONDS:
//					break;
//				}
//				
			}
		});
		return cView;
	}
	
	public String loginCheck()
	{

		SharedPreferences sp1 = context.getSharedPreferences("tagazine", 0);
//		int logincheck = sp.getInt("loginCheck", 0);
		String logcheck = sp1.getString("loginCheck", "");
		Mylog.v("checkLoginn "+ logcheck);
		return logcheck;
	}
	
	
	public void sliding() {
//		Toast.makeText(activity, "여기", 0).show();
		Mylog.v("Sliding back");
		((Main_Activity)context).slidingview.toggleMenu();
	}
	
	/*
	 * Facebook Login
	 */
//	void facebookLogin()
//	{
//		Mylog.v("Facebook login page");
//		Login_fragment loginfragment;
//		fragmentmanager = ((Main_Activity)context).getSupportFragmentManager();
//		loginfragment = new Login_fragment();
//		android.support.v4.app.FragmentTransaction ft = fragmentmanager.beginTransaction();
//		ft.replace(R.id.Main_feedlayout, loginfragment);
//		ft.addToBackStack(null);
//		ft.commit();
//	}
	/*
	 * MyPage
	 */
	void myPageCall()
	{
		MyPage_fragment myPagefragment;
		fragmentmanager = ((Main_Activity)context).getSupportFragmentManager();
		myPagefragment = new MyPage_fragment();
		FragmentTransaction ft = fragmentmanager.beginTransaction();
		ft.replace(R.id.Main_notablayout, myPagefragment);
		ft.addToBackStack(null);
		ft.commit();
		
	}
	/*
	 * Menu Power Tagger call
	 */
	void powerTaggerCall()
	{		
		Mylog.v("Powertagger fragment here from menulist adapter");
		PowerTagger_fragment powertaggerfragment; //파워태거 프래그먼트
		fragmentmanager = ((Main_Activity)context).getSupportFragmentManager();
		powertaggerfragment = new PowerTagger_fragment();
		android.support.v4.app.FragmentTransaction ft = fragmentmanager.beginTransaction();
		ft.replace(R.id.Main_imgFeedLayout, powertaggerfragment);
		ft.addToBackStack(null);
		ft.commit();
//		powertaggerfragment.who_calls = 3;
	}
	/*
	 * Menu MD's pick call
	 */
	void mdsPickCall()
	{ 
		
		Mylog.v("MD's Pick Fragment here from menulist adapter");
		MDsPick_fragment mdspickfragment;
		fragmentmanager = ((Main_Activity)context).getSupportFragmentManager();
		mdspickfragment = new MDsPick_fragment();
		android.support.v4.app.FragmentTransaction ft1 = fragmentmanager.beginTransaction();
		ft1.replace(R.id.Main_imgFeedLayout, mdspickfragment);
		ft1.addToBackStack(null);
		ft1.commit();
	}
	/*
	 * Menu Hot Street Fashion call
	 */
	void hotStreetFashionCall()
	{
		Mylog.v("Hot street Fashion fragment from menulist adapter");
		HotStreetFashion_fragment hotstreetfashionfragment;
		fragmentmanager = ((Main_Activity)context).getSupportFragmentManager();
		hotstreetfashionfragment = new HotStreetFashion_fragment();
		android.support.v4.app.FragmentTransaction ft2 = fragmentmanager.beginTransaction();
		ft2.replace(R.id.Main_imgFeedLayout, hotstreetfashionfragment);
		ft2.addToBackStack(null);
		ft2.commit();
	}
	
}
