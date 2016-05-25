package com.styletag.tagazine.json;

import java.io.Serializable;

public class Item_ProductList implements Serializable {


	private static final long serialVersionUID = -5856815304636007300L;

	private String product_id;
	private String upload_date;
	private int view_cnt;
	private int like_cnt;
	private String memo;
	private int comment_cnt;
	private String thumb_img_path;
	private String full_img_path;
	private String origin_img_path;
	private String user_id;
	private String category;
	
	
	private boolean isLike;
	
	public boolean isLike() {
		return isLike;
	}
	public void setLike(boolean isLike) {
		this.isLike = isLike;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getComment_cnt() {
		return comment_cnt;
	}
	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
	public String getThumb_img_path() {
		return thumb_img_path;
	}
	public void setThumb_img_path(String thumb_img_path) {
		this.thumb_img_path = thumb_img_path;
	}
	public String getFull_img_path() {
		return full_img_path;
	}
	public void setFull_img_path(String full_img_path) {
		this.full_img_path = full_img_path;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOrigin_img_path() {
		return origin_img_path;
	}
	public void setOrigin_img_path(String origin_img_path) {
		this.origin_img_path = origin_img_path;
	}
	@Override
	public String toString() {
		return "Item_ProductList [product_id=" + product_id + ", upload_date="
				+ upload_date + ", view_cnt=" + view_cnt + ", like_cnt="
				+ like_cnt + ", memo=" + memo + ", comment_cnt=" + comment_cnt
				+ ", thumb_img_path=" + thumb_img_path + ", full_img_path="
				+ full_img_path + ", origin_img_path=" + origin_img_path
				+ ", user_id=" + user_id + ", category=" + category
				+ ", isLike=" + isLike + "]";
	}

	
	
	
}
