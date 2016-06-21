/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.completeTranscation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompleteTranscationReq {
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String requestNo; //请求流水号
//	public String platformUserNo;//用户编号
//	public String getPlatformUserNo() {
//		return platformUserNo;
//	}
//	public void setPlatformUserNo(String platformUserNo) {
//		this.platformUserNo = platformUserNo;
//	}
	public String mode; //CONFIRM
	public String notifyUrl; //服务器通知
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
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
