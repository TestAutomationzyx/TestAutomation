package com.testcase;

import java.util.ArrayList;
import java.util.List;

public class CaseItem {

	String CASE_TYPE, CASE_MODULE, CASE_STEP;
	//测试案例类，包含测试类型，测试模块，测试步骤三个对象
	public CaseItem(String type, String module, String step) {
		this.CASE_TYPE = type;
		this.CASE_MODULE = module;
		this.CASE_STEP = step;
	}
	
	//添加同一类型及模块，一系列测试步骤的测试案例
	public static List<CaseItem> addCaseItem(String type, String module, List<String> step){
		List<CaseItem> caseItems = new ArrayList<CaseItem>();
		for (int i = 0; i < step.size(); i++) {
			caseItems.add(new CaseItem(type, module, step.get(i)));
		}
		return caseItems;
	}
}
