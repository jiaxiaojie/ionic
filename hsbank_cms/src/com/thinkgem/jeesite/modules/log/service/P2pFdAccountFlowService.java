/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.log.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.P2pFdAccountFlow;
import com.thinkgem.jeesite.modules.log.dao.P2pFdAccountFlowDao;

/**
 * 平台账号流水Service
 * @author yangtao
 * @version 2015-08-12
 */
@Service
@Transactional(readOnly = true)
public class P2pFdAccountFlowService extends CrudService<P2pFdAccountFlowDao, P2pFdAccountFlow> {

	public P2pFdAccountFlow get(String id) {
		return super.get(id);
	}
	
	public List<P2pFdAccountFlow> findList(P2pFdAccountFlow p2pFdAccountFlow) {
		return super.findList(p2pFdAccountFlow);
	}
	
	public Page<P2pFdAccountFlow> findPage(Page<P2pFdAccountFlow> page, P2pFdAccountFlow p2pFdAccountFlow) {
		return super.findPage(page, p2pFdAccountFlow);
	}
	
	@Transactional(readOnly = false)
	public void save(P2pFdAccountFlow p2pFdAccountFlow) {
		super.save(p2pFdAccountFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(P2pFdAccountFlow p2pFdAccountFlow) {
		super.delete(p2pFdAccountFlow);
	}
	
}