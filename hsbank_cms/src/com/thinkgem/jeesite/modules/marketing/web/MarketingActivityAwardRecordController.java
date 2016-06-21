/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralSnapshotService;
import com.thinkgem.jeesite.modules.customer.service.CustomerInvestmentTicketService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.service.InvestmentTicketTypeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;

/**
 * 营销活动奖励记录Controller
 * @author ydt
 * @version 2015-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/marketing/marketingActivityAwardRecord")
public class MarketingActivityAwardRecordController extends BaseController {

	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private InvestmentTicketTypeService investmentTicketTypeService;
	@Autowired
	private CustomerInvestmentTicketService customerInvestmentTicketService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerIntegralSnapshotService customerIntegralSnapshotService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	
	@ModelAttribute
	public MarketingActivityAwardRecord get(@RequestParam(required=false) String id) {
		MarketingActivityAwardRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = marketingActivityAwardRecordService.get(id);
		}
		if (entity == null){
			entity = new MarketingActivityAwardRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("marketing:marketingActivityAwardRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(MarketingActivityAwardRecord marketingActivityAwardRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MarketingActivityAwardRecord> page = marketingActivityAwardRecordService.findPage(new Page<MarketingActivityAwardRecord>(request, response), marketingActivityAwardRecord); 
		model.addAttribute("page", page);
		List<MarketingActivityInfo> marketingActivityInfoList = marketingActivityInfoService.findAllList();
		model.addAttribute("marketingActivityInfoList", marketingActivityInfoList);
		return "modules/marketing/marketingActivityAwardRecordList";
	}

	@RequiresPermissions("marketing:marketingActivityAwardRecord:view")
	@RequestMapping(value = "form")
	public String form(MarketingActivityAwardRecord marketingActivityAwardRecord, Model model) {
		model.addAttribute("marketingActivityAwardRecord", marketingActivityAwardRecord);
		List<MarketingActivityInfo> marketingActivityInfoList = marketingActivityInfoService.findAllList();
		model.addAttribute("marketingActivityInfoList", marketingActivityInfoList);
		return "modules/marketing/marketingActivityAwardRecordForm";
	}

	@RequiresPermissions("marketing:marketingActivityAwardRecord:edit")
	@RequestMapping(value = "save")
	public String save(MarketingActivityAwardRecord marketingActivityAwardRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, marketingActivityAwardRecord)){
			return form(marketingActivityAwardRecord, model);
		}
		marketingActivityAwardRecordService.save(marketingActivityAwardRecord);
		addMessage(redirectAttributes, "保存营销活动奖励记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityAwardRecord/?repage";
	}
	
	@RequiresPermissions("marketing:marketingActivityAwardRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(MarketingActivityAwardRecord marketingActivityAwardRecord, RedirectAttributes redirectAttributes) {
		marketingActivityAwardRecordService.delete(marketingActivityAwardRecord);
		addMessage(redirectAttributes, "删除营销活动奖励记录成功");
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityAwardRecord/?repage";
	}
	
	@RequiresPermissions("marketing:marketingActivityAwardRecord:edit")
	@RequestMapping(value = "patchAward", method = RequestMethod.GET)
	public String patchAward(MarketingActivityAwardRecord marketingActivityAwardRecord, RedirectAttributes redirectAttributes, Model model) {
		//活动列表
		List<MarketingActivityInfo> marketingActivityInfoList = marketingActivityInfoService.findAllList();
		model.addAttribute("marketingActivityInfoList", marketingActivityInfoList);
		//投资券类型列表
		List<InvestmentTicketType> investmentTicketTypeList = investmentTicketTypeService.findListByStatus(ProjectConstant.INVESTMENT_TICKET_TYPE_STATUS_NORMAL);
		model.addAttribute("investmentTicketTypeList", investmentTicketTypeList);
		return "modules/marketing/marketingActivityAwardRecordPatchAward";
	}
	

	@RequiresPermissions("marketing:marketingActivityAwardRecord:edit")
	@RequestMapping(value = "patchAward", method = RequestMethod.POST)
	public String doPatchAward(MarketingActivityAwardRecord marketingActivityAwardRecord, RedirectAttributes redirectAttributes, Model model) {
		CustomerAccount customerAccount = customerAccountService.getByMobile(marketingActivityAwardRecord.getCustomerMobile());
		if(customerAccount == null) {
			addMessage(redirectAttributes, "补送营销奖励失败，用户不存在或处于锁定状态！");
			return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityAwardRecord/patchAward";
		}
		marketingActivityAwardRecord.setAccountId(customerAccount.getAccountId());
		marketingActivityAwardRecord.setAwardDt(new Date());
		marketingActivityAwardRecord.setChannelId(ProjectConstant.OP_TERM_DICT_PC);
		marketingActivityAwardRecord.setUser(UserUtils.getUser());
		PatchMarketingAwardFactory patchMarketingAwardFactory = new PatchMarketingAwardFactory();
		try {
			patchMarketingAwardFactory.doPatchAward(marketingActivityAwardRecord);
			marketingActivityAwardRecordService.save(marketingActivityAwardRecord);
			addMessage(redirectAttributes, "补送营销奖励成功！");
		} catch(Exception e) {
			addMessage(redirectAttributes, "补送营销奖励失败！");
		}
		return "redirect:"+Global.getAdminPath()+"/marketing/marketingActivityAwardRecord/patchAward";
	}

	class PatchMarketingAwardFactory {
		void doPatchAward(MarketingActivityAwardRecord marketingActivityAwardRecord) throws Exception {
			PatchMarketingAward patchMarketingAward = null;
			if(ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET.equals(marketingActivityAwardRecord.getAwardType())) {
				patchMarketingAward = new PatchMarketingAwardInvestmentTicket();
			} else if(ProjectConstant.MARKETING_AWARD_TYPE_MONEY.equals(marketingActivityAwardRecord.getAwardType())) {
				patchMarketingAward = new PatchMarketingAwardMoney();
			} else if(ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL.equals(marketingActivityAwardRecord.getAwardType())) {
				patchMarketingAward = new PatchMarketingAwardIntegral();
			} else if(ProjectConstant.MARKETING_AWARD_TYPE_FREE_WITHDRAW_COUNT.equals(marketingActivityAwardRecord.getAwardType())) {
				patchMarketingAward = new PatchMarketingAwardFreeWithdrawCount();
			}
			patchMarketingAward.doPatchAward(marketingActivityAwardRecord.getAccountId(),
					marketingActivityAwardRecord.getAwardValue(), marketingActivityAwardRecord.getAwardReason(), marketingActivityAwardRecord.getChannelId());
		}
	}
	
	interface PatchMarketingAward {
		public void doPatchAward(long accountId, String value, String remark, String termType) throws Exception;
	}
	
	class PatchMarketingAwardInvestmentTicket implements PatchMarketingAward {
	
		@Override
		public void doPatchAward (long accountId, String value, String remark, String termType) throws Exception {
			try {
				customerInvestmentTicketService.giveCustomerTickets(accountId, new int[]{Integer.parseInt(value)}, remark);
			} catch(Exception e) {
				throw e;
			}
		}
		
	}
	
	class PatchMarketingAwardMoney implements PatchMarketingAward {
	
		@Override
		public void doPatchAward(long accountId, String value, String remark, String termType) throws Exception {
			CustomerAccount customerAccount = customerAccountService.get(accountId + "");
			double amount = Double.parseDouble(value);
			if(amount > 0 && ProjectConstant.HASOPENED.equals(customerAccount.getHasOpenThirdAccount())) {
				yeepayCommonHandler.transferToCustomerFromPlatform(accountId, amount, ProjectConstant.CHANGE_TYPE_BALANCE_DOUBLE_HOLIDAY_INVESTMENT, remark, "");
			} else {
				throw new Exception("赠送失败");
			}
		}
		
	}
	
	class PatchMarketingAwardIntegral implements PatchMarketingAward {
	
		@Override
		public void doPatchAward(long accountId, String value, String remark, String termType) {
			customerIntegralSnapshotService.giveIntegral(accountId, Integer.parseInt(value), ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, remark, termType);
		}
		
	}
	
	class PatchMarketingAwardFreeWithdrawCount implements PatchMarketingAward {
	
		@Override
		public void doPatchAward(long accountId, String value, String remark, String termType) {
			customerBalanceService.updateFreeWithdrawCount(accountId, Integer.parseInt(value), ProjectConstant.FREE_WITHDRAW_COUNT_CHANGE_TYPE_ACTIVITY_GIVE);
		}
		
	}
}