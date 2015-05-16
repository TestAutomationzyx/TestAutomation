package com.brocastreceiver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;

public class LogCatReceiver extends Service {
	
	private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
	private static final String LOG_PATH = SDCARD_PATH+"/TestAutomation/log";
	private static final String LOG_FILE = LOG_PATH + "/log_verbose.txt";
	
	private static final String CMD_SU = "sh";
    private static final String CMD_LINE_END = "\n";
    private static final String CMD_EXITS = "exit\n";
    private static final String CMD_GET_lOG = "logcat -v time *:V >" + LOG_FILE;
    
    Process process;
    DataOutputStream os = null;
    InputStream is = null;
    String command;

	@Override
	public void onCreate() {
		super.onCreate();
		File logFile = new File(LOG_PATH);
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
	
	public void getLog() {
		try {
			// root权限才可以查看全局log,否则只能查看本应用log
			process = Runtime.getRuntime().exec(CMD_SU);
			os = new DataOutputStream(process.getOutputStream());
			command = CMD_GET_lOG;
			os.write(command.getBytes());
			os.writeBytes(CMD_LINE_END);
			os.flush();
			os.writeBytes(CMD_EXITS);
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
			File log_info = new File(LOG_FILE);
			if(!errorTime.equals("notError")){
				File log_error = new File(LOG_PATH+"/"+intent.getStringExtra("errorTime")+".txt");
				log_info.renameTo(log_error);
			}else{
				if (log_info.exists())
					log_info.delete();
			}
			getLog();		
		}		
	}
}
