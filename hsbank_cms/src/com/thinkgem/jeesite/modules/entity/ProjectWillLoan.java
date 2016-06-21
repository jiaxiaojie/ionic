/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 借贷意向管理Entity
 * @author yangtao
 * @version 2015-06-24
 */
public class ProjectWillLoan extends DataEntity<ProjectWillLoan> {
	
	private static final long serialVersionUID = 1L;
	private String contactName;		// 联系人
	private String mobile;		// 手机号码
	private Double loanMoney;		// 融资金额
	private String title;	//标题
	private Integer	duration; //期限
	private Double annualizedRate; //年化利率
	private Area area;		// 所在区域
	private String remark;		// 备注说明
	private Date createDt;		// 创建时间
	private Long createUserId;		// 创建人账号
	private String createIp;		// 创建IP
	private Long confirmUserId;		// 确认人员
	private String status;		// 确认状态
	private String confirmRemark;		// 确认备注
	private Double serviceCharge;//平台手续费
	private String useType;//借款用途
	private Double contractMoney;//合同金额
	private Double monthRate; //月综合费率
	private Double sumRepayMoney;//总还款额
	private Double monthRepayMoney;//月还款额
	private String hasCreateProject;//是否已经生成项目

	private String canLoanMoneyStr;//可以借款格式化结果


	public String getCanLoanMoneyStr() {
		return canLoanMoneyStr;
	}

	public void setCanLoanMoneyStr(String canLoanMoneyStr) {
		this.canLoanMoneyStr = canLoanMoneyStr;
	}

	//过程数据，不体现在意向中
	private Double creditLimit;// 信用额度
	private Double applyingMoney;// 申请中的额度
	private Double loaningMoney;// 还款中的额度
	private Double canLoanMoney;// 可以接的额度
	
	private String projectCode;		// 转换为项目编号
	private Double beginMoney;		// 开始 融资金额
	private Double endMoney;		// 结束 融资金额
	private Date beginCreateDt;		// 开始 创建时间
	private Date endCreateDt;		// 结束 创建时间
	
	private User confirmUser; //审核人
	private CustomerBase createCustomer;
	
	public Double getMonthRepayMoney() {
		return monthRepayMoney;
	}

	public String getHasCreateProject() {
		return hasCreateProject;
	}

	public void setHasCreateProject(String hasCreateProject) {
		this.hasCreateProject = hasCreateProject;
	}
	public void setMonthRepayMoney(Double monthRepayMoney) {
		this.monthRepayMoney = monthRepayMoney;
	}
	public CustomerBase getCreateCustomer() {
		return createCustomer;
	}

	public void setCreateCustomer(CustomerBase createCustomer) {
		this.createCustomer = createCustomer;
	}

	public ProjectWillLoan() {
		super();
	}

	public ProjectWillLoan(String id){
		super(id);
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@Length(min=0, max=1000, message="备注说明长度必须介于 0 和 1000 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	@Length(min=0, max=25, message="创建IP长度必须介于 0 和 25 之间")
	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	
	public Long getConfirmUserId() {
		return confirmUserId;
	}

	public void setConfirmUserId(Long confirmUserId) {
		this.confirmUserId = confirmUserId;
	}
	
	@Length(min=0, max=2, message="确认状态长度必须介于 0 和 2 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=10, max=1000, message="确认备注长度必须介于10 和 1000 之间")
	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}
	
	@Length(min=0, max=50, message="转换为项目编号长度必须介于 0 和 50 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Double getBeginMoney() {
		return beginMoney;
	}

	public void setBeginMoney(Double beginMoney) {
		this.beginMoney = beginMoney;
	}
	
	public Double getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(Double endMoney) {
		this.endMoney = endMoney;
	}
		
	public Date getBeginCreateDt() {
		return beginCreateDt;
	}

	public void setBeginCreateDt(Date beginCreateDt) {
		this.beginCreateDt = beginCreateDt;
	}
	
	public Date getEndCreateDt() {
		return endCreateDt;
	}

	public void setEndCreateDt(Date endCreateDt) {
		this.endCreateDt = endCreateDt;
	}
		
	public User getConfirmUser() {
		return confirmUser;
	}

	public void setConfirmUser(User confirmUser) {
		this.confirmUser = confirmUser;
	}


	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Double getAnnualizedRate() {
		return annualizedRate;
	}

	public void setAnnualizedRate(Double annualizedRate) {
		this.annualizedRate = annualizedRate;
	}
	
	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Double getApplyingMoney() {
		return applyingMoney;
	}

	public void setApplyingMoney(Double applyingMoney) {
		this.applyingMoney = applyingMoney;
	}

	public Double getLoaningMoney() {
		return loaningMoney;
	}

	public void setLoaningMoney(Double loaningMoney) {
		this.loaningMoney = loaningMoney;
	}

	public Double getCanLoanMoney() {
		return canLoanMoney;
	}

	public void setCanLoanMoney(Double canLoanMoney) {
		this.canLoanMoney = canLoanMoney;
	}
	
	public Double getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(Double loanMoney) {
		this.loanMoney = loanMoney;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Double getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Double getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(Double monthRate) {
		this.monthRate = monthRate;
	}

	public Double getSumRepayMoney() {
		return sumRepayMoney;
	}

	public void setSumRepayMoney(Double sumRepayMoney) {
		this.sumRepayMoney = sumRepayMoney;
	}

	public void replaceSpecialStr() {
		StringUtils.replaceSpecialStr(title);
	}


}