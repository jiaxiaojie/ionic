/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.AdChannelInfo;

/**
 * 推广渠道信息DAO接口
 * @author wanduanrui
 * @version 2015-11-17
 */
@MyBatisDao
public interface AdChannelInfoDao extends CrudDao<AdChannelInfo> {

	AdChannelInfo getByChannel(String channel);

	List<Map<String, Object>> registPopChannelsStatisticsList(HashMap<String, Object> params);

	AdChannelInfo getByName(AdChannelInfo adChannelInfo);
	
}