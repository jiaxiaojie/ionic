/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.unfreeze;

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
public class UnfreezeReq {
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String freezeRequestNo; //冻结时的请求流水号
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getFreezeRequestNo() {
		return freezeRequestNo;
	}
	public void setFreezeRequestNo(String freezeRequestNo) {
		this.freezeRequestNo = freezeRequestNo;
	}
	public String toReq(){
		return JaxbMapper.toXml(this);
	}
}
