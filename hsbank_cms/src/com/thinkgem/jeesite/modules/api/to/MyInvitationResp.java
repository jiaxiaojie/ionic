package com.thinkgem.jeesite.modules.api.to;


public class MyInvitationResp {
	private String name;		// 姓名
	private String account;		// 账号
	private Long status;		// 状态
	private String statusName;		// 状态名称
	private String registerDt; 	//注册时间
	private String registerChannel;	//注册渠道
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getRegisterDt() {
		return registerDt;
	}
	public void setRegisterDt(String registerDt) {
		this.registerDt = registerDt;
	}
	public String getRegisterChannel() {
		return registerChannel;
	}
	public void setRegisterChannel(String registerChannel) {
		this.registerChannel = registerChannel;
	}
	
    
    
}
