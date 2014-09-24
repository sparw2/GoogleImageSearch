package com.sandeep.gridimagesearch.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import com.sandeep.gridimagesearch.R;
import com.sandeep.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends Activity {

	private ImageView ivDisplayImageView;

	private ShareActionProvider miShareAction;
	private Intent shareIntent; 
	
	public void onShareItem(MenuItem item) {
		startActivity(Intent.createChooser(shareIntent, "Share Image"));
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		
		final ImageResult result = (ImageResult)getIntent().getExtras().getSerializable("result");
		
		ivDisplayImageView = (ImageView)findViewById(R.id.ivImageResult);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
	
		ivDisplayImageView.setImageResource(0);
		Picasso.with(ImageDisplayActivity.this).load(result.fullUrl).resize(size.x,
			size.y).into(ivDisplayImageView, new Callback() {
		        @Override
		        public void onSuccess() {
		            // Setup share intent now that image has loaded
		            setupShareIntent();
		        }
		        
		        @Override
		        public void onError() { 
		            
		        }
		   });
  
	}

	// Gets the image URI and setup the associated share intent to hook into the provider
	public void setupShareIntent() {
	    // Fetch Bitmap Uri locally
	    ImageView ivImage = (ImageView) findViewById(R.id.ivImageResult);
	    Uri bmpUri = getLocalBitmapUri(ivImage); // see previous remote images section
	    // Create share intent as described above
	    shareIntent = new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
	    shareIntent.setType("image/*");
	    // Attach share event to the menu item provider
	    miShareAction.setShareIntent(shareIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		
		 // Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_item_share);
	    
	    // Fetch reference to the share action provider
	    miShareAction = (ShareActionProvider) item.getActionProvider();
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	// Returns the URI path to the Bitmap displayed in specified ImageView
	public Uri getLocalBitmapUri(ImageView imageView) {
	    // Extract Bitmap from ImageView drawable
	    Drawable drawable = imageView.getDrawable();
	    Bitmap bmp = null;
	    if (drawable instanceof BitmapDrawable){
	       bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
	    } else {
	       return null;
	    }
	    // Store image to default external storage directory
	    Uri bmpUri = null;
	    try {
	        File file =  new File(Environment.getExternalStoragePublicDirectory(  
		        Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
	        file.getParentFile().mkdirs();
	        FileOutputStream out = new FileOutputStream(file);
	        bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
	        out.close();
	        bmpUri = Uri.fromFile(file);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return bmpUri;
	}
}
