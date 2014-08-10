package com.qfen.mobile.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfen.mobile.R;

/*** 
 * 自定义ListView子类，继承ListView 
 * @author Administrator 
 * 
 */  
public class ListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局 /*构造函数*/

	public ListViewAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return 30;
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	/* 书中详细解释该方法 */
	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {
		ListViewHolder holder;
		// 观察convertView随ListView滚动情况
		Log.v("MyListViewBase", "getView " + position + " " + convertView);
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.mainpagefragment_listview_item, null);
			holder = new ListViewHolder();
			/* 得到各个控件的对象 */
			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.textView2);
			holder.tvAddr = (TextView) convertView
					.findViewById(R.id.textView3);
			holder.tvTotalBonus = (Button) convertView
					.findViewById(R.id.button1);
			holder.tvLeavingBonus = (Button) convertView
					.findViewById(R.id.button2);
			holder.ivShare = (ImageView) convertView
					.findViewById(R.id.ivCircleShare);
			
			convertView.setTag(holder);// 绑定ListViewHolder对象
		} else {
			holder = (ListViewHolder) convertView.getTag();// 取出ListViewHolder对象
		}
		
		/* 设置TextView显示的内容，即我们存放在动态数组中的数据 */
//		设置当TextView中的文字超过TextView的容量时，用省略号代替
//		只需要下边的设置：
//		textview.setSingleLine(); 
//		textview.setEllipsiz(TextUtils.TruncateAt.valueOf("END"));
//		在xml中设置如下：
//		android:singleLine="true"
//		android:ellipsize="end"
		
		holder.tvTitle.setText("峨眉山温泉酒店旅游套票"+position);
		holder.tvTime.setText("开始时间:2014-01-10 结束时间:2014-06-30");
		holder.tvAddr.setText("地点:四川峨眉山灵秀温泉");
		holder.tvTotalBonus.setText("总奖金：3000");
		holder.tvLeavingBonus.setText("剩余奖金：2734.6");

		/* 为Button添加点击事件 */
		holder.tvTotalBonus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v("MyListViewBase", "你点击了按钮[总奖金]:" + position);// 打印Button的点击信息
			}
		});
		
		holder.ivShare.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView iv = (ImageView)v;
				Log.v("ivShare", "你onTouch了ivShare:hasFocus=" + event.getAction());// 打印Button的点击信息
				switch (event.getAction()) {
			      case MotionEvent.ACTION_DOWN:
			      {
			    	  iv.setImageResource(R.drawable.icon_circle_share_hover);
			      }
			      break;

			      case MotionEvent.ACTION_MOVE:
			      break;

			      case MotionEvent.ACTION_UP:
			      {
			    	  iv.setImageResource(R.drawable.icon_circle_share);
			      }
			      break;
			    }
				
				return true;
			}
		});

		return convertView;
	}
}
