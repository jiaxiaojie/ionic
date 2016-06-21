/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.integral.service;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.entity.IntegralMallProduct;
import com.thinkgem.jeesite.modules.integral.dao.IntegralMallProductDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 花生乐园上架产品Service
 * @author lizibo
 * @version 2015-09-22
 */
@Service
@Transactional(readOnly = true)
public class IntegralMallProductService extends CrudService<IntegralMallProductDao, IntegralMallProduct> {
	@Autowired
	private IntegralMallProductDao integralMallProductDao;

	public IntegralMallProduct get(String id) {
		return super.get(id);
	}
	
	public List<IntegralMallProduct> findList(IntegralMallProduct integralMallProduct) {
		return super.findList(integralMallProduct);
	}
	
	public Page<IntegralMallProduct> findPage(Page<IntegralMallProduct> page, IntegralMallProduct integralMallProduct) {
		return super.findPage(page, integralMallProduct);
	}
	
	public List<IntegralMallProduct> getMallProductPageList(Integer pageNumber, Integer pageSize){
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("theDay", new Date());
		map.put("status", "1");
		return dao.searchPageList(map);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegralMallProduct integralMallProduct) {
		if ((integralMallProduct.getProductId() == null)
				|| integralMallProduct.getProductId().equals("")) {
			integralMallProductDao.insert(integralMallProduct);
		} else {
			integralMallProductDao.update(integralMallProduct);
		}
//		super.save(integralMallProduct);
	}
	
	@Transactional(readOnly = false)
	public void review(IntegralMallProduct integralMallProduct) {
		integralMallProductDao.update(integralMallProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(IntegralMallProduct integralMallProduct) {
		super.delete(integralMallProduct);
	}
	
	public Page<IntegralMallProduct> searchList(String searchType, String integralRange, String keywords, String orderBy, String theDay,String pageNo) {
		Page<IntegralMallProduct> page = new Page<IntegralMallProduct>(NumberUtil.toInt(pageNo, 1), ProjectConstant.FRONT_PAGE_SIZE);
		page.setOrderBy(orderBy);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("integralRange", integralRange);
		map.put("keywords", keywords);
		map.put("theDay", theDay);
		map.put("page", page);
		page.setList(integralMallProductDao.searchList(map));
		return page;
	}

	/**
	 * 根据type查询花生乐园产品
	 * @param integralMallProductPage
	 * @param type (1:优惠券,2:商品实物）
     * @return
     */
	public Page<IntegralMallProduct> findPage(Page<IntegralMallProduct> integralMallProductPage, String type) {


		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("type",type);


		return findPage(integralMallProductPage,params);
	}

	/**
	 * 查询花生乐园产品通用方法 无分页
	 * @param params
	 * @return
     */
	public List<IntegralMallProduct> queryList(Map<String,Object> params){
		String theDay= DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		params.put("theDay", theDay);
		return integralMallProductDao.queryList(params);
	}

	/**
	 * 查询花生乐园产品通用方法 有分页
	 * @param page
	 * @param params
     * @return
     */
	public Page<IntegralMallProduct> findPage(Page<IntegralMallProduct> page, Map<String,Object> params) {
		params.put("page", page);
		String theDay= DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		params.put("theDay", theDay);
		page.setList(integralMallProductDao.queryList(params));
		return page;
	}


	public List<IntegralMallProduct> findList(String type,Integer beginLimit,Integer endLimit) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("type",type);
		params.put("beginLimit",beginLimit);
		params.put("endLimit",endLimit);
		return queryList(params);
	}
}