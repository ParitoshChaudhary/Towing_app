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

public class SpecialOil extends Activity implements OnClickListener {

	TextView txt,text;
	LinearLayout SplOil;
	private static String url_specialoil = Constents.CONNECT_URL + "APISpecialOilPan";
	ProgressDialog pDialog;
	int flag;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	String info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sploil);

		txt = (TextView) findViewById(R.id.txtstandardequipment);

		Typeface typeface1 = Typeface.createFromAsset(getAssets(),
				"fonts/Existence-Light.otf");
		txt.setTypeface(typeface1, Typeface.BOLD);
		
		SplOil = (LinearLayout) findViewById(R.id.spl_home);
		SplOil.setOnClickListener(this);
		text = (TextView) findViewById(R.id.text);
		Typeface typeface2 = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
		text.setTypeface(typeface2);
		new specialoil().execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.spl_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;

		default:
			break;
		}
		
	}

	private class specialoil extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SpecialOil.this);
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
