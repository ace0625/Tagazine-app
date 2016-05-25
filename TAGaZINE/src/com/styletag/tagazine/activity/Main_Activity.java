package com.styletag.tagazine.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.styletag.tagazine.adapters.MenuList_Adapter;
import com.styletag.tagazine.adapters.MenuList_Item;
import com.styletag.tagazine.fragment.CameraCall_fragment;

import com.styletag.tagazine.fragment.Magazine_fragment;
import com.styletag.tagazine.fragment.Main_Stylefeed_fragment;
import com.styletag.tagazine.fragment.Searchable_fragment;
import com.styletag.tagazine.json.Item_Detail;
import com.styletag.tagazine.json.Item_Menu;
import com.styletag.tagazine.json.Item_Menulist_Brand;
import com.styletag.tagazine.json.Item_Menulist_StyleLook;
import com.styletag.tagazine.json.JSONParser;
import com.styletag.tagazine.utils.NetManager;
import com.styletag.tagazine.views.ProgressWheel;
import com.styletag.tagazine.views.SlidingView;
public class Main_Activity extends FragmentActivity {
	
	FrameLayout mainimgfeedlayout; //style feed 이미지 레이아웃
	public SlidingView slidingview; //메인페이지 슬라이딩 뷰
	LinearLayout cameraCallLayout; //call camera & gallery buttons 
	android.support.v4.app.FragmentManager fragmentmanager = null; //프래그먼트 매니저
	
	Magazine_fragment magazineFragment; //메거진 화면 프래그먼트
	
	Searchable_fragment searchablefragment; //검색화면 프래그먼트
	
	public Main_Stylefeed_fragment mainstylefeedfragment; //메인 스타일 피드 프래그먼트
	
	CameraCall_fragment cameracallfragment;


//	Login_fragment loginfragment;
	TextView feedtaponoff;   //turn on/off feed tab
	TextView magazinetabonoff; //turn on/off magazine tab
	  public static int loginornot = 0;
	private static final int REQ_PICTURE = 0; 
	private static final int REQ_GALLERY = 1;
	Uri photo_uri;
	
	Item_Menu item_menu;
	ListView menulist; //메뉴 리스트뷰
	ArrayList<MenuList_Item> menudata; //메뉴 리스트 아이템
	MenuList_Adapter menuadapter; //메뉴 리스트 어뎁터
	
	
	LinearLayout mainmMenuTab;
	FrameLayout mainTab;
	
