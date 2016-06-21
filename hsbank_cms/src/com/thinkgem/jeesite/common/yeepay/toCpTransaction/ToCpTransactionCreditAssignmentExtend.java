/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.toCpTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.yeepay.common.Property;

/**
 * @author yangtao
 *
 */
public class ToCpTransactionCreditAssignmentExtend{

	public String tenderOrderNo ;// 项目编号

	public String creditorPlatformUserNo ;// 债权出让人

	public String originalRequestNo ;// 需要转让的投资记录流水号

	public String getTenderOrderNo() {
		return tenderOrderNo;
	}
	public void setTenderOrderNo(String tenderOrderNo) {
		this.tenderOrderNo = tenderOrderNo;
	}
	public String getCreditorPlatformUserNo() {
		return creditorPlatformUserNo;
	}
	public void setCreditorPlatformUserNo(String creditorPlatformUserNo) {
		this.creditorPlatformUserNo = creditorPlatformUserNo;
	}
	public String getOriginalRequestNo() {
		return originalRequestNo;
	}
	public void setOriginalRequestNo(String originalRequestNo) {
		this.originalRequestNo = originalRequestNo;
	}
	public String toXml(){
		StringBuffer sb=new StringBuffer();
		sb.append("<property name=\"tenderOrderNo\" value=\"").append(tenderOrderNo).append("\"/><property name=\"originalRequestNo\" value=\"").append(originalRequestNo).append("\"/><property name=\"creditorPlatformUserNo\" value=\"").append(creditorPlatformUserNo).append("\"/>");
		return sb.toString();
	}
	public Map<String,String> toMap(){
		Map<String,String> ret=new HashMap<String,String>();
		ret.put("tenderOrderNo", tenderOrderNo);
		ret.put("creditorPlatformUserNo", creditorPlatformUserNo);
		ret.put("originalRequestNo", originalRequestNo);
		return ret;
	}

	public List<Property> toList() {
		List<Property> list = new ArrayList<Property>();
		Property property = new Property();
		property.setName("tenderOrderNo");
		property.setValue(tenderOrderNo);
		list.add(property);

		Property property2 = new Property();
		property2.setName("creditorPlatformUserNo");
		property2.setValue(creditorPlatformUserNo);
		list.add(property2);

		Property property3 = new Property();
		property3.setName("originalRequestNo");
		property3.setValue(originalRequestNo);
		list.add(property3);
		return list;
	}
}
