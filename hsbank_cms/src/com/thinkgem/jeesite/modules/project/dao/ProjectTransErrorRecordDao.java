/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectTransErrorRecord;

/**
 * 异常交易记录表DAO接口
 * @author lzb
 * @version 2016-03-03
 */
@MyBatisDao
public interface ProjectTransErrorRecordDao extends CrudDao<ProjectTransErrorRecord> {
	/**
	 * 删除 days前的异常交易记录
	 * @param days
	 */
	void deleteBeforeDaysErrorRecord(int days);
	
	/**
	 * 根据第三方流水号统计数量
	 * @param thirdPartySeq
	 * @return
	 */
	int getCountByThirdPartySeq(@Param("thirdPartySeq") String thirdPartySeq);
}