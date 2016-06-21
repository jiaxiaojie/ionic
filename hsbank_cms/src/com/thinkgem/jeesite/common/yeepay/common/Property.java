package com.thinkgem.jeesite.common.yeepay.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "extend")
@XmlAccessorType(XmlAccessType.FIELD)
public class Property {
	@XmlAttribute(name = "name")
	public String name;
	@XmlAttribute(name = "value")
	public String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
