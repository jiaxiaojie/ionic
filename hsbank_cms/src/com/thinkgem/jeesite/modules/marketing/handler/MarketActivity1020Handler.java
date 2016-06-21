package com.thinkgem.jeesite.modules.marketing.handler;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.exception.CustomException;
import com.thinkgem.jeesite.modules.api.frame.exception.MessageException;
import com.thinkgem.jeesite.modules.api.frame.exception.ParamsException;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;
import com.thinkgem.jeesite.modules.api.frame.util.BooleanUtils;
import com.thinkgem.jeesite.modules.api.frame.util.LotteryUtils;
import com.thinkgem.jeesite.modules.api.frame.util.ProbabilityUtils;
import com.thinkgem.jeesite.modules.api.frame.util.WStringUtils;
import com.thinkgem.jeesite.modules.customer.service.CustomerBaseService;
import com.thinkgem.jeesite.modules.entity.*;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 欧洲杯-射门
 * Created by 万端瑞 on 2016/6/13.
 */
@Component
public class MarketActivity1020Handler extends MarketActivityBaseSupportHandler {
	Logger logger = Logger.getLogger(this.getClass());
	private static final int SHOOT_TIMES = 3;
	/**本次射门机会正在进行中*/
	public static String SHOOT_RECORD_STATUS_DOING = "doing";
	/**本次射门机会已结束1*/
	public static String SHOOT_RECORD_STATUS_DID = "did";



	@Autowired
	private MarketingWheelLotteryTimesService marketingWheelLotteryTimesService;
	@Autowired
	private ShootRecordService shootRecordService;
	@Autowired
	private MobileAwardRecordService mobileAwardRecordService;
	@Autowired
	private EuropeanCupTopScorerService europeanCupTopScorerService;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	@Autowired
	private CustomerBaseService customerBaseService;

	private MarketingActivityInfo marketingActivityInfo = null;

	private static ProbabilityUtils probabilityUtils = new ProbabilityUtils(new Integer[]{0,55,40,5});

	public Long getActivityId(){
		if(marketingActivityInfo == null){
			marketingActivityInfo = marketingActivityInfoService.getByBizClassName("marketActivity1020Handler");
		}
		return marketingActivityInfo.getActicityId();
	}




	private class LotteryInfo{
		private LinkedList<Boolean> shootInfo;

		//剩余机会次数
		private Integer residualTimes;
		// 总机会次数
		private Integer totalTimes;

		public LotteryInfo(LinkedList<Boolean> shootInfo, Integer totalTimes, Integer residualTimes) {
			this.residualTimes = residualTimes;
			this.shootInfo = shootInfo;
			this.totalTimes = totalTimes;
		}

		public LinkedList<Boolean> getShootInfo() {
			return shootInfo;
		}

		public void setShootInfo(LinkedList<Boolean> shootInfo) {
			this.shootInfo = shootInfo;
		}

		public Integer getResidualTimes() {
			return residualTimes;
		}

		public void setResidualTimes(Integer residualTimes) {
			this.residualTimes = residualTimes;
		}

		public Integer getTotalTimes() {
			return totalTimes;
		}

		public void setTotalTimes(Integer totalTimes) {
			this.totalTimes = totalTimes;
		}
	}

