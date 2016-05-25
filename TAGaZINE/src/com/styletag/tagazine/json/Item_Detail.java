package com.styletag.tagazine.json;

import java.io.Serializable;
import java.util.ArrayList;

import com.styletag.tagazine.adapters.TagItem;

public class Item_Detail implements Serializable {


	private static final long serialVersionUID = 6203558396559120778L;

	private String user_id;
	private String product_id;
	private String upload_date;
	private int view_cnt;
	private int like_cnt;
	private int comment_cnt;
	private String memo;
	private String origin_img_path;
	private String full_img_path;
	private String thumb_img_path;
	private String category;
	private String user_pw;
	private String facebook;
	private String join_date;
	private String nickname;
	private String profile_img_path;
    private ArrayList<TagItem> tagList = new ArrayList<TagItem>();
    private ArrayList<Comment> commentList = new ArrayList<Comment>();
    
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(String upload_date) {
		this.upload_date = upload_date;
	}
	public int getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}
	public int getLike_cnt() {
		return like_cnt;
	}
	public void setLike_cnt(int like_cnt) {
		this.like_cnt = like_cnt;
	}
	public int getComment_cnt() {
		return comment_cnt;
	}
	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOrigin_img_path() {
		return origin_img_path;
	}
	public void setOrigin_img_path(String origin_img_path) {
		this.origin_img_path = origin_img_path;
	}
	public String getFull_img_path() {
		return full_img_path;
	}
	public void setFull_img_path(String full_img_path) {
		this.full_img_path = full_img_path;
	}
	public String getThumb_img_path() {
		return thumb_img_path;
	}
	public void setThumb_img_path(String thumb_img_path) {
		this.thumb_img_path = thumb_img_path;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfile_img_path() {
		return profile_img_path;
	}
	public void setProfile_img_path(String profile_img_path) {
		this.profile_img_path = profile_img_path;
	}
	
	public ArrayList<TagItem> getTagList() {
		return tagList;
	}
	public void setTagList(ArrayList<TagItem> tagList) {
		this.tagList = tagList;
	}
	public ArrayList<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(ArrayList<Comment> commentList) {
		this.commentList = commentList;
	}
	@Override
	public String toString() {
		return "Item_Detail [user_id=" + user_id + ", product_id=" + product_id
				+ ", upload_date=" + upload_date + ", view_cnt=" + view_cnt
				+ ", like_cnt=" + like_cnt + ", comment_cnt=" + comment_cnt
				+ ", memo=" + memo + ", origin_img_path=" + origin_img_path
				+ ", full_img_path=" + full_img_path + ", thumb_img_path="
				+ thumb_img_path + ", category=" + category + ", user_pw="
				+ user_pw + ", facebook=" + facebook + ", join_date="
				+ join_date + ", nickname=" + nickname + ", profile_img_path="
				+ profile_img_path + ", tagList=" + tagList + ", commentList="
				+ commentList + "]";
	}


	
}
