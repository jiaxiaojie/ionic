/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.toBindBankCard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yangtao
 *
 *
 */
@XmlRootElement(name = "notify")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToBindBankCardNotify {
	public String requestNo; //请求流水号
	public String platformNo; //商户编号
	public String bizType; //固定值BIND_BANK_CARD
	public String code; //【见返回码】
	public String message; //描述信息
	public String platformUserNo; //用户编号
	public String cardNo; //绑定的卡号
	public String cardStatus; //【见绑卡状态】
	public String bank; //【见银行代码】

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

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPlatformUserNo() {
		return platformUserNo;
	}

	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
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
	
}
