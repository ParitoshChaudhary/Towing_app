package com.example.towing_app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MainActivity extends CommonActivity implements OnClickListener {
	LinearLayout llequipment,llothersearch,llinstruction, llgenjumpstart, llmotorjumpstart, llundervehicle, llspecialoil;
	TextView txtwelcome,txtequipment,txtinstruction,txtothersearch, txtgenjumpstart, txtundervehicle, txtspecialoil, txtmotorjumpstart;
	Boolean isInternetPresent = false;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
				
			
		setContentView(R.layout.activity_main);


		llequipment= (LinearLayout) findViewById(R.id.llequipment);
		llothersearch = (LinearLayout) findViewById(R.id.llothersearch);
//		llpolicy = (LinearLayout) findViewById(R.id.llpolicy);
		llinstruction = (LinearLayout) findViewById(R.id.llinstruction);
		llgenjumpstart = (LinearLayout) findViewById(R.id.llgenjumpstart);
		llmotorjumpstart = (LinearLayout) findViewById(R.id.llmotorjumpstart);
		llundervehicle = (LinearLayout) findViewById(R.id.llundervehicle);
		llspecialoil = (LinearLayout) findViewById(R.id.llspecialoil);
		
		txtwelcome= (TextView) findViewById(R.id.txtwelcome);
		txtequipment= (TextView) findViewById(R.id.txtequipment);
		txtinstruction= (TextView) findViewById(R.id.txtinstruction);
		txtothersearch =(TextView) findViewById(R.id.txtothersearch);
//		txtpolicy= (TextView) findViewById(R.id.txtpolicy);
		txtgenjumpstart = (TextView) findViewById(R.id.txtjumpstart);
		txtmotorjumpstart = (TextView) findViewById(R.id.txtmotorjumpstart);
		txtundervehicle = (TextView) findViewById(R.id.txtundervehicle);
		txtspecialoil = (TextView) findViewById(R.id.txtspecialoil);
		
		llequipment.setOnClickListener(this);
		llinstruction.setOnClickListener(this);
		llothersearch.setOnClickListener(this);
		llgenjumpstart.setOnClickListener(this);
		llmotorjumpstart.setOnClickListener(this);
		llundervehicle.setOnClickListener(this);
		llspecialoil.setOnClickListener(this);
		
//		llpolicy.setOnClickListener(this);
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),"fonts/Existence-Light.otf");
		 txtwelcome.setTypeface(typeface1, Typeface.BOLD);
		 
		 
		 
		Typeface typeface2 = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
		txtinstruction.setTypeface(typeface2, Typeface.BOLD);
	     txtequipment.setTypeface(typeface2, Typeface.BOLD);
	     txtothersearch.setTypeface(typeface2, Typeface.BOLD);
	     txtgenjumpstart.setTypeface(typeface2, Typeface.BOLD);
	     txtmotorjumpstart.setTypeface(typeface2, Typeface.BOLD);
	     txtundervehicle.setTypeface(typeface2, Typeface.BOLD);
	     txtspecialoil.setTypeface(typeface2, Typeface.BOLD);
        
//        txtpolicy.setTypeface(typeface2);
		
		
	
	}
	
//	 public void showAlertDialog(Context context, String title, String message, Boolean status) {
//	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//
//	        // Setting Dialog Title
//	        alertDialog.setTitle(title);
//
//	        // Setting Dialog Message
//	        alertDialog.setMessage(message);
//
//	        // Setting alert dialog icon
//	        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
//
//
//
//	        // Showing Alert Message
//	        alertDialog.show();
//	    }



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.llequipment:
			Intent equipment = new Intent(this,Standardequipment.class);
			startActivity(equipment);
			overridePendingTransition(R.anim.bottom_in, R.anim.left_out);
			break;
			
		case R.id.llinstruction:
			Intent instruction = new Intent(this,General_instruction.class);
			startActivity(instruction);
			overridePendingTransition(R.anim.bottom_in, R.anim.left_out);
			break;
			
		case R.id.llothersearch:
			Intent search = new Intent(this,Search.class);
			startActivity(search);
			overridePendingTransition(R.anim.bottom_in, R.anim.left_out);
			break;
			
		case R.id.llgenjumpstart:
			Intent geninst = new Intent(this,GenJumpStart.class);
			startActivity(geninst);
			overridePendingTransition(R.anim.bottom_in, R.anim.left_out);
			break;
			
		case R.id.llmotorjumpstart:
			Intent motoinst = new Intent(this,MotorCycleJumpStart.class);
			startActivity(motoinst);
			overridePendingTransition(R.anim.bottom_in, R.anim.left_out);
			break;
			
		case R.id.llundervehicle:
			Intent undve = new Intent(this,UnderVehicle.class);
			startActivity(undve);
			overridePendingTransition(R.anim.bottom_in, R.anim.left_out);
			break;
			
		case R.id.llspecialoil:
			Intent sploil = new Intent(this,SpecialOil.class);
			startActivity(sploil);
			overridePendingTransition(R.anim.bottom_in, R.anim.left_out);
			break;

		default:
			break;
		}
		
	}
	
	 @Override
		public void onBackPressed() {
			super.onBackPressed();
			finish();
		
			Intent intent = new Intent(Intent.ACTION_MAIN);
			   intent.addCategory(Intent.CATEGORY_HOME);
			   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			   startActivity(intent);
			   
		}

	
}