	/**
	 * 注册逻辑
	 * 注册时判断用户未登录状态下是否踢过球，如果提过则更新相关数据
	 * @param para
	 * @return
	 */
	@Transactional(readOnly = false)
	@Override
	public Map<String, Object> register(Map<String, Object> para) {

		MarketingActivityInfo marketingActivityInfo = marketingActivityInfoService.getByBizClassName("marketActivity1020Handler");
		Long accountId = (Long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		CustomerBase customerBase = customerBaseService.getByAccountId(accountId);

		//更新手机号中奖记录
		MobileAwardRecord mobileAwardRecord = mobileAwardRecordService.getByActivityIdAndMobileAndStatus(marketingActivityInfo.getActicityId(), customerBase.getMobile(), MarketActivity1018Handler.MOBILE_AWARD_RECORD_STATUS_GIVING);
		if(mobileAwardRecord != null){
			mobileAwardRecord.setStatus(MarketActivity1018Handler.MOBILE_AWARD_RECORD_STATUS_GIVEN);
			mobileAwardRecord.setAccountId(accountId);
			mobileAwardRecord.setAwardDt(new Date());
			mobileAwardRecordService.save(mobileAwardRecord);

			//构造射门信息
			LinkedList<Boolean> shootInfo = getCurrentTimesChanceShootInfoByGoalCount(mobileAwardRecord.getPrizeInstanceId().intValue());

			//领取礼品
			receivePrize(customerBase,shootInfo, (String)para.get(MarketConstant.CHANNEL_PARA));
		}
		return super.register(para);
	}


	@Transactional(readOnly = false)
	public Map<String, Object> getPrize(String mobile,String shootInfoStr,String opTerm){
		Map<String, Object> result = null;
		try {
			receivePrize(mobile, shootInfoStr, opTerm);
			result = APIGenerator.createSuccessAPI();
		}catch (MessageException me){
			result = APIGenerator.createResultAPI(me.getCode(),me.getMessage());
		}catch (ParamsException e){
			result = APIGenerator.createResultAPI(2,e.getMessage());
		}
		return result;
	}

	/**
	 * 领取礼物 未登录状态下
	 * @param mobile
	 * @param shootInfoStr
	 * @param opTerm
     */
	@Transactional(readOnly = false)
	public void receivePrize(String mobile,String shootInfoStr,String opTerm){
		if(mobile != null){
			CustomerBase cb = customerBaseService.getByMobile(mobile);

			LinkedList<Boolean> shootInfo = BooleanUtils.toList(shootInfoStr);

			//如果已注册,则领取奖品
			if(cb != null){
				receivePrize(cb, shootInfo, opTerm);
			}//如果没有注册，记录信息，等注册后再发放奖品
			else{
				Integer goalCount = BooleanUtils.getCount(shootInfo,true);

				if(goalCount > SHOOT_TIMES){
					throw new ParamsException("进球数不能大于"+SHOOT_TIMES);
				}

				saveMobileAwardRecord(getActivityId(), mobile,goalCount ,opTerm);
			}
		}else {
			throw new  ParamsException("手机号不能为空");
		}
	}
	/**
	 *	领取礼物 未登录状态下
	 * @param customerBase
	 * @param shootInfo
	 * @param opTerm
     */
	public void receivePrize(CustomerBase customerBase,LinkedList<Boolean> shootInfo,String opTerm){
		updateLotteryInfo(getActivityId(),customerBase.getAccountId(), shootInfo, opTerm,false);
	}

	/**
	 * 通过当局进球次数获取当局射门信息
	 * @param goalCount
	 * @return
     */
	public LinkedList<Boolean> getCurrentTimesChanceShootInfoByGoalCount(Integer goalCount){
		if(goalCount > SHOOT_TIMES){
			throw new ParamsException("进球数不能大于"+SHOOT_TIMES);
		}
		LinkedList<Boolean> goalInfo = BooleanUtils.toList(WStringUtils.complement(WStringUtils.complement(new StringBuffer(),goalCount,'1'),SHOOT_TIMES,'0').toString());
		return  goalInfo;
	}


	/**
	 * 对于未注册用户，保存踢球信息到临时表
	 * @param activityId
	 * @param mobile
	 * @param goalBallCount
	 * @param opTerm
     */
	public void saveMobileAwardRecord(Long activityId, String mobile, Integer goalBallCount, String opTerm){
		MobileAwardRecord mobileAwardRecord = new MobileAwardRecord();
		mobileAwardRecord.setActivityId(activityId);
		mobileAwardRecord.setMobile(mobile);
		mobileAwardRecord.setPrizeInstanceId((long) goalBallCount);//保存的是进球数
		mobileAwardRecord.setOpDt(new Date());
		mobileAwardRecord.setOpTerm(opTerm);
		mobileAwardRecord.setStatus(MarketActivity1018Handler.MOBILE_AWARD_RECORD_STATUS_GIVING);
		mobileAwardRecordService.save(mobileAwardRecord);
	}

	/**
	 *
	 * @param map
	 * @return
	 */
	@Override
	@Transactional(readOnly = false)
	public Map<String,Object> luckDraw(Map<String,Object> map) {

		String handlerClassName = map.get(MarketConstant.HANDLER_PARA) == null ? null : (String)map.get(MarketConstant.HANDLER_PARA);
		if(!StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName()).equals(handlerClassName)) {
			return map;
		}

		Map<String, Object> mesg = null;
		try {
			shoot(map);
			mesg = APIGenerator.createResultAPI(true,"中奖了");
		}catch (MessageException me){
			mesg = APIGenerator.createResultAPI(me.getCode(),me.getMessage());
		}catch (ParamsException pm){
			mesg = APIGenerator.createResultAPI(2,pm.getMessage());
		}
		map.putAll(mesg);
		return map;
	}


