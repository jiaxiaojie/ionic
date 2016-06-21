/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.SysExchangeRatePara;
import com.thinkgem.jeesite.modules.sys.dao.SysExchangeRateParaDao;

/**
 * 汇率参数Service
 * @author lzb
 * @version 2016-04-20
 */
@Service
@Transactional(readOnly = true)
public class SysExchangeRateParaService extends CrudService<SysExchangeRateParaDao, SysExchangeRatePara> {

	public SysExchangeRatePara get(String id) {
		return super.get(id);
	}
	
	public List<SysExchangeRatePara> findList(SysExchangeRatePara sysExchangeRatePara) {
		return super.findList(sysExchangeRatePara);
	}
	
	public Page<SysExchangeRatePara> findPage(Page<SysExchangeRatePara> page, SysExchangeRatePara sysExchangeRatePara) {
		return super.findPage(page, sysExchangeRatePara);
	}
	
	@Transactional(readOnly = false)
	public void save(SysExchangeRatePara sysExchangeRatePara) {
		super.save(sysExchangeRatePara);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysExchangeRatePara sysExchangeRatePara) {
		super.delete(sysExchangeRatePara);
	}
	
	/**
	 * 根据汇率类型获取一条信息
	 * @param rateType
	 * @return
	 */
	public SysExchangeRatePara getRateParaByRateType(String rateType){
		return dao.getRateParaByRateType(rateType);
	}
	
}