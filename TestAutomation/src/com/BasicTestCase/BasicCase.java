package com.BasicTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;

import com.testcase.CaseItem;

public class BasicCase {

	String TAG = "基本";
	List<String> BasicModule = Arrays.asList("电话", "计算器", "设置");
	List<CaseItem> BasicItems = new ArrayList<CaseItem>();

	//基本类型所有测试案例初始化
	public BasicCase() {
		for(int i=0; i<BasicModule.size();i++){
			switch (BasicModule.get(i)) {
			case "电话":
				BasicItems.addAll(CaseItem.addCaseItem(TAG, "电话", new Phone().getCaselist()));
				break;
			case "计算器":
				BasicItems.addAll(CaseItem.addCaseItem(TAG, "计算器", new Calculator().getCaselist()));
				break;
			case "设置":
				BasicItems.addAll(CaseItem.addCaseItem(TAG, "设置", new Setting().getCaselist()));
				break;
			default:
				break;
			}
		}
	}

	//执行基本类型某个模块的所有测试案例
	public BasicCase(Context context, String module) {
		switch (module) {
		case "电话":
			new Phone(context).method();
			break;
		case "计算器":
			new Calculator(context).method();
		case "设置":
			new Setting(context).method();

		default:
			break;
		}
	}

	//执行基本类型指定模块及步骤的某个测试案例
	public BasicCase(Context context, String module, String step) {
		switch (module) {
		case "电话":
			new Phone(context).debugCase(step);
			break;
		case "计算器":
			new Calculator(context).debugCase(step);
		case "设置":
			new Setting(context).debugCase(step);

		default:
			break;
		}
	}
	
	//获取基本类型所有测试案例名称
	public List<CaseItem> getBasicCase(){
		return BasicItems;
	}
}
