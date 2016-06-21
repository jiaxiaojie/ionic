package com.thinkgem.jeesite.modules.front;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.CustomerBase;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRank;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRankCalendar;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1008Handler;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRankCalendarService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRankService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

/**
 * 投资排行
 * @author lizibo
 *
 */
@Controller
@RequestMapping(value = "${frontPath}/customer/investmentRank")
public class InvestmentRankController extends BaseController {
	@Autowired
	private ProjectInvestmentRankService projectInvestmentRankService;
	@Autowired
	private ProjectInvestmentRankCalendarService projectInvestmentRankCalendarService;
	@Autowired
	private CustomerBaseService customerBaseService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	
	@RequestMapping("rankWeek")
	public String rankWeek(HttpServletRequest request, ProjectInvestmentRank projectInvestmentRank, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId =principal !=null ? principal.getAccountId() : 0L;
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		if(customerBase == null){
			customerBase = new CustomerBase();
		}
		String year = projectInvestmentRank.getYear() != null ? projectInvestmentRank.getYear() : DateUtils.getYear();
		String month = projectInvestmentRank.getMonth() != null ? projectInvestmentRank.getMonth() : DateUtils.getMonth();
		String week = projectInvestmentRank.getWeek() != null && !"".equals(projectInvestmentRank.getWeek())? projectInvestmentRank.getWeek() : String.valueOf(DateUtils.getWeekOfYear(new Date()));
		ProjectInvestmentRankCalendar iRankCalendar = projectInvestmentRankCalendarService.getInvestmentRankCalendar(year, month, week);
		if(iRankCalendar ==null){
			iRankCalendar = new ProjectInvestmentRankCalendar();
		}
		Date beginDataDt = iRankCalendar.getBeginDt();
		Date endDataDt = iRankCalendar.getEndDt();
		projectInvestmentRank.setYear(year);
		projectInvestmentRank.setWeek(week);
		projectInvestmentRank.setAccountId(accountId);
		projectInvestmentRank.setType(ProjectConstant.INVESTMENT_RANK_TYPE_WEEK);
		projectInvestmentRank.setBeginDataDt(beginDataDt);
		projectInvestmentRank.setEndDataDt(endDataDt);
		ProjectInvestmentRank investmentRank = new ProjectInvestmentRank();
		List<ProjectInvestmentRank> list = projectInvestmentRankService.findListWithMonthStat(projectInvestmentRank);
        if(principal == null){
        	investmentRank.setRank("-");
        	investmentRank.setAmount("-");
        }else{
        	investmentRank = projectInvestmentRankService.getInvestmentRank(projectInvestmentRank,list);
        }
		
		model.addAttribute("list", list);
		model.addAttribute("rank",projectInvestmentRank);
		model.addAttribute("customerBase", customerBase);
		model.addAttribute("investmentRank",investmentRank);
		model.addAttribute("calendarList",projectInvestmentRankCalendarService.findList(year));
		model.addAttribute("menu", "zxhd");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/zxhd'>最新活动</a>&nbsp;&gt;&nbsp");
		return "modules/front/activity/leaderboard_week";
	}
	

