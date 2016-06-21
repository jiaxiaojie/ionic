/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.ProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.project.dao.ProjectExecuteSnapshotDao;
import com.hsbank.util.type.NumberUtil;

/**
 * 合同执行快照Service
 * @author yangtao
 * @version 2015-06-24
 */
@Service
@Transactional(readOnly = true)
public class ProjectExecuteSnapshotService extends CrudService<ProjectExecuteSnapshotDao, ProjectExecuteSnapshot> {
	@Autowired
	private ProjectExecuteSnapshotDao projectExecuteSnapshotDao;
	public ProjectExecuteSnapshot get(String id) {
		return super.get(id);
	}
	
	public List<ProjectExecuteSnapshot> findList(ProjectExecuteSnapshot projectExecuteSnapshot) {
		return super.findList(projectExecuteSnapshot);
	}
	
	public Page<ProjectExecuteSnapshot> findPage(Page<ProjectExecuteSnapshot> page, ProjectExecuteSnapshot projectExecuteSnapshot) {
		return super.findPage(page, projectExecuteSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectExecuteSnapshot projectExecuteSnapshot) {
		super.save(projectExecuteSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectExecuteSnapshot projectExecuteSnapshot) {
		super.delete(projectExecuteSnapshot);
	}
	
	/**
	 * 根据项目流水号获取信息
	 * @param projectId
	 * @return
	 */
	public ProjectExecuteSnapshot getByProjectId(String projectId) {
		return projectExecuteSnapshotDao.getByProjectId(NumberUtil.toLong(projectId, 0L), 0L);
	}
	/**
	 * 根据项目流水号 和 转让流水号 获取信息
	 * @param projectId
	 * @return
	 */
	public ProjectExecuteSnapshot getByProjectIdAndTransferId(String projectId,String transferProjectId) {
		return projectExecuteSnapshotDao.getByProjectId(new Long(projectId),new Long(transferProjectId));
	}

	/**
	 * 根据projectId transferProjectId获取执行快照信息
	 * @param projectId
	 * @param transferProjectId
	 * @return
	 */
	public ProjectExecuteSnapshot getTransferSnapshot(Long projectId, Long transferProjectId) {
		return dao.getTransferSnapshot(projectId, transferProjectId);
	}
}