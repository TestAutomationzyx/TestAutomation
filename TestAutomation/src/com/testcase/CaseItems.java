package com.testcase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.widget.Toast;

import com.BasicTestCase.BasicCase;
import com.PressTestCase.PressCase;

public class CaseItems {

	List<CaseItem> CASE_ITEMS = new ArrayList<CaseItem>();

	//���԰������Ĺ��췽�����������в��԰���
	public CaseItems() {
		CASE_ITEMS.addAll(new BasicCase().getBasicCase());
		CASE_ITEMS.addAll(new PressCase().getPressCase());
	}

	//��ȡ���в��԰���
	public List<CaseItem> getCaseItems() {
		return CASE_ITEMS;
	}

	//��ȡ����ָ��������-ģ�顱�����в���
	public CharSequence[] getStep(String type, String module) {
		List<String> steps = new ArrayList<String>();
		for (int i = 0; i < CASE_ITEMS.size(); i++) {
			if (CASE_ITEMS.get(i).CASE_TYPE.equals(type)
					&& CASE_ITEMS.get(i).CASE_MODULE.equals(module))
				steps.add(CASE_ITEMS.get(i).CASE_STEP);
		}
		return (CharSequence[]) steps.toArray(new String[0]);

	}

	//��ȡָ���������͵�����ģ��
	public CharSequence[] getModule(String type) {
		Set<String> modules = new HashSet<String>();
		for (int i = 0; i < CASE_ITEMS.size(); i++) {
			if (CASE_ITEMS.get(i).CASE_TYPE.equals(type))
				modules.add(CASE_ITEMS.get(i).CASE_MODULE);
		}
		return (CharSequence[]) modules.toArray(new String[0]);
	}

	//��ȡ���еĲ��԰�������
	public CharSequence[] getTypes() {
		Set<String> types = new HashSet<String>();
		for (int i = 0; i < CASE_ITEMS.size(); i++)
			types.add(CASE_ITEMS.get(i).CASE_TYPE);
		return (CharSequence[]) types.toArray(new String[0]);
	}

	//�ж�ָ�������еĲ��Բ��裨CASE_ITEMS�����Ƿ����ָ����caseitem
	public boolean isExists(CaseItem caseitem) {
		for (CaseItem ct : CASE_ITEMS)
			if (ct.CASE_TYPE.equals(caseitem.CASE_TYPE)
					&& ct.CASE_MODULE.equals(caseitem.CASE_MODULE)
					&& ct.CASE_STEP.equals(caseitem.CASE_STEP))
				return true;
		return false;
	}

	//ִ��ĳ��ָ���Ĳ��԰���
	public void startCase(Context context, CaseItem caseitem) {
		if (isExists(caseitem))
			switch (caseitem.CASE_TYPE) {
			case "����":
				new BasicCase(context, caseitem.CASE_MODULE, caseitem.CASE_STEP);
				break;
			case "ѹ��":
				new PressCase(context, caseitem.CASE_MODULE, caseitem.CASE_STEP);
				break;

			default:
				break;
			}
		else {
			Toast toast = Toast.makeText(context, "��ѡ���Բ��費���ڣ�������ѡ��",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
