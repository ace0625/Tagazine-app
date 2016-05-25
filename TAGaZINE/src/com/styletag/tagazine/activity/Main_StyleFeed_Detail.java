package com.styletag.tagazine.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class Main_StyleFeed_Detail extends Activity {

	ImageView img;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.main_stylefeed_detail);
	
	    img = (ImageView)findViewById(R.id.detail_bar);
	    Intent intent = getIntent();
	    String str = intent.getStringExtra("filename");
	
	    Bitmap bitmap=BitmapFactory.decodeFile(str);
	    img.setImageBitmap(bitmap);
	    
	}
}
