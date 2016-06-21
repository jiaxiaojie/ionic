/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectReviewRecord;

/**
 * 借贷产品审核记录DAO接口
 * @author yangtao
 * @version 2015-06-24
 */
@MyBatisDao
public interface ProjectReviewRecordDao extends CrudDao<ProjectReviewRecord> {
	
}