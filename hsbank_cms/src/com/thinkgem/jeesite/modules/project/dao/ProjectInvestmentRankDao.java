/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRank;

/**
 * 投资排行DAO接口
 * @author lizibo
 * @version 2015-11-23
 */
@MyBatisDao
public interface ProjectInvestmentRankDao extends CrudDao<ProjectInvestmentRank> {
	
	/**
	 * 根据日期及类型删除
	 * @param projectInvestmentRank
	 * @return
	 */
	public int deleteByTheDateAndType(ProjectInvestmentRank projectInvestmentRank);
	
	/**
	 * 投资统计
	 * @param projectInvestmentRank
	 * @return
	 */
	public int investmentStat(ProjectInvestmentRank projectInvestmentRank);
	
	/**
	 * 投资统计查询
	 * @param map
	 * @return
	 */
    public List<ProjectInvestmentRank> findListWithStat(Map<String, Object> map);
    
    /**
     * 根据type获取
     * @param type
     * @return
     */
    public List<ProjectInvestmentRank> findInvestmentListByType(@Param("type") String type);
    
    /**
     * 获取某个用户的投资信息
     * @param map
     * @return
     */
    public ProjectInvestmentRank getInvestmentRank(ProjectInvestmentRank projectInvestmentRank);
    
    /**
     * 根据type获取用户的投资信息
     * @param type
     * @param accountId
     * @return
     */
    public ProjectInvestmentRank getInvestmentRankByType(@Param("type") String type, @Param("accountId") Long accountId);

	/**
	 * 删除某一类型排行数据
	 * @param type
	 */
	public void deleteByType(@Param("type") String type);

	/**
	 * 根据投资记录表将一月份活动期间的投资排行数据添加到表中
	 */
	public void insertJanuaryInvestmentRankData(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 根据类型查询排行榜列表
	 * @param type
	 * @return
	 */
	public List<ProjectInvestmentRank> findListByType(@Param("type") String type);
	
}