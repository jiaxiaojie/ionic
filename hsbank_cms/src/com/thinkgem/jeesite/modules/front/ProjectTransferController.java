package com.thinkgem.jeesite.modules.front;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.modules.cms.service.ArticleDataService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectDateNode;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectFactorCarFlow;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;
import com.thinkgem.jeesite.modules.entity.ProjectShowTerm;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.entity.front.ProjectTransferSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConfig;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectFactorCarFlowService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentPlanService;
import com.thinkgem.jeesite.modules.project.service.ProjectShowTermService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

@Controller
@RequestMapping(value = "${frontPath}/projectTransfer")
public class ProjectTransferController extends BaseController {
	//默认风控信息文章Id
	public static String DEFAULT_RISK_INFO_ID = "5df72c7559c848fb91edfd344674bd27";
	public static String platformNo = YeepayConstant.YEEPAY_PLATFORM_NO;
	
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private ArticleDataService ariticleDataService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectExecuteSnapshotService projectExecuteSnapshotService;
	@Autowired
	private ProjectRepaymentPlanService projectRepaymentPlanService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private ProjectFactorCarFlowService projectFactorCarFlowService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private ProjectShowTermService projectShowTermService;
	
	/**
	 * 我要投资
	 * 可以查询项目
	 * @param projecttransferSearchBean 查询Bean
	 * @param pageNo 分页【页码】
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String searchProject(ProjectTransferSearchBean projecttransferSearchBean, String pageNo, HttpServletRequest request, HttpServletResponse response, Model model) {
		projecttransferSearchBean.setDefaultSearch();
		Page<ProjectTransferInfo> page = projectTransferInfoService.searchPage(projecttransferSearchBean,pageNo,ProjectConstant.FRONT_PAGE_SIZE);
		for(ProjectTransferInfo projectTransferInfo : page.getList()) {
			List<String> showTermList = new ArrayList<String>();
			for(ProjectShowTerm projectShowTerm : projectShowTermService.findListByProjectId(projectTransferInfo.getProjectId() + "")) {
				showTermList.add(projectShowTerm.getTermCode());
			}
			projectTransferInfo.getProjectBaseInfo().setShowTermList(showTermList);
			//剩余期限
			int remainingTime = DateUtils.getDistanceMonthOfTwoDate(new Date(), projectTransferInfo.getProjectBaseInfo().getLastRepaymentDate());
			remainingTime = remainingTime < 0 ? 0 : remainingTime;
			projectTransferInfo.getProjectBaseInfo().getPes().setRemainingTime(remainingTime);
		}
		//项目列表
		model.addAttribute("page", page);
		//查询信息
		model.addAttribute("SearchBean", projecttransferSearchBean);
		
		model.addAttribute("menu", "wytz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/projectTransfer/list'>我要投资</a>&nbsp;&gt;&nbsp;项目列表");
		return "modules/front/zqzr";
	}
	
	/**
	 * 债权转让详情
	 * @param projectTransferInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail")
	public String project_detail(ProjectTransferInfo projectTransferInfo, HttpServletRequest request, Model model) {
		//转让信息
		projectTransferInfo = projectTransferInfoService.get(projectTransferInfo);
		model.addAttribute("projectTransferInfo", projectTransferInfo);
		//关联的项目信息
		Long projectId = projectTransferInfoService.get(projectTransferInfo).getProjectId();
		ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(projectId + "");
		if(!ProjectConstant.PROJECT_STATUS_REPAYMENTING.equals(projectBaseInfo.getProjectStatus()) && !ProjectConstant.PROJECT_TRANSFER_STATUS_RUNNING.equals(projectTransferInfo.getStatus())) {
			throw new ServiceException("can not visit this status project.");
		}
		//获取默认风控信息，若项目风控信息为空则设为默认风控信息
		if(StringUtils.isBlank(projectBaseInfo.getRiskInfo())) {
			String defaultRiskInfo = ariticleDataService.get(DEFAULT_RISK_INFO_ID).getContent();
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
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		//转让项目执行快照
		ProjectExecuteSnapshot projectExecuteSnapshot = projectExecuteSnapshotService.getTransferSnapshot(projectId, projectTransferInfo.getTransferProjectId());
		//剩余期限
		int remainingTime = DateUtils.getDistanceMonthOfTwoDate(new Date(), projectBaseInfo.getLastRepaymentDate());
		remainingTime = remainingTime < 0 ? 0 : remainingTime;
		projectExecuteSnapshot.setRemainingTime(remainingTime);
		model.addAttribute("projectExecuteSnapshot", projectExecuteSnapshot);
		
		//剩余天、时、分
		long distanceMillis = projectTransferInfo.getDiscountDate().getTime() - new Date().getTime();
		long remainDay = distanceMillis / (1000 * 60 * 60 * 24);
		long remainHour = (distanceMillis - remainDay * (1000 * 60 * 60 * 24))/(1000 * 60 * 60);
		long remainMinute = (distanceMillis - remainDay * (1000 * 60 * 60 * 24) - remainHour * (1000 * 60 * 60))/(1000 * 60);
		model.addAttribute("remainDay", remainDay);
		model.addAttribute("remainHour", remainHour);
		model.addAttribute("remainMinute", remainMinute);
		
		//成交用时（单位：天）
		int finishDays = (int)DateUtils.getDistanceOfTwoDate(projectTransferInfo.getCreateDate(), projectTransferInfo.getCloseDate() == null ? projectTransferInfo.getDiscountDate() : projectTransferInfo.getCloseDate());
		model.addAttribute("finishDays", finishDays);

		//融资方姓名
		String borrowersName = customerBaseService.getCustomerNameByAccountId(projectBaseInfo.getBorrowersUser());
		model.addAttribute("borrowersName", borrowersName);
		//车辆抵押贷的车辆信息
		ProjectFactorCarFlow projectFactorCarFlow = projectFactorCarFlowService.getByProject(projectBaseInfo);
		model.addAttribute("projectFactorCarFlow", projectFactorCarFlow);
		
		//转入费率（百分比）
		double joinRate = ProjectConfig.getInstance().getAssignmentFeeRateDown() * 100;
		model.addAttribute("joinRate", joinRate);
		
		//每万元收益
		double profit = 100.0;
		model.addAttribute("profit", profit);
		
		//项目历程信息
		ProjectDateNode projectDateNode = projectBaseInfoService.getProjectDateNodeByProjectId(projectBaseInfo.getProjectId());
		model.addAttribute("projectDateNode", projectDateNode);
		//还款计划
		List<ProjectRepaymentPlan> projectRepaymentPlanList = projectRepaymentPlanService.findListByProjectId(projectBaseInfo.getProjectId());
		model.addAttribute("projectRepaymentPlanList", projectRepaymentPlanList);
		//投资列表
		List<ProjectInvestmentRecord> projectInvestmentRecordList = projectInvestmentRecordService.findListByProjectId(projectBaseInfo.getProjectId());
		for(ProjectInvestmentRecord projectInvestmentRecord : projectInvestmentRecordList) {
			projectInvestmentRecord.getCa().setAccountName(StringUtils.vagueName(projectInvestmentRecord.getCa().getAccountName()));
		}
		model.addAttribute("projectInvestmentRecordList", projectInvestmentRecordList);
		
		//债权投资列表
		List<ProjectInvestmentRecord> transferProjectInvestmentRecordList = projectInvestmentRecordService.findListByProjectIdAndTransferProjectId(projectBaseInfo.getProjectId(), projectTransferInfo.getTransferProjectId(),"0");
		for(ProjectInvestmentRecord projectInvestmentRecord : transferProjectInvestmentRecordList) {
			projectInvestmentRecord.getCa().setAccountName(StringUtils.vagueName(projectInvestmentRecord.getCa().getAccountName()));
		}
		
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal != null ) {
			//登录账户余额信息
			CustomerBalance customerBalance = customerBalanceService.get(principal.getAccountId() + "");
			model.addAttribute("customerBalance", customerBalance);
		}
		
		model.addAttribute("transferProjectInvestmentRecordList", transferProjectInvestmentRecordList);
		
		model.addAttribute("menu", "wytz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/projectTransfer/list'>我要投资</a>&nbsp;&gt;&nbsp;项目列表");
		return "modules/front/zqzr/transferProject_detail";
	}
}
