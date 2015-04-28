package com.brocastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;

public class StepFloatingReceiver extends FloatingService {
	
	String mStep="",mResult="";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		super.setTextBackground(Color.TRANSPARENT);
		stepShow();
	}
	
	public class StepReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if((mResult = intent.getStringExtra("result")).equals("")){
				setTextColor(Color.rgb(255, 218, 185));
				mStep = intent.getStringExtra("step");
				setTextViewContent(mStep);
			}else{
				if(mResult.equals("false"))
					setTextColor(Color.rgb(255, 99, 71));
				setTextViewContent(getTextViewContent() + "\t"+mResult);					
			}			
		}
		
	}
	
	public void stepShow(){
		StepReceiver myStepReceiver = new StepReceiver();
		IntentFilter stepIntentFilter = new IntentFilter();
		stepIntentFilter.addAction("stepFloating");
		registerReceiver(myStepReceiver, stepIntentFilter);
	}
	
	@Override
	public void setTextViewContent(String string) {
		// TODO Auto-generated method stub
		super.setTextViewContent(string);
	}
	
	@Override
	public String getTextViewContent() {
		// TODO Auto-generated method stub
		return super.getTextViewContent();
	}
	
	@Override
	public void setTextColor(int color) {
		// TODO Auto-generated method stub
		super.setTextColor(color);
	}
	
}
