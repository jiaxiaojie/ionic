/**
 * 
 */
package com.thinkgem.jeesite.modules.front;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 邀请好友
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/inviteFriends")
public class CustomerInviteFriends extends BaseController {
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	
	/**
	 * 我的好友
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("myFriends")
	public String myFriends(String pageNo, HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAccount customerAccount = customerAccountService.get(accountId + "");
		
		Page<CustomerAccount> page = customerAccountService.getByRecommendorAccountId(customerAccount.getAccountId(), pageNo);
		for(CustomerAccount ca : page.getList()) {
			CustomerBase customerBase = customerBaseService.getByAccountId(ca.getAccountId());
			ca.setCustomerBase(customerBase);
			//暂时将ca的accountName表示为状态
			String status = "已注册";
			int afterRepaymentCount = projectInvestmentRecordService.getRecordCountAfterRepaymentByAccountId(ca.getAccountId());
			if(afterRepaymentCount > 0) {
				status = "已成交";
			} else {
				int investCount = projectInvestmentRecordService.getInvestCountByAccountId(ca.getAccountId());
				if(investCount > 0) {
					status = "已投资";
				} else {
					CustomerBalance customerBalance = customerBalanceService.get(ca.getAccountId() + "");
					if(customerBalance.getRechargeCount() > 0) {
						status = "已充值";
					} else if("1".equals(ca.getHasOpenThirdAccount())) {
						status = "已开通第三方账号";
					}
				}
			}
			ca.setAccountName(status);
		}
		
		model.addAttribute("page", page);
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "yqhy");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>邀请好友</a>");
		return "modules/front/wdzh/inviteFriends/myFriends";
	}
	
	/**
	 * 我的收益
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("myEarning")
	public String myEarning(PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		Principal principal = (Principal) CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAccount customerAccount = customerAccountService.get(accountId + "");
		pageSearchBean.setDefaultDateRangeWithMonths(-1);
		Page<MarketingActivityAwardRecord> page = marketingActivityAwardRecordService.findCustomerEarningCauseByFriendPage(accountId, pageSearchBean);
		for(MarketingActivityAwardRecord marketingActivityAwardRecord : page.getList()) {
			marketingActivityAwardRecord.setAwardReason(marketingActivityAwardRecord.getAwardReason().replace("好友完成首次投资送投资额千分之一现金。", "好友首投金额1‰返现").replace("好友完成首次投资送现金。", "好友首投").replace("好友开通第三方账号送现金。", "好友开通托管账户"));
		}
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		model.addAttribute("customerAccount", customerAccount);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "yqhy");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>邀请好友</a>");
		return "modules/front/wdzh/inviteFriends/myEarning";
	}
}
