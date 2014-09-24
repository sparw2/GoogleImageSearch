package com.sandeep.gridimagesearch.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable{


	private static final long serialVersionUID = -3928902935167983878L;

	public String fullUrl;
	public int imageWidth;
	public int imageHeight;
	
	public String tbUrl;
	public int tbImageWidth;
	public int tbImageHeight; 
	
	public String title;
	
	public ImageResult(JSONObject json){
		try{
			this.fullUrl = json.getString("url");
			this.imageWidth = json.getInt("width");
			this.imageHeight = json.getInt("height");
			
			this.tbUrl = json.getString("tbUrl");
			this.tbImageWidth = json.getInt("tbWidth");
			this.tbImageHeight = json.getInt("tbHeight");
			
			this.title = json.getString("title");
		}catch(JSONException e){
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<ImageResult> fromJSONArray(JSONArray array){
	
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		
		for (int i=0; i < array.length() ; i++){
			try{
				results.add(new ImageResult(array.getJSONObject(i)));
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		
		
		return results;
	}
}
