package com.thinkgem.jeesite.modules.front;

import com.hsbank.util.http.HttpRequestUtil;
import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.loan.util.bean.RateTicketBean;
import com.thinkgem.jeesite.common.loan.util.bean.RepaymentPlanItem;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.modules.cms.service.ArticleDataService;
import com.thinkgem.jeesite.modules.customer.service.*;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.front.ProjectSearchBean;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.bean.InvestAmountBean;
import com.thinkgem.jeesite.modules.project.service.*;
import com.thinkgem.jeesite.modules.project.service.assignment.AssignmentService;
import com.thinkgem.jeesite.modules.project.service.investment.InvestmentService;
import com.thinkgem.jeesite.modules.project.service.util.handler.RepaymentPlanHandler;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "${frontPath}/")
public class ProjectController extends BaseController {
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ArticleDataService ariticleDataService;
	@Autowired
	private ProjectRepaymentPlanService projectRepaymentPlanService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private InvestmentService investmentService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private ProjectExecuteSnapshotService executeSnapshotService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private CustomerInvestmentTicketService investmentTicketService;
	@Autowired
	private ProjectFactorCarFlowService projectFactorCarFlowService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private ProjectShowTermService projectShowTermService;
	@Autowired
	private CustomerRateTicketService customerRateTicketService;
	
