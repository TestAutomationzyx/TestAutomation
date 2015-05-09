package com.testautomationservice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.telephony.TelephonyManager;

import com.brocastreceiver.LogCatReceiver;
import com.brocastreceiver.StepFloatingReceiver;
import com.element.ActivityName;
import com.element.AdbShell;
import com.element.AndroidKeyCode;
import com.element.Position;
import com.element.Position.Element;
import com.element.Position.ElementAttribs;
import com.utils.UIDump;

@SuppressLint("SimpleDateFormat")
public class AutoTool implements ToolApi {
	
	AdbShell adbTool;
	Position position;
	Context context;
	ClipboardManager clipboardManager;
	TelephonyManager telephonyManager;
	WifiManager wifiManager;
	int stepNum=1,tempNum=1;
	long stepTimes=0,allTimes=0;
	String errorTime;
	String sdcardPath = Environment.getExternalStorageDirectory().getPath();
	String reportPath = sdcardPath+"/TestAutomation/report";

	public AutoTool(Context context,boolean isTotest) {
		this.context = context;
		if(isTotest){
			Intent sIntent = new Intent(context, StepFloatingReceiver.class);
			context.startService(sIntent);
			Intent lIntent = new Intent(context, LogCatReceiver.class);
			context.startService(lIntent);
		}
		adbTool = new AdbShell();
		sendKeyCode(AndroidKeyCode.HOME);
		position = new Position();
	}

	public String adbShell(String cmd) {
		return adbTool.adbshell(cmd);
	}

	@Override
	public void sendKeyCode(int keycode) {
		adbTool.sendKeyEvent(keycode);
	}

	@Override
	public String getElementValuebyId(String id, int index, boolean fresh, int attri) {
			
		ArrayList<HashMap<Integer, String>> data = position.getAttribs(ElementAttribs.RESOURCE_ID, id,fresh);
		if (data.size()<=index)
			return null;
		return data.get(index).get(attri);
	}
	
	@Override
	public String getCurrentPackageAndActicity() {
		return adbTool.getCurrentPackageAndActicity();
	}

	@Override
	public String getCurrentPackage() {
		return adbTool.getCurrentPackageName();
	}

	@Override
	public String getCurrentActivity() {
		return adbTool.getCurrentActivity();
	}
	
	@Override
	public boolean hasFocus(String activity) {
		return getCurrentPackageAndActicity().equals(activity);
	}

	@Override
	public String closeApplication(String apppackage) {
		return adbTool.adbshell("am force-stop "+apppackage);
	}

	@Override
	public String closeCurrentApplicaion() {
		return closeApplication(getCurrentPackage());
	}

	@Override
	public void startActivity(String activity) {
		adbTool.startActivity(activity);
	}

	@Override
	public boolean touch(String e, int type, int index, boolean fresh, long times) {
		List<Element> eList = position.findElements(type, e,fresh);
		if(eList.size()<=index)
			return false;
		else{
			Element e1 = eList.get(index);
			adbTool.touch(e1);
			return true;
		}
	}
	
	@Override
	public boolean touchText(String e, int index, boolean fresh, long times) {
		return touch(e, ElementAttribs.TEXT, index, fresh, times);
	}

	@Override
	public boolean touchId(String id, int index, boolean fresh, long times) {
		return touch(id, ElementAttribs.RESOURCE_ID, index, fresh, times);
	}

	@Override
	public void touch(double x, double y, long times) {
		if(times==0)
			adbTool.touch(x, y);
		else
			adbTool.longPress(x, y, times);
	}

