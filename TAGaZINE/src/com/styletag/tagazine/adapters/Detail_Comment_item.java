package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class Detail_Comment_item implements Serializable {


	private static final long serialVersionUID = 7387527382615193145L;

	private String cm_userid;
	private String cm_username;
	private String cm_time;
	private String cm_comment;
	public String getCm_userid() {
		return cm_userid;
	}
	public void setCm_userid(String cm_userid) {
		this.cm_userid = cm_userid;
	}
	public String getCm_username() {
		return cm_username;
	}
	public void setCm_username(String cm_username) {
		this.cm_username = cm_username;
	}
	public String getCm_time() {
		return cm_time;
	}
	public void setCm_time(String cm_time) {
		this.cm_time = cm_time;
	}
	public String getCm_comment() {
		return cm_comment;
	}
	public void setCm_comment(String cm_comment) {
		this.cm_comment = cm_comment;
	}
	@Override
	public String toString() {
		return "Detail_Comment_item [cm_userid=" + cm_userid + ", cm_username="
				+ cm_username + ", cm_time=" + cm_time + ", cm_comment="
				+ cm_comment + "]";
	}
	
	
}
