/**
 * 
 */
package com.thinkgem.jeesite.modules.front;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.PayMoneyUtil;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.cms.CmsConstant;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.Category;
import com.thinkgem.jeesite.modules.cms.service.ArticleDataService;
import com.thinkgem.jeesite.modules.cms.service.ArticleService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.customer.service.CustomerCreditAuthService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerCreditAuth;
import com.thinkgem.jeesite.modules.entity.ProjectWillLoan;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectWillLoanService;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangtao
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/willloan")
public class CustomerWillLoanController extends BaseController {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	CustomerCreditAuthService customerCreditAuthService;
	@Autowired
	ProjectWillLoanService projectWillLoanService;
	@Autowired
	ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	CustomerBaseService customerBaseService;
	@Autowired
	DictService dictService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;

	/**
	 * 我要融资首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("willloan")
	public String willloan(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("menu", "wyrz");
		model.addAttribute("nav", "<a href='"
				+ Global.getInstance().getFrontRootUrl(request)
				+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='#'>我要融资</a>");
		// 账号
		String accountId = "";
		// 信用得分
		Double creditScore;
		// 信用额度
		Double creditLimit;
		// 申请中的额度
		Double applyingMoney;
		// 还款中的额度
		Double loaningMoney;
		// 可以借的额度
		Double canLoanMoney;
		// 期数
		int terms = ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_MAX_TERMS;
		// 判断用户是否登录
		Principal principal = (Principal) CustomerUtils.getSubject()
				.getPrincipal();
		if (principal == null) {
			// 如果未登录
			creditScore = new Double(0);
			creditLimit = new Double(0);
			applyingMoney = new Double(0);
			loaningMoney = new Double(0);
			canLoanMoney = ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_MAX_VALUE;
			model.addAttribute("terms",
					ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_MAX_TERMS);
		} else {
			// 如果登录
			accountId = principal.getAccountId().longValue() + "";
			// 获取信用得分及信用额度
			CustomerCreditAuth customerCreditAuth = customerCreditAuthService
					.getByAccountId(new Long(accountId));
			creditScore = customerCreditAuth.getCreditScore();
			if (creditScore == null) {
				creditScore = new Double("0");
			}
			creditLimit = customerCreditAuth.getCreditLimit();
			if (creditLimit == null) {
				creditLimit = new Double("0");
			}
			// 获得申请中的额度
			applyingMoney = projectWillLoanService
					.findNotEndApplyMoneyOfCustomer(accountId);
			// 获得借贷中的额度
			loaningMoney = projectBaseInfoService
					.getNotEndProjectMyCreditProject(accountId);
			// 获得还可以借贷的额度
			canLoanMoney = creditLimit - applyingMoney - loaningMoney;
			if (canLoanMoney.doubleValue() < 0) {
				canLoanMoney = new Double("0");
			}
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		DecimalFormat df2 = new DecimalFormat("######0");
		model.addAttribute("creditScore", df.format(creditScore));
		model.addAttribute("creditLimit", df.format(creditLimit));
		model.addAttribute("applyingMoney", df.format(applyingMoney));
		model.addAttribute("loaningMoney", df.format(loaningMoney));
		model.addAttribute("canLoanMoney", df2.format(canLoanMoney));
		model.addAttribute("terms", terms);
		double serviceCharge = canLoanMoney
				* ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_SERVICE_RATE;
		double money = canLoanMoney + serviceCharge;
		double monthRepay = PayMoneyUtil.payPerMonthByYearRate(money,
				ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE, terms);
		double monthFeeRate = PayMoneyUtil.getMonthFeeByYearRate(money,
				ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE, terms) * 100;
		double sumRapay = monthRepay * terms;
		// 进行预算
		model.addAttribute("serviceCharge", df.format(serviceCharge));
		model.addAttribute("monthFeeRate", df.format(monthFeeRate));
		model.addAttribute("monthFeeRate", df.format(monthFeeRate));
		model.addAttribute("monthRepay", df.format(monthRepay));
		model.addAttribute("contractMoney", df.format(money));
		model.addAttribute("sumRepay", df.format(sumRapay));

		// 初始化期限字典
		Dict dict = new Dict();
		dict.setType("customer_credit_loan_term_dict");
		List<Dict> termList = dictService.findList(dict);
		model.addAttribute("termList", termList);

		// 富定新闻
		Category categoryFdxw = new Category();
		categoryFdxw.setId(CmsConstant.CATEGORYID_FDXW);
		Page<Article> pageFdxw = new Page<Article>(1, 4, -1);
		Article articleFdxw = new Article(categoryFdxw);
		pageFdxw = articleService.findPage(pageFdxw, articleFdxw, false);
		if (pageFdxw.getList().size() > 0) {
			for (Article fdxw : pageFdxw.getList()) {
				fdxw.setArticleData(articleDataService.get(fdxw.getId()));
			}
			model.addAttribute("pageFdxw", pageFdxw);
		}

		return "modules/front/willloan";
	}

	/**
	 * 我要融资首页部分的计算
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("willloan/budgetary")
	@ResponseBody
	public Map<String, Object> budgetary(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("######0.00");
		String loanMoneyPara = request.getParameter("loanMoney");
		String termsPara = request.getParameter("terms");
		if ((termsPara == null) || (termsPara.equals(""))) {
			termsPara = ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_MAX_TERMS
					+ "";
		}
		if ((loanMoneyPara == null) || (loanMoneyPara.equals(""))) {
			loanMoneyPara = "5000";
		}

		double loanMoney = new Double(loanMoneyPara);
		int terms = new Integer(termsPara).intValue();

		double serviceCharge = loanMoney
				* ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_SERVICE_RATE;
		double money = loanMoney + serviceCharge;
		double monthRepay = PayMoneyUtil.payPerMonthByYearRate(money,
				ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE, terms);
		double monthFeeRate = PayMoneyUtil.getMonthFeeByYearRate(money,
				ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE, terms) * 100;
		double sumRapay = monthRepay * terms;
		// 进行预算
		map.put("serviceCharge", df.format(serviceCharge));
		map.put("monthFeeRate", df.format(monthFeeRate));
		map.put("monthRepay", df.format(monthRepay));
		map.put("contractMoney", df.format(money));
		map.put("sumRepay", df.format(sumRapay));
		return map;
	}

	/**
	 * 我要融资的立即申请
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "willloan/apply")
	public String apply(ProjectWillLoan projectWillLoan,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		model.addAttribute("nav", "<a href='"
				+ Global.getInstance().getFrontRootUrl(request)
				+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
				+ Global.getInstance().getFrontRootUrl(request)
				+ "/willloan/willloan'>我要融资</a>&nbsp;&gt;&nbsp;<a href='#'>立即申请</a>");
		model.addAttribute(ProjectConstant.KEY_NEED_THIRD_ACCOUNT_TIP, true);
		// 获取当前用户信息
		CustomerAccount customerAccount = CustomerUtils.getCustomerAccount();

		CustomerBase customerBase = customerBaseService
				.getByAccountId(customerAccount.getAccountId());
		customerBase = customerBase.vague();

		projectWillLoan.setCreateCustomer(customerBase);
		// 根据传过来的信息进行预算
		String termsStr = request.getParameter("terms");
		String wantLoanMoneyStr = request.getParameter("wantLoanMoney");
		wantLoanMoneyStr = wantLoanMoneyStr == null ? "50000"
				: wantLoanMoneyStr;
		termsStr = termsStr == null ? "24" : termsStr;
		DecimalFormat df = new DecimalFormat("######0.00");
		DecimalFormat df2 = new DecimalFormat("######0");
		double loanMoney = new Double(wantLoanMoneyStr);
		projectWillLoan.setLoanMoney(loanMoney);
		int terms = new Integer(termsStr).intValue();
		projectWillLoan.setDuration(terms);
		double serviceCharge = loanMoney
				* ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_SERVICE_RATE;
		double money = loanMoney + serviceCharge;
		double monthRepay = PayMoneyUtil.payPerMonthByYearRate(money,
				ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE, terms);
		double monthFeeRate = PayMoneyUtil.getMonthFeeByYearRate(money,
				ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE, terms) * 100;
		double sumRapay = monthRepay * terms;
		projectWillLoan.setServiceCharge(new Double(df.format(serviceCharge)));
		projectWillLoan.setContractMoney(new Double(df.format(money)));
		projectWillLoan.setMonthRate(new Double(df.format(monthFeeRate)));
		projectWillLoan.setMonthRepayMoney(new Double(monthRepay));
		projectWillLoan.setSumRepayMoney(new Double(df.format(sumRapay)));
		// 设置当前人员的提示信息

		// 信用得分
		// 获取信用得分及信用额度
		CustomerCreditAuth customerCreditAuth = customerCreditAuthService
				.getByAccountId(customerAccount.getAccountId());
		Double creditLimit = customerCreditAuth.getCreditLimit();
		if (creditLimit == null) {
			creditLimit = new Double("0");
		}
		// 获得申请中的额度
		Double applyingMoney = projectWillLoanService
				.findNotEndApplyMoneyOfCustomer(customerAccount.getAccountId()
						.longValue() + "");
		// 获得借贷中的额度
		Double loaningMoney = projectBaseInfoService
				.getNotEndProjectMyCreditProject(customerAccount.getAccountId()
						.longValue() + "");
		// 获得还可以借贷的额度
		Double canLoanMoney = creditLimit - applyingMoney - loaningMoney;

		projectWillLoan.setCreditLimit(new Double(df.format(creditLimit)));
		projectWillLoan.setApplyingMoney(new Double(df.format(applyingMoney)));
		projectWillLoan.setLoaningMoney(new Double(df.format(loaningMoney)));
		projectWillLoan.setCanLoanMoney(new Double(df.format(canLoanMoney)));
		projectWillLoan.setCanLoanMoneyStr(df2.format(canLoanMoney));
		return "modules/front/willloan/apply";
	}

	@RequestMapping(value = "willloan/save")
	public String save(ProjectWillLoan projectWillLoan,
			HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) throws Exception {
		projectWillLoan.replaceSpecialStr();
		// 判断用户是否登录
		Principal principal = (Principal) CustomerUtils.getSubject()
				.getPrincipal();
		String accountId = principal.getAccountId().longValue() + "";
		if (!beanValidator(model, projectWillLoan)) {
			return apply(projectWillLoan, request, response, model);
		}
		// 获取当前用户信息
		CustomerAccount customerAccount = CustomerUtils.getCustomerAccount();
		if ((customerAccount.getHasOpenThirdAccount()==null)||(customerAccount.getHasOpenThirdAccount().equals("0"))) {
			return "redirect:" + Global.getFrontPath()
					+ "/customer/thirdAccount/open";
		} else {
			// 获取信用得分及信用额度
			CustomerCreditAuth customerCreditAuth = customerCreditAuthService
					.getByAccountId(customerAccount.getAccountId());
			Double creditLimit = customerCreditAuth.getCreditLimit();
			if (creditLimit == null) {
				creditLimit = new Double("0");
			}
			// 获得申请中的额度
			Double applyingMoney = projectWillLoanService
					.findNotEndApplyMoneyOfCustomer(customerAccount.getAccountId()
							.longValue() + "");
			// 获得借贷中的额度
			Double loaningMoney = projectBaseInfoService
					.getNotEndProjectMyCreditProject(customerAccount.getAccountId()
							.longValue() + "");
			// 获得还可以借贷的额度
			Double canLoanMoney = creditLimit - applyingMoney - loaningMoney;
			// 对前台传过来的数据进行再次修正
			double loanMoney = projectWillLoan.getLoanMoney();
			if(loanMoney>canLoanMoney.doubleValue()){
				throw (new Exception("借款额度大于可借额度，恶意修改"));
			}
			int terms = projectWillLoan.getDuration();
			// 进行计算
			DecimalFormat df = new DecimalFormat("######0.00");
			// String loanMoneyPara = request.getParameter("loanMoney");
			String termsPara = request.getParameter("terms");
			if ((termsPara == null) || (termsPara.equals(""))) {
				termsPara = ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_MAX_TERMS
						+ "";
			}
			double serviceCharge = loanMoney
					* ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_SERVICE_RATE;
			double money = loanMoney + serviceCharge;
			double monthRepay = PayMoneyUtil.payPerMonthByYearRate(money,
					ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE,
					terms);
			double monthFeeRate = PayMoneyUtil.getMonthFeeByYearRate(money,
					ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE,
					terms) * 100;
			double sumRapay = monthRepay * terms;
			if (projectWillLoan.getContractMoney().doubleValue() != new Double(
					df.format(money)).doubleValue()) {
				logger.error("借款警告，存在不良修改可能，正确合同额应该为：" + money + " ,前台请求合同额为 ："
						+ projectWillLoan.getContractMoney().doubleValue());
				throw (new Exception("借款额度大于可借额度，恶意修改"));
			}
			projectWillLoan.setContractMoney(new Double(df.format(money)));
			projectWillLoan.setSumRepayMoney(new Double(df.format(sumRapay)));
			projectWillLoan.setMonthRate(new Double(df.format(monthFeeRate)));
			projectWillLoan
					.setMonthRepayMoney(new Double(df.format(monthRepay)));

			projectWillLoan
					.setAnnualizedRate(ProjectConstant.PROJECT_PERSONAL_CREDIT_LOAN_YEAR_RATE);
			projectWillLoan.setCreateUserId(new Long(accountId));
			projectWillLoan.setCreateDt(new Date());
			projectWillLoan.setHasCreateProject("0");
			projectWillLoan.setCreateIp(request.getHeader("X-Real-IP"));
			projectWillLoan
					.setStatus(ProjectConstant.PROJECT_WILL_STATUS_WAITTING_REVIEW);
			Area area = new Area();
			area.setCode("0");
			area.setId("0");
			projectWillLoan.setArea(area);
			projectWillLoanService.save(projectWillLoan);
			addMessage(redirectAttributes, "保存借贷意向成功");
			return "redirect:" + Global.getFrontPath()
					+ "/customer/loan/applyForQuery";
		}
	}

	/**
	 * 我要融资的申请申请提交
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "willloan/applysubmit")
	public String applysubmit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("nav", "<a href='"
				+ Global.getInstance().getFrontRootUrl(request)
				+ "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='"
				+ Global.getInstance().getFrontRootUrl(request)
				+ "/wyrz'>我要融资</a>&nbsp;&gt;&nbsp;<a href='#'>立即申请</a>");
		return "modules/front/willloan";
	}

}
