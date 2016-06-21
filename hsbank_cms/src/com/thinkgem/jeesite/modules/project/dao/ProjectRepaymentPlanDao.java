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
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;

/**
 * 还款记录DAO接口
 * @author yangtao
 * @version 2015-07-10
 */
@MyBatisDao
public interface ProjectRepaymentPlanDao extends CrudDao<ProjectRepaymentPlan> {
	public void deleteByProjectId(String projectId);

	/**
	 * 获取还款计划列表
	 * 		排序：时间 升序
	 * @param projectId 项目流水号
	 * @return
	 */
	public List<ProjectRepaymentPlan> findListByProjectId(@Param("projectId") String projectId);

	/**
	 * 按项目id分组获取剩余本息为空的列表
	 * @return
     */
	public List<ProjectRepaymentPlan> findListGroupByProjectId();

	/**
	 * 更新剩余本息
	 * @param map
	 * @return
     */
	public int updateRemainingPrincipalInterest(Map<String, Object> map);
	
	
	public ProjectRepaymentPlan getByProjectIdAndDay(@Param("projectId") String projectId, @Param("theDay") String theDay);
	/**
	 * 更新还款计划状态
	 * @param projectId
	 * @param theDay
	 */
	public void updateStatusForEarlyRepay(@Param("projectId") String projectId, @Param("theDay") Date theDay, @Param("status") String status);
	
	public void updateStatus(@Param("planId") String planId, @Param("status") String status);
	
	public List<ProjectRepaymentPlan> findNextPlan(@Param("projectId") String projectId, @Param("theDay") Date theDay);
	
	/**
	 * 获取30天内应还款金额
	 * @param projectId
	 * @return
	 */
	public Double getRepayment30Will(@Param("projectId") String projectId);
	
	/**
	 * 获得还款总额
	 * @param projectId
	 * @return
	 */
	public Double getSumPlanMoney(@Param("projectId") String projectId);
	/**
	 * 获得指定项目的剩余还款计划数量
	 * @param projectId
	 * @param theDay
	 * @return
	 */
	public int getRemaining(@Param("projectId") String projectId, @Param("theDay") Date theDay);
	

}