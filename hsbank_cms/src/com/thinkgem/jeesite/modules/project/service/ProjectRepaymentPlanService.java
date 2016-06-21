/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentPlan;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentPlanDao;

/**
 * 还款记录Service
 * @author yangtao
 * @version 2015-07-10
 */
@Service
@Transactional(readOnly = true)
public class ProjectRepaymentPlanService extends CrudService<ProjectRepaymentPlanDao, ProjectRepaymentPlan> {

	
	
	public ProjectRepaymentPlan get(String id) {
		return super.get(id);
	}
	
	public List<ProjectRepaymentPlan> findList(ProjectRepaymentPlan projectRepaymentPlan) {
		return super.findList(projectRepaymentPlan);
	}
	
	public Page<ProjectRepaymentPlan> findPage(Page<ProjectRepaymentPlan> page, ProjectRepaymentPlan projectRepaymentPlan) {
		return super.findPage(page, projectRepaymentPlan);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectRepaymentPlan projectRepaymentPlan) {
		super.save(projectRepaymentPlan);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectRepaymentPlan projectRepaymentPlan) {
		super.delete(projectRepaymentPlan);
	}

	/**
	 * 获取还款计划列表
	 * @param projectId 项目流水号
	 * @return
	 */
	public List<ProjectRepaymentPlan> findListByProjectId(String projectId) {
		return dao.findListByProjectId(projectId);
	}

	/**
	 * 按项目id分组获取剩余本息为空的列表
	 * @return
     */
	public List<ProjectRepaymentPlan> findListGroupByProjectId() {
		return dao.findListGroupByProjectId();
	}
	/**
	 * 获得指定项目的下一还款日
	 * @param projectId
	 * @return
	 */
	public Date getNextReapyDay(String projectId){
		Date today=DateUtils.dateFormate(new Date());
		List<ProjectRepaymentPlan> planList=dao.findNextPlan(projectId,today);
		if((planList!=null)&&(planList.size()>0)){
			ProjectRepaymentPlan plan=planList.get(0);
			return plan.getPlanDate();
		}else{
			return null;
		}
	}
	/**
	 * 获得指定项目的剩余还款计划数量
	 * @param projectId
	 * @param theDay
	 * @return
	 */
	public int getRemaining(String projectId,Date theDay){
		return dao.getRemaining(projectId,theDay);
	}

	/**
	 * 更新剩余本息
	 * @param projectRepaymentPlan
	 * @return
     */
	@Transactional(readOnly = false)
	public int updateRemainingPrincipalInterest(ProjectRepaymentPlan projectRepaymentPlan){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("planId",projectRepaymentPlan.getPlanId());
		map.put("projectId",projectRepaymentPlan.getProjectId());
		map.put("planDate",projectRepaymentPlan.getPlanDate());
		map.put("remainingPrincipal",projectRepaymentPlan.getRemainingPrincipal());
		return dao.updateRemainingPrincipalInterest(map);
	}
	
}