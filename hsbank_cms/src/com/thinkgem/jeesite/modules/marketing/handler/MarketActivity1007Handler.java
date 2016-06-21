/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author ydt
 * 
 * 双旦活动（圣诞+元旦）
 * 活动期间内好友注册并且单次投资满1000元，赠送推荐人现金奖励，奖励额度如下：
 * 第一位，奖励10元；
 * 第二位，奖励20元；
 * 第三位，奖励30元；
 * 第四位，奖励40元；
 * 第五位，奖励50元；
 * 超过五位的，每位奖励10元。
 *
 */
@Component("marketActivity1007Handler")
public class MarketActivity1007Handler extends MarketActivityBaseSupportHandler{
	
	@Override
	public Map<String, Object> investmentTender(Map<String, Object> para) {
		return para;
	}
}
