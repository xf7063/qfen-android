package com.qfen.mobile.activity.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.qfen.mobile.R;
import com.qfen.mobile.activity.MainActivity;
import com.qfen.mobile.activity.base.BaseSlidingFragment;
import com.qfen.mobile.view.adapter.ListViewAdapter;

public class MainPageFragment extends BaseSlidingFragment implements OnClickListener {
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;

	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Log.v("handleMessage", msg.toString());
			viewPager.setCurrentItem(currentItem, true);// 切换当前显示的图片
		};
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		imageResId = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
		titles = new String[imageResId.length];
		titles[0] = "巩俐不低俗，我就不能低俗";
		titles[1] = "扑树又回来啦！再唱经典老歌引万人大合唱";
		titles[2] = "揭秘北京电影如何升级";
		titles[3] = "乐视网TV版大派送";
		titles[4] = "热血屌丝的反杀";

		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this.getActivity());
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));
		
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) findViewById(R.id.vp);
		PagerAdapter pageAdapter = new PagerAdapter() {
			@Override
			public int getCount() {
				return imageResId.length;
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(imageViews.get(position));
				return imageViews.get(position);
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView((View) object);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public void restoreState(Parcelable arg0, ClassLoader arg1) {

			}

			@Override
			public Parcelable saveState() {
				return null;
			}

			@Override
			public void startUpdate(View arg0) {

			}

			@Override
			public void finishUpdate(View arg0) {

			}
		};
		viewPager.setAdapter(pageAdapter);//设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}
	
	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 2, 4, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	public void onStop() {
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

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}
	
	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	@Override
	public void initViews() {
		View titleBarView = findViewById(R.id.head_title_bar);
		titleBarView.getBackground().setAlpha(130);
		
		initListView();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 设置相应的监听器
	 */
	@Override
	public void addListener() {
		ImageView iv1 = (ImageView) findViewById(R.id.imageView1);  
		Map<String,Object> data1 = new HashMap<String,Object>();
		data1.put("hover", false);
		iv1.setTag(data1);
		ImageView iv2 = (ImageView) findViewById(R.id.imageView2); 
		Map<String,Object> data2 = new HashMap<String,Object>();
		data2.put("hover", false);
		iv2.setTag(data2);
		
		iv1.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView)v;
				Map<String,Object> data = (Map<String,Object>)v.getTag();
				boolean hover = (boolean)data.get("hover");
				
				 switch (event.getAction()) {
			      case MotionEvent.ACTION_DOWN:
			      {
							iv.setImageResource(R.drawable.btn_mainpage_list_hover);
			      }
			      break;

			      case MotionEvent.ACTION_MOVE:
			      break;

			      case MotionEvent.ACTION_UP:
			      {
			    	  iv.setImageResource(R.drawable.btn_mainpage_list);
			    	  getFragmentActivity(MainActivity.class).getSlidingMenu().showMenu();
			      }
			      break;
			    }
				
				return true;
			}
		});
		iv2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView)v;
				Map<String,Object> data = (Map<String,Object>)v.getTag();
				boolean hover = (boolean)data.get("hover");
				switch (event.getAction()) {
			      case MotionEvent.ACTION_DOWN:
			      {
							iv.setImageResource(R.drawable.btn_pc_hover);
			      }
			      break;

			      case MotionEvent.ACTION_MOVE:
			      break;

			      case MotionEvent.ACTION_UP:
			      {
			    	  iv.setImageResource(R.drawable.btn_pc);
			    	  getFragmentActivity(MainActivity.class).getSlidingMenu().showSecondaryMenu();
			      }
			      break;
			    }
				
				return true;
			}
		});
	}
	
//	String[] nameList = {"Miley Cyruc","Alice Keys","Jewel","Dublin","Kelly Clarkson",  
//            "Mariah Carey","Sheen","Adele","Avril Lavigne","Taylor Swift"};  
	String[] nameList = {
			"峨眉山温泉酒店旅游套票",
			"大型山水实景演出《道解都江堰》",
			"中国泰迪熊博物馆",
			"成都海宁皮革城跨年大让利",
			"感恩时节成都欢乐谷日场全价门票",  
            "领馆国际城 春季大回馈",
            "2014成都（花舞人间）郁金香节",
            "红楼梦境-2014郑绪岚成都演唱会",
            "地音乐课堂（GMC）同城学习交流活动组织者",
            "【树舍出品】魔界体验——magic分享沙龙"};  

	public void initListView() {
		ListView lv = (ListView) findViewById(R.id.listView1);  
	      
	    //lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//选择效果  
	    
	    // 得到一个ListViewAdapter对象
	    ListViewAdapter lva = new ListViewAdapter(MainPageFragment.this.getActivity());
		lv.setAdapter(lva);
		// 为ListView绑定Adapter
		/* 为ListView添加点击事件 */
		lv.setOnItemClickListener(new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.v("MyListViewBase", "你点击了ListView条目" + position);// 在LogCat中输出信息
			}
		});

	}
}
