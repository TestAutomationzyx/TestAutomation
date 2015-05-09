package com.testautomationclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.Intent;

import com.brocastreceiver.LogFloatingReceiver;

public class OpenLog {

	Process process;
	Context context;
	
	public OpenLog(Context context) {
		this.context = context;
		Intent lIntent = new Intent(context, LogFloatingReceiver.class);
		context.startService(lIntent);
		logcat();
	}
	
	public void logcat(){
		DataOutputStream os;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			String command = "logcat -v time *:V";
			os.write(command.getBytes());
			os.writeBytes("\n");
			os.flush();
			os.writeBytes("exit\n");
			os.flush();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuffer log = new StringBuffer();
			String line;
			while((line = bufferedReader.readLine())!=null){				
				log.append(line);
				Intent mIntent = new Intent("com.floatingreceiver.LogFloatingReceiver");
				mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mIntent.putExtra("log", log.toString());
				mIntent.setAction("logFloating");
				context.sendBroadcast(mIntent);
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}
