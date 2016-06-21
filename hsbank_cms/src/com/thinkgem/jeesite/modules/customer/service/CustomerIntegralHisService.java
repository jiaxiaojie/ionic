/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.api.PageBean;
import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralHisDao;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralHis;
import com.thinkgem.jeesite.modules.entity.front.PageSearchBean;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 会员花生豆变更流水Service
 * @author ydt
 * @version 2015-06-26
 */
@Service
@Transactional(readOnly = true)
public class CustomerIntegralHisService extends CrudService<CustomerIntegralHisDao, CustomerIntegralHis> {

	public CustomerIntegralHis get(String id) {
		return super.get(id);
	}
	
	public List<CustomerIntegralHis> findList(CustomerIntegralHis customerIntegralHis) {
		return super.findList(customerIntegralHis);
	}
	
	public Page<CustomerIntegralHis> findPage(Page<CustomerIntegralHis> page, CustomerIntegralHis customerIntegralHis) {
		return super.findPage(page, customerIntegralHis);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerIntegralHis customerIntegralHis) {
		super.save(customerIntegralHis);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerIntegralHis customerIntegralHis) {
		super.delete(customerIntegralHis);
	}

	public Integer getTotalIntegral(Long accountId) {
		return dao.getTotalIntegral(accountId);
	}

	public Integer getIntegralCurrentMonth(Long accountId) {
		//获取前月的第一天
		Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.DAY_OF_MONTH, 1);  
		//将小时至0  
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		//将分钟至0  
		calendar.set(Calendar.MINUTE, 0);  
		//将秒至0  
		calendar.set(Calendar.SECOND,0);  
		//将毫秒至0  
		calendar.set(Calendar.MILLISECOND, 0);  
		//获得当前月第一天  
		Date firstDay = calendar.getTime();  
		return dao.getIntegralDuringTime(accountId,firstDay,new Date());
	}

	public Page<CustomerIntegralHis> findListWithQuery(Long accountId, String changeTypeCode, PageSearchBean pageSearchBean) {
		Page<CustomerIntegralHis> page = new Page<CustomerIntegralHis>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", pageSearchBean.getEndDateTime());
		map.put("changeTypeCode", changeTypeCode);
		page.setList(dao.findListWithQuery(map));
		return page;
	}

	public Page<CustomerIntegralHis> findListWithQuery(Long accountId, String changeTypeCode, PageSearchBean pageSearchBean,CustomerIntegralHis customerIntegralHis) {
		Page<CustomerIntegralHis> page = new Page<CustomerIntegralHis>(pageSearchBean.getPageNo(), ProjectConstant.FRONT_PAGE_SIZE);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountId", accountId);
		map.put("page", page);
		map.put("startDateTime", pageSearchBean.getStartDateTime());
		map.put("endDateTime", DateUtils.toRealEndDate(pageSearchBean.getEndDateTime()));
		map.put("changeTypeCode", changeTypeCode);

		if(customerIntegralHis != null){
			map.put("changeType", customerIntegralHis.getChangeType());
			map.put("startDateTime",customerIntegralHis.getBeginOpDate() );
			map.put("endDateTime", DateUtils.toRealEndDate(customerIntegralHis.getEndOpDate()));
		}

		page.setList(dao.findListWithQuery(map));
		return page;
	}
	
	/**
	 * 分页获取花生豆信息
	 * @param accountId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<CustomerIntegralHis> findPageList(Long accountId, String type,Integer pageNumber, Integer pageSize) {
		Map<String, Object> map = new HashMap<String,Object>();
		PageBean pageBean = new PageBean(pageNumber, pageSize);
		map.put("startNumber", pageBean.getStartNumber());
		map.put("endNumber", pageBean.getEndNumber());
		map.put("accountId", accountId);
		map.put("type",type);
		return dao.findPageList(map);
	}

	public Integer getCount(Long customerId,String type) {
		return dao.getCount(customerId,type);
	}
}