package com.thinkgem.jeesite.modules.api.to;

/**
 * 借款协议
 * @author lizibo
 *
 */
public class InvestAgreementResp {
	private String aCustomerName;		// 甲方（出借人）
	private String aAccountName;		// 甲方（花生金服用户名）
	private String aCertNum;		// 甲方（身份证号码）
	private String aAddress;			//甲方（通讯地址）
	private String aMobile;			//甲方（联系电话）
	private String bCustomerName;			//乙方（借款人）
	private String bAccountName;			//乙方（花生金服用户名）
	private String bCertNum;			//乙方（身份证号码）
	private String bAddress;			//乙方（通讯地址）
	private String bMobile;			//乙方（联系电话）
	private String loanAmount;			//借款金额
	private String startYear;			//借款期限（起始日期：年）
	private String startMonth;			//借款期限（起始日期：月）
	private String startDay;			//借款期限（起始日期：日）
	private String endYear;			//借款期限（截止日期：年）
	private String endMonth;			//借款期限（截止日期：月）
	private String endDay;			//借款期限（截止日期：日）
	private String rate;			//借款年利率
	private String useType;			//借款用途
	private String repaymentMode;			//还款方式
	private String repaymentModeName;			//还款方式名称
	private String repayDay;			//还款日
	private String safeguardMode;			//担保方式
	private String upperLoanAmount;			//借款金额（大写）
	private String theYear;			//日期(年)
	private String theMonth;			//日期(月)
	private String theDay;			//日期(日)
	public String getaCustomerName() {
		return aCustomerName;
	}
	public void setaCustomerName(String aCustomerName) {
		this.aCustomerName = aCustomerName;
	}
	public String getaAccountName() {
		return aAccountName;
	}
	public void setaAccountName(String aAccountName) {
		this.aAccountName = aAccountName;
	}
	public String getaCertNum() {
		return aCertNum;
	}
	public void setaCertNum(String aCertNum) {
		this.aCertNum = aCertNum;
	}
	public String getaAddress() {
		return aAddress;
	}
	public void setaAddress(String aAddress) {
		this.aAddress = aAddress;
	}
	public String getaMobile() {
		return aMobile;
	}
	public void setaMobile(String aMobile) {
		this.aMobile = aMobile;
	}
	public String getbCustomerName() {
		return bCustomerName;
	}
	public void setbCustomerName(String bCustomerName) {
		this.bCustomerName = bCustomerName;
	}
	public String getbAccountName() {
		return bAccountName;
	}
	public void setbAccountName(String bAccountName) {
		this.bAccountName = bAccountName;
	}
	public String getbCertNum() {
		return bCertNum;
	}
	public void setbCertNum(String bCertNum) {
		this.bCertNum = bCertNum;
	}
	public String getbAddress() {
		return bAddress;
	}
	public void setbAddress(String bAddress) {
		this.bAddress = bAddress;
	}
	public String getbMobile() {
		return bMobile;
	}
	public void setbMobile(String bMobile) {
		this.bMobile = bMobile;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndYear() {
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	public String getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getRepaymentMode() {
		return repaymentMode;
	}
	public void setRepaymentMode(String repaymentMode) {
		this.repaymentMode = repaymentMode;
	}

	public String getRepaymentModeName() {
		return repaymentModeName;
	}

	public void setRepaymentModeName(String repaymentModeName) {
		this.repaymentModeName = repaymentModeName;
	}

	public String getRepayDay() {
		return repayDay;
	}
	public void setRepayDay(String repayDay) {
		this.repayDay = repayDay;
	}
	public String getSafeguardMode() {
		return safeguardMode;
	}
	public void setSafeguardMode(String safeguardMode) {
		this.safeguardMode = safeguardMode;
	}
	public String getUpperLoanAmount() {
		return upperLoanAmount;
	}
	public void setUpperLoanAmount(String upperLoanAmount) {
		this.upperLoanAmount = upperLoanAmount;
	}
	public String getTheYear() {
		return theYear;
	}
	public void setTheYear(String theYear) {
		this.theYear = theYear;
	}
	public String getTheMonth() {
		return theMonth;
	}
	public void setTheMonth(String theMonth) {
		this.theMonth = theMonth;
	}
	public String getTheDay() {
		return theDay;
	}
	public void setTheDay(String theDay) {
		this.theDay = theDay;
	}
	
	
}