	/**
	 * 我要投资
	 * 可以查询项目
	 * @param status 查询字段【项目状态】
	 * @param duration 查询字段【项目期限】
	 * @param rate 查询字段【项目收益】
	 * @param repaymentMode 查询字段【还款方式】
	 * @param projectType 查询字段【项目类型】
	 * @param pageNo 分页【页码】
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "wytz")
	public String searchProject(ProjectSearchBean projectSearchBean, String pageNo, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectSearchBean.setDefaultSearch();
		Page<ProjectBaseInfo> page = projectBaseInfoService.searchPage(projectSearchBean,pageNo);
		for(ProjectBaseInfo projectBaseInfo : page.getList()) {
			List<String> showTermList = new ArrayList<String>();
			for(ProjectShowTerm projectShowTerm : projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId())) {
				showTermList.add(projectShowTerm.getTermCode());
			}
			projectBaseInfo.setShowTermList(showTermList);
		}
		//项目列表
		model.addAttribute("page", page);
		//查询信息
		model.addAttribute("projectSearchBean", projectSearchBean);
		//融资项目数量
		int onlineProjectCount = projectBaseInfoService.getOnlineProjectCount();
		model.addAttribute("onlineProjectCount", onlineProjectCount);
		//投标中项目数量
		int tenderingProjectCount = projectBaseInfoService.getTenderingProjectCount();
		model.addAttribute("tenderingProjectCount", tenderingProjectCount);
		//投标完成项目数量
		int tenderedProjectCount = projectBaseInfoService.getTenderedProjectCount();
		model.addAttribute("tenderedProjectCount", tenderedProjectCount);
		//还款中项目数量
		int repaymentingProjectCount = projectBaseInfoService.getRepaymentingProjectCount();
		model.addAttribute("repaymentingProjectCount", repaymentingProjectCount);
		//还款完成项目数量
		int repaymentedProjectCount = projectBaseInfoService.getRepaymentedProjectCount();
		model.addAttribute("repaymentedProjectCount", repaymentedProjectCount);
		model.addAttribute("menu", "wytz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;项目列表");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		return "modules/front/wytz";
	}
	
	/**
	 * 项目详情
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project_detail")
	public String project_detail(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectBaseInfo = projectBaseInfoService.get(projectBaseInfo);
		String[] canVisitProjectStatuses = {"3", "4", "5", "6"};
		if(!Arrays.asList(canVisitProjectStatuses).contains(projectBaseInfo.getProjectStatus())) {
			throw new ServiceException("can not visit this status project.");
		}
		//获取默认风控信息，若项目风控信息为空则设为默认风控信息
		if("".equals(StringUtil.dealString(projectBaseInfo.getRiskInfo()))) {
			String defaultRiskInfo = ariticleDataService.get(ProjectConstant.DEFAULT_RISK_INFO_ID).getContent();
			projectBaseInfo.setRiskInfo(defaultRiskInfo);
		}
		if(projectBaseInfo.getMaxAmount() == null) {
			projectBaseInfo.setMaxAmount(ProjectConstant.PROJECT_MAX_AMOUNT_DEFAULT);
		}
		//项目显示终端信息
		List<String> showTermList = new ArrayList<String>();
		for(ProjectShowTerm projectShowTerm : projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId())) {
			showTermList.add(projectShowTerm.getTermCode());
		}
		projectBaseInfo.setShowTermList(showTermList);
		//项目信息
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		//项目执行快照信息
		ProjectExecuteSnapshot projectExecuteSnapshot = executeSnapshotService.getByProjectId(projectBaseInfo.getProjectId());
		model.addAttribute("projectExecuteSnapshot", projectExecuteSnapshot);
		//还款计划
		List<ProjectRepaymentPlan> projectRepaymentPlanList = projectRepaymentPlanService.findListByProjectId(projectBaseInfo.getProjectId());
		model.addAttribute("projectRepaymentPlanList", projectRepaymentPlanList);
		//投资列表
		List<ProjectInvestmentRecord> projectInvestmentRecordList = projectInvestmentRecordService.findListByProjectIdAndStatuses(projectBaseInfo.getProjectId());
		for(ProjectInvestmentRecord projectInvestmentRecord : projectInvestmentRecordList) {
			projectInvestmentRecord.getCa().setAccountName(StringUtils.vagueName(projectInvestmentRecord.getCa().getAccountName()));
		}
		model.addAttribute("projectInvestmentRecordList", projectInvestmentRecordList);
		//项目历程信息
		ProjectDateNode projectDateNode = projectBaseInfoService.getProjectDateNodeByProjectId(projectBaseInfo.getProjectId());
		model.addAttribute("projectDateNode", projectDateNode);
		
		//剩余天、时、分
		long distanceMillis = projectBaseInfo.getBiddingDeadline().getTime() - new Date().getTime();
		long remainDay = distanceMillis / (1000 * 60 * 60 * 24);
		long remainHour = (distanceMillis - remainDay * (1000 * 60 * 60 * 24))/(1000 * 60 * 60);
		long remainMinute = (distanceMillis - remainDay * (1000 * 60 * 60 * 24) - remainHour * (1000 * 60 * 60))/(1000 * 60);
		model.addAttribute("remainDay", remainDay);
		model.addAttribute("remainHour", remainHour);
		model.addAttribute("remainMinute", remainMinute);
		
		//下一还款日
		Date nextReapyDay = projectRepaymentPlanService.getNextReapyDay(projectBaseInfo.getProjectId());
		model.addAttribute("nextRepayDay", nextReapyDay);
		
		//融资方姓名
		Long borrowersId = projectBaseInfo.getAgentUser();
		if(borrowersId == null || borrowersId == 0) {
			borrowersId = projectBaseInfo.getBorrowersUser();
		}
		String borrowersName = customerBaseService.getCustomerNameByAccountId(borrowersId);
		model.addAttribute("borrowersName", borrowersName);
		//车辆抵押贷的车辆信息
		ProjectFactorCarFlow projectFactorCarFlow = projectFactorCarFlowService.getByProject(projectBaseInfo);
		model.addAttribute("projectFactorCarFlow", projectFactorCarFlow);
		//登录账户
		Long accountId = 0L;
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal != null ) {
			accountId = principal.getAccountId();
			CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
			model.addAttribute("customerBalance", customerBalance);
		}
		model.addAttribute("accountId",accountId);
		model.addAttribute("isNewUser",projectBaseInfo.getIsNewUser()==null ? "1" : projectBaseInfo.getIsNewUser());
		model.addAttribute("menu", "wytz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;<a href='#'>项目详情</a>");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		return "modules/front/wytz/project_detail";
	}
	
	/**
	 * 投资提交
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project_confirm")
	public String project_confirm(HttpServletRequest request, HttpServletResponse response, Model model) {
		String projectId = HttpRequestUtil.getInstance(request).getParameter("project_id");
		ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(projectId);
		//项目显示终端信息
		List<String> showTermList = new ArrayList<String>();
		for(ProjectShowTerm projectShowTerm : projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId())) {
			showTermList.add(projectShowTerm.getTermCode());
		}
		//若此项目的显示信息不包括pc端，则抛出异常
		if(!showTermList.contains(ProjectConstant.OP_TERM_DICT_PC)) {
			throw new ServiceException("cannot buy this project on pc.");
		}
		ProjectExecuteSnapshot pesInvest = executeSnapshotService.getByProjectId(projectBaseInfo.getProjectId());
		projectBaseInfo.setPes(pesInvest);
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		//会员基本信息
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		//投资金额
		Double amount = NumberUtil.toDouble(request.getParameter("amount"), 0.00);
		//优惠券Ids
		String ticketIds = request.getParameter("ticketIds");
		//加息券ids
		String rateTicketIds = StringUtil.dealString(request.getParameter("rateTicketIds"));
		//计息开始日期
		Date beginInterestDate = DateUtils.dateFormate(new Date());
		//优惠券金额
		Double ticketAmount = NumberUtil.toDouble(request.getParameter("ticketAmount"), 0.00);
		//平台垫付金额
		Double platformAmount = NumberUtil.toDouble(request.getParameter("platformAmount"), 0.00);
		//实际支付金额
		Double actualAmount = LoanUtil.formatAmount(amount - ticketAmount - platformAmount);
		String type = StringUtil.dealString(request.getParameter("type"));
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
		//项目信息
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		model.addAttribute("amount", amount);
		model.addAttribute("type", type);
		String borrowerPlatformUserNo = customerAccountService.get(loanAccountId).getPlatformUserNo();
		if (ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT.equals(type)) {
			String[] canVisitProjectStatuses = {"3", "4", "5", "6"};
			if(!Arrays.asList(canVisitProjectStatuses).contains(projectBaseInfo.getProjectStatus())) {
				throw new ServiceException("can not buy this status project.");
			}
			//直投
			//给平台服务费
			Double serviceCharge = ProjectUtil.getServiceCharge(actualAmount, projectBaseInfo.getServiceCharge(), projectBaseInfo.getServiceChargeTypeCode(), projectBaseInfo.getPes().getSumServiceCharge());
			//给融资方的金额
			Double financingAmount = actualAmount > serviceCharge ? actualAmount - serviceCharge : 0;
			financingAmount = LoanUtil.formatAmount(financingAmount);
			//投资交易第三方流水编号
			String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_TENDER_REQ,customerAccount.getPlatformUserNo());
			//投资前置服务
			try {
				investmentService.beforeInvest(projectBaseInfo, ProjectConstant.OP_TERM_DICT_PC, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds, beginInterestDate);
			} catch (Exception e) {
				model.addAttribute("description", e.getMessage());
				return "modules/front/wytz/project_exception";
			}
			//生成调用易宝投标接口数据
			String req = YeepayUtils.generationXml_for_tender(projectName, projectId, ProjectConstant.OP_TERM_DICT_PC, projectCode, requestNo, platformUserNo, borrowerPlatformUserNo, projectBaseInfo.getFinanceMoney(), serviceCharge, financingAmount);
			//签名
			String sign = SignUtil.sign(req);
			//请求易宝接口的日志
			logThirdPartyService.insertToCpTransaction(YeepayConstant.BIZ_TYPE_TENDER, requestNo, req);
			model.addAttribute("yeepayURL",YeepayConstant.YEEPAY_GATE_URL_PREFIX + "toCpTransaction");
			model.addAttribute("req",req);
			model.addAttribute("sign",sign);
			model.addAttribute("actualAmount", actualAmount);
		} else if (ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT.equals(type)) {
			//债权转让
			//投资交易第三方流水编号
			String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOCPTRANSACTION_CREDIT_ASSIGNMENT_REQ,customerAccount.getPlatformUserNo());
			//转让项目流水号
			String transferProjectId = StringUtil.dealString(request.getParameter("transfer_project_id"));
			ProjectTransferInfo projectTransferInfo = projectTransferInfoService.get(transferProjectId);
			if(!ProjectConstant.PROJECT_STATUS_REPAYMENTING.equals(projectBaseInfo.getProjectStatus()) && !ProjectConstant.PROJECT_TRANSFER_STATUS_RUNNING.equals(projectTransferInfo.getStatus())) {
				throw new ServiceException("can not visit this status project.");
			}
			//投资记录
			Long investmentRecordId = projectTransferInfo.getInvestmentRecordId();
			ProjectInvestmentRecord pir = projectInvestmentRecordService.get(String.valueOf(investmentRecordId));
			projectTransferInfo.setPir(pir);
			//执行快照
			ProjectExecuteSnapshot pesAssign = executeSnapshotService.getByProjectIdAndTransferId(projectId, transferProjectId);
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
			//债权转让前置服务
			try {
				assignmentService.beforeAssign(projectTransferInfo, ProjectConstant.OP_TERM_DICT_PC, requestNo, accountId, ticketIds, amount, ticketAmount, platformAmount, rateTicketIds, beginInterestDate);
			} catch (Exception e) {
				model.addAttribute("description", e.getMessage());
				return "modules/front/wytz/project_exception";
			}
			//原投资记录里的投资人
			//融资人
			Long oldInvestmentUserId = pir.getInvestmentUserId();
			String oldInvestmentUserAccountId = customerAccountService.get(oldInvestmentUserId).getPlatformUserNo();
			//生成调用易宝投标接口数据
			String req = YeepayUtils.generationXml_for_assignment(projectId, ProjectConstant.OP_TERM_DICT_PC, requestNo, platformUserNo, oldInvestmentUserAccountId, borrowerPlatformUserNo, sumServiceCharge, financingAmount, pir.getThirdPartyOrder());
			//签名
			String sign = SignUtil.sign(req);
			//请求易宝接口的日志
			logThirdPartyService.insertToCpTransaction(YeepayConstant.BIZ_TYPE_CREDIT_ASSIGNMENT, requestNo, req);
			model.addAttribute("yeepayURL",YeepayConstant.YEEPAY_GATE_URL_PREFIX + "toCpTransaction");
			model.addAttribute("req",req);
			model.addAttribute("sign",sign);
			model.addAttribute("actualAmount", LoanUtil.formatAmount(actualAmount + downServiceCharge));
		}
		return "modules/front/wytz/project_confirm";
	}
		
	/**
	 * 投资确认
	 * @param projectBaseInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "project_buy")
	public String project_buy(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectBaseInfo = projectBaseInfoService.get(projectBaseInfo);String[] canVisitProjectStatuses = {"3", "4", "5", "6"};
		if(!Arrays.asList(canVisitProjectStatuses).contains(projectBaseInfo.getProjectStatus())) {
			throw new ServiceException("can not buy this status project.");
		}
		//项目显示终端信息
		List<String> showTermList = new ArrayList<String>();
		for(ProjectShowTerm projectShowTerm : projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId())) {
			showTermList.add(projectShowTerm.getTermCode());
		}
		//若此项目的显示信息不包括pc端，则抛出异常
		if(!showTermList.contains(ProjectConstant.OP_TERM_DICT_PC)) {
			throw new ServiceException("cannot buy this project on pc.");
		}
		Double amount = LoanUtil.formatAmount(NumberUtil.toDouble(request.getParameter("amount"), 0.00));
		//计息开始日期
		Date beginInterestDate = DateUtils.dateFormate(new Date());
		//项目信息
		model.addAttribute("projectBaseInfo", projectBaseInfo);
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
		//还款计划
		List<RepaymentPlanItem> repaymentPlan = repaymentPlanHandler.generateForInvestment(projectBaseInfo, amount, "");
		model.addAttribute("repaymentPlan", repaymentPlan);
		//可用优惠券列表
		List<CustomerInvestmentTicket> investmentTicketList = investmentTicketService.findCanUseListByAccountIdAndAmount(accountId, amount);
		model.addAttribute("investmentTicketList", investmentTicketList);
		model.addAttribute("investmentTicketCount", investmentTicketList.size());
		//可用加息券列表
		List<CustomerRateTicket> rateTicketList = customerRateTicketService.getRateTicketList(accountId, ProjectConstant.TICKET_DICT_NORMAL);
		model.addAttribute("rateTicketList", rateTicketList);
		model.addAttribute("rateTicketCount", rateTicketList.size());
		Double maxTicketAmount = LoanUtil.formatAmount(amount * ProjectConfig.getInstance().getTicketAmountRate());
		model.addAttribute("maxTicketAmount", maxTicketAmount);
		//投资金额
		model.addAttribute("amount", amount);
		//类型：直投、债权转让
		model.addAttribute("type", StringUtil.dealString(request.getParameter("type")));
		//如果是债权转让，则需要记录转让项目Id
		String transferProjectId = StringUtil.dealString(request.getParameter("transferProjectId"));
		if ("".equals(transferProjectId)) {
			//直投
			model.addAttribute("investment_type", ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT);
		} else {
			//债权转让
			model.addAttribute("investment_type", ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT);
		}
		model.addAttribute("transferProjectId", transferProjectId);
		//加息券列表
		List<IncreaseInterestItem> interestItems = new ArrayList<IncreaseInterestItem>();
		Double willProfit = ProjectUtil.calculateWillProfit(interestItems, projectBaseInfo, amount, "", beginInterestDate);
		model.addAttribute("willProfit", LoanUtil.formatAmount(willProfit));
		model.addAttribute("menu", "wytz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/wytz'>我要投资</a>&nbsp;&gt;&nbsp;<a href='#'>投资确认</a>");
		return "modules/front/wytz/project_buy";
	}
	
	/**
	 * 计算某次投资可使用的平台垫付金额
	 * @param amount
	 * @param sumTicketAmount
	 * @param userPlatformAmount
	 * @return
	 */
	@RequestMapping(value = "calculate_invest_amout")
	@ResponseBody
	public InvestAmountBean calculateInvestAmout(String investmentType, Double amount, Double sumTicketAmount, boolean usePlatformAmount) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal!=null){
		Long accountId = principal.getAccountId();
		CustomerBalance customerBalance = customerBalanceService.get(String.valueOf(accountId));
		return ProjectUtil.calculateInvestAmout(investmentType, amount, sumTicketAmount, usePlatformAmount, customerBalance);
		}else{
			InvestAmountBean nullBean=new InvestAmountBean();
			nullBean.setAmount(new Double("0"));
			nullBean.setSumTicketAmount(new Double("0"));
			nullBean.setPlatformAmount(new Double("0"));
			nullBean.setPayableAmount(new Double("0"));
			nullBean.setDownServiceCharge(new Double("0"));
			nullBean.setBalance (new Double("0"));
			nullBean.setDifferenceAmount (new Double("0"));
			return nullBean;
		}
	}
	
	/**
	 * 
	 * @param rateTicketId
	 * @return
	 */
	@RequestMapping(value = "getRateTicketInfo")
	@ResponseBody
	public RateTicketBean getRateTicketInfo(String rateTicketId) {
		CustomerRateTicket rateTicket = customerRateTicketService.get(rateTicketId);
		RateTicketBean ticketBean = new RateTicketBean();
		ticketBean.setId(rateTicket.getId());
		ticketBean.setInvalidDt(DateUtils.formatDate(rateTicket.getInvalidDt(),"yyyy-MM-dd"));
		ticketBean.setRateDuration(rateTicket.getRateTicketType().getRateDuration());
		ticketBean.setUseDescription(rateTicket.getRateTicketType().getUseDescription());
		ticketBean.setRate(rateTicket.getRateTicketType().getRate() * 100);
		ticketBean.setMaxAmount(rateTicket.getRateTicketType().getMaxAmount());;
		return ticketBean;
	}
		
}
