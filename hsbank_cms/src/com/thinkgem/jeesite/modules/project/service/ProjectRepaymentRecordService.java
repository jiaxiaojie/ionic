/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentRecord;
import com.thinkgem.jeesite.modules.project.dao.ProjectRepaymentRecordDao;

/**
 * 还款记录Service
 * @author yangtao
 * @version 2015-06-24
 */
@Service
@Transactional(readOnly = true)
public class ProjectRepaymentRecordService extends CrudService<ProjectRepaymentRecordDao, ProjectRepaymentRecord> {
	@Autowired
	ProjectRepaymentRecordDao projectRepaymentRecordDao;
	public ProjectRepaymentRecord get(String id) {
		return super.get(id);
	}
	
	public List<ProjectRepaymentRecord> findList(ProjectRepaymentRecord projectRepaymentRecord) {
		return super.findList(projectRepaymentRecord);
	}
	
	public Page<ProjectRepaymentRecord> findPage(Page<ProjectRepaymentRecord> page, ProjectRepaymentRecord projectRepaymentRecord) {
		return super.findPage(page, projectRepaymentRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectRepaymentRecord projectRepaymentRecord) {
		super.save(projectRepaymentRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectRepaymentRecord projectRepaymentRecord) {
		super.delete(projectRepaymentRecord);
	}
	
	/**
	 * 获得指定状态的超时任务投资记录
	 * @param status
	 * @param theDate
	 * @return
	 */
	public List<ProjectRepaymentRecord> getOverTimeRepaymentRecord(String status,Date theDate){
		return projectRepaymentRecordDao.getOverTimeRepaymentRecord(status, theDate);
	}
	
}