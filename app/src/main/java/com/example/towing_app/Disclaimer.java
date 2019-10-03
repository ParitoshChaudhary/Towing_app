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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Disclaimer extends CommonActivity implements OnClickListener {
	Button agree;
	CheckBox checkBox;
	TextView disclaimer;
	ProgressDialog pDialog;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	ExpandableListView exp;
	ExpandableListAdapter listAdapter;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	SharedPreferences sharedPreferences;
	int flag;
	private static String url_fornet = "http://216.224.177.43:8080/TowingApp/HelloServlet";
	TextView trademarks, copyright, general, termination, liability, unlawful, links, modification, info;
	String strtrademarks, strcopyright, strgeneral, strtermination, strliability, strunlawful, strlinks, strmodification, strinfo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		if (!isNetworkAvailable()) {
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(Disclaimer.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(Disclaimer.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		} else {
			setContentView(R.layout.activity_disclaimer);
			agree = (Button) findViewById(R.id.agree);
			checkBox = (CheckBox) findViewById(R.id.checkbox);
			disclaimer = (TextView) findViewById(R.id.txtDisclaimer);
			agree.setOnClickListener(this);
			new Net().execute();
			Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/Existence-Light.otf");
			disclaimer.setTypeface(typeface1, Typeface.BOLD);
			trademarks = (TextView) findViewById(R.id.trademarks);
			copyright = (TextView) findViewById(R.id.copyright);
			general = (TextView) findViewById(R.id.general);
			termination = (TextView) findViewById(R.id.termination);
			liability = (TextView) findViewById(R.id.liability);
			unlawful = (TextView) findViewById(R.id.unlawful);
			links = (TextView) findViewById(R.id.links);
			modification = (TextView) findViewById(R.id.modification);
			info = (TextView) findViewById(R.id.info);

			Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/Helvetica.otf");

			trademarks.setTypeface(typeface2);
			copyright.setTypeface(typeface2);
			general.setTypeface(typeface2);
			termination.setTypeface(typeface2);
			liability.setTypeface(typeface2);
			unlawful.setTypeface(typeface2);
			links.setTypeface(typeface2);
			modification.setTypeface(typeface2);
			info.setTypeface(typeface2);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (checkBox.isChecked()) {

			Intent in = new Intent(this, MainActivity.class);
			startActivity(in);
		} else {

			new AlertDialog.Builder(Disclaimer.this)
					.setTitle("Message")
					.setMessage("Please Accept the terms and Conditions")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog,
										int which) {
								}
							})
					.show();

		}

	}

	private class Net extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Disclaimer.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			json = jParse.makeHttpRequest(url_fornet, "POST", params);

			try {
				// JSONArray jsonary = json.getJSONArray("info");

				JSONObject jObj = json.getJSONObject("info");


				if (jObj.equals("fail")) {
					flag = 0;
				} else {
					flag = 1;


					if (jObj.toString().contains("termination")) {
						strtermination = jObj.getString("termination");

					}

					if (jObj.toString().contains("copyright")) {
						strcopyright = jObj.getString("copyright");

					}

					if (jObj.toString().contains("info"))

					{

						strinfo = jObj.getString("info");

					}

					if (jObj.toString().contains("trademarks"))

					{

						strtrademarks = jObj.getString("trademarks");

					}

					if (jObj.toString().contains("modification"))

					{

						strmodification = jObj.getString("modification");

					}

					if (jObj.toString().contains("links"))

					{

						strlinks = jObj.getString("links");

					}

					if (jObj.toString().contains("unlawful"))

					{

						strunlawful = jObj.getString("unlawful");

					}

					if (jObj.toString().contains("liability"))

					{

						strliability = jObj.getString("liability");

					}

					if (jObj.toString().contains("general"))

					{

						strgeneral = jObj.getString("general");

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

				trademarks.setText(strtrademarks);
				copyright.setText(strcopyright);
				general.setText(strgeneral);
				termination.setText(strtermination);
				liability.setText(strliability);
				unlawful.setText(strunlawful);
				links.setText(strlinks);
				modification.setText(strmodification);
				info.setText(strinfo);


			}
		}


	}
}
