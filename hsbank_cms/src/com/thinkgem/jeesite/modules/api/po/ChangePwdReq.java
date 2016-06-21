package com.thinkgem.jeesite.modules.api.po;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.thinkgem.jeesite.modules.api.base.RegexConstant;

public class ChangePwdReq {

	private String newPassword; 	//新密码
	private String oldPassword;		//旧密码
	
	@NotBlank(message="新密码不能为空")
	@Length(min=0, max=100, message="新密码长度必须介于 0 和 100之间")
	@Pattern(regexp = RegexConstant.PASSWORD_REGEX, message = "请输入6-16位数字或字母组合密码")
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@NotBlank(message="旧密码不能为空")
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
