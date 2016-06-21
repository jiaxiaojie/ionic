/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
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
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.toAuthorizeAutoRepayment.ToAuthorizeAutoRepaymentReq;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.handler.CurrentYeepayHander;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.current.service.investment.CurrentInvestmentServiceImpl;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.hsbank.util.type.NumberUtil;

/**
 * 活期项目信息Controller
 * @author ydt
 * @version 2015-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/current/currentProjectInfo")
public class CurrentProjectInfoController extends BaseController {

	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CurrentProjectExecuteSnapshotService currentProjectExecuteSnapshotService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	LogThirdPartyService logThirdPartyService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	@Autowired
	private CurrentInvestmentServiceImpl currentInvestmentServiceImpl;
	
	@ModelAttribute
	public CurrentProjectInfo get(@RequestParam(required=false) String id) {
		CurrentProjectInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = currentProjectInfoService.get(id);
		}
		if (entity == null){
			entity = new CurrentProjectInfo();
		}
		return entity;
	}
	
	@ResponseBody
	@RequiresPermissions("current:currentProjectInfo:view")
	@RequestMapping(value = "queryById")
	public CurrentProjectInfo queryById(String id){
		return get(id);
	}
	
	@RequiresPermissions("current:currentProjectInfo:view")
	@RequestMapping(value = "query")
	@ResponseBody
	public List<CurrentProjectInfo> querySimpleList(String queryParas) {
		CurrentProjectInfo qa=new CurrentProjectInfo();
		if(queryParas==null){
			return new ArrayList<CurrentProjectInfo>();
		}
		queryParas=queryParas.trim();
		qa.setQueryParas(queryParas);
		return currentProjectInfoService.querySimpleList(qa);
	}
	
	@RequiresPermissions("current:currentProjectInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(CurrentProjectInfo currentProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CurrentProjectInfo> page = currentProjectInfoService.findPage(new Page<CurrentProjectInfo>(request, response), currentProjectInfo); 
		for(CurrentProjectInfo cInfo : page.getList()){
			String isAuthorizeShow = "0";
			List<CurrentAccountPrincipalChangeHis> investList = currentAccountPrincipalChangeHisService.getPrincipalChangeHisList(cInfo.getId(), CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL);
			if(investList !=null && investList.size() > 0){
				isAuthorizeShow = "1";
			}
			cInfo.setIsAuthorizeShow(isAuthorizeShow);
		}
		model.addAttribute("page", page);
		return "modules/current/currentProjectInfoList";
	}
	
	@RequiresPermissions("current:currentProjectInfo:create")
	@RequestMapping(value = {"createList"})
	public String createList(CurrentProjectInfo currentProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		currentProjectInfo.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_CREATED);
		Page<CurrentProjectInfo> page = currentProjectInfoService.findPage(new Page<CurrentProjectInfo>(request, response), currentProjectInfo); //
		model.addAttribute("page", page);
		return "modules/current/currentProjectInfoCreateList";
	}							
	
	
	
	@RequiresPermissions("current:currentProjectInfo:review")
	@RequestMapping(value = {"reviewList"})
	public String reviewList(CurrentProjectInfo currentProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		currentProjectInfo.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_REVIEWING);
		Page<CurrentProjectInfo> page = new Page<CurrentProjectInfo>(request, response);
		
		page = currentProjectInfoService.findPage(page, currentProjectInfo); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectInfoReviewList";
	}
	
	@RequiresPermissions("current:currentProjectInfo:clear")
	@RequestMapping(value = {"clearList"})
	public String clearList(CurrentProjectInfo currentProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		currentProjectInfo.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_TENDER_OVER);
		currentProjectInfo.setWindingUpStatus(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_NO_APPLY);
		Page<CurrentProjectInfo> page = currentProjectInfoService.findPage(new Page<CurrentProjectInfo>(request, response), currentProjectInfo); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectInfoClearList";
	}
	
	
	@RequiresPermissions("current:currentProjectInfo:clearReview")
	@RequestMapping(value = {"clearReviewList"})
	public String clearReviewList(CurrentProjectInfo currentProjectInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		currentProjectInfo.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_TENDER_OVER);
		currentProjectInfo.setWindingUpStatus(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEWING);
		Page<CurrentProjectInfo> page = new Page<CurrentProjectInfo>(request, response);
		page.setOrderBy(" a.winding_up_apply_dt desc ");
		page =  currentProjectInfoService.findPage(page, currentProjectInfo); 
		model.addAttribute("page", page);
		return "modules/current/currentProjectInfoClearReviewList";
	}

	@RequiresPermissions("current:currentProjectInfo:view")
	@RequestMapping(value = "form")
	public String form(CurrentProjectInfo currentProjectInfo, Model model) {
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("projectId", currentProjectInfo.getId());
		model.addAttribute("model", params);
		return "modules/current/currentProjectInfoForm";
	}
	
	@RequiresPermissions("current:currentProjectInfo:create")
	@RequestMapping(value = "createForm")
	public String createForm(CurrentProjectInfo currentProjectInfo, Model model) {
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		return "modules/current/currentProjectInfoCreateForm";
	}
	
	@RequiresPermissions("current:currentProjectInfo:view")
	@RequestMapping(value = "details")
	public String details(CurrentProjectInfo currentProjectInfo,String toUrl, Model model) {
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
		//model.addAttribute("toUrlData", HttpUtil.sendGet(path+toUrl, "utf-8"));
		
		 
		return "modules/current/currentProjectInfoDetails";
	}
	
	@RequiresPermissions("current:currentProjectInfo:review")
	@RequestMapping(value = "reviewForm")
	public String reviewForm(CurrentProjectInfo currentProjectInfo, Model model) {
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		return "modules/current/currentProjectInfoReviewForm";
	}
	
	@RequiresPermissions("current:currentProjectInfo:clear")
	@RequestMapping(value = "clearForm")
	public String clearForm(CurrentProjectInfo currentProjectInfo, Model model) {
		
		List<CurrentProjectExecuteSnapshot> currentProjectExecuteSnapshots = currentProjectExecuteSnapshotService.findList(new CurrentProjectExecuteSnapshot(currentProjectInfo.getId()));
		currentProjectInfo.setSnapshot(Collections3.getFirst(currentProjectExecuteSnapshots));
		
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		return "modules/current/currentProjectInfoClearForm";
	}
	
	@RequiresPermissions("current:currentProjectInfo:clearReview")
	@RequestMapping(value = "clearReviewForm")
	public String clearReviewForm(CurrentProjectInfo currentProjectInfo, Model model) {
		
		List<CurrentProjectExecuteSnapshot> currentProjectExecuteSnapshots = currentProjectExecuteSnapshotService.findList(new CurrentProjectExecuteSnapshot(currentProjectInfo.getId()));
		currentProjectInfo.setSnapshot(Collections3.getFirst(currentProjectExecuteSnapshots));
		
		model.addAttribute("currentProjectInfo", currentProjectInfo);
		return "modules/current/currentProjectInfoClearReviewForm";
	}

	@RequiresPermissions("current:currentProjectInfo:edit")
	@RequestMapping(value = "save")
	public String save(CurrentProjectInfo currentProjectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectInfo)){
			return form(currentProjectInfo, model);
		}
		currentProjectInfoService.save(currentProjectInfo);
		addMessage(redirectAttributes, "保存活期项目信息成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectInfo/?repage";
	}
	
	
	@RequiresPermissions("current:currentProjectInfo:review")
	@RequestMapping(value = "review")
	public String reviewsave(CurrentProjectInfo currentProjectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectInfo)){
			return reviewForm(currentProjectInfo, model);
		}
		
		currentProjectInfoService.review(currentProjectInfo);
		addMessage(redirectAttributes, "审批活期项目成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectInfo/reviewList";
	}
	
	/**
	 * 提交清盘审核
	 * @param currentProjectInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("current:currentProjectInfo:clear")
	@RequestMapping(value = "clear")
	public String clear(CurrentProjectInfo currentProjectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectInfo)){
			return reviewForm(currentProjectInfo, model);
		}
		
		currentProjectInfoService.clear(currentProjectInfo);
		addMessage(redirectAttributes, "申请清盘活期项目成功"); 
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectInfo/clearList";
	}
	
	/**
	 * 审核清盘
	 * @param currentProjectInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("current:currentProjectInfo:clearReview")
	@RequestMapping(value = "clearReview")
	public String clearReview(CurrentProjectInfo currentProjectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectInfo)){
			return reviewForm(currentProjectInfo, model);
		}
		
		currentProjectInfoService.clearReview(currentProjectInfo);
		addMessage(redirectAttributes, "审批清盘活期项目成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectInfo/clearReviewList";
	}
	
	
	/**
	 * 创建（保存）活期产品
	 * @param currentProjectInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("current:currentProjectInfo:create")
	@RequestMapping(value = "create")
	public String create(CurrentProjectInfo currentProjectInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, currentProjectInfo)){
			return form(currentProjectInfo, model);
		}
		
		//设置基础数据
		currentProjectInfo.setCreateUserId(new Long(UserUtils.getUser().getId()));
		currentProjectInfo.setCreateDt(new Date());
		if (currentProjectInfo.getRiskInfo()!=null){
			currentProjectInfo.setRiskInfo(StringEscapeUtils.unescapeHtml4(currentProjectInfo.getRiskInfo()));
		}
		currentProjectInfo.setWindingUpStatus(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_NO_APPLY);
		currentProjectInfo.setIsAutoRepay(CurrentProjectConstant.NO);
		
		//执行保存操作
		currentProjectInfoService.save(currentProjectInfo);
		
		if(CurrentProjectConstant.CURRENT_PROJECT_STATUS_CREATED.equals(currentProjectInfo.getStatus())){
		addMessage(redirectAttributes, "保存活期产品成功");
		}
		else if( CurrentProjectConstant.CURRENT_PROJECT_STATUS_REVIEWING.equals(currentProjectInfo.getStatus()) )
		{
			addMessage(redirectAttributes, "保存活期产品并提交审批成功");
		}
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectInfo/createList";
	}
	
	/**
	 * 克隆活期产品
	 * @param currentProjectInfo
	 * @param model
	 * @param redirectAttributes
	 * @author 万端瑞
	 * @return
	 */
	@RequiresPermissions("current:currentProjectInfo:create")
	@RequestMapping(value = "cloneForm")
	public String cloneForm(CurrentProjectInfo currentProjectInfo, Model model, RedirectAttributes redirectAttributes) {
		CurrentProjectInfo clone =  currentProjectInfoService.get(currentProjectInfo.getId());
		clone.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_CREATED);
		clone.setId(null);
		clone.setName(clone.getName()+"(克隆)");
		clone.setCode(clone.getCode()+"(clone)");
		model.addAttribute("currentProjectInfo", clone);
		return "modules/current/currentProjectInfoCreateForm";
	}
	
	@RequiresPermissions("current:currentProjectInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(CurrentProjectInfo currentProjectInfo, RedirectAttributes redirectAttributes) {
		currentProjectInfoService.delete(currentProjectInfo);
		addMessage(redirectAttributes, "删除活期项目信息成功");
		return "redirect:"+Global.getAdminPath()+"/current/currentProjectInfo/?repage";
	}
	
	@ResponseBody
	@RequiresPermissions("current:currentProjectInfo:create")
	@RequestMapping(value = "checkCode")
	public String checkCode(String code,String id) {
		if (code !=null) {
			CurrentProjectInfo currentProjectInfo = new CurrentProjectInfo();
			currentProjectInfo.setCode(code);
			currentProjectInfo = currentProjectInfoService.getCurrentProjectInfoByInfo(currentProjectInfo);	
			if(currentProjectInfo == null){
				return "true";
			}
			else if(id != null && id.equals(currentProjectInfo.getId())){
				return "true";
			}
			
		}
		return "false";
	}
	
	@ResponseBody
	@RequiresPermissions("current:currentProjectInfo:create")
	@RequestMapping(value = "checkName")
	public String checkName(String name,String id) {
		if (name !=null) {
			CurrentProjectInfo currentProjectInfo = new CurrentProjectInfo();
			currentProjectInfo.setName(name);
			currentProjectInfo = currentProjectInfoService.getCurrentProjectInfoByInfo(currentProjectInfo);	
			if(currentProjectInfo == null){
				return "true";
			}
			else if(id != null && id.equals(currentProjectInfo.getId())){
				return "true";
			}
			
		}
		return "false";
	}
	
	/**
	 * 自动还款授权
	 * @param projectId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("current:currentProjectInfo:authorize")
	@RequestMapping(value = "authorizeCurrentAutoRepaymentDo")
	public String authorizeCurrentAutoRepayment(String id, HttpServletRequest request, HttpServletResponse response, Model model){
		CurrentProjectInfo cInfo = this.get(id);
		CustomerAccount  customerAccount = customerAccountService.get(String.valueOf(cInfo.getBorrowerAccountId()));
		String platformUserNo = customerAccount.getPlatformUserNo();
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_REQ, platformUserNo);
		ToAuthorizeAutoRepaymentReq toAuthorizeAutoRepaymentReq = new ToAuthorizeAutoRepaymentReq();
		toAuthorizeAutoRepaymentReq.setRequestNo(requestNo);
		toAuthorizeAutoRepaymentReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		toAuthorizeAutoRepaymentReq.setPlatformUserNo(platformUserNo);
		toAuthorizeAutoRepaymentReq.setOrderNo(YeepayConstant.YEEPAY_CURRENT_TENDERORDERNO_PREFIX + id);
		toAuthorizeAutoRepaymentReq.setCallbackUrl(YeepayConstant.YEEPAY_BACKSTAGE_CALLBACK_URL_PREFIX + "toAuthorizeCurrentAutoRepayment?requestNo=" + requestNo);
		toAuthorizeAutoRepaymentReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toAuthorizeCurrentAutoRepayment");
		String req = toAuthorizeAutoRepaymentReq.toReq();
		String sign = SignUtil.sign(req);
		model.addAttribute("cInfo", cInfo);
		model.addAttribute("customerAccount", customerAccount);
		model.addAttribute("req", req);
		model.addAttribute("sign", sign);
		model.addAttribute("requestNo", requestNo);
		model.addAttribute("authorizeAutoRepaymentUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOAUTHORIZEAUTOREPAYMENT_ACTION);
		//记录日志
		logThirdPartyService.insertAuthorizeautoRepaymentReq(requestNo, req);
		return "modules/current/currentAutoRepaymentConfirm";
	}
	
	/**
	 * 活期产品：购买
	 * @param id
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("current:currentProjectInfo:authorize")
	@RequestMapping(value = "currentProjectBuyDetail")
	public String currentProjectBuyDetail(String id, HttpServletRequest request, HttpServletResponse response, Model model){
		CurrentProjectInfo cInfo = this.get(id);
		model.addAttribute("cInfo", cInfo);
		return "modules/current/currentProjectBuyDetail";
	}
	
	@RequiresPermissions("current:currentProjectInfo:authorize")
	@RequestMapping(value = "currentProjectBuyConfirm")
	public String currentProjectBuyConfirm(String id, HttpServletRequest request, HttpServletResponse response, Model model){
		//投资金额
		Double amount = NumberUtil.toDouble(request.getParameter("amount"), 0.00);
		String mobile  = String.valueOf(request.getParameter("mobile"));
		CurrentProjectInfo cInfo = this.get(id);
		
		CurrentProjectExecuteSnapshot executeSnapshot = currentProjectExecuteSnapshotService.getByProjectId(NumberUtil.toLong(id, 0L));
		cInfo.setSnapshot(executeSnapshot);
		model.addAttribute("cInfo", cInfo);
		CustomerBase customerBase = customerBaseService.getByMobile(mobile);
		if(customerBase == null){
			model.addAttribute("description", "该手机号对应的账号不存在");
			return "modules/current/currentProjectBuyException";
		}
		Long accountId = customerBase.getAccountId();
		//会员基本信息
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		String projectName = StringUtils.replaceSpecialStr(cInfo.getName());
		String projectCode = StringUtils.replaceSpecialStr(cInfo.getCode());
		if(projectCode != null && projectCode.length() > 100 ){
			projectCode = projectCode.substring(1,100);
		}
		
		String platformUserNo = customerAccount.getPlatformUserNo();
		//融资人
		Long loanAccountId = cInfo.getBorrowerAccountId();
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
			currentInvestmentServiceImpl.beforeCurrentInvest(cInfo, ProjectConstant.OP_TERM_DICT_PC, requestNo, accountId, financingAmount, false);;
		} catch (Exception e) {
			model.addAttribute("description", e.getMessage());
			return "modules/current/currentProjectBuyException";
		}
		//生成调用易宝投标接口数据
		String req = CurrentYeepayHander.generationCurrentXml_for_tender(projectName, id, ProjectConstant.OP_TERM_DICT_PC, projectCode, requestNo, platformUserNo, borrowerPlatformUserNo, cInfo.getFinanceMoney(), serviceCharge, financingAmount);
		//签名
		String sign = SignUtil.sign(req);
		//请求易宝接口的日志
		logThirdPartyService.insertToCpTransaction(YeepayConstant.BIZ_TYPE_TENDER, requestNo, req);
		model.addAttribute("yeepayURL",YeepayConstant.YEEPAY_GATE_URL_PREFIX + "toCpTransaction");
		model.addAttribute("req",req);
		model.addAttribute("sign",sign);
		
		return "modules/current/currentProjectBuyConfirm";
	}
}