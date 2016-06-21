package com.thinkgem.jeesite.modules.front;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountInterestChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectHoldInfoService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

@Controller
@RequestMapping("${frontPath}/currentAccount")
public class CurrentAccountController extends BaseController {
	public static final double ONE_DAY_TEN_THOUSAND_PROFIT = 10000;
	@Autowired
	private CurrentProjectHoldInfoService currentProjectHoldInfoService;
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CurrentAccountSummaryService currentAccountSummaryService;
	@Autowired
	private CurrentAccountInterestChangeHisService currentAccountInterestChangeHisService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	@Autowired
	CustomerAccountService customerAccountService;
	
	/**
	 * 用户活期产品持有信息列表
	 * @param model
	 * @return
	 */
	@RequestMapping("holdInfoList")
	public String holdInfoList(Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		List<CurrentProjectHoldInfo> currentProjectHoldInfoList = currentProjectHoldInfoService.findListByAccountId(accountId);
		model.addAttribute("currentProjectHoldInfoList", currentProjectHoldInfoList);
		return "modules/front/current/account/holdInfoList";
	}
	
	/**
	 * 我的活花生
	 * @param pageSearchBean
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("myCurrentPeanut")
	public String myCurrentPeanut(PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		//账户总览信息
		CurrentAccountSummary summary = currentAccountSummaryService.getByAccountId(accountId);
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		model.addAttribute("summary", summary);
		Double yesterdayProfit = currentAccountInterestChangeHisService.getYesterdayProfitByAccountId(accountId, CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST);
		yesterdayProfit = NumberUtils.formatWithScale(yesterdayProfit,4);
		model.addAttribute("yesterdayProfit", yesterdayProfit);
		Page<CurrentProjectHoldInfo> page = currentProjectHoldInfoService.searchMyPageList(accountId, pageSearchBean);
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "wdhhs");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='#'>我的活花生</a>");
		return "modules/front/wdzh/current/my_current_peanut";
	}
	
	
	/**
	 * 活期产品：每日收益
	 * @param projectId
	 * @param pageSearchBean
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("myDailyProfit")
	public String myDailyProfit(String projectId, PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		//收息
		String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST;
		//myHead信息
		myHeadInfo(accountId, projectId, model);
		Page<CurrentAccountInterestChangeHis> page = currentAccountInterestChangeHisService.searchMyInterestPageList(accountId, projectId, changeType, pageSearchBean);
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "wdhhs");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='"+Global.getInstance().getFrontRootUrl(request)+"/currentAccount/myCurrentPeanut'>我的活花生</a>");
		return "modules/front/wdzh/current/my_daily_profit";
	}
	
	/**
	 * 活期产品：转入
	 * @param projectId
	 * @param pageSearchBean
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("myChangeInto")
	public String myChangeInto(String projectId, PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		//投资
		String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT;
		//myHead信息
		myHeadInfo(accountId, projectId, model);
		Page<CurrentAccountPrincipalChangeHis> page = currentAccountPrincipalChangeHisService.searchMyPrincipalPageList(accountId, projectId, changeType, pageSearchBean);
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "wdhhs");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='"+Global.getInstance().getFrontRootUrl(request)+"/currentAccount/myCurrentPeanut'>我的活花生</a>");
		return "modules/front/wdzh/current/my_change_into";
	}
	
	/**
	 * 活期产品：转出
	 * @param projectId
	 * @param pageSearchBean
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("myRollOut")
	public String myRollOut(String projectId, PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		//赎回
		String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_REDEEM;
		//myHead信息
		myHeadInfo(accountId, projectId, model);
		Page<CurrentAccountPrincipalChangeHis> page = currentAccountPrincipalChangeHisService.searchMyPrincipalAndInterestPageList(accountId, projectId, changeType, pageSearchBean);
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "wdhhs");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='"+Global.getInstance().getFrontRootUrl(request)+"/currentAccount/myCurrentPeanut'>我的活花生</a>");
		return "modules/front/wdzh/current/my_roll_out";
	}
	
	/**
	 * 活期产品：myHead详情
	 * @param accountId
	 * @param projectId
	 * @param changeType
	 * @param model
	 */
	public void myHeadInfo(long accountId, String projectId, Model model){
		//持有信息
		CurrentProjectHoldInfo holdInfo = currentProjectHoldInfoService.getMyCurrentProjectHoldInfo(accountId, projectId, "");
		if(holdInfo == null){
			holdInfo = new CurrentProjectHoldInfo();
		}
		model.addAttribute("holdInfo", holdInfo);
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST;
		//昨日收益
		Double yesterdayProfit = currentAccountInterestChangeHisService.getYesterdayProfitByAccountIdAndProjectId(accountId, projectId, changeType);
		yesterdayProfit = NumberUtils.formatWithScale(yesterdayProfit,4);
		model.addAttribute("yesterdayProfit", yesterdayProfit);
		//累计收益
		Double sumProfit = currentAccountInterestChangeHisService.getSumProfitByAccountIdAndProjectId(accountId, projectId, changeType);
		sumProfit = NumberUtils.formatWithScale(sumProfit,4); 
		model.addAttribute("sumProfit", sumProfit);
		//每万元日收益
		Double rate = holdInfo.getCurrentProjectInfo()!=null ? holdInfo.getCurrentProjectInfo().getRate() : 0;
		double dayProfit = NumberUtils.formatWithScale(NumberUtils.div(
				NumberUtils.mul(rate, ONE_DAY_TEN_THOUSAND_PROFIT),
				new Double(CurrentProjectConstant.DAYS_IN_YEAR)), 2);
		model.addAttribute("dayProfit", dayProfit);
	}
	
