/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.yeepay.toResetMobile.ToResetMobileReq;
import com.thinkgem.jeesite.common.yeepay.toResetPassword.ToResetPasswordReq;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.utils.Base64Utils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.IdcardUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.toBindBankCard.ToBindBankCardReq;
import com.thinkgem.jeesite.common.yeepay.toRecharge.ToRechargeReq;
import com.thinkgem.jeesite.common.yeepay.toRegister.ToRegisterReq;
import com.thinkgem.jeesite.common.yeepay.toUnbindBankCard.ToUnbindBankCardResp;
import com.thinkgem.jeesite.common.yeepay.toWithdraw.ToWithdrawReq;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.po.ToInvestReq;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.current.service.investment.CurrentInvestmentServiceImpl;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceHisService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.entity.BankInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBankCard;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.project.service.assignment.AssignmentService;
import com.thinkgem.jeesite.modules.project.service.investment.InvestmentService;
import com.thinkgem.jeesite.modules.sys.service.BankInfoService;
import com.thinkgem.jeesite.modules.sys.utils.ApiCacheUtils;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;

/**
 * 易宝Controller
 * 
 * @author lzb
 * @version 2015-10-16
 */
@Controller
@RequestMapping("${frontPath}/api/yeepay")
public class YeepayController extends APIBaseController {

	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private CustomerBalanceHisService customerBalanceHisService;
	@Autowired
	private ProjectExecuteSnapshotService projectExecuteSnapshotService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CurrentProjectExecuteSnapshotService currentProjectExecuteSnapshotService;
	@Autowired
	private CurrentInvestmentServiceImpl currentInvestmentServiceImpl;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;


