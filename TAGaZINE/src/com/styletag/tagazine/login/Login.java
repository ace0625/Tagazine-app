package com.styletag.tagazine.login;

import java.util.Arrays;

import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.styletag.tagazine.activity.R;


public class Login extends Activity {

	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
//	TextView welcom;
	Button btLogin,btCreateAc;
	RelativeLayout rlFacebook;
	LoginButton authButton;
	Session session;
	
	
//	View.OnClickListener bHandler = new View.OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			//login부분 로직구현해햐함!!.
//			case R.id.btn_facebook_login:
//				break;
//				
//			
//			}
//		}
//	};
	public void autoLogInFB(){
		authButton.performClick();
	}
	/*private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
//		welcom = (TextView)findViewById(R.id.tvfb);
		
		
		
		authButton = (LoginButton)findViewById(R.id.btn_facebook_login);
//		authButton.setVisibility(View.GONE);
		authButton.setOnErrorListener(new OnErrorListener() {
			
			@Override
			public void onError(FacebookException error) {
				Toast.makeText(Login.this, "로그인 에러입니다.", Toast.LENGTH_SHORT).show();
			}
		});
		authButton.setReadPermissions(Arrays.asList("basic_info","email"));
		authButton.setSessionStatusCallback(new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state, Exception exception) {
				if(session.isOpened()){
					Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

						@Override
						public void onCompleted(GraphUser user, Response response) {
							
							if (user != null) { 
								setLoginvalues(user);
								
							}
							
							
						}
					});
				}
			}
		});
		
	}
	
	
	public void setLoginvalues(GraphUser user){
		SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);

		SharedPreferences.Editor editor = login.edit();
		editor.putBoolean("checkLogin", true);
		editor.putString("name", user.asMap().get("name").toString());
		editor.putString("email", user.asMap().get("email").toString());
		editor.putString("userId", user.getId());
		editor.commit();
		
	}
	public boolean checkLogin(){
		SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
		boolean a = login.getBoolean("checkLogin",false );
		
		
		
		return login.getBoolean("checkLogin",false );
		
	}

	/*public void fbLogIn(){
		session = Session.getActiveSession();
		if (session != null) {

			// Check for publish permissions    
			List<String> permissions = session.getPermissions();
			if (!isSubsetOf(PERMISSIONS, permissions)) {
				pendingPublishReauthorization = true;
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, PERMISSIONS);
				session.requestNewPublishPermissions(newPermissionsRequest);
				return;
			}
		}
		
	}*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	

}
