package com.styletag.tagazine.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.styletag.tagazine.activity.Mylog;
import com.styletag.tagazine.adapters.MenuList_Item;
import com.styletag.tagazine.adapters.TagItem;

public class JSONParser {
	
	public static ArrayList<Item_ProductList> mainfeedParser(String data)
	{
		JSONObject obj = null;
		JSONArray dataArr;
		ArrayList<Item_ProductList> product_item = new ArrayList<Item_ProductList>();
		try {
			obj = new JSONObject(data);
			dataArr = obj.getJSONArray("results");
			for(int i=0; i<dataArr.length(); i++)
			{
				obj = dataArr.getJSONObject(i);
				Item_ProductList pList = new Item_ProductList();
				
				pList.setUser_id(obj.getString("user_id"));
				pList.setProduct_id(obj.getString("product_id"));
				pList.setUpload_date(obj.getString("upload_date"));
				pList.setView_cnt(obj.getInt("view_cnt"));
				pList.setLike_cnt(obj.getInt("like_cnt"));
				pList.setComment_cnt(obj.getInt("comment_cnt"));
				pList.setMemo(obj.getString("memo"));
				pList.setOrigin_img_path(obj.getString("origin_img_path"));
				pList.setFull_img_path(obj.getString("full_img_path"));
				pList.setThumb_img_path(obj.getString("thumb_img_path"));
				pList.setCategory(obj.getString("category"));
				
				product_item.add(pList);
			}
		} catch (Exception e) {
			Mylog.v("mainfeedParser error: " +e);
		}
		return product_item;
	}
	
	public static Object likeParse(String data)
	{
		Item_Like item = null;
		JSONObject obj = null;
		try {
			obj = new JSONObject(data);
			String message = obj.getString("message");
			if(message.equals("success"))
			{
				item = new Item_Like();
				item.setStatus(obj.getString("status"));
			}
			else
			{
				return obj.getString("err");
			}
		} catch (Exception e) {
			Mylog.v("Like Parser error: " +e);
		}
		return item;
	}
		
	public static Object detailParse(String data)
	{
		JSONObject obj = null;
		Item_Detail item = new Item_Detail();
		
		try {
			obj = new JSONObject(data);
			String message = obj.getString("message");
			
			if(message.equals("success"))
			{	
				JSONArray pro_arr = obj.getJSONArray("product");
				for(int i=0; i<pro_arr.length(); i++)
				{
					JSONObject pro_obj = pro_arr.getJSONObject(i);
					item.setUser_id(pro_obj.getString("user_id"));
					Mylog.v("parser1 : " +pro_obj.getString("user_id"));
					item.setProduct_id(pro_obj.getString("product_id"));
					item.setUpload_date(pro_obj.getString("upload_date"));
					item.setView_cnt(pro_obj.getInt("view_cnt"));
					item.setLike_cnt(pro_obj.getInt("like_cnt"));
					item.setComment_cnt(pro_obj.getInt("comment_cnt"));
					item.setMemo(pro_obj.getString("memo"));
					item.setOrigin_img_path(pro_obj.getString("origin_img_path"));
					item.setFull_img_path(pro_obj.getString("full_img_path"));
					item.setThumb_img_path(pro_obj.getString("thumb_img_path"));
					item.setCategory(pro_obj.getString("category"));
					
				}
				JSONArray user_arr = obj.getJSONArray("user");
				for(int i=0; i<user_arr.length();i++)
				{
					JSONObject user_obj = user_arr.getJSONObject(i);
					item.setUser_id(user_obj.getString("user_id"));
					item.setUser_pw(user_obj.getString("user_pw"));
					item.setFacebook(user_obj.getString("facebook"));
					item.setJoin_date(user_obj.getString("join_date"));
					item.setNickname(user_obj.getString("nickname"));
					item.setProfile_img_path(user_obj.getString("profile_img_path"));
				}
				
				JSONArray tag_arr = obj.getJSONArray("tag");
				ArrayList<TagItem> tagList = item.getTagList();
				for(int i=0; i<tag_arr.length(); i++)
				{
					JSONObject tag_obj = tag_arr.getJSONObject(i);
					TagItem tagitem = new TagItem();
					tagitem.setProduct_id(tag_obj.getString("product_id"));
					tagitem.setProduct(tag_obj.getString("title"));
					tagitem.setBrand(tag_obj.getString("brand"));
					tagitem.setPrice(tag_obj.getString("price"));
					tagitem.setAddress(tag_obj.getString("place"));
					tagitem.setPosX(tag_obj.getInt("location_x"));
					tagitem.setPosY(tag_obj.getInt("location_y"));
					tagitem.setType(tag_obj.getInt("kind"));
					Mylog.v("tag add : " + tagitem.getType());
					tagList.add(tagitem);
				}
				JSONArray comment_arr = obj.getJSONArray("comment");
				ArrayList<Comment> commentList = item.getCommentList();
				for(int i=0; i<comment_arr.length(); i++)
				{
					JSONObject comment_obj = comment_arr.getJSONObject(i);
					Comment comitem = new Comment();
					comitem.setUser_id(comment_obj.getString("user_id"));
					comitem.setProduct_id(comment_obj.getString("product_id"));
					comitem.setWrite_date(comment_obj.getString("write_date"));
					comitem.setContent(comment_obj.getString("content"));
					comitem.setNickname(comment_obj.getString("nickname"));
					commentList.add(comitem);
				}
				
			}
			
		} catch (Exception e) {
			Mylog.v("detailParse error: " +e);
		}
		return item;
	}
	
