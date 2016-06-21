/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectWillLoan;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectBaseInfoDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectWillLoanDao;
import com.thinkgem.jeesite.modules.project.service.ProjectWillLoanService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 借贷意向管理Controller
 * @author yangtao
 * @version 2015-06-24
 */
@Controller
@RequestMapping(value = "${adminPath}/project/projectWillLoan")
public class ProjectWillLoanController extends BaseController {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private ProjectWillLoanService projectWillLoanService;
	@Autowired
	private ProjectBaseInfoDao projectBaseInfoDao;
	@Autowired
	private ProjectWillLoanDao projectWillLoanDao;
	@Autowired
	private MarketFacadeService marketFacadeService;
	@ModelAttribute
	public ProjectWillLoan get(@RequestParam(required=false) String id) {
		ProjectWillLoan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = projectWillLoanService.get(id);
		}
		if (entity == null){
			entity = new ProjectWillLoan();
		}
		return entity;
	}
	
	@RequiresPermissions("project:projectWillLoan:review")
	@RequestMapping(value = {"reviewlist", ""})
	public String list(ProjectWillLoan projectWillLoan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectWillLoan> page = projectWillLoanService.findPage(new Page<ProjectWillLoan>(request, response), projectWillLoan); 
		model.addAttribute("page", page);
		return "modules/project/projectWillLoanReviewList";
	}
	@RequiresPermissions("project:projectWillLoan:view")
	@RequestMapping(value = {"querylist", ""})
	public String querylist(ProjectWillLoan projectWillLoan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProjectWillLoan> page = projectWillLoanService.findPage(new Page<ProjectWillLoan>(request, response), projectWillLoan); 
		model.addAttribute("page", page);
		return "modules/project/projectWillLoanQueryList";
	}
	

	@RequiresPermissions("project:projectWillLoan:view")
	@RequestMapping(value = "viewform")
	public String viewform(ProjectWillLoan projectWillLoan, Model model) {
		model.addAttribute("projectWillLoan", projectWillLoan);
		return "modules/project/projectWillLoanViewForm";
	}
	
	@RequiresPermissions("project:projectWillLoan:review")
	@RequestMapping(value = "reviewform")
	public String reviewform(ProjectWillLoan projectWillLoan, Model model) {
		model.addAttribute("projectWillLoan", projectWillLoan);
		return "modules/project/projectWillLoanReviewForm";
	}
	
	
	@RequiresPermissions("project:projectWillLoan:edit")
	@RequestMapping(value = "save")
	public String save(ProjectWillLoan projectWillLoan, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (!beanValidator(model, projectWillLoan)){
			return reviewform(projectWillLoan, model);
		}
		projectWillLoan.setCreateUserId(new Long(UserUtils.getUser().getId()));
		projectWillLoan.setCreateDt(new Date());
		projectWillLoan.setCreateIp(request.getHeader("X-Real-IP"));
		projectWillLoan.setStatus(ProjectConstant.PROJECT_WILL_STATUS_WAITTING_REVIEW);
		if(projectWillLoan.getArea().getId().equals("")){
			projectWillLoan.getArea().setId("0");
		}
		projectWillLoanService.save(projectWillLoan);
		addMessage(redirectAttributes, "保存借贷意向成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectWillLoan/mylist";
	}
	
	@RequiresPermissions("project:projectWillLoan:review")
	@RequestMapping(value = "review")
	public String review(ProjectWillLoan projectWillLoan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, projectWillLoan)){
			return reviewform(projectWillLoan, model);
		}
		projectWillLoan.setConfirmUserId(new Long(UserUtils.getUser().getId()));
		projectWillLoanService.save(projectWillLoan);
		//营销活动融资入口
		this.marketFacadeLoanMoney(projectWillLoan);
		addMessage(redirectAttributes, "审批借贷意向成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectWillLoan/reviewlist";
	}
	
	/**
	 * 营销活动融资入口
	 * @param projectWillLoan
	 */
	public void marketFacadeLoanMoney(ProjectWillLoan projectWillLoan){
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, projectWillLoan.getCreateUserId());
		para.put(MarketConstant.AMOUNT_PARA, projectWillLoan.getLoaningMoney());
		para.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
		para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LOAN_MONRY);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ": marketActivity LoanMoney para：【" + para.toString() + "】 start.");
		marketFacadeService.loanMoney(para);
		logger.info("---------" + DateUtils.formatDateTime(new Date())
				+ ": marketActivity LoanMoney para：【" + para.toString() + "】 end.");
	}
	
	@RequiresPermissions("project:projectWillLoan:edit")
	@RequestMapping(value = "delete")
	public String delete(ProjectWillLoan projectWillLoan, RedirectAttributes redirectAttributes) {
		projectWillLoanService.delete(projectWillLoan);
		addMessage(redirectAttributes, "删除借贷意向成功");
		return "redirect:"+Global.getAdminPath()+"/project/projectWillLoan/mylist";
	}
	
	
	@RequiresPermissions("project:projectWillLoan:edit")
	@RequestMapping(value = "copy")
	@ResponseBody
	public Map<String,Object> copy(String willId) {
		Map<String,Object> map=new HashMap<String,Object>();
		ProjectWillLoan projectWillLoan=projectWillLoanService.get(willId);
		ProjectBaseInfo pbi=new ProjectBaseInfo();
		String title=projectWillLoan.getTitle();
		if(title.length()>=40){
			title=title.substring(0,40)+"...";
		}
		pbi.setProjectCode(title+"--"+willId);
		pbi.setIsNewUser("1");
		pbi.setIsRecommend("0");
		pbi.setProjectTypeCode(new Long(2));
		pbi.setProjectName("来自自动转换:"+projectWillLoan.getTitle());
		pbi.setProjectIntroduce(projectWillLoan.getRemark());
		pbi.setRepaymentMode(ProjectConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL_PLUS_INTEREST);
		pbi.setBorrowersUser(projectWillLoan.getCreateUserId());
		pbi.setAgentUser(new Long("0"));
		Area area=new Area();
		area.setId("0");
		pbi.setArea(area);
		pbi.setAnnualizedRate(projectWillLoan.getAnnualizedRate());
		pbi.setProjectDuration(new Long(projectWillLoan.getDuration()));
		pbi.setUseMethod(DictUtils.getDictLabel(projectWillLoan.getUseType(), "customer_credit_loan_use_dict", ""));
		pbi.setFinanceMoney(projectWillLoan.getContractMoney());
		//待补充
		pbi.setRepaymentMoney(projectWillLoan.getSumRepayMoney());
		pbi.setCreditExtensionMoney(projectWillLoan.getContractMoney());
		pbi.setPlannedRepaymentDate(DateUtils.addMonths(DateUtils.dateAddDay(new Date(), 3),1));
		pbi.setLastRepaymentDate(DateUtils.addMonths(DateUtils.dateAddDay(new Date(), 3),1+projectWillLoan.getDuration()));
		pbi.setActualRepaymentDate(null);
		pbi.setBiddingDeadline(DateUtils.dateAddDay(new Date(), 3));
		pbi.setTransferCode(new Long("0"));
		pbi.setCreateDt(new Date());
		pbi.setServiceCharge(projectWillLoan.getServiceCharge());
		pbi.setServiceChargeTypeCode(ProjectConstant.PROJECT_SERVICE_CHARGE_TYPE_BY_RATE);
		pbi.setStartingAmount(new Long("100"));
		pbi.setRiskInfo(null);
		pbi.setAboutFiles(null);
		pbi.setMaxAmount(new Long(ProjectConstant.PROJECT_MAX_AMOUNT_DEFAULT*100));
		pbi.setSafeguardMode(new Long("1"));//本金+利息
		pbi.setIsAutoRepay("");
		pbi.setAutoRepayCode("");
		pbi.setMonthRepayMoney(projectWillLoan.getMonthRepayMoney());
		pbi.setEarlyRepaymentRate(ProjectConstant.PROJECT_EARLY_REPAY_DEFAULT_PAYMENT_RATIO);
		pbi.setCreateUserId(projectWillLoan.getConfirmUserId());
		pbi.setReviewRemark("");
		pbi.setReviewDt(null);
		pbi.setReviewUserId(null);
		pbi.setPublishDt(null);
		pbi.setCloseDt(null);
		pbi.setCloseUserId(null);
		pbi.setProjectStatus(ProjectConstant.PROJECT_STATUS_CREATE);
		pbi.setIsDel("0");
		pbi.setApplySrcId(new Long(projectWillLoan.getId()));
		projectBaseInfoDao.insert(pbi);
		projectWillLoanDao.updateCreateRelProjectFlag(willId);
		map.put("flag", "ok");
		map.put("message", "ok");
		return map;
	}

}