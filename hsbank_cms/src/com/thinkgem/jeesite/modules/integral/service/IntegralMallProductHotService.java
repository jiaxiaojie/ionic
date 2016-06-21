/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductHot;
import com.thinkgem.jeesite.modules.integral.dao.IntegralMallProductHotDao;

/**
 * 产品活动标签Service
 * @author lizibo
 * @version 2015-09-21
 */
@Service
@Transactional(readOnly = true)
public class IntegralMallProductHotService extends CrudService<IntegralMallProductHotDao, IntegralMallProductHot> {

	public IntegralMallProductHot get(String id) {
		return super.get(id);
	}
	
	public List<IntegralMallProductHot> findList(IntegralMallProductHot integralMallProductHot) {
		return super.findList(integralMallProductHot);
	}
	
	public Page<IntegralMallProductHot> findPage(Page<IntegralMallProductHot> page, IntegralMallProductHot integralMallProductHot) {
		return super.findPage(page, integralMallProductHot);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralMallProductHot integralMallProductHot) {
		super.save(integralMallProductHot);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralMallProductHot integralMallProductHot) {
		super.delete(integralMallProductHot);
	}
	
}