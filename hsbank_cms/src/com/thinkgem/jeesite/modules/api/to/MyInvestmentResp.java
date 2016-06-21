package com.thinkgem.jeesite.modules.api.to;

import java.util.List;

/**
 * 账号基本信息
 * @author lizibo
 *
 */
public class MyInvestmentResp {
	private Double totalInvestment;		// 投资总额
	private Double receiveMoney;			// 收款总额
	private String tipMsg;				// 收款提示信息
	private List<ProjectBaseInfoResp> projectList;		//项目列表
	public Double getTotalInvestment() {
		return totalInvestment;
	}
	public void setTotalInvestment(Double totalInvestment) {
		this.totalInvestment = totalInvestment;
	}
	public Double getReceiveMoney() {
		return receiveMoney;
	}
	public void setReceiveMoney(Double receiveMoney) {
		this.receiveMoney = receiveMoney;
	}
	public String getTipMsg() {
		return tipMsg;
	}
	public void setTipMsg(String tipMsg) {
		this.tipMsg = tipMsg;
	}
	public List<ProjectBaseInfoResp> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<ProjectBaseInfoResp> projectList) {
		this.projectList = projectList;
	}
	
}
