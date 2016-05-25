package com.styletag.tagazine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.ProgressBar;

public class LoadingPage_Activity extends Activity {

	ProgressBar progressbar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.loadingpage);
	    progressbar = (ProgressBar)findViewById(R.id.progressBar);
	    
	    handler.sendEmptyMessage(0);
	  	}
	  	  Handler handler = new Handler()
	  	    {
	  	    		
	  			@Override
	  			public void handleMessage(Message msg) {
	  				
	  				super.handleMessage(msg);
	  				if(progressbar.getProgress()>=100){
	  					Intent intent = new Intent(LoadingPage_Activity.this, Main_Activity.class);
	  					startActivity(intent);
	  					finish();
	  					Mylog.v("loading");
	  				}
	  				else
	  				{
	  					progressbar.incrementProgressBy(5);
	  					setProgress(progressbar.getProgress()*100);
	  					handler.sendEmptyMessageDelayed(0,100);
	  				}
	  			}
	  	    	
	  	    };
	  	    
	  	    
	  	  @Override
	  	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  		if(keyCode == KeyEvent.KEYCODE_BACK)
	  		{
	  			return false;
	  		}
	  		return super.onKeyDown(keyCode, event);
	  	}

  public void onBackPressed(){}
	    
//	    Handler handler = new Handler();
//	    handler.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				finish();
//				
//			}
//		}, 3000);
//	   
//	}

}
