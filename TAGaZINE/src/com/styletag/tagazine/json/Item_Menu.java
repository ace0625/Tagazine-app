package com.styletag.tagazine.json;

import java.io.Serializable;
import java.util.ArrayList;

public class Item_Menu implements Serializable {

	ArrayList<Item_Menulist_StyleLook> styleList = new ArrayList<Item_Menulist_StyleLook>();
	ArrayList<Item_Menulist_Brand> brandList = new ArrayList<Item_Menulist_Brand>();
	private static final long serialVersionUID = 5562443154619647442L;

	private String title;
	

	public ArrayList<Item_Menulist_StyleLook> getStyleList() {
		return styleList;
	}

	public void setStyleList(ArrayList<Item_Menulist_StyleLook> styleList) {
		this.styleList = styleList;
	}

	public ArrayList<Item_Menulist_Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(ArrayList<Item_Menulist_Brand> brandList) {
		this.brandList = brandList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Item_Menu [styleList=" + styleList + ", brandList=" + brandList
				+ ", title=" + title + "]";
	}

}
