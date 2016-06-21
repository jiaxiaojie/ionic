/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.project.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ProjectRepaymentRecord;

/**
 * 还款记录DAO接口
 * @author yangtao
 * @version 2015-06-24
 */
@MyBatisDao
public interface ProjectRepaymentRecordDao extends CrudDao<ProjectRepaymentRecord> {
	/**
	 * 根据第三方交互编号获得指定的投资记录
	 * @param thirdPartyCode
	 * @return
	 */
	public List<ProjectRepaymentRecord> getProjectRepaymentRecordByThirdPartyCode(@Param("thirdPartyCode") String thirdPartyCode);
	/**
	 * 获得指定状态的超时任务投资记录
	 * @param status
	 * @param theDate
	 * @return
	 */
	public List<ProjectRepaymentRecord> getOverTimeRepaymentRecord(@Param("status") String status, @Param("theDate") Date theDate);
	
}