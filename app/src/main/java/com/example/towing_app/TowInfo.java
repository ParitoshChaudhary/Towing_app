package com.example.towing_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TowInfo extends Activity implements OnClickListener {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	LinearLayout TowinHome;
	TextView txt;
	HashMap<String, List<String>> listDataChild;
	SharedPreferences sharedPreferences;
	String carId, txtJumpStarting, txtTireChanging, txtNote1, txtCaution1,
			txtTowInfo, txtHighVoltageSystem;
	String txtShiftIntelockOverride,txtNote2,txtNote3,txtEngineAccess,txtElectronicKey,
	txtHhybridSystem,txtCaution2,txtCaution3,txtElectronicParkBrake,txtFuelDelivery;
	
	private static String url_CarInfobyCarId = Constents.CONNECT_URL + "APICarInfoByCarId";
	ProgressDialog pDialog;
	int flag;
	JSONParser jParse = new JSONParser();
	JSONObject json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tow_info);
		
		Asyc();
		
		sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedPreferences.edit();
		
		TowinHome = (LinearLayout) findViewById(R.id.tinfo_home);
		TowinHome.setOnClickListener(this);
		
		txt = (TextView) findViewById(R.id.txtstandardequipment);
		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		
		// txtJumpStarting =
		// String.valueOf(sharedPreferences.getString("txtJumpStarting",
		// "abc"));
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		

		// preparing list data

		

		// setting list adapter
		
		
		

		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				
			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				// Toast.makeText(
				// getApplicationContext(),
				// listDataHeader.get(groupPosition)
				// + " : "
				// + listDataChild.get(
				// listDataHeader.get(groupPosition)).get(
				// childPosition), Toast.LENGTH_SHORT)
				// .show();

				return false;
			}
		});

	}

	public void Asyc() {
		new Carinfo().execute();

	}

	

	private class Carinfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TowInfo.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// Getting username and password from user input

		
			carId = String.valueOf(sharedPreferences.getString("carId", "carId"));
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("txtCarId", carId));

			json = jParse.makeHttpRequest(url_CarInfobyCarId, "POST", params);

			try {
				// JSONArray jsonary = json.getJSONArray("info");

				JSONObject jObj = json.getJSONObject("info");
				listDataChild = new HashMap<String, List<String>>();

				if (jObj.equals("fail")) {
					flag = 0;
				} else {
					flag = 1;
					
					if (jObj.toString().contains("txtTowInfo"))
					{
						txtTowInfo = jObj.getString("txtTowInfo");
					}
					
					
					if (jObj.toString().contains("txtCaution1"))
					{
						txtCaution1 = jObj.getString("txtCaution1");
					}
					
					
					if (jObj.toString().contains("txtShiftIntelockOverride"))
					{
						txtShiftIntelockOverride = jObj.getString("txtShiftIntelockOverride");
					}
					
					if (jObj.toString().contains("txtNote1"))
					{
						 txtNote1 = jObj.getString("txtNote1");
					}
					
					if (jObj.toString().contains("txtNote2"))
					{
						 txtNote2 = jObj.getString("txtNote2");
					}
					
					if (jObj.toString().contains("txtNote3"))
					{
						 txtNote3 = jObj.getString("txtNote3");
					}
					
					if (jObj.toString().contains("txtEngineAccess"))
					{
						txtEngineAccess = jObj.getString("txtEngineAccess");
					}
					
					if (jObj.toString().contains("txtJumpStarting"))
					{
						txtJumpStarting = jObj.getString("txtJumpStarting");
					}
					
					if (jObj.toString().contains("txtTireChanging"))
					{
						txtTireChanging = jObj.getString("txtTireChanging");
					}
					
					if (jObj.toString().contains("txtElectronicKey"))
					{
						txtElectronicKey = jObj.getString("txtElectronicKey");
					}
					
					if (jObj.toString().contains("txtHhybridSystem"))
					{
						txtHhybridSystem = jObj.getString("txtHhybridSystem");
					}
					
					if (jObj.toString().contains("txtCaution2"))
					{
						txtCaution2 = jObj.getString("txtCaution2");
					}
					
					if (jObj.toString().contains("txtCaution3"))
					{
						txtCaution3 = jObj.getString("txtCaution3");
					}
					
					if (jObj.toString().contains("txtElectronicParkBrake"))
					{
						txtElectronicParkBrake = jObj.getString("txtElectronicParkBrake");
					}
					
					if (jObj.toString().contains("txtFuelDelivery"))
					{
						txtFuelDelivery = jObj.getString("txtFuelDelivery");
					}
					
					if (jObj.toString().contains("txtHighVoltageSystem"))
					{
						txtHighVoltageSystem = jObj.getString("txtHighVoltageSystem");
					}
					
					

					sharedPreferences = getSharedPreferences(
							AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
					Editor editor = sharedPreferences.edit();
					editor.putString("txtEngineAccess", txtEngineAccess);
					editor.putString("txtTowInfo", txtTowInfo);
					editor.putString("txtCaution1", txtCaution1);
					editor.putString("txtShiftIntelockOverride", txtShiftIntelockOverride);
					editor.putString("txtNote1", txtNote1);
					editor.putString("txtNote2", txtNote2);
					editor.putString("txtNote3", txtNote3);
					editor.putString("txtJumpStarting", txtJumpStarting);
					editor.putString("txtTireChanging", txtTireChanging);
					editor.putString("txtElectronicKey", txtElectronicKey);
					editor.putString("txtHhybridSystem", txtHhybridSystem);
					editor.putString("txtCaution2", txtCaution2);
					editor.putString("txtCaution3", txtCaution3);
					editor.putString("txtElectronicParkBrake", txtElectronicParkBrake);
					editor.putString("txtFuelDelivery", txtFuelDelivery);
					editor.putString("txtHighVoltageSystem", txtHighVoltageSystem);
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
		listDataHeader.add("Tow Info");
		listDataHeader.add("Caution1");
		listDataHeader.add("Caution2");
		listDataHeader.add("Caution3");
		listDataHeader.add("JumpStarting");
		listDataHeader.add("Electronic Key");
		listDataHeader.add("Electronic Park Brake");
		listDataHeader.add("Fuel Delivery");
		listDataHeader.add("Hybrid System");
		listDataHeader.add("High Voltage System");
		listDataHeader.add("Note1");
		listDataHeader.add("Note2");
		listDataHeader.add("Note3");
		listDataHeader.add("Shift Interlock Override");
		listDataHeader.add("Tire Changing");
		listDataHeader.add("Engine Access");

		
		
		sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
				Context.MODE_PRIVATE);
		sharedPreferences.edit();

		txtJumpStarting = String.valueOf(sharedPreferences.getString(
				"txtJumpStarting", ""));

		txtTireChanging = String.valueOf(sharedPreferences.getString(
		"txtTireChanging", ""));

		txtNote1 = String.valueOf(sharedPreferences.getString(
		"txtNote1", ""));

		txtCaution1 = String.valueOf(sharedPreferences.getString(
		"txtCaution1", ""));

		txtTowInfo = String.valueOf(sharedPreferences.getString(
		"txtTowInfo", ""));

		txtHighVoltageSystem = String.valueOf(sharedPreferences.getString(
		"txtHighVoltageSystem", ""));

		txtShiftIntelockOverride = String.valueOf(sharedPreferences.getString(
		"txtShiftIntelockOverride", ""));

		txtNote2 = String.valueOf(sharedPreferences.getString(
		"txtNote2", ""));

		txtNote3 = String.valueOf(sharedPreferences.getString(
		"txtNote3", ""));

		txtEngineAccess = String.valueOf(sharedPreferences.getString(
		"txtEngineAccess", ""));

		txtElectronicKey = String.valueOf(sharedPreferences.getString(
		"txtElectronicKey", ""));

		txtHhybridSystem = String.valueOf(sharedPreferences.getString(
		"txtHhybridSystem", ""));

		txtCaution2= String.valueOf(sharedPreferences.getString(
		"txtCaution2", ""));

		txtCaution3= String.valueOf(sharedPreferences.getString(
		"txtCaution3", ""));

		txtElectronicParkBrake = String.valueOf(sharedPreferences.getString(
		"txtElectronicParkBrake",""));

		txtFuelDelivery = String.valueOf(sharedPreferences.getString(
		"txtFuelDelivery", ""));

		
		
		List<String> TowInfo = new ArrayList<String>();
		TowInfo.add(txtTowInfo);
		

		List<String> Caution1 = new ArrayList<String>();
		Caution1.add(txtCaution1);
		
		List<String> Caution2 = new ArrayList<String>();
		Caution2.add(txtCaution2);

		List<String> Caution3 = new ArrayList<String>();
		Caution3.add(txtCaution3);

		List<String> JumpStarting = new ArrayList<String>();
		JumpStarting.add(txtJumpStarting);

		
		List<String> ElectronicKey = new ArrayList<String>();
		ElectronicKey.add(txtElectronicKey);
		

		List<String> ElectronicParkBrake = new ArrayList<String>();
		ElectronicParkBrake.add(txtElectronicParkBrake);

		List<String> FuelDelivery = new ArrayList<String>();
		FuelDelivery.add(txtFuelDelivery);
		
		
		List<String> HybridSystem = new ArrayList<String>();
		HybridSystem.add(txtHhybridSystem);
		
		List<String> HighVoltageSystem = new ArrayList<String>();
		HighVoltageSystem.add(txtHighVoltageSystem);
		
		List<String> Note1 = new ArrayList<String>();
		Note1.add(txtNote1);
		
		List<String> Note2 = new ArrayList<String>();
		Note2.add(txtNote2);
		
		List<String> Note3 = new ArrayList<String>();
		Note3.add(txtNote3);
		
		
		List<String> ShiftInterlockOverride = new ArrayList<String>();
		ShiftInterlockOverride.add(txtShiftIntelockOverride);
		
		List<String> TireChanging = new ArrayList<String>();
		TireChanging.add(txtTireChanging);
		
		List<String> EngineAccess = new ArrayList<String>();
		EngineAccess.add(txtEngineAccess);
		

		listDataChild.put(listDataHeader.get(0), TowInfo); // Header, Child data
		listDataChild.put(listDataHeader.get(1), Caution1);
		listDataChild.put(listDataHeader.get(2), Caution2);
		listDataChild.put(listDataHeader.get(3), Caution3);
		listDataChild.put(listDataHeader.get(4), JumpStarting);
		listDataChild.put(listDataHeader.get(5), ElectronicKey);
		listDataChild.put(listDataHeader.get(6), ElectronicParkBrake);
		listDataChild.put(listDataHeader.get(7), FuelDelivery);
		listDataChild.put(listDataHeader.get(8), HybridSystem);
		listDataChild.put(listDataHeader.get(9), HighVoltageSystem);
		listDataChild.put(listDataHeader.get(10), Note1);
		listDataChild.put(listDataHeader.get(11), Note2);
		listDataChild.put(listDataHeader.get(12), Note3);
		listDataChild.put(listDataHeader.get(13), ShiftInterlockOverride);
		listDataChild.put(listDataHeader.get(14), TireChanging);
		listDataChild.put(listDataHeader.get(15), EngineAccess);

		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);
		expListView.setAdapter(listAdapter);
		
		
	}
	
	
