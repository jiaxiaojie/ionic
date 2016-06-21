package com.thinkgem.jeesite.modules.api.to;

/**
 * 我的信息
 * @author lizibo
 *
 */
public class MyProfitResp {
	private Double profitTotal;		// 账户收益(用户总的收益)
	private Double currentProfit;	//活期收益(用户活期投资历史总收益)
	private Double regularProfit;	//定期收益(用户定期投资的已回款的累计收益)
	private Double willProfit;		// 待收收益(用户定期投资的未回款的累计收益)
	private Double activityReward;	//活动奖励(用户参与各种活动，任务所获得的奖励)

	public Double getProfitTotal() {
		return profitTotal;
	}

	public void setProfitTotal(Double profitTotal) {
		this.profitTotal = profitTotal;
	}

	public Double getCurrentProfit() {
		return currentProfit;
	}

	public void setCurrentProfit(Double currentProfit) {
		this.currentProfit = currentProfit;
	}

	public Double getRegularProfit() {
		return regularProfit;
	}

	public void setRegularProfit(Double regularProfit) {
		this.regularProfit = regularProfit;
	}

	public Double getWillProfit() {
		return willProfit;
	}

	public void setWillProfit(Double willProfit) {
		this.willProfit = willProfit;
	}

	public Double getActivityReward() {
		return activityReward;
	}

	public void setActivityReward(Double activityReward) {
		this.activityReward = activityReward;
	}
}
