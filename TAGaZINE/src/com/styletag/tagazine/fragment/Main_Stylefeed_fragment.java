package com.styletag.tagazine.fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.StaggeredAdapter;
import com.styletag.tagazine.json.Item_ProductList;
import com.styletag.tagazine.json.JSONParser;
import com.styletag.tagazine.utils.NetManager;
import com.styletag.tagazine.views.ProgressWheel;
import com.styletag.tagazine.views.StaggeredGridView;

public class Main_Stylefeed_fragment extends android.support.v4.app.Fragment {
	Activity activity;
//	ArrayList<StaggeredItem> staggered_data = new ArrayList<StaggeredItem>();
	public ProgressWheel progresswheel;
	
	StaggeredGridView staggeredgridview;
	StaggeredAdapter adapter;
	ProgressDialog pDialog = null;
	ArrayList<Item_ProductList> product_data = new ArrayList<Item_ProductList>();
	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}
	String category = "";
	public void getCategory(String category){
		this.category = category;
		new LoadCategoryThread(category).start();
	}
	class LoadCategoryThread extends Thread
	{
		String category = "";
		LoadCategoryThread(String category){
			this.category = category;
		}
		public void run()
		{
			Mylog.v("run");
			String url = "http://tagazine.nkr1545.cloulu.com/product/list/category";
			
			HttpClient client = null;
			HttpPost request = null;
			HttpResponse response = null;
			
			BufferedReader br = null;
			StringBuilder sb = null;
			byte[] brr = null;
			int code = 0;
			Message msg = handler.obtainMessage();
			
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("category", category));
			
			UrlEncodedFormEntity entity = null;
			try {
				Mylog.v("run2");
				entity = new UrlEncodedFormEntity(params);
				client = NetManager.getHttpClient();
				request = NetManager.getPost(url);
				request.setEntity(entity); //post 인경우
				response = client.execute(request);
				code = response.getStatusLine().getStatusCode();
				Mylog.v("code: " +code);
				switch(code)
				{
				case 200:
//					br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//					sb = new StringBuilder();
					
//					String str = "";
					
//					while((str=br.readLine()) != null)
//					{
//						sb.append(str).append("\n");
//					}
					String data = IOUtils.toString(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
					Mylog.v("data: "+data);
					product_data = JSONParser.mainfeedParser(data);
					Mylog.v("product data: " + product_data.size());
					msg.what = 999;
					break;
				}
			} catch (Exception e) {
				Mylog.v("LoadThread error : " +e);
			}
			handler.sendMessage(msg);
		}
	}
	

	class LoadThread extends Thread
	{
		public void run()
		{
			Mylog.v("run");
			String url = "http://tagazine.nkr1545.cloulu.com/product/list";
			
			HttpClient client = null;
			HttpGet request = null;
			HttpResponse response = null;
			
			BufferedReader br = null;
			StringBuilder sb = null;
			byte[] brr = null;
			int code = 0;
			Message msg = handler.obtainMessage();
			
//			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
//			params.add(new BasicNameValuePair("product_id", ));
			
			UrlEncodedFormEntity entity = null;
			try {
				Mylog.v("run2");
//				entity = new UrlEncodedFormEntity(params);
				client = NetManager.getHttpClient();
				request = NetManager.getGet(url);
//				request.setEntity(entity); //post 인경우
				response = client.execute(request);
				code = response.getStatusLine().getStatusCode();
				Mylog.v("code: " +code);
				switch(code)
				{
				case 200:
//					br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//					sb = new StringBuilder();
					
//					String str = "";
					
//					while((str=br.readLine()) != null)
//					{
//						sb.append(str).append("\n");
//					}
					String data = IOUtils.toString(new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
					Mylog.v("data: "+data);
					product_data = JSONParser.mainfeedParser(data);
					Mylog.v("product data: " + product_data.size());
					msg.what = 999;
					break;
				}
			} catch (Exception e) {
				Mylog.v("LoadThread error : " +e);
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
			}
		
			switch(msg.what)
			{
			case 999:
				adapter.setData(product_data);
				adapter.notifyDataSetChanged();
				break;
				
			}
			super.handleMessage(msg);
		}
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = null;
		v = inflater.inflate(R.layout.main_stylefeed_fragment, null);
		
//		progresswheel = (ProgressWheel)v.findViewById(R.id.progressWheel1);
		
		//		doload();
		
//				int num=0;
//				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
//				{
//					String path = Environment.getExternalStorageDirectory()+"/Petsitter/";
//						
//					String str;
//					//File file = new File(path);
//					File file = new File(path);
//					String urls[] = new String[file.listFiles().length];;
//					
//					for(File f:file.listFiles())
//					{
//						str = f.getName();
//						String sum = path + str;
//						StaggeredItem item = new StaggeredItem();
//						item.image_path = sum;
//						product_data.add(item);
////						urls[num] = sum;
////						Log.v(TAG,"urls:"+urls[num]);
//						num++;
//					}
//				
//				}
		
				pDialog = ProgressDialog.show(getActivity(), "", "loading...");
//				progresswheel.spin();
				new LoadThread().start();
				
				staggeredgridview = (StaggeredGridView) v.findViewById(R.id.staggeredGridView1);
				int margin = getResources().getDimensionPixelSize(R.dimen.main_stylefeed_margin);
				staggeredgridview.setItemMargin(margin); // set the GridView margin
				staggeredgridview.setPadding(10, 12, 10, 0); // have the margin on the sides as well 
				adapter = new StaggeredAdapter(activity, R.id.main_stylefeed_item_img, product_data);
				staggeredgridview.setAdapter(adapter);
				adapter.notifyDataSetChanged();

			
		return v;
	}
	
//	Boolean checkPosition ;
//	int page;
// Boolean onCheck(Boolean flag){
//		checkPosition = flag;
//		return checkPosition;
//	}
//	AbsListView.OnScrollListener OsHandler = new AbsListView.OnScrollListener() {
//		
//		@Override
//		public void onScrollStateChanged(AbsListView view, int scrollState) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onScroll(AbsListView view, int firstVisibleItem,
//				int visibleItemCount, int totalItemCount) {
//			int count = totalItemCount - visibleItemCount;
//			
//			if((firstVisibleItem >= count) && (totalItemCount != 0))
//			{	
//				onCheck(false);
//				
//			}
//			
//		}
//	};

}
