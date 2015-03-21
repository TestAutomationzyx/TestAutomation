package com.testautomationclient;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tabhost.CallLogFragment;
import com.tabhost.ContactFragment;
import com.tabhost.MessageFragment;

@SuppressLint("ResourceAsColor")
public class DataOperator extends FragmentActivity implements OnClickListener,OnPageChangeListener {
	
	private List<Fragment> fragmentList;
	private MyPagerViewAdapter pagerAdapter;
	private FragmentManager manager;
	private ViewPager viewpager;
	private ImageView deletekey;
	private Context context;	
	private Toast toast;
	private TextView[] titles = new TextView[3];
	private int current_index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getApplication();
		setContentView(R.layout.data);
		
		manager = getSupportFragmentManager();
		
		deletekey = (ImageView) findViewById(R.id.deletekey);
		titles[0] = (TextView) findViewById(R.id.contact);
		titles[1] = (TextView) findViewById(R.id.message);
		titles[2] = (TextView) findViewById(R.id.calllog);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		
		titles[0].setOnClickListener(this);
		titles[1].setOnClickListener(this);
		titles[2].setOnClickListener(this);
		
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new ContactFragment());
		fragmentList.add(new MessageFragment());
		fragmentList.add(new CallLogFragment());
		
		pagerAdapter = new MyPagerViewAdapter(manager, fragmentList);
		viewpager.setAdapter(pagerAdapter);
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(this);
		
		deletekey.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);
				context.getContentResolver().delete(Uri.parse("content://sms"), null, null);
				context.getContentResolver().delete(RawContacts.CONTENT_URI, null, null);
				toast = Toast.makeText(context, "通话记录，联系人，短信已全部清除", Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}
	
	private class MyPagerViewAdapter extends FragmentPagerAdapter{

		private List<Fragment> fragmentList;
		
		public MyPagerViewAdapter(FragmentManager fm,List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		@Override
		public Fragment getItem(int index) {
			return fragmentList.get(index);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}
		
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int index) {
		titles[current_index].setTextColor(Color.WHITE);
		titles[index].setTextColor(R.color.LightBlue);	
		current_index = index;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.contact:
			viewpager.setCurrentItem(0);
			break;
		case R.id.message:
			viewpager.setCurrentItem(1);
			break;
		case R.id.calllog:
			viewpager.setCurrentItem(2);
			break;
		default:
			break;
		}
		
	}	
}
