package com.styletag.tagazine.json;

import java.io.Serializable;

public class Item_Menulist_StyleLook implements Serializable {

	private static final long serialVersionUID = 5904298985123381163L;

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Item_Menulist_StyleLook [title=" + title + "]";
	}
	
	
	
	
}
