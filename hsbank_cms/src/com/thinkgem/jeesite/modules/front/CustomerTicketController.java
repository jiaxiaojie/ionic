/**
 * 
 */
package com.thinkgem.jeesite.modules.front;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerInvestmentTicketService;
import com.thinkgem.jeesite.modules.customer.service.CustomerRateTicketService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerInvestmentTicket;
import com.thinkgem.jeesite.modules.entity.CustomerRateTicket;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 我的现金券
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/ticket")
public class CustomerTicketController extends BaseController {
	@Autowired
	private CustomerInvestmentTicketService customerInvestmentTicketService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerRateTicketService customerRateTicketService;

	/**
	 * 取得登录用户账号信息，并设置到model中
	 * 此方法会在其他方法调用之前被调用
	 * @return
	 */
	@ModelAttribute("customerAccount")
	public CustomerAccount get() {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		return customerAccountService.get(accountId);
	}
	
	/**
	 * 我的优惠卷-可使用
	 * @param request
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("my")
	public String wdyhjKy(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		//可使用现金券
		Page<CustomerInvestmentTicket> page = customerInvestmentTicketService.findCanUsePageByAccountId(accountId, pageNo);
		//获取用户可用现金券价值总额与总数
		Map<String,Object> ticketStatistics = customerInvestmentTicketService.getTicketStatistics(accountId,ProjectConstant.TICKET_DICT_NORMAL);
		model.addAttribute("ticketStatistics", ticketStatistics);
		model.addAttribute("page", page);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdyhj");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>我的现金券</a>");
		return "modules/front/wdzh/wdyhj_ky";
	}
	
	/**
	 * 我的优惠卷-已使用
	 * @param request
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("wdyhj_ysy")
	public String wdyhjYsy(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		//已使用现金券
		Page<CustomerInvestmentTicket> page = customerInvestmentTicketService.findUsedListByAccountId(accountId, pageNo);
		//获取用户已使用现金券价值总额与总数
		Map<String,Object> ticketStatistics = customerInvestmentTicketService.getTicketStatistics(accountId,ProjectConstant.TICKET_DICT_USED);
		model.addAttribute("ticketStatistics", ticketStatistics);
		model.addAttribute("page", page);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdyhj");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>我的现金券</a>");
		return "modules/front/wdzh/wdyhj_ysy";
	}
	
	/**
	 * 我的优惠卷-已失效
	 * @param request
	 * @param pageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("wdyhj_ysx")
	public String wdyhjYsx(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		//已失效现金券
		Page<CustomerInvestmentTicket> page = customerInvestmentTicketService.findExpiredListByAccountId(accountId, pageNo);
		//获取用户已失效现金券价值总额与总数
		Map<String,Object> ticketStatistics = customerInvestmentTicketService.getTicketStatistics(accountId,ProjectConstant.TICKET_DICT_OVER);
		model.addAttribute("ticketStatistics", ticketStatistics);
		model.addAttribute("page", page);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdyhj");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>我的现金券</a>");
		return "modules/front/wdzh/wdyhj_ysx";
	}
	
	/**
	 * 可用优惠劵
	 * @param customerAccount
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("kyyhj")
	public String kyyhj(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		//可使用现金券
		List<CustomerInvestmentTicket> canUseInvestmentTicketList = customerInvestmentTicketService.findCanUseListByAccountId(accountId);
		model.addAttribute("canUseInvestmentTicketList", canUseInvestmentTicketList);
	
		return "modules/front/wdzh/kyyhj";
	}
	
	@RequestMapping("myUseableRateTicket")
	public String myUseableRateTicket(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		Page<CustomerRateTicket> page = new Page<CustomerRateTicket>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		page = customerRateTicketService.getPage(accountId, ProjectConstant.TICKET_DICT_NORMAL, page);
		model.addAttribute("page", page);
		return "modules/front/wdzh/rateTicket/useable";
	}
	
	@RequestMapping("myUsedRateTicket")
	public String myUsedRateTicket(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		Page<CustomerRateTicket> page = new Page<CustomerRateTicket>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		page = customerRateTicketService.getPage(accountId, ProjectConstant.TICKET_DICT_USED, page);
		model.addAttribute("page", page);
		return "modules/front/wdzh/rateTicket/used";
	}
	
	@RequestMapping("myExpiredRateTicket")
	public String myExpiredRateTicket(HttpServletRequest request, String pageNo, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		Page<CustomerRateTicket> page = new Page<CustomerRateTicket>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		page = customerRateTicketService.getPage(accountId, ProjectConstant.TICKET_DICT_OVER, page);
		model.addAttribute("page", page);
		return "modules/front/wdzh/rateTicket/expired";
	}
}
