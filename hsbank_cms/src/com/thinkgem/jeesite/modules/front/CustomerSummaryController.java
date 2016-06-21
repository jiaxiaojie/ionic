/**
 * 
 */
package com.thinkgem.jeesite.modules.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountSummaryService;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentSplitRecordService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 账户总览
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer")
public class CustomerSummaryController extends BaseController {
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private ProjectRepaymentSplitRecordService projectRepaymentSplitRecordService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	@Autowired
	private CurrentAccountSummaryService currentAccountSummaryService;
	
	/**
	 * 总览
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "summary")
	public String summary(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		model.addAttribute("customerAccount", customerAccount);
		//获取账号安全级别分数，满分100分
		int safeScore = customerAccount.getSafeScore();
		model.addAttribute("safeScore",safeScore);
		//获取当前账号余额信息
		CustomerBalance customerBalance=customerBalanceService.get(accountId.longValue()+"");
		if(customerBalance==null){
			customerBalanceService.createInitCustomerBalance(accountId);
			customerBalance=customerBalanceService.get(accountId.longValue()+"");
		}
		model.addAttribute("customerBalance", customerBalance);
		//获取当前账号进行中的投资项目的投资收益总值
		Float sumProfitInProject =projectRepaymentSplitRecordService.getSumProfitInRunningProjectByAccountId(accountId);
		if(sumProfitInProject==null){
			sumProfitInProject=new Float("0");
		}
		model.addAttribute("sumProfitInProject", sumProfitInProject);
		//获取当前账号进行中的投资项目列表
		List<ProjectInvestmentRecord> investmentProjectList= projectInvestmentRecordService.findInvestmentRunningProjectListByAccountId(accountId.longValue()+"");
		model.addAttribute("investmentProjectList", investmentProjectList);
		CurrentAccountSummary summary = currentAccountSummaryService.getByAccountId(accountId);
		Double willPrincipal = NumberUtils.formatWithScale(customerBalance.getWillPrincipal() + summary.getCurrentPrincipal(),2);
		model.addAttribute("willPrincipal", willPrincipal);
		//我的活花生
		List<CurrentAccountPrincipalChangeHis> myPeanutList = currentAccountPrincipalChangeHisService.getMyPeanutList(accountId, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL, 10);
		model.addAttribute("myPeanutList", myPeanutList);
		//获取当前账号进行中的借贷项目尚未还的借款总值
		Float sumLoanInProject=projectRepaymentSplitRecordService.getSumLoanInRunningProjectByAccountId(accountId);
		if(sumLoanInProject==null){
			sumLoanInProject=new Float("0");
		}
		model.addAttribute("sumLoanInProject", sumLoanInProject);
		//获取当前账号进行中的借贷项目列表
		List<ProjectRepaymentSplitRecord> loanProjectList=projectRepaymentSplitRecordService.getLoanListInRunningProjectByAccountId(accountId);
		model.addAttribute("loanProjectList", loanProjectList);
		//获取针对当前账号的推荐项目列表
		List<ProjectBaseInfo> recommendProjectList=projectBaseInfoService.findRecommendProjectList();
		model.addAttribute("recommendProjectList", recommendProjectList);
		//头像
		model.addAttribute("avatar_image", customerAccount.getAvatarImage());
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zhzl");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>账户总览</a>");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		return "modules/front/wdzh/zhzl";
	}
}