	/**
	 * 月投资排行
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping("rankMonth")
	public String rankMonth(HttpServletRequest request, ProjectInvestmentRank projectInvestmentRank, PageSearchBean pageSearchBean, Model model) {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId =principal !=null ? principal.getAccountId() : 0L;
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		if(customerBase == null){
			customerBase = new CustomerBase();
		}
		String year = projectInvestmentRank.getYear() != null ? projectInvestmentRank.getYear() : DateUtils.getYear();
		String month = projectInvestmentRank.getMonth() != null ? projectInvestmentRank.getMonth() : DateUtils.getMonth();
		Date theDate = DateUtils.stringToDateShort(year + "-" + month + "-" + "01");
		Date beginDataDt = DateUtils.dateFormateDayOfTheBeginTime(DateUtils.getMonthBegin(theDate));
		Date endDataDt = DateUtils.dateFormateDayOfTheLastTime(DateUtils.getMonthEnd(theDate));
		projectInvestmentRank.setYear(year);
		projectInvestmentRank.setMonth(month);
		projectInvestmentRank.setAccountId(accountId);
		projectInvestmentRank.setType(ProjectConstant.INVESTMENT_RANK_TYPE_MONTH);
		projectInvestmentRank.setBeginDataDt(beginDataDt);
		projectInvestmentRank.setEndDataDt(endDataDt);
		ProjectInvestmentRank investmentRank = new ProjectInvestmentRank();
		List<ProjectInvestmentRank> list = projectInvestmentRankService.findListWithMonthStat(projectInvestmentRank);
		if(principal == null){
        	investmentRank.setRank("0");
        	investmentRank.setAmount("0");
        }else{
        	investmentRank = projectInvestmentRankService.getInvestmentRank(projectInvestmentRank,list);
        }
		model.addAttribute("list", list);
		model.addAttribute("rank",projectInvestmentRank);
		model.addAttribute("investmentRank",investmentRank);
		model.addAttribute("customerBase", customerBase);
		model.addAttribute("menu", "zxhd");
		model.addAttribute("nav", "<a href='" + Global.getInstance().getFrontRootUrl(request) + "/index'>首页</a>&nbsp;&gt;&nbsp;<a href='" + Global.getInstance().getFrontRootUrl(request) + "/zxhd'>最新活动</a>&nbsp;&gt;&nbsp");
		return "modules/front/activity/leaderboard_month";
	}
	
	
	@RequestMapping(value = "rankMonthAjax")
	@ResponseBody
    public Map<String, Object> rankMonthAjax(HttpServletRequest request, ProjectInvestmentRank projectInvestmentRank, PageSearchBean pageSearchBean, Model model){
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId =principal !=null ? principal.getAccountId() : 0L;
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		if(customerBase == null){
			customerBase = new CustomerBase();
		}
		customerBase.setMobile(StringUtils.vagueMobile(customerBase.getMobile()));
		String year = projectInvestmentRank.getYear() != null ? projectInvestmentRank.getYear() : DateUtils.getYear();
		String month = projectInvestmentRank.getMonth() != null ? projectInvestmentRank.getMonth() : DateUtils.getMonth();
		Date theDate = DateUtils.stringToDateShort(year + "-" + month + "-" + "01");
		Date beginDataDt = DateUtils.dateFormateDayOfTheBeginTime(DateUtils.getMonthBegin(theDate));
		Date endDataDt = DateUtils.dateFormateDayOfTheLastTime(DateUtils.getMonthEnd(theDate));
		projectInvestmentRank.setYear(year);
		projectInvestmentRank.setMonth(month);
		projectInvestmentRank.setAccountId(accountId);
		projectInvestmentRank.setType(ProjectConstant.INVESTMENT_RANK_TYPE_MONTH);
		projectInvestmentRank.setBeginDataDt(beginDataDt);
		projectInvestmentRank.setEndDataDt(endDataDt);
		ProjectInvestmentRank investmentRank = new ProjectInvestmentRank();
		List<ProjectInvestmentRank> list = projectInvestmentRankService.findListWithMonthStat(projectInvestmentRank);
		if(principal == null){
        	investmentRank.setRank("-");
        	investmentRank.setAmount("-");
        }else{
        	investmentRank = projectInvestmentRankService.getInvestmentRank(projectInvestmentRank, list);
        }
		for(int i=0;i < list.size();i++){
			ProjectInvestmentRank iRank = list.get(i);
			iRank.setMobile(StringUtils.vagueMobile(iRank.getMobile()));
		}
		retMap.put("list", list);
		retMap.put("investmentRank", investmentRank);
		retMap.put("prizeShow", "0");
		retMap.put("customerBase", customerBase);
    	return retMap;
    }
	
	@RequestMapping(value = "rankWeekAjax")
	@ResponseBody
    public Map<String, Object> rankWeekAjax(HttpServletRequest request, ProjectInvestmentRank projectInvestmentRank, PageSearchBean pageSearchBean, Model model){
		Map<String, Object> retMap = new HashMap<String, Object>();
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId =principal !=null ? principal.getAccountId() : 0L;
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);
		if(customerBase == null){
			customerBase = new CustomerBase();
		}
		customerBase.setMobile(StringUtils.vagueMobile(customerBase.getMobile()));
		String year = projectInvestmentRank.getYear() != null ? projectInvestmentRank.getYear() : DateUtils.getYear();
		String month = projectInvestmentRank.getMonth() != null ? projectInvestmentRank.getMonth() : DateUtils.getMonth();
		String week = projectInvestmentRank.getWeek() != null && !"".equals(projectInvestmentRank.getWeek()) ? projectInvestmentRank.getWeek() : String.valueOf(DateUtils.getWeekOfYear(new Date()));
		int currWeek = DateUtils.getWeekOfYear(new Date());
		
		ProjectInvestmentRankCalendar iRankCalendar = projectInvestmentRankCalendarService.getInvestmentRankCalendar(year, month, week);
		if(iRankCalendar ==null){
			iRankCalendar = new ProjectInvestmentRankCalendar();
		}
		Date beginDataDt = iRankCalendar.getBeginDt();
		Date endDataDt = iRankCalendar.getEndDt();
		projectInvestmentRank.setYear(year);
		projectInvestmentRank.setWeek(week);
		projectInvestmentRank.setAccountId(accountId);
		projectInvestmentRank.setType(ProjectConstant.INVESTMENT_RANK_TYPE_WEEK);
		projectInvestmentRank.setBeginDataDt(beginDataDt);
		projectInvestmentRank.setEndDataDt(endDataDt);
		ProjectInvestmentRank investmentRank = new ProjectInvestmentRank();
		List<ProjectInvestmentRank> list = projectInvestmentRankService.findListWithMonthStat(projectInvestmentRank);
        if(principal == null){
        	investmentRank.setRank("-");
        	investmentRank.setAmount("-");
        }else{
        	investmentRank = projectInvestmentRankService.getInvestmentRank(projectInvestmentRank,list);
        }
		
		for(int i=0;i < list.size();i++){
			ProjectInvestmentRank iRank = list.get(i);
			iRank.setMobile(StringUtils.vagueMobile(iRank.getMobile()));
		}
		String prizeShow =Integer.parseInt(week) < currWeek && list !=null && list.size() > 0 ? "1" : "0";
		retMap.put("list", list);
		retMap.put("investmentRank", investmentRank);
		retMap.put("prizeShow", prizeShow);
		retMap.put("week", week);
		retMap.put("customerBase", customerBase);
    	return retMap;
    }
	
	/**
	 * 
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "calendarList")
	@ResponseBody
    public Map<String, Object> calendarList(String year){
    	Map<String, Object> retMap = new HashMap<String, Object>();
    	retMap.put("calendarList", projectInvestmentRankCalendarService.findList(year));
    	return retMap;
    }
	
	@RequestMapping(value = "januaryInvestmentRank")
	public String januaryInvestmentRank(Model model) {
		List<ProjectInvestmentRank> projectInvestmentRankList = projectInvestmentRankService.findListByType(ProjectConstant.INVESTMENT_RANK_TYPE_JANUARY);
		projectInvestmentRankList = projectInvestmentRankList == null ? new ArrayList<ProjectInvestmentRank>() : projectInvestmentRankList;
		for(ProjectInvestmentRank projectInvestmentRank : projectInvestmentRankList) {
			projectInvestmentRank.setMobile(StringUtils.vagueMobile(customerBaseService.getByAccountId(projectInvestmentRank.getAccountId()).getMobile()));
		}
		model.addAttribute("projectInvestmentRankList", projectInvestmentRankList);
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		model.addAttribute("hasGetAward", false);
		if(principal != null) {
			CustomerBase customerBase = customerBaseService.getByAccountId(principal.getAccountId());
			MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1008Handler.class.getSimpleName()));
			double januaryInvestmentAmount = projectInvestmentRecordService.getCustomerAnnualizedAmountDuringTime(principal.getAccountId(),
					DateUtils.dateFormateDayOfTheBeginTime(marketingActivityInfo.getBeginDate()),
					DateUtils.dateFormateDayOfTheLastTime(marketingActivityInfo.getEndDate()));
			model.addAttribute("hasGetAward", marketingActivityAwardRecordService.hasGetJanuaryAward(principal.getAccountId()));
			model.addAttribute("awardValue", 5);
			model.addAttribute("customerBase", customerBase);
			model.addAttribute("januaryInvestmentAmount", januaryInvestmentAmount);
		}
		return "modules/front/activity/januaryInvestmentRank";
	}
	
	@RequestMapping(value = "getJanuaryActivityAward")
	@ResponseBody
	public String getJanuaryActivityAward() {
		Map<String,Object> result = marketingActivityAwardRecordService.getJanuaryActivityAward(CustomerUtils.getCurrentAccountId());
		return JsonMapper.toJsonString(result);
	}
}
