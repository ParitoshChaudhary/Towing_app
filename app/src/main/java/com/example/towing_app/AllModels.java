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

public class AllModels extends CommonActivity implements OnItemClickListener, OnClickListener {
	ListView models_list;
	LinearLayout Model_home;
	String carName;
	TextView txt;
	ProgressDialog pDialog;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	int flag;
	private static String url_allmodels = "http://216.224.177.43:8080/TowingApp/APIModelsByManufaturer";
	ArrayList<HashMap<String, String>> arraysmodels;
	HashMap<String, String> hashmodels;
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_models);
		models_list= (ListView) findViewById(R.id.models_list);
		
		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(AllModels.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(AllModels.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}
		
		else {
		
		txt = (TextView) findViewById(R.id.txtallmodels);
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		 
		 Model_home = (LinearLayout) findViewById(R.id.model_home);
		 Model_home.setOnClickListener(this);
		
		new Allmodels().execute();
		sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedPreferences.edit();
		models_list.setOnItemClickListener(this);
		}
		
		
	}

	private class Allmodels extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AllModels.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input

			carName = String.valueOf(sharedPreferences.getString("carName", "Model"));
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("txtManufacturer",carName));
			
			json = jParse.makeHttpRequest(url_allmodels, "POST", params);
			

			try {
				JSONArray jsonary = json.getJSONArray("info");

				arraysmodels = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < jsonary.length(); i++) {

					hashmodels = new HashMap<String, String>();
					JSONObject local_obj = (JSONObject) jsonary.get(i);
					String carModel = local_obj.getString("carModel");
					
					 
					hashmodels.put("carModel", carModel);

					arraysmodels.add(hashmodels);

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

			MyAdapter adapter = new MyAdapter(AllModels.this, arraysmodels);
			models_list.setAdapter(adapter);

		}
	}
	
	 @Override
		public void onBackPressed() {
			super.onBackPressed();
			finish();
		
			Intent intent = new Intent(AllModels.this, Search.class);
			  
			   startActivity(intent);
			   overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

		}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		HashMap<String, String> selectedImage = arraysmodels
                .get(position);
        String carModel = (String) selectedImage.get("carModel");
		Intent modellist = new Intent(this, Search.class);
//		sharedPreferences = getSharedPreferences(
//				AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
//		Editor editor = sharedPreferences.edit();
//		editor.putString("carModel",carModel);
//		editor.commit();
		modellist.putExtra("carModel", carModel);
		modellist.putExtra("carName", carName);
		startActivity(modellist);
	
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.model_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;

		default:
			break;
		}
		
	}
}
