/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * @author yangtao
 * 
 *	投资送花生豆（长期活动）：
 *		1：投资放款，送相应投资额（取整）颗花生豆
 *
 */
@Component("marketActivity1003Handler")
public class MarketActivity1003Handler extends MarketActivityBaseSupportHandler{
	Logger logger = Logger.getLogger(this.getClass());
	
	
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	
	/**
	 * 投资完成
	 * 		1：送相应投资额（取整）颗花生豆
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> investmentTenderOver(Map<String,Object> para) {
		logger.info("=====market activity 1003 investmentTenderOver, 投资送花生豆 start=====");
		//调用super.investmentTenderOver
		logger.info("super investmentTenderOver method start");
		super.investmentTenderOver(para);
		logger.info("super investmentTenderOver method end");
		//送用户花生豆
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		int integralNumber = ((Double)para.get(MarketConstant.AMOUNT_PARA)).intValue();
		String remark = MarketConstant.ACTIVITY_1003_INVESTMENTTENDEROVER_GIVE_INTEGRAL_REMARK;
		logger.info("do give integral start. accountId:" + accountId + ", number:" + integralNumber);
		customerIntegralSnapshotHandler.changeIntegralValue(accountId, integralNumber, ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_ACTIVITY, remark, (String)para.get(MarketConstant.CHANNEL_PARA));
		logger.info("do give integral end.");
		//插入marketingActivityAwardRecord赠送记录
		logger.info("insert marketingActivityAwardRecord start.");
		super.insertRecordInMarketingActivityAwardRecord(para, MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER, ProjectConstant.MARKETING_AWARD_TYPE_INTEGRAL,
				integralNumber + "", remark);
		logger.info("insert marketingActivityAwardRecord end.");
		logger.info("=====market activity 1003 investmentTenderOver, 投资送花生豆 end=====");
		return para;
	}
}
