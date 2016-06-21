package com.thinkgem.jeesite.modules.api.to;

import java.util.Map;

/**
 * 我的信息
 * @author lizibo
 *
 */
public class MyResp {
	private String accountId;	//会员账号Id
	private String avatar;		// 头像
	private String nickname;		// 昵称
	private String accountName;  //账户名称（空表示昵称未设置）
	private String customerName;// 真实姓名
	private Double goldBalance;		// 当前账户余额值
	private Double congealVal;		// 冻结余额值
	private String certNum;		// 身份证号码
	private String mobile;			// 手机号
	private String email;			// 电子邮箱
	private String bankCardNo;		//银行卡号
	private String BankName;         //银行名称
	private Double netAssets;		// 账户净资产
	private Double currentInvestment;	//活期投资总额
	private Double periodicInvestment;	//定期投资总额
	private Double profitTotal;	//账户收益(用户总的收益)
	private Double willProfit;		// 待收收益
	private Double willPrincipal;	//待收本金
	private Double sumProfit;		// 累计收益
	private Double currentProfit;	//活期收益
	private Double obtainProfit;		//已获收益(用户定期投资的已回款的累计收益)
	private Double activityReward;	//活动奖励(用户参与各种活动，任务所获得的奖励)
	private Double availableBalance;	//可用余额
	private Double availableIntegral;	//可用花生豆余额
	private String hasSigned; //是否签到（true 或 false)
	private String hasRemindOfMsg;	//是否有未读消息（true 或 false)
	private String hasRemindOfTicket;	//是否有可用投资券（true 或 false)
	private String hasRemindOfReward;	//是否有积分提醒（true 或 false)
	private String hasOpenThirdAccount;	//是否开通第三方账号（0：未开通；1：已开通）
	private String hasBindBankCard;	//是否绑定银行卡（0：未绑卡；1：已绑卡）
	private String hasRecharged;		//是否充过值（0是，其它不是）
	private String isNewUser;		//是否新手（0是，其它不是）
	private BeforeWithdrawResp bankCard; //银行卡信息
	private Map<String, Object> current;
	private String nameAuthCode; //是否实名认证
	private String mobileAuthCode; //是否手机号认证
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public Double getNetAssets() {
		return netAssets;
	}
	public void setNetAssets(Double netAssets) {
		this.netAssets = netAssets;
	}
	public Double getWillProfit() {
		return willProfit;
	}
	public void setWillProfit(Double willProfit) {
		this.willProfit = willProfit;
	}
	public Double getSumProfit() {
		return sumProfit;
	}
	public void setSumProfit(Double sumProfit) {
		this.sumProfit = sumProfit;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getHasSigned() {
		return hasSigned;
	}
	public void setHasSigned(String hasSigned) {
		this.hasSigned = hasSigned;
	}
	public String getHasRemindOfMsg() {
		return hasRemindOfMsg;
	}
	public void setHasRemindOfMsg(String hasRemindOfMsg) {
		this.hasRemindOfMsg = hasRemindOfMsg;
	}
	public String getHasRemindOfTicket() {
		return hasRemindOfTicket;
	}
	public void setHasRemindOfTicket(String hasRemindOfTicket) {
		this.hasRemindOfTicket = hasRemindOfTicket;
	}
	public String getHasRemindOfReward() {
		return hasRemindOfReward;
	}
	public void setHasRemindOfReward(String hasRemindOfReward) {
		this.hasRemindOfReward = hasRemindOfReward;
	}
	public String getHasOpenThirdAccount() {
		return hasOpenThirdAccount;
	}
	public void setHasOpenThirdAccount(String hasOpenThirdAccount) {
		this.hasOpenThirdAccount = hasOpenThirdAccount;
	}
	public String getHasBindBankCard() {
		return hasBindBankCard;
	}
	public void setHasBindBankCard(String hasBindBankCard) {
		this.hasBindBankCard = hasBindBankCard;
	}
	public String getIsNewUser() {
		return isNewUser;
	}
	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}
	public Double getCongealVal() {
		return congealVal;
	}
	public void setCongealVal(Double congealVal) {
		this.congealVal = congealVal;
	}
	public Double getGoldBalance() {
		return goldBalance;
	}
	public void setGoldBalance(Double goldBalance) {
		this.goldBalance = goldBalance;
	}
	public Double getAvailableIntegral() {
		return availableIntegral;
	}
	public void setAvailableIntegral(Double availableIntegral) {
		this.availableIntegral = availableIntegral;
	}
	public Double getWillPrincipal() {
		return willPrincipal;
	}
	public void setWillPrincipal(Double willPrincipal) {
		this.willPrincipal = willPrincipal;
	}
	public String getHasRecharged() {
		return hasRecharged;
	}
	public void setHasRecharged(String hasRecharged) {
		this.hasRecharged = hasRecharged;
	}
	public BeforeWithdrawResp getBankCard() {
		return bankCard;
	}
	public void setBankCard(BeforeWithdrawResp bankCard) {
		this.bankCard = bankCard;
	}
	public Map<String, Object> getCurrent() {
		return current;
	}
	public void setCurrent(Map<String, Object> current) {
		this.current = current;
	}
	public Double getCurrentInvestment() {
		return currentInvestment;
	}
	public void setCurrentInvestment(Double currentInvestment) {
		this.currentInvestment = currentInvestment;
	}
	public Double getPeriodicInvestment() {
		return periodicInvestment;
	}
	public void setPeriodicInvestment(Double periodicInvestment) {
		this.periodicInvestment = periodicInvestment;
	}
	public Double getProfitTotal() {
		return profitTotal;
	}
	public void setProfitTotal(Double profitTotal) {
		this.profitTotal = profitTotal;
	}
	public Double getCurrentProfit() {
		return currentProfit;
	}
	public void setCurrentProfit(Double currentProfit) {
		this.currentProfit = currentProfit;
	}
	public Double getObtainProfit() {
		return obtainProfit;
	}
	public void setObtainProfit(Double obtainProfit) {
		this.obtainProfit = obtainProfit;
	}
	public Double getActivityReward() {
		return activityReward;
	}
	public void setActivityReward(Double activityReward) {
		this.activityReward = activityReward;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getNameAuthCode() {
		return nameAuthCode;
	}

	public void setNameAuthCode(String nameAuthCode) {
		this.nameAuthCode = nameAuthCode;
	}

	public String getMobileAuthCode() {
		return mobileAuthCode;
	}

	public void setMobileAuthCode(String mobileAuthCode) {
		this.mobileAuthCode = mobileAuthCode;
	}

	public String getBankName() {
		return BankName;
	}

	public void setBankName(String bankName) {
		BankName = bankName;
	}


}
