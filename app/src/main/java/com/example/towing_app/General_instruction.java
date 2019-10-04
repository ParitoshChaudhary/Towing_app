package com.example.towing_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class General_instruction extends Activity implements OnClickListener {
	
	TextView txt,text;
	LinearLayout Gen_home;

	private static String url_genralinstruction = Constents.CONNECT_URL + "APIGeneralTowingInstructions";
	ProgressDialog pDialog;
	int flag;
	JSONParser jParse = new JSONParser();
	JSONObject json;
	String info;

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	SharedPreferences sharedPreferences;
     String wheelequipment,carequipment,towinglockedvehicle,towinglightduty,groundclearances,towingairbags,calculatingmaxloadforsafesteering,calculatingmaxloadforrearaxle,grossvehicleweight,safetytips,reartowingprecaution;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_general_instruction);
		
		txt = (TextView) findViewById(R.id.txtstandardequipment);
		text = (TextView) findViewById(R.id.text);
		
		Gen_home = (LinearLayout) findViewById(R.id.gen_home);
		Gen_home.setOnClickListener(this);
		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		 
		 Typeface typeface2 = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
//		 text.setTypeface(typeface2, Typeface.BOLD);
		new Instruction().execute();
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.gen_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;

		default:
			break;
		}
		
	}
	private class Instruction extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(General_instruction.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input



			List<NameValuePair> params = new ArrayList<NameValuePair>();


			json = jParse.makeHttpRequest(url_genralinstruction, "POST", params);

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

					if (jObj.toString().contains("wheelLeftEquipment"))
					{

						wheelequipment = jObj.getString("wheelLeftEquipment");
					}

					if (jObj.toString().contains("carCarrierEquipment"))
					{

						carequipment = jObj.getString("carCarrierEquipment");
					}

					if (jObj.toString().contains("towingLockedVehicles"))
					{

						towinglockedvehicle = jObj.getString("towingLockedVehicles");
					}

					if (jObj.toString().contains("groundClearancesWhileTowing"))
					{
						groundclearances = jObj.getString("groundClearancesWhileTowing");
					}

					if (jObj.toString().contains("towingLightDutyCommercialVehicles"))
					{

						towinglightduty = jObj.getString("towingLightDutyCommercialVehicles");
					}


					if (jObj.toString().contains("towingAirbagEquippedVehicles"))
					{

						towingairbags = jObj.getString("towingAirbagEquippedVehicles");
					}


					if (jObj.toString().contains("calculatingMaxLoadForSafeSteering"))
					{

						calculatingmaxloadforsafesteering = jObj.getString("calculatingMaxLoadForSafeSteering");
					}


					if (jObj.toString().contains("calculatingMaxLoadForTruckRearAxle"))
					{

						 calculatingmaxloadforrearaxle= jObj.getString("calculatingMaxLoadForTruckRearAxle");
					}


					if (jObj.toString().contains("grossVehicleWeightRating"))
					{

						grossvehicleweight = jObj.getString("grossVehicleWeightRating");
					}


					if (jObj.toString().contains("safetyTips"))
					{

						safetytips = jObj.getString("safetyTips");
					}


					if (jObj.toString().contains("rearTowingPrecautions"))
					{

						reartowingprecaution = jObj.getString("rearTowingPrecautions");
					}



					sharedPreferences = getSharedPreferences(
							AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("wheelequipment", wheelequipment);
					editor.putString("info", info);
					editor.putString("carequipment", carequipment);
					editor.putString("towinglockedvehicle", towinglockedvehicle);
					editor.putString("towinglightduty", towinglightduty);
					editor.putString("towingairbags", towingairbags);
					editor.putString("calculatingmaxloadforsafesteering", calculatingmaxloadforsafesteering);
					editor.putString("calculatingmaxloadforrearaxle", calculatingmaxloadforrearaxle);
					editor.putString("grossvehicleweight", grossvehicleweight);
					editor.putString("safetytips", safetytips);
					editor.putString("reartowingprecaution", reartowingprecaution);
					editor.putString("groundclearances",groundclearances);

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
          prepareListData();

			}
		}
	}


	public void prepareListData() {
		// TODO Auto-generated method stub



		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data

		listDataHeader.add("INFO");
		listDataHeader.add("WHEEL LIFT EQUIPMENT");
		listDataHeader.add("CAR CARRIER EQUIPMENT");
		listDataHeader.add("TOWING LOCKED VEHICLES, KEYS NOT AVAILABLE");
		listDataHeader.add("TOWING LIGHT DUTY COMMERCIAL VEHICLES");
		listDataHeader.add("GROUND CLEARANCES WHILE TOWING");
		listDataHeader.add("TOWING AIR BAG EQUIPPED VEHICLES");
		listDataHeader.add("CALCULATING MAXIMUM LOAD FOR SAFE STEERING");
		listDataHeader.add("GROSS VEHICLE WEIGHT RATING");
		listDataHeader.add("SAFETY TIPS");
		listDataHeader.add("CALCULATING MAX LOAD FOR TRUCK REAR AXLE");
		listDataHeader.add("REAR TOWING PRECAUTIONS");



		sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedPreferences.edit();

		wheelequipment = String.valueOf(sharedPreferences.getString(
				"wheelequipment", ""));
		info = String.valueOf(sharedPreferences.getString(
				"info", ""));
		towinglockedvehicle = String.valueOf(sharedPreferences.getString(
				"towinglockedvehicle", ""));
		towingairbags = String.valueOf(sharedPreferences.getString(
				"towingairbags", ""));
		towinglightduty = String.valueOf(sharedPreferences.getString(
				"towinglightduty", ""));
		grossvehicleweight = String.valueOf(sharedPreferences.getString(
				"grossvehicleweight", ""));
		groundclearances = String.valueOf(sharedPreferences.getString(
				"groundclearances", ""));
		safetytips = String.valueOf(sharedPreferences.getString(
				"safetytips", ""));
		reartowingprecaution = String.valueOf(sharedPreferences.getString(
				"reartowingprecaution", ""));
		calculatingmaxloadforrearaxle = String.valueOf(sharedPreferences.getString(
				"calculatingmaxloadforrearaxle", ""));
		calculatingmaxloadforsafesteering = String.valueOf(sharedPreferences.getString(
				"calculatingmaxloadforsafesteering", ""));



		List<String> Info = new ArrayList<String>();
		Info.add(info);

		List<String> WHEELLIFTEQUIPMENT = new ArrayList<String>();
		WHEELLIFTEQUIPMENT.add(wheelequipment);


		List<String> CARCARRIEREQUIPMENT = new ArrayList<String>();
		CARCARRIEREQUIPMENT.add(carequipment);

		List<String> TOWINGLOCKEDVEHICLES_KEYSNOTAVAILABLE = new ArrayList<String>();
		TOWINGLOCKEDVEHICLES_KEYSNOTAVAILABLE.add(towinglockedvehicle);

		List<String> TOWINGLIGHTDUTYCOMMERCIALVEHICLES = new ArrayList<String>();
		TOWINGLIGHTDUTYCOMMERCIALVEHICLES.add(towinglightduty);

		List<String> GROUNDCLEARANCESWHILETOWING = new ArrayList<String>();
		GROUNDCLEARANCESWHILETOWING.add(groundclearances);


		List<String> TOWINGAIRBAGEQUIPPEDVEHICLES = new ArrayList<String>();
		TOWINGAIRBAGEQUIPPEDVEHICLES.add(towingairbags);


		List<String> CALCULATINGMAXIMUMLOADFORSAFESTEERING = new ArrayList<String>();
		CALCULATINGMAXIMUMLOADFORSAFESTEERING.add(calculatingmaxloadforsafesteering);

		List<String> GROSSVEHICLEWEIGHTRATING = new ArrayList<String>();
		GROSSVEHICLEWEIGHTRATING.add(grossvehicleweight);


		List<String> SAFETYTIPS = new ArrayList<String>();
		SAFETYTIPS.add(safetytips);

		List<String> CALCULATINGMAXLOADFORTRUCKREARAXLE = new ArrayList<String>();
		CALCULATINGMAXLOADFORTRUCKREARAXLE.add(calculatingmaxloadforrearaxle);

		List<String> REARTOWINGPRECAUTIONS = new ArrayList<String>();
		REARTOWINGPRECAUTIONS.add(reartowingprecaution);



		listDataChild.put(listDataHeader.get(0), Info);
		listDataChild.put(listDataHeader.get(1),WHEELLIFTEQUIPMENT); // Header, Child data
		listDataChild.put(listDataHeader.get(2), CARCARRIEREQUIPMENT);
		listDataChild.put(listDataHeader.get(3), TOWINGLOCKEDVEHICLES_KEYSNOTAVAILABLE);
		listDataChild.put(listDataHeader.get(4), TOWINGLIGHTDUTYCOMMERCIALVEHICLES);
		listDataChild.put(listDataHeader.get(5), GROUNDCLEARANCESWHILETOWING);
		listDataChild.put(listDataHeader.get(6), TOWINGAIRBAGEQUIPPEDVEHICLES);
		listDataChild.put(listDataHeader.get(7), CALCULATINGMAXIMUMLOADFORSAFESTEERING);
		listDataChild.put(listDataHeader.get(8), GROSSVEHICLEWEIGHTRATING);
		listDataChild.put(listDataHeader.get(9), SAFETYTIPS);
		listDataChild.put(listDataHeader.get(10), CALCULATINGMAXLOADFORTRUCKREARAXLE);
		listDataChild.put(listDataHeader.get(11), REARTOWINGPRECAUTIONS);


		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);
		expListView.setAdapter(listAdapter);


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
