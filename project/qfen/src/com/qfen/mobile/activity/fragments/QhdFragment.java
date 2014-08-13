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

public class QhdFragment extends BaseSlidingFragment {
	private TextView tv_title;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_qhd);
	}

	@Override
	public void initViews() {
		View titleBarView = findViewById(R.id.head_title_bar);
		titleBarView.getBackground().setAlpha(130);
		
		initListView();
	}
	
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
	public void initListView() {
		ListView lv = (ListView) findViewById(R.id.listView1);  
	      
	    //lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//选择效果  
	    
	    // 得到一个ListViewAdapter对象
	    ListViewAdapter lva = new ListViewAdapter(QhdFragment.this.getActivity());
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
