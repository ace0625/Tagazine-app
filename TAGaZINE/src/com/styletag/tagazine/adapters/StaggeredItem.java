package com.styletag.tagazine.adapters;

import java.io.Serializable;

import android.graphics.Bitmap;

public class StaggeredItem implements Serializable {


	private static final long serialVersionUID = 7641824643525021459L;
	public String image_path;
	Bitmap image;
	
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Myitem [image_path=" + image_path + ", image=" + image + "]";
	}
}
