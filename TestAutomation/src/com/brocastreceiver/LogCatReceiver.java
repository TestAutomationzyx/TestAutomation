package com.brocastreceiver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;

public class LogCatReceiver extends Service {
	
	String sdcardPath = Environment.getExternalStorageDirectory().getPath();
	String logPath = sdcardPath+"/TestAutomation/log";
	Process process;
	
	@Override
	public void onCreate() {
		super.onCreate();
		File logFile = new File(logPath);
		if(!logFile.exists())
			logFile.mkdir();
		getLog();
		clearLog();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getLog(){
		DataOutputStream os;
		try {
			process = Runtime.getRuntime().exec("sh");
			os = new DataOutputStream(process.getOutputStream());
			String command = "logcat -v time *:V >" + logPath + "/log_verbose.txt";
			os.write(command.getBytes());
			os.writeBytes("\n");
			os.flush();
			os.writeBytes("exit\n");
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void clearLog(){
		LogReceiver myLogReceiver = new LogReceiver();
		IntentFilter logIntentFilter = new IntentFilter();
		logIntentFilter.addAction("endCase");
		registerReceiver(myLogReceiver, logIntentFilter);
	}
	
	public class LogReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {	
			process.destroy();
			String errorTime = intent.getStringExtra("errorTime");
			File log_info = new File(logPath + "/log_verbose.txt");
			if(!errorTime.equals("notError")){
				File log_error = new File(logPath+"/"+intent.getStringExtra("errorTime")+".txt");
				log_info.renameTo(log_error);
			}else{
				if(log_info.exists())
					log_info.delete();
			}
			getLog();
		}		
	}

}
