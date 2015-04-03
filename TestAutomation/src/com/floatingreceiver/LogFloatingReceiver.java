package com.floatingreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class LogFloatingReceiver extends FloatingService {

	String mLog;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		super.setTextViewSize(800, 500);
		logShow();
	}

	public class LogReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			mLog = intent.getStringExtra("log");
			setTextViewContent(mLog);
		}		
	}
	
	private void logShow() {
		LogReceiver myLogReceiver = new LogReceiver();
		IntentFilter logIntentFilter = new IntentFilter();
		logIntentFilter.addAction("logFloating");
		registerReceiver(myLogReceiver, logIntentFilter);		
	}
	
	@Override
	public void setTextViewContent(String string) {
		// TODO Auto-generated method stub
		super.setTextViewContent(string);
	}
	
}
