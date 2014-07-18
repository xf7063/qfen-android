package com.qfen.mobile.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.qfen.mobile.R;

public class AppStartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_start);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
//				Intent intent = null;
//				intent = new Intent(AppStartActivity.this, MainActivity.class);
//				startActivity(intent);
				AppStartActivity.this.finish();
			}
		}, 2000);
	}
}
