package com.testcase;

import java.util.ArrayList;
import java.util.List;

public class CaseItem {

	String CASE_TYPE, CASE_MODULE, CASE_STEP;
	//���԰����࣬�����������ͣ�����ģ�飬���Բ�����������
	public CaseItem(String type, String module, String step) {
		this.CASE_TYPE = type;
		this.CASE_MODULE = module;
		this.CASE_STEP = step;
	}
	
	//���ͬһ���ͼ�ģ�飬һϵ�в��Բ���Ĳ��԰���
	public static List<CaseItem> addCaseItem(String type, String module, List<String> step){
		List<CaseItem> caseItems = new ArrayList<CaseItem>();
		for (int i = 0; i < step.size(); i++) {
			caseItems.add(new CaseItem(type, module, step.get(i)));
		}
		return caseItems;
	}
}
