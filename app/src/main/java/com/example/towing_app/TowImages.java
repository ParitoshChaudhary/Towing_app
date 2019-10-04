package com.example.towing_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TowImages extends CommonActivity  {

	ImageView Image1, Image2;

	private static String url_CarTowImage = Constents.CONNECT_URL + "APIGetTowImageByCarId";
	ProgressDialog pDialog;
	String img1, img2;

	JSONParser jParse = new JSONParser();
	JSONObject json;

	SharedPreferences sharedPreferences;
	String carId;

	public com.example.lazyloading.ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!isNetworkAvailable()) {
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(TowImages.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(TowImages.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		} else {
			setContentView(R.layout.activity_tow_images);

			Image1 = (ImageView) findViewById(R.id.image1);
			Image2 = (ImageView) findViewById(R.id.image2);

			new GetTowImages().execute();

		}
	}

	private class GetTowImages extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TowImages.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("txtCarId", "1"));

			json = jParse.makeHttpRequest(url_CarTowImage, "POST", params);

			try {

				JSONObject jObj = json.getJSONObject("info");

				if (jObj.toString().contains("txtImage1"))
				{
					img1 = jObj.getString("txtImage1");
				}

				if (jObj.toString().contains("txtImage2")){
					img2 = jObj.getString("txtImage2");
				}
			}

			catch (Exception e){
				System.out.println("Execption : " +e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.cancel();

			imageLoader = new com.example.lazyloading.ImageLoader(
					getApplicationContext());
			imageLoader.DisplayImage(img1, Image1);
			imageLoader.DisplayImage(img2, Image2);

		}

	}

}
