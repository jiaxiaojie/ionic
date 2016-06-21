/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.projectQuery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectQueryResp {
	@XmlAttribute(name = "platformNo")
	public String platformNo ;// 商户编号
	public String code ;// 【见返回码】
	public String description ;// 描述信息
	public String targetPlatformUserNo ;// 项目借款方平台用户编号
	public String targetUserType ;// 项目借款方用户类型
	public String transferAmount ;// 项目金额
	public String autoRepayment ;// 是否可以自劢还款
	public String createTime ;// 项目创建时间
	public String notifyUrl ;// 异步通知地址
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTargetPlatformUserNo() {
		return targetPlatformUserNo;
	}
	public void setTargetPlatformUserNo(String targetPlatformUserNo) {
		this.targetPlatformUserNo = targetPlatformUserNo;
	}
	public String getTargetUserType() {
		return targetUserType;
	}
	public void setTargetUserType(String targetUserType) {
		this.targetUserType = targetUserType;
	}
	public String getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	public String getAutoRepayment() {
		return autoRepayment;
	}
	public void setAutoRepayment(String autoRepayment) {
		this.autoRepayment = autoRepayment;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
}
