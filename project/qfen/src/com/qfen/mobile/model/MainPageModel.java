package com.qfen.mobile.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainPageModel extends BaseModel {
	public List<MainPageModel> list = null;
	
	public MainPageModel(String json) {
		super(json);
		
		try {
			list = new ArrayList<MainPageModel>();
			fromJsonObj2Model(this.jsonObj,"data");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public MainPageModel() {
	}

	public String exhibition_id;
	public String ex_type;
	public String title;
	public String address;
	public String litpic;
	public String StartTime;
	public String EndTime;
	public String all_jiangjin;
	public String other_jiangjin;
	public String fenxiang_jiangjin;
	
	private MainPageModel fromJsonObj2Model(JSONObject jsonObj) throws JSONException {
		MainPageModel model = new MainPageModel();
		model.exhibition_id = jsonObj.getString("exhibition_id");
		model.ex_type = jsonObj.getString("ex_type");
		model.title = jsonObj.getString("title");
		model.address = jsonObj.getString("address");
		model.litpic = jsonObj.getString("litpic");
		model.StartTime = jsonObj.getString("StartTime");
		model.EndTime = jsonObj.getString("EndTime");
		model.all_jiangjin = jsonObj.getString("all_jiangjin");
		model.other_jiangjin = jsonObj.getString("other_jiangjin");
		model.fenxiang_jiangjin = jsonObj.getString("fenxiang_jiangjin");
		
		return model;
	}
	
	
	private void fromJsonObj2Model(JSONObject jsonObj,String listKey) throws JSONException {
		JSONArray array = jsonObj.getJSONArray("data");
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			list.add(fromJsonObj2Model(obj));
		}
	}
}
