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
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TowLimits extends CommonActivity implements View.OnClickListener {

    private static String url_CarInfobyId = "http://216.224.177.43:8080/TowingApp/APICarInfoById";
    TextView towimages;
    ImageView imghome;
    TextView frontAmph,frontMmph,rearAmph,rearMmph;
    TextView frontAmiles,frontMmiles,rearAmiles,rearMmiles;
    ProgressDialog pDialog;
    int flag;
    JSONParser jParse = new JSONParser();
    JSONObject json;
    SharedPreferences sharedPreferences;
    String carId,txtFrontAmiles,txtFrontAmph, txtRearAmph,txtRearAmiles,txtRearMmph,txtRearMmiles,txtFrontMmph,txtFrontMmiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(!isNetworkAvailable()){
            AlertDialog.Builder b;
            AlertDialog alt;
            b = new AlertDialog.Builder(TowLimits.this);
            b.setMessage("Intenet Connection Problem. Please connect to Internet.");
            b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent(TowLimits.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            b.show();
        }

        else
        {
            setContentView(R.layout.activity_tow_limits);

            towimages = (TextView) findViewById(R.id.txttowimages);
            Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
            towimages.setTypeface(typeface1, Typeface.BOLD);
            imghome = (ImageView) findViewById(R.id.imghome);
            imghome.setOnClickListener(this);
            frontAmph = (TextView) findViewById(R.id.frontAmph);
            frontAmiles = (TextView) findViewById(R.id.frontAmiles);
            frontMmiles = (TextView) findViewById(R.id.frontMmiles);
            frontMmph = (TextView) findViewById(R.id.frontMmph);

            rearAmph = (TextView) findViewById(R.id.rearAmph);
            rearAmiles = (TextView) findViewById(R.id.rearAmiles);
            rearMmph = (TextView) findViewById(R.id.rearMmph);
            rearMmiles = (TextView) findViewById(R.id.rearMmiles);
            sharedPreferences = this.getSharedPreferences(AllManufacture.MyPREFERENCES,
                    Context.MODE_PRIVATE);
            sharedPreferences.edit();
            new Carinfo().execute();

            Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
            frontAmiles.setTypeface(typeface,Typeface.BOLD);
            frontAmph .setTypeface(typeface, Typeface.BOLD);
            frontMmiles .setTypeface(typeface,Typeface.BOLD);
            frontMmph .setTypeface(typeface,Typeface.BOLD);
            rearAmiles .setTypeface(typeface,Typeface.BOLD);
            rearAmph .setTypeface(typeface,Typeface.BOLD);
            rearMmiles .setTypeface(typeface,Typeface.BOLD);
            rearMmph .setTypeface(typeface,Typeface.BOLD);


        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);

    }


    private class Carinfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TowLimits.this);
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

            json = jParse.makeHttpRequest(url_CarInfobyId, "POST", params);

            try {
                // JSONArray jsonary = json.getJSONArray("info");

                JSONObject jObj = json.getJSONObject("info");


                if (jObj.equals("fail")) {
                    flag = 0;
                } else {
                    flag = 1;

                    if (jObj.toString().contains("txtFrontMPH"))
                    {
                        txtFrontAmph = jObj.getString("txtFrontMPH");
                    }


                    if (jObj.toString().contains("txtFrontMiles"))
                    {
                        txtFrontAmiles = jObj.getString("txtFrontMiles");
                    }


                    if (jObj.toString().contains("txtFront1MPH"))
                    {
                        txtFrontMmph = jObj.getString("txtFront1MPH");
                    }

                    if (jObj.toString().contains("txtFront1Miles"))
                    {
                        txtFrontMmiles = jObj.getString("txtFront1Miles");
                    }

                    if (jObj.toString().contains("txtRearMPH"))
                    {
                        txtRearAmph = jObj.getString("txtRearMPH");
                    }

                    if (jObj.toString().contains("txtRearMiles"))
                    {
                        txtRearAmiles = jObj.getString("txtRearMiles");
                    }

                    if (jObj.toString().contains("txtRear1MPH"))
                    {
                        txtRearMmph = jObj.getString("txtRear1MPH");
                    }

                    if (jObj.toString().contains("txtRear1Miles"))
                    {
                        txtRearMmiles = jObj.getString("txtRear1Miles");
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

                frontMmph.setText(txtFrontMmph);
                frontMmiles.setText(txtFrontMmiles);
                frontAmiles.setText(txtFrontAmiles);
                frontAmph.setText(txtFrontAmph);
                rearAmph .setText(txtRearAmph);
                rearAmiles.setText(txtRearAmiles);
                rearMmiles.setText(txtRearMmiles);
                rearMmph.setText(txtRearMmph);




            }
        }

    }
}