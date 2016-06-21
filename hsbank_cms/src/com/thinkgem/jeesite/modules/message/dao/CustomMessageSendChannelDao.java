/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomMessageSendChannel;

/**
 * 信息渠道DAO接口
 * @author huangyuchen
 * @version 2016-01-18
 */
@MyBatisDao
public interface CustomMessageSendChannelDao extends CrudDao<CustomMessageSendChannel> {
    /**
     * 插入渠道
     * @param channelLimitList
     * @return
     */
	public int insertBatch(List<CustomMessageSendChannel> channelLimitList);
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);
	/**
	 * 根据id查询渠道信息
	 * @param id
	 * @return
	 */
	List<CustomMessageSendChannel> findListById(String id);

}