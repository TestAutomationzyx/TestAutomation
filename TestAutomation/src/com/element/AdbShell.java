package com.element;

import java.util.ArrayList;

import com.element.Position.Element;
import com.utils.ShellUtils;

public class AdbShell {
	
	String TAG = "AdbShell";
	int[] display = new int[2];

	public AdbShell() {

	}

	/**
	 * 获取设备SN号
	 * 
	 * @return 设备SN号
	 */
	public String getDeviceId() {
		return this.getprop("ro.boot.serialno");
	}
	/**
	 * 获取设备IMEI号
	 * @return
	 */
	public String getIMEI(){
		return this.getprop("gsm.sim.imei");
	}

	/**
	 * 获取系统固件版本
	 * @return
	 */
	public String getSystemId(){
		return this.getprop("ro.build.inside.id");
	}
	/**
	 * 获取设备中Android版本号，如 4.4.4
	 * 
	 * @return Android版本号
	 */
	public String getAndroidVersion() {
		return this.getprop("ro.build.version.release");
	}

	/**
	 * 获取设备中SDK的版本号
	 * 
	 * @return SDK版本号
	 */
	public int getSDKVersion() {
		return Integer.parseInt(this.getprop("ro.build.version.sdk"));
	}

	/**
	 * 查看设备电池电量
	 * 
	 * @return 返回电量值
	 */
	public int getBatteryLevel() {
		return Integer
				.parseInt(dumpsys("battery | grep level").split("level")[1]
						.split(": ")[1]);
	}

	/**
	 * 查看设备电池温度
	 * 
	 * @return 返回电池温度
	 */
	public double getBatteryTemp() {
		return Integer.parseInt(dumpsys("battery | grep temperature").split(
				": ")[1]) / 10.0;
	}

	/**
	 * 获取电池充电状态 1 : BATTERY_STATUS_UNKNOWN, 未知状态 2 : BATTERY_STATUS_CHARGING,
	 * 充电状态 3 : BATTERY_STATUS_DISCHARGING, 放电状态 4 :
	 * BATTERY_STATUS_NOT_CHARGING, 未充电 : BATTERY_STATUS_FULL, 充电已满
	 * 
	 * @return
	 */
	public int getBatteryStatus() {
		return Integer
				.parseInt(dumpsys("battery | grep status").split(": ")[1]);
	}

	/**
	 * 获取设备上当前界面的package和activity
	 * 
	 * @return 当前界面package/activity
	 */
	public String getCurrentPackageAndActicity() {
		return dumpsys("window w | grep \\/ | grep name=").split("=")[2].replace(")", "").replace("mSurface", "").trim();
	}

	/**
	 * 获取设备屏幕分辨率
	 * 
	 * @return
	 */
	public int[] getScreenResolution() {
		String s = dumpsys("display | grep PhysicalDisplayInfo").split(",")[0];//.split("x");
		int index = s.indexOf("{");
		String sr[] = s.substring(index+1,s.length()).split("x");
		int[] Sr = { Integer.parseInt(sr[0].trim()), Integer.parseInt(sr[1].trim()) };
		return Sr;
	}

	/**
	 * 获取设备当前界面包名
	 * 
	 * @return package
	 */
	public String getCurrentPackageName() {
		return getCurrentPackageAndActicity().split("/")[0];
	}

	/**
	 * 获取设备当前界面activity
	 * 
	 * @return activity
	 */
	public String getCurrentActivity() {
		return getCurrentPackageAndActicity().split("/")[1];
	}

	/**
	 * 启动指定应用
	 * 
	 * @param component
	 */
	public void startActivity(String component) {
		ShellUtils.execCommand("am start -n " + component, true);
		sleep(1000);
	}

	/**
	 * 使用默认浏览器打开某个网址
	 * 
	 * @param url
	 */
	public void startWebPage(String url) {
		ShellUtils.execCommand("am start -a android.intent.action.VIEW -d "
				+ url, true);
	}

	/**
	 * 使用拨号器拨打号码
	 * 
	 * @param number
	 */
	public void callPhone(int number) {
		ShellUtils.execCommand("am start -a android.intent.action.CALL -d tel:"
				+ number, true);
	}

	/**
	 * 发送按键事件
	 * 
	 * @param keycode
	 */
	public void sendKeyEvent(int keycode) {
		ShellUtils.execCommand("input keyevent " + keycode, true);
		sleep(100);
	}

	/**
	 * 发送点击事件
	 * 
	 * @param x
	 *            坐标
	 * @param y
	 *            坐标
	 */
	public void touch(int x, int y) {
		ShellUtils.execCommand("input tap " + x + " " + y, true);
		sleep(100);
	}

	/**
	 * 发送点击事件
	 * 
	 * @param x
	 * @param y
	 */
	public void touch(double x, double y) {
		double[] coords = ratio(x, y);
		ShellUtils
				.execCommand("input tap " + coords[0] + " " + coords[1], true);
		sleep(100);
	}

	/**
	 * 发送点击事件
	 * 
	 * @param e
	 *            元素对象
	 */
	public void touch(Element e) {
		ShellUtils.execCommand("input tap " + e.getX() + " " + e.getY(), false);
		sleep(100);
	}

