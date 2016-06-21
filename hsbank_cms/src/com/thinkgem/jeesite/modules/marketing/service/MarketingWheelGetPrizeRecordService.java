/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.*;

import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.MarketingWheelGetPrizeRecord;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingWheelGetPrizeRecordDao;

/**
 * 大转盘中奖记录Service
 * @author ydt
 * @version 2015-11-24
 */
@Service
@Transactional(readOnly = true)
public class MarketingWheelGetPrizeRecordService extends CrudService<MarketingWheelGetPrizeRecordDao, MarketingWheelGetPrizeRecord> {
	@Autowired
	private MarketingActivityInfoDao marketingActivityInfoDao;

	public MarketingWheelGetPrizeRecord get(String id) {
		return super.get(id);
	}
	
	public List<MarketingWheelGetPrizeRecord> findList(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord) {
		return super.findList(marketingWheelGetPrizeRecord);
	}
	
	public Page<MarketingWheelGetPrizeRecord> findPage(Page<MarketingWheelGetPrizeRecord> page, MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord) {
		return super.findPage(page, marketingWheelGetPrizeRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord) {
		super.save(marketingWheelGetPrizeRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord) {
		super.delete(marketingWheelGetPrizeRecord);
	}
	
	@Transactional(readOnly = false)
	public void insert(MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord) {
		dao.insert(marketingWheelGetPrizeRecord);
	}

	/**
	 * 插入获奖记录
	 * @param prizeInstanceId
	 * @param opTerm
	 */
	@Transactional(readOnly = false)
	public void insertRecord(long prizeInstanceId, String token, String opTerm) {
		MarketingWheelGetPrizeRecord marketingWheelGetPrizeRecord = new MarketingWheelGetPrizeRecord();
		marketingWheelGetPrizeRecord.setPrizeInstanceId(prizeInstanceId);
		marketingWheelGetPrizeRecord.setStatus(MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GET);
		marketingWheelGetPrizeRecord.setToken(token);
		marketingWheelGetPrizeRecord.setOpTerm(opTerm);
		marketingWheelGetPrizeRecord.setInvalidDt(DateUtils.dateAddMinute(new Date(), MarketConstant.WHEEL_TOKEN_TIME_OUT));
		marketingWheelGetPrizeRecord.setGetDt(new Date());
		dao.insert(marketingWheelGetPrizeRecord);
	}

	/**
	 * 根据状态查询获奖记录列表
	 * @param status
	 * @return
	 */
	public List<MarketingWheelGetPrizeRecord> findListByStatus(String status) {
		return dao.findListByStatus(status);
	}
	
	/**
	 * 更新奖励记录状态
	 * @param id
	 * @param status
	 */
	@Transactional(readOnly = false)
	public void updateStatusToGiven(String id) {
		dao.updateStatusToGiven(id);
	}
	
	/**
	 * 获取记录的所有信息（包括 用户名 手机号……）
	 * @param id
	 * @return
	 */
	public MarketingWheelGetPrizeRecord getAllInfo(String id) {
		return dao.getAllInfo(id);
	}

	/**
	 * 获取用户某活动的中奖信息
	 * @param accountId
	 * @param activityId
	 * @return
	 */
	public List<MarketingWheelGetPrizeRecord> findGotListByAccountIdAndActivityId(Long accountId, Long activityId) {
		return dao.findGotListByAccountIdAndActivityId(accountId, activityId);
	}

	/**
	 * 获取某活动奖品类型为prizeType的获奖记录列表
	 * @param prizeType
	 * @param limit
	 * @return
	 */
	public List<MarketingWheelGetPrizeRecord> findGotListByActivityIdAndPrizeType(Long activityId, String prizeType, int limit) {
		return dao.findGotListByActivityIdAndPrizeType(activityId, prizeType, limit);
	}

	/**
	 * 获取用户某活动的奖品数据（已赠送）
	 * @param code
	 * @param pageSize
	 * @param pageNumber
	 * @param accountId
     * @return
     */
	public Map<String, Object> getGivePrizeList(String code, Integer pageSize, Integer pageNumber, Long accountId) {
		Map<String, Object> data = new HashMap<String, Object>();
		int count = 0;
		pageSize = pageSize == null ? 10 : pageSize;
		pageNumber = pageNumber == null ? 1 : pageNumber;
		MarketingActivityInfo marketingActivityInfo = code == null ? null : marketingActivityInfoDao.getByBizClassName("marketActivity" + code + "Handler");
		List<Map<String,String>> prizeList = new ArrayList<Map<String,String>>();
		if(marketingActivityInfo != null) {
			Page<MarketingWheelGetPrizeRecord> page = this.findPrizeRecordPageList(new Page<MarketingWheelGetPrizeRecord>(pageNumber, pageSize, true),
					accountId,marketingActivityInfo.getActicityId(),MarketConstant.WHEEL_GET_PRIZE_RECORD_STATUS_GIVEN);
			for(MarketingWheelGetPrizeRecord prizeRecord : page.getList()){
				Map<String,String> m = new HashMap<String,String>();
				m.put("date", DateUtils.formatDate(prizeRecord.getGetDt(), "yyyy-MM-dd HH:mm:ss"));
				m.put("prize", prizeRecord.getPrizeName());
				prizeList.add(m);
			}
			count = new Long(page.getCount()).intValue();
		}
		data.put("count", count);
		data.put("data", prizeList);
		return data;
	}

	/**
	 * 获取用户某活动的奖品分页列表
	 * @param page
	 * @param accountId
	 * @param activityId
	 * @param status
     * @return
     */
	public Page<MarketingWheelGetPrizeRecord> findPrizeRecordPageList(Page<MarketingWheelGetPrizeRecord> page,Long accountId, Long activityId, String status){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("page",page);
		map.put("accountId",accountId);
		map.put("activityId",activityId);
		map.put("status", status);
		page.setList(dao.findPrizeRecordPageList(map));
		return page;
	}
}