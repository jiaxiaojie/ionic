/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ProjectOperationSummary;
import com.thinkgem.jeesite.modules.operation.dao.ProjectOperationSummaryDao;

/**
 * 项目运营数据汇总Service
 * @author ydt
 * @version 2015-12-25
 */
@Service
@Transactional(readOnly = true)
public class ProjectOperationSummaryService extends CrudService<ProjectOperationSummaryDao, ProjectOperationSummary> {

	
	public ProjectOperationSummary get(String id) {
		return super.get(id);
	}
	
	public List<ProjectOperationSummary> findList(ProjectOperationSummary projectOperationSummary) {
		return super.findList(projectOperationSummary);
	}
	
	public Page<ProjectOperationSummary> findPage(Page<ProjectOperationSummary> page, ProjectOperationSummary projectOperationSummary) {
		return super.findPage(page, projectOperationSummary);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectOperationSummary projectOperationSummary) {
		super.save(projectOperationSummary);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectOperationSummary projectOperationSummary) {
		super.delete(projectOperationSummary);
	}

	/**
	 * 将需要插入的数据插入到表中
	 */
	@Transactional(readOnly = false)
	public void insertNeedInsertData() {
		dao.insertNeedInsertData();
	}
	
	
	
	
	
}