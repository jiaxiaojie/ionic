/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay.toRegister;

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
public class ToRegisterReq {
	@XmlAttribute(name = "platformNo")
	public String platformNo; //商户编号
	public String platformUserNo; //商户平台会员标识
	public String requestNo; //请求流水号
	public String nickName; //昵称
	public String realName; //会员真实姓名
	public String idCardType; //【见身份证类型】
	public String idCardNo; //会员身份证号
	public String mobile; //接收短信验证码的手机号
	public String email; //邮箱
	public String callbackUrl; //页面回跳URL
	public String notifyUrl; //服务器通知URL
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
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
	
	public static void main(String[] args){
		ToRegisterReq req=new ToRegisterReq();
		req.setPlatformNo("110"); //商户编号
		req.setPlatformUserNo("YANGTAO"); //商户平台会员标识
		req.setRequestNo("abcdefg1979"); //请求流水号
		req.setNickName("老杨"); //昵称
		req.setRealName("杨涛"); //会员真实姓名
		req.setIdCardType("G2_IDCARD"); //【见身份证类型】
		req.setIdCardNo("610523197922222");
		req.setMobile("13816713626");
		req.setEmail("yangtao22@163.com");
		req.setCallbackUrl("http://www.sohu.com");
		req.setNotifyUrl("ddddd");
		System.out.println(req.toReq());
	}
	
}
