/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MarketingMoneyAwardRecord;

/**
 * 活动现金奖励记录DAO接口
 * @author ydt
 * @version 2016-01-18
 */
@MyBatisDao
public interface MarketingMoneyAwardRecordDao extends CrudDao<MarketingMoneyAwardRecord> {

	List<MarketingMoneyAwardRecord> findListByStatus(@Param("status") String status);

	void updateStatus(@Param("id") String id, @Param("status") String status, @Param("finishDt") Date finishDt);
	
}