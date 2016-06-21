/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.AdChannelInfo;
import com.thinkgem.jeesite.modules.sys.dao.AdChannelInfoDao;

/**
 * 推广渠道信息Service
 * @author wanduanrui
 * @version 2015-11-17
 */
@Service
@Transactional(readOnly = true)
public class AdChannelInfoService extends CrudService<AdChannelInfoDao, AdChannelInfo> {
	@Autowired
	private AdChannelInfoDao adChannelInfoDao;
	
	public AdChannelInfo get(String id) {
		return super.get(id);
	}
	
	public List<AdChannelInfo> findList(AdChannelInfo adChannelInfo) {
		return super.findList(adChannelInfo);
	}
	
	public Page<AdChannelInfo> findPage(Page<AdChannelInfo> page, AdChannelInfo adChannelInfo) {
		return super.findPage(page, adChannelInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AdChannelInfo adChannelInfo) {
		super.save(adChannelInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdChannelInfo adChannelInfo) {
		super.delete(adChannelInfo);
	}

	public Page<Map<String, Object>> registPopChannelsStatisticsPage(Page<Map<String, Object>> page, String channelId,
			Date beginDate, Date endDate) {
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("channelId", channelId);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("dbName", Global.getConfig("jdbc.type"));
		List<Map<String, Object>> result = adChannelInfoDao.registPopChannelsStatisticsList(params);
		page.setList(result);
		
		return page;
	}

	public AdChannelInfo getAdChannelInfoByName(String name) {
		// TODO Auto-generated method stub
		AdChannelInfo adChannelInfo = new AdChannelInfo();
		adChannelInfo.setChannelName(name);
		return adChannelInfoDao.getByName(adChannelInfo);
	}
	
}