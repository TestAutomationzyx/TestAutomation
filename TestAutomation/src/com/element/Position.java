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
import android.annotation.SuppressLint;
import android.util.Log;
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
	private String uiautomator_dump = "/data/local/tmp/dumpfile.xml";
	
	private Map<Integer, String> attrib = null;
	private ArrayList<HashMap<Integer, String>> attribs = null;
	private InputStream xml = null;
	private List<UIDump> dumps = null;
	
	public Position(){
		temp = uiautomator_dump;
		dumpfile = new File(temp);
		if (!dumpfile.exists())
			ShellUtils.execCommand(new String[]{"touch " + temp,"chmod 777 "+temp}, true);
		if(!uiautomator_jar.exists())
			ShellUtils.execCommand(new String[]{"cp /sdcard/TestAutomation/dump/UiAutomator.jar /data/local/tmp/"}, true);	
	}
	
	//获取设备当前界面的控件信息，并解析dumpfile.xml文件
	public List<UIDump> uidump(){	
			ShellUtils.execCommand("uiautomator runtest UiAutomator.jar -c com.uia.example.my.Test", true);
		try {
			xml = new FileInputStream(dumpfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dumps = new DumpReader().getDumps(xml);
		return dumps;
	}
	
	/**
	 * 返回text定位单个元素
	 * @param text
	 * @return 返回元素位置坐标
	 */
	public Element findElementByText(String text,boolean fresh){
		return this.findElement(ElementAttribs.TEXT, text,fresh);
	}
	/**
	 * 通过text定位多个同属性的相同元素
	 * @param text 返回元素位置坐标集合
	 * @return
	 */
	public ArrayList<Element> findElementsByText(String text,boolean fresh){
		return this.findElements(ElementAttribs.TEXT, text,fresh);
	}
	
	/**
	 * 返回resource-id定位单个元素
	 * @param resourceId
	 * @return 返回元素位置坐标
	 */
	public Element findElementById(String resourceId,boolean fresh){
		return this.findElement(ElementAttribs.RESOURCE_ID, resourceId,fresh);
	}
	
	/**
	 * 通过resource-id定位多个同属性的相同元素
	 * @param resourceId 返回元素位置坐标集合
	 * @return
	 */
	public ArrayList<Element> findElementsById(String resourceId,boolean fresh){
		return this.findElements(ElementAttribs.RESOURCE_ID, resourceId,fresh);
	}
	/**
	 * 返回className定位单个元素
	 * @param className
	 * @return 返回元素位置坐标
	 */
	public Element findElementByClass(String className,boolean fresh){
		return this.findElement(ElementAttribs.CLASS, className,fresh);
	}
	/**
	 * 通过className定位多个同属性的相同元素
	 * @param className 返回元素位置坐标集合
	 * @return
	 */
	public ArrayList<Element> findElementsByClass(String className,boolean fresh){
		return this.findElements(ElementAttribs.CLASS, className,fresh);
	}
	/**
	 * 返回checked定位单个元素
	 * @param checked
	 * @return 返回元素位置坐标
	 */
	public Element findElementByChecked(String checked,boolean fresh){
		return this.findElement(ElementAttribs.CHECKED, checked,fresh);
	}
	/**
	 * 通过checked定位多个同属性的相同元素
	 * @param checked 返回元素位置坐标集合
	 * @return
	 */
	public ArrayList<Element> findElementsByChecked(String checked,boolean fresh){
		return this.findElements(ElementAttribs.CHECKED, checked,fresh);
	}
	/**
	 * 返回checkable定位单个元素
	 * @param checkable
	 * @return 返回元素位置坐标
	 */
	public Element findElementByCheckable(String checkable,boolean fresh){
		return this.findElement(ElementAttribs.CHECKABLE, checkable,fresh);
	}
	/**
	 * 通过checkable定位多个同属性的相同元素
	 * @param checkable 返回元素位置坐标集合
	 * @return
	 */
	public ArrayList<Element> findElementsByCheckable(String checkable,boolean fresh){
		return this.findElements(ElementAttribs.CHECKABLE, checkable,fresh);
	}
	/**
	 * 返回contentdesc定位单个元素
	 * @param contentdesc
	 * @return 返回元素位置坐标
	 */
	public Element findElementByContentdesc(String contentdesc,boolean fresh){
		return this.findElement(ElementAttribs.CONTENTDESC, contentdesc,fresh);
	}
	/**
	 * 通过contentdesc定位多个同属性的相同元素
	 * @param contentdesc 返回元素位置坐标集合
	 * @return
	 */
	public ArrayList<Element> findElementsByContentdesc(String contentdesc,boolean fresh){
		return this.findElements(ElementAttribs.CONTENTDESC, contentdesc,fresh);
	}
	/**
	 * 返回clickable定位单个元素
	 * @param clickable
	 * @return 返回元素位置坐标
	 */
	public Element findElementByClickable(String clickable,boolean fresh){
		return this.findElement(ElementAttribs.CHECKED, clickable,fresh);
	}
	/**
	 * 通过clickable定位多个同属性的相同元素
	 * @param clickable 返回元素位置坐标集合
	 * @return
	 */
	public ArrayList<Element> findElementsByClickable(String clickable,boolean fresh){
		return this.findElements(ElementAttribs.CHECKED, clickable,fresh);
	}
	public Element findElement(int att, String str,boolean fresh){
		if(fresh)
			uidump();
		CharSequence input = getAttrib(att, str).get(ElementAttribs.BOUNDS);
		if (input == null)
			return null;//throw new RuntimeException("未在当前界面找到元素(" + str + ")");
		Matcher mat = pattern.matcher(input);
		ArrayList<Integer> coords = new ArrayList<Integer>();
		while (mat.find())
			coords.add(Integer.valueOf(mat.group()));
		
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
	
	public ArrayList<Element> findElements(int att, String str,boolean fresh){
		if(fresh)
			uidump();
		ArrayList<Element> elements = new ArrayList<Element>();
		ArrayList<HashMap<Integer, String>> attribs = getAttribs(att, str);
		
		for(HashMap<Integer, String> hashMap:attribs){
			Matcher mat = pattern.matcher((String)hashMap.get(ElementAttribs.BOUNDS));
			ArrayList<Integer> coords = new ArrayList<Integer>();
			while(mat.find())
				coords.add(Integer.parseInt(mat.group()));
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
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, String> getAttrib(int att, String str) {
		attrib = new HashMap<Integer, String>();
		for(UIDump dump : dumps){
			Log.e("------->", dump.toString());
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
	@SuppressLint("UseSparseArrays")
	private ArrayList<HashMap<Integer, String>> getAttribs(int att, String str) {
		HashMap<Integer, String> a = null;
		attribs = new ArrayList<HashMap<Integer, String>>();
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
