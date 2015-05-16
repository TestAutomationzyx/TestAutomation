package com.testautomationclient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class LoadingActivity extends Activity {

	private ProgressDialog mDialog; 
	private Intent mIntent,nIntent;
	private int type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mDialog = ProgressDialog.show(this, "加载中...", "正在加载，请稍后！", true, false);		
		thread.start();
	}
	
	Thread thread = new Thread(new Runnable() {
		
		@Override
		public void run() {	
			mHandler.sendEmptyMessage(0);
		}
	});
	
	//处理跳转到Activity
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) { 
			startIntent(type);
			LoadingActivity.this.finish();
			if (mDialog.isShowing()) 
				mDialog.dismiss();
		}
	};
	
	public void startIntent(int type){
		nIntent = getIntent();
		type = nIntent.getIntExtra("type", 0);
		mIntent = new Intent();
		switch (type) {
		case 0:
			mIntent.setClass(LoadingActivity.this, Information.class);
			startActivity(mIntent);
			break;
		case 1:
			mIntent.setClass(LoadingActivity.this, More.class);
			startService(mIntent);
		default:
			break;
		}
	}
}
