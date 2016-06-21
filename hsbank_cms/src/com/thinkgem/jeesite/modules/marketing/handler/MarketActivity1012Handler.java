package com.thinkgem.jeesite.modules.marketing.handler;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.MarketingMoneyAwardRecord;
import com.thinkgem.jeesite.modules.entity.marketing.MarketingMoneyAwardRecordParameters;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingMoneyAwardRecordDao;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;

/**
 * @author wdr 
 * 
 *	 4月春游季
 *
 */
@Component("marketActivity1012Handler")
public class MarketActivity1012Handler  extends MarketActivityBaseSupportHandler {
	@Autowired
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	
	@Autowired
	private MarketingMoneyAwardRecordDao marketingMoneyAwardRecordDao;
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	@Autowired
	private MarketingActivityInfoService marketingActivityInfoService;
	
	public static HashMap<Integer,Map<Object,Object>> amountAward = null;
	public static Integer[] keys = null;
	public static HashMap<Integer,Integer>  amountMapping =  null;
	static{
		amountAward = new HashMap<Integer,Map<Object,Object>>();
		amountAward.put(1000, toMap(new Object[][]{{"count",2000},{"type",MarketConstant.RECEIVE_TYPE_PEANUT_BEAN}}));
		amountAward.put(5000, toMap(new Object[][]{{"count",10000},{"type",MarketConstant.RECEIVE_TYPE_PEANUT_BEAN}}));
		
		amountAward.put(10000, toMap(new Object[][]{{"count",10d},{"type",MarketConstant.RECEIVE_TYPE_MONEY}}));
		amountAward.put(20000, toMap(new Object[][]{{"count",18d},{"type",MarketConstant.RECEIVE_TYPE_MONEY}}));
		
		amountAward.put(50000, toMap(new Object[][]{{"count",28d},{"type",MarketConstant.RECEIVE_TYPE_MONEY}}));
		amountAward.put(100000, toMap(new Object[][]{{"count",88d},{"type",MarketConstant.RECEIVE_TYPE_MONEY}}));
		amountAward.put(200000, toMap(new Object[][]{{"count",168d},{"type",MarketConstant.RECEIVE_TYPE_MONEY}}));
		amountAward.put(500000, toMap(new Object[][]{{"count",588d},{"type",MarketConstant.RECEIVE_TYPE_MONEY}}));
		amountAward.put(1000000, toMap(new Object[][]{{"count",988d},{"type",MarketConstant.RECEIVE_TYPE_MONEY}}));
		amountAward.put(2000000, toMap(new Object[][]{{"count",2000d},{"type",MarketConstant.RECEIVE_TYPE_MONEY}}));
		
		
		
		
		
		keys = new Integer[]{1,2,4,8,16,32,64,128,256,512};
		amountMapping = new HashMap<Integer,Integer>();
		amountMapping.put(keys[0], 1000);
		amountMapping.put(keys[1], 5000);
		amountMapping.put(keys[2], 10000);
		amountMapping.put(keys[3], 20000);
		amountMapping.put(keys[4], 50000);
		amountMapping.put(keys[5], 100000);
		amountMapping.put(keys[6], 200000);
		amountMapping.put(keys[7], 500000);
		amountMapping.put(keys[8], 1000000);
		amountMapping.put(keys[9], 2000000);
	}
	
