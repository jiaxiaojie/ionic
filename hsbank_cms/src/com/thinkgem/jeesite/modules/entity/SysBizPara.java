/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 业务参数Entity
 * @author yangtao
 * @version 2015-08-13
 */
public class SysBizPara extends DataEntity<SysBizPara> {
	
	private static final long serialVersionUID = 1L;
	private String projectDefaultRiskArticleId;		// 缺省风控文章Id
	private String cmsZxggCategoryId;		// 最新公告栏目编号
	private String cmsZxhdCategoryId;		// 最新活动栏目编号
	private String cmsZxxwCategoryId;		// 富定新闻栏目编号
	private Double projectEarlyRepayRatio;		// 提前还款罚息比例
	private Double projectOverdueRepayRatio;		// 逾期还款日罚息比例
	private Double projectPersonCreditLoanYearRate;		// 个人信用贷年化利率
	private Double projectPersonalCreditLoanServiceRate;		// 个人信用贷服务费费率
	private Double projectPersonalCreditLoanMaxValue;		// 个人信用贷最高额度
	private Integer projectPersonalCreditLoanMaxTerms;		// 个人信用贷缺省期数
	private Double projectTransferServiceRate;		// 投资转让手续费费率
	private Integer projectMaxOverdueDayCount;		// 逾期代偿天数
	private Integer projectTransferDayCountToNextRepay;		// 距下一个收款日N天内不允许转让
	private Double projectTransferServiceUpRate;		// 债权转让上家费率
	private Double projectTrnasferServiceDownRate;		// 债权转让下家费率
	private Double platformAmountRate;		// 平台垫付金额比例
	private Double projectServiceChargeRate;		// 投资时收取服务费的比例
	private Double projectTicketAmountRate;		// 投资时可使用优惠券的比例
	private Integer projectTransferMaxAssignmentHours;		// 债权转让周期
	private Integer projectMaxAmountDefault; //最大投资金额
	private Integer onedayMaxWithdrawCount; //一天最多提现次数
	
	public Integer getProjectMaxAmountDefault() {
		return projectMaxAmountDefault;
	}

	public void setProjectMaxAmountDefault(Integer projectMaxAmountDefault) {
		this.projectMaxAmountDefault = projectMaxAmountDefault;
	}

	public Integer getOnedayMaxWithdrawCount() {
		return onedayMaxWithdrawCount;
	}

	public void setOnedayMaxWithdrawCount(Integer onedayMaxWithdrawCount) {
		this.onedayMaxWithdrawCount = onedayMaxWithdrawCount;
	}

	public SysBizPara() {
		super();
	}

	public SysBizPara(String id){
		super(id);
	}

	@Length(min=0, max=500, message="缺省风控文章Id长度必须介于 0 和 500 之间")
	public String getProjectDefaultRiskArticleId() {
		return projectDefaultRiskArticleId;
	}

	public void setProjectDefaultRiskArticleId(String projectDefaultRiskArticleId) {
		this.projectDefaultRiskArticleId = projectDefaultRiskArticleId;
	}
	
	@Length(min=0, max=500, message="最新公告栏目编号长度必须介于 0 和 500 之间")
	public String getCmsZxggCategoryId() {
		return cmsZxggCategoryId;
	}

	public void setCmsZxggCategoryId(String cmsZxggCategoryId) {
		this.cmsZxggCategoryId = cmsZxggCategoryId;
	}
	
	@Length(min=0, max=500, message="最新活动栏目编号长度必须介于 0 和 500 之间")
	public String getCmsZxhdCategoryId() {
		return cmsZxhdCategoryId;
	}

	public void setCmsZxhdCategoryId(String cmsZxhdCategoryId) {
		this.cmsZxhdCategoryId = cmsZxhdCategoryId;
	}
	
	@Length(min=0, max=500, message="富定新闻栏目编号长度必须介于 0 和 500 之间")
	public String getCmsZxxwCategoryId() {
		return cmsZxxwCategoryId;
	}

	public void setCmsZxxwCategoryId(String cmsZxxwCategoryId) {
		this.cmsZxxwCategoryId = cmsZxxwCategoryId;
	}
	
	public Double getProjectEarlyRepayRatio() {
		return projectEarlyRepayRatio;
	}

