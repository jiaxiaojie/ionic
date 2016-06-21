/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.LogTimerTask;
import com.thinkgem.jeesite.modules.log.dao.LogTimerTaskDao;

/**
 * 定时任务日志Service
 * @author lizibo
 * @version 2015-08-14
 */
@Service
@Transactional(readOnly = true)
public class LogTimerTaskService extends CrudService<LogTimerTaskDao, LogTimerTask> {

	public LogTimerTask get(String id) {
		return super.get(id);
	}
	
	public List<LogTimerTask> findList(LogTimerTask logTimerTask) {
		return super.findList(logTimerTask);
	}
	
	public Page<LogTimerTask> findPage(Page<LogTimerTask> page, LogTimerTask logTimerTask) {
		return super.findPage(page, logTimerTask);
	}
	
	@Transactional(readOnly = false)
	public void save(LogTimerTask logTimerTask) {
		super.save(logTimerTask);
	}
	
	@Transactional(readOnly = false)
	public void delete(LogTimerTask logTimerTask) {
		super.delete(logTimerTask);
	}
	
	/**
	 * 插入定时任务日志
	 * @param taskName
	 * @param beginDt
	 * @param endDt
	 * @param remark
	 */
	@Transactional(readOnly = false)
	public void insert(String taskName, Date beginDt, Date endDt, String status, String remark){
		LogTimerTask logTimerTask = new LogTimerTask();
		logTimerTask.setTaskName(taskName);
		logTimerTask.setBeginDt(beginDt);
		logTimerTask.setEndDt(endDt);
		logTimerTask.setStatus(status);
		logTimerTask.setRemark(remark);
		dao.insert(logTimerTask);
	}
	
	/**
	 * 记录日志信息
	 * @param list
	 * @param taskName
	 * @param beginDt
	 * @param endDt
	 * @param status
	 * @param remark
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = false)
	public void logTimer(List list, String taskName, Date beginDt, Date endDt, String status, String remark){
		if(list != null && list.size() > 0){
			//记录任务日志
        	this.insert(taskName, beginDt, endDt, status,remark);
		}else{
			remark = "" + taskName + " dataList is 0";
			this.insert(taskName, beginDt, endDt, status,remark);
		}
	}
	
	/**
	 * 删除days天以前的数据
	 * @param days
	 */
	@Transactional(readOnly = false)
	public void deleteSomeDaysAgoData(int days) {
		dao.deleteSomeDaysAgoData(days);
	}
	
}