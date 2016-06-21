/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectDateNode;

/**
 * 项目时间节点信息DAO接口
 * @author ydt
 * @version 2015-07-30
 */
@MyBatisDao
public interface ProjectDateNodeDao extends CrudDao<ProjectDateNode> {
	/**
	 * 更新还款结束时间
	 * @param theDate
	 */
	public void updateRepayEndTime(@Param("projectId") String projectId, @Param("theDate") Date theDate);
	/**
	 * 更新幕资结束时间
	 * @param projectId
	 * @param theDate
	 */
	public void updateEndFundingDt(@Param("projectId") String projectId, @Param("theDate") Date theDate);
}