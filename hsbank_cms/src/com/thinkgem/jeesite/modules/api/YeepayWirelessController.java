/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.toRegister.ToRegisterReq;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.Record;
import com.thinkgem.jeesite.common.yeepay.unbindRecord.UnbindRecordResp;
import com.thinkgem.jeesite.modules.api.po.ToInvestReq;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.current.service.investment.CurrentInvestmentServiceImpl;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.project.service.assignment.AssignmentService;
import com.thinkgem.jeesite.modules.project.service.investment.InvestmentService;
import com.thinkgem.jeesite.modules.sys.utils.ApiCacheUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * 登录Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping("${frontPath}/api/yeepay/wireless")
public class YeepayWirelessController extends BaseController {


	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectExecuteSnapshotService projectExecuteSnapshotService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CurrentProjectExecuteSnapshotService currentProjectExecuteSnapshotService;
	@Autowired
	private CurrentInvestmentServiceImpl currentInvestmentServiceImpl;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;




	/**
	 * 重置交易密码跳转
	 * @param request
	 * @param model
	 * @param uuid
     * @return
     */
	@RequestMapping(value = "toResetTransPwdDo")
	public String toResetTransPwdDo(HttpServletRequest request, Model model, String uuid) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toResetTransPwdDo start...");
		ApiCacheObject aObject = ApiCacheUtils.getByUUID(uuid);
		if(aObject != null){
			String req = aObject.getReq();
			String sign = aObject.getSign();
			HashMap<String, Object> mapCache = aObject.getMap();
			String requestNo = String.valueOf(mapCache.get("requestNo"));
			String accountId = String.valueOf(mapCache.get("accountId"));

			CustomerBase customerBase = customerBaseService.getByAccountId(NumberUtils.toLong(accountId,0L));
			customerBase.setCustomerName(StringUtils.vagueName(customerBase.getCustomerName()));
			model.addAttribute("customerBase", customerBase);

			model.addAttribute("req", req);
			model.addAttribute("sign", sign);
			String resetTransPwdUrl = ApiUtil.getYeepayUrlPrefix(ApiConstant.OP_TERM_DICT_WEB) + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETPASSWORD_ACTION;
			logger.info("-------------toResetTransPwdDo resetTransPwdUrl：" + resetTransPwdUrl);
			model.addAttribute("resetTransPwdUrl", resetTransPwdUrl);
			if(checkRequestNo(requestNo)){
				logThirdPartyService.insertToRegisterReq(requestNo, req);
			}
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toResetTransPwdDo end...");
		logger.info("api toResetTransPwdDo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return "mobile/biz/resetTransPwdApiConfirm";
	}


	/**
	 * 修改手机号跳转
	 * @param request
	 * @param model
	 * @param uuid
     * @return
     */
	@RequestMapping(value = "toResetMobileDo")
	public String toResetMobileDo(HttpServletRequest request, Model model, String uuid) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toResetMobileDo start...");
		ApiCacheObject aObject = ApiCacheUtils.getByUUID(uuid);
		if(aObject != null){
			String req = aObject.getReq();
			String sign = aObject.getSign();
			HashMap<String, Object> mapCache = aObject.getMap();
			String requestNo = String.valueOf(mapCache.get("requestNo"));
			String accountId = String.valueOf(mapCache.get("accountId"));

			CustomerBase customerBase = customerBaseService.getByAccountId(NumberUtils.toLong(accountId,0L));
			customerBase.setCustomerName(StringUtils.vagueName(customerBase.getCustomerName()));
			model.addAttribute("customerBase", customerBase);

			model.addAttribute("req", req);
			model.addAttribute("sign", sign);
			String resetMobileUrl = ApiUtil.getYeepayUrlPrefix(ApiConstant.OP_TERM_DICT_WEB) + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORESETMOBILE_ACTION;
			logger.info("-------------toResetMobileDo resetTransPwdUrl：" + resetMobileUrl);
			model.addAttribute("resetMobileUrl", resetMobileUrl);
			if(checkRequestNo(requestNo)){
				logThirdPartyService.insertToRegisterReq(requestNo, req);
			}
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toResetMobileDo end...");
		logger.info("api toResetMobileDo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return "mobile/biz/resetMobileApiConfirm";
	}


	/**
	 * 绑卡跳转
	 * @param request
	 * @param model
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "toBindBankCardDo")
	public String toBindBankCardDo(HttpServletRequest request, Model model, String uuid) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toBindBankCardDo start...");
		ApiCacheObject aObject = ApiCacheUtils.getByUUID(uuid);
		if(aObject != null){
			String req = aObject.getReq();
			String sign = aObject.getSign();
			HashMap<String, Object> mapCache = aObject.getMap();
			String requestNo = String.valueOf(mapCache.get("requestNo"));
			String accountId = String.valueOf(mapCache.get("accountId"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));

			CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(NumberUtils.toLong(accountId, 0L));
			CustomerAccount  customerAccount = customerAccountService.get(accountId);
			
			AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
			String cardNo = accountInfoResp.getCardNo();
			String cardStatus = accountInfoResp.getCardStatus();
			String bank = accountInfoResp.getBank();
			
			boolean needUpdate = needUpdate(customerBankCard, cardNo, cardStatus);
			
			customerBankCard.setCardNo(cardNo);
			customerBankCard.setCardStatusCode(cardStatus);
			customerBankCard.setBankCode(bank);
			customerBankCard.setOpDt(new Date());
			customerBankCard.setLastModifyDt(new Date());
			//boolean hasHisInBeforeTowDay = customerBankCardHisService.hasHisInAfter(customerAccount.getAccountId(),-2);
			boolean isUpgrade = YeepayConstant.YEEPAY_PAY_SWIFT_UPGRADE.equals(accountInfoResp.getPaySwift()) ;
			
			
			
			//易宝远程验证当前有没有正预约解绑的卡,三种情况，1没查到hasAppointmentYeePayVerify=1, 2有预约解绑hasAppointmentYeePayVerify=2, 3没有预约解绑hasAppointmentYeePayVerify=3
			UnbindRecordResp unbindRecordResp = yeepayCommonHandler.queryUnbindRecord(customerBankCard.getUnbindRequestNo());
			Record record = (unbindRecordResp != null && unbindRecordResp.getRecords() != null && unbindRecordResp.getRecords().size()==1 ? unbindRecordResp.getRecords().get(0) : null);
			int hasAppointmentYeePayVerify = 1;
			if(record != null){
				hasAppointmentYeePayVerify = "INIT".equals(record.getStatus())?2:3;
				model.addAttribute("appointmentDate", DateUtils.parseDate(record.getAppointmentDate())); 
			}
			
			
			//如果当前没有正预约解绑的卡
			if(hasAppointmentYeePayVerify != 2  || !isUpgrade){
				
				//若在易宝查询到的银行卡信息与数据库中不一致
				if(needUpdate) {
					//则更新数据库中的信息
					customerBankCardService.update(customerBankCard);
				}
			}
			if("1".equals(customerAccount.getHasOpenThirdAccount())) {
				model.addAttribute("hasOpenThirdAccount", true);
			}
			//银行卡绑定状态："UNBIND"未绑定、"VERIFYING"认证中、"VERIFIED"已认证
			if(StringUtils.isBlank(cardNo)) {
				model.addAttribute("cardStatus", "UNBIND");
				model.addAttribute("requestNo", requestNo);
				model.addAttribute("req", req);
				model.addAttribute("sign", sign);
				String bindBankCardUrl = ApiUtil.getYeepayUrlPrefix(mobileType) + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOBINDBANKCARD_ACTION;
				logger.info("-------------toBindBankCardDo bindBankCardUrl：" + bindBankCardUrl);
				model.addAttribute("bindBankCardUrl",bindBankCardUrl );
				model.addAttribute("customerBankCard", customerBankCard);
				model.addAttribute("customerAccount", customerAccount);
				if(checkRequestNo(requestNo)){
					//customerBankCard.setRequestNo(requestNo);
					customerBankCardService.updateBankCardRequestNo(customerBankCard.getAccountId(),requestNo);
					logThirdPartyService.insertToBindBankCardReq(requestNo, req);
				}
			}else{
				model.addAttribute("cardStatus", cardStatus);
			}
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toBindBankCardDo end...");
		logger.info("api toBindBankCardDo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return "mobile/biz/bindBankCardApiConfirm";
	}
	
	
	private boolean needUpdate(CustomerBankCard customerBankCard, String cardNo, String cardStatus) {
		if((cardNo != null && !cardNo.equals(customerBankCard.getCardNo())) || (cardStatus != null && !cardStatus.equals(customerBankCard.getCardStatusCode()))
				|| (customerBankCard.getCardNo() != null && !customerBankCard.getCardNo().equals(cardNo)) || (customerBankCard.getCardStatusCode() != null && !customerBankCard.getCardStatusCode().equals(cardStatus))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 注册跳转
	 * @param request
	 * @param toRegisterReq
	 * @param model
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "toRegisterDo")
	public String toRegisterDo(HttpServletRequest request, ToRegisterReq toRegisterReq, Model model, String uuid) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toRegisterDo start...");
		ApiCacheObject aObject = ApiCacheUtils.getByUUID(uuid);
		if(aObject != null){
			String req = aObject.getReq();
			String sign = aObject.getSign();
			HashMap<String, Object> mapCache = aObject.getMap();
			String requestNo = String.valueOf(mapCache.get("requestNo"));
			String accountId = String.valueOf(mapCache.get("accountId"));
			String realName = String.valueOf(mapCache.get("realName"));
			String idCardNo = String.valueOf(mapCache.get("idCardNo"));
			String mobile = String.valueOf(mapCache.get("mobile"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			CustomerAccount  customerAccount = customerAccountService.get(accountId);
			
			toRegisterReq.setRealName(realName);
			toRegisterReq.setIdCardNo(idCardNo);
			toRegisterReq.setMobile(mobile);
			model.addAttribute("toRegisterReq", toRegisterReq);
			model.addAttribute("req", req);
			model.addAttribute("sign", sign);
			String registerUrl = ApiUtil.getYeepayUrlPrefix(mobileType) + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOREGISTER_ACTION;
			logger.info("-------------toRegisterDo registerUrl：" + registerUrl);
			model.addAttribute("registerUrl", registerUrl);
			if(checkRequestNo(requestNo)){
				customerAccount.setRequestNo(requestNo);
				customerAccountService.updateRequestNo(customerAccount);
				CustomerBase customerBase = new CustomerBase();
				customerBase.setAccountId(customerAccount.getAccountId());
				customerBase.setCustomerName(toRegisterReq.getRealName());
				customerBase.setCertNum(toRegisterReq.getIdCardNo());
				customerBaseService.updateNameAndCertNum(customerBase);
				logThirdPartyService.insertToRegisterReq(requestNo, req);
			}
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toRegisterDo end...");
		logger.info("api toRegisterDo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return "mobile/biz/openThirdAccountApiConfirm";
	}
	
	/**
	 * 检查requestNo
	 * @param requestNo
	 * @return
	 */
	public boolean checkRequestNo(String requestNo){
		LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(requestNo);
		if(logThirdParty == null){
			return true;
		}
		return false;
	}
	/**
	 * 充值跳转
	 * @param request
	 * @param model
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "toRechargeDo")
	public String toRechargeDo(HttpServletRequest request, Model model, String uuid) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toRechargeDo start...");
		ApiCacheObject aObject = ApiCacheUtils.getByUUID(uuid);
		if(aObject != null){
			String req = aObject.getReq();
			String sign = aObject.getSign();
			HashMap<String, Object> mapCache = aObject.getMap();
			String requestNo = String.valueOf(mapCache.get("requestNo"));
			String amount = String.valueOf(mapCache.get("amount"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			model.addAttribute("amount", amount);
			model.addAttribute("req", req);
			model.addAttribute("sign", sign);
			model.addAttribute("requestNo", requestNo);
			String rechargeUrl = ApiUtil.getYeepayUrlPrefix(mobileType) + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORECHARGE_ACTION;
			logger.info("-------------toRechargeDo rechargeUrl：" + rechargeUrl);
			model.addAttribute("rechargeUrl", rechargeUrl);
			if(checkRequestNo(requestNo)){
				logThirdPartyService.insertToRechargeReq(requestNo, req);
			}
			
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toRechargeDo end...");
		logger.info("api toRechargeDo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return "mobile/biz/rechargeApiConfirm";
	}
	
	/**
	 * 提现
	 * @param request
	 * @param model
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "toWithdrawDo")
	public String toWithdrawDo(HttpServletRequest request, Model model, String uuid) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toWithdrawDo start...");
		ApiCacheObject aObject = ApiCacheUtils.getByUUID(uuid);
		if(aObject != null){
			String req = aObject.getReq();
			String sign = aObject.getSign();
			String errorMsg = aObject.getErrorMsg();
			if(StringUtils.isNotBlank(errorMsg)){
				model.addAttribute("errorMsg", errorMsg);
				return "mobile/biz/withdrawApiException";
			}
			HashMap<String, Object> mapCache = aObject.getMap();
			String requestNo = String.valueOf(mapCache.get("requestNo"));
			Long accountId = (Long)mapCache.get("accountId");
			String mobileType = String.valueOf(mapCache.get("mobileType"));

			model.addAttribute("customerBase", customerBaseService.getByAccountId(accountId));
			model.addAttribute("customerBankCard", customerBankCardService.getByAccountId(accountId));
			model.addAttribute("req", req);
			model.addAttribute("sign", sign);
			model.addAttribute("requestNo", requestNo);
			String withdrawUrl = ApiUtil.getYeepayUrlPrefix(mobileType) + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_ACTION;
			logger.info("-------------toWithdrawDo withdrawUrl：" + withdrawUrl);
			model.addAttribute("withdrawUrl", withdrawUrl);
			if(checkRequestNo(requestNo)){
				logThirdPartyService.insertToWithdrawReq(requestNo, req);
			}
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toWithdrawDo end...");
		logger.info("api toWithdrawDo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return "mobile/biz/withdrawApiConfirm";
	}
	
	/**
	 * 定期投资跳转
	 * @param request
	 * @param toRegisterReq
	 * @param model
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "toInvestDo")
	public String toInvestDo(HttpServletRequest request, ToRegisterReq toRegisterReq, Model model, String uuid) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toInvestDo start...");
		ApiCacheObject aObject = ApiCacheUtils.getByUUID(uuid);
		if(aObject != null){
			String req = aObject.getReq();
			String sign = aObject.getSign();
			HashMap<String, Object> mapCache = aObject.getMap();
			String requestNo = String.valueOf(mapCache.get("requestNo"));
			Long accountId = (Long)mapCache.get("accountId");
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			ToInvestReq toInvestReq = (ToInvestReq)mapCache.get("toInvestReq");
			//操作终端渠道
			String channel = ApiUtil.getOperaChannel(mobileType);
			String projectId = toInvestReq.getProjectId();
			String transferProjectId = toInvestReq.getTransferProjectId();
			ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(projectId);
			ProjectExecuteSnapshot pesInvest = projectExecuteSnapshotService.getByProjectId(projectBaseInfo.getProjectId());
			projectBaseInfo.setPes(pesInvest);
			//投资金额
			Double amount = NumberUtils.toDouble(toInvestReq.getAmount(), 0.00);
			//优惠券Ids
			String ticketIds = toInvestReq.getTicketIds();
			//加息券ids
			String rateTicketIds =toInvestReq.getRateTicketIds();
			//计息开始日期
			Date beginInterestDate = DateUtils.dateFormate(new Date());
			//优惠券金额
			Double ticketAmount = NumberUtil.toDouble(toInvestReq.getTicketAmount(), 0.00);
			//平台垫付金额
			Double platformAmount = NumberUtil.toDouble(toInvestReq.getPlatformAmount(), 0.00);
			//实际支付金额
			Double actualAmount = LoanUtil.formatAmount(amount - ticketAmount - platformAmount);
			String type = StringUtil.dealString(toInvestReq.getType());
			//项目信息
			model.addAttribute("projectBaseInfo", projectBaseInfo);
			model.addAttribute("amount", amount);
			model.addAttribute("type", type);
			if (ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT.equals(type)) {
				if(checkRequestNo(requestNo)){
					//投资前置服务
					try {
						investmentService.beforeInvest(projectBaseInfo, channel, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds, beginInterestDate);
					} catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("errorMsg", ApiConstant.API_EXCEPTION_RESP_TEXT);
						return "mobile/biz/investApiException";
					}
					//请求易宝接口的日志
					logThirdPartyService.insertToCpTransaction(YeepayConstant.BIZ_TYPE_TENDER, requestNo, req);
				}
				String yeepayURL = ApiUtil.getYeepayUrlPrefix(mobileType) + "toCpTransaction";
				logger.info("-------------projectInvestment toInvestDo yeepayURL：" + yeepayURL);
				model.addAttribute("yeepayURL",yeepayURL);
				model.addAttribute("req",req);
				model.addAttribute("sign",sign);
				model.addAttribute("actualAmount", actualAmount);
			}else if(ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT.equals(type)){
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
				//下家(受让方)服务费
				Double downServiceCharge = ProjectUtil.getDownServiceCharge(amount);
				if(checkRequestNo(requestNo)){
					//债权转让前置服务
					try {
						assignmentService.beforeAssign(projectTransferInfo, channel, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds, beginInterestDate);
					} catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("errorMsg", ApiConstant.API_EXCEPTION_RESP_TEXT);
						return "mobile/biz/investApiException";
					}
					//请求易宝接口的日志
					logThirdPartyService.insertToCpTransaction(YeepayConstant.BIZ_TYPE_CREDIT_ASSIGNMENT, requestNo, req);
				}
				String yeepayURL = ApiUtil.getYeepayUrlPrefix(mobileType) + "toCpTransaction";
				logger.info("-------------projectInvestmentAssignment toInvestDo yeepayURL：" + yeepayURL);
				model.addAttribute("yeepayURL",yeepayURL);
				model.addAttribute("req",req);
				model.addAttribute("sign",sign);
				model.addAttribute("actualAmount", LoanUtil.formatAmount(actualAmount + downServiceCharge));
			}
			
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toInvestDo end...");
		logger.info("api toInvestDo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return "mobile/biz/investApiConfirm";
	}
	
	/**
	 * 活期投资跳转
	 * @param request
	 * @param toRegisterReq
	 * @param model
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "toCurrentInvestDo")
	public String toCurrentInvestDo(HttpServletRequest request, ToRegisterReq toRegisterReq, Model model, String uuid) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api toCurrentInvestDo start...");
		ApiCacheObject aObject = ApiCacheUtils.getByUUID(uuid);
		if(aObject != null){
			String req = aObject.getReq();
			String sign = aObject.getSign();
			HashMap<String, Object> mapCache = aObject.getMap();
			String requestNo = String.valueOf(mapCache.get("requestNo"));
			Long accountId = (Long)mapCache.get("accountId");
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			ToInvestReq toInvestReq = (ToInvestReq)mapCache.get("toInvestReq");
			//操作终端渠道
			String opTerm = ApiUtil.getOperaChannel(mobileType);
			String projectId = toInvestReq.getProjectId();
			CurrentProjectInfo cInfo = currentProjectInfoService.get(projectId);
			//执行快照信息
			CurrentProjectExecuteSnapshot executeSnapshot = currentProjectExecuteSnapshotService.getByProjectId(NumberUtil.toLong(projectId, 0L));
			cInfo.setSnapshot(executeSnapshot);
			//投资金额
			Double amount = NumberUtils.toDouble(toInvestReq.getAmount(), 0.00);
			//项目信息
			model.addAttribute("cInfo", cInfo);
			model.addAttribute("amount", amount);
			if(checkRequestNo(requestNo)){
				//投资前置服务
				try {
					currentInvestmentServiceImpl.beforeCurrentInvest(cInfo, opTerm, requestNo, accountId, amount, true);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("errorMsg", ApiConstant.API_EXCEPTION_RESP_TEXT);
					return "mobile/biz/currentInvestApiException";
				}
				//请求易宝接口的日志
				logThirdPartyService.insertToCpTransaction(YeepayConstant.BIZ_TYPE_TENDER, requestNo, req);
			}
			String yeepayURL = ApiUtil.getYeepayUrlPrefix(mobileType) + "toCpTransaction";
			logger.info("-------------toCurrentInvestDo yeepayURL：" + yeepayURL);
			model.addAttribute("yeepayURL",yeepayURL);
			model.addAttribute("req",req);
			model.addAttribute("sign",sign);
			
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api toCurrentInvestDo end...");
		logger.info("api toCurrentInvestDo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return "mobile/biz/currentInvestApiConfirm";
	}
	
}
