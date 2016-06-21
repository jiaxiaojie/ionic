package com.thinkgem.jeesite.modules.marketing.task;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.EuropeanCupTopScorer;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.EuropeanCupTopScorerDao;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1020Handler;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingMoneyAwardRecordService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将欧洲杯活动最佳射手榜前10名获得的奖励赠送给用户
 * @author lzb
 *
 */
@Component
public class GiveActivity1020TopScorerAwardTask {

	
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private MarketingMoneyAwardRecordService marketingMoneyAwardRecordService;
	@Autowired
	private EuropeanCupTopScorerDao europeanCupTopScorerDao;
	
	/**
	 * 2016-07-11 00:30:00将活动1020 欧洲杯活动最佳射手榜前10名获得的奖励，插入到现金奖励记录中，随后由task完成现金赠送动作
	 *
	 */
	public void job() {
		logger.info("=====giveActivity1020TopScorerTask start=====");
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1020Handler.class.getSimpleName()));
		List<EuropeanCupTopScorer> list = europeanCupTopScorerDao.getTop10Scorer(marketingActivityInfo.getActicityId(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
		for(int i=0;i< list.size();i++){
			EuropeanCupTopScorer topScorer = list.get(i);
			int giveMoney = MarketConstant.ACTIVITY_1020_EUROPEAN_CUP_TOP10SCORER_REWARD_AMOUNT[i];
			marketingMoneyAwardRecordService.offlineDoGiveMoney(topScorer.getAccountId(), giveMoney, MarketConstant.ACTIVITY_1020_EUROPEAN_CUP_TOP10SCORER_REWARD_REMARK, Long.parseLong(topScorer.getId()), ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF,
					MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, "", topScorer.getActivityId());
		}
		logger.info("=====giveActivity1020TopScorerTask end=====");
	}
}
