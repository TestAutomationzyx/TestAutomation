package com.PressTestCase;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.element.ActivityName;
import com.element.Position.Element;
import com.testautomationservice.AutoTool;

public class Camera {

	AutoTool MyAutoTool;
	Intent mIntent;
	Context context;
	String TAG = "Camera",step="";
	List<String> caselist;
	boolean result;
	
	public Camera(){
		setCaselist();
	}
	
	public Camera(Context context){
		this.context = context;
		MyAutoTool = new AutoTool(context,true);
		start();
	}
	
	public void setCaselist(){
		caselist = Arrays.asList("�������","����200��","����/�ر������100��","����/�ر���Ч50��");
	}

	public List<String> getCaselist() {
		return caselist;
	}
	
	public void method(){
		gotoCamera();
		shut200();	
		flash100();
		special50();
	}
	
	public void debugCase(String casename){
		switch (casename) {
		case "�������":
			gotoCamera();
			break;
		case "����200��":
			shut200();
			break;
		case "����/�ر������100��":
			flash100();
			break;
		case "����/�ر���Ч50��":
			special50();
			break;
		default:
			break;
		}
	}

	private void start() {
		setCaselist();
		MyAutoTool.returnHome();
	}
	
	//----------------case--------------//

	private void gotoCamera() {
		step = MyAutoTool.toStep("������������\n");
		MyAutoTool.startatHome("���");	
		result = MyAutoTool.toResult(MyAutoTool.hasFocus(ActivityName.CAMERA));
		MyAutoTool.addtoFile(TAG,step, result);		
	}
	
	private void shut200() {
		step = MyAutoTool.toStep("����200��\n");
		startCamera();
		MyAutoTool.waitforId("com.meizu.media.camera:id/shutter_btn", 1, 10*1000);
		Element shutter = MyAutoTool.searchElementbyId("com.meizu.media.camera:id/shutter_btn", 0, true);
		for(int i=0;i<200;i++){
			step += MyAutoTool.toStep("��"+(i+1)+"��\t");
			MyAutoTool.touch(shutter, 0);
		}
		result = MyAutoTool.toResult(MyAutoTool.hasFocus(ActivityName.CAMERA));
		MyAutoTool.addtoFile(TAG,step, result);		
	}
	
	private void flash100() {
		step = MyAutoTool.toStep("����/�ر������100��\n");
		startCamera();
		Element flash = MyAutoTool.searchElementbyId("com.meizu.media.camera:id/flashlight_control", 0, true);
		for(int i=0;i<100;i++){
			step += MyAutoTool.toStep("��"+(i+1)+"��\t");
			MyAutoTool.touch(flash, 0);
		}
		result = MyAutoTool.toResult(MyAutoTool.hasFocus(ActivityName.CAMERA));
		MyAutoTool.addtoFile(TAG,step, result);		
	}
	
	private void special50(){
		step = MyAutoTool.toStep("����/�ر���Ч50��\n");
		startCamera();
		for(int i=0;i<50;i++){
			step += MyAutoTool.toStep("��"+(i+1)+"��\t");
			MyAutoTool.touchId("com.meizu.media.camera:id/filter_control", 0, i==0, 0);
		}
		result = MyAutoTool.toResult(MyAutoTool.hasFocus(ActivityName.CAMERA));
		MyAutoTool.addtoFile(TAG,step, result);		
	}
	//---------------//
	
	private void startCamera(){
		if(!MyAutoTool.hasFocus(ActivityName.CAMERA))
			MyAutoTool.startActivity(ActivityName.CAMERA);
	}
}
