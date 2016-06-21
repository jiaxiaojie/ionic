/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import com.hsbank.util.tool.MobileUtil;
import com.hsbank.util.tool.mail.MailUtil;
import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.customer.dao.*;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBaseHandler;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.front.EmailCodeController;
import com.thinkgem.jeesite.modules.log.dao.LogSendValidateCodeDao;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelGetPrizeRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelPrizeInstanceDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.dao.AdChannelInfoDao;
import com.thinkgem.jeesite.modules.sys.dao.CreditLevelInfoDao;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 会员账号信息Service
 * @author ydt
 * @version 2015-06-23
 */
@Service
@Transactional(readOnly = true)
public class CustomerAccountService extends CrudService<CustomerAccountDao, CustomerAccount> {

	@Autowired
	private CustomerBaseHandler customerBaseHandler;
	@Autowired
	private CustomerRestPwdLogDao customerRestPwdLogDao;
	@Autowired
	private CustomerBaseDao customerBaseDao;
	@Autowired
	private CustomerBalanceDao customerBalanceDao;
	@Autowired
	private CreditLevelInfoDao creditLevelInfoDao;
	@Autowired
	private CustomerCreditAuthDao customerCreditAuthDao;
	@Autowired
	private CustomerIntegralSnapshotDao customerIntegralSnapshotDao;
	@Autowired
	private CustomerBankCardDao customerBankCardDao;
	@Autowired
	private LogSendValidateCodeDao logSendValidateCodeDao;
	@Autowired
	private CustomerEmployeeDao customerEmployeeDao;
	@Autowired
	private AdChannelInfoDao adChannelInfoDao;
	@Autowired
	private MarketingWheelPrizeInstanceDao marketingWheelPrizeInstanceDao;
	@Autowired
	private MarketingWheelGetPrizeRecordDao marketingWheelGetPrizeRecordDao;
	@Autowired
	private CurrentAccountSummaryDao currentAccountSummaryDao;
	
	
	public CustomerAccount get(String id) {
		return super.get(id);
	}
	
	public CustomerAccount get(Long accountId) {
		return dao.get(accountId);
	}
	
	public CustomerAccount getByLoginName(String loginName){
		return dao.getByLoginName(loginName);
	}
	public List<CustomerAccount> findList(CustomerAccount customerAccount) {
		return super.findList(customerAccount);
	}
	
	public List<CustomerAccount> findAlignmentList(Map<String, Object> map){
		return dao.findAlignmentList(map);
	}
	
	public Page<CustomerAccount> findPage(Page<CustomerAccount> page, CustomerAccount customerAccount) {
		return super.findPage(page, customerAccount);
	}
	
	/**
	 * 保存账号信息
	 * 	若果是新账号
	 * 		1.保存账号信息
	 * 		2.生成customerBase并保存
	 * 	否则
	 * 		1.更新账号信息
	 */
	@Transactional(readOnly = false)
	public void save(HttpServletRequest request, CustomerAccount customerAccount) {
		if(customerAccount.getIsNewRecord()) {
			customerAccount.setDefaultValue();
			customerAccount.setRegisterDt(new Date());
			customerAccount.setRegisterIp(request.getHeader("X-Real-IP"));
			dao.insert(customerAccount);
			dao.setPlatformUserNo(customerAccount.getAccountId(), generatePlatformUserNo(customerAccount.getAccountId()));
			CustomerBase customerBase = new CustomerBase();
			customerBase.setAccountId(customerAccount.getAccountId());
			customerBaseHandler.save(customerBase);
		} else {
			//如果为修改密码操作，则在密码密码重置记录表中插入记录
			dao.update(customerAccount);
			/*if(StringUtils.isNotBlank(customerAccount.getResetPwdVal())) {
				if(!customerAccount.getResetPwdVal().equals(customerAccount.getAccountPwd())) {
					CustomerRestPwdLog crp = new CustomerRestPwdLog();
					crp.setAccountId(customerAccount.getAccountId());
					crp.setResetDt(new Date());
					crp.setResetType(customerAccount.getResetPwdType());
					crp.setStatus(ProjectConstant.DICT_DEFAULT_VALUE);
					customerRestPwdLogDao.insert(crp);
				}
			}*/
		}
	}
	
