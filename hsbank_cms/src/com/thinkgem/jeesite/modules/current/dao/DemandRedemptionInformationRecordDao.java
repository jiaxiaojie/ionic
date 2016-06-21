/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.DemandRedemptionInformationRecord;

/**
 * 活期赎回信息记录DAO接口
 * @author huangyuchen
 * @version 2016-04-11
 */
@MyBatisDao
public interface DemandRedemptionInformationRecordDao extends CrudDao<DemandRedemptionInformationRecord> {
	
}