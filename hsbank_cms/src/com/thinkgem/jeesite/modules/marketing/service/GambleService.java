/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralHisDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.entity.Gamble;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.GambleDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1016Handler;
import com.thinkgem.jeesite.modules.project.ProjectConstant;


/**
 * 竞猜Service
 * @author ydt
 * @version 2016-04-20
 */
@Service
@Transactional(readOnly = true)
public class GambleService extends CrudService<GambleDao, Gamble> {
	@Autowired
	private CustomerIntegralHisDao customerIntegralHisDao;
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	@Autowired
	private MarketingActivityInfoDao marketingActivityInfoDao;
	public Gamble get(String id) {
		return super.get(id);
	}
	
	public List<Gamble> findList(Gamble gamble) {
		return super.findList(gamble);
	}
	
	public Page<Gamble> findPage(Page<Gamble> page, Gamble gamble) {
		return super.findPage(page, gamble);
	}
	
	@Transactional(readOnly = false)
	public void save(Gamble gamble) {
		super.save(gamble);
	}
	
	@Transactional(readOnly = false)
	public void delete(Gamble gamble) {
		super.delete(gamble);
	}
	
	/**
	 * 获取用户某活动某天的参赛记录
	 * @param activityId
	 * @param accountId
	 * @param opDt
	 * @return
	 */
	public Gamble getByActivityIdAndAccountIdAndOpDate(Long activityId, Long accountId, Date opDt) {
		return dao.getByActivityIdAndAccountIdAndOpDate(activityId, accountId, opDt);
	}

	/**
	 * 更新某活动某日的比赛结果信息
	 * @param activityId
	 * @param rightSide
	 * @param date
	 */
	@Transactional(readOnly = false)
	public void updateRightSideByActivityIdAndOpDt(Long activityId, String rightSide, Date date) {
		dao.updateRightSideByActivityIdAndOpDt(activityId, rightSide, date);
	}

	/**
	 * 根据activityId、accountId、opDt(可选)查询用户竞猜列表
	 * @param activityId
	 * @param accountId
	 * @return
	 */
	public List<Gamble> findListByActivityIdAndAccountId(Long activityId, Long accountId) {
		return dao.findListByActivityIdAndAccountId(activityId, accountId, null);
	}

	/**
	 * 赠送用户竞猜奖励
	 * 	1.判断是否应该赠送奖励
	 *  2.判断用户领奖时间是否超过了活动领奖时间
	 * 	3.更新竞猜奖励时间
	 * 	4.赠送奖励
	 * 	5.添加奖励赠送记录
	 * @param gambleId
	 * @param opTerm
	 * @return
	 */
	@Transactional
	public Map<String, Object> giveIntegralAward(Long gambleId, String opTerm) {
		Map<String, Object> result = new HashMap<String,Object>();
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoDao
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1016Handler.class.getSimpleName()));
		if((new Date()).getTime() >= DateUtils.addDays(marketingActivityInfo.getEndDate(), 2).getTime()){
			result.put("isSuccess", false);
			result.put("message", "领奖时间已超时！");
			return result;
		}
		Gamble gamble = dao.get(gambleId + "");
		if(gamble.getChoiceSide().equals(gamble.getRightSide()) && gamble.getAwardDt() == null) {
			dao.updateAwardDtById(new Date(), gambleId);
			customerIntegralSnapshotHandler.changeIntegralValue(gamble.getAccountId(), MarketConstant.MATCH_GUESSINGS_1016_GIVE_INTEGRAL_1000,
					ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, MarketConstant.MATCH_GUESSINGS_SECSSES_1016_GIVE_INTEGRAL_REMARK, ProjectConstant.OP_TERM_DICT_PC);
			
			MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
			marketingActivityAwardRecord.setAccountId(gamble.getAccountId());
			marketingActivityAwardRecord.setActivityId(gamble.getActivityId());
			marketingActivityAwardRecord.setAwardDt(new Date());
			marketingActivityAwardRecord.setAwardReason(MarketConstant.MATCH_GUESSINGS_SECSSES_1016_GIVE_INTEGRAL_REMARK);
			marketingActivityAwardRecord.setAwardType(ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL);
			marketingActivityAwardRecord.setAwardValue(String.valueOf(MarketConstant.MATCH_GUESSINGS_1016_GIVE_INTEGRAL_1000));
			marketingActivityAwardRecord.setBehaviorCode(MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW);
			marketingActivityAwardRecord.setChannelId(opTerm);
			marketingActivityAwardRecord.setCauseType(ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF);
			marketingActivityAwardRecord.setCauseId(null);
			marketingActivityAwardRecordDao.insert(marketingActivityAwardRecord);
			result.put("isSuccess", true);
		} else {
			result.put("isSuccess", false);
			result.put("message", "不符合领奖条件");
		}
		return result;
	}
}