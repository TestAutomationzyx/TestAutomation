package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//解析dumpfile.xml文件
public class DumpReader extends DefaultHandler{
	
	private List<UIDump> dumps = null;
	private UIDump dump = null;
	
	public List<UIDump> getDumps(InputStream xml){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DumpReader handler = new DumpReader();
		try {
			parser.parse(xml, handler);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return handler.getDumps();
	}
	
	private List<UIDump> getDumps(){
		return dumps;
	}
	
	@Override
	public void startDocument(){
		dumps = new ArrayList<UIDump>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			org.xml.sax.Attributes attributes) throws SAXException {
		if("node".equals(qName)){
			dump = new UIDump();
			dump.setText(attributes.getValue("text"));
			dump.setResourceId(attributes.getValue("resource-id"));
			dump.setClassName(attributes.getValue("class"));
			dump.setContentDesc(attributes.getValue("content-desc"));
			dump.setCheckable(attributes.getValue("checkable"));
			dump.setChecked(attributes.getValue("checked"));
			dump.setClickable(attributes.getValue("clickable"));
			dump.setBounds(attributes.getValue("bounds"));
			
			dumps.add(dump);
		}
		super.startElement(uri, localName, qName, attributes);
	}
}
