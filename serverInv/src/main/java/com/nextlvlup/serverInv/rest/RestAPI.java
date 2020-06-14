package com.nextlvlup.serverInv.rest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class RestAPI {
	
	private String apiUrl;
	
	private HttpClient client;
	
	public RestAPI(String apiUrl) {	
		this.apiUrl = apiUrl;
		this.client = HttpClientBuilder.create().build();
	}
	
	
	//Methods
	public JSONObject get(String resource) {
		try {
			HttpResponse response = client.execute(new HttpGet(apiUrl + resource));
			
			if(response.getStatusLine().getStatusCode() == 200) {
				return new JSONObject(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<JSONObject> getList(String resource) {
		try {
			HttpResponse response = client.execute(new HttpGet(apiUrl + resource));
			
			if(response.getStatusLine().getStatusCode() == 200) {
				List<JSONObject> list = new ArrayList<JSONObject>();
				JSONArray array = new JSONArray(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
				
				for(int i = 0; i < array.length(); i++) {
					list.add(array.getJSONObject(i));
				}
				return list;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<JSONObject>();
	}
}
