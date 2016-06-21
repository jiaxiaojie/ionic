/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import com.hsbank.util.type.NumberUtil;
import com.hsbank.util.type.StringUtil;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.api.base.APIBaseController;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.po.MesInfoReq;
import com.thinkgem.jeesite.modules.api.to.CarouselResp;
import com.thinkgem.jeesite.modules.api.to.InvestmentRankResp;
import com.thinkgem.jeesite.modules.api.to.MobileVersionResp;
import com.thinkgem.jeesite.modules.carousel.service.CarouselInfoService;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.customer.service.CustomerNewTaskService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.feedback.service.CustomerLeaveMessageService;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.marketing.MarketingUtils;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1012Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1015Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1020Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1021Handler;
import com.thinkgem.jeesite.modules.marketing.service.*;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRankService;
import com.thinkgem.jeesite.modules.sys.service.SysMobileVersionParaService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 事件Controller
 *
 * @author lzb
 * @version 2015-12-31
 */
@Controller
@RequestMapping("${frontPath}/api/event")
public class EventController extends APIBaseController {
	@Autowired
	private SysMobileVersionParaService sysMobileVersionParaService;
	@Autowired
	private CustomerNewTaskService customerNewTaskService;
	@Autowired
	private CarouselInfoService carouselInfoService;
	@Autowired
	private MarketFacadeService marketFacadeService;
	@Autowired
	private MarketingWheelGetPrizeRecordService marketingWheelGetPrizeRecordService;
	@Autowired
	private ProjectInvestmentRankService projectInvestmentRankService;
	@Autowired
	private MarketingWheelLotteryTimesService marketingWheelLotteryTimesService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private MarketingShareRecordService marketingShareRecordService;
	@Autowired
	private CustomerLeaveMessageService customerLeaveMessageService;
	@Autowired
	private MarketActivity1012Handler marketActivity1012Handler;
	@Autowired
	private JoinMatchRecordService joinMatchRecordService;
	@Autowired
	private GambleService gambleService;
	@Autowired
	private MarketingWheelPrizeInfoService marketingWheelPrizeInfoService;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private MobileAwardRecordService mobileAwardRecordService;
	@Autowired
	private SachetRecordService sachetRecordService;
	@Autowired
	private MarketActivity1020Handler marketActivity1020Handler;
	@Autowired
	private MarketActivity1021Handler marketActivity1021Handler;
	@Autowired
	private EuropeanCupTopScorerService europeanCupTopScorerService;