	/**
	 * 发送滑动事件
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param times
	 */
	public void swipe(int startX, int startY, int endX, int endY, long times) {
		if (getSDKVersion() < 19) {
			ShellUtils.execCommand("input swipe " + startX + " " + startY + " "
					+ endX + " " + endY, true);
		} else {
			ShellUtils.execCommand("input swipe " + startX + " " + startY + " "
					+ endX + " " + endY + " " + times, true);
		}

		sleep(500);
	}
	/**
	 * 发送点击事件
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param times
	 */
	public void swipe(double startX,double startY,double endX,double endY,long times){
		double[] coords = ratio(startX, startY, endX, endY);
		if (getSDKVersion() < 19) {
			ShellUtils.execCommand("input swipe " + coords[0] + " " + coords[1] + " "
					+ coords[2] + " " + coords[3], true);
		} else {
			ShellUtils.execCommand("input swipe " + coords[0] + " " + coords[1] + " "
					+ coords[2] + " " + coords[3] + " " + times, true);
		}

		sleep(500);
	}
	/**
	 * 发送滑动事件
	 * @param e1 起始元素
	 * @param e2 终点元素
	 * @param times
	 */
	public void swipe(Element e1, Element e2, long times){
		if (getSDKVersion() < 19) {
			ShellUtils.execCommand("input swipe " + e1.getX() + " " + e1.getY() + " "
					+ e2.getX() + " " + e2.getY(), true);
		} else {
			ShellUtils.execCommand("input swipe " + e1.getX() + " " + e1.getY() + " "
					+ e2.getX() + " " + e2.getY() + " " + times, true);
		}

		sleep(500);
	}
	/**
	 * 左滑屏幕
	 */
	public void swipetoLeft(){
		swipe(0.8, 0.5, 0.2, 0.5, 500);
	}
	/**
	 * 右滑屏幕
	 */
	public void swipetoRight(){
		swipe(0.2, 0.5, 0.8, 0.5, 500);
	}
	/**
	 * 上滑屏幕
	 */
	public void swipetoTop(){
		swipe(0.5, 0.7, 0.5, 0.3, 500);
	}
	/**
	 * 下滑屏幕
	 */
	public void swipetoButtom(){
		swipe(0.5, 0.3, 0.5, 0.7, 500);
	}
	/**
	 * 发送长按事件
	 * @param x
	 * @param y
	 * @param times
	 */
	public void longPress(double x, double y, long times){
		swipe(x, y, x, y, times);
	}
	/**
	 * 发送长按事件
	 * @param e 元素对象
	 * @param times
	 */
	public void longPress(Element e,long times){
		swipe(e.getX(), e.getY(), e.getX(), e.getY(), times);
	}
	/**
	 * 发送文本，只支持英文，多个空格视为一个空格
	 * @param text
	 */
	public void inputText(String text){
		String[] str = text.split(" ");
		ArrayList<String> out = new ArrayList<String>();
		for(String string : str){
			if(!string.equals(" "))
				out.add(string);
		}
		int length = out.size();
		for(int i=0; i<length; i++){
			ShellUtils.execCommand("input text "+out.get(i), true);
			sleep(100);
			if(i!=length-1)
				sendKeyEvent(AndroidKeyCode.SPACE);
		}
	}
	/**
	 * 清除文本
	 * @param text 获取到的文本框的text
	 */
	public void clearText(String text){
		int length = text.length();
		for(int i =length; i>0; i--)
			sendKeyEvent(AndroidKeyCode.SPACE);
	}
	/**
	 * 小于1，自动乘以分辨率转换为实际坐标，大于1，当做实际坐标处理
	 * @param x          
	 * @param y       
	 * @return
	 */
	private double[] ratio(double x, double y) {
		if(display[0]==0 && display[1]==0)
			display = getScreenResolution();
		double[] coords = new double[2];

		if (x < 1)
			coords[0] = display[0] * x;
		else
			coords[0] = x;

		if (y < 1)
			coords[1] = display[1] * y;
		else
			coords[1] = y;
		return coords;
	}

	/**
	 * 小于1，自动乘以分辨率转换为实际坐标，大于1，当做实际坐标处理
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return
	 */
	private double[] ratio(double startX,double startY,double endX,double endY){
		if(display[0]==0 && display[1]==0)
			display = getScreenResolution();
		double[] coords = new double[4];
		if(startX<1)
			coords[0] = display[0] * startX;
		else
			coords[0] = startX;
		if(startY<1)
			coords[1] = display[1] * startY;
		else
			coords[1] = startY;
		if(endX<1)
			coords[2] = display[0] * endX;
		else
			coords[2] = endX;
		if(endY<1)
			coords[3] = display[1] * endY;
		else
			coords[3] = endY;
		
		return coords;
	}
	private String getprop(String command) {
		return ShellUtils.execCommand("getprop " + command, false).successMsg;
	}

	private String dumpsys(String command) {
		return ShellUtils.execCommand("dumpsys " + command, true).successMsg;
	}

	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String adbshell(String cmd){
		return ShellUtils.execCommand(cmd, true).successMsg;
	}
}
