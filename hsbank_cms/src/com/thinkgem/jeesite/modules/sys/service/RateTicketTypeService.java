/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.RateTicketType;
import com.thinkgem.jeesite.modules.sys.dao.RateTicketTypeDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 加息券类型Service
 * @author ydt
 * @version 2016-04-05
 */
@Service
@Transactional(readOnly = true)
public class RateTicketTypeService extends CrudService<RateTicketTypeDao, RateTicketType> {

	public RateTicketType get(String id) {
		return super.get(id);
	}
	
	public List<RateTicketType> findList(RateTicketType rateTicketType) {
		return super.findList(rateTicketType);
	}
	
	public Page<RateTicketType> findPage(Page<RateTicketType> page, RateTicketType rateTicketType) {
		return super.findPage(page, rateTicketType);
	}
	
	@Transactional(readOnly = false)
	public void save(RateTicketType rateTicketType) {
		if(rateTicketType.getIsNewRecord()) {
			rateTicketType.setCreateUser(Long.parseLong(UserUtils.getUser().getId()));
			rateTicketType.setCreateDt(new Date());
			dao.insert(rateTicketType);
		} else {
			rateTicketType.setLastModifyUser(Long.parseLong(UserUtils.getUser().getId()));
			rateTicketType.setLastModifyDt(new Date());
			dao.update(rateTicketType);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(RateTicketType rateTicketType) {
		super.delete(rateTicketType);
	}
	
}