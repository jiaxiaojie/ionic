/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.SysMobileVersionPara;

/**
 * 会员账户余额对齐DAO接口
 * @author lzb
 * @version 2015-11-10
 */
@MyBatisDao
public interface SysMobileVersionParaDao extends CrudDao<SysMobileVersionPara> {
	public SysMobileVersionPara getSysMobileVersionPara(@Param("channel") String channel, @Param("type") String type);

	/**
	 * @author 万端瑞
	 * @param sysMobileVersionPara
	 */
	public void updateAllMark(SysMobileVersionPara sysMobileVersionPara);
}