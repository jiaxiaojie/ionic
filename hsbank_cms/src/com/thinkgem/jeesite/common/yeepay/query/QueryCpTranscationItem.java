/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryCpTranscationItem {
	public String requestNo; //流水号
	public String bizType; //业务类型
	public String amount; //转账总金额
	public String status; //订单状态：PREAUTH
	public String subStatus; //处理状态:
	public String sourceUserType;
	public String sourceUserNo;
	public String createdTime;
	public String completedTime;
	
	public String getSourceUserType() {
		return sourceUserType;
	}
	public void setSourceUserType(String sourceUserType) {
		this.sourceUserType = sourceUserType;
	}
	public String getSourceUserNo() {
		return sourceUserNo;
	}
	public void setSourceUserNo(String sourceUserNo) {
		this.sourceUserNo = sourceUserNo;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubStatus() {
		return subStatus;
	}
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

}
