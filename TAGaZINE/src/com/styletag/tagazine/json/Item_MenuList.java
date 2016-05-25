package com.styletag.tagazine.json;

import java.io.Serializable;

public class Item_MenuList implements Serializable {

	private static final long serialVersionUID = -2311472207516108709L;

	private String title;
	private String section_title;
	private String update_date;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSection_title() {
		return section_title;
	}
	public void setSection_title(String section_title) {
		this.section_title = section_title;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	@Override
	public String toString() {
		return "Item_MenuList [title=" + title + ", section_title="
				+ section_title + ", update_date=" + update_date + "]";
	}
	
	
}
