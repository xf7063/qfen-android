package com.qfen.mobile.activity.sliding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.qfen.mobile.R;
import com.qfen.mobile.activity.AccountBalancesActivity;
import com.qfen.mobile.activity.AccountSettingActivity;
import com.qfen.mobile.activity.ActivityActivity;
import com.qfen.mobile.activity.base.BaseSlidingFragment;
public class RightFragment extends BaseSlidingFragment {
	private boolean isLoading = false;//是否正在加载
	
	//右侧菜单项布局数组
	private View[] mRightMenuItemLayout;
	//保存当前被选中的菜单项序号
	private int mCurrentLeftMenuItemIndex = -1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding_right);
		setMenuChecked(0);
	}

	@Override
	public void initViews() {
		mRightMenuItemLayout = new View[] {
				findViewById(R.id.right_menuitem_share_layout),
				findViewById(R.id.right_menuitem_activity_layout),
				findViewById(R.id.right_menuitem_discount_layout),
				findViewById(R.id.right_menuitem_residualamount_layout),
				findViewById(R.id.right_menuitem_sponsor_layout),
				findViewById(R.id.right_menuitem_accountsetting_layout),
				findViewById(R.id.right_menuitem_faq_layout)};
	}

	@Override
	public void addListener() {
		for (int i = 0; i < mRightMenuItemLayout.length; i++) {
			mRightMenuItemLayout[i].setTag(i);
			mRightMenuItemLayout[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int index = (Integer)v.getTag();
					changeMenuByIndex(index);
					
					dispatchMenuItemAction(index);
				}
			});
		}
	}
	
	public void changeMenuByIndex(int index) {
		if(mCurrentLeftMenuItemIndex != index) {
			clearMenu(mCurrentLeftMenuItemIndex);
			setMenuChecked(index);
		}
	}

	private void clearMenu(int index) {
		if(index >= 0 && index < mRightMenuItemLayout.length) {
			mRightMenuItemLayout[index].setBackgroundDrawable(null);
		}
	}

	private void setMenuChecked(int index) {
		mRightMenuItemLayout[index].setBackgroundResource(R.color.side_menuitem_checked_color);
		//保存被选中菜单项序号到currentLeftMenuItemIndex变量
		mCurrentLeftMenuItemIndex = index;
	}
	
	private void dispatchMenuItemAction(int index) {
		if(index == 1) {
			Intent intent = null;
			intent = new Intent(this.getActivity(), ActivityActivity.class);
			startActivity(intent);
		}
		if(index == 3) {
			Intent intent = null;
			intent = new Intent(this.getActivity(), AccountBalancesActivity.class);
			startActivity(intent);
		}
		if(index == 5) {
			Intent intent = null;
			intent = new Intent(this.getActivity(), AccountSettingActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
