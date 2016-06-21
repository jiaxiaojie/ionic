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
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoRepayment.ToAuthorizeAutoRepaymentReq;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;
import com.thinkgem.jeesite.modules.entity.ProjectWillLoan;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentPlanService;
import com.thinkgem.jeesite.modules.project.service.ProjectWillLoanService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
 * 融资管理
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/loan")
public class CustomerLoanController extends BaseController {
	@Autowired
	CustomerAccountService customerAccountService;
	@Autowired
	ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	ProjectWillLoanService projectWillLoanService;
	@Autowired
	CustomerBalanceService customerBalanceService;
	@Autowired
	CustomerBaseService customerBaseService;
	@Autowired
	LogThirdPartyService logThirdPartyService;
	@Autowired
	ProjectRepaymentPlanService projectRepaymentPlanService;
	
	/**
	 * 我的融资【募集中】
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "myFundingProject")
	public String myFundingProject(String pageNo, HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获取当前账号基本信息
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		customerBase=customerBase.vague();
		model.addAttribute("customerBase", customerBase);
		// 获取当前账号余额信息
		CustomerBalance customerBalance = customerBalanceService.get(accountId.longValue() + "");
		model.addAttribute("customerBalance", customerBalance);
		//募集中的融资
		Page<ProjectBaseInfo> fundingProjectPage = projectBaseInfoService.findFundingProjectByBorrowerAccountId(accountId, pageNo);
		model.addAttribute("fundingProjectPage", fundingProjectPage);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "rzgl");
		model.addAttribute("two_menu", "wdjk");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;融资管理&nbsp;&gt;&nbsp;<a href='#'>我的融资</a>");
		return "modules/front/wdzh/loan/myFundingProject";
	}
	
	/**
	 * 我的融资【还款中】
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "myRepayingProject")
	public String myRepayingProject(String pageNo, HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获取当前账号余额信息
		CustomerBalance customerBalance = customerBalanceService.get(accountId.longValue() + "");
		model.addAttribute("customerBalance", customerBalance);
		// 获取当前账号基本信息
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		customerBase=customerBase.vague();
		model.addAttribute("customerBase", customerBase);
		// 还款中的融资
		Page<ProjectBaseInfo> repayingProjectPage = projectBaseInfoService.findRepayingProjectByBorrowerAccountId(accountId, pageNo);
		model.addAttribute("repayingProjectPage", repayingProjectPage);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "rzgl");
		model.addAttribute("two_menu", "wdjk");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;融资管理&nbsp;&gt;&nbsp;<a href='#'>我的融资</a>");
		return "modules/front/wdzh/loan/myRepayingProject";
	}
	
	/**
	 * 我的融资【还款结束】
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "myEndedProject")
	public String myEndedProject(String pageNo, HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		// 获取当前账号基本信息
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		customerBase=customerBase.vague();
		model.addAttribute("customerBase", customerBase);
		// 获取当前账号余额信息
		CustomerBalance customerBalance = customerBalanceService.get(accountId.longValue() + "");
		model.addAttribute("customerBalance", customerBalance);
		// 已还清的融资
		Page<ProjectBaseInfo> endedProjectPage = projectBaseInfoService.findEndedProjectByBorrowerAccountId(accountId, pageNo);
		model.addAttribute("endedProjectPage", endedProjectPage);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "rzgl");
		model.addAttribute("two_menu", "wdjk");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;融资管理&nbsp;&gt;&nbsp;<a href='#'>我的融资</a>");
		return "modules/front/wdzh/loan/myEndedProject";
	}

	/**
	 * 自动还款授权签名并跳转到自动还款授权确认页面
	 * 		1.更新projectBase表的autoRepayCode为本次requestNo
	 * 		2.插入logThirdParty表提现记录
	 * @param projectId
	 * @param model
	 * @return
	 */
	@RequestMapping("authorizeAutoRepayment")
	public String authorizeAutoRepayment(String projectId, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		String platformUserNo = customerAccount.getPlatformUserNo();
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_REQ, platformUserNo);
		ToAuthorizeAutoRepaymentReq toAuthorizeAutoRepaymentReq = new ToAuthorizeAutoRepaymentReq();
		toAuthorizeAutoRepaymentReq.setRequestNo(requestNo);
		toAuthorizeAutoRepaymentReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		toAuthorizeAutoRepaymentReq.setPlatformUserNo(platformUserNo);
		toAuthorizeAutoRepaymentReq.setOrderNo(YeepayConstant.YEEPAY_TENDERORDERNO_PREFIX + projectId);
		toAuthorizeAutoRepaymentReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toAuthorizeAutoRepayment?requestNo=" + requestNo);
		toAuthorizeAutoRepaymentReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toAuthorizeAutoRepayment");
		
		projectBaseInfoService.updateAutoRepayCode(projectId, requestNo);
		
		ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(projectId + "");
		
		String req = toAuthorizeAutoRepaymentReq.toReq();
		String sign = SignUtil.sign(req);
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		model.addAttribute("req", req);
		model.addAttribute("sign", sign);
		model.addAttribute("requestNo", requestNo);
		model.addAttribute("authorizeAutoRepaymentUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_ACTION);
		
		logThirdPartyService.insertAuthorizeautoRepaymentReq(requestNo, req);
		
		model.addAttribute("menu", "wdzh");
		return "modules/front/wdzh/loan/authorizeAutoRepaymentConfirm";
	}
	
