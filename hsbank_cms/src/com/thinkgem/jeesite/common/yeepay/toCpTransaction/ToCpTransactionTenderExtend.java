/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.toCpTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thinkgem.jeesite.common.yeepay.common.Property;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToCpTransactionTenderExtend {
	public String tenderOrderNo;// 项目编号
	public String tenderName;// 项目名称
	public String tenderAmount;// 项目金额
	public String tenderDescription;// 项目描述信息
	public String borrowerPlatformUserNo;// 项目的借款人平台用户编号
	public String tenderSumLimit;//累计投标金额限制，如果此参数不为空，则项目累计已投金额+本次投标金额不能超过此参数

	public String getTenderSumLimit() {
		return tenderSumLimit;
	}

	public void setTenderSumLimit(String tenderSumLimit) {
		this.tenderSumLimit = tenderSumLimit;
	}

	public String getTenderOrderNo() {
		return tenderOrderNo;
	}

	public void setTenderOrderNo(String tenderOrderNo) {
		this.tenderOrderNo = tenderOrderNo;
	}

	public String getTenderName() {
		return tenderName;
	}

	public void setTenderName(String tenderName) {
		this.tenderName = tenderName;
	}

	public String getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(String tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public String getTenderDescription() {
		return tenderDescription;
	}

	public void setTenderDescription(String tenderDescription) {
		this.tenderDescription = tenderDescription;
	}

	public String getBorrowerPlatformUserNo() {
		return borrowerPlatformUserNo;
	}

	public void setBorrowerPlatformUserNo(String borrowerPlatformUserNo) {
		this.borrowerPlatformUserNo = borrowerPlatformUserNo;
	}

	public String toXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<property name=\"tenderOrderNo\" value=\"")
				.append(tenderOrderNo).append("\"/>");
		sb.append("<property name=\"tenderName\" value=\"").append(tenderName)
				.append("\"/>");
		sb.append("<property name=\"tenderAmount\" value=\"")
				.append(tenderAmount).append("\"/>");
		sb.append("<property name=\"tenderDescription\" value=\"")
				.append(tenderDescription).append("\"/>");
		sb.append("<property name=\"borrowerPlatformUserNo\" value=\"")
				.append(borrowerPlatformUserNo).append("\"/>");
		sb.append("<property name=\"tenderSumLimit\" value=\"")
		.append(tenderSumLimit).append("\"/>");
		return sb.toString();
	}
	
	public Map<String,String> toMap(){
		Map<String,String> ret=new HashMap<String,String>();
		ret.put("tenderOrderNo", tenderOrderNo);
		ret.put("tenderName", tenderName);
		ret.put("tenderAmount", tenderAmount);
		ret.put("tenderDescription", tenderDescription);
		ret.put("borrowerPlatformUserNo", borrowerPlatformUserNo);
		ret.put("tenderSumLimit", tenderSumLimit);
		return ret;
	}
	
	public List<Property> toList() {
		List<Property> list = new ArrayList<Property>();
		Property property = new Property();
		property.setName("tenderOrderNo");
		property.setValue(tenderOrderNo);
		list.add(property);

		Property property2 = new Property();
		property2.setName("tenderName");
		property2.setValue(tenderName);
		list.add(property2);

		Property property3 = new Property();
		property3.setName("tenderAmount");
		property3.setValue(tenderAmount);
		list.add(property3);

		Property property4 = new Property();
		property4.setName("tenderDescription");
		property4.setValue(tenderDescription);
		list.add(property4);

		Property property5 = new Property();
		property5.setName("borrowerPlatformUserNo");
		property5.setValue(borrowerPlatformUserNo);
		list.add(property5);

		Property property6 = new Property();
		property6.setName("tenderSumLimit");
		property6.setValue(tenderSumLimit);
		list.add(property6);
		return list;
	}
}
