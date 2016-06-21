package com.thinkgem.jeesite.modules.front;

import com.hsbank.util.http.HttpRequestUtil;
import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.current.service.investment.CurrentInvestmentServiceImpl;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

@Controller
@RequestMapping(value = "${frontPath}/current")
public class CurrentProjectController extends BaseController {
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CurrentProjectExecuteSnapshotService currentProjectExecuteSnapshotService;
	@Autowired
	private CurrentInvestmentServiceImpl currentInvestmentServiceImpl;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	
	@Autowired
	private CurrentAccountSummaryService currentAccountSummaryService;
	
	/**
	 * 活期产品列表
	 * @param rate
	 * @param status
	 * @param pageSearchBean
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "hqcp")
	public String hqcp(String rate, String status, PageSearchBean pageSearchBean, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentProjectInfo> page = currentProjectInfoService.searchPage(rate, status, pageSearchBean);
		//活期项目列表
		model.addAttribute("page", page);
		model.addAttribute("rate", rate);
		model.addAttribute("status", status);
		model.addAttribute("menu", "hqcp");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;活期产品");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		return "modules/front/hqcp";
	}
	
	/**
	 * 去到最新的投标中活期项目页
	 * @param cInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toFirstTenderingCurrentProject")
	public String toFirstTenderingCurrentProject(CurrentProjectInfo cInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		CurrentProjectInfo currentProjectInfo = currentProjectInfoService.getFirstTenderingCurrentProjectInfo();
		cInfo.setId(currentProjectInfo.getId());
		return currentProjectDetail( cInfo,  request,  response,  model);
	}
	
	@RequestMapping(value = "currentProjectDetail")
	public String currentProjectDetail(CurrentProjectInfo cInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		cInfo = currentProjectInfoService.get(cInfo);
		
		String[] canVisitProjectStatuses = {"3", "4", "5"};
		if(!Arrays.asList(canVisitProjectStatuses).contains(cInfo.getStatus())) {
			throw new ServiceException("can not visit this status project.");
		}
		
		//剩余天、时、分
		long distanceMillis = cInfo.getEndInvestmentDt().getTime() - new Date().getTime();
		long remainDay = distanceMillis / (1000 * 60 * 60 * 24);
		long remainHour = (distanceMillis - remainDay * (1000 * 60 * 60 * 24))/(1000 * 60 * 60);
		long remainMinute = (distanceMillis - remainDay * (1000 * 60 * 60 * 24) - remainHour * (1000 * 60 * 60))/(1000 * 60);
		model.addAttribute("remainDay", remainDay);
		model.addAttribute("remainHour", remainHour);
		model.addAttribute("remainMinute", remainMinute);
		//登录账户
		Long accountId = 0L;
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal != null ) {
			accountId = principal.getAccountId();
			CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
			model.addAttribute("customerBalance", customerBalance);
		}
		model.addAttribute("accountId",accountId);
		//项目信息
		model.addAttribute("cInfo", cInfo);
		
		//每万元日收益
		model.addAttribute("DAYS_IN_YEAR", CurrentProjectConstant.DAYS_IN_YEAR);
		model.addAttribute("everyWanDayEarnings", NumberUtils.formatNotRoundWithScale(10000*cInfo.getRate()/CurrentProjectConstant.DAYS_IN_YEAR, 2));
		
		model.addAttribute("menu", "wytz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;活期产品");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		
		//获得总购买人数
		CurrentAccountPrincipalChangeHis queryEntity = new CurrentAccountPrincipalChangeHis();
		queryEntity.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT);
		queryEntity.setStatus(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL);
		queryEntity.setProjectId(Long.parseLong(cInfo.getId()));
		Integer buyCount = currentAccountPrincipalChangeHisService.getCount(queryEntity);
		model.addAttribute("buyCount",buyCount);
		
		
		CurrentAccountPrincipalChangeHis queryEntity2 = new CurrentAccountPrincipalChangeHis();
		queryEntity2.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT);
		queryEntity2.setStatus(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_FREEZE);
		queryEntity2.setAccountId(accountId);
		Double changeValueSum = currentAccountPrincipalChangeHisService.getChangeValueSum(queryEntity2);
		
		
		//当前用户还可投资额度
		CurrentAccountSummary currentAccountSummary = currentAccountSummaryService.getByAccountId(accountId);
		
		if(currentAccountSummary != null && currentAccountSummary.getCurrentPrincipal() != null){
			model.addAttribute("investable",CurrentProjectConstant.MAX_INVESTMENT_MONEY-currentAccountSummary.getCurrentPrincipal()-changeValueSum);
		}
		
		
		return "modules/front/wytz/current/current_project_detail";
	}


	/**
	 * 活期产品：投资确认
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping(value = "currentBuyConfirm")
	public String currentBuyConfirm(HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = HttpRequestUtil.getInstance(request).getParameter("projectId");
		CurrentProjectInfo cInfo = currentProjectInfoService.get(projectId);
		//执行快照信息
		CurrentProjectExecuteSnapshot executeSnapshot = currentProjectExecuteSnapshotService.getByProjectId(NumberUtil.toLong(projectId, 0L));
		cInfo.setSnapshot(executeSnapshot);
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		//会员基本信息
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		//投资金额
		Double amount = NumberUtil.toDouble(request.getParameter("amount"), 0.00);
		amount = LoanUtil.formatAmount(amount);
		String projectName = StringUtils.replaceSpecialStr(cInfo.getName());
		String projectCode = StringUtils.replaceSpecialStr(cInfo.getCode());
		if(projectCode != null && projectCode.length() > 100 ){
			projectCode = projectCode.substring(1,100);
		}
		String platformUserNo = customerAccount.getPlatformUserNo();
		//融资人
		Long loanAccountId = cInfo.getBorrowerAccountId();
		//项目信息
		model.addAttribute("cInfo", cInfo);
		model.addAttribute("amount", amount);
		String borrowerPlatformUserNo = customerAccountService.get(loanAccountId).getPlatformUserNo();
		//给平台服务费
		Double serviceCharge = 0.0;
		//给融资方的金额
		Double financingAmount = amount > serviceCharge ? amount - serviceCharge : 0;
		financingAmount = LoanUtil.formatAmount(financingAmount);
		//投资交易第三方流水编号
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ,customerAccount.getPlatformUserNo());
		//投资前置服务
		try {
			currentInvestmentServiceImpl.beforeCurrentInvest(cInfo, ProjectConstant.OP_TERM_DICT_PC, requestNo, accountId, amount,true);;
		} catch (Exception e) {
			model.addAttribute("description", e.getMessage());
			return "modules/front/wytz/current/current_buy_exception";
		}
		//生成调用易宝投标接口数据
		String req = YeepayUtils.generationCurrentXml_for_tender(projectName, projectId, ProjectConstant.OP_TERM_DICT_PC, projectCode, requestNo, platformUserNo, borrowerPlatformUserNo, cInfo.getFinanceMoney(), serviceCharge, financingAmount);
		//签名
		String sign = SignUtil.sign(req);
		//请求易宝接口的日志
		logThirdPartyService.insertToCpTransaction(YeepayConstant.BIZ_TYPE_TENDER, requestNo, req);
		model.addAttribute("yeepayURL",YeepayConstant.YEEPAY_GATE_URL_PREFIX + "toCpTransaction");
		model.addAttribute("req",req);
		model.addAttribute("sign",sign);
		model.addAttribute("actualAmount", amount);
		
		return "modules/front/wytz/current/current_buy_confirm";
	}
		
	/**
	 * 活期产品：投资购买
	 * @param projectBaseInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "currentProjectBuy")
	public String currentProjectBuy(CurrentProjectInfo cInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		cInfo = currentProjectInfoService.get(cInfo);
		Double amount = LoanUtil.formatAmount(NumberUtil.toDouble(request.getParameter("amount"), 0.00));
		//项目信息
		model.addAttribute("cInfo", cInfo);
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		//会员基本信息
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		model.addAttribute("customerBase", customerBase);
		//会员账户信息
		CustomerBalance customerBalance = customerBalanceService.get(String.valueOf(accountId));
		if(customerBalance == null){
			customerBalance = new CustomerBalance();
		}
		model.addAttribute("customerBalance", customerBalance);
		//投资金额
		model.addAttribute("amount", amount);
		model.addAttribute("menu", "wytz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='#'>投资确认</a>");
		return "modules/front/wytz/current/current_project_buy";
	}
}
