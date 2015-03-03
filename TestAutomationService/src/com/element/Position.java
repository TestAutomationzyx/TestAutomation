package com.element;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;

import com.adbtool.element.Element;
import com.adbtool.element.UiDumpService;
import com.utils.DumpReader;
import com.utils.ShellUtils;
import com.utils.UIDump;

//元素位置
public class Position {
	//匹配坐标值
	private Pattern pattern = Pattern.compile("([0-9]+)");
	
	//获取系统dumpfile文件
	private File dumpfile;
	private String temp;
	private File uiautomator_jar = new File("/data/local/tmp/UiAutomator.jar");
	private String sdcardpath = Environment.getExternalStorageDirectory().getPath();
	private String window_dump = sdcardpath + "window_dump.xml";
	private String uiautomator_dump = "/data/local/temp/dumpfile.xml";
	
	private Map<Integer, String> attrib = null;
	private ArrayList<HashMap> attribs = null;
	private InputStream xml = null;
	private List<UIDump> dumps = null;
	private boolean isRoot;
	
	public Position(){
		isRoot = isRoot();
		if(isRoot)
			temp = uiautomator_dump;
		else
			temp = window_dump;
		dumpfile = new File(temp);
	}
	
	//获取设备当前界面的控件信息，并解析dumpfile.xml文件
	private void uidump(){
		if (!dumpfile.exists())
			ShellUtils.execCommand("touch " + temp, false);
		if(isRoot){
			if(!uiautomator_jar.exists()){
				ShellUtils.execCommand("cp /sdcard/dump/UiAutomator.jar /data/local/tmp/", false);
				ShellUtils.execCommand("chmod 777 " + temp, true);
			}
			ShellUtils.execCommand("uiautomator runtest UiAutomator.jar -c com.uia.example.my.Test", false);
		}else{
//			ShellUtils.execCommand("uiautomator dump", false);
		}
		try {
			xml = new FileInputStream(dumpfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dumps = new DumpReader().getDumps(xml);
	}
	
	public Element findElementByText(String text){
		return this.findElement(ElementAttribs.TEXT, text);
	}
	
	private Element findElement(int att, String str){
		uidump();
		CharSequence input = getAttrib(att, str).get(ElementAttribs.BOUNDS);
		if (input == null)
			throw new RuntimeException("未在当前界面找到元素(" + str + ")");

		Matcher mat = pattern.matcher(input);
		ArrayList<Integer> coords = new ArrayList<Integer>();
		while (mat.find())
			coords.add(new Integer(mat.group()));
		
		int startX = coords.get(0);
		int startY = coords.get(1);
		int endX = coords.get(2);
		int endY = coords.get(3);

		int centerCoordsX = (endX - startX)/2 +startX;
		int centerCoordsY = (endY - startY)/2 +startY;
		
		Element element = new Element();
		
		element.setX(centerCoordsX);
		element.setY(centerCoordsY);
		
		return element;
	}
	
	private ArrayList<Element> findElements(int att, String str){
		uidump();
		ArrayList<Element> elements = new ArrayList<Element>();
		ArrayList<HashMap> attribs = getAttribs(att, str);
		
		for(HashMap hashMap:attribs){
			Matcher mat = pattern.matcher((String)hashMap.get(ElementAttribs.BOUNDS));
			ArrayList<Integer> coords = new ArrayList<Integer>();
			while(mat.find())
				coords.add(new Integer(mat.group()));
			int startX = coords.get(0);
			int startY = coords.get(1);
			int endX = coords.get(2);
			int endY = coords.get(3);

			int centerCoordsX = (endX - startX)/2 +startX;
			int centerCoordsY = (endY - startY)/2 +startY;
			
			Element element = new Element();
			
			element.setX(centerCoordsX);
			element.setY(centerCoordsY);
			
			elements.add(element);
			
		}
		return elements;
	}
	
	// 获取单个元素属性值集合
	private HashMap<Integer, String> getAttrib(int att, String str) {
		attrib = new HashMap<Integer, String>();
		for(UIDump dump : dumps){
			boolean flag = false;
			switch (att) {
			case 0:
				flag = str.equals(dump.getText());
				break;
			case 1:
				flag = str.equals(dump.getResourceId());
				break;
			case 2:
				flag = str.equals(dump.getClassName());
				break;
			case 3:
				flag = str.equals(dump.getChecked());
				break;
			case 4:
				flag = str.equals(dump.getCheckable());
				break;
			case 5:
				flag = str.equals(dump.getContentDesc());
				break;
			case 6:
				flag = str.equals(dump.getClickable());
				break;
			case 7:
				flag = str.equals(dump.getBounds());
				break;

			default:
				break;
			}
			if(flag){
				attrib.put(ElementAttribs.TEXT, dump.getText());
				attrib.put(ElementAttribs.RESOURCE_ID, dump.getResourceId());
				attrib.put(ElementAttribs.CLASS, dump.getClassName());
				attrib.put(ElementAttribs.CHECKED, dump.getChecked());
				attrib.put(ElementAttribs.CHECKABLE, dump.getCheckable());
				attrib.put(ElementAttribs.CONTENTDESC, dump.getContentDesc());
				attrib.put(ElementAttribs.CLICKABLE, dump.getClickable());
				attrib.put(ElementAttribs.BOUNDS, dump.getBounds());
			}
		}
		return (HashMap<Integer, String>) attrib;
	}

	// 获取多个元素的属性值集合
	private ArrayList<HashMap> getAttribs(int att, String str) {
		HashMap<Integer, String> a = null;
		attribs = new ArrayList<HashMap>();
		for(UIDump dump : dumps){
			boolean flag = false;
			switch (att) {
			case 0:
				flag = str.equals(dump.getText());
				break;
			case 1:
				flag = str.equals(dump.getResourceId());
				break;
			case 2:
				flag = str.equals(dump.getClassName());
				break;
			case 3:
				flag = str.equals(dump.getChecked());
				break;
			case 4:
				flag = str.equals(dump.getCheckable());
				break;
			case 5:
				flag = str.equals(dump.getContentDesc());
				break;
			case 6:
				flag = str.equals(dump.getClickable());
				break;
			case 7:
				flag = str.equals(dump.getBounds());
				break;

			default:
				break;
			}
			if(flag){
				a = new HashMap<Integer, String>();
				a.put(ElementAttribs.TEXT, dump.getText());
				a.put(ElementAttribs.RESOURCE_ID, dump.getResourceId());
				a.put(ElementAttribs.CLASS, dump.getClassName());
				a.put(ElementAttribs.CHECKED, dump.getChecked());
				a.put(ElementAttribs.CHECKABLE, dump.getCheckable());
				a.put(ElementAttribs.CONTENTDESC, dump.getContentDesc());
				a.put(ElementAttribs.CLICKABLE, dump.getClickable());
				a.put(ElementAttribs.BOUNDS, dump.getBounds());
				
				attribs.add(a);
			}
			a = null;
		}
		return attribs;
	}
	
	/**
	 * 判断手机是否ROOT
	 */
	public boolean isRoot() {

		boolean root = false;

		try {
			if ((!new File("/system/bin/su").exists())
					&& (!new File("/system/xbin/su").exists())) {
				root = false;
			} else {
				root = true;
			}

		} catch (Exception e) {
		}

		return root;
	}
	
	//坐标
	public class Element  {
		private int x;
		private int y;
		
		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
	
	public interface ElementAttribs {
		int TEXT = 0;
		int RESOURCE_ID = 1;
		int CLASS = 2;
		int CHECKED = 3;
		int CHECKABLE = 4;
		int CONTENTDESC = 5;
		int CLICKABLE = 6;
		int BOUNDS = 7;
	}

}