	public static Object menuParser(String data)
	{
		JSONObject obj = null;
		Item_Menu item_menu = new Item_Menu();
		try {
			obj = new JSONObject(data);
			String message = obj.getString("message");
			if(message.equals("success"))
			{	
				JSONArray style_arr = obj.getJSONArray("STYLE");
				ArrayList<Item_Menulist_StyleLook> styleList = item_menu.getStyleList();
				for(int i=0; i<style_arr.length(); i++)
				{
					JSONObject style_obj = style_arr.getJSONObject(i);
					Item_Menulist_StyleLook styleItem = new Item_Menulist_StyleLook();
					styleItem.setTitle(style_obj.getString("title"));
					styleList.add(styleItem);
			
				}
				JSONArray brand_arr = obj.getJSONArray("BRAND");
				ArrayList<Item_Menulist_Brand> brandList = item_menu.getBrandList();
				for(int i=0; i<brand_arr.length();i++)
				{
					JSONObject brand_obj = brand_arr.getJSONObject(i);
					Item_Menulist_Brand brandItem = new Item_Menulist_Brand();
					brandItem.setTitle(brand_obj.getString("title"));
					brandList.add(brandItem);
				}
				
			}
			
		} catch (Exception e) {
			Mylog.v("menuparser error: "+e);
		}
		return item_menu;
		
	}
	
	public static Object userinfoParser(String data)
	{
		Item_UserInfo item = null;
		JSONObject obj = null;
		try {
			obj = new JSONObject(data);
			String message = obj.getString("message");
			if(message.equals("success"))
			{
				JSONArray user_arr = obj.getJSONArray("results");
				for(int i=0; i<user_arr.length(); i++)
				{
					JSONObject user_obj = user_arr.getJSONObject(i);
					item = new Item_UserInfo();
					item.setUser_id(user_obj.getString("user_id"));
					item.setUser_pw(user_obj.getString("user_pw"));
					item.setFacebook(user_obj.getInt("facebook"));
					item.setJoin_date(user_obj.getString("join_date"));
					item.setNickname(user_obj.getString("nickname"));
					item.setProfile_img_path(user_obj.getString("profile_img_path"));
				}
			}
			else
			{
				
			}
		} catch (Exception e) {
			Mylog.v("userinfo parser error : " +e);
		}
		return item;
	}
	

	

}
