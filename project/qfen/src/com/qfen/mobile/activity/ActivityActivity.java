package com.qfen.mobile.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qfen.mobile.R;

public class ActivityActivity extends Activity {

	private ViewPager viewPager;//页卡内容
	private ImageView imageView;// 动画图片
	private ImageView ivBack;// 返回图片
	private TextView textView1,textView2,textView3;
	private List<View> views;// Tab页面列表
	private List<TextView> textViews;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1,view2,view3;//各个页卡
	private Button btnPubActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity);
		initImageView();
		initViewPager();
		initTextView();
		initBackImageView();
		initButtons();
	}

	private void initViewPager() {
		viewPager=(ViewPager) findViewById(R.id.vPager);
		views=new ArrayList<View>();
		textViews = new ArrayList<TextView>();
		LayoutInflater inflater=getLayoutInflater();
		view1=inflater.inflate(R.layout.aa_lay1, null);
		view2=inflater.inflate(R.layout.aa_lay2, null);
		view3=inflater.inflate(R.layout.aa_lay3, null);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	 /**
	  *  初始化头标
	  */

	private void initTextView() {
		textView1 = (TextView) findViewById(R.id.aa_text1);
		textView1.setTag(1);
		textViews.add(textView1);
		textView2 = (TextView) findViewById(R.id.aa_text2);
		textView2.setTag(2);
		textViews.add(textView2);
		textView3 = (TextView) findViewById(R.id.aa_text3);
		textView3.setTag(3);
		textViews.add(textView3);

		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
	}

	private void initImageView() {
		imageView= (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.cursor_bar).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	private void initButtons() {
		btnPubActivity = (Button)findViewById(R.id.btn_pub_activity);
		btnPubActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = null;
				intent = new Intent(ActivityActivity.this,PubActivityActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initBackImageView() {
		ivBack= (ImageView) findViewById(R.id.aa_iv_back);
		ivBack.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView)v;
				switch (event.getAction()) {
			      case MotionEvent.ACTION_DOWN:
			      {
			    	  iv.setImageResource(R.drawable.icon_back_hover);
			    	  Intent intent = null;
			    	  intent = new Intent(ActivityActivity.this,MainActivity.class);
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

	/** 
	 *     
	 * 头标点击监听 3 */
	private class MyOnClickListener implements OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
        	index=i;
        }
		public void onClick(View v) {
			viewPager.setCurrentItem(index);			
		}
		
	}
	
	public class MyViewPagerAdapter extends PagerAdapter{
		private List<View> mListViews;
		
		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) 	{	
			container.removeView(mListViews.get(position));
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {			
			 container.addView(mListViews.get(position), 0);
			 return mListViews.get(position);
		}

		@Override
		public int getCount() {			
			return  mListViews.size();
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {			
			return arg0==arg1;
		}
	}

    public class MyOnPageChangeListener implements OnPageChangeListener{

    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageScrollStateChanged(int arg0) {
			
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			
		}

		public void onPageSelected(int arg0) {
			/*
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
				
			}
			*/
			textViews.get(arg0).setTextColor(getResources().getColor(R.color.aa_focus_text));
			textViews.get(currIndex).setTextColor(getResources().getColor(R.color.aa_default_text));
			
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
			Toast.makeText(ActivityActivity.this, "您选择了"+ viewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show();
		}
    	
    }
}
