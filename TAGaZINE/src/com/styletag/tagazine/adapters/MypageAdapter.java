package com.styletag.tagazine.adapters;

import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.StaggeredAdapter.Holder;
import com.styletag.tagazine.views.ScaleImageView;
import com.styletage.tagazine.loader.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MypageAdapter extends BaseAdapter {
	private ImageLoader mLoader;
	Context context;
	int layout;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	class Holder
	{
		ScaleImageView imageView;
		Button btnlike;
		TextView likecount;
	}

	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		Holder holder;
		if (cView == null) 
		{
			Mylog.v("mypage adapter");
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
//		holder.likecount.setText(product_data.get(position).getLike_cnt()+"");
		
		return cView;
	}

}
