/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.TokenUtils;
import com.thinkgem.jeesite.modules.api.annotation.Authenticate;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.frame.util.APIUtils;
import com.thinkgem.jeesite.modules.api.frame.util.APIUtils.ConvertAction;
import com.thinkgem.jeesite.modules.api.to.CurrentChangeHisResp;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.current.handler.CurrentPrincipalHandler;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountInterestChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentAccountPrincipalChangeHisService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectHoldInfoService;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 活期产品Controller
 * 
 * @author lizibo
 * @version 2016-1-12
 */
@Controller
@RequestMapping(value="${frontPath}/api/current",method=RequestMethod.POST)
public class CurrentController extends APIBaseController {
	@Autowired
	private CurrentProjectInfoService currentProjectInfoService;
	@Autowired
	private CurrentAccountPrincipalChangeHisService currentAccountPrincipalChangeHisService;
	@Autowired
	private CurrentAccountInterestChangeHisService currentAccountInterestChangeHisService;
	@Autowired
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	@Autowired
	private CurrentProjectHoldInfoService currentProjectHoldInfoService;

	/**
	 *  活期产品详细信息
	 * @param response
	 * @param request
	 * @param client
	 * @param projectId 活期项目id
	 * @author 万端瑞
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "detail")
	public Map<String,Object> detail(String client,String projectId){
		
		Map<String,Object> map = APIUtils.toAPIMap(currentProjectInfoService.get(projectId), new ConvertAction<CurrentProjectInfo>() {
			@Override
			public Map<String, Object> convert(CurrentProjectInfo currentProjectInfo) {

				Map<String,Object> dataMap = JsonUtils.beanToMap(currentProjectInfo, "CurrentProjectInfoDetail");
				
				

				String statusName = DictUtils.getDictLabel(currentProjectInfo.getStatus(), "current_project_status", "");
				
				Double amount = currentProjectInfo.getFinanceMoney()-currentProjectInfo.getSnapshot().getHasFinancedMoney();
				
				
				Double rate = ApiUtil.formatRate((currentProjectInfo.getSnapshot().getHasFinancedMoney()/currentProjectInfo.getFinanceMoney()) * 100);
				String interestOf10000Yuan = String.valueOf(NumberUtils.formatNotRoundWithScale((10000*currentProjectInfo.getRate()/CurrentProjectConstant.DAYS_IN_YEAR)+"", 2));
				String startDate = "T+0";
				Double hasFinancedMoney = currentProjectInfo.getSnapshot().getHasFinancedMoney();
				
				//获得总购买人数
				CurrentAccountPrincipalChangeHis queryEntity = new CurrentAccountPrincipalChangeHis();
				queryEntity.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT);
				queryEntity.setStatus(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL);
				queryEntity.setProjectId(Long.parseLong(currentProjectInfo.getId()));
				Integer buyCount = currentAccountPrincipalChangeHisService.getCount(queryEntity);
				
				Double hasRepaidMoney = currentProjectInfo.getSnapshot().getHasRepaidMoney();
				
				dataMap.put("projectType", null);
				dataMap.put("status", Integer.parseInt(currentProjectInfo.getStatus()));
				dataMap.put("projectId", Integer.parseInt(currentProjectInfo.getId()));
				dataMap.put("amount", NumberUtils.formatWithScale(amount, 2));
				dataMap.put("statusName", statusName);
				dataMap.put("rate", rate);
				dataMap.put("annualizedRateNormal", currentProjectInfo.getRate());
				dataMap.put("annualizedRateAdd", 0);
				
				dataMap.put("interestOf10000Yuan", interestOf10000Yuan);
				dataMap.put("startDate", startDate);
				dataMap.put("hasFinancedMoney", hasFinancedMoney);
				dataMap.put("buyCount", buyCount);
				dataMap.put("hasRepaidMoney", NumberUtils.formatNotRoundWithScale(hasRepaidMoney,2));
				dataMap.put("activityRemark", "一元起投，灵活存取，按日计息");
				dataMap.put("terminalCodes", "0,1,2,3");
				dataMap.put("repaymentMode", "按日计息");
				
				return dataMap;
			}

			
		});
		
		
		return map;
	}
	
	/**
	 * 活期项目分页列表
	 * @param client
	 * @param pageSize
	 * @param pageNumber
	 * @author 万端瑞
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pageList")
	public Map<String,Object> pageList(String client,
			@RequestParam(required=false,defaultValue="10")Integer pageSize, 
			@RequestParam(required=false,defaultValue="1")Integer pageNumber){
		Page<CurrentProjectInfo> page = currentProjectInfoService.searchPage(null, null,pageNumber, pageSize," a.review_dt desc");
		Map<String,Object> map = APIUtils.toAPIMap(page.getList(),new ConvertAction<CurrentProjectInfo>(){
			@Override
			public Map<String, Object> convert(CurrentProjectInfo currentProjectInfo) {
				CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot = currentProjectExecuteSnapshotDao.getByProjectId(Long.parseLong(currentProjectInfo.getId()));
				Map<String,Object> dataMap = JsonUtils.beanToMap(currentProjectInfo, "CurrentProjectInfo");
				Double amount = currentProjectInfo.getFinanceMoney()-currentProjectExecuteSnapshot.getHasFinancedMoney();
				Double rate = NumberUtils.formatWithScale((currentProjectExecuteSnapshot.getHasFinancedMoney()/currentProjectInfo.getFinanceMoney())*100,1);
				String statusName = DictUtils.getDictLabel(currentProjectInfo.getStatus(), "current_project_status", "");
				String interestOf10000Yuan = String.valueOf(NumberUtils.formatNotRoundWithScale((10000*currentProjectInfo.getRate()/CurrentProjectConstant.DAYS_IN_YEAR)+"", 2));
				dataMap.put("annualizedRateNormal", currentProjectInfo.getRate());
				dataMap.put("annualizedRateAdd", 0);
				dataMap.put("status", Integer.parseInt(currentProjectInfo.getStatus()));
				dataMap.put("projectId", Integer.parseInt(currentProjectInfo.getId()));
				dataMap.put("amount", NumberUtils.formatWithScale(amount,2));
				dataMap.put("statusName", statusName);
				dataMap.put("rate", rate);
				dataMap.put("interestOf10000Yuan", interestOf10000Yuan);
				dataMap.put("activityRemark", "一元起投，灵活存取，按日计息");
				dataMap.put("terminalCodes", "0,1,2,3");
				return dataMap;
			}
			
		});
		
		return map;
	}
	
	/**
	 * 我的活花生
	 * @param client
	 * @param token
	 * @author 万端瑞
	 * @return
	 */
	@Authenticate
	@ResponseBody
	@RequestMapping(value = "myCurrent")
	public Map<String,Object> myCurrent(String client){
		//获得当前用户
		CustomerClientToken customerClientToken = TokenUtils.getCurrentCustomerClientToken();
		
		
		//查询数据
		List<CurrentProjectHoldInfo> myCurrentProjectHoldInfos =  currentProjectHoldInfoService.searchMyList(customerClientToken.getCustomerId());
		
		//将数据包装成Api的Map形式
		Map<String,Object> apiMap = APIUtils.toAPIMap(myCurrentProjectHoldInfos, new ConvertAction<CurrentProjectHoldInfo>(){
			@Override
			public Map<String, Object> convert(CurrentProjectHoldInfo currentProjectHoldInfo) {
				CurrentProjectInfo currentProjectInfo = currentProjectInfoService.get(currentProjectHoldInfo.getProjectId().toString());
				currentProjectHoldInfo.setCurrentProjectInfo(currentProjectInfo); 
				Map<String,Object> dataMap = JsonUtils.beanToMap(currentProjectHoldInfo, "CurrentProjectHoldInfo");
				BigDecimal receivedProfit = new BigDecimal(currentProjectHoldInfo.getTotalProfit());
				receivedProfit = receivedProfit.setScale(4,   BigDecimal.ROUND_HALF_UP);
				dataMap.put("receivedProfit", receivedProfit);
				dataMap.put("amount", NumberUtils.formatWithScale(currentProjectHoldInfo.getPrincipal(),2));
				return dataMap;
			}
		});
		
		return apiMap;
	}
	
	
	/**
	 * 我的活花生--项目详情
	 * @param client
	 * @param token
	 * @param projectId
	 * @author 万端瑞
	 * @return
	 * @throws Exception 
	 */
	@Authenticate
	@ResponseBody
	@RequestMapping(value = "myCurrentDetail")
	public Map<String,Object> myCurrentDetail(String client,String projectId) throws Exception{
		//获得当前用户
		CustomerClientToken customerClientToken = TokenUtils.getCurrentCustomerClientToken();
		//创建一个空的APIMap
		Map<String, Object> ApiMap = APIUtils.createSuccessAPIMap();
		
		//-----收集数据----------
		String changeType = CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST;
		//昨日收益
		Double yesterdayProfit = currentAccountInterestChangeHisService.getYesterdayProfitByAccountIdAndProjectId(customerClientToken.getCustomerId(), projectId, changeType);
		yesterdayProfit = NumberUtils.formatWithScale(yesterdayProfit,4);
		//累计收益
		Double sumProfit = currentAccountInterestChangeHisService.getSumProfitByAccountIdAndProjectId(customerClientToken.getCustomerId(), projectId, changeType);
		sumProfit = NumberUtils.formatWithScale(sumProfit,4); 
		//持有信息
		CurrentProjectHoldInfo holdInfo = currentProjectHoldInfoService.getMyCurrentProjectHoldInfo(customerClientToken.getCustomerId(), projectId, "");
		if(holdInfo == null){
			holdInfo = new CurrentProjectHoldInfo();
			holdInfo.setPrincipal(0d);
			holdInfo.setInterest(0d);
			holdInfo.setApplyRedeemPrincipal(0d);
		}
		Double redeemingPrincipal = NumberUtils.formatWithScale(holdInfo.getApplyRedeemPrincipal(),2);
		Double canRedeemPrincipal = NumberUtils.formatWithScale(holdInfo.getPrincipal()-redeemingPrincipal,2);
		
		
		//项目详情数据
		Map<String, Object> detail = CurrentController.this.detail(client, projectId);
		Object projectJson = detail.get(ApiConstant.API_RESP_DATA);
		
		//封装数据
		Map<String, Object> accountJson = new LinkedHashMap<String, Object>();
		accountJson.put("principal", NumberUtils.formatWithScale(holdInfo.getPrincipal(),2));
		accountJson.put("interest", NumberUtils.formatWithScale(holdInfo.getInterest(),4));
		
		accountJson.put("redeemingPrincipal", redeemingPrincipal);
		accountJson.put("canRedeemPrincipal", canRedeemPrincipal);
		
		accountJson.put("totalInterest", sumProfit);
		accountJson.put("interestOfYesterday", yesterdayProfit);
		
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("account", accountJson);
		data.put("project", projectJson);
		APIUtils.transferApiStatus(ApiMap, detail);
		
		//数据放入返回MAP中
		ApiMap.put(ApiConstant.API_RESP_DATA, data);
		
		
		return ApiMap;
	}
	
	
	
