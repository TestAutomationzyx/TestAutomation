package com.BasicTestCase;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.element.Position.ElementAttribs;
import com.testautomationservice.AutoTool;

public class Setting {
	AutoTool MyAutoTool;
	Intent mIntent;
	Context context;
	String TAG = "Setting", step = "";
	List<String> caselist;
	boolean result;

	public Setting() {
		setCaselist();
	}

	public Setting(Context context) {
		this.context = context;
		MyAutoTool = new AutoTool(context,true);
		initial();
	}

	public void method() {
		gotoCallCase();
		returnHomeCase();
		gotoWeiboCase();
	}

	public void setCaselist() {
		caselist = Arrays.asList("个性化", "账户", "网络", "显示");
	}

	public List<String> getCaselist() {
		return caselist;
	}

	private void initial() {
		setCaselist();
		MyAutoTool.returnHome();
	}

	private void returnHomeCase() {
		step = MyAutoTool.toStep("返回桌面");
		MyAutoTool.returnHome();
		result = MyAutoTool.toResult(true);
		MyAutoTool.addtoFile(TAG, step, result);
	}

	private void gotoWeiboCase() {
		step = MyAutoTool.toStep("从桌面进入微博");
		MyAutoTool.startatHome("微博");
		result = MyAutoTool.toResult(false);
		MyAutoTool.addtoFile(TAG, step, result);
	}

	private void gotoCallCase() {
		step = MyAutoTool.toStep("进入电话");
		MyAutoTool.touch("电话", ElementAttribs.TEXT, 0, true, 0);
		MyAutoTool.sleep(1000);
		result = MyAutoTool.toResult(false);
		MyAutoTool.addtoFile(TAG, step, result);
	}

	public void debugCase(String casename) {
		switch (casename) {
		case "打开电话":
			gotoCallCase();
			break;
		case "返回桌面":
			returnHomeCase();
			break;
		case "打开微博":
			gotoWeiboCase();
			break;
		default:
			break;
		}
	}

}
