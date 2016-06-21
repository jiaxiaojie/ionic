/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.cancelAuthorizeAutoRepayment;

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
public class CancelAuthorizeAutoRepaymentReq {
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String platformUserNo; //平台会员编号
	public String requestNo; //请求流水号
	public String orderNo; //标的号
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String toReq(){
		return JaxbMapper.toXml(this);
	}
	public static void main(String[] args) {
		CancelAuthorizeAutoRepaymentReq req = new CancelAuthorizeAutoRepaymentReq();
		req.setPlatformNo("platformNo");
		req.setOrderNo("orderNo");
		req.setPlatformUserNo("platformUserNo");
		req.setRequestNo("requestNo");
		System.out.println(req.toReq());
	}
}
