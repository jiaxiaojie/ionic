package com.thinkgem.jeesite.modules.api.to;

/**
 * 我的活期汇总信息
 * @author lizibo
 *
 */
public class MyCurStatisticsResp {
	private Double currentPrincipal;		// 持有本金总额
	private Double totalGetInterest;	//累计收益
	private Double yesterdayProfit;	//昨日收益

	public Double getCurrentPrincipal() {
		return currentPrincipal;
	}

	public void setCurrentPrincipal(Double currentPrincipal) {
		this.currentPrincipal = currentPrincipal;
	}

	public Double getTotalGetInterest() {
		return totalGetInterest;
	}

	public void setTotalGetInterest(Double totalGetInterest) {
		this.totalGetInterest = totalGetInterest;
	}

	public Double getYesterdayProfit() {
		return yesterdayProfit;
	}

	public void setYesterdayProfit(Double yesterdayProfit) {
		this.yesterdayProfit = yesterdayProfit;
	}
}
