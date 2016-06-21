package com.thinkgem.jeesite.modules.customer.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.*;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentSplitRecordService;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 会员账号信息Controller
 * @version 2015-11-16
 */
@Controller
@RequestMapping(value = "${adminPath}/customer/customerAccountInfo")
public class CustomerAccountInfoController  extends BaseController {
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private CustomerInvestmentTicketService customerInvestmentTicketService;
	@Autowired
	private CustomerBalanceHisService customerBalanceHisService;
	@Autowired
	private CustomerFreeWithdrawCountHisService customerFreeWithdrawCountHisService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private CustomerIntegralHisService customerIntegralHisService;
	@Autowired
	private CustomerIntegralSnapshotService customerIntegralSnapshotService;

	@Autowired
	private ProjectRepaymentSplitRecordService projectRepaymentSplitRecordService;
	@Autowired
	private CustomerWithdrawHisService customerWithdrawHisService;

	/**
	 * 查询会员列表
	 * @param customerAccount
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("customer:customerAccountInfo:customerAccountListView")
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = {"customerAccountInfoList"})
	public String customerAccountInfoList(CustomerAccount customerAccount,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerAccount> page = new Page<CustomerAccount>();
		page.setPageSize(0);
		Boolean query = false;
		if(customerAccount != null){
			if(customerAccount.getCustomerBase()!= null){
				String mobile =  customerAccount.getCustomerBase().getMobile();
				 if(mobile != null && mobile.length()==11 ){
					 query = true;
				 }
			}
			 if(customerAccount.getAccountId() != null && !"".equals(customerAccount.getAccountId())){
				 query = true;
			 }
			if(customerAccount.getCustomerBase() != null){
					query = true;
			}
			if(customerAccount.getCustomerBankCard() != null){
				query = true;
			}
		}
		if(query ){
			 page = customerAccountService.findPage(new Page<CustomerAccount>(request, response), customerAccount);
		}
		/**
		 * 为查询出的会员信息添加用户状态
		 * 判断过程已投资、已开通第三方账号、已绑卡否则为已注册
		 */
		for(CustomerAccount ca : page.getList()) {
			CustomerBase customerBase = customerBaseService.getByAccountId(ca.getAccountId());
			ca.setCustomerBase(customerBase);
			//CustomerAccount的userStatus用户的状态
			String status = "已注册";
			int investCount = projectInvestmentRecordService.getInvestCountByAccountId(ca.getAccountId());
			if(investCount > 0) {
				status = "已投资";
			}else if("1".equals(ca.getHasOpenThirdAccount())) {
				status = "已开通第三方账号";
			}else{
				CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(ca.getAccountId());
				if(StringUtils.isNotBlank(customerBankCard.getCardNo())){
					status = "已绑卡";
				}
			}
			//写入状态
			ca.setUserStatus(status);
		}
		model.addAttribute("page", page);
		return "modules/customer/customerAccountInfoList";
	}
	
	/**
	 * 查询会员账户信息
	 * @param customerAccount
	 * @param model
	 * @return
	 */
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = "customerAccountInfo")
	public String customerAccountInfo(CustomerAccount customerAccount, Model model) {
		customerAccount.setDefaultValue();
		CustomerAccount c = customerAccountService.get(customerAccount.getAccountId());
		c.setId(c.getAccountId()+"");
		model.addAttribute("customerAccount",c );
		model.addAttribute("model",c );
		return "modules/customer/customerAccountInfo";
	}
	
	/**
	 * 查询会员基本信息
	 * @param customerBase
	 * @param model
	 * @return
	 */
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = "baseInfo")
	public String baseInfo(CustomerBase customerBase, Model model) {
		customerBase = customerBaseService.getByAccountId(customerBase.getAccountId());
		customerBase.setDefaultValue();
		model.addAttribute("customerBase", customerBase);
		model.addAttribute("model", customerBase);
		return "modules/customer/customerBaseInfo";
	}
	
	/**
	 * 余额汇总信息
	 * @param customerBalance
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("customer:customerAccountInfo:balanceInfoView")
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = {"balanceInfo"})
	public String balanceInfo(CustomerBalance customerBalance, Model model) {
		CustomerBalance customerBalanceModel = customerBalanceService.get(customerBalance.getAccountId()+"");
		model.addAttribute("customerBalance", customerBalanceModel);
		model.addAttribute("model", customerBalanceModel);
		return "modules/customer/customerBalanceInfo";
	}
	
	/**
	 * 投资券清单
	 * @param customerInvestmentTicket
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("customer:customerAccountInfo:investmentTicketInfoView")
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = {"investmentTicketInfo"})
	public String investmentTicketInfo(CustomerInvestmentTicket customerInvestmentTicket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerInvestmentTicket> page = customerInvestmentTicketService.findPage(new Page<CustomerInvestmentTicket>(request, response), customerInvestmentTicket);
		//获取用户可用现金券价值总额与总数
		Map<String,Object> ticketStatistics = customerInvestmentTicketService.getTicketStatistics(customerInvestmentTicket.getAccountId(), ProjectConstant.TICKET_DICT_NORMAL);
		model.addAttribute("ticketStatistics", ticketStatistics);
		model.addAttribute("page", page);
		model.addAttribute("model", customerInvestmentTicket);
		return "modules/customer/customerInvestmentTicketInfoList";
	}
	
	/**
	 * 余额流水
	 * @param customerBalanceHis
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("customer:customerAccountInfo:customerBalanceHisInfoView")
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = {"customerBalanceHisInfo"})
	public String customerBalanceHisInfo(CustomerBalanceHis customerBalanceHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerBalanceHis> page = customerBalanceHisService.findPage(new Page<CustomerBalanceHis>(request, response), customerBalanceHis); 
		model.addAttribute("page", page);
		model.addAttribute("model", customerBalanceHis);
		return "modules/customer/customerBalanceHisInfoList";
	}
	
	/**
	 * 提现次数流水
	 * @param customerFreeWithdrawCountHis
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("customer:customerAccountInfo:customerFreeWithdrawCountHisInfoView")
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = {"customerFreeWithdrawCountHisInfo"})
	public String customerFreeWithdrawCountHisInfo(CustomerFreeWithdrawCountHis customerFreeWithdrawCountHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerFreeWithdrawCountHis> page = customerFreeWithdrawCountHisService.findPage(new Page<CustomerFreeWithdrawCountHis>(request, response), customerFreeWithdrawCountHis); 
		model.addAttribute("page", page);
		model.addAttribute("model", customerFreeWithdrawCountHis);
		return "modules/customer/customerFreeWithdrawCountHisInfoList";
	}

	/**
	 * 提现记录信息
	 * @param customerWithdrawHis
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = {"customerWithdrawHisInfo"})
	public String customerWithdrawHisInfo(CustomerWithdrawHis customerWithdrawHis, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CustomerWithdrawHis> page = customerWithdrawHisService.findPage(new Page<CustomerWithdrawHis>(request, response), customerWithdrawHis);
		model.addAttribute("page", page);
		model.addAttribute("model", customerWithdrawHis);
		return "modules/customer/customerWithdrawHisInfoList";
	}

	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = {"repaymentSplitRecordInfo"})
	public String projectRepaymentSplitRecordInfo(ProjectRepaymentSplitRecord repaymentSplitRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isBlank(repaymentSplitRecord.getStatus())){
			repaymentSplitRecord.setStatus(ProjectConstant.PROJECT_REPAY_STATUS_BUDGET);
		}
		List<ProjectRepaymentSplitRecord> list = projectRepaymentSplitRecordService.getProjectRepaymentInfoList(repaymentSplitRecord.getPayeeUserId(),
				repaymentSplitRecord.getStatus(),repaymentSplitRecord.getStartDate(),repaymentSplitRecord.getEndDate());
		Map<String,Object> map = projectRepaymentSplitRecordService.getSumProjectRepaymentInfo(repaymentSplitRecord.getPayeeUserId(),
				repaymentSplitRecord.getStatus(),repaymentSplitRecord.getStartDate(),repaymentSplitRecord.getEndDate());
		map = map != null ? map : new HashMap<String,Object>();
		String sumPrincipal = map.get("sumPrincipal") != null ? String.valueOf(map.get("sumPrincipal")) : "0.0";
		String sumInterest = map.get("sumInterest") != null ? String.valueOf(map.get("sumInterest")) : "0.0";
		model.addAttribute("list", list);
		model.addAttribute("repaymentSplitRecord", repaymentSplitRecord);
		model.addAttribute("sumPrincipal", sumPrincipal);
		model.addAttribute("sumInterest", sumInterest);
		return "modules/customer/repaymentSplitRecordInfoList";
	}

	/**
	 * 银行卡信息
	 * @param customerBankCard
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("customer:customerAccountInfo:customerBankCardInfoView")
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = "customerBankCardInfo")
	public String customerBankCardInfo(CustomerBankCard customerBankCard, Model model) {
		CustomerBankCard customerBankCardEnttiy = customerBankCardService.getByAccountId(customerBankCard.getAccountId());
		model.addAttribute("customerBankCard", customerBankCardEnttiy);
		model.addAttribute("model", customerBankCard);
		return "modules/customer/customerBankCardInfo";
	}
	

	/**
	 * 投资信息
	 * @param projectInvestmentRecord
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("customer:customerAccountInfo:projectInvestmentRecordInfoView")
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping(value = {"projectInvestmentRecordInfo"})
	public String projectInvestmentRecordInfo(ProjectInvestmentRecord projectInvestmentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		projectInvestmentRecord.setStatus("0,3");
		Page<ProjectInvestmentRecord> page = projectInvestmentRecordService.findPage(new Page<ProjectInvestmentRecord>(request, response), projectInvestmentRecord); 
		model.addAttribute("page", page);
		CustomerAccount account = new CustomerAccount();
		account.setAccountId(projectInvestmentRecord.getInvestmentUserId());
		model.addAttribute("model", account);
		return "modules/customer/projectInvestmentRecordInfoList";
	}
	
	
	/**
	 * 我的好友
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("customer:customerAccountInfo:view")
	@RequestMapping("customerFriendsInfo")
	public String myFriends(CustomerAccount customerAccount,String pageNo, HttpServletRequest request, Model model) {
		
		
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
		model.addAttribute("customerAccount", customerAccount);
		model.addAttribute("model", customerAccount);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "yqhy");
		model.addAttribute("two_menu", "");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;<a href='#'>邀请好友</a>");
		return "modules/customer/customerFriendsInfoList";
	}
	
	
	/**
	 * 花生豆流水
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("customerIntegralDetailInfo")
	public String detail(CustomerIntegralHis customerIntegralHis,HttpServletRequest request, String changeTypeCode, PageSearchBean pageSearchBean, Model model) {
		
		Long accountId = customerIntegralHis.getAccountId();



		CustomerIntegralSnapshot customerIntegralSnapshot = customerIntegralSnapshotService.getByAccountId(accountId);
		model.addAttribute("customerIntegralSnapshot", customerIntegralSnapshot);
		//累计获得花生豆
		model.addAttribute("getTotalIntegral", customerIntegralHisService.getTotalIntegral(accountId));
		//本月获得花生豆
		model.addAttribute("getIntegralCurrentMonth", customerIntegralHisService.getIntegralCurrentMonth(accountId));

		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		pageSearchBean.setDefaultDateRangeWithMonths(-1);
		if(customerIntegralHis.getBeginOpDate() == null){
			customerIntegralHis.setBeginOpDate(pageSearchBean.getStartDateTime());
		}
		if(customerIntegralHis.getEndOpDate() == null){
			customerIntegralHis.setEndOpDate(pageSearchBean.getEndDateTime());
		}
		Page<CustomerIntegralHis> page = customerIntegralHisService.findListWithQuery(accountId, customerIntegralHis.getReceiveOrUsed(), pageSearchBean,customerIntegralHis);
		model.addAttribute("page", page);
		model.addAttribute("changeTypeCode",changeTypeCode);
		model.addAttribute("pageSearchBean",pageSearchBean);
		
		model.addAttribute("model",customerIntegralHis);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "wdjf");
		model.addAttribute("two_menu", "szmx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;我的花生豆&nbsp;&gt;&nbsp;<a href='#'>收支明细</a>");
		return "modules/customer/customerIntegralDetailInfo";
	}
}
