package com.styletag.tagazine.json;

import java.io.Serializable;

public class Comment implements Serializable {

	private static final long serialVersionUID = 1565866720901242629L;
	
	private String user_id;
	private String product_id;
	private String write_date;
	private String content;
	private String nickname;
	
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "Comment [user_id=" + user_id + ", product_id=" + product_id
				+ ", write_date=" + write_date + ", content=" + content
				+ ", nickname=" + nickname + "]";
	}
	
	
}
