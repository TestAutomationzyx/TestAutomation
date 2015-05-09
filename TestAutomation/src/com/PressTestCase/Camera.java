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
		caselist = Arrays.asList("进入相机","拍照200次","开启/关闭闪光灯100次","开启/关闭特效50次");
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
		case "进入相机":
			gotoCamera();
			break;
		case "拍照200次":
			shut200();
			break;
		case "开启/关闭闪光灯100次":
			flash100();
			break;
		case "开启/关闭特效50次":
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
		step = MyAutoTool.toStep("从桌面进入相机\n");
		MyAutoTool.startatHome("相机");	
		result = MyAutoTool.toResult(MyAutoTool.hasFocus(ActivityName.CAMERA));
		MyAutoTool.addtoFile(TAG,step, result);		
	}
	
	private void shut200() {
		step = MyAutoTool.toStep("拍照200次\n");
		startCamera();
		MyAutoTool.waitforId("com.meizu.media.camera:id/shutter_btn", 1, 10*1000);
		Element shutter = MyAutoTool.searchElementbyId("com.meizu.media.camera:id/shutter_btn", 0, true);
		for(int i=0;i<200;i++){
			step += MyAutoTool.toStep("第"+(i+1)+"次\t");
			MyAutoTool.touch(shutter, 0);
		}
		result = MyAutoTool.toResult(MyAutoTool.hasFocus(ActivityName.CAMERA));
		MyAutoTool.addtoFile(TAG,step, result);		
	}
	
	private void flash100() {
		step = MyAutoTool.toStep("开启/关闭闪光灯100次\n");
		startCamera();
		Element flash = MyAutoTool.searchElementbyId("com.meizu.media.camera:id/flashlight_control", 0, true);
		for(int i=0;i<100;i++){
			step += MyAutoTool.toStep("第"+(i+1)+"次\t");
			MyAutoTool.touch(flash, 0);
		}
		result = MyAutoTool.toResult(MyAutoTool.hasFocus(ActivityName.CAMERA));
		MyAutoTool.addtoFile(TAG,step, result);		
	}
	
	private void special50(){
		step = MyAutoTool.toStep("开启/关闭特效50次\n");
		startCamera();
		for(int i=0;i<50;i++){
			step += MyAutoTool.toStep("第"+(i+1)+"次\t");
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
