/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectFactorCarFlow;

/**
 * 车贷项目要素DAO接口
 * @author yangtao
 * @version 2015-07-08
 */
@MyBatisDao
public interface ProjectFactorCarFlowDao extends CrudDao<ProjectFactorCarFlow> {
	public void deleteByProjectId(String projectId);

	/**
	 * 根据项目（projectId,projectCode）获取其车辆信息
	 * @param projectBaseInfo
	 * @return
	 */
	public ProjectFactorCarFlow getByProject(ProjectBaseInfo projectBaseInfo);
}