package com.example.towing_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Myimages extends CommonActivity implements OnClickListener {
	
	TextView txt;
	LinearLayout ImagesHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myimages);
		
		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(Myimages.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(Myimages.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}
		
		else {
		
		txt = (TextView) findViewById(R.id.txtstandardequipment);
		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txt.setTypeface(typeface1, Typeface.BOLD);
		 
		 ImagesHome = (LinearLayout) findViewById(R.id.images_home);
		 ImagesHome.setOnClickListener(this);
		
	}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.images_home:
			
			Intent home = new Intent(this, MainActivity.class);
			startActivity(home);
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
	
		Intent intent = new Intent(this, Standardequipment.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

	}

	
}
