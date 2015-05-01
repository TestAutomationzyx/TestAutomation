package com.testautomationservice;

import java.util.List;

import com.utils.UIDump;

public interface ToolApi {
	
	String adbShell(String cmd);
	void sendKeyCode(int keycode);
	String getSimOperator();
	String getElementValuebyId(String id,int index,boolean fresh,String item);
	String getCurrentPackageAndActicity();
	String getCurrentPackage();
	String getCurrentActivity();
	boolean hasFocus(String activity);
	String closeApplication(String apppackage);
	String closeCurrentApplicaion();
	boolean touch(String e, int type, int index, boolean fresh, long times);
	boolean touchText(String e, int index, boolean fresh,long times);
	boolean touchId(String id, int index, boolean fresh,long times);
	void touch(double x, double y,long times);
	boolean slidebyId(String id1, String id2,boolean fresh,long times);
	boolean waitforId(String id,int index,long times);
	boolean waitforText(String text,int index,long times);
	boolean isPlaying();
	boolean hasSIMCard();
	void operateWifi(boolean open);
	void slideScreenLeft();
	void slideScreenRight();
	void slideScreenTop();
	void slideScreenButtom();
	void sleep(long times);
	void returnHome();
	void startatHome(String appname);
	void startActivity(String activity);
	void inputText(String text);
	void clearText(String text);
	void copytoClipboard(String text);
	void pasteText(String text,boolean fresh);
	String screenShot(String name);
	void insertContact(String given_name, String phone,int count,boolean oneperson);
	void deleteAllContact();
	void insertCallLog(String phone, int count, boolean oneperson);
	void deleteAllCallLog();
	void insertMessage(String phone, String content, int count, boolean oneperson);
	void deleteAllMessage();	
	String toStep(String step);
	boolean toResult(boolean result);
	void addtoFile(String file, String step,boolean result);
	List<UIDump> getUIDump(boolean fresh);
	
}
