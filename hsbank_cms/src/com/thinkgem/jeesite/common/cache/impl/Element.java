package com.thinkgem.jeesite.common.cache.impl;
/**
 * 
 * @author 万端瑞
 *
 */
public class Element {
	private Object objectKey;
	private Object objectValue;
	public Object getObjectKey() {
		return objectKey;
	}
	public void setObjectKey(Object objectKey) {
		this.objectKey = objectKey;
	}
	public Object getObjectValue() {
		return objectValue;
	}
	public void setObjectValue(Object objectValue) {
		this.objectValue = objectValue;
	}
	public Element(Object objectKey, Object objectValue) {
		super();
		this.objectKey = objectKey;
		this.objectValue = objectValue;
	}
	
	
}
