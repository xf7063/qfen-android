package com.qfen.mobile.activity.sliding;

import java.util.Arrays;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.qfen.mobile.R;
import com.qfen.mobile.activity.AccountBalancesActivity;
import com.qfen.mobile.activity.AccountSettingActivity;
import com.qfen.mobile.activity.ActivityActivity;
import com.qfen.mobile.activity.FjjActivity;
import com.qfen.mobile.activity.MainActivity;
import com.qfen.mobile.activity.base.BaseSlidingFragment;
import com.qfen.mobile.activity.fragments.MainPageFragment;
import com.qfen.mobile.common.ActivityHelper;
import com.qfen.mobile.ui.FollowFragment;

/**
 * 左侧菜单的Fragment
 * @author zhangkeqi
 *
 */
public class LeftFragment extends BaseSlidingFragment {
	
	//左侧菜单项对应的Fragment数组，和leftMenuItemLayout变量中的序号对应
	public final static Class[] FRAGMENTS_CLASSES = { MainPageFragment.class, FollowFragment.class,FollowFragment.class};
	//左侧菜单项布局数组 和 FRAGMENTS_CLASSES 数组变量序号对应
	private View[] mLeftMenuItemLayout;
	//保存当前被选中的菜单项序号
	private int mCurrentLeftMenuItemIndex = -1;
	
	private Bitmap mLoadingBitmap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.sliding_left);
		setData();
		changeMenuByClass(MainPageFragment.class);
	}

	@Override
	public void initViews() {
		mLeftMenuItemLayout = new View[] { 
				findViewById(R.id.left_menuitem_home_layout),
				findViewById(R.id.left_menuitem_fjj_layout),
				findViewById(R.id.left_menuitem_qhd_layout)};
	}

	@Override
	public void addListener() {
		for (int i = 0; i < mLeftMenuItemLayout.length; i++) {
			//设置菜单项布局变量在leftMenuItemLayout数组中的序号到菜单项布局变量中的tag中去。可以理解setTag是android中view组件存数据的地方
			mLeftMenuItemLayout[i].setTag(i);
			mLeftMenuItemLayout[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//当菜单项布局有click事件发生时，拿出存到菜单项布局变量中的内容，这里用getTag()，因为存的是序号，所以拿出来的内容就是菜单项序号值，然后根据该值作相应的操作
					int index = (Integer) v.getTag();
					//根据被选中菜单项序号做相应的功能操作
					changeMenuByIndex(index);
					
					dispatchMenuItemAction(index);
				}
			});
		}
	}

	private void setData() {
	}

	/**
	 * 通过索引改变Menu
	 * 
	 * @param index
	 */
	@SuppressWarnings("unchecked")
	private void changeMenuByIndex(int index) {
		Class<? extends Fragment> clazz = null;
		if (mCurrentLeftMenuItemIndex != index) {
			clearMenu(mCurrentLeftMenuItemIndex);
			setMenuChecked(index);
		}
		
		//获取被选中菜单项对应的frament
		clazz = FRAGMENTS_CLASSES[index];
		//调用MainActivity中的switchCenterFragment方法，显示被选中菜单项对应的frament
		getFragmentActivity(MainActivity.class).switchCenterFragment(clazz);
	}

	/**
	 * 通过Fragment类改变menu
	 * 
	 * @param clazz
	 */
	public void changeMenuByClass(Class<? extends Fragment> clazz) {
		int index = Arrays.asList(FRAGMENTS_CLASSES).indexOf(clazz);
		if (index != -1) {
			changeMenuByIndex(index);
		}
	}

	/**
	 * 清除菜单项布局上被选中的状态，或者或复到默认状态
	 */
	@SuppressWarnings("deprecation")
	private void clearMenu(int index) {
		if(index >= 0 && index < mLeftMenuItemLayout.length) {
			mLeftMenuItemLayout[index].setBackgroundDrawable(null);
		}
	}

	/**
	 * 设置菜单项被选中的状态
	 * @param index
	 */
	private void setMenuChecked(int index) {
		mLeftMenuItemLayout[index].setBackgroundResource(R.color.side_menuitem_checked_color);
		//保存被选中菜单项序号到currentLeftMenuItemIndex变量
		mCurrentLeftMenuItemIndex = index;
	}


	@Override
	public void onDestroy() {
		if (mLoadingBitmap != null && !mLoadingBitmap.isRecycled()) {
			mLoadingBitmap.recycle();
			mLoadingBitmap = null;
		}

		super.onDestroy();
	}
	
	private void dispatchMenuItemAction(int index) {
		if(index == 1) {
			ActivityHelper.switchActivity(this.getActivity(),FjjActivity.class);
		}
	}
}
