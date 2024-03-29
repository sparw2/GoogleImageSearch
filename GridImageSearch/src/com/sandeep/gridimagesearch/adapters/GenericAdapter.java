package com.sandeep.gridimagesearch.adapters;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sandeep.gridimagesearch.R;

public abstract class GenericAdapter<T> extends ArrayAdapter<T> {

	// the main data list to save loaded data
	protected List<T> dataList;
	
	protected Activity mActivity;
	
	// the serverListSize is the total number of items on the server side,
	// which should be returned from the web request results
	protected int serverListSize = -1;
	
	// Two view types which will be used to determine whether a row should be displaying 
	// data or a Progressbar
	
	public static final int VIEW_TYPE_LOADING = 0;
	public static final int VIEW_TYPE_ACTIVITY = 1;


	public GenericAdapter(Activity activity, List<T> list) {
		super(activity, android.R.layout.simple_list_item_1, list);
		
		mActivity = activity;
		dataList = list;
	}
	
	
	public void setServerListSize(int serverListSize){
		this.serverListSize = serverListSize;
	}

	
	/**
	 * disable click events on indicating rows
	 */
	@Override
	public boolean isEnabled(int position) {
		 
		return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
	}
	
	/**
	 * One type is normal data row, the other type is Progressbar
	 */
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	
	/**
	 * the size of the List plus one, the one is the last row, which displays a Progressbar
	 */
	@Override
	public int getCount() {
		return dataList.size() + 1;
	}


	/**
	 * return the type of the row, 
	 * the last row indicates the user that the ListView is loading more data
	 */
	@Override
	public int getItemViewType(int position) {
		return (position >= dataList.size()) ? VIEW_TYPE_LOADING
				: VIEW_TYPE_ACTIVITY;
	}
	
	@Override
	public T getItem(int position) {
		return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? dataList
				.get(position) : null;
	}
	
	@Override
	public long getItemId(int position) {
		return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? position
				: -1;
	}

	/**
	 *  returns the correct view 
	 */
	@Override
	public  View getView(int position, View convertView, ViewGroup parent){
		if (getItemViewType(position) == VIEW_TYPE_LOADING) {
			// display the last row
			return getFooterView(position, convertView, parent);
		}
		View dataRow = convertView;
		dataRow = getDataRow(position, convertView, parent);
		
		return dataRow;
	};

	/**
	 * A subclass should override this method to supply the data row.
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getDataRow(int position, View convertView, ViewGroup parent);


	/**
	 * returns a View to be displayed in the last row.
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public View getFooterView(int position, View convertView,
			ViewGroup parent) {
		
		
		Log.e("GenericAdapter", "position:"+position);
		
		if (position >= serverListSize && serverListSize > 0) {
			// the ListView has reached the last row
			TextView tvLastRow = new TextView(mActivity);
			tvLastRow.setHint("Reached the last row.");
			tvLastRow.setGravity(Gravity.CENTER);
			return tvLastRow;
		}
		
		View row = convertView;
		if (row == null) {
			row = mActivity.getLayoutInflater().inflate(
					R.layout.progress, parent, false);
		}
	
		return row;
	}

}
