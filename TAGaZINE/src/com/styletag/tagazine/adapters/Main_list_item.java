package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class Main_list_item implements Serializable {

	private static final long serialVersionUID = -5644202875747958295L;
	
	private String image;
	private int count;
	
	public Main_list_item(String image, int count)
	{
		this.image = image;
		this.count = count;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Main_list_item [image=" + image + ", count=" + count + "]";
	}
	
}
