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
import com.thinkgem.jeesite.modules.customer.service.CustomerFreeWithdrawCountHisService;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerFreeWithdrawCountHis;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 我的提现额
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/withdrawTicket")
public class CustomerWithdrawTicketController extends BaseController {
	
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerFreeWithdrawCountHisService customerFreeWithdrawCountHisService;
	@Autowired
	private CustomerBalanceService customerBalanceService;

	/**
	 * 我的提现券
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping()
	public String withdrawCount( PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		pageSearchBean.setDefaultDateRangeWithMonths(-3);
		Page<CustomerFreeWithdrawCountHis> page = customerFreeWithdrawCountHisService.searchList(accountId, pageSearchBean);
		CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
		model.addAttribute("customerBalance", customerBalance);
		model.addAttribute("page", page);
		model.addAttribute("pageSearchBean", pageSearchBean);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdtxe");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>我的提现额</a>");
		return "modules/front/wdzh/withdrawTicket";
	}
}
