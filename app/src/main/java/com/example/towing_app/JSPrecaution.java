package com.example.towing_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSPrecaution extends AppCompatActivity {

    TextView note1text, note2text, information;
    ProgressDialog pDialog;
    JSONParser jParse = new JSONParser();
    JSONObject json;
    int flag;
    String info, note1,note2;
    private static String url_allmodels = Constents.CONNECT_URL + "APIJumpStartingInstructionsAndPrecautions";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsprecaution);

        information = (TextView) findViewById(R.id.information);
        note1text = (TextView) findViewById(R.id.notetext);
        note2text = (TextView) findViewById(R.id.note2text);
        new Text().execute();


    }

    private class Text extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(JSPrecaution.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // Getting username and password from user input

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            json = jParse.makeHttpRequest(url_allmodels, "POST", params);

            try {
                JSONObject jsonObject = json.getJSONObject("info");

                info = jsonObject.getString("info");
                note1 = jsonObject.getString("note1");
                note2 = jsonObject.getString("note2");



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

            note1text.setText(note1);
            note2text.setText(note2);
            information.setText(info);



        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        Intent intent = new Intent(JSPrecaution.this, GenJumpStart.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }

}
