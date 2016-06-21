/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBlanceAlignment;

/**
 * 会员账户余额对齐DAO接口
 * @author lzb
 * @version 2015-11-03
 */
@MyBatisDao
public interface CustomerBlanceAlignmentDao extends CrudDao<CustomerBlanceAlignment> {
	/**
	 * 选择更新
	 * @param customerBlanceAlignment
	 * @return
	 */
	public int updateSelected(CustomerBlanceAlignment customerBlanceAlignment);

	/**
	 * 删除days天以前的数据
	 * @param days
	 */
	public void deleteSomeDaysAgoData(int days);
}