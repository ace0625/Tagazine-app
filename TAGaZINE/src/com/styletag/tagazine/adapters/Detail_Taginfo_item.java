package com.styletag.tagazine.adapters;

import java.io.Serializable;

public class Detail_Taginfo_item implements Serializable {


	private static final long serialVersionUID = 685214318612686190L;
	 
	private int tag_info;

	public int getTag_info() {
		return tag_info;
	}

	public void setTag_info(int tag_info) {
		this.tag_info = tag_info;
	}

	@Override
	public String toString() {
		return "Detail_Taginfo_item [tag_info=" + tag_info + "]";
	}

	
}
