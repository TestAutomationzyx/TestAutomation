package com.brocastreceiver;

import android.graphics.Color;
import android.widget.Toast;

import com.element.Position;
import com.testautomationclient.R;
import com.utils.ShellUtils;

public class DumpFileService extends FloatingService {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		super.setIconSrc(R.drawable.dump);
		super.setTextBackground(Color.TRANSPARENT);
		super.setTextViewContent("进入界面后，点击图标以获取界面布局");
	}
	
	@Override
	public void clickIcon() {
		// TODO Auto-generated method stub
		super.clickIcon();
		super.setTextViewContent("正在获取……");
		Position postion = new Position();
		postion.uidump();
		ShellUtils.execCommand("cp /data/local/tmp/dumpfile.xml /sdcard/TestAutomation/dump/", true);
		Toast toast2 = Toast.makeText(getApplicationContext(), "获取成功，可进入TestAutomation/dump/目录查看", Toast.LENGTH_LONG);
		toast2.show();
		super.setTextViewContent("可重新获取");
	}

}
