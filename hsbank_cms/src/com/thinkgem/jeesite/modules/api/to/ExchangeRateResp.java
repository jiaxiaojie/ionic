package com.thinkgem.jeesite.modules.api.to;

/**
 * 注册返回
 * @author lizibo
 *
 */
public class ExchangeRateResp {
	private String projectName;		// 项目名称
	private Double annualizedRate;		// 年化利率
	private String interest;		//预期收益
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Double getAnnualizedRate() {
		return annualizedRate;
	}
	public void setAnnualizedRate(Double annualizedRate) {
		this.annualizedRate = annualizedRate;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
    
}
