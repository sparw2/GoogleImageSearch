package com.sandeep.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.sandeep.gridimagesearch.R;
import com.sandeep.gridimagesearch.models.ImageFilter;

public class FilterActivity extends Activity {

	private Spinner spColorView;
	private ArrayAdapter<CharSequence> adapterColorView;
	
	private Spinner spTypeView;
	private ArrayAdapter<CharSequence> adapterTypeView;
	
	private Spinner spSizeView;
	private ArrayAdapter<CharSequence> adapterSizeView;
	
	private EditText etSiteView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		
		setupViews();
		
		// set the filter criterion chosen previously
		ImageFilter filter = (ImageFilter)getIntent().getSerializableExtra("filter");
	
		if (filter.color != null)
			spColorView.setSelection(adapterColorView.getPosition(filter.color));
		
		if (filter.type != null)
			spTypeView.setSelection(adapterTypeView.getPosition(filter.type));
		
		if (filter.size != null)
			spSizeView.setSelection(adapterSizeView.getPosition(filter.size));
		
		if (filter.site != null)
			etSiteView.setText(filter.site);
	}
	
	private void setupViews(){
		
		// setting up spinners
		spColorView = (Spinner) findViewById(R.id.spImageColor);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapterColorView = ArrayAdapter.createFromResource(this,
		        R.array.Color_filter, android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapterColorView.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		spColorView.setAdapter(adapterColorView);
		
		spTypeView = (Spinner) findViewById(R.id.spImageType);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapterTypeView = ArrayAdapter.createFromResource(this,
		        R.array.Type, android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapterTypeView.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		spTypeView.setAdapter(adapterTypeView);
		
		spSizeView = (Spinner) findViewById(R.id.spImageSize);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapterSizeView = ArrayAdapter.createFromResource(this,
		        R.array.Size, android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapterSizeView.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		spSizeView.setAdapter(adapterSizeView);
		
		etSiteView = (EditText) findViewById(R.id.etSiteFIlter);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter, menu);
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
	
	public void saveFilter(View view){
		
		ImageFilter filter = new ImageFilter();
		filter.color = (String)spColorView.getSelectedItem();
		filter.type = (String)spTypeView.getSelectedItem();
		filter.site = etSiteView.getText().toString();
		filter.size = (String)spTypeView.getSelectedItem();
		
		
		Intent data = new Intent();
		data.putExtra("filter", filter);
		
		setResult(RESULT_OK, data);
		
		finish();
	}
}