	/**
	 * 重置交易密码
	 * @param response
	 * @param client
	 * @param token
	 * @param callbackUrl
     * @return
     */
	@RequestMapping(value = "resetTransPwd", method = RequestMethod.POST)
	public String resetTransPwd(HttpServletResponse response,String client, String token, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api resetTransPwd start...");
		//解析client
		ClientProperty cProperty = ApiUtil.getClient(client);
		String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
		if(clientToken != null ){
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			String errorMsg="";
			if(ProjectConstant.UNOPENED.equals(customerAccount.getHasOpenThirdAccount())){
				errorMsg = "您还未开通托管账号，不能重置交易密码";
			}

			String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_REQ, customerAccount.getPlatformUserNo());
			ToResetPasswordReq toResetPasswordReq = new ToResetPasswordReq();
			toResetPasswordReq.setRequestNo(requestNo);
			toResetPasswordReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
			toResetPasswordReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
			toResetPasswordReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toResetTransPwd?requestNo=" + requestNo);

			String req = toResetPasswordReq.toReq();
			String sign = SignUtil.sign(req);
			//添加缓存
			HashMap<String, Object> mapCache = new HashMap<String, Object>();
			mapCache.put("requestNo", requestNo);
			mapCache.put("accountId", customerAccount.getAccountId());
			mapCache.put("mobileType", StringUtil.dealString(cProperty.getType()).toUpperCase());
			logger.info("-------------resetTransPwd mobileType：" + StringUtil.dealString(cProperty.getType()).toUpperCase());
			logger.info("-------------resetTransPwd callbackUrl：" + Base64Utils.getDecodeBASE64(callbackUrl));
			mapCache.put("callbackUrl", Base64Utils.getDecodeBASE64(callbackUrl));
			this.addCache(mapCache, requestNo, req, sign, ApiConstant.API_YEEPAY_BUSINESS_RES_TORESETPASSWORD, errorMsg);
			String reqUrl = ApiConstant.YEEPAY_REQ_URL_PREFIX+ "toResetTransPwdDo?uuid=" + requestNo;
			if(StringUtils.isNotBlank(errorMsg)){
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, errorMsg, false);
			}else{
				ApiUtil.mapRespData(map, reqUrl, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api resetTransPwd end...");
		logger.info("api resetTransPwd total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}


	/**
	 * 修改手机号码
	 * @param response
	 * @param client
	 * @param token
	 * @param callbackUrl
     * @return
     */
	@RequestMapping(value = "toResetMobile", method = RequestMethod.POST)
	public String toResetMobile(HttpServletResponse response,String client, String token, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toResetMobile start...");
		//解析client
		ClientProperty cProperty = ApiUtil.getClient(client);
		String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
		if(clientToken != null ){
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			String errorMsg="";
			if(ProjectConstant.UNOPENED.equals(customerAccount.getHasOpenThirdAccount())){
				errorMsg = "您还未开通托管账号，不能修改手机号";
			}

			String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_REQ, customerAccount.getPlatformUserNo());
			ToResetMobileReq toResetMobileReq = new ToResetMobileReq();
			toResetMobileReq.setRequestNo(requestNo);
			toResetMobileReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
			toResetMobileReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
			toResetMobileReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toResetMobile?requestNo=" + requestNo);
			toResetMobileReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toResetMobile?requestNo=" + requestNo);

			String req = toResetMobileReq.toReq();
			String sign = SignUtil.sign(req);
			//添加缓存
			HashMap<String, Object> mapCache = new HashMap<String, Object>();
			mapCache.put("requestNo", requestNo);
			mapCache.put("accountId", customerAccount.getAccountId());
			mapCache.put("mobileType", StringUtil.dealString(cProperty.getType()).toUpperCase());
			logger.info("-------------toResetMobile mobileType：" + StringUtil.dealString(cProperty.getType()).toUpperCase());
			logger.info("-------------toResetMobile callbackUrl：" + Base64Utils.getDecodeBASE64(callbackUrl));
			mapCache.put("callbackUrl", Base64Utils.getDecodeBASE64(callbackUrl));
			this.addCache(mapCache, requestNo, req, sign, ApiConstant.API_YEEPAY_BUSINESS_RES_TORESETMOBILE, errorMsg);
			String reqUrl = ApiConstant.YEEPAY_REQ_URL_PREFIX+ "toResetMobileDo?uuid=" + requestNo;
			if(StringUtils.isNotBlank(errorMsg)){
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, errorMsg, false);
			}else{
				ApiUtil.mapRespData(map, reqUrl, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toResetMobile end...");
		logger.info("api toResetMobile total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}


    /**
     * 绑卡
     * @param response
     * @param client
     * @param token
     * @return
     */
	@RequestMapping(value = "toBindBankCard", method = RequestMethod.POST)
	public String toBindBankCard(HttpServletResponse response,String client, String token, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toBindBankCard start...");
		ClientProperty cProperty = ApiUtil.getClient(client);
		String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ){
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(customerAccount.getAccountId());
			AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
			String errorMsg="";
			if(StringUtils.isNotBlank(accountInfoResp.getCardNo())){
				errorMsg = "已绑卡";
			}else{
				int i = customerBankCardService.hasAppointment(customerBankCard.getUnbindRequestNo());
				if(i == 2){
					errorMsg = "预约解绑中";
				}
			}

			String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_REQ, customerAccount.getPlatformUserNo());
			ToBindBankCardReq toBindBankCardReq = new ToBindBankCardReq();
			toBindBankCardReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
			toBindBankCardReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
			toBindBankCardReq.setRequestNo(requestNo);
			toBindBankCardReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toBindBankCard?requestNo=" + requestNo);
			toBindBankCardReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toBindBankCard");
			String req = toBindBankCardReq.toReq();
			String sign = SignUtil.sign(req);
			//添加缓存
			HashMap<String, Object> mapCache = new HashMap<String, Object>();
			mapCache.put("requestNo", requestNo);
			mapCache.put("accountId", customerAccount.getAccountId());
			mapCache.put("mobileType", StringUtil.dealString(cProperty.getType()).toUpperCase());
			logger.info("-------------toBindBankCard mobileType：" + StringUtil.dealString(cProperty.getType()).toUpperCase());
			logger.info("-------------toBindBankCard callbackUrl：" + Base64Utils.getDecodeBASE64(callbackUrl));
			mapCache.put("callbackUrl", Base64Utils.getDecodeBASE64(callbackUrl));
			this.addCache(mapCache, requestNo, req, sign, ApiConstant.API_YEEPAY_BUSINESS_RES_TOBINDBANKCARD, errorMsg);
			String reqUrl = ApiConstant.YEEPAY_REQ_URL_PREFIX+ "toBindBankCardDo?uuid=" + requestNo;
			if(StringUtils.isNotBlank(errorMsg)){
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, errorMsg, false);
			}else{
				ApiUtil.mapRespData(map, reqUrl, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toBindBankCard end...");
		logger.info("api toBindBankCard total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	/**
	 * 解绑银行卡
	 * @param response
	 * @param client
	 * @param token
	 * @param callbackUrl
	 * @return
	 */
	@RequestMapping(value = "toUnBindBankCard", method = RequestMethod.POST)
	public String toUnBindBankCard(HttpServletResponse response,String client, String token, String idCardNo, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toUnBindBankCard start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("the toUnBindBankCard idCardNo:"+idCardNo);
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ){
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
			CustomerBankCard card = customerBankCardService.getByAccountId(customerAccount.getAccountId());
			int i = customerBankCardService.hasAppointment(card.getUnbindRequestNo());
			String errorMsg="";
			if(!customerBankCardService.hasBindBankCard(accountInfoResp)){
				errorMsg = "未绑定银行卡";
			}else if(i == 2){
				errorMsg = "预约解绑中";
			}else if(ProjectConstant.OP_TERM_DICT_PC.equals(opTerm)){
				errorMsg = this.validateSixCardNo(customerAccount,idCardNo,errorMsg);
			}
			logger.info("the accountId: "+customerAccount.getAccountId()+" ,custoUnBindBankCard errorMsg:"+errorMsg);
			if(StringUtils.isNotBlank(errorMsg)){
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, errorMsg, false);
			}else{
				ToUnbindBankCardResp resp = customerBankCardService.toUnbindBankCard(customerAccount.getPlatformUserNo(),customerAccount.getAccountId());
				if(resp !=null && "1".equals(resp.getCode())){
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}else{
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
				}
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}

		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toUnBindBankCard end...");
		logger.info("api toUnBindBankCard total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 验证身份证号后6位
	 * @param customerAccount
	 * @param idCardNo
     * @return
     */
	public String validateSixCardNo(CustomerAccount  customerAccount, String idCardNo, String errorMsg){
		String certNum = StringUtil.dealString(customerAccount.getCustomerBase().getCertNum());
		//身份证号后6位
		String lastSixCardNo = certNum.length() > 6 ? certNum.substring(certNum.length()- 6) : certNum;
		if(StringUtils.isBlank(idCardNo)){
			errorMsg = "请输入身份证号后6位";
		}else if(!lastSixCardNo.equals(idCardNo)){
			errorMsg = "请输入正确的身份证号后6位";
		}
		return errorMsg;
	}
	
	/**
	 * 添加缓存
	 * @param map
	 * @param req
	 * @param sign
	 * @param type
	 */
	public void addCache(HashMap<String, Object> map, String uuid, String req, String sign, String type, String errorMsg){
		ApiCacheObject apiCacheObject = new ApiCacheObject();
		apiCacheObject.setUuid(uuid);
		apiCacheObject.setReq(req);
		apiCacheObject.setSign(sign);
		apiCacheObject.setType(type);
		apiCacheObject.setLastDt(new Date());
		apiCacheObject.setMap(map);
		apiCacheObject.setErrorMsg(errorMsg);
		ApiCacheUtils.addCache(apiCacheObject);
	}
	
	/**
	 * 充值
	 * @param response
	 * @param client
	 * @param token
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "toRecharge", method = RequestMethod.POST)
	public String toRecharge(HttpServletResponse response,String client, String token, String amount, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toRecharge start...");
		ClientProperty cProperty = ApiUtil.getClient(client);
		String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
		if(clientToken != null ){
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			if(customerAccount != null){

				String errorMsg="";
				if(StringUtils.isNotBlank(amount)){
					if(Double.parseDouble(amount) <= 0) {
						errorMsg="充值金额不能小于等于0";
					}else if(amount.length() > 13){
						errorMsg="充值金额过大";
					}
				}
				CustomerBankCard card = customerBankCardService.getByAccountId(clientToken.getCustomerId());
				BankInfo bankInfo = bankInfoService.getBankInfoByBankCode(card.getBankCode());
				if(bankInfo != null && ProjectConstant.BANK_INFO_STATUS_INVALID.equals(bankInfo.getStatus())){
					errorMsg = "懒猫金服（易宝支付）暂停与"+bankInfo.getBankName()+"的合作，请更换其他银行卡进行操作。";
				}
				//组装报文数据
				String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORECHARGE_REQ, customerAccount.getPlatformUserNo());
				ToRechargeReq toRechargeReq = new ToRechargeReq();
				toRechargeReq.setRequestNo(requestNo);
				toRechargeReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
				//充值手续费由平台出
				toRechargeReq.setFeeMode(YeepayConstant.YEEPAY_FEE_MODE_PLATFORM);
				toRechargeReq.setAmount(amount);
				toRechargeReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
				toRechargeReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toRecharge?requestNo=" + requestNo);
				toRechargeReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toRecharge");
				String req = toRechargeReq.toReq();
				String sign = SignUtil.sign(req);
				//添加缓存
				
				HashMap<String, Object> mapCache = new HashMap<String, Object>();
				mapCache.put("requestNo", requestNo);
				mapCache.put("amount", amount);
				mapCache.put("mobileType", StringUtil.dealString(cProperty.getType()).toUpperCase());
				logger.info("-------------toRecharge mobileType：" + StringUtil.dealString(cProperty.getType()).toUpperCase());
				logger.info("-------------toRecharge callbackUrl：" + Base64Utils.getDecodeBASE64(callbackUrl));
				mapCache.put("callbackUrl", Base64Utils.getDecodeBASE64(callbackUrl));
				this.addCache(mapCache, requestNo, req, sign, ApiConstant.API_YEEPAY_BUSINESS_RES_TORECHARGE, errorMsg);
				String reqUrl = ApiConstant.YEEPAY_REQ_URL_PREFIX+ "toRechargeDo?uuid=" + requestNo;
				if(StringUtils.isNotBlank(errorMsg)){
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, errorMsg, false);
				}else{
					ApiUtil.mapRespData(map, reqUrl, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "账号不存在", false);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toRecharge end...");
		logger.info("api toRecharge total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	
	
	/**
	 * 注册
	 * @param response
	 * @param toRegisterReq
	 * @param client
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "toRegister", method = RequestMethod.POST)
	public String toRegister(HttpServletResponse response, ToRegisterReq toRegisterReq, String client, String token, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toRegister start...");
		ClientProperty cProperty = ApiUtil.getClient(client);
		String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ){
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			if(customerAccount != null){

				String errorMsg="";
				AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
				//验证是否是18位身份证
				if(!IdcardUtils.validateIdCard18(toRegisterReq.getIdCardNo())) {
					errorMsg = "身份证不合法";
				}else if(!customerAccountService.isIdCardNoLessThanUseTimesLimit(toRegisterReq.getIdCardNo())) {
					errorMsg = "身份证号码超过最大使用次数";
				}else if(ProjectConstant.HASOPENED.equals(accountInfoResp.getCode())){
					//检查本地数据是否已同步
					if(ProjectConstant.UNOPENED.equals(customerAccount.getHasOpenThirdAccount())){
						customerAccountService.updateHasOpenThirdAccount(customerAccount.getAccountId(),ProjectConstant.HASOPENED);
					}
					errorMsg = "已开通托管账号";
				}
				//组装报文数据
				String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOREGISTER_REQ, customerAccount.getPlatformUserNo());
				toRegisterReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
				toRegisterReq.setIdCardType(ApiConstant.ID_CARD_TYPE);
				toRegisterReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
				toRegisterReq.setRequestNo(requestNo);
				toRegisterReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toRegister?requestNo=" + requestNo);
				toRegisterReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toRegister");
				String req = toRegisterReq.toReq();
				String sign = SignUtil.sign(req);
				//添加缓存
				HashMap<String, Object> mapCache = new HashMap<String, Object>();
				mapCache.put("requestNo", requestNo);
				mapCache.put("accountId", customerAccount.getAccountId());
				mapCache.put("realName", toRegisterReq.getRealName());
				mapCache.put("idCardNo", toRegisterReq.getIdCardNo());
				mapCache.put("mobile", toRegisterReq.getMobile());
				mapCache.put("mobileType", StringUtil.dealString(cProperty.getType()).toUpperCase());
				logger.info("-------------toRegister mobileType：" + StringUtil.dealString(cProperty.getType()).toUpperCase());
				logger.info("-------------toRegister callbackUrl：" + Base64Utils.getDecodeBASE64(callbackUrl));
				mapCache.put("callbackUrl", Base64Utils.getDecodeBASE64(callbackUrl));
				this.addCache(mapCache, requestNo, req, sign, ApiConstant.API_YEEPAY_BUSINESS_RES_TOREGISTER, errorMsg);
				String reqUrl = ApiConstant.YEEPAY_REQ_URL_PREFIX+ "toRegisterDo?uuid=" + requestNo;
				if(StringUtils.isNotBlank(errorMsg)){
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, errorMsg, false);
				}else{
					ApiUtil.mapRespData(map, reqUrl, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "账号不存在", false);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toRegister end...");
		logger.info("api toRegister total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 提现
	 * @param response
	 * @param client
	 * @param token
	 * @param amount
	 * @param useTicket
	 * @return
	 */
	@RequestMapping(value = "toWithdraw", method = RequestMethod.POST)
	public String toWithdraw(HttpServletResponse response, String client, String token, String amount, String useTicket, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toWithdraw start...");
		ClientProperty cProperty = ApiUtil.getClient(client);
		String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ){
			CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
			if(customerAccount != null){
				CustomerBalance customerBalance = customerBalanceService.get(customerAccount.getAccountId() + "");
				int todayWithdrawCount = customerBalanceHisService.getCustomerTodayWithdrawCount(customerAccount.getAccountId());
				String errorMsg="";

				//对数据进行验证
				if(Double.parseDouble(amount) > (AccountController.availableBalance(customerBalance))) {
					errorMsg = "可提现金额不足";
				}else if("true".equals(useTicket) && customerBalance.getFreeWithdrawCount() <= 0) {
					errorMsg = "提现券已用完";
				}
				//如果超过当天提现次数限制，则跳转到超过当天提现次数页面
				else if(todayWithdrawCount >= ProjectConstant.ONEDAY_MAX_WITHDRAW_COUNT) {
					errorMsg = "超过当天最多提现" + ProjectConstant.ONEDAY_MAX_WITHDRAW_COUNT + "次限制";
				}
				//组装报文数据
				String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_REQ, customerAccount.getPlatformUserNo());
				ToWithdrawReq toWithdrawReq = new ToWithdrawReq();
				toWithdrawReq.setRequestNo(requestNo);
				toWithdrawReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
				toWithdrawReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
				toWithdrawReq.setAmount(amount);
				if("true".equals(useTicket)) {
					toWithdrawReq.setFeeMode(YeepayConstant.YEEPAY_FEE_MODE_PLATFORM);
				} else {
					toWithdrawReq.setFeeMode(YeepayConstant.YEEPAY_FEE_MODE_USER);
				}
				toWithdrawReq.setWithdrawType(YeepayConstant.YEEPAY_WITHDRAW_TYPE_NORMAL);
				toWithdrawReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "toWithdraw?requestNo=" + requestNo);
				toWithdrawReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toWithdraw");
				String req = toWithdrawReq.toReq();
				String sign = SignUtil.sign(req);
				//添加缓存
				HashMap<String, Object> mapCache = new HashMap<String, Object>();
				mapCache.put("requestNo", requestNo);
				mapCache.put("accountId", customerAccount.getAccountId());
				mapCache.put("mobileType", StringUtil.dealString(cProperty.getType()).toUpperCase());
				logger.info("-------------toWithdraw mobileType：" + StringUtil.dealString(cProperty.getType()).toUpperCase());
				logger.info("-------------toWithdraw callbackUrl：" + Base64Utils.getDecodeBASE64(callbackUrl));
				mapCache.put("callbackUrl", Base64Utils.getDecodeBASE64(callbackUrl));
				this.addCache(mapCache, requestNo, req, sign, ApiConstant.API_YEEPAY_BUSINESS_RES_TOWITHDRAW, errorMsg);
				String reqUrl = ApiConstant.YEEPAY_REQ_URL_PREFIX+ "toWithdrawDo?uuid=" + requestNo;
				if(StringUtils.isNotBlank(errorMsg)){
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, errorMsg, false);
				}else{
					ApiUtil.mapRespData(map, reqUrl, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "账号不存在", false);
			}
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toWithdraw end...");
		logger.info("api toWithdraw total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	/**
	 * 定期投资
	 * @param response
	 * @param toInvestReq
	 * @param client
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "toInvest", method = RequestMethod.POST)
	public String toInvest(HttpServletResponse response, ToInvestReq toInvestReq, String client, String token, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toInvest start...");
		ClientProperty cProperty = ApiUtil.getClient(client);
		String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
		HashMap<String, Object> map = new HashMap<String, Object>();
		//入参数据校验
		List<String> messages  = ApiUtil.validateBean(toInvestReq);
		if(messages != null){
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
		}else{
			CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
			if(clientToken != null ){

				String projectId = toInvestReq.getProjectId();
				Long accountId = clientToken.getCustomerId();
				CustomerAccount  customerAccount = customerAccountService.get(clientToken.getCustomerId());
				ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(projectId);
				ProjectExecuteSnapshot pesInvest = projectExecuteSnapshotService.getByProjectId(projectId);
				projectBaseInfo.setPes(pesInvest);
				//投资金额
				Double amount = NumberUtils.toDouble(toInvestReq.getAmount(), 0.00);
				//优惠券Ids
				String ticketIds = toInvestReq.getTicketIds();
				//加息券ids
				String rateTicketIds =toInvestReq.getRateTicketIds();
				//优惠券金额
				Double ticketAmount = NumberUtil.toDouble(toInvestReq.getTicketAmount(), 0.00);
				//平台垫付金额
				Double platformAmount = NumberUtil.toDouble(toInvestReq.getPlatformAmount(), 0.00);
				//实际支付金额
				Double actualAmount = LoanUtil.formatAmount(amount - ticketAmount - platformAmount);

				String type = StringUtil.dealString(toInvestReq.getType());
				String projectName = StringUtils.replaceSpecialStr(projectBaseInfo.getProjectName());
				String projectCode = StringUtils.replaceSpecialStr(projectBaseInfo.getProjectCode());
				if(projectCode != null && projectCode.length() > 100 ){
					projectCode = projectCode.substring(1,100);
				}
				String platformUserNo = customerAccount.getPlatformUserNo();
				//融资代理人
				Long agentId = projectBaseInfo.getAgentUser();
				//融资人
				Long loanAccountId = projectBaseInfo.getBorrowersUser();
				if ((agentId != null) && (!agentId.equals(""))
						&& (agentId.longValue()!=0)) {
					loanAccountId = agentId;
				}
				String borrowerPlatformUserNo = customerAccountService.get(loanAccountId).getPlatformUserNo();
				//定期投资操作动作
				String operationAction = "toInvestDo";
				if (ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT.equals(type)) {
					//直投
					//给平台服务费
					Double serviceCharge = ProjectUtil.getServiceCharge(actualAmount, projectBaseInfo.getServiceCharge(), projectBaseInfo.getServiceChargeTypeCode(), projectBaseInfo.getPes().getSumServiceCharge());
					//给融资方的金额
					Double financingAmount = actualAmount > serviceCharge ? actualAmount - serviceCharge : 0;
					financingAmount = LoanUtil.formatAmount(financingAmount);
					//投资交易第三方流水编号
					String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ,customerAccount.getPlatformUserNo());
					//生成调用易宝投标接口数据
					String req = YeepayUtils.generationXml_for_tender(projectName, projectId, ProjectConstant.OP_TERM_DICT_WEIXIN, projectCode, requestNo, platformUserNo, borrowerPlatformUserNo, projectBaseInfo.getFinanceMoney(), serviceCharge, financingAmount);
					String sign = SignUtil.sign(req);
					//添加缓存
					String errorMsg="";
					try {
						//参数检查
						investmentService.beforeCheck(projectBaseInfo, opTerm, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds);
					} catch (Exception e) {
						errorMsg = e.getMessage();
					}
					this.toInvestAddCache(errorMsg, operationAction, callbackUrl, customerAccount.getAccountId(), cProperty, toInvestReq, requestNo, req, sign, map);
					
				}else if(ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT.equals(type)){
					//债权转让
					//投资交易第三方流水编号
					String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_CREDIT_ASSIGNMENT_REQ,customerAccount.getPlatformUserNo());
					//转让项目流水号
					String transferProjectId = StringUtil.dealString(toInvestReq.getTransferProjectId());
					ProjectTransferInfo projectTransferInfo = projectTransferInfoService.get(transferProjectId);
					//投资记录
					Long investmentRecordId = projectTransferInfo.getInvestmentRecordId();
					ProjectInvestmentRecord pir = projectInvestmentRecordService.get(String.valueOf(investmentRecordId));
					projectTransferInfo.setPir(pir);
					//执行快照
					ProjectExecuteSnapshot pesAssign = projectExecuteSnapshotService.getByProjectIdAndTransferId(projectId, transferProjectId);
					projectTransferInfo.setProjectExecuteSnapshot(pesAssign);
					//原始项目信息
					projectTransferInfo.setProjectBaseInfo(projectBaseInfo);
					//给平台服务费
					Double serviceCharge = ProjectUtil.getServiceCharge(actualAmount, projectBaseInfo.getServiceCharge(), projectBaseInfo.getServiceChargeTypeCode(), pesAssign.getSumServiceCharge());
					//下家(受让方)服务费
					Double downServiceCharge = ProjectUtil.getDownServiceCharge(amount);
					//总服务费
					Double sumServiceCharge = LoanUtil.formatAmount(serviceCharge + downServiceCharge);
					//给融资方的金额
					Double financingAmount = actualAmount > serviceCharge ? actualAmount - serviceCharge : 0;
					financingAmount = LoanUtil.formatAmount(financingAmount);
					//原投资记录里的投资人
					//融资人
					Long oldInvestmentUserId = pir.getInvestmentUserId();
					String oldInvestmentUserAccountId = customerAccountService.get(oldInvestmentUserId).getPlatformUserNo();
					//生成调用易宝投标接口数据
					String req = YeepayUtils.generationXml_for_assignment(projectId, ProjectConstant.OP_TERM_DICT_WEIXIN, requestNo, platformUserNo, oldInvestmentUserAccountId, borrowerPlatformUserNo, sumServiceCharge, financingAmount, pir.getThirdPartyOrder());
					//签名
					String sign = SignUtil.sign(req);
					//添加缓存
					String errorMsg="";
					try {
						//参数检查
						assignmentService.beforeAssignCheck(projectTransferInfo, opTerm, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds);
					} catch (Exception e) {
						errorMsg = e.getMessage();
					}
					this.toInvestAddCache(errorMsg, operationAction, callbackUrl, customerAccount.getAccountId(), cProperty, toInvestReq, requestNo, req, sign, map);
				}else{
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
				}
			}else{
				ApiUtil.tokenInvalidRespMap(map);
			}
		}
		
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toInvest end...");
		logger.info("api toInvest total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	/**
	 * 活期投资
	 * @param response
	 * @param toInvestReq
	 * @param client
	 * @param token
	 * @param callbackUrl
	 * @return
	 */
	@RequestMapping(value = "toCurrentInvest", method = RequestMethod.POST)
	public String toCurrentInvest(HttpServletResponse response, ToInvestReq toInvestReq, String client, String token, String callbackUrl) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toCurrentInvest start...");
		ClientProperty cProperty = ApiUtil.getClient(client);
		String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
		HashMap<String, Object> map = new HashMap<String, Object>();
		//入参数据校验
		List<String> messages  = ApiUtil.validateBean(toInvestReq);
		if(messages != null){
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
		}else{
			CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
			if(clientToken != null ){

				String projectId = toInvestReq.getProjectId();
				Long accountId = clientToken.getCustomerId();
				CustomerAccount  customerAccount = customerAccountService.get(accountId);
				CurrentProjectInfo cInfo = currentProjectInfoService.get(projectId);
				//执行快照信息
				CurrentProjectExecuteSnapshot executeSnapshot = currentProjectExecuteSnapshotService.getByProjectId(NumberUtil.toLong(projectId, 0L));
				cInfo.setSnapshot(executeSnapshot);
				//投资金额
				Double amount = NumberUtils.toDouble(toInvestReq.getAmount(), 0.00);

				String projectName = StringUtils.replaceSpecialStr(cInfo.getName());
				String projectCode = StringUtils.replaceSpecialStr(cInfo.getCode());
				if(projectCode != null && projectCode.length() > 100 ){
					projectCode = projectCode.substring(1,100);
				}
				String platformUserNo = customerAccount.getPlatformUserNo();
				//融资人
				Long loanAccountId = cInfo.getBorrowerAccountId();
				String borrowerPlatformUserNo = customerAccountService.get(loanAccountId).getPlatformUserNo();
				//活期投资操作动作
				String operationAction = "toCurrentInvestDo";
				//给平台服务费
				Double serviceCharge = 0.0;
				//给融资方的金额
				Double financingAmount = amount > serviceCharge ? amount - serviceCharge : 0;
				financingAmount = LoanUtil.formatAmount(financingAmount);
				//投资交易第三方流水编号
				String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ,platformUserNo);
				
				//生成调用易宝投标接口数据
				String req = YeepayUtils.generationCurrentXml_for_tender(projectName, projectId, ProjectConstant.OP_TERM_DICT_WEIXIN, projectCode, requestNo, platformUserNo, borrowerPlatformUserNo, cInfo.getFinanceMoney(), serviceCharge, financingAmount);
				//签名
				String sign = SignUtil.sign(req);
				//添加缓存
				String errorMsg="";
				try {
					//参数检查
					currentInvestmentServiceImpl.checkCurrentInvest(cInfo, accountId, financingAmount, true);;
				} catch (Exception e) {
					errorMsg = e.getMessage();
				}
				this.toInvestAddCache(errorMsg, operationAction, callbackUrl, customerAccount.getAccountId(), cProperty, toInvestReq, requestNo, req, sign, map);
				
			}else{
				ApiUtil.tokenInvalidRespMap(map);
			}
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toCurrentInvest end...");
		logger.info("api toCurrentInvest total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 投资添加缓存
	 * @param errorMsg
	 * @param customerAccount
	 * @param toInvestReq
	 * @param requestNo
	 * @param req
	 * @param sign
	 * @param map
	 */
	public void toInvestAddCache(String errorMsg, String operationAction, String callbackUrl, Long accountId,ClientProperty cProperty, ToInvestReq toInvestReq, 
			String requestNo, String req, String sign, HashMap<String, Object> map){
		HashMap<String, Object> mapCache = new HashMap<String, Object>();
		mapCache.put("accountId", accountId);
		mapCache.put("toInvestReq", toInvestReq);
		mapCache.put("cProperty", cProperty);
		mapCache.put("requestNo", requestNo);
		mapCache.put("mobileType", StringUtil.dealString(cProperty.getType()).toUpperCase());
		logger.info("-------------toInvest mobileType：" + StringUtil.dealString(cProperty.getType()).toUpperCase());
		logger.info("-------------toInvest callbackUrl：" + Base64Utils.getDecodeBASE64(callbackUrl));
		mapCache.put("callbackUrl", Base64Utils.getDecodeBASE64(callbackUrl));
		this.addCache(mapCache, requestNo, req, sign, ApiConstant.API_YEEPAY_BUSINESS_RES_TOINVEST, errorMsg);
		String reqUrl = ApiConstant.YEEPAY_REQ_URL_PREFIX + operationAction + "?uuid=" + requestNo;
		if(StringUtils.isNotBlank(errorMsg)){
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, errorMsg, false);
		}else{
			ApiUtil.mapRespData(map, reqUrl, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}
		
	}
}