	/**
	 * Android自动更新接口
	 * @param response
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "checkUpdate", method = RequestMethod.POST)
	public String checkUpdate(HttpServletResponse response, String client) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api androidUpdate start...");
		String jsonStr = Base64Utils.getDecodeBASE64(client);
		logger.info("client:" + client);
		ClientProperty cProperty = (ClientProperty)JsonMapper.fromJsonString(jsonStr, new ClientProperty().getClass());
		if(cProperty == null){
			cProperty = new ClientProperty();
		}
		String channel = "";
		String type = ApiUtil.getOperaChannel(cProperty.getType());
		if(ProjectConstant.OP_TERM_DICT_ANDROID.equals(type) && cProperty.getAndroid()!= null){
			channel = cProperty.getAndroid().getChannel();
		}
		String oldVersion = cProperty.getVersion();
		logger.info("channel:" + channel);
		logger.info("type:" + type);
		HashMap<String, Object> map = new HashMap<String, Object>();
		MobileVersionResp data = new MobileVersionResp();
		SysMobileVersionPara versionPara = sysMobileVersionParaService.getSysMobileVersionPara(channel, type);
		if(versionPara !=null){
			String newVersion = versionPara.getVersion();
			String needUpdate = ApiUtil.isNewVersion(-1, oldVersion, newVersion) ? "0" : "1";
			String nfUpdate = "1".equals(versionPara.getIsForcedUpdate()) ? "0" : "1";
			String needForcedUpdate = "0".equals(needUpdate) ? nfUpdate : "1";
			data.setNeedUpdate(needUpdate);
			data.setNeedForcedUpdate(needForcedUpdate);
			data.setUrl(versionPara.getUrl());
			data.setVersion(versionPara.getVersion());
			data.setAndroidAppSize(versionPara.getVersionSize());
			data.setVersionInfo(versionPara.getVersionInfo());
			ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else{
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api androidUpdate end...");
		logger.info("api androidUpdate total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 新手任务
	 * @param response
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "newUserTask", method = RequestMethod.POST)
	public String newUserTask(HttpServletResponse response, String client) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api newUserTask start...");
		CustomerNewTask customerNewTask = new CustomerNewTask();
		customerNewTask.setStatus("1");
		List<CustomerNewTask> list = customerNewTaskService.findList(customerNewTask);
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{").append('"').append(ApiConstant.API_STATUS_CODE).append('"').append(":").append(ApiConstant.API_OPERA_SUCCESS).append(",");
		strBuf.append('"').append(ApiConstant.API_STATUS_TEXT).append('"').append(":").append('"').append("ok").append('"').append(",");
		strBuf.append('"').append(ApiConstant.API_RESP_DATA).append('"').append(":");
		strBuf.append("{");
		for(CustomerNewTask cNewTask : list){
			strBuf.append('"').append(cNewTask.getStep()).append('"').append(":").append('"').append(cNewTask.getDes()).append('"').append(",");
		}
		if(strBuf.indexOf(",")!=-1){
			strBuf = strBuf.deleteCharAt(strBuf.length()-1);
		}
		strBuf.append("}");
		strBuf.append("}");
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api newUserTask end...");
		logger.info("api newUserTask total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return this.renderString(response, strBuf.toString(), "application/json");
	}

	/**
	 * 首页轮播列表
	 * @param response
	 * @param request
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "carousel", method = RequestMethod.POST)
	public String carousel(HttpServletResponse response, HttpServletRequest request, String client) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api carousel start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Object> dataList = new ArrayList<Object>();
		//解析client
		ClientProperty cProperty = ApiUtil.getClient(client);
		String theDate = DateUtils.getDateTime();
		String term = ApiUtil.getOperaChannel(cProperty.getType());
		String isNewWebsite = ProjectConstant.DICT_DEFAULT_VALUE;
		//接口web端调用时，则为新网站轮播
		if(ProjectConstant.OP_TERM_DICT_PC.equals(term)){
			isNewWebsite = "1";
		}
		List<CarouselInfo> list = carouselInfoService.getCarouselListByStatusAndTerm(ProjectConstant.CAROUSEL_INFO_STATUS_PASS, term,isNewWebsite, theDate);
		for(CarouselInfo cInfo: list){
            CarouselResp data = new CarouselResp();
            data.setImageUrl(ApiUtil.imageUrlConver(cInfo.getPictureBig()));
            data.setTitle(cInfo.getTitle());
            data.setType(NumberUtil.toInt(cInfo.getTypeCode(),0));
            data.setTarget(cInfo.getTarget());
            dataList.add(data);
		}
		ApiUtil.mapRespData(map, dataList, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api carousel end...");
		logger.info("api carousel total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 服务端状态检查接口
	 * @param response
	 * @param request
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "checkServerStatus", method = RequestMethod.POST)
	public String checkServerStatus(HttpServletResponse response, HttpServletRequest request, String client) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api checkServerStatus start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> mapData = new HashMap<String, String>();
		mapData.put("statusCode", "0");
		mapData.put("statusText", "ok");
		ApiUtil.mapRespData(map, mapData, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api checkServerStatus end...");
		logger.info("api checkServerStatus total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 抽奖信息
	 * @param response
	 * @param request
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "lotteryInfo", method = RequestMethod.POST)
	public String lotteryInfo(HttpServletResponse response, HttpServletRequest request, String client, String token, String code) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api lotteryInfo start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> data = new HashMap<String,Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		if(clientToken != null ) {
			MarketingActivityInfo marketingActivityInfo = code == null ? null : marketingActivityInfoService.getByBizClassName("marketActivity" + code + "Handler");

			if("1011".equals(code)){
				CustomerBase customerBase = customerBaseService.getByAccountId(clientToken.getCustomerId());
				if(ProjectConstant.SEX_FEMALE.equals(customerBase.getGenderCode())){//女性用户
					Integer i = marketingActivityAwardRecordService.getCountByAccountIdAndActivityId(clientToken.getCustomerId(), marketingActivityInfo.getActicityId());
					Integer userd = (i==null || i==0?0:1);

					data.put("count", 1);
					data.put("used", userd);
					data.put("over", 1-userd);
				}
				else{
					data.put("count", 0);
					data.put("used", 0);
					data.put("over", 0);
				}
			} else if("1012".equals(code)){
				data.putAll(marketActivity1012Handler.activityInfo(clientToken.getCustomerId()));
			} else if("1019".equals(code)) {
				data.put("receiveAmount", sachetRecordService.getGetSachetCountByAccountIdAndDate(marketingActivityInfo.getActicityId(),
						clientToken.getCustomerId(), new Date()) > 0 ? 1 : 0);
				data.put("over", sachetRecordService.getSachetCountByAccountId(marketingActivityInfo.getActicityId(), clientToken.getCustomerId()));
			} else if("1020".equals(code)){
				if(!MarketingUtils.check(marketingActivityInfo)){
					data =  APIGenerator.createResultAPI(-1, "本活动已结束");
				}else {
					data = marketActivity1020Handler.lotteryInfoWithAPI(marketingActivityInfo.getActicityId(),clientToken.getCustomerId());
				}

			} else if("1021".equals(code)){
				marketingActivityInfo = marketingActivityInfoService.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1020Handler.class.getSimpleName()));
				data.putAll(marketActivity1021Handler.lotteryInfo(clientToken.getCustomerId(),marketingActivityInfo.getActicityId()));
			}else {
				if("1010".equals(code)) {
					//初始化活动1010的抽奖次数
					marketingWheelLotteryTimesService.initLotteryTimes(clientToken.getCustomerId(), marketingActivityInfo.getActicityId(),2);
				}
				MarketingWheelLotteryTimes marketingWheelLotteryTimes = marketingActivityInfo == null ? null : marketingWheelLotteryTimesService
						.getByAccountIdAndActivityId(clientToken.getCustomerId(), marketingActivityInfo.getActicityId());
				if(marketingWheelLotteryTimes != null) {
					int count = marketingWheelLotteryTimes.getTotalTimes() == null ? 0 : marketingWheelLotteryTimes.getTotalTimes();
					int used = marketingWheelLotteryTimes.getUsedTimes() == null ? 0 : marketingWheelLotteryTimes.getUsedTimes();
					data.put("count", count);
					data.put("used", used);
					data.put("over", count - used);
				} else {
					data.put("count", 0);
					data.put("used", 0);
					data.put("over", 0);
				}
			}
			ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}else {
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api lottery end...");
		logger.info("api lotteryInfo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 抽奖
	 * @param response
	 * @param request
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "lottery", method = RequestMethod.POST)
	public String lottery(HttpServletResponse response, HttpServletRequest request, String client, String token, String code,String data) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api lottery start...");
		String opTerm = ApiUtil.getOperTerm(client);
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);

		MarketingActivityInfo marketingActivityInfo = code == null ? null : marketingActivityInfoService.getByBizClassName("marketActivity" + code + "Handler");

		if(clientToken != null  && !"1018".equals(code)) {
			if("1016".equals(code)) {
				Map<String,Object> result = gambleService.giveIntegralAward(Long.parseLong(data), ApiUtil.getOperaChannel(ApiUtil.getClient(client).getType()));
				if(!(boolean)result.get("isSuccess")) {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, (String)result.get("message"), false);
				} else {
					ApiUtil.mapRespData(map, result, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}
			} else if("1019".equals(code)) {
				Map<String,Object> para = new HashMap<>();
				para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, clientToken.getCustomerId());
				para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
				para.put(MarketConstant.CHANNEL_PARA, ApiUtil.getOperaChannel(ApiUtil.getClient(client).getType()));
				para.put(MarketConstant.HANDLER_PARA, "marketActivity" + (code == null ? "-1" : code) + "Handler");
				if("1".equals(data)) {
					para.put("action", "get");
				} else if("2".equals(data)) {
					para.put("action", "exchange");
					para.put("prize", "1");
				} else if("3".equals(data)) {
					para.put("action", "exchange");
					para.put("prize", "2");
				} else if("4".equals(data)) {
					para.put("action", "exchange");
					para.put("prize", "3");
				}
				marketFacadeService.luckDraw(para);
				if(para.get("isSuccess") == null) {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "本活动已结束", false);
				} else if(!(boolean)para.get("isSuccess")) {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, para.get("message"), false);
				} else {
					Map<String,Object> resultData = new HashMap<>();
					resultData.put("result", para.get("result"));
					ApiUtil.mapRespData(map, resultData, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}
			} else if("1020".equals(code)){
				map = lottery1020(clientToken.getCustomerId(), opTerm, marketingActivityInfo,data);
			} else {
				Map<String,Object> para = new HashMap<String,Object>();
				para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, clientToken.getCustomerId());
				para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
				para.put(MarketConstant.CHANNEL_PARA, ApiUtil.getOperaChannel(ApiUtil.getClient(client).getType()));
				para.put(MarketConstant.HANDLER_PARA, "marketActivity" + (code == null ? "-1" : code) + "Handler");
				para.put(MarketConstant.FUNC_PARA, data);
				marketFacadeService.luckDraw(para);
				Map<String,Object> resultData = new HashMap<String,Object>();
				if(para.get("isSuccess") == null) {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "本活动已结束", false);
				} else if(!(boolean)para.get("isSuccess")) {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, (String)para.get("message"), false);
				} else {
					resultData.put("angle", para.get("angle"));
					resultData.put("result", para.get("result"));
					ApiUtil.mapRespData(map, resultData, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				}
			}
		} else if("1018".equals(code)) {
			Map<String,Object> para = new HashMap<>();
			para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
			para.put(MarketConstant.CHANNEL_PARA, opTerm);
			para.put(MarketConstant.HANDLER_PARA, "marketActivity1018Handler");
			para.put("mobile", data);
			marketFacadeService.luckDraw(para);
			Map<String,Object> resultData = new HashMap<>();
			if(para.get("isSuccess") == null) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "本活动已结束", false);
			} else if(!(boolean)para.get("isSuccess")) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, (String)para.get("message"), false);
			} else {
				MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoService.getByInstanceId((Long)para.get("prizeInstanceId"));
				resultData.put("result", marketingWheelPrizeInfo.getGetTips());
				ApiUtil.mapRespData(map, resultData, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}
		}  else if("1020".equals(code)){
			map = lottery1020(null, opTerm, marketingActivityInfo,data);
		} else {
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api lottery end...");
		logger.info("api lottery total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 1020射门抽奖
	 * @param accountId
	 * @param opTerm
	 * @param marketingActivityInfo
	 * @return
	 */
	private HashMap<String, Object> lottery1020(Long accountId, String opTerm, MarketingActivityInfo marketingActivityInfo,String beforeGoalResult){

		if(!MarketingUtils.check(marketingActivityInfo)){
			return APIGenerator.createResultAPI(-1, "本活动已结束");
		}

		Map<String,Object> para = new HashMap<>();
		para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, accountId);
		para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
		para.put(MarketConstant.CHANNEL_PARA, opTerm);
		para.put(MarketConstant.HANDLER_PARA, "marketActivity1020Handler");
		para.put(MarketConstant.ACTICITY_PARA, marketingActivityInfo.getActicityId());
		para.put("beforeGoalResult",beforeGoalResult==null?"":beforeGoalResult);
		marketFacadeService.luckDraw(para);



