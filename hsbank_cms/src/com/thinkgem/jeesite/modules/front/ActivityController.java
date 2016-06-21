package com.thinkgem.jeesite.modules.front;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.ArrayUtils;
import com.thinkgem.jeesite.common.utils.RequstUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.CustomerLeaveMessage;
import com.thinkgem.jeesite.modules.entity.Gamble;
import com.thinkgem.jeesite.modules.entity.JoinMatchRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.feedback.service.CustomerLeaveMessageService;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.MarketFacadeService;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1012Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1015Handler;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1016Handler;
import com.thinkgem.jeesite.modules.marketing.service.GambleService;
import com.thinkgem.jeesite.modules.marketing.service.JoinMatchRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

@Controller
@RequestMapping("${frontPath}/activity")
public class ActivityController extends BaseController {
	@Autowired
	private CustomerLeaveMessageService customerLeaveMessageService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private MarketFacadeService marketFacadeService;
	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private MarketActivity1012Handler marketActivity1012Handler;
	@Autowired
	private CustomerAccountService customerAccountService;
	@Autowired
	private GambleService gambleService;
	@Autowired
	private JoinMatchRecordService joinMatchRecordService;

	@RequestMapping("shake")
	public String shake() {
		return "modules/front/activity/shake";
	}

	@RequestMapping(value = "{jspName}.html")
	public String toDetail(HttpServletRequest request,HttpServletResponse response, Model model,@PathVariable String jspName) {
		//注入参数
		for(String name : request.getParameterMap().keySet()){
			model.addAttribute(name, request.getParameter(name));
		}
		return "modules/front/activity/"+jspName;
	}

	@RequestMapping("marketing")
	public String marketing(HttpServletRequest request,HttpServletResponse response, Model model) {
		String channel = request.getParameter("ad");
		if(StringUtils.isNotBlank(channel)) {
			RequstUtils.clearCookie(response, "channel");
			RequstUtils.addCookie(response, "channel", channel, 2592000);
		}

		model.addAttribute("menu", "zxhd");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/zxhd'>最新活动</a>&nbsp;&gt;&nbsp");

		return "modules/front/activity/marketing";
	}
	@RequestMapping("double_eleven")
	public String doubleEleven(HttpServletRequest request, Model model) {
		model.addAttribute("menu", "zxhd");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/zxhd'>最新活动</a>&nbsp;&nbsp;");
		return "modules/front/activity/double_eleven";
	}

	@RequestMapping("bug")
	public String bug(HttpServletRequest request, Model model) {
		model.addAttribute("menu", "zxhd");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/zxhd'>最新活动</a>&nbsp;&gt;&nbsp;<a href='#'>来找茬</a>");
		return "modules/front/activity/bug";
	}
	@RequestMapping("invitation")
	public String invitation(HttpServletRequest request, Model model) {
		model.addAttribute("menu", "zxhd");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/zxhd'>最新活动</a>&nbsp;&gt;&nbsp;<a href='#'>邀请好友</a>");
		return "modules/front/activity/invitation";
	}
	@RequestMapping("midautumn_national_day")
	public String midautumn_national_day(HttpServletRequest request, Model model) {
		model.addAttribute("menu", "zxhd");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/zxhd'>最新活动</a>&nbsp;&gt;&nbsp;<a href='#'>中秋、国庆豪礼大派送</a>");
		return "modules/front/activity/midautumn_national_day";
	}

	@RequestMapping("hanworld")
	public String hanworld(HttpServletRequest request, Model model) {
		return "modules/front/activity/hanworld";
	}

