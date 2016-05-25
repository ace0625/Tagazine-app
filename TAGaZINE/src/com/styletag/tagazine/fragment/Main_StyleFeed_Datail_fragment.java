package com.styletag.tagazine.fragment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.widget.ProfilePictureView;
import com.styletag.tagazine.activity.Main_Activity;
import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.Detail_Comment_Adapter;
import com.styletag.tagazine.adapters.Detail_Comment_item;
import com.styletag.tagazine.adapters.Detail_Taginfo_Adapter;
import com.styletag.tagazine.adapters.Detail_Taginfo_item;
import com.styletag.tagazine.adapters.StaggeredAdapter;
import com.styletag.tagazine.adapters.TagItem;
import com.styletag.tagazine.json.Comment;
import com.styletag.tagazine.json.Item_Detail;
import com.styletag.tagazine.json.Item_ProductList;
import com.styletag.tagazine.json.JSONParser;
import com.styletag.tagazine.utils.ListViewUtil;
import com.styletag.tagazine.utils.NetManager;

public class Main_StyleFeed_Datail_fragment extends android.support.v4.app.Fragment {
	Activity activity;
	ImageView img;
	LinearLayout tagButton_layout;
	GridView tag_gridview;//
	ProfilePictureView profileimg;
	TextView userName, uploadDate;
	TextView styleTip;
	Button btn_like_up;
	TextView like_cnt;
	TextView view_cnt;
	Button btn_comment;
	ListView comment_Listview;//
	Button btn_like2;
	EditText et_comment;
	Button btn_write;
	ScrollView scrollview;
	ProgressDialog pDialog;
	Item_Detail detail_item;
	Item_ProductList product_item;
	ArrayList<Item_ProductList> product_data;
	
	ArrayList<Item_Detail> detail_data = new ArrayList<Item_Detail>();
	ArrayList<TagItem> tagitem = new ArrayList<TagItem>();
	ArrayList<Comment> commentitem = new ArrayList<Comment> ();
	///////////////////////////////////////////////
	Detail_Taginfo_Adapter taginfo_adapter;
	ArrayList<Detail_Taginfo_item> taginfo_data = new ArrayList<Detail_Taginfo_item>();
	
	Detail_Comment_Adapter comment_adapter;
	ArrayList<Detail_Comment_item> comment_data = new ArrayList<Detail_Comment_item>();

