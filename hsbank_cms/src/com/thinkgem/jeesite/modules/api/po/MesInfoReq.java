package com.thinkgem.jeesite.modules.api.po;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class MesInfoReq extends DataEntity<ToInvestReq>{
	private static final long serialVersionUID = 1L;
	private String accountName;		// 项目流水号
	private String mobile; //转让项目编号
	private String email;		// 投资金额
	private String message; //抵用券编号列表，逗号分隔
	
	@NotBlank(message="姓名不能为空")
	@Length(min=0, max=20, message="姓名长度必须介于 0 和 20 之间")
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	@NotBlank(message="手机号码不能为空")
	@Length(min=0, max=20, message="手机号码长度必须介于 0 和 20 之间")
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
	@Length(min=0, max=500, message="留言长度必须介于 0 和 500 之间")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

	
}
