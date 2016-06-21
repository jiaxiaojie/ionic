package com.thinkgem.jeesite.modules.api.to;

/**
* 我的信息
        * @author hyc
        */
public class CurrentResp {
    private Integer projectId;             //活期项目id
    private String projectName;        //项目名称
    private String projectTips;        //项目提示
    private Integer projectType;          //项目类型
    private String projectTypeName;    //项目类型名称
    private String repaymentMode;      //还款方式
    private Double financeMoney;       //融资金额
    private Double netWorth;           //单位净值
    private Double amount;             //可投金额
    private Double rate;               //已投百分比(%)
    private Integer status;               //状态(3-投标中，4--投标结束，5-还款中，6--还款结束，7-清算结束)
    private String statusName;         //状态名称
    private Double annualizedRate;     //年化利率
    private Double annualizedRateNormal;//正常年化利率
    private Double annualizedRateAdd;   //活动加息年化利率
    private String interestOf10000Yuan; //每万元日收益
    private String activityRemark;      //活动说明（一元起投，灵活存取，按日计息）
    private Double  startingAmount;     //起投金额
    private Double maxAmount;           //最大投资金额
    private String startDate;           //收益起始日
    private String redeemRule;          //提取到账时间
    private String introduce;          //项目简介
    private String investmentScope;    //投资范围
    private String duration;           //期限
    private String buyRule;           //购买规则
    private String interestCalculateRule;        //计息规则
    private String fee;                         //费用
    private Double hasFinancedMoney;           //累计投资总额
    private Integer buyCount;                  //购买次数
    private Double hasRepaidMoney;           //累计已赚收益
    private String terminalCodes;           //适用终端编号，多个用逗号间隔(0：PC，1：Android，2：iOS，3：weixin)
    private Double buyLimit;               //每人购买限额
    private Double extractLimit;          //每日提取限额
    private String publishDt;           //项目发布时间(yyyy-MM-dd HH:mi)
    private String endInvestmentDt;           //投标截止时间(yyyy-MM-dd HH:mi)
    private String buyAmount;           //可购买额度
    private String availableBalance;           //可用余额(用户的账户可用余额)
    private String surplusTime;           //剩余时间

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectTips() {
        return projectTips;
    }

    public void setProjectTips(String projectTips) {
        this.projectTips = projectTips;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getRepaymentMode() {
        return repaymentMode;
    }

    public void setRepaymentMode(String repaymentMode) {
        this.repaymentMode = repaymentMode;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Double getAnnualizedRate() {
        return annualizedRate;
    }

    public void setAnnualizedRate(Double annualizedRate) {
        this.annualizedRate = annualizedRate;
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

    public String getInterestOf10000Yuan() {
        return interestOf10000Yuan;
    }

    public void setInterestOf10000Yuan(String interestOf10000Yuan) {
        this.interestOf10000Yuan = interestOf10000Yuan;
    }

    public String getActivityRemark() {
        return activityRemark;
    }

    public void setActivityRemark(String activityRemark) {
        this.activityRemark = activityRemark;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getRedeemRule() {
        return redeemRule;
    }

    public void setRedeemRule(String redeemRule) {
        this.redeemRule = redeemRule;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getInvestmentScope() {
        return investmentScope;
    }

    public void setInvestmentScope(String investmentScope) {
        this.investmentScope = investmentScope;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBuyRule() {
        return buyRule;
    }

    public void setBuyRule(String buyRule) {
        this.buyRule = buyRule;
    }

    public String getInterestCalculateRule() {
        return interestCalculateRule;
    }

    public void setInterestCalculateRule(String interestCalculateRule) {
        this.interestCalculateRule = interestCalculateRule;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Double getHasFinancedMoney() {
        return hasFinancedMoney;
    }

    public void setHasFinancedMoney(Double hasFinancedMoney) {
        this.hasFinancedMoney = hasFinancedMoney;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Double getHasRepaidMoney() {
        return hasRepaidMoney;
    }

    public void setHasRepaidMoney(Double hasRepaidMoney) {
        this.hasRepaidMoney = hasRepaidMoney;
    }

    public String getTerminalCodes() {
        return terminalCodes;
    }

    public void setTerminalCodes(String terminalCodes) {
        this.terminalCodes = terminalCodes;
    }

    public Double getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Double buyLimit) {
        this.buyLimit = buyLimit;
    }

    public Double getExtractLimit() {
        return extractLimit;
    }

    public void setExtractLimit(Double extractLimit) {
        this.extractLimit = extractLimit;
    }

    public String getPublishDt() {
        return publishDt;
    }

    public void setPublishDt(String publishDt) {
        this.publishDt = publishDt;
    }

    public String getEndInvestmentDt() {
        return endInvestmentDt;
    }

    public void setEndInvestmentDt(String endInvestmentDt) {
        this.endInvestmentDt = endInvestmentDt;
    }

    public String getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(String buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(String surplusTime) {
        this.surplusTime = surplusTime;
    }
}
