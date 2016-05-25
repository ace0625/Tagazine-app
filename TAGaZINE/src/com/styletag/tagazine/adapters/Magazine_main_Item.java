package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class Magazine_main_Item implements Serializable {

	private static final long serialVersionUID = 9078372148648594894L;

	private int image;
	private String date;
	private String volnum;
	
	public Magazine_main_Item(int image, String date, String volnum)
	{
		this.image = image;
		this.date = date;
		this.volnum = volnum;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getVolnum() {
		return volnum;
	}
	public void setVolnum(String volnum) {
		this.volnum = volnum;
	}
	@Override
	public String toString() {
		return "Magazine_main_Item [image=" + image + ", date=" + date
				+ ", volnum=" + volnum + "]";
	}
	
	
}
