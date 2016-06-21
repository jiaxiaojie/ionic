/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.directTranscation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class DirectTranscationReq {
	public String requestNo; //请求流水号
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String platformUserNo; //出款人用户编号，目前只支持传入平台商户编号
	public String userType; //出款人用户类型，目前只支持传入
	public String bizType; //目前只支持传入
	@XmlElementWrapper(name = "details")
	@XmlElement(name = "detail")
	public List<MoneyDetail> detail; //资金明细记录
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
	public  List<MoneyDetail> getDetail() {
		return detail;
	}
	public void setDetail( List<MoneyDetail> detail) {
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
	
}
