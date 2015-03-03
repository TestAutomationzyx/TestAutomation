package com.testautomationservice;

import com.utils.ShellUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class AdbService extends Service{
	
	String PHONE_ACTIVITY = "com.android.dialer/com.android.dialer.DialtactsActivity";
	
	@Override
	public IBinder onBind(Intent arg0) {
		Log.e("---------->", "---------->Adb远程服务：onBind");
		return new MyBinder();
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.e("---------->", "---------->Adb远程服务：onUnbind");
		return super.onUnbind(intent);
	}
	
	@Override
	public void onCreate() {
		Log.e("---------->", "---------->Adb远程服务：onCreate");
		System.out.println("Adb远程服务：onCreate");
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		Log.e("---------->", "---------->Adb远程服务：onDestroy");
		super.onDestroy();
	}
	
	class MyBinder extends IService.Stub{
		
		@Override
		public void callServiceMethod() {
			Log.e("---------->", "---------->callServiceMethod");
			getproperty();
			TestCase();
		}
	}
	
	private void TestCase() {
		ShellUtils.execCommand(new String[] {
								"cp /sdcard/UiAutomator.jar /data/local/tmp/",
								"touch /data/local/tmp/dumpfile.xml",
								"chmod 777 /data/local/tmp/dumpfile.xml",
								"input keyevent 3",
								"input tap 133 400",
								"uiautomator runtest UiAutomator.jar -c com.uia.example.my.Test",
								"cp /data/local/tmp/dumpfile.xml /sdcard",
								"input keyevent 3" }, false);
//		ShellUtils.execCommand("input keyevent 3", false, false);
//		ShellUtils.execCommand(new String[] {"input keyevent 3"}, false);
//		Position p = new Position();
//		Element e = p.findElementByText("微博");
//		ShellUtils.execCommand("input tap " + e.getX() + " " + e.getY(),false);
	}
	
	private String getproperty(){
		Log.e("---------->", "---------->"+System.getProperty("java.io.tmpdir"));
		return System.getProperty("java.io.tmpdir");
	}
	
}
