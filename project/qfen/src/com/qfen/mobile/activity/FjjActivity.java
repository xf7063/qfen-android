package com.qfen.mobile.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.qfen.mobile.R;
import com.qfen.mobile.common.ActivityHelper;

public class FjjActivity extends Activity {

	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	
	private Button btnBack;//返回图片
	
	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;
	
	// 切换当前显示的图片
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem,true);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fjj_activity);
		initImageView();
		initViewPager();
		initBackButton();
	}

	private void initBackButton() {
		btnBack= (Button) findViewById(R.id.fjj_btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActivityHelper.switchActivity(FjjActivity.this, MainActivity.class);
			}
		});
	}

	private void initViewPager() {
		viewPager = (ViewPager)findViewById(R.id.fjj_vp); 
		PagerAdapter pagerAdapter = new PagerAdapter() {

			@Override
			public int getCount() {
				return imageResId.length;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(imageViews.get(position));
				return imageViews.get(position);
			}
			
			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView((View)object);
			}
		};
		viewPager.setAdapter(pagerAdapter);
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			private int oldPosition = 0;
			@Override
			public void onPageSelected(int position) {
				currentItem = position;
				tv_title.setText(titles[position]);
				dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
				dots.get(position).setBackgroundResource(R.drawable.dot_focused);
				oldPosition = position;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initImageView() {
		imageResId = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
		titles = new String[imageResId.length];
		titles[0] = "巩俐不低俗，我就不能低俗";
		titles[1] = "扑树又回来啦！再唱经典老歌引万人大合唱";
		titles[2] = "揭秘北京电影如何升级";
		titles[3] = "乐视网TV版大派送";
		titles[4] = "热血屌丝的反杀";
		
		imageViews = new ArrayList<ImageView>();
		for(int i=0;i<imageResId.length;i++) {
			ImageView iv = new ImageView(this);
			iv.setImageResource(imageResId[i]);
			iv.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(iv);
		}
		
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.fjj_v_dot0));
		dots.add(findViewById(R.id.fjj_v_dot1));
		dots.add(findViewById(R.id.fjj_v_dot2));
		dots.add(findViewById(R.id.fjj_v_dot3));
		dots.add(findViewById(R.id.fjj_v_dot4));
		
		tv_title = (TextView)findViewById(R.id.fjj_vp_tv_title);
		tv_title.setText(titles[0]);
	}
	
	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 2, 4, TimeUnit.SECONDS);
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}
	
	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			synchronized(viewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();//通过Handler切换图片
			}
		}
	}
}
