package com.styletag.tagazine.activity;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.styletag.tagazine.adapters.StaggeredAdapter;
import com.styletag.tagazine.adapters.TagItem;
import com.styletag.tagazine.fragment.Upload_CategorySelection_fragment;
import com.styletag.tagazine.utils.NetManager;
import com.styletag.tagazine.views.UploadTagView;

public class ImageCall_Activity extends FragmentActivity {

	FragmentManager fragmentmanager;
	Upload_CategorySelection_fragment categoryfragment;
	private static final int REQ_PICTURE = 0; 
	private static final int REQ_GALLERY = 1;
	ImageView img;
	
	Uri camera = null;
	Uri gallery = null;
	UploadTagView tagview;
	
	ArrayList<TagItem> tagData;
	TagItem item;
	StaggeredAdapter main_adapter;
	ProgressDialog pDialog;
	Main_Activity mainactivity;
	FrameLayout upLoadImg = null;
	
	String origin_img;
	
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btn_upload_home:
				Intent intent = new Intent(ImageCall_Activity.this, Main_Activity.class);
				startActivity(intent);
				break;
			case R.id.btn_upload_finalok:
				Mylog.v("upload_ok");
				tagview.state1 = 0; // hide the tag table
				tagview.tagshow = 1;// hide the tag img
				//태그 이미지 캡쳐
				upLoadImg.setDrawingCacheEnabled(true);
				Bitmap bitmap = upLoadImg.getDrawingCache();
				ByteArrayOutputStream boas = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.PNG, 100, boas);
				arr = boas.toByteArray();
				
				uploadComplete();
				break;
			}
			
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
	public String getNickName()
	{
		String email = getIntent().getStringExtra("email");
		Mylog.v("email: " +email);
		int idx = email.indexOf("@");
		email = email.substring(0, idx);
		
		return email;
	}
	
	byte[] arr = null;
	class upLoadImageThread extends Thread
	{

		public void run()
		{	
			String url = "http://tagazine.nkr1545.cloulu.com/upload";
			
			HttpClient client = NetManager.getHttpClient();
			HttpPost post = NetManager.getPost(url);
			HttpResponse response = null;
			BufferedReader br = null;
			StringBuffer sb = null;
			String line = "";
			
			Date currentDate = new Date();   
			SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
			String dateString = format.format(currentDate);
			Mylog.v("current time: " +dateString);
			  
			String userId = getUserId();
//			String nickname = getNickName();
			
			try{
				Mylog.v("uploading process");
//				
				FileInputStream fis = new FileInputStream (new File(origin_img));
				byte[] byteData = IOUtils.toByteArray(fis);
				InputStreamBody origin_file = 
						new InputStreamBody(new ByteArrayInputStream(byteData), userId + dateString+".PNG");
				
				InputStreamBody upFile = 
						new InputStreamBody(new ByteArrayInputStream(arr), "f_"+userId + dateString+".PNG");
//				
				
				Mylog.v("uploading process11");
				StringBody userId1 = new StringBody(userId, Charset.forName("UTF-8"));
				
				Mylog.v("uploading process2");
				StringBody productId = new StringBody(userId + dateString, Charset.forName("UTF-8"));				
				StringBody category = new StringBody(categoryfragment.category,  Charset.forName("UTF-8"));
				StringBody memo = new StringBody(categoryfragment.upload_styletip.getText().toString(), Charset.forName("UTF-8"));
				
				Mylog.v("add part");
		
				MultipartEntity entity = new MultipartEntity();
				entity.addPart("full_img", upFile);
				entity.addPart("origin_img", origin_file);
				entity.addPart("user_id", userId1);
				entity.addPart("product_id", productId);
				entity.addPart("category", category);
				entity.addPart("memo", memo);
				Mylog.v("add part2");
				
				tagData = tagview.tlist;
				if(tagData.size() != 0)
				{
					entity.addPart("tag_info",  new StringBody("true",Charset.forName("UTF-8") ));
					for(TagItem item : tagData)
					{
						Mylog.v("add part3"+ item.getBrand());
						entity.addPart("brand",  new StringBody(item.getBrand(),Charset.forName("UTF-8") ));
						entity.addPart("title",  new StringBody(item.getProduct(),Charset.forName("UTF-8") ));
						entity.addPart("price",  new StringBody(item.getPrice(),Charset.forName("UTF-8") ));
						entity.addPart("place",  new StringBody(item.getAddress(),Charset.forName("UTF-8") ));
						entity.addPart("kind",  new StringBody(item.getType()+"",Charset.forName("UTF-8") ));
						
					}
					Mylog.v("add part5555");
				}
				post.setEntity(entity);
				response = client.execute(post);
				int code = response.getStatusLine().getStatusCode();
				Mylog.v("process code : " + code);
				
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
					handler.sendEmptyMessage(0);
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
	
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			if(pDialog != null)
			{
				pDialog.cancel();
				ImageCall_Activity.this.finish();
			}
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
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.imagecallfortag);
	    
	    img = (ImageView)findViewById(R.id.tagimage);
		findViewById(R.id.btn_upload_finalok).setOnClickListener(bHandler);
		findViewById(R.id.btn_upload_home).setOnClickListener(bHandler);
		upLoadImg = (FrameLayout)findViewById(R.id.upload_sublayout);
		
		tagview = (UploadTagView)findViewById(R.id.uploadTagView1);
		tagview.requestFocus();
		tagview.requestFocusFromTouch();
		
		camera = (Uri) getIntent().getExtras().get("camera");
		gallery = (Uri) getIntent().getExtras().get("gallery");
	
		//카메라
		if(camera != null)
		{
			ExifInterface exif1 = null;
		try {
			 exif1 = new ExifInterface(camera.getPath());
			 Mylog.v("camera path: " +camera.getPath());
			int rotation = exif1.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL); 
			
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), camera);
			BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 4;
	        Mylog.v("bitmap "+bitmap);
//	        rotateBmp(bitmap, rotation);
			img.setImageBitmap(bitmap);
//			origin_img = convertMediaUriToPath(camera); //convert to path
			origin_img = camera.getPath();
//		img.setImageURI(camera);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
 
		//갤러리
		else if(gallery != null)
		{
			ExifInterface exif1 = null;
			try {
				
				exif1 = new ExifInterface(gallery.getPath());
				int rotation = exif1.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL); 
				Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), gallery);
				BitmapFactory.Options options1 = new BitmapFactory.Options();
		        options1.inSampleSize = 4;
		        rotateBmp(bitmap1, rotation);
		        img.setImageBitmap(bitmap1);
		        origin_img = convertMediaUriToPath(gallery); //convert to path
