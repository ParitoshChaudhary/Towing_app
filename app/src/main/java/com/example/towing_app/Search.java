package com.example.towing_app;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Search extends CommonActivity implements OnClickListener {
	LinearLayout arrow1, arrow2, arrow3, SearchHome,manufacturer,model,layoutyear;
	TextView manufacturename, modelname, year;
	String carName, carModel, carYear;
	SharedPreferences sharedPreferences;
	Button searchbtn;
	TextView txt,txtmanufacture,txtmodel,txtyear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		

		if(!isNetworkAvailable()){
			AlertDialog.Builder b;
			AlertDialog alt;
			b = new AlertDialog.Builder(Search.this);
			b.setMessage("Intenet Connection Problem. Please connect to Internet.");
			b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(Search.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			});
			b.show();
		}
		
		else 
		{

		txt = (TextView) findViewById(R.id.txtsearch);
		txtmanufacture = (TextView) findViewById(R.id.txtmanufacture);
		txtmodel = (TextView) findViewById(R.id.txtmodel);
		txtyear = (TextView) findViewById(R.id.txtYear);
		
		
		Typeface typeface1 = Typeface.createFromAsset(getAssets(),
				"fonts/Existence-Light.otf");
		txt.setTypeface(typeface1, Typeface.BOLD);

		manufacturer = (LinearLayout) findViewById(R.id.manufacturer);
		model = (LinearLayout) findViewById(R.id.model);
		layoutyear = (LinearLayout) findViewById(R.id.layoutyear);

		SearchHome = (LinearLayout) findViewById(R.id.search_home);
		SearchHome.setOnClickListener(this);

		manufacturer.setOnClickListener(this);
		model.setOnClickListener(this);
		layoutyear.setOnClickListener(this);
		manufacturename = (TextView) findViewById(R.id.manufacturename);
		modelname = (TextView) findViewById(R.id.modelname);
		year = (TextView) findViewById(R.id.year);
		
		
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Helvetica.otf");
		txtmanufacture.setTypeface(typeface,Typeface.BOLD);
		txtmodel.setTypeface(typeface,Typeface.BOLD);
		txtyear.setTypeface(typeface,Typeface.BOLD);
		manufacturename.setTypeface(typeface,Typeface.BOLD);
		modelname.setTypeface(typeface,Typeface.BOLD);
		year.setTypeface(typeface,Typeface.BOLD);
		
		
		Bundle extras = getIntent().getExtras();
		
		if (extras != null)
		{
		carName = extras.getString("carName");
		carModel = extras.getString("carModel");
		carYear= extras.getString("carYear");
		manufacturename.setText(carName);
		if (carModel == null)
		{
			modelname.setText("Select");
		}
		else 
		{
			modelname.setText(carModel);
		}
		
		if (carYear == null)
		{
			year.setText("Select");
		}
		else
		{
		year.setText(carYear);
		}
		}
		
		else {
		
		manufacturename.setText("Select");
		modelname.setText("Select");
		year.setText("Select");
		}
		//		sharedPreferences = this.getSharedPreferences(
//				AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
//		sharedPreferences.edit();

//		carName = String
//				.valueOf(sharedPreferences.getString("carName", "Name"));
//		carModel = String.valueOf(sharedPreferences.getString("carModel",
//				"Model"));
//		carYear = String.valueOf(sharedPreferences.getString("carYear",
//				"CarYear"));

		
		searchbtn = (Button) findViewById(R.id.searchbtn);
		searchbtn.setOnClickListener(this);
		
		
		sharedPreferences = getSharedPreferences(
				AllManufacture.MyPREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		carName = manufacturename.getText().toString();
		carModel = modelname.getText().toString();
		carYear = year.getText().toString();
		
		editor.putString("carName", carName);
		editor.putString("carModel", carModel);
		editor.putString("carYear", carYear);
		editor.commit();
	}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.manufacturer:
			Intent allmanufactures = new Intent(this, AllManufacture.class);
			startActivity(allmanufactures);
			break;

		case R.id.model:
			Intent allmodels = new Intent(this, AllModels.class);
			startActivity(allmodels);

			break;

		case R.id.layoutyear:
			Intent year = new Intent(this, Year.class);
			startActivity(year);

			break;

		case R.id.searchbtn:
			
			
			if (carName.equals("") || carModel.equals("") || carYear.equals("") 
					) {
				
				new AlertDialog.Builder(Search.this)
				.setTitle("Missing Details")
				.setMessage("Please fill all details")
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog,
									int which) {
							}
						})
				.show();
				
			}
			
			else if (carName.equals("Select") || carModel.equals("Select") || carYear.equals("Select"))
			{
				new AlertDialog.Builder(Search.this)
				.setTitle("Missing Details")
				.setMessage("Please fill all details")
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog,
									int which) {
							}
						})
				.show();
			}
			else 
			{
//				CustomDialog cdd=new CustomDialog(Search.this);
//	            cdd.show();
				Intent search = new Intent(Search.this, CarInfo.class);
				startActivity(search);
				
			}
//			new AlertDialog.Builder(Search.this)
//			.setTitle("Caution")
//			.setMessage(carName  + "\n" + carModel + "\n" + carYear)
////			.setMessage(carModel)
////			.setMessage(carYear)
//			
//			.setPositiveButton(android.R.string.yes,
//					new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog,
//								int which) {
//							
//							Intent search = new Intent(Search.this, CarInfo.class);
//							startActivity(search);
//							
//						}
//					}).setIcon(android.R.drawable.ic_dialog_alert)
//			.show();
			
			

			break;

		case R.id.search_home:
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

		Intent intent = new Intent(Search.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

	}

}
