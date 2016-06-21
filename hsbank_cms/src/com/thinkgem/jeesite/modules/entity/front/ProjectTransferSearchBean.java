package com.thinkgem.jeesite.modules.entity.front;

import java.util.Arrays;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;

public class ProjectTransferSearchBean {
	String[] remainAmounts = {"0", "1", "2", "3", "4"};
	String[] rates = {"0", "1", "2", "3", "4", "5", "6", "7"};
	String[] remainDurations = {"0", "1", "2", "3"};
	String[] repaymentModes = {"0", "1", "2", "3"};
	
	private String remainAmount;			//可投金额
	private Integer minAmount;				//范围最小值
	private Integer maxAmount;				//范围最大值
	private String rate;					//年化利率
	private String remainDuration;			//剩余期限
	private String repaymentMode;			//还款方式
	private Page<ProjectTransferInfo> page;
	
	public String getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(String remainAmount) {
		this.remainAmount = remainAmount;
	}
	public Integer getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(Integer minAmount) {
		this.minAmount = minAmount;
	}
	public Integer getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount = maxAmount;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRemainDuration() {
		return remainDuration;
	}
	public void setRemainDuration(String remainDuration) {
		this.remainDuration = remainDuration;
	}
	public String getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}

	public Page<ProjectTransferInfo> getPage() {
		return page;
	}
	public void setPage(Page<ProjectTransferInfo> page) {
		this.page = page;
	}
	
	/**
	 * 若属性为空或不合法，则设置为默认值"0"
	 */
	public void setDefaultSearch() {
		if((StringUtils.isBlank(remainAmount) || !Arrays.asList(remainAmounts).contains(remainAmount)) && (minAmount == null && maxAmount == null)) {
			remainAmount = "0";
		}
		if(StringUtils.isBlank(rate) || !Arrays.asList(rates).contains(rate)) {
			rate = "0";
		}
		if(StringUtils.isBlank(remainDuration) || !Arrays.asList(remainDurations).contains(remainDuration)) {
			remainDuration = "0";
		}
		if(StringUtils.isBlank(repaymentMode) || !Arrays.asList(repaymentModes).contains(repaymentMode)) {
			repaymentMode = "0";
		}
	}
}
