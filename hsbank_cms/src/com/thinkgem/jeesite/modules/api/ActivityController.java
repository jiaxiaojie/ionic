/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1015Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.entity.Gamble;
import com.thinkgem.jeesite.modules.entity.JoinMatchRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.marketing.MarketingUtils;
import com.thinkgem.jeesite.modules.marketing.service.GambleService;
import com.thinkgem.jeesite.modules.marketing.service.JoinMatchRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;

/**
 * 活动Controller
 * 
 * @author ydt
 * @version 2016-04-20
 */
@Controller("apiActivityController")
@RequestMapping("${frontPath}/api/activity")
public class ActivityController extends APIBaseController {
	@Autowired
	private MarketFacadeService marketFacadeService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private JoinMatchRecordService joinMatchRecordService;
	@Autowired
	private GambleService gambleService;

	/**
	 * 参加活动/竞猜
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param code
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "joinActivity", method = RequestMethod.POST)
	public String joinActivity(HttpServletResponse response, HttpServletRequest request, String client, String token, String code,String param) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api joinActivity start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, opTerm);
		if(clientToken != null ) {
			Map<String,Object> para = new HashMap<String,Object>();
			para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, clientToken.getCustomerId());
			para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
			para.put(MarketConstant.CHANNEL_PARA, opTerm);
			para.put(MarketConstant.HANDLER_PARA, "marketActivity" + (code == null ? "-1" : code) + "Handler");
			para.put("side", "1".equals(param) ? MarketConstant.MATCH_SIDE_RED : ("2".equals(param) ? MarketConstant.MATCH_SIDE_BLUE : param));
			marketFacadeService.luckDraw(para);
			Map<String,Object> resultData = new HashMap<String,Object>();
			if(para.get("isSuccess") == null) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "活动失效", false);
			} else if(!(boolean)para.get("isSuccess")) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, (String)para.get("message"), false);
			} else {
				resultData.put("prizeType", para.get("prizeType"));
				resultData.put("prizeTypeName", String.valueOf(para.get("prizeTypeName")));
				resultData.put("prize", String.valueOf(para.get("prize")));
				ApiUtil.mapRespData(map, resultData, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}
		} else {
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api joinActivity end...");
		logger.info("api joinActivity total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}
	
	/**
	 * 已支持的团队
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "supportTeam", method = RequestMethod.POST)
	public String supportTeam(HttpServletResponse response, HttpServletRequest request, String client, String token, String code) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api joinActivity start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token, ApiUtil.getOperTerm(client));
		if(clientToken != null ) {
			if("999".equals(code)) {
				MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService
						.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1015Handler.class.getSimpleName()));
				final double winSideAmount = 20000;
				final double loseSideAmount = 10000;
				double redSideAmount = joinMatchRecordService
						.getSideInvestmentAmount(marketingActivityInfo.getActicityId(), MarketConstant.MATCH_SIDE_RED, marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
				double blueSideAmount = joinMatchRecordService
						.getSideInvestmentAmount(marketingActivityInfo.getActicityId(), MarketConstant.MATCH_SIDE_BLUE, marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
				double redSideAwardAmount = redSideAmount >= blueSideAmount ? winSideAmount : loseSideAmount;
				double blueSideAwardAmount = redSideAwardAmount == winSideAmount ? loseSideAmount : winSideAmount;
				JoinMatchRecord joinMatchRecord = joinMatchRecordService.getByActivityIdAndAccountId(marketingActivityInfo.getActicityId(), clientToken.getCustomerId());
				if(joinMatchRecord != null) {
					double userInvestmentAmount = joinMatchRecordService
							.getUserInvestmentAmount(marketingActivityInfo.getActicityId(), joinMatchRecord.getAccountId(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
					double awardAmount = 0d;
					if(MarketConstant.MATCH_SIDE_RED.equals(joinMatchRecord.getSide()) && redSideAmount != 0) {
						awardAmount = NumberUtils.div(NumberUtils.mul(userInvestmentAmount, redSideAwardAmount), redSideAmount, 2);
					} else if(MarketConstant.MATCH_SIDE_BLUE.equals(joinMatchRecord.getSide()) && blueSideAmount != 0) {
						awardAmount = NumberUtils.div(NumberUtils.mul(userInvestmentAmount, blueSideAwardAmount), blueSideAmount, 2);
					}
					Map<String,Object> resultData = new HashMap<>();
					resultData.put("teamNo", MarketConstant.MATCH_SIDE_RED.equals(joinMatchRecord.getSide()) ? 1 : 2);
					resultData.put("teamName", userInvestmentAmount);
					resultData.put("amount", awardAmount);
					ApiUtil.mapRespData(map, resultData, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}
			}
			else {
				MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.getByBizClassName("marketActivity" + (code == null ? "-1" : code) + "Handler");
				if (!MarketingUtils.check(marketingActivityInfo)) {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "活动失效", false);
				} else if ("1015".equals(code)) {
					activity1015SupportTeam(marketingActivityInfo.getActicityId(), clientToken.getCustomerId(), map);
				} else if ("1016".equals(code)) {
					activity1016SupportTeam(marketingActivityInfo.getActicityId(), clientToken.getCustomerId(), map);
				}
			}
		} else {
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api supportTeam end...");
		logger.info("api supportTeam total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	private void activity1015SupportTeam(Long activityId, Long accountId, Map<String,Object> map) {
		JoinMatchRecord joinMatchRecord = joinMatchRecordService.getByActivityIdAndAccountId(activityId, accountId);
		if(joinMatchRecord == null) {
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "未参加比赛", false);
		} else {
			Map<String,Object> resultData = new HashMap<String,Object>();
			resultData.put("teamNo", MarketConstant.MATCH_SIDE_RED.equals(joinMatchRecord.getSide()) ? 1 : 2);
			resultData.put("teamName", MarketConstant.MATCH_SIDE_RED.equals(joinMatchRecord.getSide()) ? "红队" : "蓝队");
			ApiUtil.mapRespData(map, resultData, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}
	}

	private void activity1016SupportTeam(Long activityId, Long accountId, HashMap<String, Object> map) {
		Gamble gamble = gambleService.getByActivityIdAndAccountIdAndOpDate(activityId, accountId, new Date());
		if(gamble == null) {
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "未参加竞猜", false);
		} else {
			Map<String,Object> resultData = new HashMap<String,Object>();
			resultData.put("teamNo", MarketConstant.MATCH_SIDE_RED.equals(gamble.getChoiceSide()) ? "1" : "2");
			resultData.put("teamName", MarketConstant.MATCH_SIDE_RED.equals(gamble.getChoiceSide()) ? "红队" : "蓝队");
			ApiUtil.mapRespData(map, resultData, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}
	}
}
