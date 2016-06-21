/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.credit.service.CreditBaseInfoService;
import com.thinkgem.jeesite.modules.entity.CreditBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectDateNode;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectShowTerm;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectShowTermService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 借贷合同管理Controller
 * @author yangtao
 * @version 2015-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectBaseInfo")
public class ProjectBaseInfoController extends BaseController {

	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectExecuteSnapshotService projectExecuteSnapshotService;
	@Autowired
	private ProjectShowTermService projectShowTermService;
	@Autowired
	private CreditBaseInfoService creditBaseInfoService;
	
	
	@Autowired
	private SystemService systemService;

	@Autowired
	private AreaService areaService;
	
	
	@ModelAttribute
	public ProjectBaseInfo get(@RequestParam(required=false) String id) {
		ProjectBaseInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectBaseInfoService.get(id);
		}
		if (entity == null){
			entity = new ProjectBaseInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = {"projectDateNodeDetail"})
	public String projectDateNodeDetail(ProjectDateNode projectDateNode, HttpServletRequest request, HttpServletResponse response, Model model){
		projectDateNode = projectBaseInfoService.getProjectDateNodeByProjectId(projectDateNode.getQueryProjectId()==null?null:projectDateNode.getQueryProjectId().toString());
		model.addAttribute("model", projectDateNode);
		return "modules/project/projectDateNodeDetail";
	}
	
