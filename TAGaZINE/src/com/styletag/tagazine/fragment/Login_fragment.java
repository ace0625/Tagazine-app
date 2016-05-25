package com.styletag.tagazine.fragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.LoginActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.MenuList_Adapter;
import com.styletag.tagazine.adapters.MenuList_Item;

public class Login_fragment extends Fragment {

	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;

	LoginButton authButton;
	Session session;
	
	Main_Activity activity;
	MenuList_Adapter menulistadapter;
	
	public static int loginornot = 0;
	public String userId;
	public String userEmail;
	public String userName;
	public String userLocation;
	
	MenuList_Item menu_item;
	ArrayList<MenuList_Item> data = new ArrayList<MenuList_Item>();
	ListView menulist;
	@Override
	public void onAttach(Activity activity) {
		this.activity = (Main_Activity)activity;
		super.onAttach(activity);
		Mylog.v("facebook fragment");
	}
	@Override
	public void onResume() {
		sliding();
		super.onResume();
	}
	
	
	@Override
	public void onPause() {
//		activity.finish();
		
		Login_fragment.this.getActivity().finish();
		super.onPause();
	}


	public void autoLogInFB()
	{
		authButton.performClick();
	}
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.login_main, null);
		try {
	        PackageInfo info = getActivity().getPackageManager().getPackageInfo(
	        		"com.styletag.tagazine.activity", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
		authButton = (LoginButton)v.findViewById(R.id.btn_facebook_login);
		authButton.setFragment(this);
		authButton.setVisibility(View.VISIBLE);
		menulist = (ListView)v.findViewById(R.id.Menu_listview);
		authButton.setOnErrorListener(new OnErrorListener() {
			
			@Override
			public void onError(FacebookException error) {
				Toast.makeText(getActivity(), "로그인 에러입1니다.1", Toast.LENGTH_SHORT).show();
			}
		});
		authButton.setReadPermissions(Arrays.asList("basic_info","email"));
		authButton.setSessionStatusCallback(new Session.StatusCallback() {

			@SuppressWarnings("deprecation")
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if(session.isOpened()){
					Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

						@Override
						public void onCompleted(GraphUser user, Response response) {
							
							if (user != null) { 
								Context context  = getActivity();
								userId = user.getId();
                                Mylog.v("User ID "+ user.getId());
                                Mylog.v("Email "+ user.asMap().get("email"));
                                Mylog.v("name "+ user.asMap().get("name"));
                                
                                userId = user.getId();
                            	userEmail = (String) user.asMap().get("email");
                            	userName = (String) user.asMap().get("name");
                            	userLocation = (String) user.asMap().get("locale");
//                                menulistadapter.profilePictureView.setProfileId(userId);
                            	if(user != null)
                            	{
                            		setLoginvalues(user);
                            		loginornot = 1;
                            		getFacebookInfo();
                            	}
								
							}
								
						}
					});
				}
			}
		});
		return v;
	}

	public void setLoginvalues(GraphUser user){
		SharedPreferences login = getActivity().getSharedPreferences("login", activity.MODE_PRIVATE);

		SharedPreferences.Editor editor = login.edit();
		editor.putBoolean("checkLogin", true);
		editor.putString("name", user.asMap().get("name").toString());
		editor.putString("email", user.asMap().get("email").toString());
		editor.putString("userId", user.getId());
		editor.commit();
		
	}
	public boolean checkLogin(){
		SharedPreferences login = getActivity().getSharedPreferences("login", activity.MODE_PRIVATE);
		boolean a = login.getBoolean("checkLogin",false );
		
		
		
		return login.getBoolean("checkLogin",false );
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
	}
	
	public void sliding() {
		Mylog.v("Sliding back");
		activity.slidingview.toggleMenu();
	}
	public void getFacebookInfo()
	{
		
		 menu_item = new MenuList_Item(0, userId, userName, userLocation);
		 data.add(menu_item);
		 menulistadapter = new MenuList_Adapter(activity, R.layout.menu_list_user, data);
		 menulist.setAdapter(menulistadapter);
//		 menu_item.setUserid(userId);
//		 menu_item.setTitle(userName);
//		 menu_item.setSub(userLocation);
		 
		 
//		 menu_item.setUserLocation(userLocation);
		 
		
//		adapter = new MyAdapter(this, R.layout.item, data);
//		list.setAdapter(adapter);
	}

	
}