	/**
	 * 用户踢球逻辑
	 * @param map
	 * @return
     */
	@Transactional(readOnly = false)
	public void shoot(Map<String,Object> map){
		//活动ID
		Long activityId = (Long)map.get(MarketConstant.ACTICITY_PARA);
		//当前用户
		Long accountId = map.get(MarketConstant.CUSTOMER_ACCOUNT_PARA)==null?null:(Long)map.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		//当前终端
		String opTerm = map.get(MarketConstant.CHANNEL_PARA)==null?null:(String)map.get(MarketConstant.CHANNEL_PARA);


		//查询用户之前的抽奖信息
		LotteryInfo lotteryInfo = null;
		//如果未登陆
		if(accountId == null){
			String beforeGoalResult = map.get("beforeGoalResult")==null?null:(String)map.get("beforeGoalResult");
			lotteryInfo = lotteryInfoByBeforeShootResult(beforeGoalResult);
		}//如果已登录
		else{
			lotteryInfo = lotteryInfo(activityId,accountId);
		}

		//根据抽奖信息进行抽奖
		Boolean goalResult = LotteryUtils.doLottery(probabilityUtils.calculateNextEventHappenProbability(lotteryInfo.getShootInfo().toArray(new Boolean[]{})));

		//如果已登录则更新抽奖相关信息
		if(accountId != null){
			LinkedList<Boolean> goalInfo = new LinkedList<>();
			goalInfo.add(goalResult);
			updateLotteryInfo(activityId, accountId, goalInfo, lotteryInfo, opTerm,true);

		}

		if(goalResult == false){
			throw new MessageException(2,"未中奖");
		}
	}

	@Transactional(readOnly = false)
	private void updateLotteryInfo(Long activityId, Long accountId, LinkedList<Boolean> shootInfo, LotteryInfo lotteryInfo, String opTerm, Boolean isLogin) {
		//如果踢球数刚好是一局，则使用新的机会，而不使用当此机会
		if(shootInfo.size() == SHOOT_TIMES){
			//如果没有剩余机会
			if(lotteryInfo.getResidualTimes() <= 0){
				//如果有还未结束的射门机会
				if(lotteryInfo.getShootInfo().size() > 0){
					throw new MessageException(3,"进球无效，<br/>您上次的点球大战尚未结束！");
				}else{ //如果所有机会都已结束
					verifyShootJiHui(lotteryInfo.getTotalTimes(),lotteryInfo.getTotalTimes(),isLogin);
				}
			}
		}else{
			//数据校验
			Integer over = getOver(lotteryInfo);

			//如果没有踢球机会了
			if(over == 0){
				verifyShootJiHui(lotteryInfo.getTotalTimes(),lotteryInfo.getTotalTimes()-lotteryInfo.getResidualTimes(),isLogin);
			}//如果剩余机会不够
			else if(over - shootInfo.size() < 0){
				throw new ParamsException("进球无效，<br/>您当次机会的点球大战剩余球数少于本次踢球数！");
			}
		}

		//1.保存射门记录
		updateShootRecord(activityId, accountId,shootInfo, opTerm);



		//2.更新欧洲杯射手榜
		updateEuropeanCupTopScorer(activityId, accountId, shootInfo);

		//3.更新已使用机会
		updateMarketingWheelLotteryTimes( activityId,accountId,shootInfo, lotteryInfo);

	}






	private void updateLotteryInfo(Long activityId, Long accountId, LinkedList<Boolean> shootInfo, String opTerm, Boolean isLogin) {
		LotteryInfo lotteryInfo = lotteryInfo(activityId, accountId);
		updateLotteryInfo( activityId,  accountId, shootInfo, lotteryInfo, opTerm, isLogin);
	}

