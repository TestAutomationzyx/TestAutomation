package com.testautomationclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Intent;
import android.os.IBinder;

public class OpenLog extends FloatingService {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Process process;
		try {
			process = Runtime.getRuntime().exec("logcat -d time *:V ");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			StringBuffer log = new StringBuffer();
			while((line = bufferedReader.readLine())!=null){
				log.append(line);				  
			}
			setText(log.toString());	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onBind(intent);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void setText(String string) {
		super.setText(string, 800,500);
	}
}
