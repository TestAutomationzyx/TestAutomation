package com.module;

import android.content.Context;

import com.element.AndroidKeyCode;
import com.testautomationservice.AutoTool;

public class TestCase {

	AutoTool MyAutoTool;
	
	public TestCase(Context context){
		MyAutoTool = new AutoTool(context);	
	}
	
	public void method(){
		MyAutoTool.sendKeyCode(AndroidKeyCode.HOME);
//		MyAutoTool.touch("电话", ElementAttribs.TEXT, 1, true, 0);
//		MyAutoTool.sleep(1000);
//		MyAutoTool.pasteText("测试粘贴功能",true);
		MyAutoTool.screenShot("testing");
	}
}