	@Override
	public boolean slidebyId(String id1, String id2, boolean fresh,long times) {
		boolean flag = false;
		Element e1 = position.findElementById(id1,fresh);
		Element e2 = position.findElementById(id2,false);
		if(e1!=null && e2!=null){
			adbTool.swipe(e1, e2, times);
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean waitforId(String id, int index, long times) {
		long startTimes = System.currentTimeMillis();
		while ((System.currentTimeMillis() - startTimes) <= times) {
			sleep(500);
			if (position.findElementsById(id,true).size()>index+1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean waitforText(String text, int index, long times) {
		if(times==0)
			return position.findElementsByText(text, true).size()>=index+1;
		long startTimes = System.currentTimeMillis();
		while ((System.currentTimeMillis() - startTimes) <= times) {
			sleep(500);
			if (position.findElementsByText(text, true).size()>=index+1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPlaying() {
		AudioManager audioManage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		return audioManage.isMusicActive();
	}

	@Override
	public boolean hasSIMCard() {
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getSimState()!=TelephonyManager.SIM_STATE_ABSENT;
	}

	@Override
	public void operateWifi(boolean open) {
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(open);
	}

	@Override
	public void slideScreenLeft() {
		adbTool.swipetoLeft();
	}

	@Override
	public void slideScreenRight() {
		adbTool.swipetoRight();
	}

	@Override
	public void slideScreenTop() {
		adbTool.swipetoTop();
	}

	@Override
	public void slideScreenButtom() {
		adbTool.swipetoButtom();
	}

	@Override
	public void sleep(long times) {
		adbTool.sleep(times);
	}

	@Override
	public void returnHome() {
		while(!hasFocus(ActivityName.LAUNCHER))
			sendKeyCode(AndroidKeyCode.BACK);
	}

	@Override
	public void inputText(String text) {
		adbTool.inputText(text);
	}

	@Override
	public void clearText(String text) {
		adbTool.clearText(text);
	}

	@Override
	public void copytoClipboard(String text) {
		clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));  
	}

	@Override
	public void pasteText(String text,boolean fresh) {
		copytoClipboard(text);
		Element e = position.findElementByClass("android.widget.EditText",fresh);
		adbTool.touch(e);
		adbTool.sleep(500);
		adbTool.touch(0.2315,0.1823);
	}
	
	@Override
	public String screenShot(String name) {
		String image = name+ "_" + errorTime + ".png";
		adbTool.adbshell("screencap -p /sdcard/TestAutomation/image/" + image);
		return image;
	}

	@Override
	public void insertContact(String given_name, String phone,int count,boolean oneperson) {
		if(oneperson){ 
	    	ContentValues values = new ContentValues();        
		    Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values); 
	    	long rawContactId = ContentUris.parseId(rawContactUri);
	    	values.clear(); 
		    values.put(Data.RAW_CONTACT_ID, rawContactId); 
		    values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); 
		    values.put(StructuredName.GIVEN_NAME, given_name); 
		    context.getContentResolver().insert(Data.CONTENT_URI, values);
		    
		    for(int i=0; i<count;i++){
		    	values.clear(); 
			    values.put(Data.RAW_CONTACT_ID, rawContactId); 
			    values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE); 
			    values.put(Phone.NUMBER, count+i); 
			    values.put(Phone.TYPE,Phone.TYPE_MOBILE); 
			    context.getContentResolver().insert(Data.CONTENT_URI, values);
		    }
	    }else{
	    	for(int i=0; i<count; i++){
	    		ContentValues values = new ContentValues();        
	    	    Uri rawContactUri = context.getContentResolver().insert(RawContacts.CONTENT_URI, values); 
	    		long rawContactId = ContentUris.parseId(rawContactUri);
	    		values.clear(); 
	    	    values.put(Data.RAW_CONTACT_ID, rawContactId); 
	    	    values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); 
	    	    values.put(StructuredName.GIVEN_NAME, given_name+i); 
	    	    context.getContentResolver().insert(Data.CONTENT_URI, values);
	    	        
	    	    values.clear(); 
	    	    values.put(Data.RAW_CONTACT_ID, rawContactId); 
	    	    values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE); 
	    	    values.put(Phone.NUMBER, phone); 
	    	    values.put(Phone.TYPE,Phone.TYPE_MOBILE); 
	    	    context.getContentResolver().insert(Data.CONTENT_URI, values);
	    	}
	    }
	}

	@Override
	public void deleteAllContact() {
		context.getContentResolver().delete(RawContacts.CONTENT_URI, null, null);
	}

	@Override
	public void insertCallLog(String phone, int count, boolean oneperson) {
		for (int i = 0; i < count; i++) {
			ContentValues values = new ContentValues();
			if (oneperson)
				values.put(CallLog.Calls.NUMBER, phone);
			else
				values.put(CallLog.Calls.NUMBER, Integer.valueOf(phone + i));
			values.put(CallLog.Calls.TYPE, 1);
			values.put(CallLog.Calls.DATE, System.currentTimeMillis() + i);//来电时间
			values.put(CallLog.Calls.DURATION, "10000");//通话时长
			context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
		}
	}

	@Override
	public void deleteAllCallLog() {
		context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);
	}

