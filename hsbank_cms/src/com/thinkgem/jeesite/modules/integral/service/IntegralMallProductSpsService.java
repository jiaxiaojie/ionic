/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.IntegralMallProductSps;
import com.thinkgem.jeesite.modules.integral.dao.IntegralMallProductSpsDao;

/**
 * 产品规格参数Service
 * @author lizibo
 * @version 2015-09-21
 */
@Service
@Transactional(readOnly = true)
public class IntegralMallProductSpsService extends CrudService<IntegralMallProductSpsDao, IntegralMallProductSps> {
	@Autowired
	private IntegralMallProductSpsDao integralMallProductSpsDao;

	public IntegralMallProductSps get(String id) {
		return super.get(id);
	}
	
	public List<IntegralMallProductSps> findList(IntegralMallProductSps integralMallProductSps) {
		return super.findList(integralMallProductSps);
	}
	
	public Page<IntegralMallProductSps> findPage(Page<IntegralMallProductSps> page, IntegralMallProductSps integralMallProductSps) {
		return super.findPage(page, integralMallProductSps);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralMallProductSps integralMallProductSps) {
		super.save(integralMallProductSps);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralMallProductSps integralMallProductSps) {
		super.delete(integralMallProductSps);
	}
	
	/**
	 * 根据产品编号获取最后一条数据
	 * @param productId
	 * @return
	 */
	public IntegralMallProductSps getLastOneByProductId(Long productId) {
		return integralMallProductSpsDao.getLastOneByProductId(productId);
	}

	/**
	 * 获取产品的规格参数
	 * @param productId
	 * @return
	 */
	public List<IntegralMallProductSps> findListByProductId(String productId) {
		return dao.findListByProductId(productId);
	}
	
}