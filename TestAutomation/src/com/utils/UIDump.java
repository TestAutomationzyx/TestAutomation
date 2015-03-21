package com.utils;

public class UIDump {

	private String text;
	private String resourceId;
	private String className;
	private String contentDesc;
	private String checkable;
	private String checked;
	private String clickable;
	private String bounds;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public String getCheckable() {
		return checkable;
	}

	public void setCheckable(String checkable) {
		this.checkable = checkable;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getClickable() {
		return clickable;
	}

	public void setClickable(String clickable) {
		this.clickable = clickable;
	}

	public String getBounds() {
		return bounds;
	}

	public void setBounds(String bounds) {
		this.bounds = bounds;
	}

	@Override
	public String toString() {
		return "UIDump [text=" + text + ", resourceId=" + resourceId
				+ ", className=" + className + ", contentDesc=" + contentDesc
				+ ", checkable=" + checkable + ", checked=" + checked
				+ ", clickable=" + clickable + ", bounds=" + bounds + "]";
	}

}
