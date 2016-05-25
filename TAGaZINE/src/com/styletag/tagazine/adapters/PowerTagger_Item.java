package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class PowerTagger_Item implements Serializable {

	
	private static final long serialVersionUID = -248169628166660801L;

	private int image;
	private String name;
	
	public PowerTagger_Item(int image, String name)
	{
		this.image = image;
		this.name = name;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PowerTagger_Item [image=" + image + ", name=" + name + "]";
	}



}
