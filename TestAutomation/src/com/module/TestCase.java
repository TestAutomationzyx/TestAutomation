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
//		MyAutoTool.touch("�绰", ElementAttribs.TEXT, 1, true, 0);
//		MyAutoTool.sleep(1000);
//		MyAutoTool.pasteText("����ճ������",true);
		MyAutoTool.screenShot("testing");
	}
}