//	public void prepareListData() {
//		// TODO Auto-generated method stub
//	
//		
//
//		listDataHeader = new ArrayList<String>();
//		listDataChild = new HashMap<String, List<String>>();
//		
//		
//		if ( txtTowInfo != null)
//		{
//			listDataHeader.add("Tow Info");
//		}
//
//		if ( txtCaution1 != null)
//		{
//			listDataHeader.add("Caution1");
//		}
//		
//		if ( txtCaution2 != null)
//		{
//			listDataHeader.add("Caution2");
//		}
//		
//		if ( txtCaution3 != null)
//		{
//			listDataHeader.add("Caution3");
//		}
//		
//		if ( txtJumpStarting != null)
//		{
//			listDataHeader.add("JumpStarting");
//		}
//		
//		if (txtElectronicKey != null)
//		{
//			listDataHeader.add("Electronic Key");
//		}
//		
//		if ( txtElectronicParkBrake != null)
//		{
//			listDataHeader.add("Electronic Park Brake");
//		}
//		
//		if ( txtFuelDelivery != null)
//		{
//			listDataHeader.add("Fuel Delivery");
//		}
//		
//		if ( txtHhybridSystem  != null)
//		{
//			listDataHeader.add("Hybrid System");
//		}
//		
//		if ( txtHighVoltageSystem != null)
//		{
//			listDataHeader.add("High Voltage System");
//		}
//		
//		if ( txtNote1  != null)
//		{
//			listDataHeader.add("Note1");
//		}
//		
//		if (txtNote2 != null)
//		{
//			listDataHeader.add("Note2");
//		}
//		
//		if (txtNote3 != null)
//		{
//			listDataHeader.add("Note3");
//		}
//		
//		if (txtShiftIntelockOverride != null)
//		{
//			listDataHeader.add("Shift Interlock Override");
//		}
//		
//		if ( txtTireChanging != null)
//		{
//			listDataHeader.add("Tire Changing");
//		}
//		
//		
//		if (txtEngineAccess != null)
//		{
//			listDataHeader.add("Engine Access");
//		}
//		
//		
//		
//		sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
//				Context.MODE_PRIVATE);
//		sharedPreferences.edit();
//
//		txtJumpStarting = String.valueOf(sharedPreferences.getString(
//				"txtJumpStarting", "No Information Present"));
//
//		txtTireChanging = String.valueOf(sharedPreferences.getString(
//		"txtTireChanging", "No Information Present"));
//
//		txtNote1 = String.valueOf(sharedPreferences.getString(
//		"txtNote1", "No Information Present"));
//
//		txtCaution1 = String.valueOf(sharedPreferences.getString(
//		"txtCaution1", "No Information Present"));
//
//		txtTowInfo = String.valueOf(sharedPreferences.getString(
//		"txtTowInfo", "No Information Present"));
//
//		txtHighVoltageSystem = String.valueOf(sharedPreferences.getString(
//		"txtHighVoltageSystem", "No Information Present"));
//
//		txtShiftIntelockOverride = String.valueOf(sharedPreferences.getString(
//		"txtShiftIntelockOverride", "No Information Present"));
//
//		txtNote2 = String.valueOf(sharedPreferences.getString(
//		"txtNote2", "No Information Present"));
//
//		txtNote3 = String.valueOf(sharedPreferences.getString(
//		"txtNote3", "No Information Present"));
//
//		txtEngineAccess = String.valueOf(sharedPreferences.getString(
//		"txtEngineAccess", "No Information Present"));
//
//		txtElectronicKey = String.valueOf(sharedPreferences.getString(
//		"txtElectronicKey", "No Information Present"));
//
//		txtHhybridSystem = String.valueOf(sharedPreferences.getString(
//		"txtHhybridSystem", "No Information Present"));
//
//		txtCaution2= String.valueOf(sharedPreferences.getString(
//		"txtCaution2", "No Information Present"));
//
//		txtCaution3= String.valueOf(sharedPreferences.getString(
//		"txtCaution3", "No Information Present"));
//
//		txtElectronicParkBrake = String.valueOf(sharedPreferences.getString(
//		"txtElectronicParkBrake", "No Information Present"));
//
//		txtFuelDelivery = String.valueOf(sharedPreferences.getString(
//		"txtFuelDelivery", "No Information Present"));
//
//
//		if (txtTowInfo != "No Information Present")
//		{
//	 List<String> TowInfo = new ArrayList<String>();
//     TowInfo.add(txtTowInfo);
//			listDataChild.put(listDataHeader.get(0), TowInfo);
//		}
//		
//		if (txtCaution1 != "No Information Present")
//		{
//	 List<String> Caution1 = new ArrayList<String>();
//		Caution1.add(txtCaution1);
//			listDataChild.put(listDataHeader.get(1), Caution1);
//			
//		}
//		
//		if (txtCaution2 != "No Information Present")
//		{
//	 List<String> Caution2 = new ArrayList<String>();
//		Caution2.add(txtCaution2);
//			listDataChild.put(listDataHeader.get(2), Caution2);
//		}
//		
//		if (txtCaution3 != "No Information Present")
//		{
//	 List<String> Caution3 = new ArrayList<String>();
//		Caution3.add(txtCaution3);
//			listDataChild.put(listDataHeader.get(3), Caution3);
//		}
//		
//		if (txtJumpStarting != "No Information Present")
//		{
//	      List<String> JumpStarting = new ArrayList<String>();
//		JumpStarting.add(txtJumpStarting);
//			listDataChild.put(listDataHeader.get(4), JumpStarting);
//		}
//		
//		if (txtElectronicKey != "No Information Present")
//		{
//	 List<String> ElectronicKey = new ArrayList<String>();
//		ElectronicKey.add(txtElectronicKey);
//			listDataChild.put(listDataHeader.get(5), ElectronicKey);
//		}
//		
//		if (txtElectronicParkBrake != "No Information Present")
//		{
//		List<String> ElectronicParkBrake = new ArrayList<String>();
//		ElectronicParkBrake.add(txtElectronicParkBrake);
//			listDataChild.put(listDataHeader.get(6), ElectronicParkBrake);
//		}
//		
//		if (txtFuelDelivery != "No Information Present")
//		{
//		List<String> FuelDelivery = new ArrayList<String>();
//		FuelDelivery.add(txtFuelDelivery);
//			listDataChild.put(listDataHeader.get(7), FuelDelivery);
//		}
//		
//		if (txtHhybridSystem != "No Information Present")
//		{
//		List<String> HybridSystem = new ArrayList<String>();
//		HybridSystem.add(txtHhybridSystem);
//			listDataChild.put(listDataHeader.get(8), HybridSystem);
//		}
//		
//		if (txtHighVoltageSystem != "No Information Present")
//		{
//		List<String> HighVoltageSystem = new ArrayList<String>();
//		HighVoltageSystem.add(txtHighVoltageSystem);
//			listDataChild.put(listDataHeader.get(9), HighVoltageSystem);
//		}
//		
//		if (txtNote1 != "No Information Present")
//		{
//		List<String> Note1 = new ArrayList<String>();
//		Note1.add(txtNote1);
//		
//			listDataChild.put(listDataHeader.get(10), Note1);
//		}
//		
//		if (txtNote2 != "No Information Present")
//		{
//	 List<String> Note2 = new ArrayList<String>();
//		Note2.add(txtNote2);
//			listDataChild.put(listDataHeader.get(11), Note2);
//		}
//		
//		if (txtNote3 != "No Information Present")
//		{
//	 List<String> Note3 = new ArrayList<String>();
//		Note3.add(txtNote3);
//			listDataChild.put(listDataHeader.get(12), Note3);
//		}
//		
//		if (txtShiftIntelockOverride != "No Information Present")
//		{
//	 List<String> ShiftInterlockOverride = new ArrayList<String>();
//		ShiftInterlockOverride.add(txtShiftIntelockOverride);
//			listDataChild.put(listDataHeader.get(13), ShiftInterlockOverride);
//		}
//		
//		
//		
//		if (txtTireChanging != "No Information Present")
//		{
//	 List<String> TireChanging = new ArrayList<String>();
//		TireChanging.add(txtTireChanging);
//			listDataChild.put(listDataHeader.get(14), TireChanging);
//		}
//		
//		if (txtEngineAccess != "No Information Present")
//		{
//
//		List<String> EngineAccess = new ArrayList<String>();
//		EngineAccess.add(txtEngineAccess);
//			listDataChild.put(listDataHeader.get(15), EngineAccess);
//		}
//		
//		
//
//		listAdapter = new ExpandableListAdapter(this, listDataHeader,
//				listDataChild);
//		expListView.setAdapter(listAdapter);
//		
//		
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.tinfo_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
			overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
			
			break;

		default:
			break;
		}
		
	}
	
	

}
