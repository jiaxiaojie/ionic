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
import com.thinkgem.jeesite.modules.entity.IntegralMallProductPrice;
import com.thinkgem.jeesite.modules.integral.dao.IntegralMallProductPriceDao;

/**
 * 产品价格策略Service
 * @author lizibo
 * @version 2015-09-21
 */
@Service
@Transactional(readOnly = true)
public class IntegralMallProductPriceService extends CrudService<IntegralMallProductPriceDao, IntegralMallProductPrice> {
	@Autowired
	IntegralMallProductPriceDao integralMallProductPriceDao;

	public IntegralMallProductPrice get(String id) {
		return super.get(id);
	}
	
	public List<IntegralMallProductPrice> findList(IntegralMallProductPrice integralMallProductPrice) {
		return super.findList(integralMallProductPrice);
	}
	
	public Page<IntegralMallProductPrice> findPage(Page<IntegralMallProductPrice> page, IntegralMallProductPrice integralMallProductPrice) {
		return super.findPage(page, integralMallProductPrice);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralMallProductPrice integralMallProductPrice) {
		super.save(integralMallProductPrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralMallProductPrice integralMallProductPrice) {
		super.delete(integralMallProductPrice);
	}
	
	/**
	 * 根据产品价格策略获得当前有效的价格
	 * @param productId
	 * @param oldPrice
	 * @return
	 */
	public int getRightShowPrice(String productId, int oldPrice){
		List<IntegralMallProductPrice> list=integralMallProductPriceDao.getListByProductId(productId);
		if((list==null)||(list.size()==0)){
			return oldPrice;
		}else{
			
			IntegralMallProductPrice item=list.get(0);
			
			if("1".equals(item.getStatus())){
				if(item.getPriceType().equals("1")){
					return item.getMarketNewPrice();
				}else{
					return (int)(item.getMarketDiscount() * oldPrice);
				}
			}
			else{
				return oldPrice;
			}
			
		}
	}
}