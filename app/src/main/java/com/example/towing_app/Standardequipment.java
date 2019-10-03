package com.example.towing_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Standardequipment extends CommonActivity implements OnClickListener {
	ImageView image;
	TextView txt,text;
	LinearLayout StandardHome, MyImages;
	private static String url_standard = "http://216.224.177.43:8080/TowingApp/APIStandardEquipment";
	ProgressDialog pDialog;
	int flag;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	String info;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standardequipment);
		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(Standardequipment.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(Standardequipment.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}
		
		else 
		{
		
		txt = (TextView) findViewById(R.id.txtstandardequipment);
		text = (TextView) findViewById(R.id.text);
		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		
		StandardHome = (LinearLayout) findViewById(R.id.stand_home);
		StandardHome.setOnClickListener(this);
		
		MyImages = (LinearLayout) findViewById(R.id.my_images);
		MyImages.setOnClickListener(this);
		
		Typeface typeface2 = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
		text.setTypeface(typeface2);
			new standard().execute();
	}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.stand_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;
			
		case R.id.my_images:
			
			Intent images = new Intent(this, Myimages.class);
			startActivity(images);
			overridePendingTransition(R.anim.bottom_in, R.anim.left_out);

		default:
			break;
		}
		
	}

	private class standard extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Standardequipment.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input



			List<NameValuePair> params = new ArrayList<NameValuePair>();

			json = jParse.makeHttpRequest(url_standard, "POST", params);

			try {
				// JSONArray jsonary = json.getJSONArray("info");

				JSONObject jObj = json.getJSONObject("info");


				if (jObj.equals("fail")) {
					flag = 0;
				} else {
					flag = 1;



					if (jObj.toString().contains("txtInfo"))
					{
						info = jObj.getString("txtInfo");
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
