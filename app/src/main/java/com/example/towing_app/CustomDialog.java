package com.example.towing_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomDialog extends Dialog implements
View.OnClickListener {

public Dialog d;
public Button btn;
Activity mActivity;



public CustomDialog(Activity activity) {  
	
    super(activity); 
    mActivity = activity;
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_custom_dialog);
    btn = (Button) findViewById(R.id.btn);
	btn.setOnClickListener(this);
}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Intent in = new Intent(mActivity , SearchCar.class);
		mActivity.startActivity(in);
	}
 
	
}
