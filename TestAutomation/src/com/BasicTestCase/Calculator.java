package com.BasicTestCase;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.element.Position.ElementAttribs;
import com.testautomationservice.AutoTool;

public class Calculator {

	AutoTool MyAutoTool;
	Intent mIntent;
	Context context;
	String TAG = "Calculator",step="";
	List<String> caselist;
	boolean result;
	
	public Calculator(){
		setCaselist();
	}
	public Calculator(Context context){
		this.context = context;
		MyAutoTool = new AutoTool(context);
		initial();
	}
	
	public void method(){
		gotoCallCase();
		returnHomeCase();
		gotoWeiboCase();		
	}
	
	public void setCaselist(){
		caselist = Arrays.asList("�ӷ�","����","�˷�","����");
	}

	public List<String> getCaselist() {
		return caselist;
	}

	private void initial() {
		setCaselist();
		MyAutoTool.returnHome();
	}

	private void returnHomeCase() {
		step = MyAutoTool.toStep("��������");
		MyAutoTool.returnHome();
		result = MyAutoTool.toResult(true);
		MyAutoTool.addtoFile(TAG,step, result);		
	}

	private void gotoWeiboCase() {
		step = MyAutoTool.toStep("���������΢��");
		MyAutoTool.startatHome("΢��");
		result = MyAutoTool.toResult(false);
		MyAutoTool.addtoFile(TAG,step, result);		
	}

	private void gotoCallCase() {
		step = MyAutoTool.toStep("����绰");
		MyAutoTool.touch("�绰", ElementAttribs.TEXT, 0, true, 0);
		MyAutoTool.sleep(1000);           
		result = MyAutoTool.toResult(false);
		MyAutoTool.addtoFile(TAG,step, result);		
	}
	
	public void debugCase(String casename){
		switch (casename) {
		case "�򿪵绰":
			gotoCallCase();
			break;
		case "��������":
			returnHomeCase();
			break;
		case "��΢��":
			gotoWeiboCase();
			break;
		default:
			break;
		}
	}
}
