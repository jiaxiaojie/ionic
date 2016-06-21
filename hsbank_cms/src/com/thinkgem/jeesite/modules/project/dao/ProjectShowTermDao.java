/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectShowTerm;

/**
 * 项目显示终端DAO接口
 * @author ydt
 * @version 2015-11-16
 */
@MyBatisDao
public interface ProjectShowTermDao extends CrudDao<ProjectShowTerm> {
	/**
	 * 根据项目id删除项目显示终端信息
	 * @param projectId
	 */
	void deleteByProjectId(@Param("projectId") String projectId);
	
	/**
	 * 批量添加项目显示终端信息
	 * @param projectShowTermList
	 */
	void insertBatch(@Param("projectShowTermList") List<ProjectShowTerm> projectShowTermList);

	/**
	 * 根据项目id获取项目显示终端信息列表
	 * @param projectId
	 */
	List<ProjectShowTerm> findListByProjectId(@Param("projectId") String projectId);
}