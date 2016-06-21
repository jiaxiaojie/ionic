/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import com.hsbank.util.type.NumberUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.MarketingActivityChannelLimit;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.MarketingActivityOpHis;
import com.thinkgem.jeesite.modules.entity.MarketingActivityUserBehaviorLimit;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityChannelLimitDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityOpHisDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityUserBehaviorLimitDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author yangtao
 *
 */
@Component("marketActivityHandler")
public class MarketActivityHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MarketingActivityInfoDao marketingActivityInfoDao;
	@Autowired
	private MarketingActivityOpHisDao marketingActivityOpHisDao;
	@Autowired
	private MarketingActivityChannelLimitDao marketingActivityChannelLimitDao;
	@Autowired
	private MarketingActivityUserBehaviorLimitDao marketingActivityUserBehaviorLimitDao;

	/**
	 * 根据行为编号及渠道获取有效活动列表
	 * 
	 * @param behaviorCode
	 * @param channelId
	 * @return
	 */
	public List<MarketingActivityInfo> getRelationActivityList(
			String behaviorCode, String channelId) {
		String status = MarketConstant.MARKETING_ACTIVITY_STATUS_PASS;
		String currDate = DateUtils.getDate();
		String currTime = DateUtils.getTime();
		return marketingActivityInfoDao.getRelationActivityList(behaviorCode,
				channelId, currDate, currTime, status);
	}

	/**
	 * 获取有效活动列表
	 * 
	 * @return
	 */
	public List<MarketingActivityInfo> getEffectiveActivityList() {
		String status = MarketConstant.MARKETING_ACTIVITY_STATUS_PASS;
		String currDate = DateUtils.getDate();
		String currTime = DateUtils.getTime();
		List<MarketingActivityInfo> list = marketingActivityInfoDao.getEffectiveActivityList(currDate, currTime, status);
		for (MarketingActivityInfo activityInfo : list) {
			List<MarketingActivityChannelLimit> channelLimitList = marketingActivityChannelLimitDao.findListByActivityId(activityInfo.getActicityId());
			List<MarketingActivityUserBehaviorLimit> behaviorLimitList = marketingActivityUserBehaviorLimitDao.findListByActivityCode(activityInfo.getActicityId());
			activityInfo.setChannelLimitList(channelLimitList);
			activityInfo.setBehaviorLimitList(behaviorLimitList);
		}
		return list;
	}

	/**
	 * 根据活动编号获取活动信息
	 * @param acticityId
	 * @return
     */
	public MarketingActivityInfo getActivityInfoById(String acticityId) {
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoDao
				.get(acticityId);
		List<MarketingActivityChannelLimit> channelLimitList = marketingActivityChannelLimitDao
				.findListByActivityId(NumberUtil.toLong(acticityId, 0L));
		List<MarketingActivityUserBehaviorLimit> behaviorLimitList = marketingActivityUserBehaviorLimitDao
				.findListByActivityCode(NumberUtil.toLong(acticityId, 0L));
		marketingActivityInfo.setChannelLimitList(channelLimitList);
		marketingActivityInfo.setBehaviorLimitList(behaviorLimitList);
		return marketingActivityInfo;
	}

	/**
	 * 增加，营销活动操作流水
	 * 
	 * @param acticityId
	 * @param behaviorCode
	 * @param accountId
	 * @param channelId
	 * @return
	 */
	public MarketingActivityOpHis addMarketingActivityOpHis(Long acticityId,
			String behaviorCode, Long accountId, String channelId, String inPara, String outPara, String resultCode) {
		MarketingActivityOpHis marketingActivityOpHis = new MarketingActivityOpHis();
		marketingActivityOpHis.setActicityId(acticityId);
		marketingActivityOpHis.setBehaviorCode(behaviorCode);
		marketingActivityOpHis.setAccountId(accountId);
		marketingActivityOpHis.setChannelId(channelId);
		marketingActivityOpHis.setOpDt(new Date());
		marketingActivityOpHis.setInPara(inPara);
		marketingActivityOpHis.setOutPara(outPara);
		marketingActivityOpHis.setResultCode(resultCode);
		marketingActivityOpHisDao.insert(marketingActivityOpHis);
		return marketingActivityOpHis;
	}

}
