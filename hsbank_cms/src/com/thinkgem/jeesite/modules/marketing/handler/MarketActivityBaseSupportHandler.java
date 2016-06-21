/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * @author yangtao
 *
 */
@Component(value ="marketActivityBaseSupportHandler")
public abstract class MarketActivityBaseSupportHandler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MarketActivityHandler marketActivityHandler;
	@Autowired
	private MarketingActivityAwardRecordHandler marketingActivityAwardRecordHandler;
	
	/**
	 * 校验用户是否满足条件
	 * @return
	 */
	public boolean checkCustomer(){
		return true;
	}
	/**
	 * 校验对应产品是否满足条件
	 * @return
	 */
	public boolean checkProduct(){
		return true;
	}
	/**
	 * 校验时间是否满足条件
	 * @return
	 */
	public boolean checkDate(){
		return true;
	}
	/**
	 * 校验渠道是否满足条件
	 * @return
	 */
	public boolean checkChannel(){
		return true;
	}
	
	/**
	 * 登录入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> login(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 注册入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> register(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 签到入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> sign(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 邀请入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> invite(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 充值入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> recharge(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 开通第三方账号入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> openThirdParty(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 绑定银行卡入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> bindBankCard(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 提现入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> withDraw(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 投标入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> investmentTender(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 债权投资
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> investmentCreditAssgnment(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 融资入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> loanMoney(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 抽奖入口
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> luckDraw(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 投标完成
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> investmentTenderOver(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 竞猜
	 * @param para
	 * @return
	 */
	@Transactional(readOnly=false)
	public Map<String,Object> guessings(Map<String,Object> para){
		addMarketingActivityOpHis(para, "true");
		return para;
	}
	
	/**
	 * 增加，营销活动操作流水
	 * @param para
	 */
	public void addMarketingActivityOpHis(Map<String,Object> para, String outPara){
		Long acticityId = (Long)para.get(MarketConstant.ACTICITY_PARA);
		String behaviorCode  = String.valueOf(para.get(MarketConstant.BEHAVIOR_PARA));
		Long accountId = (Long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		String channelId = String.valueOf(para.get(MarketConstant.CHANNEL_PARA));
		String inPara = para.toString();
		String resultCode = MarketConstant.MARKETING_ACTIVITY_RESULT_CODE_SUCCESS;
		marketActivityHandler.addMarketingActivityOpHis(
				acticityId, behaviorCode,
				accountId, channelId, inPara, outPara, resultCode);
	}
	
	/**
	 * 添加营销活动奖励记录流水--针对因自身行为发生的奖励
	 * @param para
	 * @param behaviorCode
	 * @param awardType
	 * @param awardValue
	 * @param awardReason
	 */
	public void insertRecordInMarketingActivityAwardRecord(Map<String, Object> para, String behaviorCode,
			String awardType, String awardValue, String awardReason) {
		marketingActivityAwardRecordHandler.insertRecordWhenGiveAward((long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA),
				(long)para.get(MarketConstant.ACTICITY_PARA), behaviorCode, (String)para.get(MarketConstant.CHANNEL_PARA),
				awardType, awardValue, awardReason, ProjectConstant.MARKET_AWARD_CAUSE_TYPE_SELF, null);
	}
	
	/**
	 * 添加营销活动奖励记录流水--针对用户领取的奖励
	 * @param para
	 * @param behaviorCode
	 * @param awardType
	 * @param awardValue
	 * @param awardReason
	 */
	public void insertRecordInMarketingInGetActivityAwardRecord(Map<String, Object> para, String behaviorCode,
			String awardType, String awardValue, String awardReason) {
		marketingActivityAwardRecordHandler.insertRecordWhenGiveAward((long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA),
				(long)para.get(MarketConstant.ACTICITY_PARA), behaviorCode, (String)para.get(MarketConstant.CHANNEL_PARA),
				awardType, awardValue, awardReason, ProjectConstant.MARKET_AWARD_CAUSE_TYPE_FRIEND_GET, null);
	}
	
	/**
	 * 添加营销活动奖励记录流水--针对因被推荐人的行为而奖励推荐人
	 * @param para
	 * @param behaviorCode
	 * @param awardType
	 * @param awardValue
	 * @param awardReason
	 * @param accountId
	 */
	public void insertRecommenderRecordInMarketingActivityAwardRecord(Map<String, Object> para, long recommenderAccountId, String behaviorCode,
			String awardType, String awardValue, String awardReason , String causeType, long causeId) {
		marketingActivityAwardRecordHandler.insertRecordWhenGiveAward(recommenderAccountId,
				(long)para.get(MarketConstant.ACTICITY_PARA), behaviorCode, (String)para.get(MarketConstant.CHANNEL_PARA),
				awardType, awardValue, awardReason, causeType, causeId);
	}
	
}
