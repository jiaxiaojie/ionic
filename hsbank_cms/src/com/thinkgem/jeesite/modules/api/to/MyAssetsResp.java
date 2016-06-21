package com.thinkgem.jeesite.modules.api.to;

/**
 * 我的信息
 * @author lizibo
 *
 */
public class MyAssetsResp {
	private Double netAssets;		// 账户净资产
	private Double currentInvestment;	//活期投资总额
	private Double regularInvestment;	//定期投资总额
	private Double congealVal;		// 冻结余额值
	private Double availableBalance;	//可用余额

	public Double getNetAssets() {
		return netAssets;
	}

	public void setNetAssets(Double netAssets) {
		this.netAssets = netAssets;
	}

	public Double getCurrentInvestment() {
		return currentInvestment;
	}

	public void setCurrentInvestment(Double currentInvestment) {
		this.currentInvestment = currentInvestment;
	}

	public Double getRegularInvestment() {
		return regularInvestment;
	}

	public void setRegularInvestment(Double regularInvestment) {
		this.regularInvestment = regularInvestment;
	}

	public Double getCongealVal() {
		return congealVal;
	}

	public void setCongealVal(Double congealVal) {
		this.congealVal = congealVal;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
}
