package com.PressTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.testcase.CaseItem;

import android.content.Context;

public class PressCase {
	
	String TAG="ѹ��";
	List<String> PressModule = Arrays.asList("�绰", "���");
	List<CaseItem> PressItems = new ArrayList<CaseItem>();
	
	//ѹ���������в��԰�����ʼ��
	public PressCase(){
		for(int i=0; i<PressModule.size(); i++){
			switch (PressModule.get(i)) {
			case "�绰":
				PressItems.addAll(CaseItem.addCaseItem(TAG, "�绰", new Phone().getCaselist()));
				break;
			case "���":
				PressItems.addAll(CaseItem.addCaseItem(TAG, "���", new Camera().getCaselist()));
				break;
			default:
				break;
			}
		}
	}
	
	//ִ��ѹ������ĳ��ģ������в��԰���
	public PressCase(Context context,String module){
		switch (module) {
		case "�绰":
			new Phone(context).method();
			break;
		case "���":
			new Camera(context).method();
			break;

		default:
			break;
		}
	}
	
	//ִ��ѹ������ָ��ģ�鼰�����ĳ�����԰���
	public PressCase(Context context,String module,String step){
		switch (module) {
		case "�绰":
			new Phone(context).debugCase(step);
			break;
		case "���":
			new Camera(context).debugCase(step);
			break;

		default:
			break;
		}
	}

	//��ȡѹ���������в��԰�������
	public List<CaseItem> getPressCase(){
		return PressItems;
	}
}
