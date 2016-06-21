package com.thinkgem.jeesite.modules.api.to;


public class ProjectBaseInfoResp {

	private Long projectId;		// 项目流水号
	private Long transferProjectId;		// 转让编号
	private String projectCode;		// 项目编号
	private String recordId;			//投资记录
	private Long projectType;		// 项目类型
	private String projectTypeName;		// 项目类型名称
	private String projectName;		// 项目名称
	private String projectTips;     //项目提示
	private Long repaymentMode;		// 还款方式
	private String repaymentModeName;	//还款方式名称
	private String borrowersUser;		// 融资人
	private Double annualizedRate;		// 年化利率
	private Double annualizedRateNormal;	//正常年化利率
	private Double annualizedRateAdd;	//活动加息年化利率
	private String activityRemark;	//活动说明
	private Long durationUnit;	//项目期限单位(1:按天，2:按月)
	private String durationUnitName;		//项目期限单位名称
	private Long projectDuration;      //项目周期
	private Double planAmount;		// 融资金额
	private Double rate;		//已投百分比
	private Double amount;		// 可投资金额
	private Double assignAmount;		// 债权金额
	private Double receivedProfit; //已收收益
	private Double willProfit; //待收收益
	private Long remainingDays;	//剩余天数
	private String opDt;		//投资日期
	private String lastRepaymentDate;		//到期日期（取的是后台的【项目最后还款日期】）
	private Integer remainingTime;		// 剩余期限（单位：月）
	private Long status;		//状态(3-投标中，4--投标结束，5-还款中，6--还款结束，7-清算结束)
	private String statusName;	//状态名称
	private Long investStatus;		//状态(-1-已撤销，0--正常，1-已转让，2--冻结中，3-还款结束)
	private String investStatusName;	//投资状态名称
	private String publishDt;           //项目发布日期
	private String biddingDeadline;		// 投标截止时间
	private String biddingDeadlineTime; //投标截止时间保留时分秒用于pc端
	private Long startingAmount; //起投金额
	private String projectIntroduce;		// 项目概述
	private String useMethod;		// 贷款用途
	private String transferCode;		// 债权转让限制
	private String transferConstraint;		//转让限制描述
	private String riskInfo; //风控信息
	private String[] aboutFiles = new String[]{}; //相关文件
	private String terminalCodes; //显示终端
	private Long safeguardMode; //保障方式
	private String safeguardModeName; //保障方式名称
	private Integer investmentCount;		//投资人数
	private String investmentPeriod;	//收款区间
	private String remaindAndTotalMonth;	//剩余期数/总期数
	private String isNewUser;		// 是否新手项目
	private String isRecommend;	    //是否重点推荐
	private String isUseTicket;		// 是否可用券
	private String isCanAssign;		// 是否可转让
	private String area;		//项目区域
	private String surplusTime; //剩余时间
	private Integer projectCount; //权债项目数量
	private Integer transferCount;//转让中数量
	private Integer transferDoneCount;//已转让数量
	private String availableBalance;           //可用余额(用户的账户可用余额)

	public Integer getProjectCount() {
		return projectCount;
	}
	public void setProjectCount(Integer projectCount) {
		this.projectCount = projectCount;
	}
	public Integer getTransferCount() {
		return transferCount;
	}
	public void setTransferCount(Integer transferCount) {
		this.transferCount = transferCount;
	}
	public Integer getTransferDoneCount() {
		return transferDoneCount;
	}
	public void setTransferDoneCount(Integer transferDoneCount) {
		this.transferDoneCount = transferDoneCount;
	}

