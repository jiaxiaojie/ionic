/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 活期项目信息Entity
 * @author ydt
 * @version 2015-12-09
 */
public class CurrentProjectInfo extends DataEntity<CurrentProjectInfo> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 项目编号
	private String name;		// 项目名称
	private String tips;        //项目说明
	private Double rate;		// 年化利率
	private Double financeMoney;		// 融资金额
	private Double netWorth;		// 单位净值
	private Double startingAmount;		// 起投金额
	private Double maxAmount;		// 最大投资额度
	private Date publishDt;		// 发布日期
	private Date endInvestmentDt;		// 投标截止时间
	private Long borrowerAccountId;		// 融资人账户编号
	private String riskInfo;		// 风控信息
	private String aboutFiles;		// 相关文件
	private String isAutoRepay;		// 自动还款授权
	private String isAuthorizeShow;		// 自动还款授权是否显示
	private String introduce;		// 项目概述
	private String status;		// 项目状态
	private Date createDt;		// 创建时间
	private Long createUserId;		// 创建人
	private Date reviewDt;		// 审核时间
	private String reviewRemark;		// 审核意见
	private Long reviewUserId;		// 审核人
	private String windingUpStatus;		// 清盘状态
	private Date windingUpApplyDt;		// 清盘申请时间
	private String windingUpApplyReason;		// 清盘申请原因
	private Long windingUpApplyUserId;		// 清盘申请人
	private Date windingUpReviewDt;		// 清盘审核时间
	private String windingUpReviewRemark;		// 清盘审核意见
	private Long windingUpReviewUserId;		// 清盘审核人
	private Date finishDt;		// 结束时间
	
	private String investmentScope;//投标范围
	private String buyRule;//购买规则
	private String redeemRule;	//赎回规则
	private String interestCalculateRule;//收益计算规则
	private String safeDescription;//保障方式
	private String feeDescription;//费用说明
	private String duration;//期限
	
	private String queryParas; //下拉列表框查询选择
	
	private CurrentProjectExecuteSnapshot snapshot;
	
	
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getInvestmentScope() {
		return investmentScope;
	}

	public void setInvestmentScope(String investmentScope) {
		this.investmentScope = investmentScope;
	}

	public String getBuyRule() {
		return buyRule;
	}

	public void setBuyRule(String buyRule) {
		this.buyRule = buyRule;
	}

	public String getRedeemRule() {
		return redeemRule;
	}

	public void setRedeemRule(String redeemRule) {
		this.redeemRule = redeemRule;
	}

	public String getInterestCalculateRule() {
		return interestCalculateRule;
	}

	public void setInterestCalculateRule(String interestCalculateRule) {
		this.interestCalculateRule = interestCalculateRule;
	}

	public String getSafeDescription() {
		return safeDescription;
	}

	public void setSafeDescription(String safeDescription) {
		this.safeDescription = safeDescription;
	}

	public String getFeeDescription() {
		return feeDescription;
	}

	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}

	public CurrentProjectExecuteSnapshot getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(CurrentProjectExecuteSnapshot snapshot) {
		this.snapshot = snapshot;
	}

	public CurrentProjectInfo() {
		super();
	}

	public CurrentProjectInfo(String id){
		super(id);
	}

	@Length(min=0, max=500, message="项目编号长度必须介于 0 和 500 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=200, message="项目名称长度必须介于 0 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	


	public Double getFinanceMoney() {
		return financeMoney;
	}

	public void setFinanceMoney(Double financeMoney) {
		this.financeMoney = financeMoney;
	}

	public Double getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(Double netWorth) {
		this.netWorth = netWorth;
	}

	public Double getStartingAmount() {
		return startingAmount;
	}

	public void setStartingAmount(Double startingAmount) {
		this.startingAmount = startingAmount;
	}
	
	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishDt() {
		return publishDt;
	}

	public void setPublishDt(Date publishDt) {
		this.publishDt = publishDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndInvestmentDt() {
		return endInvestmentDt;
	}

	public void setEndInvestmentDt(Date endInvestmentDt) {
		this.endInvestmentDt = endInvestmentDt;
	}
	
	public Long getBorrowerAccountId() {
		return borrowerAccountId;
	}

	public void setBorrowerAccountId(Long borrowerAccountId) {
		this.borrowerAccountId = borrowerAccountId;
	}
	
	public String getRiskInfo() {
		return riskInfo;
	}

	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}
	
	@Length(min=0, max=200, message="相关文件长度必须介于 0 和 200 之间")
	public String getAboutFiles() {
		return aboutFiles;
	}

	public void setAboutFiles(String aboutFiles) {
		this.aboutFiles = aboutFiles;
	}
	
	@Length(min=0, max=2, message="自动还款授权长度必须介于 0 和 2 之间")
	public String getIsAutoRepay() {
		return isAutoRepay;
	}

	public void setIsAutoRepay(String isAutoRepay) {
		this.isAutoRepay = isAutoRepay;
	}
	
	@Length(min=0, max=2048, message="项目概述长度必须介于 0 和 2048 之间")
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@Length(min=0, max=2, message="项目状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Date reviewDt) {
		this.reviewDt = reviewDt;
	}
	
	@Length(min=0, max=500, message="审核意见长度必须介于 0 和 500 之间")
	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
	
	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	@Length(min=0, max=2, message="清盘状态长度必须介于 0 和 2 之间")
	public String getWindingUpStatus() {
		return windingUpStatus;
	}

	public void setWindingUpStatus(String windingUpStatus) {
		this.windingUpStatus = windingUpStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWindingUpApplyDt() {
		return windingUpApplyDt;
	}

	public void setWindingUpApplyDt(Date windingUpApplyDt) {
		this.windingUpApplyDt = windingUpApplyDt;
	}
	
	@Length(min=0, max=2048, message="清盘申请原因长度必须介于 0 和 2048 之间")
	public String getWindingUpApplyReason() {
		return windingUpApplyReason;
	}

	public void setWindingUpApplyReason(String windingUpApplyReason) {
		this.windingUpApplyReason = windingUpApplyReason;
	}
	
	public Long getWindingUpApplyUserId() {
		return windingUpApplyUserId;
	}

	public void setWindingUpApplyUserId(Long windingUpApplyUserId) {
		this.windingUpApplyUserId = windingUpApplyUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWindingUpReviewDt() {
		return windingUpReviewDt;
	}

	public void setWindingUpReviewDt(Date windingUpReviewDt) {
		this.windingUpReviewDt = windingUpReviewDt;
	}
	
	@Length(min=0, max=2048, message="清盘审核意见长度必须介于 0 和 2048 之间")
	public String getWindingUpReviewRemark() {
		return windingUpReviewRemark;
	}

	public void setWindingUpReviewRemark(String windingUpReviewRemark) {
		this.windingUpReviewRemark = windingUpReviewRemark;
	}
	
	public Long getWindingUpReviewUserId() {
		return windingUpReviewUserId;
	}

	public void setWindingUpReviewUserId(Long windingUpReviewUserId) {
		this.windingUpReviewUserId = windingUpReviewUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFinishDt() {
		return finishDt;
	}

	public void setFinishDt(Date finishDt) {
		this.finishDt = finishDt;
	}

	public String getQueryParas() {
		return queryParas;
	}

	public void setQueryParas(String queryParas) {
		this.queryParas = queryParas;
	}

	public String getIsAuthorizeShow() {
		return isAuthorizeShow;
	}

	public void setIsAuthorizeShow(String isAuthorizeShow) {
		this.isAuthorizeShow = isAuthorizeShow;
	}


	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}