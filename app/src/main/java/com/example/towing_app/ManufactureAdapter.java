package com.example.towing_app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ManufactureAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	TextView txt;
	public com.example.lazyloading.ImageLoader imageLoader;



	 public ManufactureAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;

		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new com.example.lazyloading.ImageLoader(activity.getApplicationContext());

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View view = convertView;
		final ViewHolder viewHolder;

		if (convertView == null)


			view = inflater.inflate(R.layout.each_manufacture, null);
		viewHolder = new ViewHolder();
			viewHolder.catlogTitle  = (TextView) view.findViewById(R.id.manufacturetext);

			Typeface typeface1 = Typeface.createFromAsset(activity.getApplicationContext().getAssets(), "fonts/Helvetica.otf");
//					(convertView.getAssets(),"fonts/Existence-Light.otf");
			viewHolder.catlogTitle .setTypeface(typeface1);
//			
			viewHolder.icon = (ImageView) view.findViewById(R.id.logoimage);


			HashMap<String, String> hashmaparray = new HashMap<String, String>();
			hashmaparray = data.get(position);

			if (activity.toString().contains("AllManufacture"))

			{
				viewHolder.catlogTitle .setText(hashmaparray.get("carName"));
				String image =hashmaparray.get("carIcon");

				imageLoader.DisplayImage(image, viewHolder.icon);

			}


		return view;

	}



}