//		        img.setImageURI(gallery);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 

		Mylog.v("origin_img: " +origin_img);
		Mylog.v("camera: " + camera +" gallery: " + gallery);
		
	}
	
	public Bitmap rotateBmp(Bitmap bmp,int rotation){
		Bitmap temp = bmp;
		Bitmap rotate;
		if(ExifInterface.ORIENTATION_ROTATE_90 == rotation)
		{
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			rotate = Bitmap.createBitmap(temp, 0, 0,
			temp.getWidth(), temp.getHeight(), matrix, true);
			Mylog.v("[WriteStoryActivity] rotateBmp: SUCEESS!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		else
		{
			Mylog.v ("[WriteStoryActivity ]rotateBmp: fail");
			return temp;
		}
		return rotate;
		}
	
//	void rotateImage(String uri)
//	{
//		Matrix matrix = new Matrix();
//		ExifInterface exif = new ExifInterface(uri);
//		int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//		int rotationInDegrees = exifToDegrees(rotation);
//		if (rotation != 0f) {matrix.preRotate(rotationInDegrees)};
//		Bitmap.createBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter);
//		Bitmap adjustedBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, width, height, matrix, true);
//	}
//	
//	private static int exifToDegrees(int exifOrientation)
//	{
//		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
//		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
//		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
//		return 0;    
//	}
	protected String convertMediaUriToPath(Uri uri) {
	    String [] proj={MediaStore.Images.Media.DATA};
	    Cursor cursor = getContentResolver().query(uri, proj,  null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    String path = cursor.getString(column_index); 
	    cursor.close();
	    return path;
	}

	int state = 0;
	void uploadComplete()
	{
		switch(state)
		{
		case 0:
			fragmentmanager = getSupportFragmentManager();
			categoryfragment = new Upload_CategorySelection_fragment();
			FragmentTransaction ft = fragmentmanager.beginTransaction();
			ft.add(R.id.upload_sublayout, categoryfragment);
			ft.commit();
			state = 1;
			break;
		case 1:
			pDialog = ProgressDialog.show(ImageCall_Activity.this, "", "Uploading...");
			new upLoadImageThread().start();
//			ImageCall_Activity.this.finish();
			break;
		}
		 // 임시
	}
	
//	void getFilePath()
//	{
//		String fName = "dog.png";
//		String path = "";
//		File sdPath = Environment.getExternalStorageDirectory();
//		File f = new File(sdPath, fName);
//		if(f.exists())
//		{
//			path = f.getAbsolutePath();
////			etFile.setText(path);
//		}
//	}
	
	
	
	
}