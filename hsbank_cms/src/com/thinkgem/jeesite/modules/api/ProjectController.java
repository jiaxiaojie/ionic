/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.loan.util.LoanConstant;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.MoneyUtils;
import com.thinkgem.jeesite.common.utils.MyBeanUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.to.ExchangeRateResp;
import com.thinkgem.jeesite.modules.api.to.InvestmentRecordsResp;
import com.thinkgem.jeesite.modules.api.to.ProjectBaseInfoResp;
import com.thinkgem.jeesite.modules.api.to.RepaymentPlanResp;
import com.thinkgem.jeesite.modules.cms.service.ArticleDataService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.entity.front.ProjectSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.ProjectUtil;
import com.thinkgem.jeesite.modules.project.service.*;
import com.thinkgem.jeesite.modules.sys.service.SysExchangeRateParaService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 定期产品Controller
 * 
 * @author lzb
 * @version 2015-10-16
 */
@Controller("apiProjectController")
@RequestMapping("${frontPath}/api/project")
public class ProjectController extends APIBaseController {
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private ProjectExecuteSnapshotService projectExecuteSnapshotService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private ProjectRepaymentPlanService projectRepaymentPlanService;
	@Autowired
	private ProjectShowTermService projectShowTermService;
	@Autowired
	private SysExchangeRateParaService sysExchangeRateParaService;
	@Autowired
	private ArticleDataService ariticleDataService;
	
