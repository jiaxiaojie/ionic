/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 借贷合同管理Entity
 * @author yangtao
 * @version 2015-06-24
 */
public class ProjectBaseInfo extends DataEntity<ProjectBaseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目流水号
	private String projectCode;		// 项目编号
	private String isNewUser;		// 是否新手项目
	private String isRecommend;	    //是否重点推荐
	private String canUseRateTicket;	//能否使用加息券
	private Long projectTypeCode;		// 项目类型
	private String projectName;		// 项目名称
	private String projectTips;		//项目提示
	private String projectIntroduce;		// 项目概述
	private String repaymentMode;		// 还款方式
	private Long borrowersUser;		// 融资人
	private Long agentUser;		// 融资代理人
	private Area area;		// 项目区域位置
	private String riskInfo; //风控信息
	private Double annualizedRate;		// 年化利率
	private String isIncreaseInterest;	//是否加息
	private Double increaseInterestRate;	//加息比例	
	private String durationType;	//项目期限类型
	private Long projectDuration;      //项目周期
	private Double serviceCharge; //服务费
	private String serviceChargeTypeCode;	//服务费收取方式
	private Date lastRepaymentDate;//最后还款日期
	private String useMethod;		// 贷款用途
	private Double financeMoney;		// 本期融资金额
	private Double repaymentMoney; //还款总额
	private Double creditExtensionMoney;		// 本次授信金额
	private Date plannedRepaymentDate;		// 计划还款日期
	private Date actualRepaymentDate;		// 实际还款日期
	private Date biddingDeadline;		// 投标截止时间
	private Long transferCode;		// 债权转让限制
	private Date createDt;		// 项目创建日期
	private Long createUserId;		// 项目创建人
	private String reviewRemark; //审核意见
	private Date reviewDt;		// 项目审核日期
	private Long reviewUserId;		// 项目审核人
	private Date publishDt;		// 项目发布日期
	private Date closeDt;		// 项目关闭日期
	private Date   beginDt;             //用于查询的开始时间
	private Date   endDt;               //用于查询的结束时间
	private Long closeUserId;		// 项目关闭确认人
	private String projectStatus;		// 项目状态
	private String isDel;		// 是否删除
	private Date beginPlannedRepaymentDate;		// 开始 计划还款日期
	private Date endPlannedRepaymentDate;		// 结束 计划还款日期
	private CustomerBase bUser; //融资人
	private CustomerBase aUser; //代理人
	private User cUser; //创建人
	private User clUser; //清算人
	private Long startingAmount; //起投金额
	private Long maxAmount;//最大投资金额
	private Long safeguardMode; //保障方式
	private Double monthRepayMoney; //月还本息
	private String aboutFiles; //相关文件
	private Double earlyRepaymentRate; //提前还款费率
	private ProjectFactorCarFlow pfcf;//车辆周转贷要素
	private String borrowersUserId;//查询 条件使用 借款人
	private String borrowersUserName;//查询 条件使用 借款人
	private String customerAccountId;//查询条件关联会员账号条件
	private String transferName ;//查询提交转让人
	private ProjectExecuteSnapshot pes;//执行快照
	private ProjectInvestmentRecord pir;//投资金额
	private ProjectTransferInfo pti;//转让信息
	private Long applySrcId; //个人信用贷申请号
	private String isAutoRepay; //是否自动还款授权
	private String autoRepayCode; //自动授权编号
	private String isLoan;	//是否放款（0：否；1：是）
	private Date loanDt;	//放款时间
	private List<String> showTermList;		//终端列表
	
	private Integer sortInList;	//在列表中的排序
	private Integer sortInIndex;	//在首页中的排序
	

	
	
	private Date beginPublishDt;		// 开始项目发布日期
	private Date endPublishDt;		// 结尾项目发布日期
	
	private Long creditId;//债权id
	private String creditName;		// 债权名称
	
	
	
	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	public Date getBeginPublishDt() {
		return beginPublishDt;
	}

	public void setBeginPublishDt(Date beginPublishDt) {
		this.beginPublishDt = beginPublishDt;
	}

	public Date getEndPublishDt() {
		return endPublishDt;
	}

	public void setEndPublishDt(Date endPublishDt) {
		this.endPublishDt = endPublishDt;
	}

	public String getIsAutoRepay() {
		return isAutoRepay;
	}

	public void setIsAutoRepay(String isAutoRepay) {
		this.isAutoRepay = isAutoRepay;
	}

	public String getAutoRepayCode() {
		return autoRepayCode;
	}

	public void setAutoRepayCode(String autoRepayCode) {
		this.autoRepayCode = autoRepayCode;
	}
	public Long getApplySrcId() {
		return applySrcId;
	}

	public void setApplySrcId(Long applySrcId) {
		this.applySrcId = applySrcId;
	}
	private String backPath; //返回路径
	
	public String getBackPath() {
		return backPath;
	}

	public void setBackPath(String backPath) {
		this.backPath = backPath;
	}

	public ProjectBaseInfo() {
		super();
	}

	public ProjectBaseInfo(String id){
		super(id);
	}

	@Length(min=1, max=11, message="项目流水号长度必须介于 1 和 11 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=50, message="项目编号长度必须介于 0 和 50 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=0, max=11, message="是否新手项目长度必须介于 0 和 11 之间")
	public String getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}
	
	public Long getProjectTypeCode() {
		return projectTypeCode;
	}

	public void setProjectTypeCode(Long projectTypeCode) {
		this.projectTypeCode = projectTypeCode;
	}
	
	@Length(min=0, max=200, message="项目名称长度必须介于 0 和 200 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=2048, message="项目概述长度必须介于 0 和 2048 之间")
	public String getProjectIntroduce() {
		return projectIntroduce;
	}

	public void setProjectIntroduce(String projectIntroduce) {
		this.projectIntroduce = projectIntroduce;
	}
	
	@Length(min=0, max=2, message="还款方式长度必须介于 0 和 2 之间")
	public String getRepaymentMode() {
		return repaymentMode;
	}

	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}
	
	public Long getBorrowersUser() {
		return borrowersUser;
	}

	public void setBorrowersUser(Long borrowersUser) {
		this.borrowersUser = borrowersUser;
	}
	
	public Long getAgentUser() {
		return agentUser;
	}

	public void setAgentUser(Long agentUser) {
		this.agentUser = agentUser;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public Double getAnnualizedRate() {
		return annualizedRate;
	}

	public void setAnnualizedRate(Double annualizedRate) {
		this.annualizedRate = annualizedRate;
	}
	
	@Length(min=0, max=100, message="贷款用途长度必须介于 0 和 100 之间")
	public String getUseMethod() {
		return useMethod;
	}

	public void setUseMethod(String useMethod) {
		this.useMethod = useMethod;
	}
	
	public Double getFinanceMoney() {
		return financeMoney;
	}

	public void setFinanceMoney(Double financeMoney) {
		this.financeMoney = financeMoney;
	}
	
	public Double getCreditExtensionMoney() {
		return creditExtensionMoney;
	}

	public void setCreditExtensionMoney(Double creditExtensionMoney) {
		this.creditExtensionMoney = creditExtensionMoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlannedRepaymentDate() {
		return plannedRepaymentDate;
	}
	public Long getProjectDuration() {
		return projectDuration;
	}

	public void setProjectDuration(Long projectDuration) {
		this.projectDuration = projectDuration;
	}

	public void setPlannedRepaymentDate(Date plannedRepaymentDate) {
		this.plannedRepaymentDate = plannedRepaymentDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getActualRepaymentDate() {
		return actualRepaymentDate;
	}

	public void setActualRepaymentDate(Date actualRepaymentDate) {
		this.actualRepaymentDate = actualRepaymentDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBiddingDeadline() {
		return biddingDeadline;
	}

	public void setBiddingDeadline(Date biddingDeadline) {
		this.biddingDeadline = biddingDeadline;
	}
	
	public Long getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(Long transferCode) {
		this.transferCode = transferCode;
	}
	
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
	
	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPublishDt() {
		return publishDt;
	}

	public void setPublishDt(Date publishDt) {
		this.publishDt = publishDt;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCloseDt() {
		return closeDt;
	}

	public void setCloseDt(Date closeDt) {
		this.closeDt = closeDt;
	}
	public Date getBeginDt() {
		return beginDt;
	}
	public void setBeginDt(Date beginDt) {
		this.beginDt = beginDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	
	public Long getCloseUserId() {
		return closeUserId;
	}

	public void setCloseUserId(Long closeUserId) {
		this.closeUserId = closeUserId;
	}
	@Length(min=0, max=2, message="项目状态长度必须介于 0 和 2 之间")
	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	@Length(min=0, max=1, message="是否删除长度必须介于 0 和 1 之间")
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	public Date getBeginPlannedRepaymentDate() {
		return beginPlannedRepaymentDate;
	}

	public void setBeginPlannedRepaymentDate(Date beginPlannedRepaymentDate) {
		this.beginPlannedRepaymentDate = beginPlannedRepaymentDate;
	}
	
	public Date getEndPlannedRepaymentDate() {
		return endPlannedRepaymentDate;
	}

	public void setEndPlannedRepaymentDate(Date endPlannedRepaymentDate) {
		this.endPlannedRepaymentDate = endPlannedRepaymentDate;
	}
	public CustomerBase getbUser() {
		return bUser;
	}

	public void setbUser(CustomerBase bUser) {
		this.bUser = bUser;
	}

	public CustomerBase getaUser() {
		return aUser;
	}

	public void setaUser(CustomerBase aUser) {
		this.aUser = aUser;
	}

	public User getcUser() {
		return cUser;
	}

	public void setcUser(User cUser) {
		this.cUser = cUser;
	}	
	public User getClUser() {
		return clUser;
	}

	public void setClUser(User clUser) {
		this.clUser = clUser;
	}
	
	public String getBorrowersUserId() {
		return borrowersUserId;
	}

	public void setBorrowersUserId(String borrowersUserId) {
		this.borrowersUserId = borrowersUserId;
	}
	
	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}
	public String getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(String customerAccountId) {
		this.customerAccountId = customerAccountId;
	}
	
	public ProjectExecuteSnapshot getPes() {
		return pes;
	}

	public void setPes(ProjectExecuteSnapshot pes) {
		this.pes = pes;
	}

	public ProjectInvestmentRecord getPir() {
		return pir;
	}

	public void setPir(ProjectInvestmentRecord pir) {
		this.pir = pir;
	}
	public ProjectTransferInfo getPti() {
		return pti;
	}

	public void setPti(ProjectTransferInfo pti) {
		this.pti = pti;
	}
	
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getBorrowersUserName() {
		return borrowersUserName;
	}

	public void setBorrowersUserName(String borrowersUserName) {
		this.borrowersUserName = borrowersUserName;
	}
	public String getTransferName() {
		return transferName;
	}

	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}

	public Long getStartingAmount() {
		return startingAmount;
	}

	public void setStartingAmount(Long startingAmount) {
		this.startingAmount = startingAmount;
	}

	public Long getSafeguardMode() {
		return safeguardMode;
	}

	public void setSafeguardMode(Long safeguardMode) {
		this.safeguardMode = safeguardMode;
	}

	public Double getMonthRepayMoney() {
		return monthRepayMoney;
	}

	public void setMonthRepayMoney(Double monthRepayMoney) {
		this.monthRepayMoney = monthRepayMoney;
	}

	public String getAboutFiles() {
		return aboutFiles;
	}

	public void setAboutFiles(String aboutFiles) {
		this.aboutFiles = aboutFiles;
	}

	public Double getEarlyRepaymentRate() {
		return earlyRepaymentRate;
	}

	public void setEarlyRepaymentRate(Double earlyRepaymentRate) {
		this.earlyRepaymentRate = earlyRepaymentRate;
	}

	public ProjectFactorCarFlow getPfcf() {
		return pfcf;
	}

	public void setPfcf(ProjectFactorCarFlow pfcf) {
		this.pfcf = pfcf;
	}
	
	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
	public String getRiskInfo() {
		return riskInfo;
	}

	public void setRiskInfo(String riskInfo) {
		this.riskInfo = riskInfo;
	}
	public Date getLastRepaymentDate() {
		return lastRepaymentDate;
	}

	public void setLastRepaymentDate(Date lastRepaymentDate) {
		this.lastRepaymentDate = lastRepaymentDate;
	}

	public Double getRepaymentMoney() {
		return repaymentMoney;
	}

	public void setRepaymentMoney(Double repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}

	public String getServiceChargeTypeCode() {
		return serviceChargeTypeCode;
	}

	public void setServiceChargeTypeCode(String serviceChargeTypeCode) {
		this.serviceChargeTypeCode = serviceChargeTypeCode;
	}
	
	public Long getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Long maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getIsIncreaseInterest() {
		return isIncreaseInterest;
	}

	public void setIsIncreaseInterest(String isIncreaseInterest) {
		this.isIncreaseInterest = isIncreaseInterest;
	}

	public Double getIncreaseInterestRate() {
		return increaseInterestRate;
	}

	public void setIncreaseInterestRate(Double increaseInterestRate) {
		this.increaseInterestRate = increaseInterestRate;
	}

	public String getIsLoan() {
		return isLoan;
	}

	public void setIsLoan(String isLoan) {
		this.isLoan = isLoan;
	}
	
	

	public Date getLoanDt() {
		return loanDt;
	}

	public void setLoanDt(Date loanDt) {
		this.loanDt = loanDt;
	}

	public Integer getSortInList() {
		return sortInList;
	}

	public void setSortInList(Integer sortInList) {
		this.sortInList = sortInList;
	}

	public Integer getSortInIndex() {
		return sortInIndex;
	}

	public void setSortInIndex(Integer sortInIndex) {
		this.sortInIndex = sortInIndex;
	}

	public List<String> getShowTermList() {
		return showTermList;
	}

	public void setShowTermList(List<String> showTermList) {
		this.showTermList = showTermList;
	}

	public String getCanUseRateTicket() {
		return canUseRateTicket;
	}

	public void setCanUseRateTicket(String canUseRateTicket) {
		this.canUseRateTicket = canUseRateTicket;
	}

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public String getCreditName() {
		return creditName;
	}

	public void setCreditName(String creditName) {
		this.creditName = creditName;
	}

	public String getProjectTips() {
		return projectTips;
	}

	public void setProjectTips(String projectTips) {
		this.projectTips = projectTips;
	}
}