		return APIGenerator.createResultAPI((Integer) para.get("code"),(String)para.get("text"));
	}


	/**
	 * 领取奖品
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param mobile
	 * @param code
     * @param data
     * @return
     */
	@RequestMapping(value = "getPrize", method = RequestMethod.POST)
	public String getPrize(HttpServletResponse response, HttpServletRequest request, String client, String token, String mobile, String code,String data) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api getPrize start...");
		String opTerm = ApiUtil.getOperTerm(client);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> para = new HashMap<String,Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,opTerm);
		MarketingActivityInfo marketingActivityInfo = code == null ? null : marketingActivityInfoService.getByBizClassName("marketActivity" + code + "Handler");
		Long acticityId = marketingActivityInfo != null ? marketingActivityInfo.getActicityId() : null;
		if(clientToken != null  && !"1020".equals(code)) {
			if("1021".equals(code)){
				para = marketActivity1021Handler.getPrize(clientToken.getAccountId(), acticityId);
				if((boolean)para.get("isSuccess")) {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
				} else {
					ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, false);
				}
			}
		} else if("1020".equals(code)) {
			if(!MarketingUtils.check(marketingActivityInfo)){
				map = APIGenerator.createResultAPI(-1, "本活动已结束");
			}else {
				map = marketActivity1020Handler.getPrize(mobile,data, opTerm);
			}
		} else {
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api getPrize end...");
		logger.info("api getPrize total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 我的中奖信息
	 * @param response
	 * @param request
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "myPrizeList", method = RequestMethod.POST)
	public String myPrizeList(HttpServletResponse response, HttpServletRequest request, String client, String token, String code,
			Integer pageSize, Integer pageNumber) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api myPrizeList start...");
		HashMap<String, Object> map = new HashMap<>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		if(clientToken != null ) {
			Map<String, Object> data;
			if(code.equals("1016")) {
				data = getActivity1016PrizeList(code, pageSize, pageNumber, clientToken.getCustomerId());
			} else if(code.equals("1018")) {
				data = getActivity1018PrizeList(clientToken.getCustomerId());
			} else if(code.equals("1021")){
                data = marketingWheelGetPrizeRecordService.getGivePrizeList(code, pageSize, pageNumber, clientToken.getCustomerId());
			} else {
				data = getPrizeList(code, pageSize, pageNumber, clientToken.getCustomerId());
			}
			if(code.equals("1018") && data == null) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "未参加活动", false);
			} else {
				ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}
		} else {
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api lottery end...");
		logger.info("api myPrizeList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	private Map<String, Object> getActivity1018PrizeList(Long accountId) {
		Map<String, Object> data = new HashMap<>();
		CustomerAccount customerAccount = customerAccountService.get(accountId);
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.getByBizClassName("marketActivity1018Handler");
		MobileAwardRecord mobileAwardRecord = mobileAwardRecordService.getByActivityIdAndMobileAndStatus(marketingActivityInfo.getActicityId(),
				customerAccount.getCustomerBase().getMobile(), null);
		if(mobileAwardRecord != null) {
			List<Map<String,Object>> prizeList = new ArrayList<>();
			MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoService.getByInstanceId(mobileAwardRecord.getPrizeInstanceId());
			Map<String,Object> m = new HashMap<>();
			if(ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL.equals(marketingWheelPrizeInfo.getType())) {
				m.put("prizeType", 1);
				m.put("prizeTypeName", "花生豆");
			} else if(ProjectConstant.MARKETING_AWARD_TYPE_MONEY.equals(marketingWheelPrizeInfo.getType())) {
				m.put("prizeType", 3);
				m.put("prizeTypeName", "现金");
			} else if(ProjectConstant.MARKETING_AWARD_TYPE_OBJECT.equals(marketingWheelPrizeInfo.getType())) {
				m.put("prizeType", 4);
				m.put("prizeTypeName", "健康卡");
			}
			m.put("prize", marketingWheelPrizeInfo.getValue());
			m.put("date", DateUtils.formatDate(mobileAwardRecord.getOpDt(), "yyyy-MM-dd HH:mm:ss"));
			prizeList.add(m);
			data.put("count", 1);
			data.put("data", prizeList);
		} else {
			data = null;
		}
		return data;
	}

	private Map<String, Object> getActivity1016PrizeList(String code, Integer pageSize, Integer pageNumber, Long accountId) {
		Map<String, Object> data = new HashMap<>();
		int count = 0;
		pageSize = pageSize == null ? 10 : pageSize;
		pageNumber = pageNumber == null ? 1 : pageNumber;
		MarketingActivityInfo marketingActivityInfo = code == null ? null : marketingActivityInfoService.getByBizClassName("marketActivity" + code + "Handler");
		List<Map<String,Object>> prizeList = new ArrayList<>();

		List<Gamble> gambleList = gambleService.findListByActivityIdAndAccountId(marketingActivityInfo.getActicityId(), accountId);
		count = gambleList.size();
		List<Gamble> list = new ArrayList<>();
		for(int i = pageSize * (pageNumber - 1); i >= 0 && i < count && i < pageSize * pageNumber; i++) {
			list.add(gambleList.get(i));
		}
		for(Gamble gambel : list) {
			Map<String,Object> m = new HashMap<>();
			m.put("recordId", Long.parseLong(gambel.getId()));
			m.put("prizeType", 1);
			m.put("prizeTypeName", "花生豆");
			m.put("prize", "1000");
			m.put("date", DateUtils.formatDate(gambel.getOpDt(), "yyyy-MM-dd"));
			if(MarketConstant.MATCH_SIDE_RED.equals(gambel.getChoiceSide())) {
				m.put("guessTarget", 1);
				m.put("guessTargetName", "红队赢");
			} else {
				m.put("guessTarget", 2);
				m.put("guessTargetName", "蓝队赢");
			}
			if(MarketConstant.MATCH_SIDE_RED.equals(gambel.getRightSide())) {
				m.put("guessResult", 1);
				m.put("guessResultName", "红队赢");
			} else if(MarketConstant.MATCH_SIDE_BLUE.equals(gambel.getRightSide())) {
				m.put("guessResult", 2);
				m.put("guessResultName", "蓝队赢");
			} else {
				m.put("guessResult", 3);
				m.put("guessResultName", "比赛未结束");
			}
			if(gambel.getChoiceSide().equals(gambel.getRightSide())) {
				if(gambel.getAwardDt() == null) {
					m.put("status", 2);
					m.put("statusName", "未领取");
				} else {
					m.put("status", 1);
					m.put("statusName", "已领取");
				}
			} else {
				m.put("status", 0);
				m.put("statusName", "未猜中");
			}
			prizeList.add(m);
		}
		data.put("count", count);
		data.put("data", prizeList);
		return data;
	}

	private Map<String, Object> getPrizeList(String code, Integer pageSize, Integer pageNumber, Long accountId) {
		Map<String, Object> data = new HashMap<String, Object>();
		int count = 0;
		pageSize = pageSize == null ? 10 : pageSize;
		pageNumber = pageNumber == null ? 1 : pageNumber;
		MarketingActivityInfo marketingActivityInfo = code == null ? null : marketingActivityInfoService.getByBizClassName("marketActivity" + code + "Handler");
		List<Map<String,String>> prizeList = new ArrayList<Map<String,String>>();
		if(marketingActivityInfo != null) {
			List<MarketingWheelGetPrizeRecord> marketingWheelGetPrizeRecordList = marketingWheelGetPrizeRecordService.findGotListByAccountIdAndActivityId(
					accountId, marketingActivityInfo.getActicityId());
			count = marketingWheelGetPrizeRecordList.size();
			List<MarketingWheelGetPrizeRecord> list = new ArrayList<MarketingWheelGetPrizeRecord>();
			for(int i = pageSize * (pageNumber - 1); i >= 0 && i < count && i < pageSize * pageNumber; i++) {
				list.add(marketingWheelGetPrizeRecordList.get(i));
			}
			for(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord : list) {
				Map<String,String> m = new HashMap<String,String>();
				m.put("date", DateUtils.formatDate(marketingWheelGetPrizeRecord.getGetDt(), "yyyy-MM-dd HH:mm:ss"));
				m.put("prize", marketingWheelGetPrizeRecord.getPrizeName());
				prizeList.add(m);
			}
		}
		data.put("count", count);
		data.put("data", prizeList);
		return data;
	}

	/**
	 * 中奖榜单
	 * @param response
	 * @param request
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "lotteryPrizeList", method = RequestMethod.POST)
	public String lotteryPrizeList(HttpServletResponse response, HttpServletRequest request, String client, String code) {
		int limit = 5;
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api lotteryPrizeList start...");
		MarketingActivityInfo marketingActivityInfo = code == null ? null : marketingActivityInfoService.getByBizClassName("marketActivity" + code + "Handler");
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		if(marketingActivityInfo != null && !"1015".equals(code)) {
			if("1019".equals(code)) {
				data = sachetRecordService.findGetPrizeList(marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate(),MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[5]);
				for(Map<String,Object> info : data) {
					info.put("mobile", StringUtils.vagueMobile((String)info.get("mobile")));
					double amount = ((BigDecimal)info.get("amount")).doubleValue();
					if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[0]) {
						info.put("prize", "200元现金券+15000花生豆");
					} else if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[1]) {
						info.put("prize", "100元现金券+7000花生豆");
					} else if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[2]) {
						info.put("prize", "60元现金券+4000花生豆");
					} else if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[3]) {
						info.put("prize", "20元现金券+1000花生豆");
					} else if(amount >= MarketConstant.ACTIVITY_1019_INVESTMENT_AMOUNT_LIMIT[4]) {
						info.put("prize", "10元现金券");
					} else {
						info.put("prize", "2000花生豆");
					}
				}
			}else if("1020".equals(code)){
				//欧洲杯最佳射手榜
				data = europeanCupTopScorerService.getTop10Scorer(marketingActivityInfo.getActicityId(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
			}else {
				List<MarketingWheelGetPrizeRecord> moneyPrizeList = marketingWheelGetPrizeRecordService
						.findGotListByActivityIdAndPrizeType(marketingActivityInfo.getActicityId(), ProjectConstant.MARKETING_AWARD_TYPE_MONEY, limit);
				List<MarketingWheelGetPrizeRecord> ticketPrizeList = marketingWheelGetPrizeRecordService
						.findGotListByActivityIdAndPrizeType(marketingActivityInfo.getActicityId(), ProjectConstant.MARKETING_AWARD_TYPE_INVESTMENT_TICKET, limit);
				List<MarketingWheelGetPrizeRecord> integralPrizeList = marketingWheelGetPrizeRecordService
						.findGotListByActivityIdAndPrizeType(marketingActivityInfo.getActicityId(), ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL, limit);
				List<MarketingWheelGetPrizeRecord> objectPrizeList = marketingWheelGetPrizeRecordService
						.findGotListByActivityIdAndPrizeType(marketingActivityInfo.getActicityId(), ProjectConstant.MARKETING_AWARD_TYPE_OBJECT, limit);
				List<MarketingWheelGetPrizeRecord> prizeList = new ArrayList<MarketingWheelGetPrizeRecord>();
				prizeList.addAll(moneyPrizeList);
				prizeList.addAll(ticketPrizeList);
				prizeList.addAll(integralPrizeList);
				prizeList.addAll(objectPrizeList);
				Collections.shuffle(prizeList);
				for(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord : prizeList) {
					Map<String,Object> m = new HashMap<String,Object>();
					m.put("mobile", StringUtils.vagueMobile(marketingWheelGetPrizeRecord.getMobile()));
					m.put("prize", marketingWheelGetPrizeRecord.getPrizeName());
					data.add(m);
				}
			}
		}else if("999".equals(code)) {
			marketingActivityInfo = marketingActivityInfoService
					.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1015Handler.class.getSimpleName()));
			final double winSideAmount = 20000;
			final double loseSideAmount = 10000;
			double redSideAmount = joinMatchRecordService
					.getSideInvestmentAmount(marketingActivityInfo.getActicityId(), MarketConstant.MATCH_SIDE_RED, marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
			double blueSideAmount = joinMatchRecordService
					.getSideInvestmentAmount(marketingActivityInfo.getActicityId(), MarketConstant.MATCH_SIDE_BLUE, marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
			double redSideAwardAmount = redSideAmount >= blueSideAmount ? winSideAmount : loseSideAmount;
			double blueSideAwardAmount = redSideAwardAmount == winSideAmount ? loseSideAmount : winSideAmount;
			List<Map<String,Object>> redList = joinMatchRecordService.getInvestmentRankByActivityId(marketingActivityInfo.getActicityId(), MarketConstant.MATCH_SIDE_RED,
					marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate(), 10);
			List<Map<String,Object>> blueList = joinMatchRecordService.getInvestmentRankByActivityId(marketingActivityInfo.getActicityId(), MarketConstant.MATCH_SIDE_BLUE,
					marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate(), 10);
			for(Map<String,Object> info : redList) {
				double awardAmount = 0d;
				if(MarketConstant.MATCH_SIDE_RED.equals(info.get("side"))) {
					awardAmount = NumberUtils.div(NumberUtils.mul(((BigDecimal)info.get("amount")).doubleValue(), redSideAwardAmount), redSideAmount, 2);
				} else {
					awardAmount = NumberUtils.div(NumberUtils.mul(((BigDecimal)info.get("amount")).doubleValue(), blueSideAwardAmount), blueSideAmount, 2);
				}
				Map<String,Object> dateInfo = new HashMap<>();
				dateInfo.put("mobile", StringUtils.vagueMobile((String)info.get("mobile")));
				dateInfo.put("prize", awardAmount);
				dateInfo.put("belongTeam", 1);
				dateInfo.put("teamName", "红队");
				dateInfo.put("investmentAmount", info.get("amount"));
				data.add(dateInfo);
			}
			for(Map<String,Object> info : blueList) {
				double awardAmount = 0d;
				if(MarketConstant.MATCH_SIDE_RED.equals(info.get("side"))) {
					awardAmount = NumberUtils.div(NumberUtils.mul(((BigDecimal)info.get("amount")).doubleValue(), redSideAwardAmount), redSideAmount, 2);
				} else {
					awardAmount = NumberUtils.div(NumberUtils.mul(((BigDecimal)info.get("amount")).doubleValue(), blueSideAwardAmount), blueSideAmount, 2);
				}
				Map<String,Object> dateInfo = new HashMap<>();
				dateInfo.put("mobile", StringUtils.vagueMobile((String)info.get("mobile")));
				dateInfo.put("prize", awardAmount);
				dateInfo.put("belongTeam", 2);
				dateInfo.put("teamName", "蓝队");
				dateInfo.put("investmentAmount", info.get("amount"));
				data.add(dateInfo);
			}
		} else {
			data = activity1015PrizeList(marketingActivityInfo.getActicityId());
		}
		ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api lotteryPrizeList end...");
		logger.info("api lotteryPrizeList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	private List<Map<String, Object>> activity1015PrizeList(Long activityId) {
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> investmentRank = joinMatchRecordService.getOnedayInvestmentRankByActivityId(activityId, new Date(), 10);
		int rank = 0;
		for(Map<String,Object> info : investmentRank) {
			Map<String,Object> map = new HashMap<String,Object>();
			int awardTicketAmount = 0;
			for(int denomination : MarketConstant.ACTIVITY_1015_INVESTMENT_RANK_AWARD_TICKET_DENOMINATIONS[rank]) {
				awardTicketAmount += denomination;
			}
			map.put("mobile", StringUtils.vagueMobile((String)info.get("mobile")));
			map.put("prizeType", 2);
			map.put("prizeTypeName", "现金券");
			map.put("prize", awardTicketAmount);
			map.put("ranking", ++rank);
			map.put("belongTeam", MarketConstant.MATCH_SIDE_RED.equals(info.get("side")) ? 1 : 2);
			map.put("teamName", MarketConstant.MATCH_SIDE_RED.equals(info.get("side")) ? "红队" : "蓝队");
			map.put("investmentAmount", info.get("amount"));
			data.add(map);
		}
		return data;
	}

	/**
	 * 投资排行
	 * @param response
	 * @param client
	 * @param rankResp
	 * @return
	 */
	@RequestMapping(value = "investmentList", method = RequestMethod.POST)
	public String investmentList(HttpServletResponse response, String client, String token, InvestmentRankResp rankResp) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api investmentList start...");
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		String myRanking = "";
		String myAmount = "";
		List<Object> iRespList = new ArrayList<Object>();
		List<ProjectInvestmentRank> iRankList = new ArrayList<ProjectInvestmentRank>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(ApiConstant.INVESTMENT_STAT_TYPE_THEYEAR.equals(rankResp.getGenre())){
			if(clientToken == null){
				myRanking = "0";
				myAmount = "0";
			}else{
				ProjectInvestmentRank investmentRank = projectInvestmentRankService.getInvestmentRankApiByType(ProjectConstant.INVESTMENT_RANK_TYPE_JANUARY, clientToken.getCustomerId());
				myRanking = StringUtil.dealString(investmentRank.getRank());
				myAmount = investmentRank.getAmount();
			}
			iRankList = projectInvestmentRankService.findInvestmentListByType(ProjectConstant.INVESTMENT_RANK_TYPE_JANUARY);
		}else{
			if(clientToken == null){
				myRanking = "0";
				myAmount = "0";
			}else{
				ProjectInvestmentRank investmentRank = projectInvestmentRankService.getInvestmentRankApi(rankResp,clientToken);
				myRanking = investmentRank.getRank();
				myAmount = investmentRank.getAmount();
			}
			iRankList = projectInvestmentRankService.findListWithApiStat(rankResp);

		}
		for(ProjectInvestmentRank iRank: iRankList){
			InvestmentRankResp iRankResp = new InvestmentRankResp();
			iRankResp.setMobile(StringUtils.vagueMobile(iRank.getMobile()));
			iRankResp.setAmount(iRank.getAmount());
			iRankResp.setRanking(iRank.getRank());
			iRespList.add(iRankResp);
		}
		Map<String,Object> dataMap = new  HashMap<String, Object>();
		dataMap.put("myRanking", myRanking);
		dataMap.put("myAmount", myAmount);
		dataMap.put("recordList", iRespList);
		ApiUtil.mapRespData(map, dataMap, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api investmentList end...");
		logger.info("api investmentList total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}

	/**
	 * 活动分享成功
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "shareSuccess", method = RequestMethod.POST)
	public String shareSuccess(HttpServletResponse response, HttpServletRequest request, String client, String token, String code) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api shareSuccess start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		if(clientToken != null ) {
			long accountId = clientToken.getCustomerId();
			ClientProperty cProperty = ApiUtil.getClient(client);
			String channel = ApiUtil.getOperaChannel(cProperty.getType());
			code = "1021".equals(code) ? "1020" : code;
			MarketingActivityInfo marketingActivityInfo = (code == null) ? null : marketingActivityInfoService.getByBizClassName("marketActivity" + code + "Handler");
			if(marketingActivityInfo != null && MarketingUtils.check(marketingActivityInfo)){
				String shareReason = "";
				Integer initTotalTimes = 0;
				if("1010".equals(code)){
					shareReason = MarketConstant.ACTIVITY_1010_SHARE_REMARK;
					initTotalTimes = 2;
				}else if("1020".equals(code)){
					shareReason = MarketConstant.ACTIVITY_1020_SHARE_REMARK;
					initTotalTimes = 1;
				}
				marketingShareRecordService.addShareRecord(accountId, marketingActivityInfo.getActicityId(), channel, code, shareReason,initTotalTimes);
			}
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		} else {
			ApiUtil.tokenInvalidRespMap(map);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api shareSuccess end...");
		logger.info("api shareSuccess total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}


	/**
	 * 亨沃美国土地提交个人信息
	 * @param response
	 * @param request
	 * @param client
	 * @param token
	 * @param mesInfoReq
	 * @return
	 */
	@RequestMapping(value = "commitAccountInfo", method = RequestMethod.POST)
	public String commitAccountInfo(HttpServletResponse response, HttpServletRequest request, String client, String token, MesInfoReq mesInfoReq) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api commitAccountInfo start...");
		HashMap<String, Object> map = new HashMap<String, Object>();
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		//入参数据校验
		List<String> messages  = ApiUtil.validateBean(mesInfoReq);
		if(messages != null){
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, messages.get(0), false);
		}else{
			this.addLeaveMessage(request, map, mesInfoReq, clientToken);
			ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api commitAccountInfo end...");
		logger.info("api commitAccountInfo total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}


	/**
	 * 添加留言
	 * @param request
	 * @param mesInfoReq
	 */
	public void addLeaveMessage(HttpServletRequest request, HashMap<String, Object> map, MesInfoReq mesInfoReq,CustomerClientToken clientToken){
		CustomerLeaveMessage customerLeaveMessage = new CustomerLeaveMessage();
		MyBeanUtils.copyBean2Bean(customerLeaveMessage, mesInfoReq);
		customerLeaveMessage.setName(mesInfoReq.getAccountName());
		customerLeaveMessage.setContent(mesInfoReq.getMessage());
		customerLeaveMessage.setOpDt(new Date());
		customerLeaveMessage.setType(ProjectConstant.CUSTOMER_LEAVE_MESSAGE_TYPE_HANWORLD_REALTY);
		customerLeaveMessage.setStatus(ProjectConstant.CUSTOMER_LEAVE_MESSAGE_STATUS_SUBMIT);
		if(clientToken != null) {
			customerLeaveMessage.setAccountId(clientToken.getCustomerId());
		}
		customerLeaveMessage.setIp(((HttpServletRequest)request).getHeader("X-Real-IP"));
		customerLeaveMessageService.save(customerLeaveMessage);
	}


	/**
	 * 好友征集令活动奖励
	 * @param response
	 * @param client
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "friendsReward", method = RequestMethod.POST)
	public String friendsReward(HttpServletResponse response, String client, String token) {
		Date startTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(startTime) + "】api friendsReward start...");
		CustomerClientToken clientToken = CustomerClientUtils.getByToken(token,ApiUtil.getOperTerm(client));
		HashMap<String, Object> map = new HashMap<String, Object>();
		BigDecimal earningTicketAmount = new BigDecimal(0);
		BigDecimal earningAmount = new BigDecimal(0);
		if(clientToken !=null){
			Map<String,Object> moneyMap = marketingActivityAwardRecordService.getFriendsTotalAwardValue(clientToken.getCustomerId());
			earningTicketAmount = (BigDecimal)moneyMap.get("ticketMoney");
			earningAmount = (BigDecimal)moneyMap.get("cashMoney");
		}
		Map<String,Object> dataMap = new  HashMap<String, Object>();
		dataMap.put("earningTicketAmount", earningTicketAmount);
		dataMap.put("earningAmount", earningAmount);
		ApiUtil.mapRespData(map, dataMap, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		Date endTime = new Date();
		logger.info("【" + DateUtils.formatDateTime(endTime) + "】api friendsReward end...");
		logger.info("api friendsReward total time consuming：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
		return renderString(response, map);
	}


}
