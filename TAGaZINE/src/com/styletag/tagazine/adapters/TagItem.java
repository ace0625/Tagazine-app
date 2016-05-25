package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class TagItem implements Serializable {

	
	private static final long serialVersionUID = -8574653737420387725L;
	
	String product_id;
	int type;
	int posX;
	int posY;
	String brand;
	String product;
	String price;
	String address;
//	String category;
//	String styleTip;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	@Override
	public String toString() {
		return "TagItem [product_id=" + product_id + ", type=" + type
				+ ", posX=" + posX + ", posY=" + posY + ", brand=" + brand
				+ ", product=" + product + ", price=" + price + ", address="
				+ address + "]";
	}


	
}
