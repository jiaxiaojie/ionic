package com.thinkgem.jeesite.modules.api;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.modules.api.annotation.Authenticate;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.frame.util.APIUtils;
import com.thinkgem.jeesite.modules.api.to.InvestAgreementResp;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 协议Controller
 * 
 * @author lzb
 * @version 2015-12-31
 */
@Controller("apiAgreementController")
@RequestMapping(value="${frontPath}/api/agreement",method=RequestMethod.POST)
public class AgreementController extends APIBaseController {
    
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private CustomerAccountService customerAccountService;

	/**
	 * 投资_协议
	 * @param response
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "investment", method = RequestMethod.POST)
	public String investment(HttpServletResponse response, String client, String token, String projectId,String amount) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api investment start...");
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(clientToken != null){
			//项目详情数据
			ProjectBaseInfo projectBaseInfo = projectBaseInfoService.get(projectId);
			Long borrowerAccountId = projectBaseInfo.getAgentUser();
			if(borrowerAccountId == null || borrowerAccountId == 0) {
				borrowerAccountId = projectBaseInfo.getBorrowersUser();
			}
			//借款人信息
			CustomerAccount borrowerAccount = customerAccountService.get(borrowerAccountId);
			CustomerBase borrowerBase = customerBaseService.getByAccountId(borrowerAccountId);
			//投资人信息
			Long accountId = clientToken.getCustomerId();
			CustomerAccount customerAccount = customerAccountService.get(accountId);
			CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
			//协议详情
			this.investmentSuccess(map, customerBase, customerAccount, borrowerAccount, borrowerBase, projectBaseInfo, amount);
			
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api investment end...");
		logger.info("api investment total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 协议详情
	 * @param customerBase
	 * @param customerAccount
	 * @param borrowerAccount
	 * @param borrowerBase
	 * @param projectBaseInfo
	 * @param amount
	 */
	public void investmentSuccess(HashMap<String, Object> map,
			CustomerBase customerBase, CustomerAccount customerAccount,
			CustomerAccount borrowerAccount, CustomerBase borrowerBase,
			ProjectBaseInfo projectBaseInfo, String amount) {
		InvestAgreementResp iResp= new InvestAgreementResp();
		//甲方（出借人）信息
		iResp.setaCustomerName(StringUtils.vagueName(customerBase.getCustomerName()));
		iResp.setaAccountName(StringUtils.vagueAccountName(customerAccount.getAccountName()));
		iResp.setaCertNum(StringUtils.vagueCertNum(customerBase.getCertNum()));
		iResp.setaAddress(StringUtils.vagueAddress(customerBase.getAddress()));
		iResp.setaMobile(StringUtils.vagueMobile(customerBase.getMobile()));
		//乙方（借款人）信息
		iResp.setbCustomerName(StringUtils.vagueName(borrowerBase.getCustomerName()));
		iResp.setbAccountName(StringUtils.vagueAccountName(borrowerAccount.getAccountName()));
		iResp.setbCertNum(StringUtils.vagueCertNum(borrowerBase.getCertNum()));
		iResp.setbAddress(StringUtils.vagueAddress(borrowerBase.getAddress()));
		iResp.setbMobile(StringUtils.vagueMobile(borrowerBase.getMobile()));
		iResp.setLoanAmount(amount);
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
		iResp.setStartYear(startYear);
		iResp.setStartMonth(startMonth);
		iResp.setStartDay(startDay);
		iResp.setEndYear(endYear);
		iResp.setEndMonth(endMonth);
		iResp.setEndDay(endDay);
		BigDecimal rate = new BigDecimal(LoanUtil.formatAmount(projectBaseInfo.getAnnualizedRate() * 100));
		rate = rate.setScale(2,   BigDecimal.ROUND_HALF_UP); 
		iResp.setRate(String.valueOf(rate));
		iResp.setUseType(projectBaseInfo.getUseMethod());
		iResp.setRepaymentMode(projectBaseInfo.getRepaymentMode());
		iResp.setRepaymentModeName(DictUtils.getDictLabel(projectBaseInfo.getRepaymentMode(), "project_repayment_mode_dict", ""));
		iResp.setRepayDay(DateUtils.formatDate(projectBaseInfo.getPlannedRepaymentDate(), "yyyy-MM-dd").substring(8, 10));
		iResp.setSafeguardMode(DictUtils.getDictLabel(projectBaseInfo.getRepaymentMode(), "project_safeguard_mode_dict", ""));
		iResp.setUpperLoanAmount(String.valueOf(ApiUtil.digitUppercase(amount != null ? NumberUtil.toDouble(amount, 0.0) : 0.00)));
		//现在时间
		String theYear = DateUtils.getYear();
		String theMonth = DateUtils.getMonth();
		String theDay = DateUtils.getDay();
		iResp.setTheYear(theYear);
		iResp.setTheMonth(theMonth);
		iResp.setTheDay(theDay);
		ApiUtil.mapRespData(map, iResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
	}


	/**
	 * 活花生协议
	 * @param client
	 * @param projectId
	 * @return
     */
	@Authenticate
	@ResponseBody
	@RequestMapping(value = "current")
	public Map<String,Object> current(String client,String projectId){
		//获得当前用户
		CustomerClientToken customerClientToken = TokenUtils.getCurrentCustomerClientToken();
		//创建一个空的APIMap
		Map<String, Object> apiMap = APIUtils.createSuccessAPIMap();
		
		CustomerBase customerBase = customerBaseService.getByAccountId(customerClientToken.getCustomerId());
		String bCustomerName = customerBase.getCustomerName();
		String bAccountName = null;
		
		CustomerAccount account = customerAccountService.get(customerBase.getAccountId());
		if(account != null){
			bAccountName = account.getAccountName();
		}
		
		Map<String,Object> data = new LinkedHashMap<String, Object>();
		data.put("aCustomerName", "花生金服");
		data.put("aAddress", "上海市闵行区陈行公路2388号2号楼13层");
		data.put("bCustomerName", StringUtils.vagueName(bCustomerName));
		data.put("bAccountName", StringUtils.vagueName(bAccountName));
		
		
		apiMap.put(ApiConstant.API_RESP_DATA, data);
			
		
		return apiMap;
	}
}
