package com.thinkgem.jeesite.modules.api.po;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.thinkgem.jeesite.modules.api.base.RegexConstant;

/**
 * 重置密码
 * 
 * @author lzb
 * @version 2016-02-03
 */
public class ResetPwdReq {

	private String mobile;		//手机号码
	private String newPassword; 	//新密码
	private String smsCode;			//验证码
	private String verifyCode;       //图片验证码

	
	@NotBlank(message="手机号码不能为空")
	@Length(min=0, max=20, message="手机号码长度必须介于 0 和 20 之间")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@NotBlank(message="密码不能为空")
	@Length(min=0, max=100, message="密码长度必须介于 0 和 100之间")
	@Pattern(regexp = RegexConstant.PASSWORD_REGEX, message = "请输入6-16位数字或字母组合密码")
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@NotBlank(message="请输入验证码")
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
}
