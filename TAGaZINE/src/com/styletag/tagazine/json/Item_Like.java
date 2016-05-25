package com.styletag.tagazine.json;

import java.io.Serializable;

public class Item_Like implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8544069695557305281L;

	private String message;
	private String status;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Item_Like [message=" + message + ", status=" + status + "]";
	}
	
	
}