	@RequiresPermissions("project:projectBaseInfo:create")
	@RequestMapping(value = {"createlist", ""})
	public String createlist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectBaseInfo> page = projectBaseInfoService.findCreatePage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoCreateList";
	}
	@RequiresPermissions("project:projectBaseInfo:review")
	@RequestMapping(value = {"reviewlist", ""})
	public String reviewlist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectBaseInfo> page = projectBaseInfoService.findReviewPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoReviewList";
	}
	
	@RequiresPermissions("project:projectBaseInfo:loan")
	@RequestMapping(value = {"loanlist", ""})
	public String loanlist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectBaseInfo> page = projectBaseInfoService.findLoanPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo);
		for(ProjectBaseInfo info: page.getList()){
			if(info.getIsAutoRepay() == null){
				info.setIsAutoRepay(ProjectConstant.DICT_DEFAULT_VALUE);
			}
		}
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoLoanList";
	}
	
	/**
	 * 查看放款信息
	 * @param projectBaseInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = "loanform")
	public String loadform(ProjectBaseInfo projectBaseInfo, Model model,HttpServletRequest request) {
		String isAutoRepay = projectBaseInfo.getIsAutoRepay();
		if(isAutoRepay == null || "".equals(isAutoRepay)){
			projectBaseInfo.setIsAutoRepay("0");
		}
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		ProjectExecuteSnapshot projectExecuteSnapshot = projectExecuteSnapshotService.getByProjectId(projectBaseInfo.getProjectId());
		if(projectExecuteSnapshot == null){
			projectExecuteSnapshot = new ProjectExecuteSnapshot();
		}
		model.addAttribute("projectExecuteSnapshot", projectExecuteSnapshot);
		Double sumPlatformAmount = projectExecuteSnapshot.getSumPlatformAmount();
		sumPlatformAmount = sumPlatformAmount == null ? 0 : sumPlatformAmount;
		Double sumTicketMoney = projectExecuteSnapshot.getSumTicketMoney();
		sumTicketMoney = sumTicketMoney == null ? 0 : sumTicketMoney;
		Double stayPaidAmount = LoanUtil.formatAmount(sumPlatformAmount + sumTicketMoney);
		//平台账户余额足够支付额
		model.addAttribute("stayPaidAmount", stayPaidAmount);
		return "modules/project/projectBaseInfoLoanForm";
	}
	
	/**
	 * 确认放款
	 * @param projectBaseInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("project:projectBaseInfo:loan")
	@RequestMapping(value = "loan")
	public String loansave(ProjectBaseInfo projectBaseInfo, Model model, RedirectAttributes redirectAttributes) {
		//确认放款操作
		try {
			projectBaseInfoService.updateProjectIsLoan(NumberUtils.toLong(projectBaseInfo.getProjectId(), 0L), "1");
		} catch (Exception e) {
			addMessage(redirectAttributes, "确认放款失败");
			return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/loanlist";		
			}
		addMessage(redirectAttributes, "确认放款成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/loanlist";
	}
	
	@RequiresPermissions("project:projectBaseInfo:clear")
	@RequestMapping(value = {"clearlist", ""})
	public String clearlist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectBaseInfo> page = projectBaseInfoService.findClearPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoClearList";
	}
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = {"querylist", ""})
	public String querylist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectBaseInfo> page = projectBaseInfoService.findPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoQueryList";
	}
	
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = {"queryAlreadyLoanlist"})
	public String queryAlreadyLoanlist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Map<String,Object>> page = projectBaseInfoService.findAlreadyLoanPage(new Page<Map<String,Object>>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoQueryAlreadyLoanList";
	}
	
	
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = "form")
	public String form(ProjectBaseInfo projectBaseInfo, Model model) {
		List<ProjectShowTerm> projectShowTermList = projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId());
		List<String> showTermList = new ArrayList<String>();
		for(ProjectShowTerm projectShowTerm : projectShowTermList) {
			showTermList.add(projectShowTerm.getTermCode());
		}
		projectBaseInfo.setShowTermList(showTermList);
		//根据项目审核人id查询审核人名称
		Long reviewUserId=projectBaseInfo.getReviewUserId();
		User user=systemService.getReviewNameByReviewUserId(reviewUserId);
		String  reviewUserName = user!=null ? user.getName() : "";
		model.addAttribute("reviewUserName", reviewUserName);
		//根据项目关闭人id查询关闭人名称
		Long closeUserId=projectBaseInfo.getCloseUserId();
		if(closeUserId!=null){
		User users=systemService.getCloseNameByCloseUserId(closeUserId);
		String  closeUserName =user!=null ? users.getName() : "";
		model.addAttribute("closeUserName", closeUserName);
		}
		CreditBaseInfo creditBaseInfo = creditBaseInfoService.get(String.valueOf(projectBaseInfo.getCreditId()));
		creditBaseInfo = creditBaseInfo !=null ? creditBaseInfo : new CreditBaseInfo();
		projectBaseInfo.setCreditName(creditBaseInfo.getCreditName());
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		return "modules/project/projectBaseInfoForm";
	}

	@RequiresPermissions("project:projectBaseInfo:create")
	@RequestMapping(value = "createform")
	public String createform(ProjectBaseInfo projectBaseInfo, Model model) {
		List<ProjectShowTerm> projectShowTermList = projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId());
		List<String> showTermList = new ArrayList<String>();
		for(ProjectShowTerm projectShowTerm : projectShowTermList) {
			showTermList.add(projectShowTerm.getTermCode());
		}
		projectBaseInfo.setShowTermList(showTermList);
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		return "modules/project/projectBaseInfoCreateForm";
	}
	@RequiresPermissions("project:projectBaseInfo:review")
	@RequestMapping(value = "reviewform")
	public String reviewform(ProjectBaseInfo projectBaseInfo, Model model) {
		List<ProjectShowTerm> projectShowTermList = projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId());
		List<String> showTermList = new ArrayList<String>();
		for(ProjectShowTerm projectShowTerm : projectShowTermList) {
			showTermList.add(projectShowTerm.getTermCode());
		}
		CreditBaseInfo creditBaseInfo = creditBaseInfoService.get(String.valueOf(projectBaseInfo.getCreditId()));
		creditBaseInfo = creditBaseInfo !=null ? creditBaseInfo : new CreditBaseInfo();
		projectBaseInfo.setShowTermList(showTermList);
		projectBaseInfo.setCreditName(creditBaseInfo.getCreditName());
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		return "modules/project/projectBaseInfoReviewForm";
	}
	@RequiresPermissions("project:projectBaseInfo:clear")
	@RequestMapping(value = "clearform")
	public String clearform(ProjectBaseInfo projectBaseInfo, Model model) {
		List<ProjectShowTerm> projectShowTermList = projectShowTermService.findListByProjectId(projectBaseInfo.getProjectId());
		List<String> showTermList = new ArrayList<String>();
		for(ProjectShowTerm projectShowTerm : projectShowTermList) {
			showTermList.add(projectShowTerm.getTermCode());
		}
		projectBaseInfo.setShowTermList(showTermList);
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		return "modules/project/projectBaseInfoClearForm";
	}
	
	@RequiresPermissions("project:projectBaseInfo:create")
	@RequestMapping(value = "create")
	public String create(ProjectBaseInfo projectBaseInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectBaseInfo)){
			return createform(projectBaseInfo, model);
		}
		projectBaseInfo.setCreateUserId(new Long(UserUtils.getUser().getId()));
		projectBaseInfo.setCreateDt(new Date());
		projectBaseInfo.setIsLoan(ProjectConstant.DICT_DEFAULT_VALUE);
		Date lastRepaymentDate = DateUtils.addDays(projectBaseInfo.getBiddingDeadline(), projectBaseInfo.getProjectDuration().intValue());
		if(ProjectConstant.PROJECT_DURATION_TYPE_MONTHLY.equals(projectBaseInfo.getDurationType())){
			lastRepaymentDate = DateUtils.addMonths(projectBaseInfo.getBiddingDeadline(), projectBaseInfo.getProjectDuration().intValue());
		}
		projectBaseInfo.setLastRepaymentDate(lastRepaymentDate);
		if (projectBaseInfo.getRiskInfo()!=null){
			projectBaseInfo.setRiskInfo(StringEscapeUtils.unescapeHtml4(projectBaseInfo.getRiskInfo()));
		}
		projectBaseInfoService.save(projectBaseInfo);
		if((projectBaseInfo.getProjectStatus().equals(ProjectConstant.PROJECT_STATUS_CREATE))){
			addMessage(redirectAttributes, "保存借贷产品成功");
		}else{
			addMessage(redirectAttributes, "保存借贷产品并提交审批成功");
		}
		return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/createlist";
	}
	
	@RequiresPermissions("project:projectBaseInfo:create")
	@RequestMapping(value = "copy")
	public String copy(ProjectBaseInfo projectBaseInfo, Model model, RedirectAttributes redirectAttributes) {
		projectBaseInfo = this.get(projectBaseInfo.getProjectId());
		projectBaseInfoService.copy(projectBaseInfo);
		addMessage(redirectAttributes, "复制借贷产品成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/createlist";
	}
	
	@RequiresPermissions("project:projectBaseInfo:create")
	@RequestMapping(value = "submit")
	public String submit(ProjectBaseInfo projectBaseInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectBaseInfo)){
			return createform(projectBaseInfo, model);
		}
		ProjectBaseInfo thePbi=projectBaseInfoService.get(projectBaseInfo.getId());
		if(!thePbi.getProjectStatus().equals(ProjectConstant.PROJECT_STATUS_CREATE)){
			addMessage(redirectAttributes, "当前状态不能提交审批");
			return createform(projectBaseInfo, model);
		}
		projectBaseInfo.setCreateUserId(new Long(UserUtils.getUser().getId()));
		projectBaseInfo.setCreateDt(new Date());
		projectBaseInfo.setProjectStatus(ProjectConstant.PROJECT_STATUS_CREATE);
		projectBaseInfoService.submit(projectBaseInfo.getId());
		addMessage(redirectAttributes, "提交借贷产品审批成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/createlist";
	}
	@RequiresPermissions("project:projectBaseInfo:review")
	@RequestMapping(value = "review")
	public String reviewsave(ProjectBaseInfo projectBaseInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectBaseInfo)){
			return reviewform(projectBaseInfo, model);
		}
		String userId=UserUtils.getUser().getId();
		projectBaseInfo.setReviewDt(new Date());
		projectBaseInfo.setReviewUserId(new Long(userId));
		if (projectBaseInfo.getRiskInfo()!=null){
			projectBaseInfo.setRiskInfo(StringEscapeUtils.unescapeHtml4(projectBaseInfo.getRiskInfo()));
		}
		
		try{
			projectBaseInfoService.review(projectBaseInfo);
			addMessage(redirectAttributes, "审批借贷产品成功");
		}
		catch(ServiceException e){
			addMessage(redirectAttributes, "审批借贷产品失败："+e.getMessage());
		}
		
		
		return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/reviewlist";
	}
	@RequiresPermissions("project:projectBaseInfo:review")
	@RequestMapping(value = "clear")
	public String clear(ProjectBaseInfo projectBaseInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectBaseInfo)){
			return createform(projectBaseInfo, model);
		}
		projectBaseInfoService.save(projectBaseInfo);
		addMessage(redirectAttributes, "审批借贷产品成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/clearlist";
	}
	
	@RequiresPermissions("project:projectBaseInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectBaseInfo projectBaseInfo, RedirectAttributes redirectAttributes) {
		projectBaseInfoService.delete(projectBaseInfo);
		addMessage(redirectAttributes, "删除借贷产品成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/?repage";
	}
	
	@RequiresPermissions("project:projectBaseInfo:create")
	@RequestMapping(value = "checkDuplicate")
	@ResponseBody
	public boolean checkDuplicate(String projectId,String projectCode,String projectName){
		ProjectBaseInfo pbi=new ProjectBaseInfo();
		pbi.setProjectId(projectId);
		pbi.setProjectCode(projectCode);
		pbi.setProjectName(projectName);
		int ret=projectBaseInfoService.checkDuplicate(pbi);
		if(ret>0){
			return false;
		}else{
			return true;
		}
	}
	
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = {"borrowerslist", ""})
	public String borrowerslist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectBaseInfo.setCustomerAccountId(request.getParameter("accountId"));
		Page<ProjectBaseInfo> page = projectBaseInfoService.findCustomerAsBorrowerPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoCustomerBorrowList";
	}
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = {"investmentlist", ""})
	public String investmentlist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectBaseInfo.setCustomerAccountId(request.getParameter("accountId"));
		Page<ProjectBaseInfo> page = projectBaseInfoService.findCustomerAsInvestmentPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoCustomerInvestmentList";
	}
	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = {"transferlist", ""})
	public String transferlist(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectBaseInfo.setCustomerAccountId(request.getParameter("accountId"));
		Page<ProjectBaseInfo> page = projectBaseInfoService.findCustomerAsTransferPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectCustomerTransferInfoList";
	}
	
	@RequiresPermissions("project:projectBaseInfo:transferRecommend")
	@RequestMapping(value = {"transferrecommend", ""})
	public String transferRecommend(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectBaseInfo> page = projectBaseInfoService.findCustomerAsTransferPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectCustomerTransferInfoRecommend";
	}

	@RequiresPermissions("project:projectBaseInfo:view")
	@RequestMapping(value = "frontSortManager")
	public String frontSortManager(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectBaseInfo> page = projectBaseInfoService.findSortedPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoFrontSortManager";
	}

	@RequiresPermissions("project:projectBaseInfo:create")
	@RequestMapping(value = "changeSortInList")
	public String changeSortInList(int projectId, int sort, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectBaseInfoService.updateSortInList(projectId, sort);
		ProjectBaseInfo projectBaseInfo = new ProjectBaseInfo();
		Page<ProjectBaseInfo> page = projectBaseInfoService.findSortedPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoFrontSortManager";
	}

	@RequiresPermissions("project:projectBaseInfo:create")
	@RequestMapping(value = "changeSortInIndex")
	public String changeSortInIndex(int projectId, int sort, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectBaseInfoService.updateSortInIndex(projectId, sort);
		ProjectBaseInfo projectBaseInfo = new ProjectBaseInfo();
		Page<ProjectBaseInfo> page = projectBaseInfoService.findSortedPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoFrontSortManager";
	}
	
	
	
	
	
	
	
	
	/**
	 * 项目下线列表
	 * @param projectBaseInfo
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("project:projectBaseInfo:downline")
	@RequestMapping(value = {"downlineList"})
	public String downlineList(ProjectBaseInfo projectBaseInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//		if(!"1".equals(UserUtils.getUser().getId())){
//			projectBaseInfo.setCreateUserId(new Long(UserUtils.getUser().getId()));
//		}
		
		projectBaseInfo.setProjectStatus(ProjectConstant.PROJECT_STATUS_REVIEW_PASS);
		projectBaseInfo.setBeginPublishDt(new Date());
		Page<ProjectBaseInfo> page = projectBaseInfoService.findPage(new Page<ProjectBaseInfo>(request, response), projectBaseInfo); 
		model.addAttribute("page", page);
		return "modules/project/projectBaseInfoDownlineList";
	}
	
	
	@RequiresPermissions("project:projectBaseInfo:downline")
	@RequestMapping(value = "downline")
	public String downline(ProjectBaseInfo projectBaseInfo, RedirectAttributes redirectAttributes) {
		projectBaseInfo = projectBaseInfoService.get(projectBaseInfo.getProjectId());
		projectBaseInfo.setProjectStatus(ProjectConstant.PROJECT_STATUS_CANCEL);
		projectBaseInfoService.save(projectBaseInfo);
		addMessage(redirectAttributes, "下线借贷产品成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectBaseInfo/downlineList?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> list = areaService.findAll();
		for (int i=0; i<list.size(); i++){
			Area e = list.get(i);
			if(!"4".equals(e.getType())) {
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("name", e.getName());
					mapList.add(map);
				}
			}
		}
		return mapList;
	}
}