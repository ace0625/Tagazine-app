package com.styletag.tagazine.adapters;

import java.util.ArrayList;

import com.facebook.widget.ProfilePictureView;
import com.styletag.tagazine.activity.R;
import com.styletag.tagazine.adapters.Powertagger_Adapter.Holder;
import com.styletag.tagazine.json.Comment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Detail_Comment_Adapter extends BaseAdapter {
	Context context;
	int layout;
	ArrayList<Comment> commentList;
	
	public Detail_Comment_Adapter(Context context, int layout, ArrayList<Comment> commentList)
	{
		this.context = context;
		this.layout = layout;
		this.commentList = commentList;
	}

	public void setCommentData(ArrayList<Comment> commentList)
	{
		this.commentList = commentList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	class Holder
	{
		ProfilePictureView proimg;
		TextView username, time, comment;
	}
	@Override
	public View getView(int position, View cView, ViewGroup parent) {
		final Comment item = commentList.get(position);
		Holder holder = null;
		
		if(cView == null)
		{
			cView = View.inflate(context, R.layout.main_stylefeed_detail_comment, null);
			holder = new Holder();
			holder.proimg = (ProfilePictureView)cView.findViewById(R.id.detail_comment_picture);
			holder.username = (TextView)cView.findViewById(R.id.detail_comment_username);
			holder.time = (TextView)cView.findViewById(R.id.detail_comment_time);
			holder.comment = (TextView)cView.findViewById(R.id.detail_comment_text);
			cView.setTag(holder);
		}
		else
		{
			holder = (Holder)cView.getTag();
		}
		holder.proimg.setProfileId(item.getUser_id());
		holder.username.setText(item.getNickname());
		holder.time.setText(item.getWrite_date() + "");
		holder.comment.setText(item.getContent());
		return cView;
	}

}
