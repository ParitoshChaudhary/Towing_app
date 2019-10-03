package com.example.towing_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllManufacture extends CommonActivity implements OnItemClickListener, OnClickListener {
	ListView manufacture_list;
	TextView Manufacturer;
	LinearLayout Manfac_home;
	ProgressDialog pDialog;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	int flag;
	private static String url_allmodels = "http://216.224.177.43:8080/TowingApp/APIAllModels";
	ArrayList<HashMap<String, String>> arraysmodels;
	HashMap<String, String> hashmodels;
	SharedPreferences sharedPreferences;
	public static final String MyPREFERENCES = "MyPREFERENCES";
	Boolean isInternetPresent = false;
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(AllManufacture.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(AllManufacture.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}
		
		else{
			setContentView(R.layout.activity_all_manufacture);
		
		new Allmanufactures().execute();
		manufacture_list = (ListView) findViewById(R.id.manufacture_list);
		manufacture_list.setOnItemClickListener(this);
		
		Manfac_home = (LinearLayout) findViewById(R.id.manufac_home);
		Manfac_home.setOnClickListener(this);
		
		Manufacturer = (TextView) findViewById(R.id.txtstandardequipment);
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 Manufacturer.setTypeface(typeface1, Typeface.BOLD);

		
		}
	}

	private class Allmanufactures extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AllManufacture.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			json = jParse.makeHttpRequest(url_allmodels, "POST", params);

			try {
				JSONArray jsonary = json.getJSONArray("info");

				arraysmodels = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < jsonary.length(); i++) {

					hashmodels = new HashMap<String, String>();


					JSONObject local_obj = (JSONObject) jsonary.get(i);

					if (local_obj.toString().contains("carName")) {
						String carName = local_obj.getString("carName");
						hashmodels.put("carName", carName);
					}

					if (local_obj.toString().contains("carIcon")) {
						String carIcon = local_obj.getString("carIcon");
						hashmodels.put("carIcon", carIcon);

					}



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

			ManufactureAdapter adapter = new ManufactureAdapter(AllManufacture.this, arraysmodels);
			manufacture_list.setAdapter(adapter);

		}
	}
	
	 @Override
		public void onBackPressed() {
		 super.onBackPressed();
			finish();
		
			Intent intent = new Intent(AllManufacture.this, Search.class);
			startActivity(intent);
		 overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

		}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		HashMap<String, String> selectedImage = arraysmodels
                .get(position);
        String carName = (String) selectedImage.get("carName");
		Intent imagelist = new Intent(this, Search.class);
//		sharedPreferences = getSharedPreferences(
//				AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
//		Editor editor = sharedPreferences.edit();
//		editor.putString("carName", carName);
//		editor.commit();
		
		imagelist.putExtra("carName", carName);
		startActivity(imagelist);

		
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.manufac_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;

		default:
			break;
		}
		
	}
	
}
