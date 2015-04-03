package com.module;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.element.Position.ElementAttribs;
import com.testautomationservice.AutoTool;

public class TestCase {

	String TAG = "TestCase";
	AutoTool MyAutoTool;
	Intent mIntent;
	Context context;
	
	public TestCase(Context context){
		this.context = context;
		MyAutoTool = new AutoTool(context);
	}
	
	public void method(){
		MyAutoTool.toStep("进入电话");
		MyAutoTool.touch("电话", ElementAttribs.TEXT, 1, true, 0);
		MyAutoTool.sleep(1000);
		MyAutoTool.toResult(true);
		MyAutoTool.screenShot("testing");
		MyAutoTool.toStep("返回桌面");
		Log.e(TAG, MyAutoTool.getCurrentPackageAndActicity());
		MyAutoTool.returnHome();
		MyAutoTool.toStep("右滑");
		MyAutoTool.slideScreenRight();
		MyAutoTool.toStep("左滑");
		MyAutoTool.slideScreenLeft();
		MyAutoTool.toResult(false);
	}
}
