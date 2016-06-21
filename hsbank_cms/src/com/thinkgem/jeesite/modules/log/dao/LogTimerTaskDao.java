/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.LogTimerTask;

/**
 * 定时任务日志DAO接口
 * @author lizibo
 * @version 2015-08-14
 */
@MyBatisDao
public interface LogTimerTaskDao extends CrudDao<LogTimerTask> {

	/**
	 * 删除days天以前的数据
	 * @param days
	 */
	void deleteSomeDaysAgoData(int days);
	
}