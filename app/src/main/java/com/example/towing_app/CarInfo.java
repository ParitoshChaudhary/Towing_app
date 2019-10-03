package com.example.towing_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarInfo extends CommonActivity implements OnClickListener {
	ImageView carimage;
	ProgressDialog pDialog;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	int flag;
	ImageView imghome;
	private static String url_search = "http://216.224.177.43:8080/TowingApp/Search";
	SharedPreferences sharedPreferences;
	String carModel,carName,carYear,image,list;
	TextView manufacturename, modelname,year,Textselect,cautiontext,caution,txtmanufacture,txtmodel,txtyear;
	Button ok;
	Bitmap myBitMap;
	Drawable drawable;
	public com.example.lazyloading.ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_info);
		carimage = (ImageView) findViewById(R.id.carimage);
		
		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(CarInfo.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(CarInfo.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}
		
		else {
		sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedPreferences.edit();
		carModel = String.valueOf(sharedPreferences.getString("carModel", "Model"));
		carName = String.valueOf(sharedPreferences.getString("carName", "Model"));
		carYear = String.valueOf(sharedPreferences.getString("carYear", "carYear"));
		manufacturename = (TextView) findViewById(R.id.manufacturename);
		modelname = (TextView) findViewById(R.id.modelname);
		year = (TextView) findViewById(R.id.year);
		manufacturename.setText(carName);
		modelname.setText(carModel);
		year.setText(carYear);
		ok = (Button) findViewById(R.id.Ok);
		ok.setOnClickListener(this);
		new Allcars().execute();
		Textselect = (TextView) findViewById(R.id.txtselect);
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		Textselect.setTypeface(typeface1, Typeface.BOLD);
		imghome = (ImageView) findViewById(R.id.imghome);
		imghome.setOnClickListener(this);
		cautiontext = (TextView) findViewById(R.id.cautiontext);
			caution = (TextView) findViewById(R.id.caution);
			txtmanufacture = (TextView) findViewById(R.id.txtmanufacture);
			txtmodel = (TextView) findViewById(R.id.txtmodel);
			txtyear = (TextView) findViewById(R.id.txtYear);


			Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
			manufacturename.setTypeface(typeface, Typeface.BOLD);
			modelname.setTypeface(typeface, Typeface.BOLD);
			year.setTypeface(typeface, Typeface.BOLD);
			cautiontext.setTypeface(typeface, Typeface.BOLD);



		if (carModel.contains("AWD"))
		{
			cautiontext.setText("Confirm this Vehicle is All-Wheel drive.");

		}

		if (carModel.contains("FWD"))
		{
			cautiontext.setText("Confirm this Vehicle is Front-Wheel drive.");

		}

		if (carModel.contains("RWD"))
		{
			cautiontext.setText("Confirm this Vehicle is Rear-Wheel drive.");

		}

		if (carModel.contains("4WD"))
		{
			cautiontext.setText("Confirm this Vehicle is Four-Wheel drive.");
		}

	}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
//		Intent in = new Intent (this, SearchCar.class);
//		startActivity(in);
        
		switch (v.getId()) {
		case R.id.Ok:

			if (list == null) {

				Intent intent = new Intent(this, SearchCar.class);
				this.startActivity(intent);
			}

			else
			{
				Intent intent = new Intent(this, Details_table.class);
				this.startActivity(intent);
			}
	        
			break;
			
case R.id.imghome:
			
	Intent in = new Intent(CarInfo.this,MainActivity.class);
	startActivity(in);
			break;

		default:
			break;
		}
		
        
        
	}

	private class Allcars extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CarInfo.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input

			carModel = String.valueOf(sharedPreferences.getString("carModel", "Model"));
			carName = String.valueOf(sharedPreferences.getString("carName", "Model"));
			carYear = String.valueOf(sharedPreferences.getString("carYear", "carYear"));
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("txtManufacturer",carName));
			params.add(new BasicNameValuePair("txtModel",carModel));
			params.add(new BasicNameValuePair("txtYear",carYear));
			
			json = jParse.makeHttpRequest(url_search, "POST", params);
			

			try {
//				JSONArray jsonary = json.getJSONArray("info");
				
				JSONObject jObj = json.getJSONObject("info");


				if (jObj.equals("fail")) {
					flag = 0;
				} else {
					flag = 1;

					if (jObj.toString().contains("carImage"))
					{
					image = jObj.getString("carImage");
					}

					if (jObj.toString().contains("lst"))
					{
						list = jObj.getString("lst");
					}

//					if (jObj.toString().contains("lst"))
//					{
//
//
//					}
//					if (image != null) {
//
//						String src = image;
//						try {
//							URL url = new URL(src);
//							HttpURLConnection connection = (HttpURLConnection) url
//									.openConnection();
//							connection.setDoInput(true);
//							connection.connect();
//							InputStream input = connection.getInputStream();
//							myBitMap = BitmapFactory.decodeStream(input);
//
//							drawable = new BitmapDrawable(myBitMap);
//
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//					
					
//					try {
//						byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
//						myBitMap = BitmapFactory.decodeByteArray(encodeByte, 0,
//								encodeByte.length);
//
//					} catch (Exception e) {
//						e.getMessage();

//					}
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

				 imageLoader = new com.example.lazyloading.ImageLoader(
				 getApplicationContext());
				 imageLoader.DisplayImage(image, carimage);
//				carimage.setImageBitmap(myBitMap);
				
			
		}
	}

	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();

		Intent intent = new Intent(CarInfo.this, Search.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

	}
}
