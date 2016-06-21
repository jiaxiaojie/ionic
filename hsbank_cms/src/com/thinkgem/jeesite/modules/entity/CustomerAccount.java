package com.thinkgem.jeesite.modules.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 会员账号信息Entity
 * @author ydt
 * @version 2015-06-23
 */
public class CustomerAccount extends DataEntity<CustomerAccount> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号编号
	private String accountName;		// 登录名
	private String recommendAccountId;		// 推荐账号
	private String accountType;		// 账号类型
	private String nickname;		// 昵称
	private String avatarImage;		// 头像
	private String accountPwd;		// 密码
	private String registerChannel;		// 注册渠道
	private String inviteCode;		// 邀请码
	private String statusCode;		// 账号状态
	private String userStatus;		// 用户状态
	private Date registerDt;		// 注册时间
	private String registerIp;		// 注册IP
	private Date lastLoginDt;		// 最后一次登录时间
	private String lastLoginTermCode;		// 最后一次登录终端类型
	private String lastLoginIp;		// 最后一次登录IP
	private String resetPwdType;		// 重置密码方式
	private Integer errCount;		// 登录次数
	private String errIp;		// 错误登录IP
	private String errTermCode;		// 错误登录终端类型
	private Date errDt;		// 错误登录时间
	private String hasOpenThirdAccount;		// 是否开通第三方账号（0：未开通；1：已开通）
	private String requestNo;		// 开通第三方账号请求流水号
	private Date beginRegisterDt;		// 开始 时间
	private Date endRegisterDt;		// 结束 时间
	private String queryParas; //下拉列表框查询选择

	private String platformUserNo;	//传给易宝的平台用户标志
	private String recommenderMobile;	//推荐人手机号
	private String recommenderType;	//推荐人类型
	private String outerId;			//外部编号
	private String registerSource;	//注册来源
	private ProjectInvestmentRecord projectInvestmentRecord;
	private CustomerBase customerBase;	//用户基本信息
	private CustomerBalance customerBalance;	//会员账户余额汇总
	private CustomerBankCard customerBankCard;  //会员账户信息

	private String channelId;

	public CustomerAccount() {
		super();
	}

	public CustomerAccount(String accountId){
		super(accountId);
	}

	public CustomerAccount(String accountId, String accountName) {
		super(accountId);
		this.accountName = accountName;
	}

	public CustomerAccount(Long accountId) {
		this.accountId = accountId;
	}


	public CustomerBankCard getCustomerBankCard() {
		return customerBankCard;
	}

	public void setCustomerBankCard(CustomerBankCard customerBankCard) {

		this.customerBankCard = customerBankCard;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getUserStatus() {
		return userStatus;
	}
   
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	@Length(min=1, max=50, message="登录名长度必须介于 1 和 50 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Length(min=0, max=50, message="推荐账号长度必须介于 0 和 50 之间")
	public String getRecommendAccountId() {
		return recommendAccountId;
	}

	public void setRecommendAccountId(String recommendAccountId) {
		this.recommendAccountId = recommendAccountId;
	}
	
	@Length(min=0, max=2, message="账号类型长度必须介于 0 和 2 之间")
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	
	public ProjectInvestmentRecord getProjectInvestmentRecord() {
		return projectInvestmentRecord;
	}

	public void setProjectInvestmentRecord(ProjectInvestmentRecord projectInvestmentRecord) {
		this.projectInvestmentRecord = projectInvestmentRecord;
	}

	
	
	/**
	 * 对应该有默认值而为空的属性设置默认值
	 */
	public void setDefaultValue() {
		if(StringUtils.isBlank(accountType)) {
			accountType = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(registerChannel)) {
			registerChannel = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(statusCode)) {
			statusCode = ProjectConstant.DICT_DEFAULT_VALUE;
		}
		if(StringUtils.isBlank(hasOpenThirdAccount)) {
			hasOpenThirdAccount = ProjectConstant.DICT_DEFAULT_VALUE;
		}
	}
	
	@Length(min=0, max=50, message="昵称长度必须介于 0 和 50 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=200, message="头像长度必须介于 0 和 200 之间")
	public String getAvatarImage() {
		avatarImage = StringUtil.dealString(avatarImage);
		if ("".equals(avatarImage)) {
			avatarImage = "/static/modules/front/images/util/sex/man.png";
		}
		return avatarImage;
	}

	public void setAvatarImage(String avatarImage) {
		this.avatarImage = avatarImage;
	}
	
	@Length(min=0, max=100, message="密码长度必须介于 0 和 100 之间")
	public String getAccountPwd() {
		return accountPwd;
	}

	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}
	
	@Length(min=0, max=2, message="注册渠道长度必须介于 0 和 2 之间")
	public String getRegisterChannel() {
		return registerChannel;
	}

	public void setRegisterChannel(String registerChannel) {
		this.registerChannel = registerChannel;
	}
	
	@Length(min=0, max=50, message="邀请码长度必须介于 0 和 50 之间")
	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	
	@Length(min=0, max=2, message="账号状态长度必须介于 0 和 2 之间")
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRegisterDt() {
		return registerDt;
	}

	public void setRegisterDt(Date registerDt) {
		this.registerDt = registerDt;
	}
	
	@Length(min=0, max=50, message="注册IP长度必须介于 0 和 50 之间")
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginDt() {
		return lastLoginDt;
	}

	public void setLastLoginDt(Date lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}
	
	@Length(min=0, max=2, message="最后一次登录终端类型长度必须介于 0 和 2 之间")
	public String getLastLoginTermCode() {
		return lastLoginTermCode;
	}

	public void setLastLoginTermCode(String lastLoginTermCode) {
		this.lastLoginTermCode = lastLoginTermCode;
	}
	
	@Length(min=0, max=50, message="最后一次登录IP长度必须介于 0 和 50 之间")
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	public Integer getErrCount() {
		return errCount;
	}

	public void setErrCount(Integer errCount) {
		this.errCount = errCount;
	}
	
	@Length(min=0, max=50, message="错误登录IP长度必须介于 0 和 50 之间")
	public String getErrIp() {
		return errIp;
	}

	public void setErrIp(String errIp) {
		this.errIp = errIp;
	}
	
	@Length(min=0, max=2, message="错误登录终端类型长度必须介于 0 和 2 之间")
	public String getErrTermCode() {
		return errTermCode;
	}

	public void setErrTermCode(String errTermCode) {
		this.errTermCode = errTermCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getErrDt() {
		return errDt;
	}

	public void setErrDt(Date errDt) {
		this.errDt = errDt;
	}
	
	public Date getBeginRegisterDt() {
		return beginRegisterDt;
	}

	public void setBeginRegisterDt(Date beginRegisterDt) {
		this.beginRegisterDt = beginRegisterDt;
	}
	
	public Date getEndRegisterDt() {
		return endRegisterDt;
	}

	public void setEndRegisterDt(Date endRegisterDt) {
		this.endRegisterDt = endRegisterDt;
	}

	public CustomerBase getCustomerBase() {
		return customerBase;
	}

	public void setCustomerBase(CustomerBase customerBase) {
		this.customerBase = customerBase;
	}

	public CustomerBalance getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(CustomerBalance customerBalance) {
		this.customerBalance = customerBalance;
	}

	@Length(min=0, max=2, message="重置密码方式长度必须介于 0 和 2 之间")
	public String getResetPwdType() {
		return resetPwdType;
	}

	public void setResetPwdType(String resetPwdType) {
		this.resetPwdType = resetPwdType;
	}
	
	public String getQueryParas() {
		return queryParas;
	}

	public void setQueryParas(String queryParas) {
		this.queryParas = queryParas;
	}

	public String getLoginFlag() {
		return null;
	}
	
	public String getHasOpenThirdAccount() {
		return hasOpenThirdAccount;
	}

	public void setHasOpenThirdAccount(String hasOpenThirdAccount) {
		this.hasOpenThirdAccount = hasOpenThirdAccount;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getRecommenderMobile() {
		return recommenderMobile;
	}

	public void setRecommenderMobile(String recommenderMobile) {
		this.recommenderMobile = recommenderMobile;
	}

	public String getRecommenderType() {
		return recommenderType;
	}

	public void setRecommenderType(String recommenderType) {
		this.recommenderType = recommenderType;
	}

	/**
	 * 获取账号安全级别分数，满分100分
	 * @return
	 */
	public int getSafeScore() {
		int safeScore = 0;
		if("1".equals(hasOpenThirdAccount)) {
			safeScore += 75;
		}else {
			safeScore += 25;
		}
		if(customerBase != null && "1".equals(customerBase.getEmailAuthCode())) {
			safeScore += 25;
		}
		return safeScore;
	}

	public String getPlatformUserNo() {
		return platformUserNo;
	}

	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getRegisterSource() {
		return registerSource;
	}

	public void setRegisterSource(String registerSource) {
		this.registerSource = registerSource;
	}
	
}