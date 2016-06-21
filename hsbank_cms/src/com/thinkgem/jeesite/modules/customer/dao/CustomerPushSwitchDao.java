/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerPushSwitch;

/**
 * 用户接收push消息开关DAO接口
 * @author lzb
 * @version 2016-02-25
 */
@MyBatisDao
public interface CustomerPushSwitchDao extends CrudDao<CustomerPushSwitch> {
	
	/**
	 * 根据账号、渠道获取push消息开关
	 * @param accountId
	 * @param pushChannel
	 * @return
	 */
	CustomerPushSwitch getCustomerPushSwitch(@Param("accountId") long accountId, @Param("pushChannel") String pushChannel);
	
	/**
	 * 更新是否接受push
	 * @param accountId
	 * @param pushChannel
	 * @param isReceive
	 * @return
	 */
	int updateIsReceive(@Param("accountId") long accountId, @Param("pushChannel") String pushChannel, @Param("isReceive") String isReceive);
	
}