/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.platformInfo;

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
public class PlatformInfoReq {
	@XmlAttribute(name = "platformNo")
	public String platformNo;// 商户编号
	public String notifyUrl ;// 异步通知地址(用亍接收银行验证卡片结果，即绑卡成功或失败状态，通知参数格式详见 2.4.3绑卡回调通知)
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String toReq(){
		return JaxbMapper.toXml(this);
	}
}
