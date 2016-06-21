/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.OperationData;

import java.util.List;
import java.util.Map;

/**
 * 运营数据DAO接口
 * @author huangyuchen
 * @version 2015-12-01
 */
@MyBatisDao
public interface OperationDataDao extends CrudDao<OperationData> {
	/**
	 * 将需要插入的数据插入到表中
	 * 例如今天是2015-10-10，此表中无2015-10-08、2015-10-09的数据而有2015-10-07及之前的数据，则将2015-10-08、2015-10-09的数据插入到表中
	 */
	public void insertNeedInsertData(List<Map<String, Object>> list);

	List<Map<String, Object>> findOperationInfoList();
}