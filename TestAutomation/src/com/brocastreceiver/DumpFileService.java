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
		super.setTextViewContent("�������󣬵��ͼ���Ի�ȡ���沼��");
	}
	
	@Override
	public void clickIcon() {
		// TODO Auto-generated method stub
		super.clickIcon();
		super.setTextViewContent("���ڻ�ȡ����");
		Position postion = new Position();
		postion.uidump();
		ShellUtils.execCommand("cp /data/local/tmp/dumpfile.xml /sdcard/TestAutomation/dump/", true);
		Toast toast2 = Toast.makeText(getApplicationContext(), "��ȡ�ɹ����ɽ���TestAutomation/dump/Ŀ¼�鿴", Toast.LENGTH_LONG);
		toast2.show();
		super.setTextViewContent("�����»�ȡ");
	}

}
