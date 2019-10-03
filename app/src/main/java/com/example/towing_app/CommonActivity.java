package com.example.towing_app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class CommonActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		
	}
	
	public void toastMsg(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
	
	public void log(String tag,String message )
	{
		Log.d(tag,message);
	}
	
	public void loge(String tag,String message)
	{
		Log.e(tag, message);
	}

//	@Override
//	public void onBackPressed() {
//		
//		super.onBackPressed();	
//		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);	
//	}

	protected String doInBackground(String[] Params) {
	
		return null;
	}

	protected Fragment getSampleFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onRefreshStarted(View view) {
		// TODO Auto-generated method stub
		
	}

	public void onComplete(Bundle values) {
		// TODO Auto-generated method stub
		
	}

	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	public void onPostExecute(String addc) {
		// TODO Auto-generated method stub
		
	}
	 public  boolean isNetworkAvailable() {
//			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo activeNetworkInfo = connectivityManager
//					.getActiveNetworkInfo();
//			return activeNetworkInfo != null && activeNetworkInfo.isConnected();
			
			
			ConnectivityManager cm =
			        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo netInfo = cm.getActiveNetworkInfo();
			    return netInfo != null && netInfo.isConnectedOrConnecting();
		}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}