	public ProgressWheel progresswheel;
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btnSlide:  //메뉴버튼 보이기 버튼
				int state1 = 0;
				if(state1==0)
				{
					slidingview.toggleMenu();
					mainTab.requestFocus();
					mainTab.requestFocusFromTouch();
					state1 = 1;
				}
				else if(state1 == 1)
				{
					slidingview.toggleMenu();
					mainmMenuTab.requestFocus();
					mainmMenuTab.requestFocusFromTouch();
					state1 = 0;
				}
				break;
			case R.id.btnSearch: //검색 버튼
				callSearchPage();
				break;
			case R.id.btnCamera: //카메라/갤러리 보이기 버튼
				cameraCall();
				break;
			case R.id.btnStyleFeed: //스타일 피드로 가기버튼
				magazinetabonoff.setBackgroundResource(R.drawable.main_tabbar_off);
				feedtaponoff.setBackgroundResource(R.drawable.main_tabbar_on);
				call_main_stylefeed();
				break;
			case R.id.btnMagazine: //매거진화면으로 가기버튼
//				mainimgfeedlayout.setVisibility(View.INVISIBLE);
				feedtaponoff.setBackgroundResource(R.drawable.main_tabbar_off);
				magazinetabonoff.setBackgroundResource(R.drawable.main_tabbar_on);
				callMagazineFragment();
				break;
//			case R.id.btnLoginmenu:
//				Intent intent2 = new Intent(MainActivity.this, Login_Activity.class);
//				startActivity(intent2);
//				break;
			}
			
		}
	};
	String check = "";
	String userid = "";
	String username = "";
	String userlocation = "";
	public String loginCheck()
	{

		SharedPreferences sp1 = getSharedPreferences("tagazine", 0);
//		int logincheck = sp.getInt("loginCheck", 0);
		String logcheck = sp1.getString("loginCheck", "");
		Mylog.v("checkLoginnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn: "+ logcheck);
		return logcheck;
	}
	public String getuser_id()
	{
		SharedPreferences sp2 = getSharedPreferences("tagazine", 0);
		String userId = sp2.getString("userid", "");
		return userId;
	}
	public String getuser_name()
	{
		SharedPreferences sp3 = getSharedPreferences("tagazine", 0);
		String username = sp3.getString("name", "");
		return username;
	}
	public String getuser_location()
	{
		SharedPreferences sp4 = getSharedPreferences("tagazine", 0);
		String userlocale = sp4.getString("locale", "");
		return userlocale;
	}
	
	static int loadingcnt = 0;
	
	class StyleTask extends AsyncTask<Object, Void, Object>
	{
	
		Object obj;
		@Override
		protected Object doInBackground(Object... params) {
			Mylog.v("Style task start");
			
			
			HttpClient client = null;
			HttpGet request = null;
			HttpResponse response = null;
			
			BufferedReader br = null;
			StringBuilder sb = null;
			byte[] brr = null;
			int code = 0;

			UrlEncodedFormEntity entity = null;
			try {
				
				String url = "http://tagazine.nkr1545.cloulu.com/menu/list";
				Mylog.v("styletask 2");
				
//				entity = new UrlEncodedFormEntity(params1);
				client = NetManager.getHttpClient();
				request = NetManager.getGet(url); 
				response  = client.execute(request);
				code = response.getStatusLine().getStatusCode();
				Mylog.v("code: " +code);
				
				switch(code)
				{
				case 200:
					String data = IOUtils.toString(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
					Mylog.v("data: "+data);
					 obj = JSONParser.menuParser(data);
					Mylog.v("style data: " + obj);
				
				
					break;
				}
			} catch (Exception e) {
				Mylog.v("detail Task error: " +e);
			}
			return obj;
		}

		@Override
		protected void onPostExecute(Object result) {
			menudata.clear();
			item_menu = (Item_Menu)result;
			
			Mylog.v("item menu data: " +item_menu);
//			check = loginCheck();
//			if(loginornot == 0)
			String coms= "logged";
			
			if(!check.equals(coms))
			{
				Mylog.v("first 0 ");
				Mylog.v("caaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +check);
				menudata.add(new MenuList_Item(0, "" , "Log In", ""));	
				
				menudata.add(new MenuList_Item(1, "RECOMMEND"));
				menudata.add(new MenuList_Item(2, "Power Tagger"));
				menudata.add(new MenuList_Item(2, "MD's Pick"));
				menudata.add(new MenuList_Item(2, "Hot Street Fashion"));
				
				menudata.add(new MenuList_Item(1, "STYLE LOOK"));
//					
				
				for( Item_Menulist_StyleLook sItem : item_menu.getStyleList())
				{
					menudata.add(new MenuList_Item(2, sItem.getTitle() ));
				}
				menudata.add(new MenuList_Item(1, "BRAND"));
				for(Item_Menulist_Brand bItem : item_menu.getBrandList())
				{
					menudata.add(new MenuList_Item(2, bItem.getTitle()));
				}
					
				
//				menudata.add(new MenuList_Item(2, "Daily Look"));
//				menudata.add(new MenuList_Item(2, "Modern Look"));
//				menudata.add(new MenuList_Item(2, "Vintage Look"));
//				menudata.add(new MenuList_Item(2, "Unique Look"));
//				menudata.add(new MenuList_Item(2, "Chic Look"));
//				menudata.add(new MenuList_Item(2, "Celeb Look"));
				
//				menudata.add(new MenuList_Item(2, "Abercrombie"));
//				menudata.add(new MenuList_Item(2, "A-Land"));
//				menudata.add(new MenuList_Item(2, "ZARA"));
//				menudata.add(new MenuList_Item(2, "Forever 21"));
//				menudata.add(new MenuList_Item(2, "H&M"));
//				menudata.add(new MenuList_Item(2, "8 seconds"));
		
				
			}
			
//			else if (check == "logged")
			else
			{	
				Mylog.v("second 222 ");
				Mylog.v("dddddddddddddddddddddddddddddddddddddddd" +check);
//				String userid = getIntent().getStringExtra("id");
//				String username = getIntent().getStringExtra("name");
//				String userlocation = getIntent().getStringExtra("location");
				userid = getuser_id();
				username = getuser_name();
				userlocation = getuser_location();
				Mylog.v("id: " + userid + "  name: " + username + "  location: " + userlocation);
				menudata.add(new MenuList_Item(0, userid , username,  userlocation));	
				menudata.add(new MenuList_Item(1, "RECOMMEND"));
				menudata.add(new MenuList_Item(2, "Power Tagger"));
				menudata.add(new MenuList_Item(2, "MD's Pick"));
				menudata.add(new MenuList_Item(2, "Hot Street Fashion"));
				
				menudata.add(new MenuList_Item(1, "STYLE LOOK"));
				for( Item_Menulist_StyleLook sItem : item_menu.getStyleList())
				{
					menudata.add(new MenuList_Item(2, sItem.getTitle() ));
				}				
				menudata.add(new MenuList_Item(1, "BRAND"));
				
				for(Item_Menulist_Brand bItem : item_menu.getBrandList())
				{
					menudata.add(new MenuList_Item(2, bItem.getTitle()));
				}
					
				
//				menudata.add(new MenuList_Item(2, "Daily Look"));
//				menudata.add(new MenuList_Item(2, "Modern Look"));
//				menudata.add(new MenuList_Item(2, "Vintage Look"));
//				menudata.add(new MenuList_Item(2, "Unique Look"));
//				menudata.add(new MenuList_Item(2, "Chic Look"));
//				menudata.add(new MenuList_Item(2, "Celeb Look"));
//				menudata.add(new MenuList_Item(2, "Abercrombie"));
//				menudata.add(new MenuList_Item(2, "A-Land"));
//				menudata.add(new MenuList_Item(2, "ZARA"));
//				menudata.add(new MenuList_Item(2, "Forever 21"));
//				menudata.add(new MenuList_Item(2, "H&M"));
//				menudata.add(new MenuList_Item(2, "8 seconds"));

			}
			menuadapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
//			pDialog = ProgressDialog.show(getActivity(), "", "Loading...");
			super.onPreExecute();
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		this.slidingview = (SlidingView)this.getLayoutInflater().inflate(R.layout.activity_main, null);
		setContentView(R.layout.main);
		if(loadingcnt==0)
		{
			startActivity(new Intent (this, LoadingPage_Activity.class));//로딩화면
			loadingcnt++;
			Mylog.v("loading count: " + loadingcnt);
		}
				check = loginCheck();
				Mylog.v("cccccccccccccccccccccccccccccccccccccccccccccccccc" +check);
		slidingview = (SlidingView)findViewById(R.id.MainViewSlide);
		
//		boolean kkkk = loginCheck();
//		Mylog.v("login check--------------------------------------------------------: " +kkkk);
		
		mainTab = (FrameLayout)findViewById(R.id.Main_feedlayout);
		mainmMenuTab = (LinearLayout)findViewById(R.id.Main_menulayout);
		
		findViewById(R.id.btnSlide).setOnClickListener(bHandler);
		findViewById(R.id.btnSearch).setOnClickListener(bHandler);
		findViewById(R.id.btnCamera).setOnClickListener(bHandler);
		findViewById(R.id.btnStyleFeed).setOnClickListener(bHandler);
		findViewById(R.id.btnMagazine).setOnClickListener(bHandler);
//		findViewById(R.id.btnCallCamera).setOnClickListener(bHandler);
//		findViewById(R.id.btnCallGallery).setOnClickListener(bHandler);
//		findViewById(R.id.btnLoginmenu).setOnClickListener(bHandler);
		
		mainimgfeedlayout = (FrameLayout)findViewById(R.id.Main_imgFeedLayout); 
		feedtaponoff = (TextView)findViewById(R.id.Feedtabonoff);
		magazinetabonoff = (TextView)findViewById(R.id.Magazinetabonoff);
		cameraCallLayout = (LinearLayout)findViewById(R.id.CameraCallLayout);
		
		call_main_stylefeed();

		menulist = (ListView)findViewById(R.id.Menu_listview);
		menudata = new ArrayList<MenuList_Item>();

	
		new StyleTask().execute(check);

		
		menuadapter = new MenuList_Adapter(this, menudata);
		menulist.setAdapter(menuadapter);
		
	}
	
	@Override
	protected void onDestroy() {
		
		loadingcnt = 0;
		Mylog.v("destroy loadingcnt: " +loadingcnt);
		super.onDestroy();
	}

	/*
	 * 카메라 버튼 눌렀을때 밑에 카메라 버튼과 갤러리버튼 보이기
	 */
	int state = 0;
	void cameraCall()
	{
		Mylog.v("cameracall fragment");
		if(state == 0)
		{
			fragmentmanager = getSupportFragmentManager();
			cameracallfragment = new CameraCall_fragment();
			android.support.v4.app.FragmentTransaction ft = fragmentmanager.beginTransaction();
//			ft.replace(R.id.CameraCallLayout, cameracallfragment);
			ft.add(R.id.CameraCallLayout, cameracallfragment);
			ft.commit();
//			cameraCallLayout.setVisibility(View.VISIBLE);
			state = 1;
		}
		else if (state == 1)
		{
			android.support.v4.app.FragmentTransaction ft = fragmentmanager.beginTransaction();
			ft.hide(cameracallfragment);
			ft.commit();
//			cameraCallLayout.setVisibility(View.INVISIBLE);
			state = 0;
		}
	}
	/*
	 * 사진찍기 버튼
	 */
	void takePic()
	{
		if( Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ){
    		// sd 카드 경로 가져오기
    		String SD_path = Environment.getExternalStorageDirectory().getAbsolutePath(); //sd 카드 루트경로.
    		SD_path += "/testCamera"; // 사용할 폴더. 경로만 지정한것이지 실제로 폴더가 생기거나 한건 아니다.
    		
   
    		File photoFile = new File(SD_path);
    		if(!photoFile.exists()){
    			Mylog.v("Image directory does not exist!!! new make directory");
    			photoFile.mkdir();
    		}else{
    			Mylog.v("photoFile : " + photoFile.getPath());
    		}
			Mylog.v("SD_path : " + SD_path);
			Mylog.v("photoFile : " + photoFile.getPath());
    		
    		// "eastgem test shoot XXXX-XX-XX XX:XX:XX.jpg" 라는 이름이 될것이다.
    		SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    				//			    				"yyyy-MM-dd"); 
    		String filename = "/tagazinephoto " + timeStampFormat.format(new Date()).toString() + ".png";

    		// 폴더 생성이나 점검을 완료했으니 파일을 직접 생성하자.
    		photoFile = new File(SD_path + filename); // 사진이 저장될 경로 및 파일명.
    		photo_uri = Uri.fromFile(photoFile);

    		Mylog.v("new photoFile : " + photoFile.getPath());
    		Mylog.v("Uri photo_uri : " + photo_uri.toString());
			
			// 카메라 Activity 부를때 사용할 인텐트
    		Intent intent_Camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    		intent_Camera.putExtra(MediaStore.EXTRA_OUTPUT, photo_uri);
    		startActivityForResult(intent_Camera, REQ_PICTURE);
		}else{
			Mylog.v("SD카드가 장착되어 있지 않습니다.");
		}
	}
	/*
	 * 갤러리 불러오기
	 */
	void callGallery()
	{
		Intent intent_getPicture = new Intent(Intent.ACTION_PICK);
		intent_getPicture.setType("image/*");
		intent_getPicture.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		intent_getPicture.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent_getPicture, REQ_GALLERY);
	}
	/*
	 * 매거진버튼 눌렀을때 매거진 페이지로 넘어가기
	 */
	void callMagazineFragment()
	{
		Mylog.v("magazine fragment success");
		fragmentmanager = getSupportFragmentManager();
		magazineFragment = new Magazine_fragment();
		android.support.v4.app.FragmentTransaction ft = fragmentmanager.beginTransaction();
//		ft.replace(R.id.Main_imgFeedLayout, magazineFragment);
		ft.replace(R.id.Main_imgFeedLayout, magazineFragment);
		ft.commit();
		
	}
	/*
	 *  검색버튼 눌렀을때 검색 창 보이기
	 */
	void callSearchPage()
	{
		Mylog.v("search page here");
		fragmentmanager = getSupportFragmentManager();
		searchablefragment = new Searchable_fragment();
		android.support.v4.app.FragmentTransaction ft1 = fragmentmanager.beginTransaction();
		ft1.replace(R.id.Main_feedlayout, searchablefragment);
		ft1.commit();
	}
	
	void call_main_stylefeed()
	{
		Mylog.v("main stylefeed here");
		fragmentmanager = getSupportFragmentManager();
		mainstylefeedfragment = new Main_Stylefeed_fragment();
		android.support.v4.app.FragmentTransaction ft2 = fragmentmanager.beginTransaction();
		ft2.replace(R.id.Main_imgFeedLayout, mainstylefeedfragment);
		ft2.commit();	
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	public void toggleMenu(View v)
//	{
//		this.slidingview.toggleMenu();
//	}
}
