package com.styletag.tagazine.views;


import java.util.ArrayList;

import com.styletag.tagazine.activity.ImageCall_Activity;
import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.TagItem;
import com.styletag.tagazine.fragment.UploadInfo_Dialog_fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class UploadTagView extends View {

	public ArrayList<TagItem> tlist = new ArrayList<TagItem>();
	Context context = null;
	android.support.v4.app.FragmentManager fm;

	int x = 0;
	int y = 0;
	int ftagX; //position x of tag
	int ftagY; //position y of tag
	String str = "";
	int state = 0; //current state
	public int type;
	int[]iconImgRes = {R.drawable.bt_tag_icon_01, R.drawable.bt_tag_icon_02, R.drawable.bt_tag_icon_03, R.drawable.bt_tag_icon_04, R.drawable.bt_tag_icon_05
			,R.drawable.bt_tag_icon_06, R.drawable.bt_tag_icon_07, R.drawable.bt_tag_icon_08, R.drawable.bt_tag_icon_09, R.drawable.bt_tag_icon_10
			,R.drawable.bt_tag_icon_11, R.drawable.bt_tag_icon_12, R.drawable.bt_tag_icon_13, R.drawable.bt_tag_icon_14, R.drawable.bt_tag_icon_15};



	private static final int MODE_BASIC = 0;  //initial state
	private static final int MODE_POPUP = 10; //when touched
	private static final int MODE_DIALOG = 20;
	//	private static final int MODE_BASIC = 0;


	TagItem item;
	void init(Context context)
	{
		setBackgroundResource(Color.TRANSPARENT);	
		this.context  = context;
	}
	/*
	 * 태그 구현
	 */
	public void onDraw(Canvas canvas)
	{
		
		switch(state)
		{
		case MODE_BASIC :
			break;
		case MODE_POPUP :
			switch(state1)
			{
			case 0:
				drawAllTag(canvas);
				break;
			case 1:
				drawAllTag(canvas);
				if(tlist.size() <= 5)
				{
					popUP(canvas);					
				}
				break;

			}
			//			changeTag(canvas, tagX, tagY);
			//			myview.setDrawingCacheEnabled(true);
			//			setDrawingCacheEnabled(true);
			break;
		case MODE_DIALOG:
			
			break;
		}
	}

	//	int pre_x;
	//	int pre_y;
	public int state1 = 0;//0은 태그창 안보이기 1은 보이기
	public int tagshow = 0; //0은 보이기 1은 안보이기
	int tempX = 0;
	int tempY = 0;

	public boolean onTouchEvent(MotionEvent event) {
		x = (int)event.getX();
		y = (int)event.getY();
		switch(state){
		case MODE_BASIC :
			switch(event.getAction()){
			case  MotionEvent.ACTION_DOWN:
				Mylog.v("x: " +x +"y: " +y);
				ftagX = x;
				ftagY = y;
				//				state1 = 0;
				break;
			case  MotionEvent.ACTION_MOVE:
				//				state1 = 0;
				break;
			case  MotionEvent.ACTION_UP:
				//				state1 = 1;
				break;
			}
			state = MODE_POPUP;
			break;
		case MODE_POPUP :
			Mylog.v("down11111");
			//			Rect rect1 = new Rect(pre_x + 10, pre_y + 100, pre_x + 10 + 100 , pre_y + 100+60); //Area of items 
			//			boolean flag = rect1.contains(x, y); // 정해진 범위에서만 터치가능 

//			Rect rect2 = new Rect(40, 600, 680, 800);
			Rect rect2 = new Rect(35, 550, 695, 880);
			boolean flag = rect2.contains(x, y);
			//			if(flag){
			//				int checkX = x - pre_x + 10; 
			//				int checkY = y - pre_y + 100; 
			//				int posX = checkX/30; //30       은 넓이
			//				int posY  = checkY/30; //30       은 넓이
			if(flag && (state1 == 1))
			{
				changeTag(x, y);
				state1 = 0;
				Mylog.v("flag");
			}
			else
			{
				Mylog.v("down22222");
				switch(event.getAction()){
				case  MotionEvent.ACTION_DOWN:
					Mylog.v("down");
					item = new TagItem();
					state1 = 0;
					break;
				case  MotionEvent.ACTION_MOVE:
					state1 = 0;
					break;
				case  MotionEvent.ACTION_UP:
					tempX = x;
					tempY = y;
					state1 = 1;
					break;
				}	
			}
			//			}
			//			else
			//			{
			//				init();
			//			}
			//			state = MODE_DIALOG;
			break;
		case MODE_DIALOG:
			break;
		}
		invalidate();

		return true;
	}
	void tagDialog()
	{
		UploadInfo_Dialog_fragment uploadfragment;
		Mylog.v("dialog:  " + context);
		fm = ((ImageCall_Activity)context).getSupportFragmentManager();
		uploadfragment = new UploadInfo_Dialog_fragment();
		uploadfragment.setTagItem(item);
		uploadfragment.setUpLoadTagView(this);
		uploadfragment.show(fm, "dialog");	
	}
	
	public void setItemOk()
	{
		tlist.add(item);
		invalidate();
		
	}
	void changeTag(int tagX, int tagY)
	{
		
//		int idxX = (tagX - 40) / 80;
//		int idxY = (tagY - 400) / 70;
		int idxX = (tagX - 35) / 132;
		int idxY = (tagY - 440) / 110;
		type = (idxX-1)*5 + idxY;

		if(type <0)
		{
			type = 0;
		}
		if(type > 14)
		{
			type = 14;
		}
		Mylog.v("change tag");
		tagimg = BitmapFactory.decodeResource(getResources(), iconImgRes[type]);
		item.setType(type);
		item.setPosX(tempX);
		item.setPosY(tempY);
		tagDialog();
//		tlist.add(item);

		//		if((x>=40 && x<=95) && (y>=315 && y<=370))
		//		{
		//			Mylog.v("change tag");
		//			tagimg = BitmapFactory.decodeResource(getResources(), iconImgRes[0]);
		//			item.setType(0);
		//			item.setPosX(tagX);
		//			item.setPosY(tagY);
		//			tlist.add(item);
		////		    canvas.drawBitmap(tagimg, tagX, tagY, null);
		//		}

	}
	/*
	 * 터치한 부분 태그 나타내기
	 */
	Bitmap tagimg;
	void drawAllTag(Canvas canvas){

		drawTagList(canvas);
		if(tlist.size()<=5)
		{
			if(tagshow == 0)
			{
				drawTag(canvas);											
			}
			else if(tagshow == 0)
			{
				
			}
		}
	}
	void drawTag(Canvas canvas)
	{

		tagimg = BitmapFactory.decodeResource(getResources(), R.drawable.icon_tag);		
		canvas.drawBitmap(tagimg,x, y, null);			

	}
	void drawTagList(Canvas canvas)
	{
		for(TagItem item : tlist)
		{
			tagimg = BitmapFactory.decodeResource(getResources(),iconImgRes[item.getType()] );		
			canvas.drawBitmap(tagimg, item.getPosX(), item.getPosY(), null);			
		}
	}
	void popUP(Canvas canvas)
	{	int imgX = 35;
		int imgY = 550;
		
	//		drawTag(canvas);
	//		for(int i=0; i<3; i++ )
	//		{	
	//			imgX = 100;
	//			for(int j=0; j<7; j++)
	//			{
	//				Bitmap bitmap  = BitmapFactory.decodeResource(getResources(), iconImgRes[i][j]);
	//				if(i==0)
	//				{
	//					canvas.drawBitmap(bitmap, imgX, 700, null);
	//					imgX += 90;
	//				}
	//				else if(i==1)
	//				{
	//					canvas.drawBitmap(bitmap, imgX, 800, null);
	//					imgX +=90;
	//				}
	//				bitmap.recycle();
	//			}
	//		}
	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_icon_clothes_all);
	canvas.drawBitmap(bitmap, imgX, imgY, null);

	}


	public UploadTagView(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public UploadTagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		// TODO Auto-generated constructor stub
	}

	public UploadTagView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		// TODO Auto-generated constructor stub
	}


}
