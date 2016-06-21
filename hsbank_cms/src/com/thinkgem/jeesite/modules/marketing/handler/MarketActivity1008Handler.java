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
 *	一月活动：投资排行三重礼
 *		1.在活动期间内完成一笔投资，即送5元现金券（投标后即，只送1次）；
 *		2.累计投资满X万  送XX元 京东E卡；									月末客服执行赠送
 *		3.累计投资额（年化）最高的前3名用户，可获得终极大奖。					月末客服执行赠送
 *
 */
@Component
public class MarketActivity1008Handler extends MarketActivityBaseSupportHandler{
	
	/**
	 * 投标入口
	 * 		1：活动期间内首次投资送5元现金券
	 */
	@Override
	@Transactional(readOnly=false)
	public Map<String,Object> investmentTender(Map<String,Object> para) {
		return para;
	}
}