	/**
	 * 项目详情信息
	 * @param response
	 * @param projectId
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public String detail(HttpServletResponse response, HttpServletRequest request, String client, String projectId) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api detail start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
			ProjectBaseInfo pInfo = projectBaseInfoService.get(projectId);
			ProjectExecuteSnapshot pSnapshot = projectExecuteSnapshotService.getByProjectId(projectId);
			if(pSnapshot == null){
				pSnapshot = new ProjectExecuteSnapshot();
			}
			pInfo.setPes(pSnapshot);
			if(pInfo != null){
				//项目详情数据
				this.detailSuccess(request, pInfo, map);
			}else{
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
			}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api detail end...");
		logger.info("api detail total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 项目详情数据
	 * @param pInfo
	 * @param map
	 */
	public void detailSuccess(HttpServletRequest request, ProjectBaseInfo pInfo, HashMap<String, Object> map){
		ProjectBaseInfoResp pResp = new ProjectBaseInfoResp();
		pResp.setProjectId(NumberUtils.toLong(pInfo.getProjectId(), 0L));
		pResp.setProjectCode(pInfo.getProjectCode());
		pResp.setProjectTips(pInfo.getProjectTips());//项目提示
		pResp.setProjectType(pInfo.getProjectTypeCode());
		pResp.setProjectTypeName(DictUtils.getDictLabel(String.valueOf(pInfo.getProjectTypeCode()), "project_type_dict", ""));
		pResp.setProjectName(pInfo.getProjectName());
		pResp.setRepaymentMode(NumberUtil.toLong(pInfo.getRepaymentMode(), 0L));
		pResp.setRepaymentModeName(DictUtils.getDictLabel(pInfo.getRepaymentMode(), "project_repayment_mode_dict", ""));
		Double financeMoney = pInfo.getFinanceMoney();
		Double endFinanceMoney = pInfo.getPes().getEndFinanceMoney() !=null ? pInfo.getPes().getEndFinanceMoney() : 0.0;
		Double rate = endFinanceMoney / financeMoney * 100;
		pResp.setPlanAmount(financeMoney);
		pResp.setAmount(this.voteAmount(financeMoney, endFinanceMoney));
		pResp.setRate(ApiUtil.formatRate(rate));
		pResp.setStatus(NumberUtil.toLong(pInfo.getProjectStatus(), 0));
		pResp.setStatusName(DictUtils.getDictLabel(pInfo.getProjectStatus(), "project_status_dict", ""));
		Double annualizedRate = pInfo.getAnnualizedRate() != null ? pInfo.getAnnualizedRate() : 0;
		Double iIntRate = pInfo.getIncreaseInterestRate() !=null && !ProjectConstant.DICT_DEFAULT_VALUE.equals(pInfo.getIsIncreaseInterest()) ? pInfo.getIncreaseInterestRate() : 0;
		Double rateNoraml = formatAmountThree(annualizedRate - iIntRate);
		pResp.setAnnualizedRate(annualizedRate);
		pResp.setAnnualizedRateNormal(rateNoraml);
		pResp.setAnnualizedRateAdd(iIntRate);
		pResp.setActivityRemark(getActivityRemark(pInfo, iIntRate));
		//借款人
		Long borrowersId = pInfo.getAgentUser();
		if(borrowersId == null || borrowersId == 0) {
			borrowersId = pInfo.getBorrowersUser();
		}
		pResp.setBorrowersUser(StringUtils.vagueName(customerBaseService.getCustomerNameByAccountId(borrowersId)));
		pResp.setDurationUnit(NumberUtil.toLong(pInfo.getDurationType(),0));
		pResp.setDurationUnitName(DictUtils.getDictLabel(String.valueOf(pInfo.getDurationType()), "project_duration_type_dict", ""));
		pResp.setProjectDuration(pInfo.getProjectDuration());
		pResp.setStartingAmount(pInfo.getStartingAmount());
		pResp.setBiddingDeadline(DateUtils.formatDate(pInfo.getBiddingDeadline()));
		pResp.setBiddingDeadlineTime(DateUtils.FormatDate(pInfo.getBiddingDeadline(),"yyyy-MM-dd HH:mm:ss"));
		pResp.setPublishDt(DateUtils.formatDateTime(pInfo.getPublishDt()));
		pResp.setSurplusTime(ApiUtil.getSurplusTime(pInfo.getBiddingDeadline(),pInfo.getProjectStatus()));
		pResp.setProjectIntroduce(pInfo.getProjectIntroduce());
		pResp.setUseMethod(pInfo.getUseMethod());
		pResp.setTransferCode(String.valueOf(pInfo.getTransferCode()));
		pResp.setTransferConstraint(DictUtils.getDictLabel(String.valueOf(pInfo.getTransferCode()), "project_transfer_code", ""));
		//获取默认风控信息，若项目风控信息为空则设为默认风控信息
		if(StringUtils.isBlank(pInfo.getRiskInfo())) {
			String defaultRiskInfo = ariticleDataService.get(ProjectConstant.DEFAULT_RISK_INFO_ID).getContent();
			pInfo.setRiskInfo(ApiUtil.prefixHref(defaultRiskInfo));
		}else{
			pInfo.setRiskInfo(ApiUtil.prefixSrc(pInfo.getRiskInfo()));
		}
		pResp.setRiskInfo(pInfo.getRiskInfo());
		pResp.setAboutFiles(aboutFiles(pInfo.getAboutFiles()));
		pResp.setTerminalCodes(getTerminalCodes(pInfo.getProjectId()));
		pResp.setArea(pInfo.getArea().getName());
		pResp.setInvestmentCount(projectInvestmentRecordService.getCountByProjectIdAndStatuses(pInfo.getProjectId()));
		pResp.setIsNewUser(pInfo.getIsNewUser());
		pResp.setIsRecommend("1".equals(pInfo.getIsRecommend()) ? "0" : "1");
		pResp.setIsUseTicket("1".equals(pInfo.getIsNewUser()) ? "0" : "1");
		pResp.setIsCanAssign(pInfo.getTransferCode() != -1 && "1".equals(pInfo.getIsNewUser()) ? "0" : "-1");
		ApiUtil.mapRespData(map, pResp, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
	}




	
	/**
	 * 显示终端
	 * @param projectId
	 * @return
	 */
	public  String getTerminalCodes(String projectId){
		List<ProjectShowTerm> list = projectShowTermService.findListByProjectId(projectId);
		StringBuffer stringBuilder = new StringBuffer();
		for(ProjectShowTerm showTerm : list){
			stringBuilder.append(showTerm.getTermCode()).append(",");
		}
		if(stringBuilder.indexOf(",")!=-1){
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 文件转化
	 * @param files
	 * @return
	 */
	public static String[] aboutFiles(String files){
		String[] aboutFiles = new String[]{};
		String[] fileStr = files.split("\\|");
		List<String> list = new ArrayList<String>();
		for(int i=1; i< fileStr.length; i++){
			if(StringUtils.isNotBlank(fileStr[i])){
				list.add(ApiUtil.imageUrlConver(fileStr[i]));
			}
		}
		aboutFiles = (String[]) list.toArray(new String[list.size()]);
		return aboutFiles;
	}
	
	/**
	 * 可投资金额
	 * @param financeMoney
	 * @param endFinanceMoney
	 * @return
	 */
	public Double voteAmount(Double financeMoney, Double endFinanceMoney){
		Double investmentFinanceMoney = financeMoney - endFinanceMoney < 0 ? 0 : financeMoney - endFinanceMoney;
		investmentFinanceMoney = LoanUtil.formatAmount(investmentFinanceMoney);
		return investmentFinanceMoney;
	}
	
	/**
	 * 项目分页列表
	 * @param projectSearchBean
	 * @param response
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "pageList", method = RequestMethod.POST)
	public String pageList(ProjectSearchBean projectSearchBean, HttpServletResponse response, String client, Integer pageNumber, Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api pageList start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProjectBaseInfo> List = projectBaseInfoService.getProjectListPage(pageNumber, pageSize);
		//项目分页列表数据
		this.pageListSuccess(List, map);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api pageList end...");
		logger.info("api pageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 项目分页列表数据
	 * @param list
	 * @param map
	 */
	public void pageListSuccess(List<ProjectBaseInfo> list, HashMap<String, Object> map){
		List<Object> pRespList = new ArrayList<Object>();
		for(ProjectBaseInfo pInfo : list){
			ProjectBaseInfoResp pResp = new ProjectBaseInfoResp();
			pResp.setProjectId(NumberUtils.toLong(pInfo.getProjectId(),0L));
			pResp.setProjectCode(pInfo.getProjectCode());
			pResp.setProjectType(pInfo.getProjectTypeCode());
			pResp.setProjectTypeName(DictUtils.getDictLabel(String.valueOf(pInfo.getProjectTypeCode()), "project_type_dict", ""));
			pResp.setProjectName(pInfo.getProjectName());
			pResp.setProjectTips(pInfo.getProjectTips());//项目提示
			pResp.setRepaymentMode(NumberUtil.toLong(pInfo.getRepaymentMode(), 0L));
			pResp.setRepaymentModeName(DictUtils.getDictLabel(pInfo.getRepaymentMode(), "project_repayment_mode_dict", ""));
			pResp.setStartingAmount(pInfo.getStartingAmount());
			Double financeMoney = pInfo.getFinanceMoney();
			Double endFinanceMoney = pInfo.getPes().getEndFinanceMoney();
			Double rate = endFinanceMoney / financeMoney * 100;
			pResp.setAmount(this.voteAmount(financeMoney, endFinanceMoney));
			pResp.setRate(ApiUtil.formatRate(rate));
			pResp.setStatus(NumberUtil.toLong(pInfo.getProjectStatus(), 0));
			pResp.setStatusName(DictUtils.getDictLabel(pInfo.getProjectStatus(), "project_status_dict", ""));
			Double annualizedRate = pInfo.getAnnualizedRate() != null ? pInfo.getAnnualizedRate() : 0;
			Double iIntRate = pInfo.getIncreaseInterestRate() !=null && !ProjectConstant.DICT_DEFAULT_VALUE.equals(pInfo.getIsIncreaseInterest()) ? pInfo.getIncreaseInterestRate() : 0;
			Double rateNoraml = formatAmountThree(annualizedRate - iIntRate);
			pResp.setAnnualizedRate(annualizedRate);
			pResp.setAnnualizedRateNormal(rateNoraml);
			pResp.setAnnualizedRateAdd(iIntRate);
			pResp.setActivityRemark(getActivityRemark(pInfo, iIntRate));
			pResp.setDurationUnit(NumberUtil.toLong(pInfo.getDurationType(),0));
			pResp.setDurationUnitName(DictUtils.getDictLabel(String.valueOf(pInfo.getDurationType()), "project_duration_type_dict", ""));
			pResp.setProjectDuration(pInfo.getProjectDuration());
			pResp.setSafeguardMode(pInfo.getSafeguardMode());
			pResp.setTerminalCodes(getTerminalCodes(pInfo.getProjectId()));
			pResp.setSafeguardModeName(DictUtils.getDictLabel(String.valueOf(pInfo.getSafeguardMode()), "project_safeguard_mode_dict", ""));
			pResp.setIsNewUser(pInfo.getIsNewUser());
			pResp.setIsRecommend("1".equals(pInfo.getIsRecommend()) ? "0" : "1");
			pResp.setIsUseTicket("1".equals(pInfo.getIsNewUser()) ? "0" : "1");
			pResp.setIsCanAssign(pInfo.getTransferCode() != -1 ? "0" : "-1");
			pRespList.add(pResp);
		}
		ApiUtil.mapRespData(map, pRespList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
	}
	
	
	/**
	 * 项目活动说明
	 * @param pInfo
	 * @param iIntRate
	 * @return
	 */
	public String getActivityRemark(ProjectBaseInfo pInfo, Double iIntRate){
		String isIncreaseInterest = pInfo.getIsIncreaseInterest();
		String activityRemark = "";
		if("1".equals(isIncreaseInterest)){//加息时
			String terminalCodes = ProjectConstant.OP_TERM_DICT_ANDROID +"," + ProjectConstant.OP_TERM_DICT_IOS;
			Double iIntRatePercent = LoanUtil.formatAmount(iIntRate * 100);
			if("0".equals(pInfo.getIsNewUser())){
				activityRemark = "新手专享加息+" + iIntRatePercent + "%";
			}else{
				activityRemark = ("" + terminalCodes + "").equals(getTerminalCodes(pInfo.getProjectId())) ? "app专享加息+"
						+ iIntRatePercent + "%" : "平台贴息+" + iIntRatePercent + "%";
			}
		}
		return activityRemark;
	}
	
	/**
	 * 
	 * @param amount
	 * @return
	 */
	public static Double formatAmountThree(Double amount) {
		if(amount == null){
			return 0.00;
		}
		return new BigDecimal(amount).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 指定项目的投资记录列表
	 * @param response
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "investmentRecords", method = RequestMethod.POST)
	public String investmentRecords(HttpServletResponse response, String client, String projectId) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api investmentRecords start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProjectInvestmentRecord> pRecordsList = projectInvestmentRecordService.findListByProjectIdAndStatuses(projectId);
		List<Object> iRespList = new ArrayList<Object>();
		for(ProjectInvestmentRecord pRecord: pRecordsList){
			InvestmentRecordsResp iResp = new InvestmentRecordsResp();
			MyBeanUtils.copyBean2Bean(iResp, pRecord);
			iResp.setInvestmentUser(StringUtils.vagueName(pRecord.getCa().getAccountName()));
			iResp.setOpDt(DateUtils.formatDateTime(pRecord.getOpDt()));
			iResp.setOpTerm(DictUtils.getDictLabel(pRecord.getOpTerm(), "op_term_dict", ""));
			iRespList.add(iResp);
		}
		ApiUtil.mapRespData(map, iRespList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api investmentRecords end...");
		logger.info("api investmentRecords total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 推荐项目列表
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "recommend", method = RequestMethod.POST)
	public String recommend(HttpServletResponse response, String client) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api recommend start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 获取推荐项目列表
		List<ProjectBaseInfo> pList = projectBaseInfoService
						.getApiRecommendList();
		//项目分页列表数据
		this.pageListSuccess(pList, map);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api recommend end...");
		logger.info("api recommend total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 指定项目的还款计划
	 * @param response
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "repaymentPlan", method = RequestMethod.POST)
	public String repaymentPlan(HttpServletResponse response, String client, String projectId) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api repaymentPlan start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProjectRepaymentPlan> pRPList = projectRepaymentPlanService.findListByProjectId(projectId);
		List<RepaymentPlanResp> respList = new ArrayList<RepaymentPlanResp>();
		for(ProjectRepaymentPlan plan: pRPList){
			RepaymentPlanResp resp = new RepaymentPlanResp();
			resp.setPlanDate(DateUtils.formatDate(plan.getPlanDate(), "yyyy-MM-dd"));
			resp.setPlanMoney(plan.getPlanMoney());
			resp.setPrincipal(plan.getPrincipal());
			resp.setInterest(plan.getInterest());
			resp.setRemainingPrincipal(plan.getRemainingPrincipal());
			resp.setStatus(plan.getStatus());
			resp.setStatusName(DictUtils.getDictLabel(plan.getStatus(), "project_repayment_plan_status_dict", ""));
			respList.add(resp);
		}
		ApiUtil.mapRespData(map, respList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api repaymentPlan end...");
		logger.info("api repaymentPlan total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 收益计算
	 * @param response
	 * @param client
	 * @param projectId
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "interestCalculation", method = RequestMethod.POST)
	public String interestCalculation(HttpServletResponse response, String client, String projectId, String amount) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api interestCalculation start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		ProjectBaseInfo pInfo = projectBaseInfoService.get(projectId);
		if(pInfo != null){
			//计息开始日期
			Date beginInterestDate = DateUtils.dateFormate(new Date());
			//加息券列表
			List<IncreaseInterestItem> interestItems = new ArrayList<IncreaseInterestItem>();
			//投资收益
			Double willProfit = ProjectUtil.calculateWillProfit(interestItems, pInfo, NumberUtils.toDouble(amount), "", beginInterestDate);
			BigDecimal profit = new BigDecimal(willProfit);
			profit = profit.setScale(2,   BigDecimal.ROUND_HALF_UP); 
			ApiUtil.mapRespData(map, String.valueOf(profit), ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
		}
		
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api interestCalculation end...");
		logger.info("api interestCalculation total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 收益计算器(新版)
	 * @param response
	 * @param client
	 * @param projectId
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "interestCalculator", method = RequestMethod.POST)
	public String interestCalculator(HttpServletResponse response, String client, String projectId, String amount) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api interestCalculator start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		ProjectBaseInfo pInfo = projectBaseInfoService.get(projectId);
		if(pInfo != null){
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("project", this.getCalProject(pInfo, amount));
			data.put("monetaryFund", this.getCalThird(sysExchangeRateParaService.getRateParaByRateType(ProjectConstant.EXCHANGE_RATE_TYPE_MONETARY), pInfo, amount));
			data.put("bankCurrent", this.getCalThird(sysExchangeRateParaService.getRateParaByRateType(ProjectConstant.EXCHANGE_RATE_TYPE_BANKCURRENT), pInfo, amount));
			ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api interestCalculator end...");
		logger.info("api interestCalculator total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 平台项目收益计算
	 * @param pInfo
	 * @param amount
	 * @return
	 */
	public ExchangeRateResp getCalProject (ProjectBaseInfo pInfo, String amount){
		//计息开始日期
		Date beginInterestDate = DateUtils.dateFormate(new Date());
		//加息券列表
		List<IncreaseInterestItem> interestItems = new ArrayList<IncreaseInterestItem>();
		//投资收益
		Double willProfit = ProjectUtil.calculateWillProfit(interestItems, pInfo, NumberUtils.toDouble(amount), "", beginInterestDate);
		ExchangeRateResp rateResp = new ExchangeRateResp();
		MyBeanUtils.copyBean2Bean(rateResp, pInfo);
		rateResp.setProjectName(getCalProjectName(pInfo.getIsNewUser(),pInfo.getDurationType(),pInfo.getProjectDuration()));
		rateResp.setInterest(MoneyUtils.FormatMoney(willProfit));
		return rateResp;
	}


	/**
	 * 收益计算器项目名称转化
	 * @param isNewUser
	 * @param durationType
	 * @param projectDuration
     * @return
     */
	public static String getCalProjectName(String isNewUser, String durationType,Long  projectDuration){
		String projectName = "月花生";
		if(ProjectConstant.DICT_DEFAULT_VALUE.equals(isNewUser)){
			projectName = "新花生";
		}else{
			if(ProjectConstant.PROJECT_DURATION_TYPE_MONTHLY.equals(durationType)){
				if(projectDuration == 1L){
					projectName = "月花生";
				}else if(projectDuration == 2L){
					projectName = "双月花生";
				}else if(projectDuration == 3L){
					projectName = "季花生";
				}else if(projectDuration == 6L){
					projectName = "双季花生";
				}else if(projectDuration == 12L){
					projectName = "年花生";
				}
			}else{
				projectName = "日花生";
			}
		}
		return projectName;
	}
	
	/**
	 * 其他第三方平台收益计算
	 * @param ratePara
	 * @param pInfo
	 * @param amount
	 * @return
	 */
	public ExchangeRateResp getCalThird(SysExchangeRatePara ratePara, ProjectBaseInfo pInfo, String amount){
		ratePara = ratePara != null ? ratePara : new SysExchangeRatePara();
		Double annualizedRate = ratePara.getAnnualizedRate() !=null ? ratePara.getAnnualizedRate() : 0.00;
		//计息开始日期
		Date beginInterestDate = DateUtils.dateFormate(new Date());
		//投资收益
		Double willProfit = ProjectUtil.calThirdWillProfit(pInfo, LoanConstant.REPAYMNET_METHOD_ONE_TIME, annualizedRate, StringUtils.toDouble(amount), beginInterestDate);
		ExchangeRateResp rateResp = new ExchangeRateResp();
		rateResp.setAnnualizedRate(annualizedRate);
		rateResp.setProjectName(StringUtil.dealString(ratePara.getRateName()));
		rateResp.setInterest(MoneyUtils.FormatMoney(willProfit));
		return rateResp;
	}
}
