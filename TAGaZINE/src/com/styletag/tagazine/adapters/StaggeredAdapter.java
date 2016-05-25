package com.styletag.tagazine.adapters;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.fragment.Main_StyleFeed_Datail_fragment;
import com.styletag.tagazine.json.Item_ProductList;
import com.styletag.tagazine.json.JSONParser;
import com.styletag.tagazine.utils.NetManager;
import com.styletag.tagazine.views.ProgressWheel;
import com.styletag.tagazine.views.ScaleImageView;
import com.styletage.tagazine.loader.ImageLoader;

public class StaggeredAdapter extends BaseAdapter {

	private ImageLoader mLoader;
	Context context;
//	ArrayList<StaggeredItem> staggered_data;
	ArrayList<Item_ProductList> product_data;
//	ArrayList<Item_MenuList> menu_data;
	Item_ProductList product_item;
	int layout;
	
	android.support.v4.app.FragmentManager fragmentmanager = null; 
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return product_data.size();
	}
	@Override
	public Object getItem(int positon) {
		// TODO Auto-generated method stub
		return product_data.get(positon);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	
	}

	public void setData(ArrayList<Item_ProductList> product_data){
		this.product_data = product_data;
	}
	
//	public void setmenuListData(ArrayList<Item_MenuList> menu_data)
//	{
//		this.menu_data = menu_data;
//	}
	public StaggeredAdapter(Context context, int layout, ArrayList<Item_ProductList> product_data) 
	{		
		//super(context, textViewResourceId, objects);
			this.layout=layout;
			this.context=context;
			this.product_data=product_data;
			mLoader = new ImageLoader(context);
	}

//	
	
	public void callImageViewer(String productId)
	{
		
		Main_StyleFeed_Datail_fragment mainstylefeeddetailfragment;
		fragmentmanager = ((Main_Activity)context).getSupportFragmentManager();
		mainstylefeeddetailfragment = new Main_StyleFeed_Datail_fragment();
		Mylog.v("Pid : " + productId);
		mainstylefeeddetailfragment.setProductData(productId);
		android.support.v4.app.FragmentTransaction ft = fragmentmanager.beginTransaction();
		ft.replace(R.id.Main_imgFeedLayout, mainstylefeeddetailfragment);
		ft.commit();
	}
	
	static class Holder 
	{
		ScaleImageView imageView;
		Button btnlike;
		TextView likecount;
	}
	@Override
	public View getView(final int position, View cView, ViewGroup parent) {
		final Item_ProductList item = product_data.get(position);
		Holder holder;
		if (cView == null) 
		{
			Mylog.v("staggered adapter");
			LayoutInflater layoutInflator = LayoutInflater.from(context);
			cView = layoutInflator.inflate(R.layout.main_stylefeed_item, null);
			holder = new Holder();
			holder.imageView = (ScaleImageView) cView .findViewById(R.id.main_stylefeed_item_img);
			holder.btnlike = (Button)cView.findViewById(R.id.btn_main_stylefeed_like);
			holder.likecount = (TextView)cView.findViewById(R.id.tv_main_stylefeed_likecnt);
			
			cView.setTag(holder);
		}
		else
		{
			holder = (Holder)cView.getTag();
		}

//		mLoader.DisplayImage(product_data.get(position).getFull_img_path(), holder.imageView);
		holder.likecount.setText(product_data.get(position).getLike_cnt()+"");
		
		holder.btnlike.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!item.isLike())
				{
					item.setLike(true);
					item.setLike_cnt(item.getLike_cnt()+1);
					new LikeTask().execute(item.getProduct_id(), position); //Like 버튼
				}
				else
				{
					item.setLike(false);
					item.setLike_cnt(item.getLike_cnt()-1);
					new LikeTask().execute(item.getProduct_id(), position);
					
				}
				
			}
		});
		
		holder.imageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				callImageViewer(item.getProduct_id());
			}
		});
		//========================================================================
		
		/*
		 * ImageLoad Task
		 */
		int lPoint = product_data.get(position).getOrigin_img_path().lastIndexOf("/");
