package com.styletag.tagazine.json;

import java.io.Serializable;

public class Item_Menulist_Brand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4874395288216434792L;
	
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Item_Menulist_Brand [title=" + title + "]";
	}
	
	

}
