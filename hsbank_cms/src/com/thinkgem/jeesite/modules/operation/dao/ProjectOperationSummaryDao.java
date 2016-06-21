/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.dao;


import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectOperationSummary;

/**
 * 项目运营数据汇总DAO接口
 * @author ydt
 * @version 2015-12-25
 */
@MyBatisDao
public interface ProjectOperationSummaryDao extends CrudDao<ProjectOperationSummary> {

	/**
	 * 将需要插入的数据插入到表中
	 */
	void insertNeedInsertData();
}