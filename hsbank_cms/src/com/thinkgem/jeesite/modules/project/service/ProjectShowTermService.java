/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ProjectShowTerm;
import com.thinkgem.jeesite.modules.project.dao.ProjectShowTermDao;

/**
 * 活动渠道限制Service
 * @author ydt
 * @version 2015-11-16
 */
@Service
@Transactional(readOnly = true)
public class ProjectShowTermService extends CrudService<ProjectShowTermDao, ProjectShowTerm> {

	public List<ProjectShowTerm> findListByProjectId(String projectId) {
		return dao.findListByProjectId(projectId);
	}

}