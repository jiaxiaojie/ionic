/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceHisService;
import com.thinkgem.jeesite.modules.customer.service.CustomerDoubleElevenActivityService;
import com.thinkgem.jeesite.modules.customer.service.CustomerInvestmentTicketService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.CustomerDoubleElevenActivity;
import com.thinkgem.jeesite.modules.entity.LogThirdParty;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;

/**
 * 双11活动手动送现金Controller
 * @author lzb
 * @version 2015-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerDoubleElevenActivity")
public class CustomerDoubleElevenActivityController extends BaseController {

	@Autowired
	private CustomerDoubleElevenActivityService customerDoubleElevenActivityService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private CustomerBalanceHisService customerBalanceHisService;
	@Autowired
	private CustomerInvestmentTicketService customerInvestmentTicketService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	
	@ModelAttribute
	public CustomerDoubleElevenActivity get(@RequestParam(required=false) String id) {
		CustomerDoubleElevenActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = customerDoubleElevenActivityService.get(id);
		}
		if (entity == null){
			entity = new CustomerDoubleElevenActivity();
		}
		return entity;
	}
	
	@RequiresPermissions("customer:customerDoubleElevenActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(CustomerDoubleElevenActivity customerDoubleElevenActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerDoubleElevenActivity> page = customerDoubleElevenActivityService.findPage(new Page<CustomerDoubleElevenActivity>(request, response), customerDoubleElevenActivity); 
		model.addAttribute("page", page);
		model.addAttribute("message", request.getParameter("message"));
		return "modules/customer/customerDoubleElevenActivityList";
	}

	@RequiresPermissions("customer:customerDoubleElevenActivity:view")
	@RequestMapping(value = "form")
	public String form(CustomerDoubleElevenActivity customerDoubleElevenActivity, Model model) {
		model.addAttribute("customerDoubleElevenActivity", customerDoubleElevenActivity);
		return "modules/customer/customerDoubleElevenActivityForm";
	}

	@RequiresPermissions("customer:customerDoubleElevenActivity:edit")
	@RequestMapping(value = "save")
	public String save(CustomerDoubleElevenActivity customerDoubleElevenActivity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, customerDoubleElevenActivity)){
			return form(customerDoubleElevenActivity, model);
		}
		customerDoubleElevenActivityService.save(customerDoubleElevenActivity);
		addMessage(redirectAttributes, "保存双11活动手动送现金成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerDoubleElevenActivity/?repage";
	}
	
	@RequiresPermissions("customer:customerDoubleElevenActivity:edit")
	@RequestMapping(value = "give")
	public String give(String id, Model model, RedirectAttributes redirectAttributes) {
		try{
			customerDoubleElevenActivityService.give(id);
			addMessage(redirectAttributes, "双11活动手动送现金成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerDoubleElevenActivity/list";
	}
	
	@RequiresPermissions("customer:customerDoubleElevenActivity:edit")
	@RequestMapping(value = "delete")
	public String delete(CustomerDoubleElevenActivity customerDoubleElevenActivity, RedirectAttributes redirectAttributes) {
		customerDoubleElevenActivityService.delete(customerDoubleElevenActivity);
		addMessage(redirectAttributes, "删除双11活动手动送现金成功");
		return "redirect:"+Global.getAdminPath()+"/customer/customerDoubleElevenActivity/?repage";
	}
	
	/**
	 * 补送奖励
	 * @param customerDoubleElevenActivity
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("customer:customerDoubleElevenActivity:edit")
	@RequestMapping(value = "patchAward")
	public String patchAward(String mobile, Model model, RedirectAttributes redirectAttributes) {
		boolean isShowOpenThirdAccountPatchTicketToCustomer = false;
		boolean isShowOpenThirdAccountPatchTicketToCustomerAndRecommender = false;
		boolean isShowOpenThirdAccountPatchAmountToCustomer = false;
		boolean isShowOpenThirdAccountPatchAmountToCustomerAndRecommender = false;
		boolean isShowFirstInvestmentPatchTicketToCustomer = false;
		
		CustomerAccount customerAccount = customerAccountService.getByMobile(mobile);

		if(StringUtils.isNotBlank(mobile) && customerAccount != null) {
			LogThirdParty logThirdParty = logThirdPartyService.getByRequestNo(customerAccount.getRequestNo());
			long getOpenThirdAccountGiveTicketCount = customerInvestmentTicketService.getTicketCountByAccountIdAndGetRemark(customerAccount.getAccountId(), "新用户注册并完成实名认证");
			if(ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount()) && getOpenThirdAccountGiveTicketCount <= 0) {
				isShowOpenThirdAccountPatchTicketToCustomer = true;
				isShowOpenThirdAccountPatchTicketToCustomerAndRecommender = true;
			}
			List<CustomerBalanceHis> customerBalanceHisList = customerBalanceHisService.getListByAccountIdAndChangeValAndChangeReason(customerAccount.getAccountId(), 1.88, "新用户注册并完成实名认证");
			if(ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount()) && (customerBalanceHisList == null || customerBalanceHisList.size() == 0)) {
				isShowOpenThirdAccountPatchAmountToCustomer = true;
				isShowOpenThirdAccountPatchAmountToCustomerAndRecommender = true;
			}
			ProjectInvestmentRecord firstRecord = projectInvestmentRecordService.getCustomerFirstNormalRecord(customerAccount.getAccountId());
			long firstInvestmentTicketCount = customerInvestmentTicketService.getTicketCountByAccountIdAndGetRemark(customerAccount.getAccountId(), "首次投资");
			if(firstRecord != null && firstInvestmentTicketCount <= 0) {
				isShowFirstInvestmentPatchTicketToCustomer = true;
			}
			model.addAttribute("firstRecord", firstRecord);
			model.addAttribute("openThirdAccountDt", logThirdParty == null ? "" : logThirdParty.getNotifyDt());
			model.addAttribute("customerAccount", customerAccount);
		}
		
		model.addAttribute("isShowOpenThirdAccountPatchTicketToCustomer", isShowOpenThirdAccountPatchTicketToCustomer);
		model.addAttribute("isShowOpenThirdAccountPatchTicketToCustomerAndRecommender", isShowOpenThirdAccountPatchTicketToCustomerAndRecommender);
		model.addAttribute("isShowOpenThirdAccountPatchAmountToCustomer", isShowOpenThirdAccountPatchAmountToCustomer);
		model.addAttribute("isShowOpenThirdAccountPatchAmountToCustomerAndRecommender", isShowOpenThirdAccountPatchAmountToCustomerAndRecommender);
		model.addAttribute("isShowFirstInvestmentPatchTicketToCustomer", isShowFirstInvestmentPatchTicketToCustomer);
		
		model.addAttribute("mobile", mobile);
		
		return "modules/customer/customerDoubleElevenActivityPatchAward";
	}
	
	@RequestMapping("openThirdAccountPatchTicketToCustomer")
	public String openThirdAccountPatchTicketToCustomer(String mobile, Model model, RedirectAttributes redirectAttributes) {
		CustomerAccount customerAccount = customerAccountService.getByMobile(mobile);
		String getRemark = "新用户注册并完成实名认证";
		if(customerAccount != null) {
			long getOpenThirdAccountGiveTicketCount = customerInvestmentTicketService.getTicketCountByAccountIdAndGetRemark(customerAccount.getAccountId(), "新用户注册并完成实名认证");
			if(ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount()) && getOpenThirdAccountGiveTicketCount <= 0) {
				int[] denominations = {5, 5, 10};
				customerInvestmentTicketService.giveCustomerTickets(customerAccount.getAccountId(), denominations, getRemark);
				addMessage(redirectAttributes, "赠送优惠券成功");
			} else {
				addMessage(redirectAttributes, "赠送优惠券失败：已赠送，不可再送");
			}
		} else {
			addMessage(redirectAttributes, "赠送优惠券失败：用户不存在");
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerDoubleElevenActivity/patchAward?mobile=" + mobile;
	}
	
	@RequestMapping("openThirdAccountPatchTicketToCustomerAndRecommender")
	public String openThirdAccountPatchTicketToCustomerAndRecommender(String mobile, Model model, RedirectAttributes redirectAttributes) {
		CustomerAccount customerAccount = customerAccountService.getByMobile(mobile);
		String getRemark = "新用户注册并完成实名认证";
		if(customerAccount != null) {
			long getOpenThirdAccountGiveTicketCount = customerInvestmentTicketService.getTicketCountByAccountIdAndGetRemark(customerAccount.getAccountId(), "新用户注册并完成实名认证");
			if(ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount()) && getOpenThirdAccountGiveTicketCount <= 0) {
				int[] denominations = {5, 5, 10};
				customerInvestmentTicketService.giveCustomerTickets(customerAccount.getAccountId(), denominations, getRemark);
				if(ProjectConstant.RECOMMENDER_TYPE_NORMAL.equals(customerAccount.getRecommenderType())) {
					CustomerAccount recommender = customerAccountService.getByMobile(customerAccount.getRecommenderMobile());
					if(recommender != null) {
						customerInvestmentTicketService.giveCustomerTickets(recommender.getAccountId(), new int[]{10, 10}, "邀请好友赠送");
					}
				}
				addMessage(redirectAttributes, "赠送成功");
			} else {
				addMessage(redirectAttributes, "赠送失败");
			}
		} else {
			addMessage(redirectAttributes, "赠送失败：用户不存在");
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerDoubleElevenActivity/patchAward?mobile=" + mobile;
	}

	@RequestMapping("openThirdAccountPatchAmountToCustomer")
	public String openThirdAccountPatchAmountToCustomer(String mobile, Model model, RedirectAttributes redirectAttributes) {
		CustomerAccount customerAccount = customerAccountService.getByMobile(mobile);
		double giveAmount = 1.88;
		String getRemark = "新用户注册并完成实名认证";
		if(customerAccount != null) {
			List<CustomerBalanceHis> customerBalanceHisList = customerBalanceHisService.getListByAccountIdAndChangeValAndChangeReason(customerAccount.getAccountId(), 1.88, "新用户注册并完成实名认证");
			if(ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount()) && (customerBalanceHisList == null || customerBalanceHisList.size() == 0)) {
				customerDoubleElevenActivityService.transferToCustomerFromPlatform(customerAccount.getAccountId(), giveAmount, ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, getRemark, "");
				addMessage(redirectAttributes, "赠送成功");
			} else {
				addMessage(redirectAttributes, "赠送成功：已赠送，不可再送");
			}
		} else {
			addMessage(redirectAttributes, "赠送失败：用户不存在");
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerDoubleElevenActivity/patchAward?mobile=" + mobile;
	}

	@RequestMapping("openThirdAccountPatchAmountToCustomerAndRecommender")
	public String openThirdAccountPatchAmountToCustomerAndRecommender(String mobile, Model model, RedirectAttributes redirectAttributes) {
		CustomerAccount customerAccount = customerAccountService.getByMobile(mobile);
		double giveAmount = 1.88;
		String getRemark = "新用户注册并完成实名认证";
		if(customerAccount != null) {
			List<CustomerBalanceHis> customerBalanceHisList = customerBalanceHisService.getListByAccountIdAndChangeValAndChangeReason(customerAccount.getAccountId(), 1.88, "新用户注册并完成实名认证");
			if(ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount()) && (customerBalanceHisList == null || customerBalanceHisList.size() == 0)) {
				customerDoubleElevenActivityService.transferToCustomerFromPlatform(customerAccount.getAccountId(), giveAmount, ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, getRemark, "");
				CustomerAccount recommender = customerAccountService.getByMobile(customerAccount.getRecommenderMobile());
				if(recommender != null) {
					customerDoubleElevenActivityService.transferToCustomerFromPlatform(recommender.getAccountId(), giveAmount, ProjectConstant.CHANGE_TYPE_BALANCE_RECHAGE_RECOMMEND_INVESTMENT, "好友注册奖励", "");
				}
				addMessage(redirectAttributes, "赠送成功");
			} else {
				addMessage(redirectAttributes, "赠送成功：已赠送，不可再送");
			}
		} else {
			addMessage(redirectAttributes, "赠送失败：用户不存在");
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerDoubleElevenActivity/patchAward?mobile=" + mobile;
	}

	@RequestMapping("firstInvestmentPatchTicketToCustomer")
	public String firstInvestmentPatchTicketToCustomer(String mobile, Model model, RedirectAttributes redirectAttributes) {
		CustomerAccount customerAccount = customerAccountService.getByMobile(mobile);
		int[] denominations = {100,50,20,20,10};
		String getRemark = "首次投资";
		if(customerAccount != null) {
			ProjectInvestmentRecord firstRecord = projectInvestmentRecordService.getCustomerFirstNormalRecord(customerAccount.getAccountId());
			long firstInvestmentTicketCount = customerInvestmentTicketService.getTicketCountByAccountIdAndGetRemark(customerAccount.getAccountId(), "首次投资");
			if(firstRecord != null && firstInvestmentTicketCount <= 0) {
				customerInvestmentTicketService.giveCustomerTickets(customerAccount.getAccountId(), denominations, getRemark);
				addMessage(redirectAttributes, "赠送优惠券成功");
			} else {
				addMessage(redirectAttributes, "赠送优惠券失败：已赠送，不可再送");
			}
		} else {
			addMessage(redirectAttributes, "赠送优惠券失败：用户不存在");
		}
		return "redirect:"+Global.getAdminPath()+"/customer/customerDoubleElevenActivity/patchAward?mobile=" + mobile;
	}
}