package com.thinkgem.jeesite.modules.marketing.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.entity.JoinMatchRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivity1015Handler;
import com.thinkgem.jeesite.modules.marketing.service.JoinMatchRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;

/**
 * 将活动1015活动结束后一日用户应获得的奖励赠送给用户
 * @author ydt
 *
 */
@Component
public class GiveActivity1015TugWarAwardTask {
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private JoinMatchRecordService joinMatchRecordService;
	@Autowired
	private ProjectInvestmentRecordService projectInvestmentRecordService;

	public static final double winSideAmount = 20000;
	public static final double loseSideAmount = 10000;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 2016-06-01 00:15:00将活动1015用户应获得的奖励赠送给用户
	 * 	1.检测奖励时间
	 * 	2.赠送奖励
	 * 
	 * (2.1 红蓝两队按活动期间累计投资额PK，赢的团队获得20,000元现金奖励，输的团队获得10,000元现金奖励；2.2 团队成员按照投资金额占团队总投资比例分享现金奖励；（用户加入团队后的投资才计算为团队总投资，才可分享奖励；在加入团队之前的投资，不纳入比赛统计中）)
	 */
	public void job() {
		logger.info("=====giveActivity1015TugWarAwardTask start=====");
		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService
				.getByBizClassName(StringUtils.lowerCaseFirstLetter(MarketActivity1015Handler.class.getSimpleName()));
		//红队投资金额
		double redSideAmount = joinMatchRecordService
				.getSideInvestmentAmount(marketingActivityInfo.getActicityId(), MarketConstant.MATCH_SIDE_RED, marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
		//蓝队投资金额
		double blueSideAmount = joinMatchRecordService
				.getSideInvestmentAmount(marketingActivityInfo.getActicityId(), MarketConstant.MATCH_SIDE_BLUE, marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
		//红队奖励金额
		double redSideAwardAmount = redSideAmount >= blueSideAmount ? winSideAmount : loseSideAmount;
		//蓝队奖励金额
		double blueSideAwardAmount = redSideAwardAmount == winSideAmount ? loseSideAmount : winSideAmount;
		List<JoinMatchRecord> list = joinMatchRecordService.findListByActivityId(marketingActivityInfo.getActicityId());
		for(JoinMatchRecord joinMatchRecord : list) {
			//用户投资金额
			double userInvestmentAmount = joinMatchRecordService
					.getUserInvestmentAmount(marketingActivityInfo.getActicityId(), joinMatchRecord.getAccountId(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate());
			//奖励用户金额
			double awardAmount = 0d;
			if(MarketConstant.MATCH_SIDE_RED.equals(joinMatchRecord.getSide())) {
				awardAmount = NumberUtils.div(NumberUtils.mul(userInvestmentAmount, redSideAwardAmount), redSideAmount, 2);
			} else {
				awardAmount = NumberUtils.div(NumberUtils.mul(userInvestmentAmount, blueSideAwardAmount), blueSideAmount, 2);
			}
			//赠送奖励
			if(awardAmount > 0) {
				logger.info("tugWar should give accountId:" + joinMatchRecord.getAccountId() + ",amount:" + awardAmount + " start.");
				try {
					joinMatchRecordService.giveTugWarAward(joinMatchRecord.getAccountId(), marketingActivityInfo.getActicityId(), awardAmount, ProjectConstant.OP_TERM_DICT_PC);
				} catch(Exception e) {
					logger.info("tugWar should give accountId:" + joinMatchRecord.getAccountId() + ",amount:" + awardAmount + " exception.");
					e.printStackTrace();
				}
				logger.info("tugWar should give accountId:" + joinMatchRecord.getAccountId() + ",amount:" + awardAmount + " end.");
			}
		}
		logger.info("=====giveActivity1015TugWarAwardTask end=====");
	}
}
