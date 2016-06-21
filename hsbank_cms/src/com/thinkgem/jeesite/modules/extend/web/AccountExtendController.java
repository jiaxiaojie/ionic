/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.extend.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.to.MyInvestmentResp;
import com.thinkgem.jeesite.modules.api.to.ProjectBaseInfoResp;
import com.thinkgem.jeesite.modules.customer.service.CustomerBalanceService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentSplitRecord;
import com.thinkgem.jeesite.modules.project.service.ProjectBaseInfoService;
import com.thinkgem.jeesite.modules.project.service.ProjectRepaymentSplitRecordService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.hsbank.util.tool.MobileUtil;
import com.hsbank.util.type.NumberUtil;

/**
 * 登录Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping("${frontPath}/extend/account")
public class AccountExtendController extends BaseController {
	@Autowired
	private CustomerBalanceService customerBalanceService;
	@Autowired
	private ProjectRepaymentSplitRecordService projectRepaymentSplitRecordService;
	@Autowired
	private ProjectBaseInfoService projectBaseInfoService;
	@Autowired
	private CustomerBaseService customerBaseService;

	
	
	/**
	 * 手机号码是否已注册
	 * @param response
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "hasRegistered", method = RequestMethod.POST)
	public String hasRegistered(HttpServletResponse response, String client, String mobile) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api hasRegistered start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(mobile)){
			if(checkIsMobilePhone(mobile, map)){
				if(checkMobileCanUse(mobile)){
					logger.info("check the mobile：" + mobile);
					map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
					map.put(ApiConstant.API_STATUS_TEXT, "未注册");
				}else{
					map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_SUCCESS);
					map.put(ApiConstant.API_STATUS_TEXT, "已注册");
				}
			}
		}else{
			map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
			map.put(ApiConstant.API_STATUS_TEXT, "手机号码不能为空");
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api hasRegistered end...");
		logger.info("api hasRegistered total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 检查手机是否可用
	 * @param mobile
	 * @return
	 */
	public boolean checkMobileCanUse(String mobile) {
		CustomerBase customerBase = new CustomerBase();
		customerBase.setMobile(mobile);
		return customerBaseService.checkMobileCanUse(customerBase);
	}
	
	/**
	 * 检查手机号码
	 * @param mobile
	 * @param map
	 * @return
	 */
	public boolean checkIsMobilePhone(String mobile, HashMap<String, Object> map) {
		logger.info("mobile =#####" + mobile + "#####");
		if(MobileUtil.isMobile(mobile)){
			return true;
		}else{
			map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
			map.put(ApiConstant.API_STATUS_TEXT, "手机号码不合法");
		}
		return false;
	}
	
	/**
	 * 我的投资
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "myInvestment", method = RequestMethod.POST)
	public String myInvestment(HttpServletResponse response, String client, String subid, String flag) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myInvestment start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerBalance customerBalance = customerBalanceService.getByExtendNo(subid);
		if(customerBalance != null){
			List<ProjectBaseInfo> projectList = projectBaseInfoService.findMyInvestmentByExtendNoList(subid + "", flag);
			//我的投资数据
			myInvestmentSuccess(customerBalance, projectList, map);
		}else{
			map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
			map.put(ApiConstant.API_STATUS_TEXT, "该渠道商编码对应的客户不存在");
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myInvestment end...");
		logger.info("api myInvestment total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 我的投资数据
	 * @param customerBalance
	 * @param projectList
	 * @param map
	 */
	public void myInvestmentSuccess(CustomerBalance customerBalance, List<ProjectBaseInfo> projectList, HashMap<String, Object> map){
		List<ProjectBaseInfoResp> projectBaseInfoRespList = new ArrayList<ProjectBaseInfoResp>();
		for(ProjectBaseInfo pInfo : projectList){
			ProjectBaseInfoResp pResp = new ProjectBaseInfoResp();
			pResp.setProjectId(NumberUtil.toLong(pInfo.getProjectId(),0L));
			pResp.setProjectCode(pInfo.getProjectCode());
			pResp.setRecordId(pInfo.getPir().getId());
			pResp.setProjectType(pInfo.getProjectTypeCode());
			pResp.setProjectTypeName(DictUtils.getDictLabel(String.valueOf(pInfo.getProjectTypeCode()), "project_type_dict", ""));
			pResp.setProjectDuration(pInfo.getProjectDuration());
			pResp.setProjectName(pInfo.getProjectName());
			pResp.setRepaymentMode(NumberUtil.toLong(pInfo.getRepaymentMode(), 0L));
			pResp.setRepaymentModeName(DictUtils.getDictLabel(pInfo.getRepaymentMode(), "project_repayment_mode_dict", ""));
			pResp.setAmount(pInfo.getPir().getAmount());
			ProjectRepaymentSplitRecord pSplitRecord = projectRepaymentSplitRecordService.getRepaymentInfoByProjectAndccountId(pInfo.getProjectId(), customerBalance.getAccountId(), NumberUtils.toLong(pInfo.getPir().getId(), 0L));
			pResp.setReceivedProfit(pSplitRecord.getSumReceivedProfit());
			pResp.setWillProfit(pSplitRecord.getSumWillProfit());
			pResp.setStatus(NumberUtil.toLong(pInfo.getProjectStatus(), 0));
			pResp.setStatusName(DictUtils.getDictLabel(pInfo.getProjectStatus(), "project_status_dict", ""));
			pResp.setInvestStatus(NumberUtil.toLong(pInfo.getPir().getStatus(),0));
			pResp.setInvestStatusName(DictUtils.getDictLabel(pInfo.getPir().getStatus(), "project_investment_status_dict", ""));
			pResp.setAnnualizedRate(pInfo.getAnnualizedRate());
			pResp.setRemainingDays((long)DateUtils.getDistanceOfTwoDate(DateUtils.dateFormate(new Date()), pSplitRecord.getLastRepaymentDt()));
			pResp.setOpDt(DateUtils.formatDate(pInfo.getPir().getOpDt()));
			pResp.setLastRepaymentDate(DateUtils.formatDate(pSplitRecord.getLastRepaymentDt()));
			pResp.setIsNewUser(pInfo.getIsNewUser());
			pResp.setIsRecommend("1".equals(pInfo.getIsRecommend()) ? "0" : "1");
			projectBaseInfoRespList.add(pResp);
		}
		MyInvestmentResp data = new MyInvestmentResp();
		data.setTotalInvestment(customerBalance.getSumInvestment());
		data.setReceiveMoney(customerBalance.getReceiveMoney());
		data.setTipMsg("");
		data.setProjectList(projectBaseInfoRespList);
		map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_SUCCESS);
		map.put(ApiConstant.API_STATUS_TEXT, "ok");
		map.put(ApiConstant.API_RESP_DATA, data);
	}
}
