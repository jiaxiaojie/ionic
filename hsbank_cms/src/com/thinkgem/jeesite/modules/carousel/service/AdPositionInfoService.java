/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.service;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.carousel.dao.AdPositionShowTermDao;
import com.thinkgem.jeesite.modules.entity.AdPositionShowTerm;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.entity.AdPositionInfo;
import com.thinkgem.jeesite.modules.carousel.dao.AdPositionInfoDao;

/**
 * 广告位显示信息Service
 * @author huangyuchen
 * @version 2016-05-17
 */
@Service
@Transactional(readOnly = true)
public class AdPositionInfoService extends CrudService<AdPositionInfoDao, AdPositionInfo> {
	@Autowired
	private AdPositionShowTermDao adPositionShowTermDao;
	@Autowired
	private AdPositionInfoDao adPositionInfoDao;
	public AdPositionInfo get(String id) {
		return super.get(id);
	}

	public List<AdPositionInfo> findList(AdPositionInfo adPositionInfo) {
		return super.findList(adPositionInfo);
	}

	public Page<AdPositionInfo> findPage(Page<AdPositionInfo> page, AdPositionInfo adPositionInfo) {
		return super.findPage(page, adPositionInfo);
	}

	@Transactional(readOnly = false)
	public void delete(AdPositionInfo adPositionInfo) {
		super.delete(adPositionInfo);
		Long id =Long.parseLong(adPositionInfo.getId());
		// 删除终端限制（根据轮播图编号）
		adPositionShowTermDao.deleteById(id);
	}


	@Transactional(readOnly = false)
	public void save(AdPositionInfo adPositionInfo) {
		if (adPositionInfo.getId() != null && !"".equals(adPositionInfo.getId())) {
			adPositionInfoDao.update(adPositionInfo);
		} else {
			adPositionInfoDao.insert(adPositionInfo);
		}
		Long id = NumberUtils.toLong(adPositionInfo.getId(),0L);
		// 维护活动渠道限制数据
		String termCode = adPositionInfo.getTermCode();
		if (termCode != null) {
			this.channelLimitData(termCode, id);
		}
	}



	/**
	 * 渠道
	 *
	 * @param termCode
	 * @param id
	 * AdPositionShowTerm adPositionShowTerm
	 */
	public void channelLimitData(String termCode, Long id) {
		AdPositionShowTerm term = new AdPositionShowTerm();
		term.setAdPositionId(id);
		term.setTermCode(termCode);
		// 先删除（根据编号）
		adPositionShowTermDao.deleteById(id);
		// 批量插入
		adPositionShowTermDao.insertBatch(term);
	}

	/**
	 * 审核
	 * @param adPositionInfo
	 */
	@Transactional(readOnly = false)
	public void review(AdPositionInfo adPositionInfo) {
		adPositionInfoDao.update(adPositionInfo);
	}

	/**
	 * 根据状态、终端和广告位类型查询出对应的广告位信息
	 * @param adCode
	 * @aram opTerm
     * @return
     */
	public AdPositionInfo findAdPositionInfoByAdCodeAndTerminalType(String adCode, String opTerm) {
        return  adPositionInfoDao.findAdPositionInfoByAdCodeAndTerminalType(adCode,opTerm);
	}
}