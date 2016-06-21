/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductType;
import com.thinkgem.jeesite.modules.integral.dao.IntegralMallProductTypeDao;

/**
 * 花生乐园上架产品类别Service
 * @author lizibo
 * @version 2015-09-23
 */
@Service
@Transactional(readOnly = true)
public class IntegralMallProductTypeService extends CrudService<IntegralMallProductTypeDao, IntegralMallProductType> {

	public IntegralMallProductType get(String id) {
		return super.get(id);
	}
	
	public List<IntegralMallProductType> findList(IntegralMallProductType integralMallProductType) {
		return super.findList(integralMallProductType);
	}
	
	public Page<IntegralMallProductType> findPage(Page<IntegralMallProductType> page, IntegralMallProductType integralMallProductType) {
		return super.findPage(page, integralMallProductType);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralMallProductType integralMallProductType) {
		super.save(integralMallProductType);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralMallProductType integralMallProductType) {
		super.delete(integralMallProductType);
	}
	
}