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
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.yeepay.SignUtil;
import com.thinkgem.jeesite.common.yeepay.YeepayConstant;
import com.thinkgem.jeesite.common.yeepay.YeepayUtils;
import com.thinkgem.jeesite.common.yeepay.accountInfo.AccountInfoResp;
import com.thinkgem.jeesite.common.yeepay.toRecharge.ToRechargeReq;
import com.thinkgem.jeesite.common.yeepay.toWithdraw.ToWithdrawReq;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceHisService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBankCardService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.CustomerBankCard;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.log.service.LogThirdPartyService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import com.thinkgem.jeesite.modules.yeepay.handler.YeepayCommonHandler;
import com.hsbank.util.type.NumberUtil;

/**
 * 资金管理
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/capital")
public class CustomerCapitalController extends BaseController {
	@Autowired
	CustomerAccountService customerAccountService;
	@Autowired
	CustomerBalanceService customerBalanceService;
	@Autowired
	CustomerBalanceHisService customerBalanceHisService;
	@Autowired
	private LogThirdPartyService logThirdPartyService;
	@Autowired
	private CustomerBankCardService customerBankCardService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private YeepayCommonHandler yeepayCommonHandler;
	
	/**
	 * 资金交易记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "record")
	public String record(String changeType, PageSearchBean pageSearchBean, HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		//获取当前账号余额信息
		CustomerBalance customerBalance = customerBalanceService.get(accountId.longValue()+"");
		model.addAttribute("customerBalance", customerBalance);
		if(StringUtils.isBlank(changeType)) {
			changeType = "";
		}
		pageSearchBean.setDefaultDateRangeWithMonths(-3);
		Page<CustomerBalanceHis> page = customerBalanceHisService.searchPage(accountId, changeType, pageSearchBean); 
		model.addAttribute("page", page);
		model.addAttribute("changeType", changeType);
		model.addAttribute("pageSearchBean", pageSearchBean);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zjgl");
		model.addAttribute("two_menu", "jyjl");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;资金管理&nbsp;&gt;&nbsp;<a href='#'>交易记录</a>");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		return "modules/front/wdzh/capital/record";
	}
	
	/**
	 * 跳转到充值页面
	 * @return
	 */
	@RequestMapping("recharge")
	public String recharge(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
		model.addAttribute("customerBalance", customerBalance);
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zjgl");
		model.addAttribute("two_menu", "cz");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;资金管理&nbsp;&gt;&nbsp;<a href='#'>充值</a>");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		return "modules/front/wdzh/capital/recharge";
	}
	
	/**
	 * 对充值数据进行签名，并返回到【即将跳转到易宝充值提示】页面
	 * 		插入logThirdParty表充值记录
	 * @param customerAccount
	 * @param amount
	 * @param model
	 * @return
	 */
	@RequestMapping("recharge/sign")
	public String rechargeSign(String amount, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		if(amount == null || !NumberUtil.isDouble(amount) || Double.parseDouble(amount) <= 0) {
			return "err";
		}
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORECHARGE_REQ, customerAccount.getPlatformUserNo());
		ToRechargeReq toRechargeReq = new ToRechargeReq();
		toRechargeReq.setRequestNo(requestNo);
		toRechargeReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		//充值手续费由平台出
		toRechargeReq.setFeeMode(YeepayConstant.YEEPAY_FEE_MODE_PLATFORM);
		toRechargeReq.setAmount(amount);
		toRechargeReq.setPayProduct(YeepayConstant.YEEPAY_PAY_PRODUCT_NET);
		toRechargeReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
		toRechargeReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toRecharge?requestNo=" + requestNo);
		toRechargeReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toRecharge");
		
		String req = toRechargeReq.toReq();
		String sign = SignUtil.sign(req);
		model.addAttribute("amount", amount);
		model.addAttribute("req", req);
		model.addAttribute("sign", sign);
		model.addAttribute("requestNo", requestNo);
		model.addAttribute("rechargeUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TORECHARGE_ACTION);
		
		logThirdPartyService.insertToRechargeReq(requestNo, req);

		model.addAttribute("menu", "wdzh");
		return "modules/front/wdzh/capital/rechargeConfirm";
	}
	
	/**
	 * 跳转到提现页面
	 * @param customerAccount
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("withdraw")
	public String withdraw(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		model.addAttribute("customerAccount", customerAccountService.get(accountId + ""));
		
		
		
		
		model.addAttribute("customerBankCard", getCustomerBankCardByYeepay(accountId));
		CustomerBalance customerBalance = customerBalanceService.get(accountId + "");
		model.addAttribute("customerBalance", customerBalance);
		
		model.addAttribute("menu", "wdzh");
		model.addAttribute("one_menu", "zjgl");
		model.addAttribute("two_menu", "tx");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/customer/summary'>我的账户</a>&nbsp;&gt;&nbsp;资金管理&nbsp;&gt;&nbsp;<a href='#'>提现</a>");
		//是否需要提示开通第三方账户
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		return "modules/front/wdzh/capital/withdraw";
	}
	
	
	private CustomerBankCard getCustomerBankCardByYeepay(Long accountId){
		CustomerBankCard customerBankCard = customerBankCardService.getByAccountId(accountId);
		
		//去易宝查询绑卡信息
		CustomerAccount  customerAccount = customerAccountService.get(accountId);
		AccountInfoResp accountInfoResp = yeepayCommonHandler.accountInfo(customerAccount.getPlatformUserNo());
		String cardNo = accountInfoResp.getCardNo();
		String cardStatus = accountInfoResp.getCardStatus();
		String bank = accountInfoResp.getBank();
		customerBankCard.setCardNo(cardNo);
		customerBankCard.setCardStatusCode(cardStatus);
		customerBankCard.setBankCode(bank);
		return  customerBankCard;
	}
	
	/**
	 * 对提现数据进行签名，并返回到【即将跳转到易宝提现提示】页面
	 * 		插入logThirdParty表提现记录
	 * @param customerAccount
	 * @param amount
	 * @param useFreeWithdrawCount	是否使用免费提现次数（0：不使用；1使用）
	 * @param model
	 * @return
	 */
	@RequestMapping("withdraw/sign")
	public String withdrawSign(String amount, String useFreeWithdrawCount, Model model) {
		CustomerAccount customerAccount = CustomerUtils.get();
		CustomerBalance customerBalance = customerBalanceService.get(customerAccount.getAccountId() + "");
		//对数据进行验证
		if(LoanUtil.formatAmount(Double.parseDouble(amount) + ("1".equals(useFreeWithdrawCount) ? 0 : 2)) > (LoanUtil.formatAmount(customerBalance.getGoldBalance() - customerBalance.getCongealVal()))) {
			throw new ServiceException("withdraw amount bigger than can withdraw gold balance!");
		}
		if("1".equals(useFreeWithdrawCount) && customerBalance.getFreeWithdrawCount() <= 0) {
			throw new ServiceException("no free withdraw count but use");
		}
		int todayWithdrawCount = customerBalanceHisService.getCustomerTodayWithdrawCount(customerAccount.getAccountId());
		//如果超过当天提现次数限制，则跳转到超过当天提现次数页面
		if(todayWithdrawCount >= ProjectConstant.ONEDAY_MAX_WITHDRAW_COUNT) {
			return "modules/front/wdzh/capital/withdrawCountExceedTips";
		}
		
		String requestNo = YeepayUtils.getSequenceNumber(YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_REQ, customerAccount.getPlatformUserNo());
		ToWithdrawReq toWithdrawReq = new ToWithdrawReq();
		toWithdrawReq.setRequestNo(requestNo);
		toWithdrawReq.setPlatformNo(YeepayConstant.YEEPAY_PLATFORM_NO);
		toWithdrawReq.setPlatformUserNo(customerAccount.getPlatformUserNo());
		toWithdrawReq.setAmount(amount);
		if("1".equals(useFreeWithdrawCount)) {
			toWithdrawReq.setFeeMode(YeepayConstant.YEEPAY_FEE_MODE_PLATFORM);
		} else {
			toWithdrawReq.setFeeMode(YeepayConstant.YEEPAY_FEE_MODE_USER);
		}
		toWithdrawReq.setWithdrawType(YeepayConstant.YEEPAY_WITHDRAW_TYPE_NORMAL);
		toWithdrawReq.setCallbackUrl(YeepayConstant.YEEPAY_GATE_WAY_CALLBACK_URL_PREFIX + "toWithdraw?requestNo=" + requestNo);
		toWithdrawReq.setNotifyUrl(YeepayConstant.YEEPAY_GATE_WAY_NOTIFY_URL_PREFIX + "toWithdraw");
		
		String req = toWithdrawReq.toReq();
		String sign = SignUtil.sign(req);
		model.addAttribute("customerBase", customerBaseService.getByAccountId(customerAccount.getAccountId()));
		model.addAttribute("customerBankCard", getCustomerBankCardByYeepay(customerAccount.getAccountId()));
		model.addAttribute("req", req);
		model.addAttribute("sign", sign);
		model.addAttribute("requestNo", requestNo);
		model.addAttribute("withdrawUrl", YeepayConstant.YEEPAY_GATE_URL_PREFIX + YeepayConstant.PROJECT_INTERFACE_GATE_WAY_TOWITHDRAW_ACTION);
		
		logThirdPartyService.insertToWithdrawReq(requestNo, req);
		
		model.addAttribute("menu", "wdzh");
		return "modules/front/wdzh/capital/withdrawConfirm";
	}
}