	private String generatePlatformUserNo(long accountId) {
		return UUID.randomUUID().toString().replace("-", "").substring(0,12) + accountId;
	}

	@Transactional(readOnly = false)
	public void delete(CustomerAccount customerAccount) {
		super.delete(customerAccount);
	}

	/**
	 * 检测登录名是否可用
	 * @param customerAccount
	 * @return
	 */
	public boolean checkAccountNameCanUse(CustomerAccount customerAccount) {
		CustomerAccount ca = dao.getByAccountName(customerAccount);
		//如果未使用过或者账号名属于这个用户则表示可用
		if(ca == null || ca.getAccountId() == customerAccount.getAccountId()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查外部编号是否可用
	 * @param extendNo
	 * @return
	 */
	public boolean checkOuterIdCanUse(String  extendNo) {
		CustomerAccount ca = dao.getByExtendNo(extendNo);
		if(ca == null) {
			return true;
		}
		return false;
	}
	
	public List<CustomerAccount> querySimpleList(CustomerAccount qa){
		return dao.querySimpleList(qa);
	}

	/**
	 * 使用手机号注册账号，并返回登录名
	 * 		1.生成customerAccount并保存
	 * 		2.生成customerBase并保存
	 * 		3.生成默认customerBalance信息
	 * 		4.生成默认customerCreditAuth信息
	 * 		5.生成默认customerBankCard信息
	 * 		6.生成默认customerIntegralSnapshot信息
	 * 		7.若奖品token不为空，则将此奖品实例设置为已被抽中状态 奖品记录状态置为待赠送状态
	 * 		8.初始化活期账户数据
	 * @param mobile
	 * @param password
	 * @param recommendMobile	推荐人手机号
	 * @return
	 */
	@Transactional(readOnly = false)
	public String registerWithMobileAndReturnAccountName(HttpServletRequest request, String registerChannel, String mobile,
			String password, String recommendMobile, String channel, String lotteryToken,String outerId, String registerSource) {
		AdChannelInfo channelInfo = adChannelInfoDao.getByChannel(channel);
		
		//1.生成customerAccount并保存
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setRegisterChannel(registerChannel);
		customerAccount.setDefaultValue();
		customerAccount.setAccountName(createCustomerAccount());
		customerAccount.setAccountPwd(SystemService.entryptPassword(password));
		customerAccount.setStatusCode(ProjectConstant.CUSTOMER_ACCOUNT_STATUS_NORMAL);
		customerAccount.setRegisterDt(new Date());
		customerAccount.setRegisterIp(request.getHeader("X-Real-IP"));
		customerAccount.setChannelId(channelInfo==null?null:channelInfo.getId());
		customerAccount.setOuterId(outerId);
		customerAccount.setRegisterSource(registerSource);
		//推荐人信息。4种情况：手机号未填写、手机号为未注册用户、普通用户、员工。
		if(StringUtils.isBlank(recommendMobile)) {
			customerAccount.setRecommenderType(ProjectConstant.RECOMMENDER_TYPE_NONE);
		}else {
			customerAccount.setRecommenderMobile(recommendMobile);
			CustomerEmployee customerEmployee = customerEmployeeDao.getByMobile(recommendMobile);
			if(customerEmployee != null) {
				customerAccount.setRecommenderType(ProjectConstant.RECOMMENDER_TYPE_EMPLOYEE);
			}else {
				CustomerAccount recommender = dao.getByMobile(recommendMobile);
				if(recommender == null) {
					customerAccount.setRecommenderType(ProjectConstant.RECOMMENDER_TYPE_UNREGISTER);
				}else {
					customerAccount.setRecommenderType(ProjectConstant.RECOMMENDER_TYPE_NORMAL);
					customerAccount.setRecommendAccountId(recommender.getAccountId() + "");
				}
			}
		}
		dao.insert(customerAccount);
		dao.setPlatformUserNo(customerAccount.getAccountId(), generatePlatformUserNo(customerAccount.getAccountId()));
		//2.生成customerBase并保存
		CustomerBase customerBase = customerBaseDao.getByAccountId(customerAccount.getAccountId());
		customerBase.setMobile(mobile);
		customerBase.setMobileAuthCode(ProjectConstant.HAS_AUTHED);
		customerBaseHandler.save(customerBase);
		//3.生成默认customerBalance信息
		CustomerBalance customerBalance = new CustomerBalance();
		customerBalance.setDefaultValue();
		customerBalance.setAccountId(customerAccount.getAccountId());
		customerBalanceDao.insert(customerBalance);
		//4.生成默认customerCreditAuth信息
		CustomerCreditAuth customerCreditAuth = new CustomerCreditAuth();
		customerCreditAuth.setDefaultValue();
		customerCreditAuth.setCustomerId(customerBase.getCustomerId());
		double creditScore = 0d;
		customerCreditAuth.setCreditScore(creditScore);
		CreditLevelInfo creditLevelInfo = creditLevelInfoDao.getByScore(creditScore);
		customerCreditAuth.setCreditLimit(creditLevelInfo.getCreditLimit());
		customerCreditAuth.setCreditLevel(creditLevelInfo.getCreditLevel());
		customerCreditAuthDao.insert(customerCreditAuth);
		//5.生成默认customerBankCard信息
		CustomerBankCard customerBankCard = new CustomerBankCard();
		customerBankCard.setAccountId(customerAccount.getAccountId());
		customerBankCardDao.insert(customerBankCard);
		//6.生成默认customerIntegralSnapshot信息
		CustomerIntegralSnapshot customerIntegralSnapshot = new CustomerIntegralSnapshot();
		customerIntegralSnapshot.setDefaultValue();
		customerIntegralSnapshot.setAccountId(customerAccount.getAccountId());
		customerIntegralSnapshotDao.insert(customerIntegralSnapshot);
		//7.若奖品token不为空，则将此奖品实例设置为已被抽中状态 奖品记录状态置为待赠送状态
		if(StringUtils.isNotBlank(lotteryToken)) {
			marketingWheelPrizeInstanceDao.updateStatusByToken(lotteryToken, MarketConstant.WHEEL_PRIZE_INSTANCE_STATUS_GOT);
			marketingWheelGetPrizeRecordDao.updateStatusGetToGivingByToken(lotteryToken, customerAccount.getAccountId());
		}
		//8.初始化活期账户数据
		CurrentAccountSummary currentAccountSummary = new CurrentAccountSummary();
		currentAccountSummary = new CurrentAccountSummary();
		currentAccountSummary.setAccountId(customerAccount.getAccountId());
		currentAccountSummary.setTotalInvestmentMoney(0d);
		currentAccountSummary.setTotalGetInterest(0d);
		currentAccountSummary.setTotalRedeemPrincipal(0d);
		currentAccountSummary.setTotalRedeemInterest(0d);
		currentAccountSummary.setCurrentPrincipal(0d);
		currentAccountSummary.setCreateDt(new Date());
		currentAccountSummaryDao.insert(currentAccountSummary);
		if(isRepeatRegist(mobile)){
			throw new ServiceException("multi register mobile : " + mobile);
		}
		return customerAccount.getAccountName();
	}
	
	
	
	/**
	 * 是否重复注册
	 * @param mobile
	 * @return
	 */
	public  boolean isRepeatRegist(String mobile){
		int count = customerBaseDao.getCountByMobile(mobile);
		boolean isRep = false;
		if(count > 1 ){
			isRep = true;
		}
		return isRep;
	}
	
	/**
	 * 随机生成8位的用户账号，账号首位必须为字母
	 * @return
	 */
	private static String createCustomerAccount() {
		String[] letter = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		Random random = new Random();
		return letter[random.nextInt(letter.length)] + UUID.randomUUID().toString().substring(0,7);
	}
	
	/**
	 * 更新用户的requestNo
	 * @param customerAccount
	 */
	@Transactional(readOnly = false)
	public void updateRequestNo(CustomerAccount customerAccount) {
		dao.updateRequestNo(customerAccount);
	}
	
	/**
	 * 更新用户的头像
	 * @param customerAccount
	 */
	@Transactional(readOnly = false)
	public void updateAvatar(CustomerAccount customerAccount) {
		dao.updateAvatar(customerAccount);
	}

	/**
	 * 1.根据accountId更新customerAccount表hasOpenThirdAccount状态
	 * 2.更新customerBase表nameAuthCode状态
	 * @param accountId
	 * @param hasOpenThirdAccount
	 */
	@Transactional(readOnly = false)
	public int updateHasOpenThirdAccount(long accountId, String hasOpenThirdAccount) {
		int changeRowNumber = dao.updateHasOpenThirdAccount(accountId, hasOpenThirdAccount);
		customerBaseDao.updateNameAuthCodeByAccountId(accountId, ProjectConstant.HAS_AUTHED);
		return changeRowNumber;
	}

	/**
	 * 根据platformUserNo获取用户信息
	 * @param platformUserNo
	 * @return
	 */
	public CustomerAccount getByPlatformUserNo(String platformUserNo) {
		return dao.getByPlatformUserNo(platformUserNo);
	}

	/**
	 * 根据开通易宝账号的requestNo获取用户信息
	 * @param platformUserNo
	 * @return
	 */
	public CustomerAccount getByRequestNo(String requestNo) {
		return dao.getByRequestNo(requestNo);
	}

	/**
	 * 通过手机重置密码
	 * 		1.修改customerAccount表accountPwd
	 * 		2.添加customerRestPwdLog表新记录
	 * @param mobile
	 * @param newPassword
	 * @return
	 */
	@Transactional(readOnly = false)
	public CustomerAccount resetPasswordByMobile(String mobile, String newPassword) {
		
		CustomerAccount customerAccount = dao.getByMobile(mobile);
		customerAccount.setAccountId(customerAccount.getAccountId());
		customerAccount.setAccountPwd(SystemService.entryptPassword(newPassword));
		customerAccount.setResetPwdType(ProjectConstant.CUSTOMER_CHANGE_PASSWORD_BY_MOBILE_RESET);
		dao.resetPassword(customerAccount);
		
		CustomerRestPwdLog customerRestPwdLog = new CustomerRestPwdLog();
		customerRestPwdLog.setAccountId(customerAccount.getAccountId());
		customerRestPwdLog.setResetDt(new Date());
		customerRestPwdLog.setResetType(ProjectConstant.CUSTOMER_CHANGE_PASSWORD_BY_MOBILE_RESET);
		customerRestPwdLog.setStatus(ProjectConstant.CUSTOMER_RESET_PASSWORD_STATUS_SUCCESS);
		customerRestPwdLogDao.insert(customerRestPwdLog);
		
		return customerAccount;
	}
	
	/**
	 * 通过邮箱重置密码
	 * 		1.修改customerAccount表accountPwd
	 * 		2.添加customerRestPwdLog表新记录
	 * @param mobile
	 * @param newPassword
	 */
	@Transactional(readOnly = false)
	public void resetPasswordByEmail(String email, String newPassword) {

		CustomerBase customerBase = customerBaseDao.getByEmail(email);
		long accountId = customerBase.getAccountId();
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountId(accountId);
		customerAccount.setAccountPwd(SystemService.entryptPassword(newPassword));
		customerAccount.setResetPwdType(ProjectConstant.CUSTOMER_CHANGE_PASSWORD_BY_EMAIL_RESET);
		dao.resetPassword(customerAccount);
		
		CustomerRestPwdLog customerRestPwdLog = new CustomerRestPwdLog();
		customerRestPwdLog.setAccountId(accountId);
		customerRestPwdLog.setResetDt(new Date());
		customerRestPwdLog.setResetType(ProjectConstant.CUSTOMER_CHANGE_PASSWORD_BY_EMAIL_RESET);
		customerRestPwdLog.setStatus(ProjectConstant.CUSTOMER_RESET_PASSWORD_STATUS_SUCCESS);
		customerRestPwdLogDao.insert(customerRestPwdLog);
	}

	/**
	 * 检查密码输入是否正确
	 * @param accountId
	 * @param oldPassword
	 * @return
	 */
	public boolean checkPasswordCorrect(Long accountId, String password) {
		CustomerAccount customerAccount = dao.get(accountId + "");
		return SystemService.validatePassword(password, customerAccount.getAccountPwd());
	}
	
	/**
	 * 修改密码
	 * 		1.修改账户密码
	 * 		2.添加customerResetPwdLog表新纪录
	 * @param accountId
	 * @param password
	 */
	@Transactional(readOnly = false)
	public void changePassword(Long accountId, String password) {
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountId(accountId);
		customerAccount.setAccountPwd(SystemService.entryptPassword(password));
		customerAccount.setResetPwdType(ProjectConstant.CUSTOMER_CHANGE_PASSWORD_BY_NORMAL_CHANGE);
		dao.resetPassword(customerAccount);
		
		CustomerRestPwdLog customerRestPwdLog = new CustomerRestPwdLog();
		customerRestPwdLog.setAccountId(accountId);
		customerRestPwdLog.setResetDt(new Date());
		customerRestPwdLog.setResetType(ProjectConstant.CUSTOMER_CHANGE_PASSWORD_BY_NORMAL_CHANGE);
		customerRestPwdLog.setStatus(ProjectConstant.CUSTOMER_RESET_PASSWORD_STATUS_SUCCESS);
		customerRestPwdLogDao.insert(customerRestPwdLog);
	}
	
	/**
	 * 检查会员昵称是否可用
	 * @param nickname
	 * @return
	 */
	public boolean checkNicknameCanUse(String nickname){
		int count = dao.getByNicknameCount(nickname);
		if(count == 0){
			return true;
		}
		return false;
	}

	/**
	 * 设置昵称
	 * @param accountId
	 * @param nickname
	 * @return
	 */
	@Transactional(readOnly = false)
	public HashMap<String,Object> setNickName(Long accountId, String nickname){
		HashMap<String,Object> map = new HashMap<String,Object>();
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountId(accountId);
		customerAccount.setNickname(nickname);
		int i = dao.updateByAccountIdSelective(customerAccount);
		if(i > 0){
			map.put("success", true);
		}else{
			map.put("success", false);
			map.put("message", "昵称设置失败！");
		}
		return map;
	}
	
	/**
	 * 修改密码
	 * @param accountId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@Transactional(readOnly = false)
	public HashMap<String,Object> changePassword(Long accountId,String oldPassword, String newPassword){
		HashMap<String,Object> map = new HashMap<String,Object>();
		//校验原始密码是否正确
		if(checkPasswordCorrect(accountId, oldPassword)){
			CustomerAccount newCustomerAccount = new CustomerAccount();
			newCustomerAccount.setAccountId(accountId);
			newCustomerAccount.setAccountPwd(SystemService.entryptPassword(newPassword));
			int i = dao.updateByAccountIdSelective(newCustomerAccount);
			String status = ProjectConstant.CUSTOMER_RESET_PASSWORD_STATUS_SUCCESS;
			if(i > 0){
				map.put("success", true);
			}else{
				status = ProjectConstant.CUSTOMER_RESET_PASSWORD_STATUS_FAIL;
				map.put("success", false);
				map.put("message", "修改密码失败！");
			}
			//插入密码重置记录
			CustomerRestPwdLog customerRestPwdLog = new CustomerRestPwdLog();
			customerRestPwdLog.setAccountId(accountId);
			customerRestPwdLog.setResetDt(new Date());
			customerRestPwdLog.setResetType(ProjectConstant.CUSTOMER_CHANGE_PASSWORD_BY_NORMAL_CHANGE);
			customerRestPwdLog.setStatus(status);
			customerRestPwdLogDao.insert(customerRestPwdLog);
		}else{
			map.put("success", false);
			map.put("message", "原始密码不正确！");
		}
		return map;
	}
	
	/**
	 * 修改邮箱
	 * @param accountId
	 * @param newEmail
	 * @param emailCode
	 * @param password
	 * @return
	 */
	@Transactional(readOnly = false)
	public HashMap<String,Object> changeEmail(Long accountId,String newEmail, String emailCode, String password){
		HashMap<String,Object> map = new HashMap<String,Object>();
		//校验登录密码是否正确
		if(checkPasswordCorrect(accountId, password)){
			//校验邮箱验证码
			if(auth(newEmail,emailCode)){
				CustomerBase customerBase = new CustomerBase();
				customerBase.setAccountId(accountId);
				customerBase.setEmail(newEmail);
				int i = customerBaseDao.updateCustomerBaseByAccountIdSelective(customerBase);
				if(i > 0){
					map.put("success", true);
				}else{
					map.put("success", false);
					map.put("message", "修改邮箱失败！");
				}
			}else{
				map.put("success", false);
				map.put("message", "邮箱和验证码不匹配！");
			}
		}else{
			map.put("success", false);
			map.put("message", "登录密码不正确！");
		}
		return map;
	}
	
	/**
	 * 绑定邮箱
	 * @param accountId
	 * @param email
	 * @param emailCode
	 * @return
	 */
	@Transactional(readOnly = false)
	public HashMap<String,Object> activateEmail(Long accountId,String email, String emailCode){
		HashMap<String,Object> map = new HashMap<String,Object>();
		//校验邮箱验证码
		if(auth(email,emailCode)){
			CustomerBase customerBase = new CustomerBase();
			customerBase.setAccountId(accountId);
			customerBase.setEmail(email);
			customerBase.setEmailAuthCode(ProjectConstant.HAS_AUTHED);
			int i = customerBaseDao.updateCustomerBaseByAccountIdSelective(customerBase);
			if(i > 0){
				map.put("success", true);
			}else{
				map.put("success", false);
				map.put("message", "绑定邮箱失败！");
			}
		}else{
			map.put("success", false);
			map.put("message", "邮箱验证码不正确！");
		}
		
		return map;
	}
	
	
	/**
	 * 修改邮箱
	 * @param accountId
	 * @param email
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateEmail(Long accountId,String email){
		CustomerBase customerBase = new CustomerBase();
		customerBase.setAccountId(accountId);
		customerBase.setEmail(email);
		int i = customerBaseDao.updateCustomerBaseByAccountIdSelective(customerBase);
		return i;
	}
	
	/**
	 * 修改手机
	 * @param accountId
	 * @param mobile
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateMobile(Long accountId,String mobile){
		CustomerBase customerBase = new CustomerBase();
		customerBase.setAccountId(accountId);
		customerBase.setMobile(mobile);
		int i = customerBaseDao.updateCustomerBaseByAccountIdSelective(customerBase);
		return i;
	}

	/**
	 * 检测邮箱验证码是否正确
	 * @param email
	 * @param emailCode
	 * @return
	 */
	public boolean auth(String email, String emailCode) {
		LogSendValidateCode logSendValidateCode = logSendValidateCodeDao.getLastEmailValidateCodeRecordByEmail(email);
		if(StringUtils.isBlank(email) || logSendValidateCode == null) {
			return false;
		}
		return checkAuth(logSendValidateCode, emailCode);
	}
	/**
	 * 验证码正确并且未超时则返回true，否则返回false
	 * @param logSendValidateCode
	 * @param emailCode
	 * @return
	 */
	private static boolean checkAuth(LogSendValidateCode logSendValidateCode, String emailCode) {
		String validateCode = logSendValidateCode.getValidateCode();
		Date opDt = logSendValidateCode.getOpDt();
		return ((new Date().getTime() - opDt.getTime()) < EmailCodeController.EMAIL_VALIDATE_CODE_TIMEOUT * 1000) && emailCode.toLowerCase().equals(validateCode.toLowerCase());
	}
	
	/**
	 * 检测平台用户编号是否可用
	 * @param customerAccount
	 * @return
	 */
	public boolean checkPlatformUserNoCanUse(CustomerAccount customerAccount) {
		CustomerAccount ca = dao.getByPlatformUserNo(customerAccount.getPlatformUserNo());
		//如果未使用过或者平台用户编号属于这个用户则表示可用
		if(ca == null || ca.getAccountId() == customerAccount.getAccountId()) {
			return true;
		}
		return false;
	}

	/**
	 * 后台添加会员
	 * 		1.添加customerAccount记录
	 * 		2.添加customrBase记录
	 * 		3.生成默认customerBalance信息
	 * 		4.生成默认customerCreditAuth信息
	 * 		5.生成默认customerBankCard信息
	 * 		6.生成默认customerIntegralSnapshot信息
	 * @param accountName
	 * @param customerName
	 * @param mobile
	 * @param platformUserNo
	 * @param hasOpenThirdAccount
	 */
	@Transactional(readOnly = false)
	public void addAccount(String accountName, String customerName, String mobile, String email, String platformUserNo, String hasOpenThirdAccount) {
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setDefaultValue();
		if(StringUtils.isBlank(accountName)) {
			accountName = createCustomerAccount();
		}
		customerAccount.setAccountName(accountName);
		if(StringUtils.isNotBlank(platformUserNo)) {
			customerAccount.setPlatformUserNo(platformUserNo);
		}
		if(StringUtils.isNoneBlank(hasOpenThirdAccount)) {
			customerAccount.setHasOpenThirdAccount(hasOpenThirdAccount);
		}
		dao.insert(customerAccount);
		if(StringUtils.isBlank(platformUserNo)) {
			dao.setPlatformUserNo(customerAccount.getAccountId(), generatePlatformUserNo(customerAccount.getAccountId()));
		}
		
		CustomerBase customerBase = new CustomerBase();
		customerBase.setAccountId(customerAccount.getAccountId());
		if(StringUtils.isNotBlank(mobile)) {
			customerBase.setMobile(mobile);
		}
		if(StringUtils.isNotBlank(customerName)) {
			customerBase.setCustomerName(customerName);
		}
		if(StringUtils.isNotBlank(email)) {
			customerBase.setEmail(email);
		}
		customerBase.setCreateDt(new Date());
		customerBaseDao.insert(customerBase);
		
		CustomerBalance customerBalance = new CustomerBalance();
		customerBalance.setDefaultValue();
		customerBalance.setAccountId(customerAccount.getAccountId());
		customerBalanceDao.insert(customerBalance);
		
		CustomerCreditAuth customerCreditAuth = new CustomerCreditAuth();
		customerCreditAuth.setDefaultValue();
		customerCreditAuth.setCustomerId(customerBase.getCustomerId());
		double creditScore = 0d;
		customerCreditAuth.setCreditScore(creditScore);
		CreditLevelInfo creditLevelInfo = creditLevelInfoDao.getByScore(creditScore);
		customerCreditAuth.setCreditLimit(creditLevelInfo.getCreditLimit());
		customerCreditAuth.setCreditLevel(creditLevelInfo.getCreditLevel());
		customerCreditAuthDao.insert(customerCreditAuth);
		
		CustomerBankCard customerBankCard = new CustomerBankCard();
		customerBankCard.setAccountId(customerAccount.getAccountId());
		customerBankCardDao.insert(customerBankCard);
		
		CustomerIntegralSnapshot customerIntegralSnapshot = new CustomerIntegralSnapshot();
		customerIntegralSnapshot.setDefaultValue();
		customerIntegralSnapshot.setAccountId(customerAccount.getAccountId());
		customerIntegralSnapshotDao.insert(customerIntegralSnapshot);
	}

	/**
	 * 根据手机号获取用户账号信息
	 * @param mobile
	 * @return
	 */
	public CustomerAccount getByMobile(String mobile) {
		return dao.getByMobile(mobile);
	}

	/**
	 * 根据推荐人id获取被推荐人page
	 * @param accountId
	 * @param pageNo
	 * @return
	 */
	public Page<CustomerAccount> getByRecommendorAccountId(Long accountId, String pageNo) {
		Page<CustomerAccount> page = new Page<CustomerAccount>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		CustomerAccount customerAccount  = new CustomerAccount();
		customerAccount.setPage(page);
		customerAccount.setAccountId(accountId);
		page.setList(dao.getByRecommendorAccountId(customerAccount));
		return page;
	}
	
	/**
	 * 获取推荐人分页列表
	 * @param accountId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<CustomerAccount> getPageListByRecommendorAccountId(Long accountId, Integer pageNumber, Integer pageSize) {
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("accountId", accountId);
		return dao.getListByRecommendorAccountId(map);
	}
	
	/**
	 * 获取推荐人列表
	 * @param accountId
	 * @return
	 */
	public List<CustomerAccount> getListByRecommendorAccountId(Long accountId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		return dao.getListByRecommendorAccountId(map);
	}

	/**
	 * 已实名认证身份证号拥有账号的个数
	 * @param idCardNo
	 * @return
	 */
	public int getHasAuthedIdCardNoHasAccountNumber(String idCardNo) {
		return dao.getHasAuthedIdCardNoHasAccountNumber(idCardNo);
	}
	
	/**
	 * 判断身份证号码小于最大使用次数
	 * @param idCardNo
	 * @return
	 */
	public boolean isIdCardNoLessThanUseTimesLimit(String idCardNo) {
		int useTimes = getHasAuthedIdCardNoHasAccountNumber(idCardNo);
		return useTimes < ProjectConstant.IDCARDNO_MAX_HAVE_ACCOUNT_NUMBER;
	}

	public boolean canPatchOpenThirdAccountTicketToCustomer(String mobile) {
		return false;
	}

	public boolean canPatchOpenThirdAccountTicketToRecommender(String mobile) {
		return false;
	}

	public boolean canPatchOpenThirdAccountAmountToCustomer(String mobile) {
		return false;
	}

	public boolean canPatchOpenThirdAccountAmountToRecommender(String mobile) {
		return false;
	}

	public boolean canPatchFirstInvestmentTicketToCustomer(String mobile) {
		return false;
	}

	/**
	 * 获取accountId用户的推荐人accountId
	 * @param accountId
	 * @return
	 */
	public Long getRecommenderAccountIdByAccountId(Long accountId) {
		return dao.getRecommenderAccountIdByAccountId(accountId);
	}
	//按用户查询用户在易宝的用户平台标示
	public String selectPlatformUserNoByAccountName(String accountName) {
		return dao.selectPlatformUserNoByAccountName(accountName);
	}
    /**
     * 根据用户的类型查询用户名
     * @return
     */
	public List<String> selectListByaccountType() {
		return dao.selectListByaccountType(new CustomerAccount());
	}

	/**
	 * 获取推荐人总数量(API-我的邀请-好友分页列表)
	 * @param accountId
	 * @return
	 */
	public long countRecommendorByAccountId(Long accountId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		return dao.countRecommendorByAccountId(accountId);
	}
	
	public CustomerAccount findLoginName(String accountName ){
	   return dao.findLoginName(accountName);	
	}
	/**
	 * 
	 * <p>
	 * Description:登录名描述<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年6月17日
	 * @param accountName
	 * @return
	 * CustomerAccount
	 */
	public CustomerAccount getCustomerAccount(String accountName ){	
	   if(MobileUtil.isMobile(accountName)){
		   return dao.loginByMobile(accountName);
	   } 
	   if(MailUtil.isEmail(accountName)){
		   return dao.loginByEmail(accountName);
	   } 
	   return dao.findLoginName(accountName);
	}
	@Transactional(readOnly = false)
	public void updateLogin(CustomerAccount customerAccount){
		dao.updateLogin(customerAccount);
	}

}