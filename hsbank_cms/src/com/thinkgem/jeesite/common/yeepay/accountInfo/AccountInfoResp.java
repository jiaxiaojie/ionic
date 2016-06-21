/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.accountInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.thinkgem.jeesite.common.yeepay.XStreamHandle;

/**
 * @author yangtao
 *
 */
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountInfoResp {
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String code; //【见返回码】
	public String description; //描述信息
	public String memberType; //【见会员类型】
	public String activeStatus; //【见会员激活状态】
	public String balance; //账户余额
	public String availableAmount; //可用余额
	public String freezeAmount; //冻结金额
	public String cardNo; //绑定的卡号,没有则表示没有绑卡
	public String cardStatus; //【见绑卡状态】
	public String bank; //【见银行代码】
	public String autoTender; //是否已授权自劢投标,true或则 false
	public String paySwift; //表示用户是否已开通快捷支付。表示用户是否已开通快捷支付。 NORMAL 表示未升级，UPGRADE 表示已升级
	public String bindMobileNo;	//绑定手机号
	
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
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}
	public String getFreezeAmount() {
		return freezeAmount;
	}
	public void setFreezeAmount(String freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAutoTender() {
		return autoTender;
	}
	public void setAutoTender(String autoTender) {
		this.autoTender = autoTender;
	}
	public String getPaySwift() {
		return paySwift;
	}
	public void setPaySwift(String paySwift) {
		this.paySwift = paySwift;
	}

	public String getBindMobileNo() {
		return bindMobileNo;
	}
	public void setBindMobileNo(String bindMobileNo) {
		this.bindMobileNo = bindMobileNo;
	}
	public String toStirng() {
		String xml=XStreamHandle.toXml(this);
		xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml;
		return xml;
	}
}
