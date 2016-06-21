/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.entity.ProjectFactorCarFlow;
import com.thinkgem.jeesite.modules.project.dao.ProjectFactorCarFlowDao;

/**
 * 车贷项目要素Service
 * @author yangtao
 * @version 2015-07-08
 */
@Service
@Transactional(readOnly = true)
public class ProjectFactorCarFlowService extends CrudService<ProjectFactorCarFlowDao, ProjectFactorCarFlow> {

	public ProjectFactorCarFlow get(String id) {
		return super.get(id);
	}
	
	public List<ProjectFactorCarFlow> findList(ProjectFactorCarFlow projectFactorCarFlow) {
		return super.findList(projectFactorCarFlow);
	}
	
	public Page<ProjectFactorCarFlow> findPage(Page<ProjectFactorCarFlow> page, ProjectFactorCarFlow projectFactorCarFlow) {
		return super.findPage(page, projectFactorCarFlow);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectFactorCarFlow projectFactorCarFlow) {
		super.save(projectFactorCarFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectFactorCarFlow projectFactorCarFlow) {
		super.delete(projectFactorCarFlow);
	}

	/**
	 * 根据项目（projectId,projectCode）获取其车辆信息
	 * @param projectBaseInfo
	 * @return
	 */
	public ProjectFactorCarFlow getByProject(ProjectBaseInfo projectBaseInfo) {
		return dao.getByProject(projectBaseInfo);
	}
	
}