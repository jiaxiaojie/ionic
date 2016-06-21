/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.ShootRecord;

import java.util.List;

/**
 * 射门记录DAO接口
 * @author lzb
 * @version 2016-06-13
 */
@MyBatisDao
public interface ShootRecordDao extends CrudDao<ShootRecord> {

    List<ShootRecord> findLastTimesList(ShootRecord shootRecord);
}