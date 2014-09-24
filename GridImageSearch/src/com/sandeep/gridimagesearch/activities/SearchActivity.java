package com.sandeep.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sandeep.gridimagesearch.R;
import com.sandeep.gridimagesearch.adapters.ImageResultsAdapter;
import com.sandeep.gridimagesearch.fragments.FilterDialog;
import com.sandeep.gridimagesearch.helpers.ImageQueryBuilder;
import com.sandeep.gridimagesearch.listeners.EndlessScrollListener;
import com.sandeep.gridimagesearch.models.ImageFilter;
import com.sandeep.gridimagesearch.models.ImageResult;

public class SearchActivity extends FragmentActivity implements FilterDialog.OnFilterEditedListener{

	private EditText etQuery;
	private StaggeredGridView gvResults;
	private static String query;
	
	private ImageResultsAdapter imageResultsAdapter; 	
	
	private ArrayList<ImageResult> imageResults;
	
	private AsyncHttpClient client;
	
	// in-memory keep track of filter criterion
	private static ImageFilter imageFilterCrit = new ImageFilter();
	
	private SearchView searchView;


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		
		searchView = (SearchView) findViewById(R.id.etQueryData);
		
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
                // perform query here
	           	// Check if the network is available or not 
	           	if (!isNetworkAvailable()){
	           		// warn the user that Internet connection is not there
	           		Toast.makeText(SearchActivity.this,"No internet connection", Toast.LENGTH_LONG).show();
	           		return true;
	           	}
        	   
        	    // update the reference to query
        	    SearchActivity.query=query;
        	    
        	    // clear the old data
        	    imageResultsAdapter.clear();
        	    
        	    String url = ImageQueryBuilder.getPaginatedQueryWithFilters(0, query, imageFilterCrit);
				
        	    
        	    loadImages(url);
				return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });
        
        ImageView settingsView = (ImageView)findViewById(R.id.ivSettings);
        settingsView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 FragmentManager fm = getSupportFragmentManager();
				 FilterDialog dialog = FilterDialog.newInstance(imageFilterCrit);
				 dialog.show(fm, "");
			}
		});
        
        
       return true;
    }
	
	private Boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}

	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
    	// change the action bar
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
        getActionBar().setCustomView(R.layout.actionbar_search);


        //etQuery = (EditText)findViewById(R.id.etQuery);
        gvResults = (StaggeredGridView)findViewById(R.id.gvResult);
        
        
        gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if (page < 8)
					customLoadMoreDataFromApi(page,totalItemsCount);
			}
		});
        
        gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				Intent i = new Intent(SearchActivity.this,ImageDisplayActivity.class);
				ImageResult result = imageResults.get(position);
				
				i.putExtra("result", result);
				
				startActivity(i);
			}
		
        	
        });	
        
        
        imageResults = new ArrayList<ImageResult>();
        imageResultsAdapter = new ImageResultsAdapter(this, imageResults);
        
        gvResults.setAdapter(imageResultsAdapter);
        
        client= new AsyncHttpClient();
    }

   
    public void customLoadMoreDataFromApi(final int offset, final int totalItemsCount) {
        
    	// append the offset to the next query 
    	String url = ImageQueryBuilder.getPaginatedQueryWithFilters(offset, query,imageFilterCrit);
    	Log.d("SearchActivity query : ", url);
    
    	// scroll view
    	loadImages(url);
    	
    }

    private void loadImages(String url){
        client.get(url,new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				
				JSONArray imageResultsJson;
				try{
					JSONObject responseJSON = response.getJSONObject("responseData");
					
					if(responseJSON == null){
						return;	
					}
					
					imageResultsJson = responseJSON.getJSONArray("results");
					
					imageResultsAdapter.addAll(ImageResult.fromJSONArray(imageResultsJson));
					Log.d("SearchActivity", "SIZE NOW ="+imageResultsAdapter.getCount());
					
				}catch(JSONException e){
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				Log.e("SearchActivity", "status code "+statusCode+ " Response :"+responseString);
				Toast.makeText(SearchActivity.this,"Failure in loading images", Toast.LENGTH_SHORT).show();
			}
        });
    }


	@Override
	public void onFilterEdited(ImageFilter filter) {
		imageFilterCrit = filter;
		
		// apply the filter
		String url = ImageQueryBuilder.getPaginatedQueryWithFilters(0, query, filter);
		// apply the filter and clear old data
		imageResultsAdapter.clear();
		loadImages(url);
	}

    
}
