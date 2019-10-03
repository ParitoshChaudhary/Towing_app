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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Details_table extends CommonActivity implements View.OnClickListener {

    TextView txt;
    LinearLayout towinfo, SearchCarHome,serviceinfo,towimages,towlimit;
    String carModel,carName,carYear,curbWeight1,curbWeight2,curbWeight3,
            carId,frontAxleWight1,frontAxleWight2,frontAxleWight3,rearAxleWeight1,rearAxleWeight2,
            rearAxleWeight3,series1,series2,series3,list;
    ProgressDialog pDialog;
    JSONParser jParse = new JSONParser();
    JSONObject json;
    int flag;
    int i;
    private static String url_search = "http://216.224.177.43:8080/TowingApp/Search";
    ArrayList<HashMap<String, String>> arraysyear;
    HashMap<String, String> hashyear;
    SharedPreferences sharedPreferences;
    TextView txtmanufacturename,txtmodelname,txtyearno,txtcurbweightno,txtfrontweightno,txtrearno;
    TextView txtmanufacture,txtmodel,txtyear,txtcurbweight1,
            txtcurbweight2, txtcurbweight3, txtfrontweight1,
            txtfrontweight2, txtfrontweight3,txtrear1,txtrear2,txtrear3,
            image,txttowinfo,txtserviceinfo,txtseries1,txtseries2,txtseries3,series,faw,raw,curbweight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_table);

        if(!isNetworkAvailable()){
            AlertDialog.Builder b;
            AlertDialog alt;
            b = new AlertDialog.Builder(Details_table.this);
            b.setMessage("Intenet Connection Problem. Please connect to Internet.");
            b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent(Details_table.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            b.show();
        }

        else
        {
            new Cardetails().execute();
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
            txtcurbweight1 = (TextView) findViewById(R.id.curbwgt1);
            txtcurbweight2 = (TextView) findViewById(R.id.curbwgt2);
            txtcurbweight3 = (TextView) findViewById(R.id.curbwgt3);
            txtfrontweight1  = (TextView) findViewById(R.id.faw1);
            txtfrontweight2  = (TextView) findViewById(R.id.faw2);
            txtfrontweight3  = (TextView) findViewById(R.id.faw3);
            txtrear1 = (TextView) findViewById(R.id.raw1);
            txtrear2 = (TextView) findViewById(R.id.raw2);
            txtrear3 = (TextView) findViewById(R.id.raw3);
            txtseries1 = (TextView) findViewById(R.id.series1);
            txtseries2 = (TextView) findViewById(R.id.series2);
            txtseries3 = (TextView) findViewById(R.id.series3);
            series = (TextView) findViewById(R.id.series);
            faw = (TextView) findViewById(R.id.faw);
            raw = (TextView) findViewById(R.id.raw);
            curbweight = (TextView) findViewById(R.id.curbweight);

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
            txtmanufacture.setTypeface(typeface2,Typeface.BOLD);
            txtmodel.setTypeface(typeface2,Typeface.BOLD);
            txtyear.setTypeface(typeface2,Typeface.BOLD);

            series.setTypeface(typeface2,Typeface.BOLD);
            curbweight.setTypeface(typeface2,Typeface.BOLD);
            faw.setTypeface(typeface2,Typeface.BOLD);
            raw.setTypeface(typeface2,Typeface.BOLD);
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

            arraysyear = new ArrayList<HashMap<String, String>>();


        }
    }




    private class Cardetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Details_table.this);
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


                    carName = jObj.getString("carName");

                    carYear = jObj.getString("carYear");
                    carId = jObj.getString("carId");
                    carModel = jObj.getString("carModel");
//                    list = jObj.getString("lst");

                    JSONArray jsonary = jObj.getJSONArray("lst");

                    for ( i = 0; i < jsonary.length(); i++) {


                        JSONObject local_obj = (JSONObject) jsonary.get(i);

                        if (i == 0)
                        {

                            curbWeight1 = local_obj.getString("curbWeight");
                            frontAxleWight1 = local_obj.getString("frontAxleWeight");
                            rearAxleWeight1 = local_obj.getString("rearAxleWeight");
                            series1 = local_obj.getString("series");

                        }

                        if ( i == 1)
                        {
                            curbWeight2 = local_obj.getString("curbWeight");
                            frontAxleWight2 = local_obj.getString("frontAxleWeight");
                            rearAxleWeight2 = local_obj.getString("rearAxleWeight");
                            series2 = local_obj.getString("series");

                        }


                        else {
                            curbWeight3 = local_obj.getString("curbWeight");
                            frontAxleWight3 = local_obj.getString("frontAxleWeight");
                            rearAxleWeight3 = local_obj.getString("rearAxleWeight");
                            series3 = local_obj.getString("series");
                        }

//                        hashyear = new HashMap<String, String>();


//                        hashyear.put("curbWeight", curbWeight1);
//                        hashyear.put("frontAxleWeight", frontAxleWight1);
//                        hashyear.put("rearAxleWeight",  rearAxleWeight1);
//                        hashyear.put("series",  series1);
//                        arraysyear.add(hashyear);




                    }


                    sharedPreferences = getSharedPreferences(
                            AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
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
                txtcurbweight1.setText(curbWeight1);
                txtcurbweight2.setText(curbWeight2);
                txtcurbweight3.setText(curbWeight3);
                txtfrontweight1.setText(frontAxleWight1);
                txtfrontweight2.setText(frontAxleWight2);
                txtfrontweight3.setText(frontAxleWight3);
                txtrear1.setText(rearAxleWeight1);
                txtrear2.setText(rearAxleWeight2);
                txtrear3.setText(rearAxleWeight3);
                txtseries1.setText(series1);
                txtseries2.setText(series2);
                txtseries3.setText(series3);






            }
        }

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }

}
