package com.sandeep.gridimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.sandeep.gridimagesearch.R;
import com.sandeep.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultsAdapter(Context context,List<ImageResult> list) {
		super(context,android.R.layout.simple_list_item_1,list);
	}

	private class ViewHolder{
		ImageView ivImage;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		ImageResult imageResult = getItem(position);
		
		ViewHolder viewHolder;
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.ivImage.setImageResource(0);
		
		int scaledHeight = (parent.getWidth()/3) *imageResult.tbImageHeight/ imageResult.tbImageWidth;
		
		Picasso.with(getContext()).load(imageResult.tbUrl).resize(parent.getWidth()/3, scaledHeight).into(viewHolder.ivImage);

		return convertView;
	}


	

}
	