	@Override
	public Map<String, Object> luckDraw(Map<String, Object> para) {
		
		if(MarketConstant.HANDLER_PARA_VAL_APRIL_SPRING_OUTING.equals(para.get(MarketConstant.HANDLER_PARA)) ){
			para.put("result", "领取失败！");
			para.put("resultCode", "3");
			para.put("isSuccess", true);
			
			long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
			//CustomerBase customerBase = customerBaseDao.getByAccountId(accountId);
			MarketingActivityInfo marketingActivityInfo =  marketingActivityInfoService.getByBizClassName("marketActivity1012Handler");
			
			//获得用户活动期间投资总额（不包括活花生、新花生）
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("accountId", accountId);
			params.put("beginDataDt", marketingActivityInfo.getBeginDate());
			params.put("endDataDt", marketingActivityInfo.getEndDate());
			params.put("isNewUser", 1);
			params.put("status", "0,3");
			Double sumAmount = projectInvestmentRecordDao.sumAmount(params);
			Integer receiveAwardCode = MarketActivity1012Handler.amountMapping.get(Integer.parseInt(para.get(MarketConstant.FUNC_PARA).toString()));
			
			
			
			//Integer receiveAwardCode = (Integer)para.get();
			
			//如果活动期内用户投资额大于领奖所需金额
			if(sumAmount>= receiveAwardCode){
				Map<Object,Object> receiveAwardInfo = MarketActivity1012Handler.amountAward.get(receiveAwardCode);
				if(receiveAwardInfo != null){
					Object countObj = receiveAwardInfo.get("count");
					
					String type = (String)receiveAwardInfo.get("type");
					
					String remark = "累计投资满"+receiveAwardCode+"元送";
					
					HashMap<String,Object> params2 = new HashMap<String,Object>();
					params2.put("accountId", accountId);
					params2.put("activityId", (long)para.get(MarketConstant.ACTICITY_PARA));
					params2.put("awardValue", countObj);
					Integer getCount = null;
					String message = "";
					if(MarketConstant.RECEIVE_TYPE_MONEY.equals(type)){//送现金
						Double count = (Double)countObj;
						remark += "现金";
						
						DecimalFormat df=new DecimalFormat("#.##"); 
						message += (df.format(count)+"元旅行经费 ");
						params2.put("awardType", ProjectConstant.MARKETING_AWARD_TYPE_MONEY);
						getCount = marketingActivityAwardRecordDao.getCountByAccountIdAndActivityId(params2);
						if(getCount == 0){ 
							offlineGiveMoney(accountId, count, remark, null, ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF,
									MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER, (String)para.get(MarketConstant.CHANNEL_PARA), (long)para.get(MarketConstant.ACTICITY_PARA));
							
							super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, ProjectConstant.MARKETING_AWARD_TYPE_MONEY,
									count + "", remark);
						}
						
					}else if(MarketConstant.RECEIVE_TYPE_PEANUT_BEAN.equals(type)){//送花生豆
						Integer count = (Integer)countObj;
						remark += "花生豆";
						message += (count+"花生豆 ");
						params2.put("awardType", ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL);
						getCount = marketingActivityAwardRecordDao.getCountByAccountIdAndActivityId(params2);
						if(getCount == 0){
							logger.info("do give integral start. accountId:" + accountId + ", number:" + count);
							customerIntegralSnapshotHandler.changeIntegralValue(accountId, count, ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, remark, (String)para.get(MarketConstant.CHANNEL_PARA));
							logger.info("do give integral end.");
							super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW, ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL,
									count + "", remark);
						}
					}
					
					if(getCount > 0){
						para.put("result", "您已领取过！");
						para.put("resultCode", "1");
					}
					else {
						para.put("result", "去旅行，成为更好的自己！恭喜您已领取" +message);
						para.put("resultCode", "2");
					}
				}
			}
			else{
				para.put("result", "您当前累计投资额度暂未达标，先去投资，过会再来领吧！");
				para.put("resultCode", "0");
			}
			
			
		}
		
		return super.luckDraw(para);
	}
	
	public static Map<Object,Object> toMap(Object[][] arrayArray){
		HashMap<Object,Object> result = new HashMap<Object,Object>();
		for(Object[] arrary : arrayArray){
			result.put(arrary[0], arrary[1]);
		}
		return result;
	}
	
	/**
	 * 将现金奖励插入到现金奖励记录中，随后由task完成现金赠送动作
	 * @param accountId
	 * @param amount
	 * @param remark
	 * @param causeId
	 * @param causeType
	 * @param behaviorCode
	 * @param channelId
	 * @param marketingActivityId
	 */
	private void offlineGiveMoney(long accountId, double amount, String remark, Long causeId, String causeType,
			String behaviorCode, String channelId, long marketingActivityId) {
		MarketingMoneyAwardRecordParameters marketingMoneyAwardRecordParameters = new MarketingMoneyAwardRecordParameters();
		marketingMoneyAwardRecordParameters.setAwardCauseId(causeId);
		marketingMoneyAwardRecordParameters.setAwardCauseType(causeType);
		marketingMoneyAwardRecordParameters.setAwardReason(remark);
		marketingMoneyAwardRecordParameters.setBehaviorCode(behaviorCode);
		marketingMoneyAwardRecordParameters.setChannelId(channelId);
		marketingMoneyAwardRecordParameters.setMarketingActivityId(marketingActivityId);
		
		MarketingMoneyAwardRecord marketingMoneyAwardRecord = new MarketingMoneyAwardRecord();
		marketingMoneyAwardRecord.setAccountId(accountId);
		marketingMoneyAwardRecord.setAmount(amount);
		marketingMoneyAwardRecord.setStatus(MarketConstant.MARKETING_MONEY_AWARD_STATUS_GIVING);
		marketingMoneyAwardRecord.setCreateDt(new Date());
		marketingMoneyAwardRecord.setParameters(JsonMapper.toJsonString(marketingMoneyAwardRecordParameters));
		marketingMoneyAwardRecordDao.insert(marketingMoneyAwardRecord);
	}
	
	
	public Map<String,Object> activityInfo(Long accountId){
		MarketingActivityInfo marketingActivityInfo =  marketingActivityInfoService.getByBizClassName("marketActivity1012Handler");
		Map<String,Object> data = new HashMap<String,Object>();
		
		Integer used = 0;
		Integer over = 0;
		Integer countBuf = 0;
		
		//获得用户活动期间投资总额（不包括活花生、新花生）
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("accountId", accountId);
		params.put("beginDataDt", marketingActivityInfo.getBeginDate());
		params.put("endDataDt", marketingActivityInfo.getEndDate());
		params.put("isNewUser", 1);
		params.put("status", "0,3");
		Double sumAmount = projectInvestmentRecordDao.sumAmount(params);
		Double receiveAmount = 0d;
		for(Integer key :MarketActivity1012Handler.keys){
			Integer amount = MarketActivity1012Handler.amountMapping.get(key);
			Integer getCount = null;
			if(sumAmount>=amount){
				Map<Object,Object> receiveAwardInfo = MarketActivity1012Handler.amountAward.get(amount);
				Object countObj = receiveAwardInfo.get("count");
				String type = (String)receiveAwardInfo.get("type");
				
				HashMap<String,Object> params2 = new HashMap<String,Object>();
				params2.put("accountId", accountId);
				params2.put("activityId", marketingActivityInfo.getActicityId());
				params2.put("awardValue", countObj);
				if(MarketConstant.RECEIVE_TYPE_MONEY.equals(type)){//送现金
					params2.put("awardType", ProjectConstant.MARKETING_AWARD_TYPE_MONEY);
				}else if(MarketConstant.RECEIVE_TYPE_PEANUT_BEAN.equals(type)){//送花生豆
					params2.put("awardType", ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL);
				}
				getCount = marketingActivityAwardRecordDao.getCountByAccountIdAndActivityId(params2);
				countBuf += key;
				if(getCount > 0){
					used += key;
					
					if(MarketConstant.RECEIVE_TYPE_MONEY.equals(type)){
						Double count = (Double)countObj;
						receiveAmount += count;
					}
				}
				else{
					over += key;
				}
			}
		}
		data.put("count", countBuf);
		data.put("used", used);
		data.put("over", over);
		data.put("investAmount", sumAmount);
		data.put("receiveAmount", receiveAmount);
		
		
		return data;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
