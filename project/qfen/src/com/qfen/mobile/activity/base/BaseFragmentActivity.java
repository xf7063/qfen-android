package com.qfen.mobile.activity.base;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.view.View.OnClickListener;

public class BaseFragmentActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		
	}

	@Override
	protected void onResume() {
		super.onResume();
	
	}
	
	public Fragment getFragmentByTag(Class<? extends Fragment> clazz) {
		return getSupportFragmentManager().findFragmentByTag(clazz.getName());
	}
	
	public Fragment getFragmentByTag(String tag) {
		return getSupportFragmentManager().findFragmentByTag(tag);
	}
	
	/**
	 * �?��
	 */
	public void exitApp(final android.content.DialogInterface.OnClickListener listener ) {
	
	}

	/**
	 * 添加引导图片
	 */
	public void addGuideImage(int rootViewId,int guideResourceId) {
		addGuideImage(rootViewId, guideResourceId, null);
	}
	/**
	 * 添加引导图片
	 */
	public void addGuideImage(int rootViewId,int guideResourceId,final OnClickListener listener) {
	
	}
}
