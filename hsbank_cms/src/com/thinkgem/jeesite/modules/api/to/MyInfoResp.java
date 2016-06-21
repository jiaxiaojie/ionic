package com.thinkgem.jeesite.modules.api.to;

/**
 * 我的信息
 * @author lizibo
 *
 */
public class MyInfoResp {
    private String accountId;	//会员账号Id
	private String avatar;		// 头像
	private String nickname;		// 昵称
	private String accountName;		//会员账号名称
	private String certNum;		// 身份证号码
	private String customerName;// 真实姓名
	private String mobile;			// 手机号
	private String email;			// 电子邮箱
	private Double availableBalance;	//可用余额
	private Double availableIntegral;	//可用花生豆余额
	private String hasSigned; //是否签到（true 或 false)
	private String hasOpenThirdAccount;	//是否开通第三方账号（0：未开通；1：已开通）
	private String hasBindBankCard;	//是否绑定银行卡（0：未绑卡；1：已绑卡）
	private String hasRecharged;		//是否充过值（0是，其它不是）
	private String isNewUser;		//是否新手（0是，其它不是）
	private Double cashCouponAmount;	//现金券总额
	private int cashCouponCount;		//现金券数量
	private int ticketCount;		//提现券张数

	private int interestCouponCount;		//加息券总数量
	private int unreadMsgTotal;		//未读消息总数量
	private String lastLoginTime;		//上次登录时间
	private int consecutiveDays;	//连续签到天数

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getAvailableIntegral() {
		return availableIntegral;
	}

	public void setAvailableIntegral(Double availableIntegral) {
		this.availableIntegral = availableIntegral;
	}

	public String getHasSigned() {
		return hasSigned;
	}

	public void setHasSigned(String hasSigned) {
		this.hasSigned = hasSigned;
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

	public String getHasRecharged() {
		return hasRecharged;
	}

	public void setHasRecharged(String hasRecharged) {
		this.hasRecharged = hasRecharged;
	}

	public String getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}

	public Double getCashCouponAmount() {
		return cashCouponAmount;
	}

	public void setCashCouponAmount(Double cashCouponAmount) {
		this.cashCouponAmount = cashCouponAmount;
	}

	public int getCashCouponCount() {
		return cashCouponCount;
	}

	public void setCashCouponCount(int cashCouponCount) {
		this.cashCouponCount = cashCouponCount;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}



	public int getInterestCouponCount() {
		return interestCouponCount;
	}

	public void setInterestCouponCount(int interestCouponCount) {
		this.interestCouponCount = interestCouponCount;
	}

	public int getUnreadMsgTotal() {
		return unreadMsgTotal;
	}

	public void setUnreadMsgTotal(int unreadMsgTotal) {
		this.unreadMsgTotal = unreadMsgTotal;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getConsecutiveDays() {
		return consecutiveDays;
	}

	public void setConsecutiveDays(int consecutiveDays) {
		this.consecutiveDays = consecutiveDays;
	}
}
