package com.BasicTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;

import com.testcase.CaseItem;

public class BasicCase {

	String TAG = "����";
	List<String> BasicModule = Arrays.asList("�绰", "������", "����");
	List<CaseItem> BasicItems = new ArrayList<CaseItem>();

	//�����������в��԰�����ʼ��
	public BasicCase() {
		for(int i=0; i<BasicModule.size();i++){
			switch (BasicModule.get(i)) {
			case "�绰":
				BasicItems.addAll(CaseItem.addCaseItem(TAG, "�绰", new Phone().getCaselist()));
				break;
			case "������":
				BasicItems.addAll(CaseItem.addCaseItem(TAG, "������", new Calculator().getCaselist()));
				break;
			case "����":
				BasicItems.addAll(CaseItem.addCaseItem(TAG, "����", new Setting().getCaselist()));
				break;
			default:
				break;
			}
		}
	}

	//ִ�л�������ĳ��ģ������в��԰���
	public BasicCase(Context context, String module) {
		switch (module) {
		case "�绰":
			new Phone(context).method();
			break;
		case "������":
			new Calculator(context).method();
		case "����":
			new Setting(context).method();

		default:
			break;
		}
	}

	//ִ�л�������ָ��ģ�鼰�����ĳ�����԰���
	public BasicCase(Context context, String module, String step) {
		switch (module) {
		case "�绰":
			new Phone(context).debugCase(step);
			break;
		case "������":
			new Calculator(context).debugCase(step);
		case "����":
			new Setting(context).debugCase(step);

		default:
			break;
		}
	}
	
	//��ȡ�����������в��԰�������
	public List<CaseItem> getBasicCase(){
		return BasicItems;
	}
}
