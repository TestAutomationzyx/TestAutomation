package com.testautomationservice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.CallLog;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.telephony.TelephonyManager;

import com.element.ActivityName;
import com.element.AdbShell;
import com.element.AndroidKeyCode;
import com.element.Position;
import com.element.Position.Element;
import com.floatingreceiver.StepFloatingReceiver;
import com.utils.UIDump;

@SuppressLint("SimpleDateFormat")
public class AutoTool implements ToolApi {
	
	AdbShell adbTool;
	Position position;
	Context context;
	ClipboardManager clipboardManager;
	TelephonyManager telephonyManager;
	WifiManager wifiManager;

	public AutoTool(Context context) {
		this.context = context;
		Intent sIntent = new Intent(context, StepFloatingReceiver.class);
		context.startService(sIntent);
		adbTool = new AdbShell();
		sendKeyCode(AndroidKeyCode.HOME);
		position = new Position();
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}

	public String adbShell(String cmd) {
		return adbTool.adbshell(cmd);
	}

	@Override
	public void sendKeyCode(int keycode) {
		adbTool.sendKeyEvent(keycode);
	}

	@Override
	public String getElementValuebyId(String id, int index, boolean fresh, String item) {
		return null;
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
		Element e1 = position.findElements(type, e,fresh).get(index);
//				position.findElement(type, e,fresh);
		if(e1==null)
			return false;
		else{
			adbTool.touch(e1);
			return true;
		}
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
			if (position.findElementsById(id,true).get(index) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean waitforText(String text, int index, long times) {
		long startTimes = System.currentTimeMillis();
		while ((System.currentTimeMillis() - startTimes) <= times) {
			sleep(500);
			if (position.findElementsByText(text, true).get(index) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isPlaying() {
		return false;
	}

	@Override
	public boolean hasSIMCard() {
		return telephonyManager.getSimState()!=TelephonyManager.SIM_STATE_ABSENT;
	}

	@Override
	public void operateWifi(boolean open) {
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
	public void screenShot(String name) {
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(currentTime);
		adbTool.adbshell("screencap -p /sdcard/TestAutomation/image/" + name
				+ "_" + formatter.format(date) + ".png");
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
		if(telephonyManager.getSimState()==TelephonyManager.SIM_STATE_READY)
			return telephonyManager.getSimOperatorName();
		return null;
	}

	@Override
	public void toStep(String step) {
		Intent mIntent = new Intent("com.floatingreceiver.StepFloatingReceiver");
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mIntent.putExtra("step", step);
		mIntent.putExtra("result", "");
		mIntent.setAction("stepFloating");
		context.sendBroadcast(mIntent);	
	}

	@Override
	public void toResult(boolean result) {
		Intent mIntent = new Intent("com.floatingreceiver.StepFloatingReceiver");
		mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mIntent.putExtra("step", "");
		mIntent.putExtra("result", String.valueOf(result));
		mIntent.setAction("stepFloating");
		context.sendBroadcast(mIntent);		
	}

	@Override
	public void savetoFile(String step, boolean result) {
			String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			String rootPath = path.substring(0, path.lastIndexOf("/WEB-INF"));
			String folderpath=rootPath+"//userOPerationLogs";
			File folder=new File(folderpath);
			if(!folder.exists()){
				folder.mkdir();
			}
			if(!folder.exists()) return;
//			String day=PublicUtil.getYYYYMMDD(new Date());
			String fileName=folderpath+"//file_name.txt";
			System.out.println(fileName);
			FileWriter fw = null;
			try {
				//如果文件存在，则追加内容；如果文件不存在，则创建文件
			    File f=new File(fileName);
			    fw = new FileWriter(f, true);
			   }catch (IOException e) {
				   e.printStackTrace();
			   }
			   PrintWriter pw = new PrintWriter(fw);
			   pw.println(step+"#"+result);
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
		returnHome();
		for(int i=0;i<10;i++){
			if(waitforText(appname, 1, 0))
				break;
			else{
				if(i<5){
					
				}else {
					
				}
			}			
		}
		
	}

	@Override
	public boolean find(String e, int type, int index, boolean fresh, long times) {
//		if(position.findElement(att, str, fresh))
		return false;
	}
}
