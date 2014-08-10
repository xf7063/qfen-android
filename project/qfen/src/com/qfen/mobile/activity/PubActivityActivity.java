package com.qfen.mobile.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qfen.mobile.R;

public class PubActivityActivity extends Activity {
	private ViewPager viewPager;//页卡内容
	private List<View> viewList;// Tab页面列表
	private View view1,view2,view3,view4;//各个页卡
	private ImageView ivBack;//返回图片
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pub_activity_activity);
		
		initViewPager();
		initBackImageView();
	}
	
	private void initViewPager() {
		viewPager=(ViewPager) findViewById(R.id.pa_vPager);
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.pub_activity_select_type, null);
		view2=inflater.inflate(R.layout.pub_activity_select_category, null);
		view3=inflater.inflate(R.layout.pub_activity_add_info, null);
		view4=inflater.inflate(R.layout.pub_activity_over, null);
		
		viewList = new ArrayList<View>();
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);
		viewList.add(view4);
		PagerAdapter pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {

				return arg0 == arg1;
			}

			@Override
			public int getCount() {

				return viewList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(viewList.get(position));

			}

			@Override
			public int getItemPosition(Object object) {

				return super.getItemPosition(object);
			}


			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));
				return viewList.get(position);
			}

		};
		
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(0);
	}
	
	private void initBackImageView() {
		ivBack= (ImageView) findViewById(R.id.pa_category_iv_back);
		ivBack.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView)v;
				switch (event.getAction()) {
			      case MotionEvent.ACTION_DOWN:
			      {
			    	  iv.setImageResource(R.drawable.icon_back_hover);
			    	  Intent intent = null;
			    	  intent = new Intent(PubActivityActivity.this,ActivityActivity.class);
			    	  startActivity(intent);
			      }
			      break;

			      case MotionEvent.ACTION_MOVE:
			      break;

			      case MotionEvent.ACTION_UP:
			      {
			    	  iv.setImageResource(R.drawable.icon_back);
			      }
			      break;
			    }
				
				return true;
			}
		});
	}
}
