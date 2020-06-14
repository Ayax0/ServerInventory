package com.nextlvlup.serverInv.rest;

import org.json.JSONObject;

import com.nextlvlup.serverInv.Main;

public class ReturnHook {
	
	private String path;
	
	public ReturnHook(String path) {
		this.path = path;
	}
	
	public void get(final ActionHandler handler) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				handler.handle(Main.getRestAPI().get(path));
			}
		}).start();
	}
	
	public void getList(final ActionHandler handler) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(JSONObject obj : Main.getRestAPI().getList(path)) {
					handler.handle(obj);
				}
			}
		}).start();
	}

}
