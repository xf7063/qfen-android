package com.qfen.mobile.activity;

import java.lang.ref.WeakReference;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.qfen.mobile.R;
import com.qfen.mobile.activity.base.BaseFragmentActivity;
import com.qfen.mobile.activity.base.BaseSlidingFragment;
import com.qfen.mobile.activity.sliding.LeftFragment;
import com.qfen.mobile.activity.sliding.RightFragment;
import com.qfen.mobile.slidingmenu.SlidingMenu;
import com.qfen.mobile.ui.MainHallFragment;

public class MainActivity extends BaseFragmentActivity {
	private Fragment mCurrentFragment;
	public static SlidingMenu mSlidingMenu;
	private Handler handler = new MyHandler(this);
	private static class MyHandler extends Handler {
		private final WeakReference<MainActivity> mActivity;

		public MyHandler(MainActivity activity) {
			mActivity = new WeakReference<MainActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			MainActivity activity = mActivity.get();
			if (activity == null) {
				return;
			}
			activity.handleMsg(msg);
		}
	}
	
	private void handleMsg(Message msg) {
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		

		if (savedInstanceState != null) {
			mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mCurrentContent");
		}
	}

	/**
	 * 该方法你用见“http://www.cnblogs.com/hanyonglu/archive/2012/03/28/2420515.html”
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mCurrentContent", mCurrentFragment);
	}
	private void initViews() {
		mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingmenu);
		mSlidingMenu.setMenu(R.layout.sliding_left_frame);
		if (getFragmentByTag(LeftFragment.class) == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.left_frame, new LeftFragment(), LeftFragment.class.getName()).commit();
		}

		mSlidingMenu.setContent(R.layout.sliding_center_frame);

		mSlidingMenu.setSecondaryMenu(R.layout.sliding_right_frame);
		if (getFragmentByTag(RightFragment.class) == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.right_frame, new RightFragment(), RightFragment.class.getName()).commit();
		}

		if (mCurrentFragment != null) {
			postSwitchFragment();
		}
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
	/**
	 * slidingMenu中的内容Fragment切换(左侧菜单触发)
	 * 
	 * @param clazz
	 */
	public void switchCenterFragment(Class<? extends Fragment> clazz) {
		try {
			if (mSlidingMenu == null) {
				removeAllFragments();
				return;
			}

			boolean isInit = false;
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			Fragment userFragment = fm.findFragmentByTag(clazz.getName());
			if (userFragment == null) {
				isInit = true;
				try {
					userFragment = clazz.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (mCurrentFragment != null && mCurrentFragment != userFragment) {
				ft.hide(mCurrentFragment);
			}

			if (!userFragment.isAdded() && isInit) {
				ft.add(R.id.center_frame, userFragment, clazz.getName());
			} else {
				ft.show(userFragment);
			}

			ft.commitAllowingStateLoss();

//			mSlidingMenu.showContent();
			mCurrentFragment = userFragment;
//			if (MainHallFragment.class.getName().equals(clazz.getName())) {
//				mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
//				if (!isInit) {
//					((MainHallFragment) userFragment).postScrollTop();
//				}
//				
//			} 
			if (MainHallFragment.class.getName().equals(clazz.getName())) {
//				mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
				mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
				if (!isInit) {
					((MainHallFragment) userFragment).postScrollTop();
					
				}

			} else {

				mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
				
			}
			
			postShowContent(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * lidingMenu中的内容Fragment内容过滤(右侧菜单触发)
	 * 
	 * @param clazz
	 * @param type
	 */
	public void filterCenterFragment(Class<? extends BaseSlidingFragment> clazz, int type) {
		BaseSlidingFragment userFragment = (BaseSlidingFragment) getFragmentByTag(clazz);
		if (userFragment != null) {
			userFragment.filter(type);
		}
		if (mSlidingMenu != null)
			mSlidingMenu.showContent();
	}
	/**
	 * 延迟切换Fragment
	 */
	private void postSwitchFragment() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				switchCenterFragment(mCurrentFragment.getClass());
			}
		}, 50);

	}
	
	/**
	 * 清除FragmentManager中所有Fragment
	 */
	private void removeAllFragments() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		for (int i = 0; i < LeftFragment.FRAGMENTS_CLASSES.length; i++) {
			Fragment fragment = getFragmentByTag(LeftFragment.FRAGMENTS_CLASSES[i].getName());
			if (fragment != null) {
				ft.remove(fragment);
			}
		}
		ft.commitAllowingStateLoss();
	}
	
	/**
	 * 延时mSlidingMenu.showContent()
	 * 
	 * @param delayMillis 延时时间 单位毫秒
	 */
	private void postShowContent(long delayMillis) {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (mSlidingMenu!=null && !MainActivity.this.isFinishing()) {
					mSlidingMenu.showContent();					
				}
			}
		}, delayMillis);
	}
	
	public SlidingMenu getSlidingMenu() {
		return  mSlidingMenu;
	}
}
