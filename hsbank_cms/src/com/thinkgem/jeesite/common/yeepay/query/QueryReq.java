/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.query;

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
public class QueryReq {
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String requestNo; //各个业务的请求流水号
	public String mode; //查询模式，有如下枚丼值：WITHDRAW_RECORD：提现记录RECHARGE_RECORD：充值记录CP_TRANSACTION：转账记录FREEZERE_RECORD：冻结/解冻接口
	
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
	public String toReq(){
		return JaxbMapper.toXml(this);
	}
}
