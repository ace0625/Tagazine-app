package com.styletag.tagazine.activity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Session.StatusCallback;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.styletag.tagazine.adapters.MenuList_Adapter;
import com.styletag.tagazine.adapters.MenuList_Item;
import com.styletag.tagazine.adapters.TagItem;
import com.styletag.tagazine.utils.NetManager;

public class LogIn_Activity extends Activity {
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
    private boolean pendingPublishReauthorization = false;
     
    Main_Activity main_activity;
    LoginButton authButton = null;
    Session session;
  
    public String userId;
    public String userEmail;
    public String userName;
    public String userLocation;
    
	MenuList_Item menu_item;
	ArrayList<MenuList_Item> data = new ArrayList<MenuList_Item>();
	ListView menulist;
	MenuList_Adapter menulistadapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login_main);
	    Mylog.v("LogIN activity");
	    
	    try {
	        PackageInfo info = getPackageManager().getPackageInfo(
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
	    authButton = (LoginButton)findViewById(R.id.btn_facebook_login);
	    authButton.setVisibility(View.VISIBLE);
        authButton.setOnErrorListener(new OnErrorListener() {      
            @Override
            public void onError(FacebookException error) {
                Mylog.v( "로그인 에러 : " + error);
            }
        });
        
      
        authButton.setReadPermissions(Arrays.asList("basic_info","email"));
        
        
        
        authButton.setSessionStatusCallback(new Session.StatusCallback() {
            @SuppressWarnings("deprecation")
			@Override
            public void call(Session session, SessionState state, Exception exception) { 
                if (session.isOpened()) {
                	
//                    shareButton.setVisibility(View.VISIBLE);
                    
                    Request.newMeRequest(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user,Response response) {    
                            if (user != null) { 
                                // 로그인 성공 (user에 정보가 들어있음.)
                            
                            	
                            	main_activity.loginornot = 1;
                                Mylog.v("User ID "+ user.getId());
                                Mylog.v("Email "+ user.asMap().get("email"));
                                Mylog.v("name "+ user.asMap().get("name"));
                                Mylog.v("location "+ user.asMap().get("locale"));
                               
                            	userId = user.getId();
                            	userEmail = (String) user.asMap().get("email");
                            	userName = (String) user.asMap().get("name");
                            	userLocation = (String) user.asMap().get("locale");
                            	
                            	setLoginvalues(user);
                            	new LoginThread().start();
//                            	checkLogin();
//                                	getFacebookInfo();
                            
                            	  Handler handler = new Handler();
                          	    handler.postDelayed(new Runnable() {
                          			
                          			@Override
                          			public void run() {                        				
                          				Toast.makeText(LogIn_Activity.this, "Login Success", Toast.LENGTH_SHORT).show();
                          				
                          				Intent intent = new Intent(LogIn_Activity.this, Main_Activity.class);
//                          				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                          				intent.putExtra("id", userId);
                          				intent.putExtra("name", userName);
                          				intent.putExtra("email", userEmail);
                          				intent.putExtra("location", userLocation);
                          				startActivity(intent);                          				
                          			}
                          		}, 500);
//                            	
                            	
                            }
                            
                        }
                        
                    }).executeAsync();
                }else{
//                	shareButton.setVisibility(View.INVISIBLE);
                }
            }
        });
	}
	
	class LoginThread extends Thread
	{
		public void run()
		{
			String url = "http://tagazine.nkr1545.cloulu.com/login";
			HttpClient client = NetManager.getHttpClient();
			HttpPost post = NetManager.getPost(url);
			HttpResponse response = null;
			BufferedReader br = null;
			StringBuffer sb = null;
			String line = "";
			Mylog.v("Login Thread start");
			
			String userId = getUserId();
			String nickName = userEmail;
			int idx = nickName.indexOf("@");
			nickName = nickName.substring(0, idx);
			Mylog.v("nick name : " +nickName);
			try{
				Mylog.v("Login Thread");
//				
//				FileInputStream fis = new FileInputStream (new File(origin_img));
//				byte[] byteData = IOUtils.toByteArray(fis);
//				InputStreamBody pro_img = 
//						new InputStreamBody(new ByteArrayInputStream(byteData), userId + dateString+".PNG");
			
				
				Mylog.v("login 11");
				StringBody userId1 = new StringBody(userId, Charset.forName("UTF-8"));
				StringBody nickname = new StringBody(nickName, Charset.forName("UTF-8"));
				
				
				Mylog.v("add part");
		
				MultipartEntity entity = new MultipartEntity();
				entity.addPart("user_id", userId1);
				entity.addPart("nickname", nickname);
//				entity.addPart("profile_img", pro_img);
			
				Mylog.v("add part2");
				
				post.setEntity(entity);
				response = client.execute(post);
				int code = response.getStatusLine().getStatusCode();
				Mylog.v("fff : " + code);
				
				switch(code)
				{
				case 200 :
//					br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"euc-kr"));
					br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					sb = new StringBuffer();
					while((line = br.readLine())!= null)
					{
						sb.append(line + "\n");
					}
					Mylog.v("process msg : " + sb);
//					data = sb.toString();
					handler1.sendEmptyMessage(0);
//					Message msg = Message.obtain();
//					msg.obj = sb.toString();
//					handler.handleMessage(msg);
				
					break;
				default :
				}
				
			}catch(Exception e){
				Mylog.v("Image upload error : " + e);
			}finally{
				try{
					br.close();
				}catch(Exception e){}
			}
		}
	}
	
	Handler handler1 = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
