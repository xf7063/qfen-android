package com.qfen.mobile.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityHelper {
	
	public static void switchActivity(Context packageContext, Class<?> target) {
		Intent intent = null;
		intent = new Intent(packageContext, target);
		packageContext.startActivity(intent);
	}
	
	/**
	 * 切换activity
	 * @param packageContext
	 * @param target
	 * @param overPakageContext
	 */
	public static void switchActivity(Context packageContext, Class<?> target,boolean overPakageContext) {
		Intent intent = null;
		intent = new Intent(packageContext, target);
		packageContext.startActivity(intent);
		
		if(overPakageContext) {
			if(packageContext instanceof Activity) {
				((Activity)packageContext).finish();
			}
		}
	}
}
