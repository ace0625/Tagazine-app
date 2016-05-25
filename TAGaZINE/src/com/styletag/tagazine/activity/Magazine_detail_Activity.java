package com.styletag.tagazine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Magazine_detail_Activity extends Activity {

	LinearLayout top, bottom;
	ImageView magazine_detail_img;
	TextView name, likecnt, pagenum;
	
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.magazine_detail_img:
				layoutshow(); //상하 버튼 보여주기
				break;
			case R.id.btn_magazine_detail_home: //홈으로가기
				break;
			case R.id.btn_magazine_detail_category: //카테고리 분류
				break;
			case R.id.btn_magazine_detail_info: //태그/정보 보여주기
				break;
			}
			
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.magazine_detail);
	    findViewById(R.id.btn_magazine_detail_home).setOnClickListener(bHandler);
	    findViewById(R.id.btn_magazine_detail_category).setOnClickListener(bHandler);
	    findViewById(R.id.btn_magazine_detail_info).setOnClickListener(bHandler);
	    
	    magazine_detail_img = (ImageView)findViewById(R.id.magazine_detail_img);
	    magazine_detail_img.setOnClickListener(bHandler);
	   
	    name = (TextView)findViewById(R.id.tv_magazine_detail_name);
	    likecnt = (TextView)findViewById(R.id.tv_magazine_detail_count_tv);
	    pagenum = (TextView)findViewById(R.id.tv_magazine_detail_page);
	    
	    top = (LinearLayout)findViewById(R.id.magazine_detail_top_layout);
	    bottom = (LinearLayout)findViewById(R.id.magazine_detail_bottom_layout);
	}
	
	/*
	 * 매거진 상하 버튼/정보 보여주기
	 */
	int state = 0;
	void layoutshow()
	{
		if(state == 0)
		{
			top.setVisibility(View.VISIBLE);
			bottom.setVisibility(View.VISIBLE);
			state = 1;
		}
		else if (state == 1)
		{
			top.setVisibility(View.INVISIBLE);
			bottom.setVisibility(View.INVISIBLE);
			state = 0;
		}
	}

}
