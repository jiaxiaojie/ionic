package com.thinkgem.jeesite.modules.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/loanAccount")
public class LoanAccountController {
	
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBalanceService customerBalanceService;
	
	private Long getAccountId() {
		User user = UserUtils.getUser();
		CustomerAccount customerAccount = customerAccountService.getByLoginName(user.getLoginName());
		return customerAccount == null ? null : customerAccount.getAccountId();
	}
	
	@RequestMapping("summary")
	public String summary(Model model) {
		CustomerBalance customerBalance = customerBalanceService.get(String.valueOf(getAccountId()));
		model.addAttribute("customerBalace", customerBalance);
		return "";
	}

	@RequestMapping("recharge")
	public String recharge() {
		return "";
	}

	@RequestMapping("withdraw")
	public String withdraw() {
		return "";
	}

	@RequestMapping("balance/history")
	public String balanceHistory() {
		return "";
	}

	@RequestMapping("fix/summary")
	public String fixSummary() {
		return "";
	}

	@RequestMapping("fix/project")
	public String fixProject() {
		return "";
	}

	@RequestMapping("current/summary")
	public String currentSummary() {
		return "";
	}

	@RequestMapping("current/project")
	public String currentProject() {
		return "";
	}
	
}
