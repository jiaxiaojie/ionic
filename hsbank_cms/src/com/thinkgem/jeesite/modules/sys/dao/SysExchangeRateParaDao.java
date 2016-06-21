/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.SysExchangeRatePara;

/**
 * 汇率参数DAO接口
 * @author lzb
 * @version 2016-04-20
 */
@MyBatisDao
public interface SysExchangeRateParaDao extends CrudDao<SysExchangeRatePara> {
	
	SysExchangeRatePara getRateParaByRateType(@Param("rateType") String rateType);
	
}