package com.example.towing_app;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchCar extends CommonActivity implements OnClickListener  {
	ListView carlist;
	TextView txt;
	LinearLayout towinfo, SearchCarHome,serviceinfo,towimages,towlimit;
	String carModel,carName,carYear,curbWeight,carId,frontAxleWight,rearAxleWeight;
	ProgressDialog pDialog;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	int flag;
	private static String url_search = "http://216.224.177.43:8080/TowingApp/Search";
	ArrayList<HashMap<String, String>> arraysyear;
	HashMap<String, String> hashyear;
	SharedPreferences sharedPreferences;
	TextView txtmanufacturename,txtmodelname,txtyearno,txtdrivetrainname,txtcurbweightno,txtfrontweightno,txtrearno;
	TextView txtmanufacture,txtmodel,txtyear,txtcurbweight,txtfrontweight,txtrear,image,txttowinfo,txtserviceinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		
		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(SearchCar.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(SearchCar.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}
		
		else 
		{
		new Allcars().execute();
		sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedPreferences.edit();
		txtserviceinfo = (TextView) findViewById(R.id.txtserviceinfo);
		txtmanufacturename = (TextView) findViewById(R.id.txtmanufacturename);
		txtmodelname = (TextView) findViewById(R.id.txtmodelname);
		txtyearno = (TextView) findViewById(R.id.txtyearno);
		txtcurbweightno = (TextView) findViewById(R.id.txtcurbweightno);
		txtfrontweightno = (TextView) findViewById(R.id.txtfrontweightno);
		txtrearno = (TextView) findViewById(R.id.txtrearno);
		
		txtmanufacture = (TextView) findViewById(R.id.txtmanufacture);
		txtmodel = (TextView) findViewById(R.id.txtmodel);
		txtyear = (TextView) findViewById(R.id.txtyear);
		txtcurbweight = (TextView) findViewById(R.id.txtcurbweight);
		txtfrontweight  = (TextView) findViewById(R.id.txtfrontweight);
		txtrear = (TextView) findViewById(R.id.txtrearweight);
		txttowinfo = (TextView) findViewById(R.id.txttowinfo);
		image = (TextView) findViewById(R.id.image);
		towimages = (LinearLayout) findViewById(R.id.towimages);
		serviceinfo = (LinearLayout) findViewById(R.id.serviceinfo);
			towlimit = (LinearLayout) findViewById(R.id.towlimits);
		
		Typeface typeface2 = Typeface.createFromAsset(getAssets(),
				"fonts/Helvetica.otf");
		txtmanufacturename.setTypeface(typeface2,Typeface.BOLD);
		txtmodelname.setTypeface(typeface2,Typeface.BOLD);
		txtyearno.setTypeface(typeface2,Typeface.BOLD);
		txtcurbweightno.setTypeface(typeface2,Typeface.BOLD);
		txtfrontweightno.setTypeface(typeface2,Typeface.BOLD);
		txtrearno.setTypeface(typeface2,Typeface.BOLD);
		txtmanufacture.setTypeface(typeface2,Typeface.BOLD);
		txtmodel.setTypeface(typeface2,Typeface.BOLD);
		txtyear.setTypeface(typeface2,Typeface.BOLD);
		txtcurbweight.setTypeface(typeface2,Typeface.BOLD);
		txtfrontweight.setTypeface(typeface2,Typeface.BOLD);
		txtrear.setTypeface(typeface2,Typeface.BOLD);
		txttowinfo.setTypeface(typeface2,Typeface.BOLD);
		image.setTypeface(typeface2,Typeface.BOLD);
		txtserviceinfo.setTypeface(typeface2,Typeface.BOLD);
		
		towinfo= (LinearLayout) findViewById(R.id.towinfo);
		towinfo.setOnClickListener(this);
		
		SearchCarHome = (LinearLayout) findViewById(R.id.search_car_home);
		SearchCarHome.setOnClickListener(this);
		
		txt = (TextView) findViewById(R.id.txtallmodels);
		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		 towimages.setOnClickListener(this);
		 serviceinfo.setOnClickListener(this);
			towlimit.setOnClickListener(this);
		
		
	}
	}

	private class Allcars extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SearchCar.this);
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

					hashyear = new HashMap<String, String>();
					 carName = jObj.getString("carName");
					 curbWeight = jObj.getString("curbWeight");
					 carYear = jObj.getString("carYear");
					carId = jObj.getString("carId");
					 carModel = jObj.getString("carModel");
					 frontAxleWight = jObj.getString("frontAxleWight");
					 rearAxleWeight = jObj.getString("rearAxleWeight");
					 
					 sharedPreferences = getSharedPreferences(
								AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
						Editor editor = sharedPreferences.edit();
						editor.putString("carId",carId);
						editor.commit();
					
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
				txtmanufacturename.setText(carName);
				txtmodelname.setText(carModel);
				txtyearno.setText(carYear);
				txtfrontweightno.setText(frontAxleWight);
				txtrearno.setText(rearAxleWeight);
				txtcurbweightno.setText(curbWeight);
				
				
			
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


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	 switch (v.getId()) {
	case R.id.towinfo:
		Intent towinfo = new Intent(this,TextTowinfo.class);
		startActivity(towinfo);
		
		break;
		
	case R.id.search_car_home:
		Intent home = new Intent(this, MainActivity.class);
		startActivity(home);
		overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
		break;

	case R.id.towimages:
		Intent image = new Intent(this, TowImages.class);
		startActivity(image);
		overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
		break;
		
	case R.id.serviceinfo:
		Intent serviceinfo = new Intent(this, ServiceInfo.class);
		startActivity(serviceinfo);
		overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
		break;

		 case R.id.towlimits:
			 Intent towlimit = new Intent(this, TowLimits.class);
			 startActivity(towlimit);
			 overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			 break;

	default:
		break;
	}
		
	}

}
