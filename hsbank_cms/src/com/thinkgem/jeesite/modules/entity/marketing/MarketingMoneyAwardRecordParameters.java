package com.thinkgem.jeesite.modules.entity.marketing;

public class MarketingMoneyAwardRecordParameters {
	private Long marketingActivityId;
	private String awardCauseType;
	private Long awardCauseId;
	private String awardReason;
	private String channelId;
	private String behaviorCode;
	public Long getMarketingActivityId() {
		return marketingActivityId;
	}
	public void setMarketingActivityId(Long marketingActivityId) {
		this.marketingActivityId = marketingActivityId;
	}
	public String getAwardCauseType() {
		return awardCauseType;
	}
	public void setAwardCauseType(String awardCauseType) {
		this.awardCauseType = awardCauseType;
	}
	public Long getAwardCauseId() {
		return awardCauseId;
	}
	public void setAwardCauseId(Long awardCauseId) {
		this.awardCauseId = awardCauseId;
	}
	public String getAwardReason() {
		return awardReason;
	}
	public void setAwardReason(String awardReason) {
		this.awardReason = awardReason;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getBehaviorCode() {
		return behaviorCode;
	}
	public void setBehaviorCode(String behaviorCode) {
		this.behaviorCode = behaviorCode;
	}
}