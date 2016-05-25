package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class HotStreetFashion_Item implements Serializable {

	
	private static final long serialVersionUID = -2930010096586488831L;

	private int image;
	
	public HotStreetFashion_Item(int image)
	{
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
		return "HotStreetFashion_Item [image=" + image + "]";
	}
	
	
}