	/**
	 * 跳转到申请赎回本金页面
	 * @param projectId
	 * @param model
	 * @return
	 */
	@RequestMapping("redeemPrincipal")
	public String redeemPrincipal(Long projectId, HttpServletRequest request, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		CurrentProjectInfo currentProjectInfo = currentProjectInfoService.get(String.valueOf(projectId));
		CurrentProjectHoldInfo currentProjectHoldInfo = currentProjectHoldInfoService.getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		model.addAttribute("customerAccount", CustomerUtils.getCustomerAccount());
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		model.addAttribute("currentProjectHoldInfo", currentProjectHoldInfo);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "wdhhs");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='"+Global.getInstance().getFrontRootUrl(request)+"/currentAccount/myCurrentPeanut'>我的活花生</a>");
		
		return "modules/front/wdzh/current/redeemPrincipal";
	}
	
	/**
	 * 申请赎回本金
	 * @param projectId
	 * @param principal
	 * @param model
	 * @return
	 */
	@RequestMapping("applyRedeemPrincipal")
	public String applyRedeemPrincipal(Long projectId, Double principal, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		Map<String,Object> result = currentProjectHoldInfoService.doRedeemPrincipal(accountId, projectId, principal, ProjectConstant.OP_TERM_DICT_PC);
		model.addAttribute("projectId", projectId);
		model.addAttribute("result", result);
		return "modules/front/wdzh/current/result";
	}
	
	/**
	 * 跳转到提取利息页面
	 * @param projectId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pollInterest", method = RequestMethod.GET)
	public String pollInterest(Long projectId, HttpServletRequest request, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		CurrentProjectInfo currentProjectInfo = currentProjectInfoService.get(String.valueOf(projectId));
		CurrentProjectHoldInfo currentProjectHoldInfo = currentProjectHoldInfoService.getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		model.addAttribute("customerAccount", CustomerUtils.getCustomerAccount());
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		model.addAttribute("currentProjectHoldInfo", currentProjectHoldInfo);

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "tzgl");
		model.addAttribute("two_menu", "wdhhs");
		model.addAttribute(
				"nav",
				"<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
						+ Global.getInstance().getFrontRootUrl(request)
						+ "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;投资管理&nbsp;&gt;&nbsp;<a href='"+Global.getInstance().getFrontRootUrl(request)+"/currentAccount/myCurrentPeanut'>我的活花生</a>");
		
		return "modules/front/wdzh/current/pollInterest";
	}
	
	/**
	 * 提取利息
	 * @param projectId
	 * @param interest
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pollInterest", method = RequestMethod.POST)
	public String pollInterest(Long projectId, Double interest, Model model) {
		long accountId = CustomerUtils.getCurrentAccountId();
		Map<String,Object> result = currentProjectHoldInfoService.doPollInterest(accountId, projectId, interest, ProjectConstant.OP_TERM_DICT_PC);
		model.addAttribute("projectId", projectId);
		model.addAttribute("result", result);
		return "modules/front/wdzh/current/result";
	}
}
