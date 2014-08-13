package com.qfen.mobile.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.qfen.mobile.R;

public class AccountSettingActivity extends Activity {
	private Button btnBack;//返回图片
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_setting_activity);
		
		initViewPager();
		initBackImageView();
	}
	
	private void initViewPager() {
	}
	
	private void initBackImageView() {
		btnBack= (Button) findViewById(R.id.asa_btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = null;
				intent = new Intent(AccountSettingActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
}