	Main_Activity maina;
	StaggeredAdapter ad;
	View.OnClickListener bHandler = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.detail_btn_like1: //like 윗부분
//				new LikeTask().execute(productId, position);
				ad.getLike(productId);
				break;
			case R.id.detail_btn_comment1: //comment 달기
//				et_comment.requestFocus();
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et_comment, InputMethodManager.SHOW_IMPLICIT);
				break;
			case R.id.detail_btn_like2: //like 아래부분 
				break;
			case R.id.btn_detail_write: //comment 완료
				new CommentUploadThread().start();
				break;
			}
			
		}
	};
	
	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
		
	}
	String productId = "";
	public void setProductData(String productId)
	{
		Mylog.v("set pid : " + productId);
		this.productId = productId;
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Mylog.v("Main stylefeed detail fragment");
		View v = null;
		v = inflater.inflate(R.layout.main_stylefeed_detail, null);
		
	
		scrollview = (ScrollView)v.findViewById(R.id.detail_scrollview);
		img = (ImageView)v.findViewById(R.id.img_mainStylefeed_detail);
		tagButton_layout = (LinearLayout)v.findViewById(R.id.detail_tagButton_layout);
		tag_gridview = (GridView)v.findViewById(R.id.detail_tag_gridview);
		profileimg = (ProfilePictureView)v.findViewById(R.id.friendProfilePicture_detail);
		userName = (TextView)v.findViewById(R.id.detail_userName);
		uploadDate = (TextView)v.findViewById(R.id.detail_date);
		styleTip = (TextView)v.findViewById(R.id.detail_styleTip_tv);
		btn_like_up = (Button)v.findViewById(R.id.detail_btn_like1);
		btn_like_up.setOnClickListener(bHandler);
		like_cnt  = (TextView)v.findViewById(R.id.detail_likecount_tv);
		view_cnt = (TextView)v.findViewById(R.id.detail_viewcount_tv);
		btn_comment = (Button)v.findViewById(R.id.detail_btn_comment1);
		btn_comment.setOnClickListener(bHandler);
		
		comment_Listview = (ListView)v.findViewById(R.id.detail_comment_listview);
		ListViewUtil.setListViewHeightBasedOnChildren(comment_Listview);
//		LayoutInflater li = getActivity().getLayoutInflater();
//		 
//		LinearLayout linearfooter = (LinearLayout) li.inflate(R.layout.main_stylefeed_detail_listview, null);
//		LinearLayout linearheader = (LinearLayout) li.inflate(R.layout.main_stylefeed_detail, null);
//		comment_Listview.addFooterView(linearfooter);
//		comment_Listview.addHeaderView(linearheader);
	


//         comment_Listview.setOnTouchListener(new View.OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				scrollview.requestDisallowInterceptTouchEvent(true);
//				return false;
//			}
//		});
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				scrollview.requestDisallowInterceptTouchEvent(true);
//				return false;
//			}
//		});
		
		btn_like2 = (Button)v.findViewById(R.id.detail_btn_like2);
		btn_like2.setOnClickListener(bHandler);
		et_comment = (EditText)v.findViewById(R.id.et_detail_comment);
		
		btn_write = (Button)v.findViewById(R.id.btn_detail_write);
		btn_write.setOnClickListener(bHandler);
		
//		
		taginfo_adapter = new Detail_Taginfo_Adapter(activity, R.layout.main_stylefeed_detail_taginfo, tagitem );
		tag_gridview.setAdapter(taginfo_adapter);
		comment_adapter = new Detail_Comment_Adapter(activity, R.layout.main_stylefeed_detail_comment, commentitem);
		comment_Listview.setAdapter(comment_adapter);
		new DetailTask().execute(); // parsing start
//	

		
		return v;
	}
	
	String path="";
	class DetailTask extends AsyncTask<Object, Void, Object>
	{
	
		Object obj;
		@Override
		protected Object doInBackground(Object... params) {
			Mylog.v("DetailThread start");
			
			
			HttpClient client = null;
			HttpGet request = null;
			HttpResponse response = null;
			
			BufferedReader br = null;
			StringBuilder sb = null;
			byte[] brr = null;
			int code = 0;
//			List<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
			Mylog.v("DetailThread start2 : " + productId );
//			params1.add(new BasicNameValuePair("product_id", productId));
//			Mylog.v("DetailThread start: " +detail_item.getProduct_id());
			UrlEncodedFormEntity entity = null;
			try {
				
				String url = "http://tagazine.nkr1545.cloulu.com/product/view/";
				url += productId; 
				Mylog.v("loading2");
				
//				entity = new UrlEncodedFormEntity(params1);
				client = NetManager.getHttpClient();
				request = NetManager.getGet(url); 
				response  = client.execute(request);
				code = response.getStatusLine().getStatusCode();
				Mylog.v("code: " +code);
				
				switch(code)
				{
				case 200:
					String data = IOUtils.toString(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
					Mylog.v("data: "+data);
					 obj = JSONParser.detailParse(data);
					Mylog.v("detail data: " + obj);
				
				
					break;
				}
			} catch (Exception e) {
				Mylog.v("detail Task error: " +e);
			}
			return obj;
		}

		@Override
		protected void onPostExecute(Object result) {
			if(pDialog != null)
			{
				pDialog.cancel();
			}
			Mylog.v("detail data2: " + obj);
			detail_item = (Item_Detail)result;
			path =detail_item.getFull_img_path();
			Mylog.v("path: " + path);
			new DownTask().execute(path);
			Mylog.v("downtask");
			
			profileimg.setProfileId(detail_item.getUser_id());
			userName.setText(detail_item.getNickname());
			uploadDate.setText(detail_item.getUpload_date());
			styleTip.setText(detail_item.getMemo());
			like_cnt.setText(detail_item.getLike_cnt()+"");
			view_cnt.setText(detail_item.getView_cnt()+"");
//			detail_item.getc
			taginfo_adapter.setTaginfoData(detail_item.getTagList());
			comment_adapter.setCommentData(detail_item.getCommentList());
			taginfo_adapter.notifyDataSetChanged();
//			comment_adapter.notifyDataSetChanged();
			
			Mylog.v("size : " + detail_item.getCommentList().size());
			int height = (getResources().getDimensionPixelSize(R.dimen.comment_listview)+1)*detail_item.getCommentList().size();
			
			Mylog.v("height : " + height);
	        LayoutParams lp = (LayoutParams)comment_Listview.getLayoutParams();
	        lp.height = height;
	        comment_Listview.setLayoutParams(lp);
	        comment_adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			pDialog = ProgressDialog.show(getActivity(), "", "Loading...");
			super.onPreExecute();
		}
		
	}
	
	/*
	 * 이미지 다운
	 */
	class DownTask extends AsyncTask<String, Void, Bitmap>
	{
//		ImageView img;
//		File f = null;
		@Override
		protected void onPostExecute(Bitmap result) {
			if(result != null)
			{
//				mLoader.DisplayImage(f.getAbsolutePath(), img);
				img.setImageBitmap(result);
			}
			else
			{
				
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			
			Mylog.v("downtask") ;
			Bitmap bitmap = null;
//			img = (ImageView)params[2];
//			f = (File)params[1];
			
			HttpClient client = null;
			HttpGet request = null;
			HttpResponse response = null;
			InputStream is = null;
			int code = 0;
			int len = 0;
			byte[] imgArr = null;
			
			try {
				client = NetManager.getHttpClient();
				request = NetManager.getGet("http://tagazine.nkr1545.cloulu.com/uploads/full_img/"+params[0]);
				Mylog.v("url: http://tagazine.nkr1545.cloulu.com/uploads/full_img"+params[0]);
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
//					bitmap.compress(CompressFormat.PNG, 100,
//							new FileOutputStream( (File) params[1]));
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

	}
	
	/*
	 * 코멘트
	 */
	
	public String getUserId()
	{

		SharedPreferences sp = activity.getSharedPreferences("tagazine", 0);
		String id = sp.getString("userid", "");
		Mylog.v("userid: "+ id);
		return id;
	}
	
	class CommentUploadThread extends Thread
	{
		public void run()
		{
			String url = "http://tagazine.nkr1545.cloulu.com/product/comment/add";
			
			String mproductId = detail_item.getProduct_id();		
			String muserId = getUserId();
			String mcontent = et_comment.getText().toString();
			
			HttpClient client = null;
			HttpPost request = null;
			HttpResponse response = null;
			
			BufferedReader br = null;
			StringBuilder sb = null;
			final Message msg = handler.obtainMessage();
			String line = "";
			int code = 0;
			
			Mylog.v("product id : "+ mproductId);
			Mylog.v("content : "+ mcontent);
			
			
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("product_id", mproductId));
			params.add(new BasicNameValuePair("user_id", muserId)); 
			params.add(new BasicNameValuePair("contents", mcontent));
			
			UrlEncodedFormEntity entity = null;
			try {
				
				Mylog.v("comment Thread ");
				
				entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				client = NetManager.getHttpClient();
				request = NetManager.getPost(url);
				request.setEntity(entity); 
				response = client.execute(request);
				code = response.getStatusLine().getStatusCode();
				Mylog.v("code : " +code);
				
//				
				switch(code)
				{
				case 200:
					br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					sb = new StringBuilder();
					while((line = br.readLine())!= null)
					{
						sb.append(line + "\n");
					}
					Mylog.v("process msg : " + sb.toString());
				
//					data = sb.toString();
//					Message msg = Message.obtain();
//					msg.obj = sb.toString();
//					handler.handleMessage(msg);
					msg.what = 999;
					msg.obj = "success";
					break;
				}
			} catch (Exception e) {
				Mylog.v("comment upload error" +e);
			}
			handler.sendMessage(msg);
		}
	}
	
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			if(pDialog != null)
			{
				pDialog.cancel();
//				ImageCall_Activity.this.finish();
			}
			switch(msg.what)
			{
	
			case 999:
				Toast.makeText(activity, "comment upload complete", Toast.LENGTH_SHORT).show();
				comment_adapter.setCommentData(detail_item.getCommentList());
				int height = (getResources().getDimensionPixelSize(R.dimen.comment_listview)+1)*detail_item.getCommentList().size();
				
				Mylog.v("height : " + height);
		        LayoutParams lp = (LayoutParams)comment_Listview.getLayoutParams();
		        lp.height = height;
		        comment_Listview.setLayoutParams(lp);
		        comment_adapter.notifyDataSetChanged();
				break;
			case 777:
			break;
			}
			super.handleMessage(msg);
		}
	};
	
	
	
	/*
	 * Like button
	 */ 
	
//	class LikeTask extends AsyncTask<Object, Void, Object>
//	{
//		Object obj;
//		String likeCount;
//		String product_id;
//		@Override
//		protected Object doInBackground(Object... params) {
//			HttpClient client = null;
//			HttpGet request = null;
//			HttpResponse response = null;
//			InputStream is = null;
//			product_id = params[0].toString();
//			BufferedReader br = null;
//			StringBuilder sb = null;
//			int code = 0;
//			Mylog.v("Like Task start");
////			String productId = params[0].toString();
//			List<BasicNameValuePair> params1 = new ArrayList<BasicNameValuePair>();
//		
//			params1.add(new BasicNameValuePair("user_id", getUserId())); 
//			params1.add(new BasicNameValuePair("product_id",product_id));
//			UrlEncodedFormEntity entity = null;
//			try {
//				String url = "http://tagazine.nkr1545.cloulu.com/product/";
//				url += product_id;
//				url += "/like/";
//				url += getUserId();
//				Mylog.v("Like Task start2222");
////				url = url + ":" + URLEncodedUtils.format( params1, "UTF-8" ) ; 
//
//
//				entity = new UrlEncodedFormEntity(params1);
//				client = NetManager.getHttpClient();
//				request = NetManager.getGet(url); 
//				response  = client.execute(request);
//				code = response.getStatusLine().getStatusCode();
//				Mylog.v("Like code1: " +code);
//				switch(code)
//				{
//				case 200:
//					br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//					sb = new StringBuilder();
//					String temp = "";
//					while((temp = br.readLine()) != null)
//					{
//						sb.append(temp).append("\n");
//					}
//					obj = JSONParser.likeParse(sb.toString());
//					Mylog.v("obj: " +sb.toString());
//					Mylog.v("Like Task start333");
//					break;
//				}
//			} catch (Exception e) {
//				Mylog.v("liketask error : " +e);
//			}
//			return obj;
//		}
//
//		@Override
//		protected void onPostExecute(Object obj) {
//			if(obj instanceof String)
//			{
//				Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
//			}
//			else
//			{
////				Item_Like item = (Item_Like)obj;
////				product_data.get(position).setLike_cnt((product_data.get(position).getLike_cnt()+1));
////				product_data.get(position).setLike_cnt(product_data.get(position).getLike_cnt()+1);
//				detail_data.get(position).setLike_cnt(product_data.get(position).getLike_cnt()+1);
////				notifyDataSetChanged();
//			}
//			super.onPostExecute(obj);
//		}		
//	}

}