	/**
	 * 更新进球记录
	 * @param accountId 账户ID
	 * @param activityId 活动ID
	 * @param opTerm 终端
	 * @param shootInfo 射门情况
	 */
	@Transactional(readOnly = false)
	public void updateShootRecord(Long activityId, Long accountId, LinkedList<Boolean> shootInfo, String opTerm){
		String status = (shootInfo.size() == SHOOT_TIMES?SHOOT_RECORD_STATUS_DID:SHOOT_RECORD_STATUS_DOING);

		//1.保存射门记录
		for(Boolean shootInfoItem : shootInfo){
			ShootRecord shootRecord = new ShootRecord();
			shootRecord.setActivityId(activityId);
			shootRecord.setAccountId(accountId);
			shootRecord.setIsGoal(shootInfoItem ?"1":"0");
			shootRecord.setOpDt(new Date());
			shootRecord.setOpTerm(opTerm);
			shootRecord.setStatus(status);
			shootRecordService.save(shootRecord);
		}

		//如果状态是进行中，则表示不是一次性更新一次机会的踢球信息，查询进行中的踢球次数是否为一次机会的次数，如果是则将踢球记录设置为结束
		if(SHOOT_RECORD_STATUS_DOING.equals(status)){
			List<ShootRecord> shootRecords =  shootRecordService.findList(new ShootRecord(activityId, accountId, new Date(), SHOOT_RECORD_STATUS_DOING));
			if(shootRecords != null && shootRecords.size() == SHOOT_TIMES){
				for(ShootRecord shootRecord : shootRecords){
					shootRecord.setStatus(SHOOT_RECORD_STATUS_DID);
					shootRecord.setOpDt(new Date());
					shootRecordService.save(shootRecord);
				}
			}
		}
	}

	/**
	 * 更新欧洲射手榜
	 */
	@Transactional(readOnly = false)
	public void updateEuropeanCupTopScorer(Long activityId, Long accountId, LinkedList<Boolean> goalInfo){
		//获得进球数
		Integer goalCount = BooleanUtils.getCount(goalInfo,true);

		//如果进球数大于0，则更新射手榜
		if(goalCount > 0){
			EuropeanCupTopScorer europeanCupTopScorer = europeanCupTopScorerService.findEuropeanCupTopScorerData(accountId,activityId);
			europeanCupTopScorer.setTotalGoals(europeanCupTopScorer.getTotalGoals()+goalCount);
			europeanCupTopScorerService.save(europeanCupTopScorer);
		}
	}

	/**
	 * 更新已使用机会
	 */
	@Transactional(readOnly = false)
	public void updateMarketingWheelLotteryTimes(Long activityId, Long accountId,  LinkedList<Boolean> goalInfo, LotteryInfo lotteryInfo){
		if(goalInfo.size() == SHOOT_TIMES || (goalInfo.size() > 0 && lotteryInfo.getShootInfo().size() == 0)){
			MarketingWheelLotteryTimes marketingWheelLotteryTimes = marketingWheelLotteryTimesService.getMarketingWheelLotteryTimes(accountId, activityId,1);
			if(marketingWheelLotteryTimes.getTotalTimes()-marketingWheelLotteryTimes.getUsedTimes()-1 >= 0){
				marketingWheelLotteryTimes.setUsedTimes(marketingWheelLotteryTimes.getUsedTimes()+1);
				marketingWheelLotteryTimesService.save(marketingWheelLotteryTimes);
			}
		}
	}


