package com.example.towing_app;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceInfo extends CommonActivity implements OnClickListener {
	TextView txtserviceinfo;
	ImageView imghome;

	SharedPreferences sharedPreferences;
	String carId, txtElectronicKey,txtJumpStarting, txtTireChanging, txtHighVoltageSystem,txtEngineAccess;

	String txtNote3,
			txtHhybridSystem,txtCaution3,txtFuelDelivery;

	private static String url_serviceinfo = "http://216.224.177.43:8080/TowingApp/APIServiceInfoByCarId";
	ProgressDialog pDialog;
	int flag;
	JSONParser jParse = new JSONParser();
	JSONObject json;

	TextView textcaution3info,textjumpstartinginfo,
			textFuelDeliveryinfo,textHybridSysteminfo,textHighVoltageSysteminfo
			,textNote3info,textTireChanginginfo,textEngineAcessinfo,textelectronickeyinfo;

	TextView textcaution3,textjumpstarting,
			textFuelDelivery,textHybridSystem,textHighVoltageSystem,
			textNote3,textTireChanging,textEngineAcess,tt,textelectronickey;


	LinearLayout Caution3,jumpstarting,
			FuelDelivery,HybridSystem,HighVoltageSystem,
			Note3,TireChanging,EngineAccess,electronickey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(ServiceInfo.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(ServiceInfo.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}

		else
		{
			setContentView(R.layout.activity_service_info);
			txtserviceinfo = (TextView) findViewById(R.id.txtserviceinfo);
			Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
			txtserviceinfo.setTypeface(typeface1, Typeface.BOLD);
			imghome = (ImageView) findViewById(R.id.imghome);
			imghome.setOnClickListener(this);
			sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
					Context.MODE_PRIVATE);
			sharedPreferences.edit();

			textcaution3info =(TextView) findViewById(R.id.textcaution3info);
			textjumpstartinginfo  =(TextView) findViewById(R.id.textjumpstartinginfo);
			textFuelDeliveryinfo  =(TextView) findViewById(R.id.textFuelDeliveryinfo);
			textHybridSysteminfo =(TextView) findViewById(R.id.textHybridSysteminfo);
			textHighVoltageSysteminfo =(TextView) findViewById(R.id.textHighVoltageSysteminfo);
			textNote3info =(TextView) findViewById(R.id.textNote3info);
			textTireChanginginfo =(TextView) findViewById(R.id.textTireChanginginfo);
			textEngineAcessinfo =(TextView) findViewById(R.id.textEngineAcessinfo);
			textelectronickeyinfo = (TextView) findViewById(R.id.textelectronickeyinfo);



//		tt= (TextView) findViewById(R.id.tt);
//		tt.setVisibility(View.INVISIBLE);

			textcaution3 = (TextView) findViewById(R.id.textcaution3);
			textjumpstarting = (TextView) findViewById(R.id.textjumpstarting);
			textFuelDelivery = (TextView) findViewById(R.id.textFuelDelivery);
			textHybridSystem = (TextView) findViewById(R.id.textHybridSystem);
			textHighVoltageSystem = (TextView) findViewById(R.id.textHighVoltageSystem);
			textNote3 = (TextView) findViewById(R.id.textNote3);
			textTireChanging = (TextView) findViewById(R.id.textTireChanging);
			textEngineAcess = (TextView) findViewById(R.id.textEngineAcess);
			textelectronickey = (TextView) findViewById(R.id.textelectronickey);


			Caution3 = (LinearLayout) findViewById(R.id.Caution3);
			jumpstarting = (LinearLayout) findViewById(R.id.jumpstarting);
			FuelDelivery = (LinearLayout) findViewById(R.id.FuelDelivery);
			HybridSystem = (LinearLayout) findViewById(R.id.HybridSystem);
			HighVoltageSystem = (LinearLayout) findViewById(R.id.HighVoltageSystem);
			Note3 = (LinearLayout) findViewById(R.id.Note3);
			TireChanging = (LinearLayout) findViewById(R.id.TireChanging);
			EngineAccess = (LinearLayout) findViewById(R.id.EngineAccess);
			electronickey = (LinearLayout) findViewById(R.id.electronickey);

			new Carinfo().execute();

			Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
			textcaution3info .setTypeface(typeface);
			textjumpstartinginfo.setTypeface(typeface);
			textFuelDeliveryinfo  .setTypeface(typeface);
			textHybridSysteminfo .setTypeface(typeface);
			textHighVoltageSysteminfo.setTypeface(typeface);
			textNote3info .setTypeface(typeface);
			textTireChanginginfo .setTypeface(typeface);
			textEngineAcessinfo .setTypeface(typeface);
			textelectronickeyinfo.setTypeface(typeface);


			textcaution3 .setTypeface(typeface, Typeface.BOLD);
			textjumpstarting .setTypeface(typeface,Typeface.BOLD);
			textFuelDelivery .setTypeface(typeface,Typeface.BOLD);
			textHybridSystem .setTypeface(typeface,Typeface.BOLD);
			textHighVoltageSystem .setTypeface(typeface,Typeface.BOLD);
			textNote3 .setTypeface(typeface,Typeface.BOLD);
			textTireChanging .setTypeface(typeface,Typeface.BOLD);
			textEngineAcess .setTypeface(typeface,Typeface.BOLD);
			textelectronickey.setTypeface(typeface,Typeface.BOLD);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent in = new Intent (this, MainActivity.class);
		startActivity(in);

	}

	private class Carinfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ServiceInfo.this);
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

			json = jParse.makeHttpRequest(url_serviceinfo, "POST", params);

			try {
				// JSONArray jsonary = json.getJSONArray("info");

				JSONObject jObj = json.getJSONObject("info");


				if (jObj.equals("fail")) {
					flag = 0;
				} else {
					flag = 1;

					if (jObj.toString().contains("txtCaution1"))
					{
						txtCaution3 = jObj.getString("txtCaution1");
					}

					if (jObj.toString().contains("txtElectronicKey"))
					{
						txtElectronicKey = jObj.getString("txtElectronicKey");
					}

					if (jObj.toString().contains("txtEngineAccess"))
					{
						txtEngineAccess = jObj.getString("txtEngineAccess");
					}

					if (jObj.toString().contains("txtFuelDelivery"))
					{
						txtFuelDelivery = jObj.getString("txtFuelDelivery");
					}

					if (jObj.toString().contains("txtHhybridSystem"))
					{
						txtHhybridSystem= jObj.getString("txtHhybridSystem");
					}

					if (jObj.toString().contains("txtHighVoltageSystem"))
					{
						txtHighVoltageSystem= jObj.getString("txtHighVoltageSystem");
					}

					if (jObj.toString().contains("txtJumpStarting"))
					{
						txtJumpStarting= jObj.getString("txtJumpStarting");
					}

					if (jObj.toString().contains("txtNote1"))
					{
						txtNote3= jObj.getString("txtNote1");
					}

					if (jObj.toString().contains("txtTireChanging"))
					{
						txtTireChanging= jObj.getString("txtTireChanging");
					}




					sharedPreferences = getSharedPreferences(
							AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString("txtEngineAccess", txtEngineAccess);
					editor.putString("txtNote3", txtNote3);
					editor.putString("txtJumpStarting", txtJumpStarting);
					editor.putString("txtTireChanging", txtTireChanging);
					editor.putString("txtElectronicKey", txtElectronicKey);
					editor.putString("txtHhybridSystem", txtHhybridSystem);
					editor.putString("txtCaution3", txtCaution3);
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

				if (txtCaution3 == null)
				{
					Caution3.setVisibility(View.GONE);
				}
				else {
					textcaution3info .setText(txtCaution3);
				}


				if (txtJumpStarting == null)
				{
					jumpstarting.setVisibility(View.GONE);
				}
				else {
					textjumpstartinginfo .setText(txtJumpStarting);
				}

				if (txtFuelDelivery == null)
				{
					FuelDelivery.setVisibility(View.GONE);
				}
				else {
					textFuelDeliveryinfo .setText(txtFuelDelivery);
				}

				if (txtHhybridSystem == null)
				{
					HybridSystem.setVisibility(View.GONE);
				}
				else {
					textHybridSysteminfo .setText(txtHhybridSystem);
				}



				if (txtElectronicKey == null)
				{
					electronickey.setVisibility(View.GONE);
				}
				else {
					textelectronickeyinfo .setText(txtElectronicKey);
				}

				if (txtHighVoltageSystem == null) {
					HighVoltageSystem.setVisibility(View.GONE);
				}
				else {
					textHighVoltageSysteminfo .setText(txtHighVoltageSystem);
				}

				if (txtNote3 == null)
				{
					Note3.setVisibility(View.GONE);
				}
				else {
					textNote3info.setText(txtNote3);
				}

				if (txtTireChanging == null)
				{
					TireChanging.setVisibility(View.GONE);
				}
				else {
					textTireChanginginfo.setText(txtTireChanging);
				}
				if (txtEngineAccess == null) {
					EngineAccess.setVisibility(View.GONE);
				}
				else {
					textEngineAcessinfo.setText(txtEngineAccess);
				}







			}
		}

	}

}