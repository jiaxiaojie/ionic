package com.thinkgem.jeesite.modules.project.web;

import java.util.Calendar;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.DateInfoService;
import com.thinkgem.jeesite.modules.entity.DateInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRankCalendar;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRankCalendarService;

/**
 * @author ydt
 * 前端页面测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "webapp")
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = "classpath:spring-context*.xml"),
		@ContextConfiguration(name = "child", locations = "classpath:spring-mvc*.xml") })
public class ProjectInvestmentRankCalendarTest {
	@Autowired
	@Qualifier("securityManagerFront")
	private SecurityManager securityManager;
	@Autowired
	private ProjectInvestmentRankCalendarService rankCalendarService;
	@Autowired
	private DateInfoService dateInfoService;
	

	@Before
	public void setUp() {
		ThreadContext.bind(securityManager);
	}

	/**
	 * 初始化投资排行日历日历
	 * @throws Exception
	 */
	@Test
	public void TestInitInvestmentRankCalendar() throws Exception {
		int year = 2017;
		for(int i=1;i < 53; i++){
			ProjectInvestmentRankCalendar cal =new ProjectInvestmentRankCalendar();
			cal.setYear(String.valueOf(year));
			cal.setWeek(i);
			cal.setBeginDt(DateUtils.dateFormateDayOfTheBeginTime(DateUtils.stringToDateShort(DateUtils.getStartDayOfWeekNo(year,i))));
			cal.setEndDt(DateUtils.dateFormateDayOfTheLastTime(DateUtils.stringToDateShort(DateUtils.getEndDayOfWeekNo(year,i))));
			cal.setStatus("0");
			rankCalendarService.save(cal);
		}
	}

	/**
	 * 初始化日期信息
	 * @throws Exception
	 */
	@Test
	public void TestInitDateInfo() throws Exception {
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.parseDate("2015-12-01"));
		for(int i = 0;i< 1080;i++){
			System.out.println();
			DateInfo dateInfo = new DateInfo();
			
			
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		    
			String isWorkDay = (dayOfWeek==7 || dayOfWeek==1)?CurrentProjectConstant.IS_WORKDAY_NO:CurrentProjectConstant.IS_WORKDAY_YES;
			dateInfo.setIsWorkday(isWorkDay);
			dateInfo.setDate(c.getTime());
			dateInfoService.save(dateInfo);
			
			  
			//日期增加一天
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 2);
		
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(dayOfWeek);
	}
	
}