	/**
	 * 活期收益计算
	 * @param response
	 * @param client
	 * @param projectId
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "interestCalculation", method = RequestMethod.POST)
	public String interestCalculation(HttpServletResponse response, String client, String projectId, String amount) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api current interestCalculation start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CurrentProjectInfo cInfo= currentProjectInfoService.get(projectId);
		if(cInfo != null){
			//活期日收益
			Double willProfit = CurrentPrincipalHandler.calculateDayProfit(NumberUtil.toDouble(amount, 0.0), cInfo.getRate());
			BigDecimal profit = new BigDecimal(willProfit);
			profit = profit.setScale(2,   BigDecimal.ROUND_HALF_UP); 
			ApiUtil.mapRespData(map, String.valueOf(profit), ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			map.put(ApiConstant.API_STATUS_CODE, ApiConstant.API_OPERA_FAIL);
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api current interestCalculation end...");
		logger.info("api current interestCalculation total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	/**
	 * 提取收益
	 * @param response
	 * @param client
	 * @param token
	 * @param projectId
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "redeemInterest", method = RequestMethod.POST)
	public String redeemInterest(HttpServletResponse response, String client, String token, String projectId, String amount) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api current redeemInterest start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
		if(clientToken != null){
			Long accountId = clientToken.getCustomerId();
			Map<String, Object> reMap = new HashMap<String, Object>();
			try {
				reMap = currentProjectHoldInfoService.doPollInterest(accountId, NumberUtil.toLong(projectId, 0L), NumberUtil.toDouble(amount, 0.0), opTerm);
				if((boolean) reMap.get("isSuccess")){
					ApiUtil.resultMapData(ApiConstant.API_RESULT_SUCCESS,
							String.valueOf(reMap.get("message")),
							ApiConstant.API_OPERA_SUCCESS,
							ApiConstant.API_STATUS_TEXT_VALUE, reMap, map);
				}else{
					ApiUtil.resultMapData(ApiConstant.API_RESULT_FAIL,
							String.valueOf(reMap.get("message")),
							ApiConstant.API_OPERA_FAIL,
							"fail", reMap, map);
				}
			} catch (Exception e) {
				ApiUtil.resultMapData(ApiConstant.API_RESULT_FAIL,
						e.getMessage(),
						ApiConstant.API_OPERA_FAIL,
						"fail", reMap, map);
			}
		}else{
			//token已失效
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api current redeemInterest end...");
		logger.info("api current redeemInterest total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 赎回本金
	 * @param response
	 * @param client
	 * @param token
	 * @param projectId
	 * @param amount
	 * @return
	 */
	@RequestMapping(value = "redeemPrincipal", method = RequestMethod.POST)
	public String redeemPrincipal(HttpServletResponse response, String client, String token, String projectId, String amount) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api current redeemPrincipal start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null){
			Long accountId = clientToken.getCustomerId();
			Map<String,Object> reMap = currentProjectHoldInfoService.doRedeemPrincipal(accountId, NumberUtil.toLong(projectId, 0L), NumberUtil.toDouble(amount, 0.0), opTerm);
			if((boolean) reMap.get("isSuccess")){
				ApiUtil.resultMapData(ApiConstant.API_RESULT_SUCCESS,
						String.valueOf(reMap.get("message")),
						ApiConstant.API_OPERA_SUCCESS,
						ApiConstant.API_STATUS_TEXT_VALUE, reMap, map);
			}else{
				ApiUtil.resultMapData(ApiConstant.API_RESULT_FAIL,
						String.valueOf(reMap.get("message")),
						ApiConstant.API_OPERA_FAIL,
						"fail", reMap, map);
			}
		}else{
			//token已失效
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api current redeemPrincipal end...");
		logger.info("api current redeemPrincipal total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	/**
	 * 指定活花生项目的本金变化
	 * @param response
	 * @param client
	 * @param token
	 * @param projectId
	 * @param changeType
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "myCurrentPrincipalPageList", method = RequestMethod.POST)
	public String myCurrentPrincipalPageList(HttpServletResponse response, String client, String token, String projectId, String changeType, Integer pageNumber, Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myCurrentPrincipalPageList start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			Long accountId = clientToken.getCustomerId();
			List<CurrentAccountPrincipalChangeHis> list = currentAccountPrincipalChangeHisService.getPrincipalPageList(accountId, projectId, changeType, pageNumber, pageSize);
			List<Object> dataList = new ArrayList<Object>();
			principalList(list, dataList);
			ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myCurrentPrincipalPageList end...");
		logger.info("api myCurrentPrincipalPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 本金变化列表
	 * @param list
	 * @param dataList
	 */
	public void principalList(List<CurrentAccountPrincipalChangeHis> list, List<Object> dataList){
		for(CurrentAccountPrincipalChangeHis cHis : list){
			CurrentChangeHisResp data = new CurrentChangeHisResp();
			data.setOpDt(DateUtils.formatDateTime(cHis.getOpDt()));
			data.setChangeType(cHis.getChangeType());
			data.setChangeTypeName(DictUtils.getDictLabel(String.valueOf(cHis.getChangeType()), "current_account_principal_change_type", ""));
			String changeValue = "";
			if(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT.equals(cHis.getChangeType())){
				changeValue = String.valueOf(new BigDecimal(cHis.getChangeValue()).setScale(0,   BigDecimal.ROUND_HALF_UP));
			}else{
				changeValue = String.valueOf(new BigDecimal(cHis.getChangeValue()).setScale(2,   BigDecimal.ROUND_HALF_UP));
			}
			data.setChangeVal(changeValue);
			dataList.add(data);
		}
	}
	
	
	/**
	 * 指定活花生项目的利息变化
	 * @param response
	 * @param client
	 * @param token
	 * @param projectId
	 * @param changeType
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "myCurrentInterestPageList", method = RequestMethod.POST)
	public String myCurrentInterestPageList(HttpServletResponse response, String client, String token, String projectId, String changeType, Integer pageNumber, Integer pageSize) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myCurrentInterestPageList start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		if(clientToken != null ){
			Long accountId = clientToken.getCustomerId();
			List<CurrentAccountInterestChangeHis> list = currentAccountInterestChangeHisService.getInterestPageList(accountId, projectId, changeType, pageNumber, pageSize);
			List<Object> dataList = new ArrayList<Object>();
			interestList(list, dataList);
			ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api myCurrentInterestPageList end...");
		logger.info("api myCurrentInterestPageList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	
	/**
	 * 利息变化列表
	 * @param list
	 * @param dataList
	 */
	public void interestList(List<CurrentAccountInterestChangeHis> list, List<Object> dataList){
		for(CurrentAccountInterestChangeHis cHis : list){
			CurrentChangeHisResp data = new CurrentChangeHisResp();
			data.setOpDt(DateUtils.formatDateTime(cHis.getOpDt()));
			data.setChangeType(cHis.getChangeType());
			data.setChangeTypeName(DictUtils.getDictLabel(String.valueOf(cHis.getChangeType()), "current_account_interest_change_type", ""));
			String changeValue = "";
			if(CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST.equals(cHis.getChangeType())){
				changeValue = String.valueOf(new BigDecimal(cHis.getChangeValue()).setScale(4,   BigDecimal.ROUND_HALF_UP));
			}else{
				changeValue = String.valueOf(new BigDecimal(cHis.getChangeValue()).setScale(2,   BigDecimal.ROUND_HALF_UP));
			}
			data.setChangeVal(changeValue);
			dataList.add(data);
		}
	}
}
