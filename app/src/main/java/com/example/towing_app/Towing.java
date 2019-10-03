package com.example.towing_app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class Towing extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_towing);
		
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					Log.e("Runn", "run");
					sleep(2000);
					
						Intent i = new Intent(Towing.this,
								Disclaimer.class);
						startActivity(i);
					}

				 catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		splashThread.start();
	}

	}

	

