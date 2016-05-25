package com.styletag.tagazine.json;

import java.io.Serializable;

public class Item_UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3615471738089934788L;
	private String user_id;
	private String user_pw;
	private int facebook;
	private String join_date;
	private String nickname;
	private String profile_img_path;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public int getFacebook() {
		return facebook;
	}
	public void setFacebook(int facebook) {
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
	@Override
	public String toString() {
		return "Item_UserInfo [user_id=" + user_id + ", user_pw=" + user_pw
				+ ", facebook=" + facebook + ", join_date=" + join_date
				+ ", nickname=" + nickname + ", profile_img_path="
				+ profile_img_path + "]";
	}
	
	
	
}

