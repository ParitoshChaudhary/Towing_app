package com.example.towing_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Year extends CommonActivity implements OnItemClickListener, OnClickListener {

	ListView yearlist;
	LinearLayout YearHome;
	String carModel,carName;
	ProgressDialog pDialog;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	TextView txt;
	int flag;
	private static String url_yearbymodel = Constents.CONNECT_URL + "APIYearByModels";
	ArrayList<HashMap<String, String>> arraysyear;
	HashMap<String, String> hashyear;
	SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(Year.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(Year.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}
		
		else 
		{
		setContentView(R.layout.activity_year);
		
			txt = (TextView) findViewById(R.id.txtyear);
		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		
		yearlist= (ListView) findViewById(R.id.year_list);
		
		YearHome = (LinearLayout) findViewById(R.id.yera_home);
		YearHome.setOnClickListener(this);
		
		new Allmodels().execute();
		sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedPreferences.edit();
		carName = String.valueOf(sharedPreferences.getString("carName", carName));
		yearlist.setOnItemClickListener(this);
	}
	}

	private class Allmodels extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Year.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input

			carModel = String.valueOf(sharedPreferences.getString("carModel", "Model"));
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("txtModel",carModel));
			
			json = jParse.makeHttpRequest(url_yearbymodel, "POST", params);
			

			try {
				JSONArray jsonary = json.getJSONArray("info");

				arraysyear = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < jsonary.length(); i++) {

					hashyear = new HashMap<String, String>();
					JSONObject local_obj = (JSONObject) jsonary.get(i);
					String carYear = local_obj.getString("carYear");
					
					 
					hashyear.put("carYear", carYear);

					arraysyear.add(hashyear);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("hjghgu=" + e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.cancel();

			MyAdapter adapter = new MyAdapter(Year.this, arraysyear);
			yearlist.setAdapter(adapter);

		}
	}
	
	 @Override
		public void onBackPressed() {
			super.onBackPressed();
			finish();
		
			Intent intent = new Intent(this, Search.class);
			startActivity(intent);
			overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

		}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		HashMap<String, String> selectedImage = arraysyear
                .get(position);
        String carYear = (String) selectedImage.get("carYear");
		Intent modellist = new Intent(this, Search.class);
//		sharedPreferences = getSharedPreferences(
//				AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
//		Editor editor = sharedPreferences.edit();
//		editor.putString("carYear",carYear);
//		editor.commit();
		modellist.putExtra("carYear", carYear);
		modellist.putExtra("carName", carName);
		modellist.putExtra("carModel", carModel);
		startActivity(modellist);
	
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.yera_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;

		default:
			break;
		}
		
	}
}

