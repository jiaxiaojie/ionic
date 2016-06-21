/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.autoTranscation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.yeepay.common.Property;
import com.thinkgem.jeesite.common.yeepay.directTranscation.MoneyDetail;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutoTranscationReq {
	public String requestNo; //请求流水号
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String platformUserNo; //出款人用户编号
	public String userType; //出款人用户类型
	public String bizType; //业务类型
	@XmlElementWrapper(name = "details")
	@XmlElement(name = "detail")
	public List<MoneyDetail> detail; //资金明细记录
	@XmlElementWrapper(name = "extend")
	@XmlElement(name = "property")
	public List<Property> extend ;// 业务扩展属性，根据
	public String notifyUrl; //服务器通知
	
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getPlatformUserNo() {
		return platformUserNo;
	}
	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public List<MoneyDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<MoneyDetail> detail) {
		this.detail = detail;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String toReq(){
		return JaxbMapper.toXml(this);
	}

	public List<Property> getExtend() {
		return extend;
	}
	public void setExtend(List<Property> extend) {
		this.extend = extend;
	}
	
	public static void main(String[] args) {
		AutoTranscationReq autoTranscationReq = new AutoTranscationReq();
		autoTranscationReq.setPlatformNo("platformNo");
		autoTranscationReq.setPlatformUserNo("platformUserNo");
		autoTranscationReq.setUserType("userType");
		autoTranscationReq.setBizType("bizType");
		
		List<MoneyDetail> details = new ArrayList<MoneyDetail>();
		MoneyDetail detail = new MoneyDetail();
		detail.setAmount("amount");
		detail.setBizType("bizType");
		detail.setTargetPlatformUserNo("targetPlatformUserNo");
		detail.setTargetUserType("targetUserType");
		MoneyDetail detail1 = new MoneyDetail();
		detail1.setAmount("amount1");
		detail1.setBizType("bizType1");
		detail1.setTargetPlatformUserNo("targetPlatformUserNo1");
		detail1.setTargetUserType("targetUserType1");
		details.add(detail);
		details.add(detail1);
		autoTranscationReq.setDetail(details);
		
		List<Property> extend = new ArrayList<Property>();
		Property property = new Property();
		property.setName("aaa");
		property.setValue("aaa");
		Property property1 = new Property();
		property1.setName("bbb");
		property1.setValue("bbb");
		extend.add(property);
		extend.add(property1);
		autoTranscationReq.setExtend(extend);
		
		System.out.println(autoTranscationReq.toReq());
	}
}
