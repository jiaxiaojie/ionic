/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.freeze;

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
public class FreezeReq {
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String platformUserNo; //平台会员编号
	public String requestNo; //请求流水号
	public String amount; //冻结金额
	public String expired; //到期自动解冻
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
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String toReq(){
		return JaxbMapper.toXml(this);
	}
}