//		String fileName = product_data.get(position).getFull_img_path().substring(lPoint);
//		String fileName = product_data.get(position).getFull_img_path();
		String fileName = product_data.get(position).getOrigin_img_path();
		Mylog.v("File name: " +fileName);
		
		if(!fileName.equals("null"))
		{
			File sdFile = Environment.getExternalStorageDirectory();
			File file = new File(sdFile, fileName);
			
			Mylog.v("file absolute_path1: "+ file.getAbsolutePath());
			if(file.exists())
			{
//				Bitmap bitmap = null;
//				BitmapFactory.Options options = new BitmapFactory.Options();
//				options.inJustDecodeBounds = false;
//				bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
//				Mylog.v("Img width: " +options.outWidth + "  Img height: " +options.outHeight);
//				bitmap = readImageWithSampling(file.getAbsolutePath(), 400, Config.RGB_565); //RGB_565는 불투명이미지
//				Mylog.v("image size: " + holder.imageView.getWidth());
//				holder.imageView.setImageBitmap(bitmap);
				mLoader.DisplayImage(file.getAbsolutePath(), holder.imageView);
			}
			else
			{
				new DownTask().execute(product_data.get(position).getOrigin_img_path(), file, holder.imageView);
			}
		}else{
			Mylog.v("File name null: " +fileName);
		}
		
		return cView;
	}

	public static Bitmap readImageWithSampling(String imagePath, int targetWidth, Bitmap.Config bmConfig) {
		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		//이미지를 메모리에 올려놓지 않고 해상도만 알아냄
		bmOptions.inJustDecodeBounds = true; 
		BitmapFactory.decodeFile(imagePath, bmOptions);

		Mylog.v("sss path : " + imagePath);
		int photoWidth  = bmOptions.outWidth;
		//int photoHeight = bmOptions.outHeight;

		// Determine how much to scale down the image
		
		//이미지 축소비율 설정
		//int scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight);

		int scaleFactor = photoWidth / targetWidth;
//		if(scaleFactor>0){
//			scaleFactor--;
//		}
			
		Mylog.v("====scaleFactor : "+scaleFactor);
		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inPreferredConfig = bmConfig;
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;
		bmOptions.inDither = false;

		Bitmap orgImage = BitmapFactory.decodeFile(imagePath, bmOptions);

		Mylog.v("orgImage : " + orgImage);
		return orgImage;
	}
	
	public String getUserId()
	{
		SharedPreferences sp1 = context.getSharedPreferences("tagazine", 0);
		
		String userid = sp1.getString("userid", "String");
		Mylog.v("userid: "+ userid);
		return userid;
	}
	String productId = "";
	public void getLike(String productId)
	{
		this.productId = productId;
		new LikeTask().execute();
	}
	class LikeTask extends AsyncTask<Object, Void, Object>
	{
		
		Object obj;
		String likeCount;
		int position;
		String productId = "";
		
		@Override
		protected Object doInBackground(Object... params) {
			HttpClient client = null;
			HttpGet request = null;
			HttpResponse response = null;
			InputStream is = null;
			position = Integer.parseInt(params[1].toString());
			BufferedReader br = null;
			StringBuilder sb = null;
			int code = 0;
			Mylog.v("Like Task start");
			String productId = params[0].toString();
			List<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
		
			params1.add(new BasicNameValuePair("user_id", getUserId())); 
			params1.add(new BasicNameValuePair("product_id",productId));
			UrlEncodedFormEntity entity = null;
			try {
				String url = "http://tagazine.nkr1545.cloulu.com/product/";
				url += productId;
				url += "/like/";
				url += getUserId();
				
//				url = url + ":" + URLEncodedUtils.format( params1, "UTF-8" ) ; 


				entity = new UrlEncodedFormEntity(params1);
				client = NetManager.getHttpClient();
				request = NetManager.getGet(url); 
				response  = client.execute(request);
				code = response.getStatusLine().getStatusCode();
				Mylog.v("Like code1: " +code);
				switch(code)
				{
				case 200:
					br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					sb = new StringBuilder();
					String temp = "";
					while((temp = br.readLine()) != null)
					{
						sb.append(temp).append("\n");
					}
					obj = JSONParser.likeParse(sb.toString());
					Mylog.v("obj: " +sb.toString());
					break;
				}
			} catch (Exception e) {
				Mylog.v("liketask error : " +e);
			}
			return obj;
		}

		@Override
		protected void onPostExecute(Object obj) {
			if(obj instanceof String)
			{
				Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
			}
			else
			{
//				Item_Like item = (Item_Like)obj;
				product_data.get(position).setLike_cnt((product_data.get(position).getLike_cnt()));
				notifyDataSetChanged();
			}
			super.onPostExecute(obj);
		}		
	}
	
	class DownTask extends AsyncTask<Object, Void, Bitmap>
	{
		ImageView img;
		File f = null;
		@Override
		protected Bitmap doInBackground(Object... params) {
			Bitmap bitmap = null;
			img = (ImageView)params[2];
			f = (File)params[1];
			
			HttpClient client = null;
			HttpGet request = null;
			HttpResponse response = null;
			InputStream is = null;
			int code = 0;
			int len = 0;
			byte[] imgArr = null;
			
			try {
				client = NetManager.getHttpClient();
				request = NetManager.getGet("http://tagazine.nkr1545.cloulu.com/uploads/origin_img/"+params[0].toString());
				Mylog.v("url: http://tagazine.nkr1545.cloulu.com/uploads/origin_img/"+params[0].toString());
				response  = client.execute(request);
				code = response.getStatusLine().getStatusCode();
				Mylog.v("code"  + code) ;
				switch(code)
				{
				case 200:
					is = response.getEntity().getContent();
					len = (int)response.getEntity().getContentLength();
					if(len != -1){
						imgArr = new byte[len];
						DataInputStream dis = new DataInputStream(is);
						dis.readFully(imgArr, 0, len);
						Mylog.v("ImgArr  "  + imgArr) ;
						
					}else{
						int inData = 0;
						ByteArrayOutputStream baos = new ByteArrayOutputStream();

						while((inData=is.read()) != -1){
							baos.write(inData);
						}
						imgArr = baos.toByteArray();
					}
					bitmap = BitmapFactory.decodeByteArray(imgArr, 0, imgArr.length);
					bitmap.compress(CompressFormat.PNG, 100,
							new FileOutputStream( (File) params[1]));
					Mylog.v("bitmap"  + bitmap) ;
					
					Mylog.v("loading success");
					//					is.close();
					break;
				}
			} catch (IOException e) {
				Mylog.v("Img Loading error: "  +e);
			} finally {
				if(is != null)
				{
					try {
						is.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
			
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			
			if(result != null)
			{
//				((Main_Activity)context).progresswheel.stopSpinning();
				mLoader.DisplayImage(f.getAbsolutePath(), img);
//				img.setImageBitmap(result);
			}
			else
			{
				
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
//			((Main_Activity)context).progresswheel.spin();
			super.onPreExecute();
		}
		
	}

	

}
