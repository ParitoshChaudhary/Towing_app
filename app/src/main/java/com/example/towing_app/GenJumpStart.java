package com.example.towing_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GenJumpStart extends Activity implements OnClickListener{
	
	TextView txt,info,textcol8,textcol9;
	LinearLayout GenJumpStart,jsprecaution;
	private static String url_jump = "http://216.224.177.43:8080/TowingApp/APIGeneralJumpStartingInstructions";
	ProgressDialog pDialog;
	int flag;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	String column1,column2,Info,information;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genjumpstart);
		
		txt = (TextView) findViewById(R.id.txtstandardequipment);
		jsprecaution = (LinearLayout) findViewById(R.id.jsprecaution);
		info = (TextView) findViewById(R.id.info);
		textcol8 = (TextView) findViewById(R.id.textcol8);
		textcol9 = (TextView) findViewById(R.id.textcol9);

		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		 
		 GenJumpStart = (LinearLayout) findViewById(R.id.jump_gen_home);
		 GenJumpStart.setOnClickListener(this);
		 
		 Typeface typeface2 = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");

		txt.setTypeface(typeface2);
				info.setTypeface(typeface2);
				textcol8.setTypeface(typeface2);
				textcol9.setTypeface(typeface2);


		new jumpstart().execute();

		jsprecaution.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent in = new Intent(GenJumpStart.this, JSPrecaution.class);
				startActivity(in);
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.jump_gen_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;

		default:
			break;
		}
		
	}

	private class jumpstart extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(GenJumpStart.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input



			List<NameValuePair> params = new ArrayList<NameValuePair>();

			json = jParse.makeHttpRequest(url_jump, "POST", params);

			try {
				// JSONArray jsonary = json.getJSONArray("info");



				JSONArray jsonary = json.getJSONArray("info");


				for (int i = 0; i < jsonary.length(); i++) {

					JSONObject local_obj = (JSONObject) jsonary.get(i);
					String jsd = local_obj.getString("jsId");

					if (jsd.equals("5"))
					{
						information = local_obj.getString("info");
						column1 = local_obj.getString("column1");
						column2 = local_obj.getString("column2");
					}
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



				textcol8.setText(column1);
				textcol9.setText(column2);
			info.setText(information);







			}
		}


	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

	}

}
