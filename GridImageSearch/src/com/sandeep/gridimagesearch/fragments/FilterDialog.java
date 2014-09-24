package com.sandeep.gridimagesearch.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.sandeep.gridimagesearch.R;
import com.sandeep.gridimagesearch.models.ImageFilter;

public class FilterDialog extends DialogFragment {

	private Spinner spColorView;
	private ArrayAdapter<CharSequence> adapterColorView;

	private Spinner spTypeView;
	private ArrayAdapter<CharSequence> adapterTypeView;

	private Spinner spSizeView;
	private ArrayAdapter<CharSequence> adapterSizeView;

	private EditText etSiteView;
	private OnFilterEditedListener listener;

	public interface OnFilterEditedListener {
		public void onFilterEdited(ImageFilter filter);
	}

	public FilterDialog() {
	}

	public static FilterDialog newInstance(ImageFilter filter) {
		FilterDialog frag = new FilterDialog();
		
		frag.setStyle(R.style.cust_dialog,R.style.cust_dialog);
		
		Bundle args = new Bundle();
		args.putSerializable("filter", filter);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnFilterEditedListener) {
			listener = (OnFilterEditedListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement FilterDialog.OnFilterEditedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.filter_fragment, container);

		setupViews(view);

		ImageFilter filter = (ImageFilter) getArguments().getSerializable(
				"filter");
		getDialog().setTitle("Advanced Filters");

		if (filter.color != null) {
			spColorView
					.setSelection(adapterColorView.getPosition(filter.color));
			adapterColorView.notifyDataSetChanged();
		}

		if (filter.type != null) {
			spTypeView.setSelection(adapterTypeView.getPosition(filter.type));
			adapterTypeView.notifyDataSetChanged();
		}

		if (filter.size != null) {
			spSizeView.setSelection(adapterSizeView.getPosition(filter.size));
			adapterSizeView.notifyDataSetChanged();
		}

		if (filter.site != null)
			etSiteView.setText(filter.site);

		// Show soft keyboard automatically
		etSiteView.requestFocus();

		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}

	private void setupViews(View view) {

		// setting up spinners
		spColorView = (Spinner) view.findViewById(R.id.spImageColor);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		adapterColorView = ArrayAdapter.createFromResource(this.getActivity(),
				R.array.Color_filter, R.layout.filter_textview);

		// Apply the adapter to the spinner
		spColorView.setAdapter(adapterColorView);

		spTypeView = (Spinner) view.findViewById(R.id.spImageType);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		adapterTypeView = ArrayAdapter.createFromResource(this.getActivity(),
				R.array.Type, R.layout.filter_textview);

		// Apply the adapter to the spinner
		spTypeView.setAdapter(adapterTypeView);

		spSizeView = (Spinner) view.findViewById(R.id.spImageSize);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		adapterSizeView = ArrayAdapter.createFromResource(this.getActivity(),
				R.array.Size, R.layout.filter_textview);

		// Apply the adapter to the spinner
		spSizeView.setAdapter(adapterSizeView);

		etSiteView = (EditText) view.findViewById(R.id.etSiteFIlter);
		etSiteView.setTextColor(getResources().getColor(R.color.red));

		Button saveButton = (Button) view.findViewById(R.id.btFilterSave);
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ImageFilter filter = new ImageFilter();
				filter.color = (String) spColorView.getSelectedItem();
				filter.type = (String) spTypeView.getSelectedItem();
				filter.site = etSiteView.getText().toString();
				filter.size = (String) spSizeView.getSelectedItem();

				listener.onFilterEdited(filter);
				getDialog().cancel();
			}
		});

		Button cancelButton = (Button) view.findViewById(R.id.btFilterCancel);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().cancel();
			}
		});
	}

}