	public String getSurplusTime() {
		return surplusTime;
	}
	public void setSurplusTime(String surplusTime) {
		this.surplusTime = surplusTime;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getIsNewUser() {
		return isNewUser;
	}
	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getPublishDt() {
		return publishDt;
	}
	public void setPublishDt(String publishDt) {
		this.publishDt = publishDt;
	}
	public Long getProjectType() {
		return projectType;
	}
	public void setProjectType(Long projectType) {
		this.projectType = projectType;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	public String getProjectTips() {
		return projectTips;
	}
	public void setProjectTips(String projectTips) {
		this.projectTips = projectTips;
	}
	public Long getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(Long repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getRepaymentModeName() {
		return repaymentModeName;
	}
	public void setRepaymentModeName(String repaymentModeName) {
		this.repaymentModeName = repaymentModeName;
	}
	
	
	public String getBorrowersUser() {
		return borrowersUser;
	}
	public void setBorrowersUser(String borrowersUser) {
		this.borrowersUser = borrowersUser;
	}
	public Double getAnnualizedRate() {
		return annualizedRate;
	}
	public void setAnnualizedRate(Double annualizedRate) {
		this.annualizedRate = annualizedRate;
	}
	public Long getProjectDuration() {
		return projectDuration;
	}
	public void setProjectDuration(Long projectDuration) {
		this.projectDuration = projectDuration;
	}
	
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
	public String getBiddingDeadline() {
		return biddingDeadline;
	}
	public void setBiddingDeadline(String biddingDeadline) {
		this.biddingDeadline = biddingDeadline;
	}
	public Long getStartingAmount() {
		return startingAmount;
	}
	public void setStartingAmount(Long startingAmount) {
		this.startingAmount = startingAmount;
	}
	public String getProjectIntroduce() {
		return projectIntroduce;
	}
	public void setProjectIntroduce(String projectIntroduce) {
		this.projectIntroduce = projectIntroduce;
	}
	
	public String getUseMethod() {
		return useMethod;
	}
	public void setUseMethod(String useMethod) {
		this.useMethod = useMethod;
	}
	public String getTransferCode() {
		return transferCode;
	}
	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
	public String getTransferConstraint() {
		return transferConstraint;
	}
	public void setTransferConstraint(String transferConstraint) {
		this.transferConstraint = transferConstraint;
	}
	public String getRiskInfo() {
		return riskInfo;
	}
	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}

	
	

	public String[] getAboutFiles() {
		return aboutFiles;
	}
	public void setAboutFiles(String[] aboutFiles) {
		this.aboutFiles = aboutFiles;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getInvestmentCount() {
		return investmentCount;
	}
	public void setInvestmentCount(Integer investmentCount) {
		this.investmentCount = investmentCount;
	}
	public String getIsUseTicket() {
		return isUseTicket;
	}
	public void setIsUseTicket(String isUseTicket) {
		this.isUseTicket = isUseTicket;
	}
	public String getIsCanAssign() {
		return isCanAssign;
	}
	public void setIsCanAssign(String isCanAssign) {
		this.isCanAssign = isCanAssign;
	}
	public Double getPlanAmount() {
		return planAmount;
	}
	public void setPlanAmount(Double planAmount) {
		this.planAmount = planAmount;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getSafeguardMode() {
		return safeguardMode;
	}
	public void setSafeguardMode(Long safeguardMode) {
		this.safeguardMode = safeguardMode;
	}
	public String getSafeguardModeName() {
		return safeguardModeName;
	}
	public void setSafeguardModeName(String safeguardModeName) {
		this.safeguardModeName = safeguardModeName;
	}
	public Double getReceivedProfit() {
		return receivedProfit;
	}
	public void setReceivedProfit(Double receivedProfit) {
		this.receivedProfit = receivedProfit;
	}
	public Double getWillProfit() {
		return willProfit;
	}
	public void setWillProfit(Double willProfit) {
		this.willProfit = willProfit;
	}
	public Long getRemainingDays() {
		return remainingDays;
	}
	public void setRemainingDays(Long remainingDays) {
		this.remainingDays = remainingDays;
	}
	public String getInvestmentPeriod() {
		return investmentPeriod;
	}
	public void setInvestmentPeriod(String investmentPeriod) {
		this.investmentPeriod = investmentPeriod;
	}
	public String getRemaindAndTotalMonth() {
		return remaindAndTotalMonth;
	}
	public void setRemaindAndTotalMonth(String remaindAndTotalMonth) {
		this.remaindAndTotalMonth = remaindAndTotalMonth;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public Integer getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(Integer remainingTime) {
		this.remainingTime = remainingTime;
	}
	public Long getTransferProjectId() {
		return transferProjectId;
	}
	public void setTransferProjectId(Long transferProjectId) {
		this.transferProjectId = transferProjectId;
	}
	public Double getAssignAmount() {
		return assignAmount;
	}
	public void setAssignAmount(Double assignAmount) {
		this.assignAmount = assignAmount;
	}
	public String getTerminalCodes() {
		return terminalCodes;
	}
	public void setTerminalCodes(String terminalCodes) {
		this.terminalCodes = terminalCodes;
	}
	
	public Double getAnnualizedRateNormal() {
		return annualizedRateNormal;
	}
	public void setAnnualizedRateNormal(Double annualizedRateNormal) {
		this.annualizedRateNormal = annualizedRateNormal;
	}
	public Double getAnnualizedRateAdd() {
		return annualizedRateAdd;
	}
	public void setAnnualizedRateAdd(Double annualizedRateAdd) {
		this.annualizedRateAdd = annualizedRateAdd;
	}
	public String getActivityRemark() {
		return activityRemark;
	}
	public void setActivityRemark(String activityRemark) {
		this.activityRemark = activityRemark;
	}
	public Long getInvestStatus() {
		return investStatus;
	}
	public void setInvestStatus(Long investStatus) {
		this.investStatus = investStatus;
	}
	public String getInvestStatusName() {
		return investStatusName;
	}
	public void setInvestStatusName(String investStatusName) {
		this.investStatusName = investStatusName;
	}
	public String getOpDt() {
		return opDt;
	}
	public void setOpDt(String opDt) {
		this.opDt = opDt;
	}
	public String getLastRepaymentDate() {
		return lastRepaymentDate;
	}
	public void setLastRepaymentDate(String lastRepaymentDate) {
		this.lastRepaymentDate = lastRepaymentDate;
	}
	public Long getDurationUnit() {
		return durationUnit;
	}
	public void setDurationUnit(Long durationUnit) {
		this.durationUnit = durationUnit;
	}
	public String getDurationUnitName() {
		return durationUnitName;
	}
	public void setDurationUnitName(String durationUnitName) {
		this.durationUnitName = durationUnitName;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}


	public String getBiddingDeadlineTime() {
		return biddingDeadlineTime;
	}

	public void setBiddingDeadlineTime(String biddingDeadlineTime) {
		this.biddingDeadlineTime = biddingDeadlineTime;
	}
}