	public void setProjectEarlyRepayRatio(Double projectEarlyRepayRatio) {
		this.projectEarlyRepayRatio = projectEarlyRepayRatio;
	}
	
	public Double getProjectOverdueRepayRatio() {
		return projectOverdueRepayRatio;
	}

	public void setProjectOverdueRepayRatio(Double projectOverdueRepayRatio) {
		this.projectOverdueRepayRatio = projectOverdueRepayRatio;
	}
	
	public Double getProjectPersonCreditLoanYearRate() {
		return projectPersonCreditLoanYearRate;
	}

	public void setProjectPersonCreditLoanYearRate(Double projectPersonCreditLoanYearRate) {
		this.projectPersonCreditLoanYearRate = projectPersonCreditLoanYearRate;
	}
	
	public Double getProjectPersonalCreditLoanServiceRate() {
		return projectPersonalCreditLoanServiceRate;
	}

	public void setProjectPersonalCreditLoanServiceRate(Double projectPersonalCreditLoanServiceRate) {
		this.projectPersonalCreditLoanServiceRate = projectPersonalCreditLoanServiceRate;
	}
	
	public Double getProjectPersonalCreditLoanMaxValue() {
		return projectPersonalCreditLoanMaxValue;
	}

	public void setProjectPersonalCreditLoanMaxValue(Double projectPersonalCreditLoanMaxValue) {
		this.projectPersonalCreditLoanMaxValue = projectPersonalCreditLoanMaxValue;
	}
	
	public Integer getProjectPersonalCreditLoanMaxTerms() {
		return projectPersonalCreditLoanMaxTerms;
	}

	public void setProjectPersonalCreditLoanMaxTerms(Integer projectPersonalCreditLoanMaxTerms) {
		this.projectPersonalCreditLoanMaxTerms = projectPersonalCreditLoanMaxTerms;
	}
	
	public Double getProjectTransferServiceRate() {
		return projectTransferServiceRate;
	}

	public void setProjectTransferServiceRate(Double projectTransferServiceRate) {
		this.projectTransferServiceRate = projectTransferServiceRate;
	}
	
	public Integer getProjectMaxOverdueDayCount() {
		return projectMaxOverdueDayCount;
	}

	public void setProjectMaxOverdueDayCount(Integer projectMaxOverdueDayCount) {
		this.projectMaxOverdueDayCount = projectMaxOverdueDayCount;
	}
	
	public Integer getProjectTransferDayCountToNextRepay() {
		return projectTransferDayCountToNextRepay;
	}

	public void setProjectTransferDayCountToNextRepay(Integer projectTransferDayCountToNextRepay) {
		this.projectTransferDayCountToNextRepay = projectTransferDayCountToNextRepay;
	}
	
	public Double getProjectTransferServiceUpRate() {
		return projectTransferServiceUpRate;
	}

	public void setProjectTransferServiceUpRate(Double projectTransferServiceUpRate) {
		this.projectTransferServiceUpRate = projectTransferServiceUpRate;
	}
	
	public Double getProjectTrnasferServiceDownRate() {
		return projectTrnasferServiceDownRate;
	}

	public void setProjectTrnasferServiceDownRate(Double projectTrnasferServiceDownRate) {
		this.projectTrnasferServiceDownRate = projectTrnasferServiceDownRate;
	}
	
	public Double getPlatformAmountRate() {
		return platformAmountRate;
	}

	public void setPlatformAmountRate(Double platformAmountRate) {
		this.platformAmountRate = platformAmountRate;
	}
	
	public Double getProjectServiceChargeRate() {
		return projectServiceChargeRate;
	}

	public void setProjectServiceChargeRate(Double projectServiceChargeRate) {
		this.projectServiceChargeRate = projectServiceChargeRate;
	}
	
	public Double getProjectTicketAmountRate() {
		return projectTicketAmountRate;
	}

	public void setProjectTicketAmountRate(Double projectTicketAmountRate) {
		this.projectTicketAmountRate = projectTicketAmountRate;
	}
	
	public Integer getProjectTransferMaxAssignmentHours() {
		return projectTransferMaxAssignmentHours;
	}

	public void setProjectTransferMaxAssignmentHours(Integer projectTransferMaxAssignmentHours) {
		this.projectTransferMaxAssignmentHours = projectTransferMaxAssignmentHours;
	}
	
}