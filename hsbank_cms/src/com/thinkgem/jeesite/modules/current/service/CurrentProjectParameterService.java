/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.current.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectParameter;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectParameterDao;

/**
 * 活期产品参数Service
 * @author ydt
 * @version 2015-12-09
 */
@Service
@Transactional(readOnly = true)
public class CurrentProjectParameterService extends CrudService<CurrentProjectParameterDao, CurrentProjectParameter> {

	public CurrentProjectParameter get(String id) {
		return super.get(id);
	}
	
	public List<CurrentProjectParameter> findList(CurrentProjectParameter currentProjectParameter) {
		return super.findList(currentProjectParameter);
	}
	
	public Page<CurrentProjectParameter> findPage(Page<CurrentProjectParameter> page, CurrentProjectParameter currentProjectParameter) {
		return super.findPage(page, currentProjectParameter);
	}
	
	@Transactional(readOnly = false)
	public void save(CurrentProjectParameter currentProjectParameter) {
		super.save(currentProjectParameter);
	}
	
	@Transactional(readOnly = false)
	public void delete(CurrentProjectParameter currentProjectParameter) {
		super.delete(currentProjectParameter);
	}
	
}