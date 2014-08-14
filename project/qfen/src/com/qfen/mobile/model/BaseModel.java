package com.qfen.mobile.model;

import org.json.JSONException;
import org.json.JSONObject;


public class BaseModel {
	public JSONObject jsonObj = null;
	
	public BaseModel(String json) {
		try {
			jsonObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public BaseModel() {
	}
}
