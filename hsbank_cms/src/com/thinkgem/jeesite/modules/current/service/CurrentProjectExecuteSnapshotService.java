/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;

/**
 * 活期项目执行快照Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentProjectExecuteSnapshotService extends CrudService<CurrentProjectExecuteSnapshotDao, CurrentProjectExecuteSnapshot> {

	@Autowired
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	
	public CurrentProjectExecuteSnapshot get(String id) {
		return super.get(id);
	}
	
	/**
	 * 根据项目流水号获取
	 * @param projectId
	 * @return
	 */
	public CurrentProjectExecuteSnapshot getByProjectId(Long projectId){
		return dao.getByProjectId(projectId);
	}
	
	public List<CurrentProjectExecuteSnapshot> findList(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot) {
		return super.findList(currentProjectExecuteSnapshot);
	}
	
	public Page<CurrentProjectExecuteSnapshot> findPage(Page<CurrentProjectExecuteSnapshot> page, CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot) {
		return super.findPage(page, currentProjectExecuteSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot) {
		super.save(currentProjectExecuteSnapshot);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot) {
		super.delete(currentProjectExecuteSnapshot);
	}

	public CurrentProjectExecuteSnapshot getByProjectId(String projectId) {
		
		return currentProjectExecuteSnapshotDao.getByProjectId(Long.parseLong(projectId));
	}
	
}