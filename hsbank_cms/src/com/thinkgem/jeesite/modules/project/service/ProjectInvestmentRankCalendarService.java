/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRankCalendar;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRankCalendarDao;

/**
 * 投资排行日历Service
 * @author lizibo
 * @version 2015-11-25
 */
@Service
@Transactional(readOnly = true)
public class ProjectInvestmentRankCalendarService extends CrudService<ProjectInvestmentRankCalendarDao, ProjectInvestmentRankCalendar> {

	public ProjectInvestmentRankCalendar get(String id) {
		return super.get(id);
	}
	
	public List<ProjectInvestmentRankCalendar> findList(ProjectInvestmentRankCalendar projectInvestmentRankCalendar) {
		return super.findList(projectInvestmentRankCalendar);
	}
	
	public Page<ProjectInvestmentRankCalendar> findPage(Page<ProjectInvestmentRankCalendar> page, ProjectInvestmentRankCalendar projectInvestmentRankCalendar) {
		return super.findPage(page, projectInvestmentRankCalendar);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectInvestmentRankCalendar projectInvestmentRankCalendar) {
		super.save(projectInvestmentRankCalendar);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectInvestmentRankCalendar projectInvestmentRankCalendar) {
		super.delete(projectInvestmentRankCalendar);
	}
	
	/**
	 * 根据年及周获取日历信息
	 * @param year
	 * @param week
	 * @return
	 */
	public ProjectInvestmentRankCalendar getInvestmentRankCalendar(String year, String month, String week) {
		return dao.getInvestmentRankCalendar(year, month, week);
	}
	
	/**
	 * 
	 * @param year
	 * @return
	 */
	public List<ProjectInvestmentRankCalendar> findList(String year) {
		List<ProjectInvestmentRankCalendar> list = dao.findListWithYear(year);
		for(int i=0;i<list.size();i++){
			ProjectInvestmentRankCalendar calendar = list.get(i);
			calendar.setWeek(calendar.getWeek());
			calendar.setWeekName("第" + calendar.getWeek() + "周（" +DateUtils.formatDate(calendar.getBeginDt(),"yyyy.MM.dd") + "--" +DateUtils.formatDate(calendar.getEndDt(),"yyyy.MM.dd")+ "）");
		}
		return list;
	}
	
}