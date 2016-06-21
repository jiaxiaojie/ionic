/**
 * 
 */
package com.thinkgem.jeesite.modules.front;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.entity.ProjectTransferInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectExecuteSnapshotService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.ProjectTransferInfoService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;

/**
 * 账户总览
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/agreement")
public class AgreementController extends BaseController {
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private ProjectTransferInfoService projectTransferInfoService;
	@Autowired
	private ProjectExecuteSnapshotService executeSnapshotService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	
	
	@RequestMapping("register")
	public String register() {
		return "modules/front/agreement/register_agreement";
	}
	
	/**
	 * 借款协议
	 * @param useType
	 * @param loanMoney
	 * @param duration
	 * @param model
	 * @return
	 */
	@RequestMapping("willLoan")
	public String willLoan(String useType, String loanMoney, int duration, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		model.addAttribute("customerAccount", customerAccount);
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		model.addAttribute("customerBase", customerBase);
		
		model.addAttribute("useType", useType);
		model.addAttribute("loanMoney", loanMoney);
		
		//借款期限起止时间
		String startDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String endDate = DateUtils.formatDate(DateUtils.addMonths(new Date(), duration), "yyyy-MM-dd");
		String startYear = startDate.substring(0, 4);
		String startMonth = startDate.substring(5, 7);
		String startDay = startDate.substring(8, 10);
		String endYear = endDate.substring(0, 4);
		String endMonth = endDate.substring(5, 7);
		String endDay = endDate.substring(8, 10);
		model.addAttribute("startYear", startYear);
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("startDay", startDay);
		model.addAttribute("endYear", endYear);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("endDay", endDay);
		
		model.addAttribute("rate", ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE);
		
		//借款金额大写
		model.addAttribute("upperMoney", digitUppercase(Double.parseDouble(loanMoney)));
		return "modules/front/agreement/loan_agreement";
	}

	/**
	 * 活期项目投资协议
	 * @param model
	 * @return
	 */
	@RequestMapping("currentInvestment")
	public String currentInvestment(HttpServletRequest request, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal == null) {
			request.getSession().setAttribute("up_path", request.getRequestURL().toString());
			return "modules/front/login";
		}
		long accountId = CustomerUtils.getCurrentAccountId();
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		model.addAttribute("customerBase", customerBase);
		CustomerAccount account = customerAccountService.get(accountId);
		model.addAttribute("account", account);
		return "modules/front/agreement/lp_investment_agreement";
	}
	
	/**
	 * 投资协议
	 * @param projectId
	 * @param amount
	 * @param model
	 * @return
	 */
	@RequestMapping("investment")
	public String investment(String projectId, double amount, Model model) {
		//项目信息
		ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(projectId);
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		Long borrowerAccountId = projectBaseInfo.getAgentUser();
		if(borrowerAccountId == null || borrowerAccountId == 0) {
			borrowerAccountId = projectBaseInfo.getBorrowersUser();
		}
		//借款人信息
		CustomerAccount borrowerAccount = customerAccountService.get(borrowerAccountId);
		CustomerBase borrowerBase = customerBaseService.getByAccountId(borrowerAccountId);
		model.addAttribute("borrowerAccount", borrowerAccount);
		model.addAttribute("borrowerBase", borrowerBase);
		//投资人信息
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		model.addAttribute("investmentAccount", customerAccount);
		model.addAttribute("investmentBase", customerBase);

		//借款金额大写
		model.addAttribute("loanAmount", amount);
		//借款金额大写
		model.addAttribute("upperLoanAmount", digitUppercase(amount));
		
		//借款期限起止时间
		String startDate = DateUtils.formatDate(projectBaseInfo.getBiddingDeadline(), "yyyy-MM-dd");
		String durationType = projectBaseInfo.getDurationType();
		Date endDt = DateUtils.addDays(projectBaseInfo.getBiddingDeadline(), projectBaseInfo.getProjectDuration().intValue());
		if(ProjectConstant.PROJECT_DURATION_TYPE_MONTHLY.equals(durationType)){
			endDt = DateUtils.addMonths(projectBaseInfo.getBiddingDeadline(), projectBaseInfo.getProjectDuration().intValue());
		}
		String endDate = DateUtils.formatDate(endDt, "yyyy-MM-dd");
		String startYear = startDate.substring(0, 4);
		String startMonth = startDate.substring(5, 7);
		String startDay = startDate.substring(8, 10);
		String endYear = endDate.substring(0, 4);
		String endMonth = endDate.substring(5, 7);
		String endDay = endDate.substring(8, 10);
		model.addAttribute("startYear", startYear);
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("startDay", startDay);
		model.addAttribute("endYear", endYear);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("endDay", endDay);
		//利率
		model.addAttribute("rate", projectBaseInfo.getAnnualizedRate());
		//借款用途
		model.addAttribute("useType", projectBaseInfo.getUseMethod());
		//非到期还本付息还款日
		model.addAttribute("repayDay",DateUtils.formatDate(projectBaseInfo.getPlannedRepaymentDate(), "yyyy-MM-dd").substring(8, 10));
		//现在时间
		String theYear = DateUtils.getYear();
		String theMonth = DateUtils.getMonth();
		String theDay = DateUtils.getDay();
		model.addAttribute("theYear", theYear);
		model.addAttribute("theMonth", theMonth);
		model.addAttribute("theDay", theDay);
		
		return "modules/front/agreement/investment_agreement";
	}
	
	/**
	 * 已投资直接投资债权协议
	 * @param projectId
	 * @param model
	 * @return
	 */
	@RequestMapping("investmentProject")
	public String investmentProject(String recordId, Model model) {
		//投资记录
		ProjectInvestmentRecord projectInvestmentRecord = projectInvestmentRecordService.get(recordId);
		//项目信息
		ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(projectInvestmentRecord.getProjectId() + "");
		model.addAttribute("projectBaseInfo", projectBaseInfo);
		Long borrowerAccountId = projectBaseInfo.getAgentUser();
		if(borrowerAccountId == null || borrowerAccountId == 0) {
			borrowerAccountId = projectBaseInfo.getBorrowersUser();
		}
		//借款人信息
		CustomerAccount borrowerAccount = customerAccountService.get(borrowerAccountId);
		CustomerBase borrowerBase = customerBaseService.getByAccountId(borrowerAccountId);
		model.addAttribute("borrowerAccount", borrowerAccount);
		model.addAttribute("borrowerBase", borrowerBase);
		//投资人信息
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		model.addAttribute("investmentAccount", customerAccount);
		model.addAttribute("investmentBase", customerBase);

		//借款金额大写
		model.addAttribute("loanAmount", projectInvestmentRecord.getAmount());
		//借款金额大写
		model.addAttribute("upperLoanAmount", digitUppercase(projectInvestmentRecord.getAmount()));
		
		//借款期限起止时间
		String startDate = DateUtils.formatDate(projectBaseInfo.getBiddingDeadline(), "yyyy-MM-dd");
		Date endDt = DateUtils.addDays(projectBaseInfo.getBiddingDeadline(), projectBaseInfo.getProjectDuration().intValue());
		String durationType = projectBaseInfo.getDurationType();
		if(ProjectConstant.PROJECT_DURATION_TYPE_MONTHLY.equals(durationType)){
			endDt = DateUtils.addMonths(projectBaseInfo.getBiddingDeadline(), projectBaseInfo.getProjectDuration().intValue());
		}
		String endDate = DateUtils.formatDate(endDt, "yyyy-MM-dd");
		String startYear = startDate.substring(0, 4);
		String startMonth = startDate.substring(5, 7);
		String startDay = startDate.substring(8, 10);
		String endYear = endDate.substring(0, 4);
		String endMonth = endDate.substring(5, 7);
		String endDay = endDate.substring(8, 10);
		model.addAttribute("startYear", startYear);
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("startDay", startDay);
		model.addAttribute("endYear", endYear);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("endDay", endDay);
		//利率
		model.addAttribute("rate", projectBaseInfo.getAnnualizedRate());
		//借款用途
		model.addAttribute("useType", projectBaseInfo.getUseMethod());
		//非到期还本付息还款日
		model.addAttribute("repayDay",DateUtils.formatDate(projectBaseInfo.getPlannedRepaymentDate(), "yyyy-MM-dd").substring(8, 10));
		//现在时间
		String theYear = DateUtils.getYear();
		String theMonth = DateUtils.getMonth();
		String theDay = DateUtils.getDay();
		model.addAttribute("theYear", theYear);
		model.addAttribute("theMonth", theMonth);
		model.addAttribute("theDay", theDay);
		model.addAttribute("opDt", projectInvestmentRecord.getOpDt());
		
		return "modules/front/agreement/investment_agreement";
	}

	/**
	 * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零 要用到正则表达式
	 */
	public static String digitUppercase(double n) {
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
		}
		return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
	}
	
	/**
	 * 债权转让协议
	 * @param projectTransferInfo
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "transfer")
	public String transfer(ProjectTransferInfo projectTransferInfo, HttpServletRequest request, Model model) {
		String type = StringUtil.dealString(request.getParameter("type"));
		//投资金额
		Double amount = NumberUtil.toDouble(request.getParameter("amount"), 0.00);
		//上家(转让方)服务费
		Double upServiceCharge = ProjectUtil.getUpServiceCharge(amount);
		//下家(受让方)服务费
		Double downServiceCharge = ProjectUtil.getDownServiceCharge(amount);
		model.addAttribute("amount", amount);
		model.addAttribute("serviceCharge", LoanUtil.formatAmount(upServiceCharge + downServiceCharge));
		projectTransferInfo = projectTransferInfoService.get(projectTransferInfo);
		//债权转让信息
		String projectId = String.valueOf(projectTransferInfo.getProjectId());
		String transferProjectId = String.valueOf(projectTransferInfo.getTransferProjectId());
		ProjectExecuteSnapshot executeSnapshot = executeSnapshotService.getByProjectIdAndTransferId(projectId, transferProjectId);
		model.addAttribute("executeSnapshot", executeSnapshot);
		Long accountId = projectTransferInfo.getTransferor();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		model.addAttribute("customerAccount",customerAccount);
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long transaccountId = principal.getAccountId();
		//受让方信息
		CustomerAccount transcustomerAccount = customerAccountService.get(transaccountId);
		model.addAttribute("transcustomerAccount", transcustomerAccount);
		String theDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String theYear = DateUtils.getYear();
		String theMonth = DateUtils.getMonth();
		String theDay = DateUtils.getDay();
		model.addAttribute("theDate", theDate);
		model.addAttribute("theYear", theYear);
		model.addAttribute("theMonth", theMonth);
		model.addAttribute("theDay", theDay);
		String returnUrl = "modules/front/agreement/transfer_agreement";
		if("0".equals(type)){
			returnUrl = "modules/front/agreement/will_transfer_agreement";
		}
		return returnUrl;
	}
	
	
	/**
	 * 债权转让协议(已转让)
	 * @param projectInvestmentRecord
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "transfer_already")
	public String transfer_already(ProjectInvestmentRecord projectInvestmentRecord, HttpServletRequest request, Model model) {
		String type = StringUtil.dealString(request.getParameter("type"));
		projectInvestmentRecord = projectInvestmentRecordService.get(projectInvestmentRecord.getId());
		//投资金额
		Double amount = projectInvestmentRecord.getAmount();
		//上家(转让方)服务费
		Double upServiceCharge = ProjectUtil.getUpServiceCharge(amount);
		//下家(受让方)服务费
		Double downServiceCharge = ProjectUtil.getDownServiceCharge(amount);
		model.addAttribute("amount", projectInvestmentRecord.getAmount());
		model.addAttribute("serviceCharge", LoanUtil.formatAmount(upServiceCharge + downServiceCharge));
		//债权转让信息
		String transferProjectId = String.valueOf(projectInvestmentRecord.getTransferProjectId());
		String projectId = String.valueOf(projectInvestmentRecord.getProjectId());
		ProjectExecuteSnapshot executeSnapshot = executeSnapshotService.getByProjectIdAndTransferId(projectId, transferProjectId);
		model.addAttribute("executeSnapshot", executeSnapshot);
		ProjectTransferInfo projectTransferInfo = projectTransferInfoService.get(transferProjectId);
		Long accountId = projectTransferInfo.getTransferor();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		model.addAttribute("customerAccount",customerAccount);
		//受让方信息
		CustomerAccount transcustomerAccount = customerAccountService.get(projectInvestmentRecord.getInvestmentUserId());
		model.addAttribute("transcustomerAccount", transcustomerAccount);
		String theDate = DateUtils.formatDate(projectInvestmentRecord.getOpDt(), "yyyy-MM-dd");
		String theYear = DateUtils.formatDate(projectInvestmentRecord.getOpDt(), "yyyy");
		String theMonth = DateUtils.formatDate(projectInvestmentRecord.getOpDt(), "MM");
		String theDay = DateUtils.formatDate(projectInvestmentRecord.getOpDt(), "dd");
		model.addAttribute("theDate", theDate);
		model.addAttribute("theYear", theYear);
		model.addAttribute("theMonth", theMonth);
		model.addAttribute("theDay", theDay);
		String returnUrl = "modules/front/agreement/transfer_agreement";
		if("0".equals(type)){
			returnUrl = "modules/front/agreement/will_transfer_agreement";
		}
		return returnUrl;
	}
}
