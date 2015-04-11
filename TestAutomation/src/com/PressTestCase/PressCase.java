package com.PressTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.testcase.CaseItem;

import android.content.Context;

public class PressCase {
	
	String TAG="压力";
	List<String> PressModule = Arrays.asList("电话", "相机");
	List<CaseItem> PressItems = new ArrayList<CaseItem>();
	
	//压力类型所有测试案例初始化
	public PressCase(){
		for(int i=0; i<PressModule.size(); i++){
			switch (PressModule.get(i)) {
			case "电话":
				PressItems.addAll(CaseItem.addCaseItem(TAG, "电话", new Phone().getCaselist()));
				break;
			case "相机":
				PressItems.addAll(CaseItem.addCaseItem(TAG, "相机", new Camera().getCaselist()));
				break;
			default:
				break;
			}
		}
	}
	
	//执行压力类型某个模块的所有测试案例
	public PressCase(Context context,String module){
		switch (module) {
		case "电话":
			new Phone(context).method();
			break;
		case "相机":
			new Camera(context).method();
			break;

		default:
			break;
		}
	}
	
	//执行压力类型指定模块及步骤的某个测试案例
	public PressCase(Context context,String module,String step){
		switch (module) {
		case "电话":
			new Phone(context).debugCase(step);
			break;
		case "相机":
			new Camera(context).debugCase(step);
			break;

		default:
			break;
		}
	}

	//获取压力类型所有测试案例名称
	public List<CaseItem> getPressCase(){
		return PressItems;
	}
}
