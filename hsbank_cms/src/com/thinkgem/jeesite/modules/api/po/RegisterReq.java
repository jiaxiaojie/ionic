package com.thinkgem.jeesite.modules.api.po;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.thinkgem.jeesite.modules.api.base.RegexConstant;

/**
 * 注册
 * 
 * @author lzb
 * @version 2016-02-03
 */
public class RegisterReq {

	private String mobile;		//手机号
	private String password;	//密码
	private String smsCode;		//短信验证码
	private String inviteCode;	//邀请码(选填)
	private String channel;		//注册渠道(选填)
	private String lotteryToken;	//奖品Token(选填)
	private String subid;		//最终客户在渠道商处的编码， 由渠道商生成，花生金服负责记录(选填)
    private String verifyCode; //图片验证码


	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@NotBlank(message="请输入手机号码")
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@NotBlank(message="请输入验证码")
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getLotteryToken() {
		return lotteryToken;
	}
	public void setLotteryToken(String lotteryToken) {
		this.lotteryToken = lotteryToken;
	}
	public String getSubid() {
		return subid;
	}
	public void setSubid(String subid) {
		this.subid = subid;
	}

}
