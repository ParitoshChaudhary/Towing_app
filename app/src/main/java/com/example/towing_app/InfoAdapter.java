package com.example.towing_app;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InfoAdapter  extends BaseAdapter{
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	TextView txt;
	private AsyncTask<String, String, String> execute;
	
	
	public InfoAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;

		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
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
		if (convertView == null)
		
			view = inflater.inflate(R.layout.each_elements, null);
			txt = (TextView) view.findViewById(R.id.textelement);
			
			HashMap<String, String> hashmaparray = new HashMap<String, String>();
			hashmaparray = data.get(position);
			
		
			
//			 if (activity.toString().contains("SearchCar"))
//			{
//				txt.setText(hashmaparray.get("carName"));
//			}
	
	
		return view;
	
	}


}
