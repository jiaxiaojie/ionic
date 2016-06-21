/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.operation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.OperationData;
import com.thinkgem.jeesite.modules.operation.dao.OperationDataDao;

/**
 * 运营数据Service
 * @author huangyuchen
 * @version 2015-12-01
 */
@Service
@Transactional(readOnly = true)
public class OperationDataService extends CrudService<OperationDataDao, OperationData> {
	@Autowired
	private OperationDataDao opertationDateDao;
	public OperationData get(String id) {
		return super.get(id);
	}
	
	public List<OperationData> findList(OperationData operationData) {
		return super.findList(operationData);
	}
	
	public Page<OperationData> findPage(Page<OperationData> page, OperationData operationData) {
		return super.findPage(page, operationData);
	}
	
	@Transactional(readOnly = false)
	public void save(OperationData operationData) {
		super.save(operationData);
	}
	
	@Transactional(readOnly = false)
	public void delete(OperationData operationData) {
		super.delete(operationData);
	}
	
	/**
	 * 将需要插入的数据插入到表中
	 * 例如今天是2015-10-10，此表中无2015-10-08、2015-10-09的数据而有2015-10-07及之前的数据，则将2015-10-08、2015-10-09的数据插入到表中
	 */
	@Transactional(readOnly = false)
	public void insertNeedInsertData() {
		/*List<Map<String, Object>> mapList= new ArrayList<Map<String, Object>>();*/
			/*List<OperationData> list = new ArrayList<OperationData>();*/
		List<Map<String, Object>> mapLists=opertationDateDao.findOperationInfoList();
			dao.insertNeedInsertData(mapLists);

	}
}