/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author ydt
 * 
 *大转盘抽奖
 *
 */
@Component("marketActivity1005Handler")
public class MarketActivity1005Handler extends MarketActivityBaseSupportHandler{

	
	
	/**
	 * 抽奖
	 */
	@Override
	@Deprecated
	@Transactional(readOnly = false)
	public Map<String, Object> luckDraw(Map<String, Object> para) {
		logger.info("=====market activity 1005 luckDraw start=====");
		/*MarketingWheelPrizeInstance marketingWheelPrizeInstance = MarketingWheelUtils.poll(String.valueOf(para.get(MarketConstant.CHANNEL_PARA)));
		MarketingWheelPrizeInfo marketingWheelPrizeInfo = marketingWheelPrizeInfoService.get(marketingWheelPrizeInstance.getPrizeId() + "");
		logger.info("poll prize instance id:" + marketingWheelPrizeInstance.getId() + ", prize id:" + marketingWheelPrizeInfo.getId());
		para.put("rotate", marketingWheelPrizeInfo.getRotate());
		para.put("result", marketingWheelPrizeInfo.getGetTips());
		para.put("token", marketingWheelPrizeInstance.getToken());
		para.put("imgUrl", marketingWheelPrizeInfo.getLogo());*/
		logger.info("=====market activity 1005 luckDraw end=====");
		return para;
	}
}
