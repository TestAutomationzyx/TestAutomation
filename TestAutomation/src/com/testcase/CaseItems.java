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

	//测试案例集的构造方法，加入所有测试案例
	public CaseItems() {
		CASE_ITEMS.addAll(new BasicCase().getBasicCase());
		CASE_ITEMS.addAll(new PressCase().getPressCase());
	}

	//获取所有测试案例
	public List<CaseItem> getCaseItems() {
		return CASE_ITEMS;
	}

	//获取所有指定“类型-模块”的所有步骤
	public CharSequence[] getStep(String type, String module) {
		List<String> steps = new ArrayList<String>();
		for (int i = 0; i < CASE_ITEMS.size(); i++) {
			if (CASE_ITEMS.get(i).CASE_TYPE.equals(type)
					&& CASE_ITEMS.get(i).CASE_MODULE.equals(module))
				steps.add(CASE_ITEMS.get(i).CASE_STEP);
		}
		return (CharSequence[]) steps.toArray(new String[0]);

	}

	//获取指定案例类型的所有模块
	public CharSequence[] getModule(String type) {
		Set<String> modules = new HashSet<String>();
		for (int i = 0; i < CASE_ITEMS.size(); i++) {
			if (CASE_ITEMS.get(i).CASE_TYPE.equals(type))
				modules.add(CASE_ITEMS.get(i).CASE_MODULE);
		}
		return (CharSequence[]) modules.toArray(new String[0]);
	}

	//获取所有的测试案例类型
	public CharSequence[] getTypes() {
		Set<String> types = new HashSet<String>();
		for (int i = 0; i < CASE_ITEMS.size(); i++)
			types.add(CASE_ITEMS.get(i).CASE_TYPE);
		return (CharSequence[]) types.toArray(new String[0]);
	}

	//判断指定的所有的测试步骤（CASE_ITEMS）中是否包含指定的caseitem
	public boolean isExists(CaseItem caseitem) {
		for (CaseItem ct : CASE_ITEMS)
			if (ct.CASE_TYPE.equals(caseitem.CASE_TYPE)
					&& ct.CASE_MODULE.equals(caseitem.CASE_MODULE)
					&& ct.CASE_STEP.equals(caseitem.CASE_STEP))
				return true;
		return false;
	}

	//执行某个指定的测试案例
	public void startCase(Context context, CaseItem caseitem) {
		if (isExists(caseitem))
			switch (caseitem.CASE_TYPE) {
			case "基本":
				new BasicCase(context, caseitem.CASE_MODULE, caseitem.CASE_STEP);
				break;
			case "压力":
				new PressCase(context, caseitem.CASE_MODULE, caseitem.CASE_STEP);
				break;

			default:
				break;
			}
		else {
			Toast toast = Toast.makeText(context, "所选调试步骤不存在，请重新选择！",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
