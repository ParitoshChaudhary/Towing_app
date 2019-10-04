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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TextTowinfo extends CommonActivity implements OnClickListener {

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
	ImageView imghome;
	TextView texttowinfodetails1,textCaution1info,textcaution2info,textelectronickeyinfo,
			textElectronicParkBrakeinfo
			,textNote1info,textNote2info,textNote3info,textShiftInterlockOverrideinfo,textCaution3info;

	TextView texttowinfo1,textCaution1,textCaution2,textelectronickey,textCaution3,
			textElectronicParkBrake,
			textNote1,textNote2,textNote3 ,textShiftInterlockOverride;


	LinearLayout layout1,caution1,Caution2,Caution3,
			electronickey,ElectronicParkBrake,Note1,Note2,Note3,
			ShiftInterlockOverride;
	TextView tt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(TextTowinfo.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(TextTowinfo.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}

		else
		{

			setContentView(R.layout.activity_text_towinfo);

			sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
					Context.MODE_PRIVATE);
			sharedPreferences.edit();

			texttowinfodetails1  =(TextView) findViewById(R.id.texttowinfodetails1);
			textCaution1info =(TextView) findViewById(R.id.textCaution1info);
			textcaution2info =(TextView) findViewById(R.id.textcaution2info);
			textelectronickeyinfo =(TextView) findViewById(R.id.textelectronickeyinfo);
			textElectronicParkBrakeinfo =(TextView) findViewById(R.id.textElectronicParkBrakeinfo);
			textNote1info =(TextView) findViewById(R.id.textNote1info);
			textNote2info=(TextView) findViewById(R.id.textNote2info);
			textNote3info = (TextView) findViewById(R.id.textNote3info);
			textCaution3info = (TextView) findViewById(R.id.textcaution3info);
			textShiftInterlockOverrideinfo =(TextView) findViewById(R.id.textShiftInterlockOverrideinfo);

			imghome = (ImageView) findViewById(R.id.imghome);
			tt= (TextView) findViewById(R.id.tt);
			tt.setVisibility(View.INVISIBLE);

			texttowinfo1 = (TextView) findViewById(R.id.texttowinfo1);
			textCaution1 = (TextView) findViewById(R.id.textCaution1);
			textCaution2 = (TextView) findViewById(R.id.textCaution2);
			textelectronickey = (TextView) findViewById(R.id.textelectronickey);
			textElectronicParkBrake = (TextView) findViewById(R.id.textElectronicParkBrake);
			textNote1 = (TextView) findViewById(R.id.textNote1);
			textNote2 = (TextView) findViewById(R.id.textNote2);
			textShiftInterlockOverride = (TextView) findViewById(R.id.textShiftInterlockOverride);
			textNote3 = (TextView) findViewById(R.id.textNote3);
			textCaution3 = (TextView) findViewById(R.id.textcaution3);



			layout1 = (LinearLayout) findViewById(R.id.layout1);
			caution1 = (LinearLayout) findViewById(R.id.caution1);
			Caution2 = (LinearLayout) findViewById(R.id.Caution2);
			electronickey = (LinearLayout) findViewById(R.id.electronickey);
			ElectronicParkBrake = (LinearLayout) findViewById(R.id.ElectronicParkBrake);
			Note1 = (LinearLayout) findViewById(R.id.Note1);
			Note2 = (LinearLayout) findViewById(R.id.Note2);
			ShiftInterlockOverride = (LinearLayout) findViewById(R.id.ShiftInterlockOverride);
			Note3 = (LinearLayout) findViewById(R.id.Note3);
			Caution3 = (LinearLayout) findViewById(R.id.Caution3);


			new Carinfo().execute();

			Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
			texttowinfodetails1.setTypeface(typeface);
			textCaution1info .setTypeface(typeface);
			textcaution2info .setTypeface(typeface);
			textelectronickeyinfo.setTypeface(typeface);
			textElectronicParkBrakeinfo .setTypeface(typeface);
			textNote1info .setTypeface(typeface);
			textNote2info .setTypeface(typeface);
			textShiftInterlockOverrideinfo .setTypeface(typeface);
			textNote3info.setTypeface(typeface);
			textCaution3info.setTypeface(typeface);


			texttowinfo1 .setTypeface(typeface, Typeface.BOLD);
			textCaution1 .setTypeface(typeface,Typeface.BOLD);
			textCaution2 .setTypeface(typeface,Typeface.BOLD);
			textelectronickey .setTypeface(typeface,Typeface.BOLD);
			textElectronicParkBrake .setTypeface(typeface,Typeface.BOLD);
			textNote1 .setTypeface(typeface,Typeface.BOLD);
			textNote2 .setTypeface(typeface,Typeface.BOLD);
			textShiftInterlockOverride .setTypeface(typeface,Typeface.BOLD);
			textNote3.setTypeface(typeface, Typeface.BOLD);
			textCaution3.setTypeface(typeface,Typeface.BOLD);
			imghome.setOnClickListener(this);
		}
	}

	private class Carinfo extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TextTowinfo.this);
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


				if (jObj.equals("fail")) {
					flag = 0;
				} else {
					flag = 1;

					if (jObj.toString().contains("txtCaution3"))
					{
						txtCaution3 = jObj.getString("txtCaution3");
					}


					if (jObj.toString().contains("txtTowInformation"))
					{
						txtTowInfo = jObj.getString("txtTowInformation");
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
					editor.putString("txtCaution3", txtCaution3);
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
					textCaution3info .setText(txtCaution3);
				}


				if (txtTowInfo == null)
				{
					layout1.setVisibility(View.GONE);
				}
				else {
					texttowinfodetails1 .setText(txtTowInfo);
				}

				if (txtCaution1 == null)
				{
					caution1.setVisibility(View.GONE);
				}
				else {
					textCaution1info .setText(txtCaution1);
				}

				if (txtCaution2 == null)
				{
					Caution2.setVisibility(View.GONE);
				}
				else {
					textcaution2info .setText(txtCaution2);
				}



				if (txtElectronicKey == null)
				{
					electronickey.setVisibility(View.GONE);
				}
				else {
					textelectronickeyinfo .setText(txtElectronicKey);
				}

				if (txtElectronicParkBrake == null)
				{
					ElectronicParkBrake.setVisibility(View.GONE);
				}
				else {
					textElectronicParkBrakeinfo .setText(txtElectronicParkBrake);
				}






				if (txtNote1 == null)
				{
					Note1.setVisibility(View.GONE);
				}
				else {
					textNote1info.setText(txtNote1);
				}

				if (txtNote2 == null)
				{
					Note2.setVisibility(View.GONE);
				}
				else {
					textNote2info.setText(txtNote2);
				}
				if (txtNote3 == null)
				{
					Note3.setVisibility(View.GONE);
				}
				else {
					textNote3info.setText(txtNote3);
				}



				if (txtShiftIntelockOverride == null)
				{
					ShiftInterlockOverride.setVisibility(View.GONE);
				}
				else {
					textShiftInterlockOverrideinfo .setText(txtShiftIntelockOverride);
				}
			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent in = new Intent(this, MainActivity.class);
		startActivity(in);
	}

}