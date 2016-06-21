/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ProjectReviewRecord;
import com.thinkgem.jeesite.modules.project.dao.ProjectReviewRecordDao;

/**
 * 借贷产品审核记录Service
 * @author yangtao
 * @version 2015-06-24
 */
@Service
@Transactional(readOnly = true)
public class ProjectReviewRecordService extends CrudService<ProjectReviewRecordDao, ProjectReviewRecord> {

	public ProjectReviewRecord get(String id) {
		return super.get(id);
	}
	
	public List<ProjectReviewRecord> findList(ProjectReviewRecord projectReviewRecord) {
		return super.findList(projectReviewRecord);
	}
	
	public Page<ProjectReviewRecord> findPage(Page<ProjectReviewRecord> page, ProjectReviewRecord projectReviewRecord) {
		return super.findPage(page, projectReviewRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectReviewRecord projectReviewRecord) {
		super.save(projectReviewRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectReviewRecord projectReviewRecord) {
		super.delete(projectReviewRecord);
	}
	
}