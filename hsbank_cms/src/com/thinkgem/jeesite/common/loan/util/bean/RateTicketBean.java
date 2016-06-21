package com.thinkgem.jeesite.common.loan.util.bean;

/**
 * 加息券bean
 * @author lzb
 *
 */
public class RateTicketBean {
    private String id;
	private String invalidDt;	//失效日期
	private Integer rateDuration;		// 加息期限（天） 
	private String useDescription;		// 使用说明
	private Double rate;		// 加息
	private Double maxAmount;		// 额度上限（元）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInvalidDt() {
		return invalidDt;
	}
	public void setInvalidDt(String invalidDt) {
		this.invalidDt = invalidDt;
	}
	public Integer getRateDuration() {
		return rateDuration;
	}
	public void setRateDuration(Integer rateDuration) {
		this.rateDuration = rateDuration;
	}
	public String getUseDescription() {
		return useDescription;
	}
	public void setUseDescription(String useDescription) {
		this.useDescription = useDescription;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	

}
