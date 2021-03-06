/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.styletag.tagazine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

public final class ServerUtilities { //네트워크 타야 되므로 쓰레드 사용

	private static final String TAG = "MainActivity";
	public static String post1(String endpoint, Map<String, String> params)
			throws IOException {
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
			.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		Log.v(TAG, "Posting '" + body + "' to " + url);
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}else{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String temp = "";
				while((temp = br.readLine()) != null)
				{
					sb.append(temp).append("\n");
				}
				Log.v(TAG, "data: " +sb.toString());
				return sb.toString();
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	
	}
//	private static void post(String endpoint, Map<String, String> params)
//			throws IOException {
//		URL url;
//		try {
//			url = new URL(endpoint);
//		} catch (MalformedURLException e) {
//			throw new IllegalArgumentException("invalid url: " + endpoint);
//		}
//		StringBuilder bodyBuilder = new StringBuilder();
//		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
//		// constructs the POST body using the parameters
//		while (iterator.hasNext()) {
//			Entry<String, String> param = iterator.next();
//			bodyBuilder.append(param.getKey()).append('=')
//			.append(param.getValue());
//			if (iterator.hasNext()) {
//				bodyBuilder.append('&');
//			}
//		}
//		String body = bodyBuilder.toString();
//		Log.v(TAG, "Posting '" + body + "' to " + url);
//		byte[] bytes = body.getBytes();
//		HttpURLConnection conn = null;
//		try {
//			conn = (HttpURLConnection) url.openConnection();
//			conn.setDoOutput(true);
//			conn.setUseCaches(false);
//			conn.setFixedLengthStreamingMode(bytes.length);
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded;charset=UTF-8");
//			// post the request
//			OutputStream out = conn.getOutputStream();
//			out.write(bytes);
//			out.close();
//			// handle the response
//			int status = conn.getResponseCode();
//			if (status != 200) {
//				throw new IOException("Post failed with error code " + status);
//			}
//		} finally {
//			if (conn != null) {
//				conn.disconnect();
//			}
//		}
//	}
}
