/**
 * 
 */
package com.thinkgem.jeesite.modules.yeepay.web;

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
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoRepayment.ToAuthorizeAutoRepaymentResp;
import com.thinkgem.jeesite.common.yeepay.toBindBankCard.ToBindBankCardResp;
import com.thinkgem.jeesite.common.yeepay.toCpTransaction.ToCpTransactionResp;
import com.thinkgem.jeesite.common.yeepay.toRecharge.ToRechargeResp;
import com.thinkgem.jeesite.common.yeepay.toRegister.ToRegisterResp;
import com.thinkgem.jeesite.common.yeepay.toResetMobile.ToResetMobileResp;
import com.thinkgem.jeesite.common.yeepay.toUnbindBankCard.ToUnbindBankCardResp;
import com.thinkgem.jeesite.common.yeepay.toWithdraw.ToWithdrawResp;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.project.service.util.handler.RepaymentPlanHandler;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import com.hsbank.util.type.StringUtil;

/**
 * 易宝回跳操作
 * @author ydt
 *
 */
@Controller
@RequestMapping(value = "/yeepay/gateWay/callback")
public class YeepayGateWayCallbackController extends BaseController {
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
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);
			
			if("1".equals(bean.getCode())) {
				receiveMes(request, requestNo);
				CustomerAccount customerAccount = customerAccountService.getByRequestNo(requestNo);
				customerAccountService.updateHasOpenThirdAccount(customerAccount.getAccountId(),ProjectConstant.HASOPENED);
				//成功开通账号后更新cache中的信息
				CacheUtils.put(CustomerUtils.CUSTOMER_CACHE, CustomerUtils.CUSTOMER_CACHE_ID_ + customerAccount.getAccountId(), customerAccount);
			}
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "modules/front/wdzh/openThirdAccountResult";
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

			if("1".equals(bean.getCode())) {
				receiveMes(request, requestNo);
			}
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "modules/front/wdzh/bindBankCardResult";
		}
		//验证失败到错误页面，暂未设计
		return "modules/front/error";
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

			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
			}
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "modules/front/wdzh/capital/rechargeResult";
		}
		return "ERROR";
	}

	/**
	 * 修改手机号回调操作
	 * 		更新logThirdParty表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("toResetMobile")
	public String toResetMobileResult(HttpServletRequest request, Model model) {
		String resp = request.getParameter("resp");
		String sign = request.getParameter("sign");
		//对数据进行验证
		if (SignUtil.verifySign(resp, sign)) {
			ToResetMobileResp bean = JaxbMapper.fromXml(resp, ToResetMobileResp.class);
			String requestNo = bean.getRequestNo();
			String respCode = bean.getCode();
			logThirdPartyService.updateWithCallback(requestNo, resp, respCode);

			if("1".equals(bean.getCode())) {
				receiveMes(request, requestNo);
			}
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "modules/front/wdzh/account/changeMobileResult";
		}
		//验证失败到错误页面，暂未设计
		return "modules/front/error";
	}
	
	/**
	 * 重置交易密码回调操作
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

			if("1".equals(bean.getCode())) {
				receiveMes(request, requestNo);
			}
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "modules/front/wdzh/account/resetTransPwdResult";
		}
		//验证失败到错误页面，暂未设计
		return "modules/front/error";
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
		String forwardUrl ="project_tzsb";
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
			List<RepaymentPlanItem> repaymentPlan = repaymentPlanHandler.generateForInvestment(projectBaseInfo, investmentRecord.getAmount(),StringUtil.dealString(investmentRecord.getRateTicketIds()));
			model.addAttribute("repaymentPlan", repaymentPlan);
			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
				forwardUrl = "project_tzcg";
			}
			model.addAttribute("description", description);
			model.addAttribute("menu", "wytz");
			model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/project_detail?id="+projectBaseInfo.getProjectId()+"'>项目详情</a>");
			return "modules/front/wytz/"+forwardUrl;
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
		String forwardUrl ="current_buy_fail";
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
			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
				forwardUrl = "current_buy_success";
			}
			model.addAttribute("description", description);
			model.addAttribute("menu", "wytz");
			model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/project_detail?id="+cInfo.getId()+"'>项目详情</a>");
			return "modules/front/wytz/current/"+forwardUrl;
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
		String forwardUrl ="project_tzsb";
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
			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
				forwardUrl = "project_assignment_tzcg";
			}
			model.addAttribute("description", description);
			model.addAttribute("menu", "wytz");
			model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/project_detail?id="+projectBaseInfo.getProjectId()+"'>项目详情</a>");
			return "modules/front/wytz/"+forwardUrl;
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

			if("1".equals(respCode)) {
				receiveMes(request, requestNo);
			}
			model.addAttribute("isSuccess", "1".equals(bean.getCode()) ? true : false);
			model.addAttribute("menu", "wdzh");
			return "modules/front/wdzh/capital/withdrawResult";
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
	
}
