package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class MenuList_Item implements Serializable {

	private static final long serialVersionUID = 7126793349520306123L;
	
	private String title;
	private String sub;
//	private String img;
	private String userid;
	private int type; //0, 1, 2
	
	public MenuList_Item(){}
	 public MenuList_Item(int type,String userid, String title, String sub) 
	 {
		 this.type = type;
		this.userid = userid;
		this.title = title;
		this.sub = sub;
	 }
//	public MenuList_Item(int type, String title, String sub, String userid)
//	{
//		this.type = type;
//		this.title = title;
//		this.sub = sub;
//		this.userid = userid;
////		this.img = img;
//	}
	public MenuList_Item(int type, String title)
	{
		this.type = type;
		this.title = title;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
//	public String getImg() {
//		return img;
//	}
//	public void setImg(String img) {
//		this.img = img;
//	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
