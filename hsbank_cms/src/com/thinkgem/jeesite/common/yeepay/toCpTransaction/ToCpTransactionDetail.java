/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.toCpTransaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "detail")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToCpTransactionDetail {
	public String amount ;// 转入金额
	public String targetUserType ;// 用户类型,见【用户类
	public String targetPlatformUserNo ;// 平台用户编号
	public String bizType ;// 资金明细业务类型。
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTargetUserType() {
		return targetUserType;
	}
	public void setTargetUserType(String targetUserType) {
		this.targetUserType = targetUserType;
	}
	public String getTargetPlatformUserNo() {
		return targetPlatformUserNo;
	}
	public void setTargetPlatformUserNo(String targetPlatformUserNo) {
		this.targetPlatformUserNo = targetPlatformUserNo;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
}
