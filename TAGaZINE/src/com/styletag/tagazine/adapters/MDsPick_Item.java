package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class MDsPick_Item implements Serializable {

	private static final long serialVersionUID = 5987077351262328804L;

	private int image;

	public MDsPick_Item(int image) {
		this.image = image;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "MDsPick_Item [image=" + image + "]";
	}
	
	
}