	/**
	 * 融资申请查询（可以查询到审核中、审核通过、被拒绝）
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "applyForQuery")
	public String applyForQuery(String status, PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		customerBase=customerBase.vague();
		model.addAttribute("customerBase", customerBase);
		//"-1"代表三个状态都可以查询到
		if(status == null) {
			status = "-1";
		}
		Page<ProjectWillLoan> page = projectWillLoanService.findListByAccountIdAndStatus(accountId, status, pageSearchBean);
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("status", status);

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "rzgl");
		model.addAttribute("two_menu", "jksqcx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;融资管理&nbsp;&gt;&nbsp;<a href='#'>融资申请查询</a>");
		return "modules/front/wdzh/loan/applyForQuery";
	}

	/**
	 * 融资统计
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tongji")
	public String tongji(HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		// 获取当前账号基本信息
		model.addAttribute("customerAccount", customerAccount);
		// 获取当前账号余额信息
		CustomerBalance customerBalance = customerBalanceService.get(accountId.longValue() + "");
		model.addAttribute("customerBalance", customerBalance);
		// 还款中的融资
		List<ProjectBaseInfo> runningProject = projectBaseInfoService
				.findMyProjectList(accountId.longValue() + "", "0");
		model.addAttribute("runningProjectCount", runningProject.size());
		// 逾期项目数
		int overdueCount = 0;
		for (ProjectBaseInfo projectBaseInfo : runningProject) {
			// 逾期
			if (projectBaseInfo.getPes() != null && "1".equals(projectBaseInfo.getPes().getStatus())) {
				overdueCount++;
			}
		}
		model.addAttribute("overdueCount", overdueCount);
		// 完成项目数
		List<ProjectBaseInfo> endProject = projectBaseInfoService
				.findMyProjectList(accountId.longValue() + "", "1");
		model.addAttribute("endProjectCount", endProject.size());
		// 提前还款项目数
		int preRepayCount = 0;
		for (ProjectBaseInfo projectBaseInfo : runningProject) {
			// 提前还款
			if (projectBaseInfo.getPes() != null && "2".equals(projectBaseInfo.getPes().getStatus())) {
				preRepayCount++;
			}
		}
		model.addAttribute("preRepayCount", preRepayCount);
		//申请中
		long applyCount = projectWillLoanService.findListByAccountIdAndStatus(accountId, "0", new PageSearchBean()).getCount();
		model.addAttribute("applyCount", applyCount);

		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "rzgl");
		model.addAttribute("two_menu", "jktj");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;融资管理&nbsp;&gt;&nbsp;<a href='#'>融资统计</a>");
		return "modules/front/wdzh/loan/tongji";
	}
	
	/**
	 * 根据projectId查询并返回json格式projectBaseInfo数据字符串
	 * @param projectId
	 * @return
	 */
	@RequestMapping("getProjectBaseInfoByProjectId")
	@ResponseBody
	public String getProjectBaseInfoByProjectId(String projectId) {
		return JsonMapper.toJsonString(projectBaseInfoService.get(projectId));
	}
	
	/**
	 * 根据projectId查询并返回json格式projectRepaymentPlan数据字符串
	 * @param projectId
	 * @return
	 */
	@RequestMapping("getProjectRepaymentPlanListByProjectId")
	@ResponseBody
	public String getProjectRepaymentPlanListByProjectId(String projectId) {
		List<ProjectRepaymentPlan> projectRepaymentPlanList = projectRepaymentPlanService.findListByProjectId(projectId);
		//将status转为对应的DictLabel
		for(ProjectRepaymentPlan projectRepaymentPlan : projectRepaymentPlanList) {
			projectRepaymentPlan.setStatus(DictUtils.getDictLabel(projectRepaymentPlan.getStatus(), "project_repayment_plan_status_dict", ""));
		}
		return JsonMapper.toJsonString(projectRepaymentPlanList);
	}

	/**
	 * 根据id查询并返回json格式projectWillLoan数据字符串
	 * @param id
	 * @return
	 */
	@RequestMapping("getProjectWillLoanById")
	@ResponseBody
	public String getProjectWillLoanById(String id) {
		ProjectWillLoan projectWillLoan = projectWillLoanService.get(id);
		projectWillLoan.setStatus(DictUtils.getDictLabel(projectWillLoan.getStatus(), "project_will_loan_status_dict", ""));
		projectWillLoan.setUseType(DictUtils.getDictLabel(projectWillLoan.getUseType(), "customer_credit_loan_use_dict", ""));
//		projectWillLoan.setCreateCustomer(projectWillLoan.getCreateCustomer().vague());
		return JsonMapper.toJsonString(projectWillLoan);
	}
	
	/**
	 * 取消融资申请
	 */
	@RequestMapping("cancelProjectWillLoan")
	@ResponseBody
	public String cancelProjectWillLoan(String id) {
		projectWillLoanService.cancelLoan(id);
		return "success";
	}
}
