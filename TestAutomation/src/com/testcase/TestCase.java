package com.testcase;

import java.util.List;

import android.content.Context;

import com.BasicTestCase.BasicCase;
import com.PressTestCase.PressCase;

public class TestCase {

	Context context;
	String TAG = "TestCase";
	
	public TestCase(Context context){
		this.context = context;
	}
	
	public void startCase(String type,List<String> module){
		for(int i=0; i<module.size();i++){
			switch (type) {
			case "»ù±¾":
				new BasicCase(context, module.get(i));
				break;
			case "Ñ¹Á¦":
				new PressCase(context, module.get(i));
				break;

			default:
				break;
			}
		}
	}
}
