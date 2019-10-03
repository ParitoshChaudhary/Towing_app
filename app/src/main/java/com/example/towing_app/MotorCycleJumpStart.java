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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MotorCycleJumpStart extends Activity implements OnClickListener{
	
	TextView txt,text;
	LinearLayout Motorhome;
	private static String url_specialoil = "http://216.224.177.43:8080/TowingApp/APIMotorCycleJumpStartingPrecautions";
	ProgressDialog pDialog;
	int flag;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	String info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_motorjump);
		
		txt = (TextView) findViewById(R.id.txtstandardequipment);
		text = (TextView) findViewById(R.id.text);
		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		
		Motorhome = (LinearLayout) findViewById(R.id.motor_home);
		Motorhome.setOnClickListener(this);
		Typeface typeface2 = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
		 text.setTypeface(typeface2);
		new motorinfo().execute();
		 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.motor_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;

		default:
			break;
		}
		
	}

	private class motorinfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MotorCycleJumpStart.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input



			List<NameValuePair> params = new ArrayList<NameValuePair>();

			json = jParse.makeHttpRequest(url_specialoil, "POST", params);

			try {
				// JSONArray jsonary = json.getJSONArray("info");

				JSONObject jObj = json.getJSONObject("info");


				if (jObj.equals("fail")) {
					flag = 0;
				} else {
					flag = 1;



					if (jObj.toString().contains("info"))
					{
						info = jObj.getString("info");
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

			if (flag == 0) {

			} else {


				text.setText(info);






			}
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
