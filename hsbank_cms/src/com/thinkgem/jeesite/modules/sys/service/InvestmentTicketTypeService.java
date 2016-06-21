/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.InvestmentTicketType;
import com.thinkgem.jeesite.modules.sys.dao.InvestmentTicketTypeDao;

/**
 * 投资券类型管理Service
 * @author yangtao
 * @version 2015-07-21
 */
@Service
@Transactional(readOnly = true)
public class InvestmentTicketTypeService extends CrudService<InvestmentTicketTypeDao, InvestmentTicketType> {

	public InvestmentTicketType get(String id) {
		return super.get(id);
	}
	
	public List<InvestmentTicketType> findList(InvestmentTicketType InvestmentTicketType) {
		return super.findList(InvestmentTicketType);
	}
	
	public Page<InvestmentTicketType> findPage(Page<InvestmentTicketType> page, InvestmentTicketType InvestmentTicketType) {
		return super.findPage(page, InvestmentTicketType);
	}
	
	@Transactional(readOnly = false)
	public void save(InvestmentTicketType InvestmentTicketType) {
		super.save(InvestmentTicketType);
	}
	
	@Transactional(readOnly = false)
	public void delete(InvestmentTicketType InvestmentTicketType) {
		super.delete(InvestmentTicketType);
	}

	/**
	 * 根据status查询投资券类型列表
	 * @param status
	 * @return
	 */
	public List<InvestmentTicketType> findListByStatus(String status) {
		return dao.findListByStatus(status);
	}
	
}