	@Override
	public void insertMessage(String phone, String content, int count, boolean oneperson) {
		for(int i=0; i<count; i++){
			ContentValues values = new ContentValues();
			if (oneperson)
				values.put("address", phone);// 来信号码
			else
				values.put("address", Integer.valueOf(phone + i));
			values.put("body", content);// 来信内容
			values.put("date", 20150223);
			values.put("read", 0);
			values.put("type", 1);
			values.put("service_center", "+8613010776500");
			
			context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
		}
	}

	@Override
	public void deleteAllMessage() {
		context.getContentResolver().delete(Uri.parse("content://sms"), null, null);
	}

	@Override
	public List<UIDump> getUIDump(boolean fresh) {
		return position.uidump();
	}

	@Override
	public String getSimOperator() {
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(telephonyManager.getSimState()==TelephonyManager.SIM_STATE_READY)
			return telephonyManager.getSimOperatorName();
		return null;
	}

	@Override
	public String toStep(String step) {
		Intent mIntent = new Intent("com.brocastreceiver.StepFloatingReceiver");
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mIntent.putExtra("step", stepNum+"."+step);
		mIntent.putExtra("result", "");
		mIntent.setAction("stepFloating");
		context.sendBroadcast(mIntent);	
		if(stepNum==tempNum)
			stepTimes = System.currentTimeMillis();
		tempNum++;
		return stepNum+"."+step;
	}

	@Override
	public boolean toResult(boolean result) {
		stepTimes = System.currentTimeMillis() -stepTimes;
		Intent mIntent = new Intent("com.brocastreceiver.StepFloatingReceiver");
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mIntent.putExtra("step", "");
		mIntent.putExtra("result", String.valueOf(result));
		mIntent.setAction("stepFloating");
		context.sendBroadcast(mIntent);	
		stepNum++;
		tempNum = stepNum;
		return result;
	}

	@Override
	public void addtoFile(String file, String step, boolean result) {
		File folder = new File(reportPath);
		if (!folder.exists()) {
			folder.mkdir();
		}
		if (!folder.exists())
			return;
		String fileName = reportPath + "//"+file+".txt";
		System.out.println(fileName);
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f = new File(fileName);
			fw = new FileWriter(f, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String picture = "";
		errorTime="notError";
		if (!result){
			long currentTime = System.currentTimeMillis();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date(currentTime);
			errorTime =formatter.format(date);
			picture = screenShot("errors");	
		}	
		Intent mIntent = new Intent("com.brocastreceiver.LogCatReceiver");
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mIntent.putExtra("errorTime", errorTime);
		mIntent.setAction("endCase");
		context.sendBroadcast(mIntent);	
		
		PrintWriter pw = new PrintWriter(fw);
		pw.println(step + "#" + result + "#" + stepTimes/1000 + "s#" + picture);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startatHome(String appname) {
		sendKeyCode(AndroidKeyCode.HOME);
		for(int i=0;i<10;i++){
			if(waitforText(appname, 0, 0))
				break;
			else{
				if(i<5){
					slideScreenLeft();
				}else {
					slideScreenRight();
				}
			}			
		}
		touch(appname, ElementAttribs.TEXT, 0, false, 0);
	}

	@Override
	public Element searchElementbyId(String id, int index, boolean fresh) {
		ArrayList<Element> ids = new ArrayList<Element>();
		ids = position.findElementsById(id,fresh);
		if(ids.size()<index+1)
			return null;
		return ids.get(index);
	}

	@Override
	public Element searchElementbyText(String text, int index, boolean fresh) {
		ArrayList<Element> texts = new ArrayList<Element>();
		texts = position.findElementsById(text,fresh);
		if(texts.size()<index+1)
			return null;
		return texts.get(index);
	}
	
	@Override
	public String getScreenText(boolean fresh){
		return getUIDump(fresh).toString();
	}

	@Override
	public boolean touch(Element e, long times) {
		if(e==null)
			return false;
		else{
			touch(e.getX(), e.getY(), 0);
			return true;
		}
	}
}
