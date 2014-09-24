package com.sandeep.gridimagesearch.helpers;

import android.util.Log;

import com.sandeep.gridimagesearch.models.ImageFilter;

public class ImageQueryBuilder {

	private static String query = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=";
	
	public static String getPaginatedQuery(int offset, String q){
		
		StringBuilder builder = new StringBuilder();
		builder.append(query);
		builder.append(q);
		builder.append("&start="+(8*offset));
		
		return builder.toString();
	}
	
	public static String getPaginatedQueryWithFilters(int offset, String q,ImageFilter filter){
		
		StringBuilder builder = new StringBuilder();
		builder.append(getPaginatedQuery(offset, q));
		
		// apply the filters to the query expression
		if (filter != null){
			if (filter.color != null && !filter.color.isEmpty())
				builder.append("&imgcolor="+filter.color);
			if (filter.size != null && !filter.size.isEmpty())
				builder.append("&imgsz="+filter.size);
			if (filter.type != null && !filter.type.isEmpty())
				builder.append("&imgtype="+filter.type);
			if (filter.site != null && !filter.site.isEmpty())
				builder.append("&as_sitesearch="+filter.site);
		}
		
		Log.d("ImageQueryBuilder", builder.toString());
		
		return builder.toString();
	}
}