	/**
	 * 从数据库中查找抽奖信息
	 * @param activityId 活动ID
	 * @param accountId 账户ID
     * @return
     */
	private LotteryInfo lotteryInfo(Long activityId, Long accountId) {
		//射门信息
		LinkedList<Boolean> shootInfo = new LinkedList<Boolean>();
		//剩余机会次数
		Integer residualTimes = null;
		// 总机会次数
		Integer totalTimes = null;

		//剩余机会次数
		MarketingWheelLotteryTimes marketingWheelLotteryTimes = marketingWheelLotteryTimesService.getMarketingWheelLotteryTimes(accountId, activityId,1);
		residualTimes = marketingWheelLotteryTimes.getTotalTimes()-marketingWheelLotteryTimes.getUsedTimes();
		totalTimes = marketingWheelLotteryTimes.getTotalTimes();

		//查询射门记录
		List<ShootRecord> shootRecords =  shootRecordService.findList(new ShootRecord(activityId, accountId, new Date(), SHOOT_RECORD_STATUS_DOING));
		int shootRecordsSize = shootRecords.size();

		if((shootRecordsSize > 0 && residualTimes >= 0) || (shootRecordsSize==0 && residualTimes > 0)){
			//构造是否进球纪录
			for(int i = 0; i < shootRecordsSize; i++){
				shootInfo.add("1".equals(shootRecords.get(i).getIsGoal()));
			}
		}

		LotteryInfo lotteryInfo = new LotteryInfo(shootInfo, totalTimes,residualTimes);



		return lotteryInfo;
	}

	/**
	 * 返回lotteryInfo的api形式
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> lotteryInfoWithAPI(Long activityId, Long accountId){
		LotteryInfo lotteryInfo = lotteryInfo(activityId,accountId);

		Integer over = getOver(lotteryInfo);

		Integer count = lotteryInfo.getResidualTimes();
		if(over == SHOOT_TIMES){
			count = count-1;
		}

		if(over == 0){
			LinkedList<Boolean> shootInfo = new LinkedList<Boolean>();
			List<ShootRecord> beforeTimesShootRecords =  shootRecordService.findLastTimesList(new ShootRecord(activityId, accountId, new Date(), SHOOT_RECORD_STATUS_DID));
			//构造是否进球纪录
			Integer beginIndex = null;
			if(beforeTimesShootRecords.size() < SHOOT_TIMES){
				beginIndex = 0;
			}else {
				beginIndex = beforeTimesShootRecords.size()-SHOOT_TIMES;
			}
			for(int i = beginIndex; i < beforeTimesShootRecords.size(); i++){
				shootInfo.add("1".equals(beforeTimesShootRecords.get(i).getIsGoal()));
			}
			lotteryInfo.setShootInfo(shootInfo);
		}

		StringBuffer goalInfo = new StringBuffer("");
		for(Boolean goalInfoItem : lotteryInfo.getShootInfo()){
			goalInfo.insert(0,goalInfoItem?"1":"0");
		}
		goalInfo.insert(0,"0");
		//补全
		//WStringUtils.complement(goalInfo,SHOOT_TIMES,'0');





		return new APIObjectNode().putNodeWithObject("over",over)
				.putNodeWithObject("count",count)
				.putNodeWithObject("used",Integer.valueOf(goalInfo.toString(),2));
	}

	public Integer getOver(LotteryInfo lotteryInfo){
		Integer over = null;
		if(lotteryInfo.getResidualTimes() <= 0 && lotteryInfo.getShootInfo().size() == 0){
			over = 0;
		}else{
			over = SHOOT_TIMES - lotteryInfo.getShootInfo().size();
		}
		return over;
	}

	/**
	 * 根据踢球情况构造LotteryInfo
	 * @param beforeGoalResult
	 * @return
     */
	private LotteryInfo lotteryInfoByBeforeShootResult(String beforeGoalResult) {
		LinkedList<Boolean> bootInfoOnBefore = BooleanUtils.toList(beforeGoalResult);
		if(bootInfoOnBefore.size() > SHOOT_TIMES){
			throw new ParamsException("一局中最多只能踢"+SHOOT_TIMES+"次");
		}
		return new LotteryInfo(bootInfoOnBefore, 1,0);
	}





	/**
	 * 校验射门机会
	 * @param totalTimes
	 * @param usedTimes
	 */
	@Transactional(readOnly = false)
	public void verifyShootJiHui(Integer totalTimes, Integer usedTimes,Boolean isLogin){
		if(totalTimes - usedTimes <= 0){
			if(totalTimes == 2){
				throw new MessageException(5,"每天最多有2次参与机会<br/>您今天已经参与过啦！");
			}else{
				if(isLogin){
					throw new MessageException(6,"约上好友来撒欢<br/>可多一次参与机会");
				}else {
					throw new MessageException(7,"本轮进球无效~~~<br/>您今天已经参与过啦！<br/>叫上朋友一起来，<br/>可多一次参与机会");
				}

			}
		}
	}



}
