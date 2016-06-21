/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;

/**
 * 活期账户利息变更历史DAO接口
 * @author ydt
 * @version 2015-12-09
 */
@MyBatisDao
public interface CurrentAccountInterestChangeHisDao extends CrudDao<CurrentAccountInterestChangeHis> {

	/**
	 * 获取指定变更类型一天内的指定用户、项目变更记录条数
	 * @param accountId 
	 * @param projectId 
	 * @param changeType 
	 * @param date 
	 * @return
	 */
	Long getCountByAccountIdAndProjectId(@Param("accountId") Long accountId, @Param("projectId") Long projectId, @Param("changeType") String changeType, @Param("date") Date date);
	
	/**
	 * 获取昨日收益
	 * @param accountId
	 * @param changeType
	 * @param date
	 * @return
	 */
	public Double getYesterdayProfit(Map<String, Object> map);
	
	/**
	 * 累计收益
	 * @param map
	 * @return
	 */
	public Double getSumProfit(Map<String, Object> map);
	
	/**
	 * 每日收益列表
	 * @param map
	 * @return
	 */
	public List<CurrentAccountInterestChangeHis> searchMyInterestPageList(Map<String, Object> map);
	
	/**
	 * 收益分页列表
	 * @param map
	 * @return
	 */
	public List<CurrentAccountInterestChangeHis> getInterestPageList(Map<String, Object> map);
	
}