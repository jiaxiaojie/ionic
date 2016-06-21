/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.toCpTransaction;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.yeepay.common.Property;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToCpTransactionReq {
	public String requestNo ;// 请求流水号
	@XmlAttribute(name = "platformNo")
	public String platformNo ;// 商户编号
	public String platformUserNo ;// 出款人平台用户编号
	public String userType ;// 出款人用户类型，目前只支持传入 MEMBER
	public String bizType ;//根据业务的丌同，需
	public String expired ;// 超过此时间即丌允许
	@XmlElementWrapper(name = "details")
	@XmlElement(name = "detail")
	public List<ToCpTransactionDetail> detail ;// 资金明细记录
	@XmlElementWrapper(name = "extend")
	@XmlElement(name = "property")
	public List<Property> extend ;// 业务扩展属性，根据
	public String notifyUrl ;// 服务器通知 URL
	public String callbackUrl ;// 页面回跳 URL 
	
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
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public List<ToCpTransactionDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<ToCpTransactionDetail> detail) {
		this.detail = detail;
	}
	public List<Property> getExtend() {
		return extend;
	}
	public void setExtend(List<Property> extend) {
		this.extend = extend;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String toReq(){
		return JaxbMapper.toXml(this);
	}
}
