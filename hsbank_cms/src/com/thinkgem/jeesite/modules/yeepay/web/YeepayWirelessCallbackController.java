/**
 * 
 */
package com.thinkgem.jeesite.modules.yeepay.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.thinkgem.jeesite.common.yeepay.toResetPassword.ToResetPasswordResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoRepayment.ToAuthorizeAutoRepaymentResp;
import com.thinkgem.jeesite.common.yeepay.toBindBankCard.ToBindBankCardResp;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionResp;
import com.thinkgem.jeesite.common.yeepay.toRecharge.ToRechargeResp;
import com.thinkgem.jeesite.common.yeepay.toRegister.ToRegisterResp;
import com.thinkgem.jeesite.common.yeepay.toResetMobile.ToResetMobileResp;
import com.thinkgem.jeesite.common.yeepay.toUnbindBankCard.ToUnbindBankCardResp;
import com.thinkgem.jeesite.common.yeepay.toWithdraw.ToWithdrawResp;
import com.thinkgem.jeesite.modules.api.ApiCacheObject;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.project.service.util.handler.RepaymentPlanHandler;
import com.thinkgem.jeesite.modules.sys.utils.ApiCacheUtils;
import com.hsbank.util.type.StringUtil;

/**
 * 易宝回跳操作
 * @author ydt
 *
 */
@Controller
@RequestMapping(value = "/yeepay/wireless/callback")
public class YeepayWirelessCallbackController extends BaseController {
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private ProjectInvestmentRecordService investmentRecordService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	
	
	@RequestMapping("iosOperaResult")
	public String toIosResult(HttpServletRequest request, Model model) {
		String operaType = request.getParameter("type");
		logger.info("iosOperaResult：" + operaType);
		model.addAttribute("operaType", operaType);
		return "mobile/biz/iosOperaResult";
	}
	
