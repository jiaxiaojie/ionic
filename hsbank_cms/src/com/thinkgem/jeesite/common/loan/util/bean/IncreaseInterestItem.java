package com.thinkgem.jeesite.common.loan.util.bean;

/**
 * 加息_bean
 * @author lzb
 * 2016-03-31
 */
public class IncreaseInterestItem {
    private String interestId;		//加息券id
	private Double interestRate;	//加息比例	
	private int interestDuration;	//加息期限
	private Double maxAmount;	//加息限额
	
	
	public String getInterestId() {
		return interestId;
	}
	public void setInterestId(String interestId) {
		this.interestId = interestId;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public int getInterestDuration() {
		return interestDuration;
	}
	public void setInterestDuration(int interestDuration) {
		this.interestDuration = interestDuration;
	}
	public Double getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	
	

}
