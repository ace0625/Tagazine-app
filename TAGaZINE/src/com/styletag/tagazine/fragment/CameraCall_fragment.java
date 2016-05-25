package com.styletag.tagazine.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.styletag.tagazine.activity.ImageCall_Activity;

import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CameraCall_fragment extends android.support.v4.app.Fragment {
	
	Activity activity;
	private static final int REQ_PICTURE = 0; 
	private static final int REQ_GALLERY = 1;
	Uri photo_uri;
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btn_call_camera:
				takePic();
				break;
			case R.id.btn_call_gallery:
				callGallery();
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.cameracall_fragment, null);
		v.findViewById(R.id.btn_call_camera).setOnClickListener(bHandler);
		v.findViewById(R.id.btn_call_gallery).setOnClickListener(bHandler);
		return v;
	}

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
    		SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
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
//    		getActivity().setResult(REQ_PICTURE, intent_Camera);
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
//		getActivity().setResult(REQ_GALLERY, intent_getPicture);
		startActivityForResult(intent_getPicture, REQ_GALLERY);
	}
	Bitmap bitmap = null;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		switch (requestCode) {
		case REQ_PICTURE: // 카메라 요청을 끝내고 리턴했을때.
			if(resultCode == Activity.RESULT_OK){ // 카메라로 사진을 찍고 저장했을때
				try{
					if(bitmap != null && !bitmap.isRecycled())
					{ 
						bitmap.recycle(); 
						bitmap = null; 
					}
			
//					bitmap = (Bitmap)data.getExtras().get(MediaStore.EXTRA_OUTPUT);
					Intent camera = new Intent(getActivity(), ImageCall_Activity.class);
					camera.putExtra("camera", photo_uri);
//					startActivity(camera);
					getActivity().startActivityForResult(camera, requestCode);
				
				}catch (Exception e) {
					String physical_path = photo_uri.toString().substring(7).replaceAll("%3B", ";").replaceAll("%20"," ");
					if(bitmap != null && !bitmap.isRecycled()){ bitmap.recycle(); bitmap = null; }
					bitmap = BitmapFactory.decodeFile(physical_path);

					Mylog.v("REQ_PICTURE/ physical_path : " + physical_path);
					Mylog.v("error" +e);
					Intent camera = new Intent(activity, ImageCall_Activity.class);
					camera.putExtra("camera", bitmap);
					startActivity(camera);
					photo_uri = null;
				}
			}else{ // 취소버튼을 눌렀을때.
				Mylog.v("취소 하셨습니다.");
			}
			break;
		case REQ_GALLERY: // 이미지 선택을 끝내고 리턴했을때.
			if(resultCode == Activity.RESULT_OK){ // 정상적으로 이미지를 선택했을경우.
				try{ 
					Uri select_uri = data.getData();
					Mylog.v("REQ_GALLERY/ select_uri : " + select_uri.toString());
					
					Cursor c = getActivity().getContentResolver().query(Uri.parse(select_uri.toString()), null,null,null,null);
					c.moveToNext();
					
					// 비트맵 이미지를 업로드 또는 가공할경우 아래의 변수를 사용하도록 한다.
//					String physical_path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA)).toString().substring(5).replaceAll("%3B", ";").replaceAll("%20"," ");
//					Log.v(TAG,"REQ_GALLERY/ physical_path : " + physical_path);
//						bitmap = BitmapFactory.decodeFile(physical_path);
						
					if(bitmap != null && !bitmap.isRecycled())
					{ 
						bitmap.recycle(); 
						bitmap = null; 
					}
					Intent gallery = new Intent(getActivity(), ImageCall_Activity.class);
					gallery.putExtra("gallery", select_uri);
//					startActivity(gallery);
					getActivity().startActivityForResult(gallery, requestCode);
					
				}catch (Exception e) {
					Mylog.v("Exception\n" + e.getMessage());
				}
			}else{ // 취소버튼을 눌렀을때.
				Mylog.v("취소 하셨습니다.");
			}
			break;
		default:
			Mylog.v("Error: onActivityResult\n여기는 default 입니다.");
			break;
	}	
//			super.onActivityResult(requestCode, resultCode, data);
	}
	
	
}