//			if(pDialog != null)
//			{
//				pDialog.cancel();
//				
//			}
			switch(msg.what)
			{
			case 200:
				break;
			case 999:
				break;
			case 777:
			break;
			}
			super.handleMessage(msg);
		}
	};
		
	
	
	public String getUserId()
	{
//		String userid = getIntent().getStringExtra("id");
//		String userid = (String) getIntent().getExtras().get("id");
//		Mylog.v("userid: " +userid);
		SharedPreferences sp = getSharedPreferences("tagazine", 0);
		String id = sp.getString("userid", "");
		Mylog.v("userid: "+ id);
		return id;
	}
//	public String getNickName(String userEmail);
//	{
////		String email = getIntent().getStringExtra("email");
//		String email = userEmail;
//		Mylog.v("email: " +email);
//		int idx = email.indexOf("@");
//		email = email.substring(0, idx);
//		
//		return email;
//	}
	public void setLoginvalues(GraphUser user)
	{
		SharedPreferences login = getSharedPreferences("tagazine", MODE_PRIVATE);

		SharedPreferences.Editor editor = login.edit();
//		editor.putBoolean("checkLogin", true);
		editor.putString("loginCheck", "logged");
		editor.putString("userid", user.getId());
		editor.putString("name", user.asMap().get("name").toString());
		editor.putString("email", user.asMap().get("email").toString());
		editor.putString("locale", user.asMap().get("locale").toString());
		editor.commit();
	}
	public boolean checkLogin()
	{
		SharedPreferences login = getSharedPreferences("login",MODE_PRIVATE);
		boolean a = login.getBoolean("checkLogin",false );

		return login.getBoolean("checkLogin",false );	
	}
	
	   // facebook에 포스팅
    private void publishStory() {
        Session session = Session.getActiveSession();
        if (session != null) {
             
            // Check for publish permissions    
            List<String> permissions = session.getPermissions();
            if (!isSubsetOf(PERMISSIONS, permissions)) {
                pendingPublishReauthorization = true;
                Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, PERMISSIONS);
                session.requestNewPublishPermissions(newPermissionsRequest);
                return;
           }
 
            Bundle postParams = new Bundle();
            postParams.putString("name", "facbTest");
            postParams.putString("caption", "다음이미지");
            postParams.putString("description", "다음이미지 업로드");
            postParams.putString("link", "http://www.daum.net");
            postParams.putString("picture", "https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-frc3/p480x480/1175078_10200115247238891_466853508_n.jpg");
 
            Request.Callback callback= new Request.Callback() {
                public void onCompleted(Response response) {
                    JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
                    String postId = null;
                    try {
                        postId = graphResponse.getString("id");
                        Toast.makeText(
                                LogIn_Activity.this,
                                "등록성공",
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        //Log.i(TAG, "JSON error "+ e.getMessage());
                    }
                     
                    FacebookRequestError error = response.getError();
                    if (error != null) {
                        Toast.makeText(LogIn_Activity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                            Toast.makeText(LogIn_Activity.this, postId, Toast.LENGTH_LONG).show();
                    }
                }
            };
 
            Request request = new Request(session, "me/feed", postParams, HttpMethod.POST, callback);
            RequestAsyncTask task = new RequestAsyncTask(request);
            task.execute();
        }else{
        	Mylog.v( "session null");
        }
    }
    
    private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }

	 @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
//	@Override
//	protected void onResume() {
//		sliding();// TODO Auto-generated method stub
//		super.onResume();
//	}

	void getFacebookInfo()
	{
//		MenuList_Item item = data.get(0);
//		item = new MenuList_Item(0, userId, userName, userLocation);
//		 data.add(menu_item);
//		 menulistadapter = new MenuList_Adapter(this, R.layout.menu_list_user, data);
//		 menulist.setAdapter(menulistadapter);
//		item = new MyItem();
//		item.setUserId(userId);
//		item.setEmail(userEmail);
//		item.setUsername(userName);
//		item.setUserLocation(userLocation);
//		data.add(item);
//		
//		adapter = new MyAdapter(this, R.layout.item, data);
//		list.setAdapter(adapter);
	}
	
	
	public void sliding() {
		Mylog.v("Sliding back");
		main_activity.slidingview.toggleMenu();
	}

}