	/**
	 * ios易宝操作返回结果
	 * @param type
	 * @param respResult
	 * @return
	 */
	public static String resultUrl(String type, String respResult){
		return YeepayConstant.YEEPAY_GATE_WAY_WIRELESS_CALLBACK_URL_PREFIX + "iosOperaResult?type="+ type + "&" + respResult;
	}
	/**
	 * 开通易宝账号回跳操作
	 * 		更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toRegister")
	public String toRegisterResult(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToRegisterResp bean = JaxbMapper.fromXml(resp, ToRegisterResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			String respResult = "toRegisterYiBaoFailure";
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			Long accountId = (Long)mapCache.get("accountId");
			if("1".equals(bean.getCode())) {
				receiveMes(request, requestNo);
				customerAccountService.updateHasOpenThirdAccount(accountId,ProjectConstant.HASOPENED);
				respResult = "toRegisterYiBaoSuccess";
			}
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toRegister callbackUrl：" + callbackUrl);
			logger.info("------------get resp toRegister mobileType：" + mobileType);
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toRegister",respResult));
			return "mobile/biz/openThirdAccountApiResult";
		}
		//验证失败到错误页面，暂未设计
		return "modules/front/error";
	}

	
	/**
	 * 绑定银行卡回调操作
	 * 		更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toBindBankCard")
	public String toBindBankCardResult(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToBindBankCardResp bean = JaxbMapper.fromXml(resp, ToBindBankCardResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			String respResult = "toBingDingCardFailure";
			if("1".equals(bean.getCode())) {
				receiveMes(request, requestNo);
				respResult = "toBingDingCardSuccess";
			}
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toBindBankCard mobileType：" + mobileType);
			logger.info("------------get resp toBindBankCard callbackUrl：" + callbackUrl);
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toBindBankCard",respResult));
			return "mobile/biz/bindBankCardApiResult";
		}
		//验证失败到错误页面，暂未设计
		return "ERROR";
	}

	/**
	 * 取消绑卡易宝callback操作
	 * 		1.更新customerBankCard表
	 * 		2.更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toUnbindBankCard")
	public String toUnbindBankCard(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToUnbindBankCardResp bean = JaxbMapper.fromXml(resp, ToUnbindBankCardResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			String message = bean.getDescription();
			
			if("1".equals(respCode)) {
				customerBankCardService.updateWithUnbindBankCardCallback(requestNo, message);
				receiveMes(request, requestNo);
			}
			
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "modules/front/wdzh/unbindBankCardResult";
		}
		//验证失败到错误页面，暂未设计
		return "modules/front/error";
	}
	
	/**
	 * 充值易宝callback操作
	 * 		更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toRecharge")
	public String toRecharge(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToRechargeResp bean=JaxbMapper.fromXml(resp, ToRechargeResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			String respResult = "toRechargeFailure";
			if("1".equals(respCode)) {
				respResult = "toRechargeSuccess";
				receiveMes(request, requestNo);
			}
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toRecharge mobileType：" + mobileType);
			logger.info("------------get resp toRecharge callbackUrl：" + callbackUrl);
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toRecharge",respResult));
			return "mobile/biz/rechargeApiResult";
		}
		return "ERROR";
	}


	
	/**
	 * 普通项目-投资易宝callback操作
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toCpTransactionTender")
	public String toCpTransactionTender(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		String forwardUrl ="investApiFail";
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToCpTransactionResp bean = JaxbMapper.fromXml(resp, ToCpTransactionResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			String description = bean.getDescription();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			List<ProjectInvestmentRecord> recordList = investmentRecordService.findListByThirdPartyOrder(requestNo);
			ProjectInvestmentRecord investmentRecord = new ProjectInvestmentRecord();
			if(recordList != null && recordList.size() > 0){
				investmentRecord = recordList.get(0);
			}
			Long transferProjectId_l = investmentRecord.getTransferProjectId() != null ? investmentRecord.getTransferProjectId() : 0;
			String transferProjectId = String.valueOf(transferProjectId_l);
			ProjectTransferInfo projectTransferInfo = new ProjectTransferInfo();
			if(!"0".equals(transferProjectId)){
				projectTransferInfo = projectTransferInfoService.get(transferProjectId);
				Long transferor = projectTransferInfo.getTransferor();
				for(ProjectInvestmentRecord pr : recordList){
					Long investmentUserId = pr.getInvestmentUserId();
					//判断是否转让部分的投资记录
					if(!investmentUserId.equals(transferor)){
						investmentRecord = pr;
						break;
					}
				}
			}
			//投资记录
			model.addAttribute("investmentRecord", investmentRecord);
			ProjectBaseInfo projectBaseInfo = new ProjectBaseInfo();
			projectBaseInfo.setId(String.valueOf(investmentRecord.getProjectId()));
			projectBaseInfo = projectBaseInfoService.get(projectBaseInfo);
			//项目信息
			model.addAttribute("projectBaseInfo", projectBaseInfo);
			//还款计划
			List<RepaymentPlanItem> repaymentPlan = repaymentPlanHandler.generateForInvestment(projectBaseInfo, investmentRecord.getAmount(), StringUtil.dealString(investmentRecord.getRateTicketIds()));
			model.addAttribute("repaymentPlan", repaymentPlan);
			String respResult = "toInvestPorjectFailure";
			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
				forwardUrl = "investApiSuccess";
				respResult = "toInvestPorjectSuccess";
			}
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toCpTransactionTender mobileType：" + mobileType);
			logger.info("------------get resp toCpTransactionTender callbackUrl：" + callbackUrl);
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toCpTransactionTender",respResult));
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("description", description);
			model.addAttribute("menu", "wytz");
			model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/project_detail?id="+projectBaseInfo.getProjectId()+"'>项目详情</a>");
			return "mobile/biz/"+forwardUrl;
		}
		return "ERROR";
	}
	
	/**
	 * 活期产品-投资易宝callback操作
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toCurrentCpTransactionTender")
	public String toCurrentCpTransactionTender(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		String forwardUrl ="currentInvestApiFail";
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToCpTransactionResp bean = JaxbMapper.fromXml(resp, ToCpTransactionResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			String description = bean.getDescription();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			CurrentAccountPrincipalChangeHis cHis = currentAccountPrincipalChangeHisService.getByThirdPartyOrder(requestNo, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT);
			//投资记录
			model.addAttribute("cHis", cHis);
			CurrentProjectInfo cInfo = currentProjectInfoService.get(String.valueOf(cHis.getProjectId()));
			//项目信息
			model.addAttribute("cInfo", cInfo);
			String respResult = "toCurrentProjectFailure";
			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
				forwardUrl = "currentInvestApiSuccess";
				respResult = "toCurrentProjectSuccess";
			}
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toCpTransactionTender mobileType：" + mobileType);
			logger.info("------------get resp toCpTransactionTender callbackUrl：" + callbackUrl);
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toCpTransactionTender",respResult));
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("description", description);
			return "mobile/biz/"+forwardUrl;
		}
		return "ERROR";
	}
	
	
	/**
	 * 债权转让项目-投资易宝callback操作
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toCpTransactionAssignment")
	public String toCpTransactionAssignment(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		String forwardUrl ="investApiFail";
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToCpTransactionResp bean = JaxbMapper.fromXml(resp, ToCpTransactionResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			String description = bean.getDescription();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			List<ProjectInvestmentRecord> recordList = investmentRecordService.findListByThirdPartyOrder(requestNo);
			ProjectInvestmentRecord investmentRecord = new ProjectInvestmentRecord();
			if(recordList != null && recordList.size() > 0){
				investmentRecord = recordList.get(0);
			}
			Long transferProjectId_l = investmentRecord.getTransferProjectId() != null ? investmentRecord.getTransferProjectId() : 0;
			String transferProjectId = String.valueOf(transferProjectId_l);
			ProjectTransferInfo projectTransferInfo = new ProjectTransferInfo();
			if(!"0".equals(transferProjectId)){
				projectTransferInfo = projectTransferInfoService.get(transferProjectId);
				Long transferor = projectTransferInfo.getTransferor();
				for(ProjectInvestmentRecord pr : recordList){
					Long investmentUserId = pr.getInvestmentUserId();
					//判断是否转让部分的投资记录
					if(!investmentUserId.equals(transferor)){
						investmentRecord = pr;
						break;
					}
				}
			}
			//投资记录
			model.addAttribute("investmentRecord", investmentRecord);
			ProjectBaseInfo projectBaseInfo = new ProjectBaseInfo();
			projectBaseInfo.setId(String.valueOf(investmentRecord.getProjectId()));
			projectBaseInfo = projectBaseInfoService.get(projectBaseInfo);
			//项目信息
			model.addAttribute("projectBaseInfo", projectBaseInfo);
			//还款计划
			List<RepaymentPlanItem> repaymentPlan = repaymentPlanHandler.generateForInvestment(projectBaseInfo, investmentRecord.getAmount(), StringUtil.dealString(investmentRecord.getRateTicketIds()));
			model.addAttribute("repaymentPlan", repaymentPlan);
			String respResult = "toCurrentProjectFailure";
			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
				forwardUrl = "investApiSuccess";
				respResult = "toCurrentProjectSuccess";
			}
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toCpTransactionAssignment mobileType：" + mobileType);
			logger.info("------------get resp toCpTransactionAssignment callbackUrl：" + callbackUrl);
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toCpTransactionAssignment",respResult));
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("description", description);
			model.addAttribute("menu", "wytz");
			model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/project_detail?id="+projectBaseInfo.getProjectId()+"'>项目详情</a>");
			return "mobile/biz/"+forwardUrl;
		}
		return "ERROR";
	}
	
	/**
	 * 提现易宝callback操作
	 * 		更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toWithdraw")
	public String toWithdraw(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToWithdrawResp bean=JaxbMapper.fromXml(resp, ToWithdrawResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			String respResult = "toWithdrawFailure";
			if("1".equals(respCode)) {
				respResult = "toWithdrawSuccess";
				receiveMes(request, requestNo);
			}
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toWithdraw mobileType：" + mobileType);
			logger.info("------------get resp toWithdraw callbackUrl：" + callbackUrl);
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toWithdraw",respResult));
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "mobile/biz/withdrawApiResult";
		}
		return "ERROR";
	}
	
	/**
	 * 授权自动还款易宝callback操作
	 * 		更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toAuthorizeAutoRepayment")
	public String toAuthorizeAutoRepayment(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToAuthorizeAutoRepaymentResp bean=JaxbMapper.fromXml(resp, ToAuthorizeAutoRepaymentResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);

			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
			}
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "modules/front/wdzh/loan/authorizeAutoRepaymentResult";
		}
		return "ERROR";
	}
	
	/**
	 * 回调后将requestNo作为key "hasReceivedMes"作为value保存到session中
	 * @param request
	 * @param requestNo
	 */
	private void receiveMes(HttpServletRequest request, String requestNo) {
		HttpSession session = request.getSession();
		if(!"hasReceivedMes".equals(session.getAttribute(requestNo))) {
			session.setAttribute(requestNo, "hasReceivedMes");
		}
	}
	