	@RequestMapping("hanworld/leaveMessage")
	@ResponseBody
	public String leaveMessage(CustomerLeaveMessage customerLeaveMessage, String token, HttpServletRequest request, Model model) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(customerLeaveMessage.getMobile() == null || customerLeaveMessage.getMobile().length() != 11
				|| customerLeaveMessage.getName() == null || customerLeaveMessage.getName().length() < 2 || customerLeaveMessage.getName().length() > 20
				|| customerLeaveMessage.getContent() == null || customerLeaveMessage.getContent().length() < 5 || customerLeaveMessage.getContent().length() > 200) {
			result.put("isSuccess", false);
			result.put("message", "请输入正确的数据。");
			return JsonMapper.toJsonString(result);
		}
		if(request.getSession().getAttribute("hanworld_submit_datetime") != null
				&& (new Date()).getTime() - ((Date)request.getSession().getAttribute("hanworld_submit_datetime")).getTime() <= 300000) {
			result.put("isSuccess", false);
			result.put("message", "您已提交过留言，我们会尽快与您联系。");
			return JsonMapper.toJsonString(result);
		}
		request.getSession().setAttribute("hanworld_submit_datetime", new Date());
		customerLeaveMessage.setOpDt(new Date());
		customerLeaveMessage.setType(ProjectConstant.CUSTOMER_LEAVE_MESSAGE_TYPE_HANWORLD_REALTY);
		customerLeaveMessage.setStatus(ProjectConstant.CUSTOMER_LEAVE_MESSAGE_STATUS_SUBMIT);
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal != null) {
			customerLeaveMessage.setAccountId(principal.getAccountId());
		}
		customerLeaveMessage.setIp(((HttpServletRequest)request).getHeader("X-Real-IP"));
		customerLeaveMessageService.save(customerLeaveMessage);
		result.put("isSuccess", true);
		return JsonMapper.toJsonString(result);
	}

	@RequestMapping("goddess")
	public String goddess(HttpServletRequest request, Model model) {
		Boolean received = false;
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal != null){
			MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.getByBizClassName("marketActivity1011Handler");
			Integer i = marketingActivityAwardRecordService.getCountByAccountIdAndActivityId(principal.getAccountId(), marketingActivityInfo.getActicityId());
			received = (i==null || i==0?false:true);
		}
		model.addAttribute("received", received);
		return "modules/front/activity/goddess";
	}

	@RequestMapping("getGiftBag")
	public String getGiftBag(HttpServletRequest request,HttpServletResponse response, Model model) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();

		Map<String,Object> data = new HashMap<String,Object>();

		if(principal == null){
			data.put("result", "请登录");
			data.put("resultCode", "-1");
			ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}
		else{
			Long accountId = principal.getAccountId();
			CustomerBase customerBase = customerBaseService.getByAccountId(accountId);


			Map<String,Object> para = new HashMap<String,Object>();
			para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA,customerBase.getAccountId());
			para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
			para.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
			para.put(MarketConstant.HANDLER_PARA, "marketActivity1011Handler");
			marketFacadeService.luckDraw(para);

			if(para.get("isSuccess") == null) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "活动失效", false);
			} else if(!(boolean)para.get("isSuccess")) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, (String)para.get("message"), false);
			} else {
				data.put("result", para.get("result"));
				data.put("resultCode", para.get("resultCode"));
				ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}
		}
		return renderString(response, map);
	}


	/**
	 * 四月春游季入口
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("aprilSpringOuting")
	public String aprilSpringOuting(HttpServletRequest request, Model model) {
		Map<String, Object>  activityInfo = new HashMap<String, Object>();
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();
		MarketingActivityInfo marketingActivityInfo =  marketingActivityInfoService.getByBizClassName("marketActivity1012Handler");
		activityInfo.put("marketingActivityInfo", marketingActivityInfo);
		Boolean activityBegin = (new Date().getTime()-marketingActivityInfo.getBeginDate().getTime())>0;
		activityInfo.put("activityBegin", activityBegin);
		if(principal != null && activityBegin){
			activityInfo.putAll(marketActivity1012Handler.activityInfo(principal.getAccountId()));

			String count = Integer.toBinaryString((Integer)activityInfo.get("count"));
			String used = Integer.toBinaryString((Integer)activityInfo.get("used"));
			String over = Integer.toBinaryString((Integer)activityInfo.get("over"));

			activityInfo.put("count",  ArrayUtils.charArrayToIntArray(toTen(count).toCharArray()));
			activityInfo.put("used", ArrayUtils.charArrayToIntArray(toTen(used).toCharArray()));
			activityInfo.put("over", ArrayUtils.charArrayToIntArray(toTen(over).toCharArray()));
		}



		model.addAttribute("activityInfo", activityInfo);
		return "modules/front/activity/spring";
	}

	@ResponseBody
	@RequestMapping("aprilSpringOutingInfo")
	public Map<String, Object> aprilSpringOutingInfo(HttpServletRequest request, Model model) {
		Map<String, Object>  activityInfo = new HashMap<String, Object>();
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();

		if(principal != null){

			activityInfo.putAll(marketActivity1012Handler.activityInfo(principal.getAccountId()));

			String count = Integer.toBinaryString((Integer)activityInfo.get("count"));
			String used = Integer.toBinaryString((Integer)activityInfo.get("used"));
			String over = Integer.toBinaryString((Integer)activityInfo.get("over"));

			activityInfo.put("count",  ArrayUtils.charArrayToIntArray(toTen(count).toCharArray()));
			activityInfo.put("used", ArrayUtils.charArrayToIntArray(toTen(used).toCharArray()));
			activityInfo.put("over", ArrayUtils.charArrayToIntArray(toTen(over).toCharArray()));
		}
		return activityInfo;
	}

	private String toTen(String strNum){
		int i = 10 - strNum.length();
		StringBuffer sb = new StringBuffer();
		for(int j = 0; j < i; j++){
			sb.append("0");
		}
		sb.append(strNum);

		return sb.reverse().toString();
	}

	/**
	 * 四月春游季领取
	 * @param receiveAwardCode
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("aprilSpringOutingReceiveAward")
	public String aprilSpringOutingReceiveAward(Integer receiveAwardCode, HttpServletRequest request,HttpServletResponse response, Model model) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();

		Map<String,Object> data = new HashMap<String,Object>();

		if(principal == null){
			data.put("result", "请登录");
			data.put("resultCode", "-1");
			ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
		}
		else{
			Long accountId = principal.getAccountId();
			CustomerBase customerBase = customerBaseService.getByAccountId(accountId);


			Map<String,Object> para = new HashMap<String,Object>();
			para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA,customerBase.getAccountId());
			para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
			para.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
			para.put(MarketConstant.HANDLER_PARA, "marketActivity1012Handler");
			para.put(MarketConstant.FUNC_PARA, receiveAwardCode);

			marketFacadeService.luckDraw(para);

			if(para.get("isSuccess") == null) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, "活动失效", false);
			} else if(!(boolean)para.get("isSuccess")) {
				ApiUtil.mapRespData(map, ApiConstant.API_RESP_DATA_DEFAULT, (String)para.get("message"), false);
			} else {
				data.put("result", para.get("result"));
				data.put("resultCode", para.get("resultCode"));
				ApiUtil.mapRespData(map, data, ApiConstant.API_STATUS_TEXT_VALUE_DEFAULT, true);
			}
		}
		return renderString(response, map);
	}
	/**
	 * 好友征集令
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("invite_friends")
	public String inviteFriends(HttpServletRequest request, Model model) {
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();
		BigDecimal ticketMoney = new BigDecimal(0);
		BigDecimal cashMoney = new BigDecimal(0);
		if(principal != null){
			Map<String,Object> moneyMap = marketingActivityAwardRecordService.getFriendsTotalAwardValue(principal.getAccountId());
			ticketMoney = (BigDecimal)moneyMap.get("ticketMoney");
			cashMoney = (BigDecimal)moneyMap.get("cashMoney");

		}
		model.addAttribute("ticketMoney", ticketMoney);
		model.addAttribute("cashMoney", cashMoney);
		return "modules/front/activity/invite_friends";
	}

	/**
	 * 拔河比赛页面
	 */
	@RequestMapping("tugWar")
	public String tugWar(Model model) {
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1015Handler.class.getSimpleName()));
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal != null) {
			JoinMatchRecord joinMatchRecord = joinMatchRecordService.getByActivityIdAndAccountId(marketingActivityInfo.getActicityId(), principal.getAccountId());
			if(joinMatchRecord != null) {
				model.addAttribute("side", joinMatchRecord.getSide());
			}
			model.addAttribute("hasLogin", true);
		} else {
			model.addAttribute("hasLogin", false);
		}
		List<Map<String,Object>> investmentRank = tugWarInvestmentRank(marketingActivityInfo.getActicityId());
		model.addAttribute("investmentRank", investmentRank);
		return "modules/front/activity/tugWar";
	}

	/**
	 * 获取拔河比赛每日投资排行榜
	 * @param activityId
	 * @return
	 */
	private List<Map<String,Object>> tugWarInvestmentRank(Long activityId) {
		List<Map<String,Object>> investmentRank = joinMatchRecordService.getOnedayInvestmentRankByActivityId(activityId, new Date(), 10);
		int rank = 0;
		for(Map<String,Object> info : investmentRank) {
			info.put("mobile", StringUtils.vagueMobile((String)info.get("mobile")));
			int awardTicketAmount = 0;
			for(int denomination : MarketConstant.ACTIVITY_1015_INVESTMENT_RANK_AWARD_TICKET_DENOMINATIONS[rank]) {
				awardTicketAmount += denomination;
			}
			info.put("ticketDenomination", awardTicketAmount);
			info.put("rank", ++rank);
		}
		return investmentRank;
	}

	/**
	 * 参加队伍
	 */
	@RequestMapping("tugWar/joinMatch")
	@ResponseBody
	public String joinMatch(String side) {
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();
		Map<String,Object> result = new HashMap<String,Object>();
		if(principal == null) {
			result.put("isSuccess", false);
			result.put("message", "请先登录");
		} else {
			Long accountId = principal.getAccountId();
			Map<String,Object> para = new HashMap<String,Object>();
			para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, accountId);
			para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
			para.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
			para.put(MarketConstant.HANDLER_PARA, "marketActivity1015Handler");
			para.put("side", side);
			result = marketFacadeService.luckDraw(para);
		}
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 竞猜页面
	 * @param request
	 * @param pageNo
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("gamble")
	public String gamble(HttpServletRequest request,String pageNo,HttpServletResponse response,Model model) {
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1016Handler.class.getSimpleName()));
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal != null) {
			List<Gamble> gambleList = gambleService.findListByActivityIdAndAccountId(marketingActivityInfo.getActicityId(), principal.getAccountId());
			Gamble gamble = gambleService
					.getByActivityIdAndAccountIdAndOpDate(marketingActivityInfo.getActicityId(), principal.getAccountId(), new Date());
			if(gamble != null) {
				model.addAttribute("choiceSide", gamble.getChoiceSide());
			}
			model.addAttribute("hasLogin", true);
			model.addAttribute("gambleList", gambleList);
		} else {
			model.addAttribute("hasLogin", false);
		}
		return "modules/front/activity/gamble";
	}

	/**
	 * 选择支持的队伍
	 */
	@RequestMapping("gambel/choiceTerm")
	@ResponseBody
	public String choiceTerm(String choiceSide) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Map<String,Object> result = new HashMap<String,Object>();
		if(principal == null) {
			result.put("isSuccess", false);
			result.put("message", "请先登录");
		} else {
			Long accountId = principal.getAccountId();
			Map<String,Object> para = new HashMap<String,Object>();
			para.put(MarketConstant.CUSTOMER_ACCOUNT_PARA, accountId);
			para.put(MarketConstant.BEHAVIOR_PARA, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
			para.put(MarketConstant.CHANNEL_PARA, ProjectConstant.OP_TERM_DICT_PC);
			para.put(MarketConstant.HANDLER_PARA, "marketActivity1016Handler");
			para.put("side", choiceSide);
			result = marketFacadeService.luckDraw(para);
		}
		return JsonMapper.toJsonString(result);
	}

	/**
	 * 领取1000花生豆
	 * @param gambleId
	 * @return
	 */
	@RequestMapping("gambel/getIntegral")
	@ResponseBody
	public String getIntegral(Long gambleId) {
		Map<String,Object> result = gambleService.giveIntegralAward(gambleId, ProjectConstant.OP_TERM_DICT_PC);
		return JsonMapper.toJsonString(result);
	}

	@RequestMapping("parkour")
	public String parkour(Model model) {
		return "modules/front/activity/parkour";

	}

	@RequestMapping("tugWarResult")
	public String tugWarResult(Model model) {
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1015Handler.class.getSimpleName()));
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();
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
			info.put("mobile", StringUtils.vagueMobile((String)info.get("mobile")));
			double awardAmount = 0d;
			if(MarketConstant.MATCH_SIDE_RED.equals(info.get("side"))) {
				awardAmount = NumberUtils.div(NumberUtils.mul(((BigDecimal)info.get("amount")).doubleValue(), redSideAwardAmount), redSideAmount, 2);
			} else {
				awardAmount = NumberUtils.div(NumberUtils.mul(((BigDecimal)info.get("amount")).doubleValue(), blueSideAwardAmount), blueSideAmount, 2);
			}
			info.put("awardAmount", awardAmount);
		}
		for(Map<String,Object> info : blueList) {
			info.put("mobile", StringUtils.vagueMobile((String)info.get("mobile")));
			double awardAmount = 0d;
			if(MarketConstant.MATCH_SIDE_RED.equals(info.get("side"))) {
				awardAmount = NumberUtils.div(NumberUtils.mul(((BigDecimal)info.get("amount")).doubleValue(), redSideAwardAmount), redSideAmount, 2);
			} else {
				awardAmount = NumberUtils.div(NumberUtils.mul(((BigDecimal)info.get("amount")).doubleValue(), blueSideAwardAmount), blueSideAmount, 2);
			}
			info.put("awardAmount", awardAmount);
		}
		model.addAttribute("redList", redList);
		model.addAttribute("blueList", blueList);
		if(principal != null) {
			JoinMatchRecord joinMatchRecord = joinMatchRecordService.getByActivityIdAndAccountId(marketingActivityInfo.getActicityId(), principal.getAccountId());
			if(joinMatchRecord != null) {
				model.addAttribute("side", joinMatchRecord.getSide());
				double userInvestmentAmount = joinMatchRecordService
						.getUserInvestmentAmount(marketingActivityInfo.getActicityId(), joinMatchRecord.getAccountId(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
				double awardAmount = 0d;
				if(MarketConstant.MATCH_SIDE_RED.equals(joinMatchRecord.getSide()) && redSideAmount != 0) {
					awardAmount = NumberUtils.div(NumberUtils.mul(userInvestmentAmount, redSideAwardAmount), redSideAmount, 2);
				} else if(MarketConstant.MATCH_SIDE_BLUE.equals(joinMatchRecord.getSide()) && blueSideAmount != 0) {
					awardAmount = NumberUtils.div(NumberUtils.mul(userInvestmentAmount, blueSideAwardAmount), blueSideAmount, 2);
				}
				model.addAttribute("investmentAmount", userInvestmentAmount);
				model.addAttribute("awardAmount", awardAmount);
			}
			model.addAttribute("hasLogin", true);
		} else {
			model.addAttribute("hasLogin", false);
		}
		return "modules/front/activity/tugWarResult";
	}

	@RequestMapping("gambleFinish")
	public String gambleFinish(HttpServletRequest request,String pageNo,HttpServletResponse response,Model model) {
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1016Handler.class.getSimpleName()));
		Principal principal =  (Principal)CustomerUtils.getSubject().getPrincipal();
		if(principal != null) {
			List<Gamble> gambleList = gambleService.findListByActivityIdAndAccountId(marketingActivityInfo.getActicityId(), principal.getAccountId());
			Gamble gamble = gambleService
					.getByActivityIdAndAccountIdAndOpDate(marketingActivityInfo.getActicityId(), principal.getAccountId(), new Date());
			if(gamble != null) {
				model.addAttribute("choiceSide", gamble.getChoiceSide());
			}
			model.addAttribute("hasLogin", true);
			model.addAttribute("gambleList", gambleList);
		} else {
			model.addAttribute("hasLogin", false);
		}
		return "modules/front/activity/gambleFinish";
	}
}