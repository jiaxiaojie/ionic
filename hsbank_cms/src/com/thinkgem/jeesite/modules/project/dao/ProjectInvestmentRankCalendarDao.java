/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRankCalendar;

/**
 * 投资排行日历DAO接口
 * @author lizibo
 * @version 2015-11-25
 */
@MyBatisDao
public interface ProjectInvestmentRankCalendarDao extends CrudDao<ProjectInvestmentRankCalendar> {
	
	/**
	 * 根据年及周获取日历信息
	 * @param iRankCalendar
	 * @return
	 */
	public ProjectInvestmentRankCalendar getInvestmentRankCalendar(@Param("year") String year, @Param("month") String month, @Param("week") String week);
	
	/**
	 * 根据年获取所有的周列表
	 * @param year
	 * @return
	 */
	public List<ProjectInvestmentRankCalendar> findListWithYear(@Param("year") String year);
	
}