	/**
	 * 根据requestNo判断是否已经收到通知
	 * @param request
	 * @param requestNo
	 * @return
	 */
	@RequestMapping("hasReceivedMes")
	@ResponseBody
	public boolean hasReceivedMes(HttpServletRequest request, String requestNo) {
		boolean hasReceived = false;
		if("hasReceivedMes".equals(request.getSession().getAttribute(requestNo))) {
			hasReceived = true;
		}
		return hasReceived;
	}

	/**
	 * 重置交易密码易宝callback操作
	 * 		更新logThirdParty表
	 * @param request
	 * @param model
     * @return
     */
	@RequestMapping("toResetTransPwd")
	public String toResetTransPwd(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToResetPasswordResp bean = JaxbMapper.fromXml(resp, ToResetPasswordResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			String respResult = "toResetTransPwdFailure";
			if("1".equals(respCode)) {
				respResult = "toResetTransPwdSuccess";
				receiveMes(request, requestNo);
			}
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toResetTransPwd mobileType：" + mobileType);
			logger.info("------------get resp toResetTransPwd callbackUrl：" + callbackUrl);
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toResetTransPwd",respResult));
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);

			return "mobile/biz/resetTransPwdApiResult";
		}
		//验证失败到错误页面，暂未设计
		return "ERROR";
	}

	/**
	 * 修改手机号码易宝callback操作
	 * 		更新logThirdParty表
	 * @param request
	 * @param model
     * @return
     */
	@RequestMapping("toResetMobile")
	public String toResetMobile(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToResetMobileResp bean = JaxbMapper.fromXml(resp, ToResetMobileResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			String respResult = "toResetMobileFailure";
			if("1".equals(respCode)) {
				respResult = "toResetMobileSuccess";
				receiveMes(request, requestNo);
			}
			ApiCacheObject aObject = ApiCacheUtils.getByUUID(requestNo);
			HashMap<String, Object> mapCache = aObject.getMap();
			String callbackUrl = String.valueOf(mapCache.get("callbackUrl"));
			String mobileType = String.valueOf(mapCache.get("mobileType"));
			logger.info("------------get resp toResetMobile mobileType：" + mobileType);
			logger.info("------------get resp toResetMobile callbackUrl：" + callbackUrl);
			model.addAttribute("callbackUrl", callbackUrl);
			model.addAttribute("mobileType", mobileType);
			model.addAttribute("resultUrl", resultUrl("toResetMobile",respResult));
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);

			return "mobile/biz/resetMobileApiResult";
		}
		//验证失败到错误页面，暂未设计
		return "ERROR";
	}

}
