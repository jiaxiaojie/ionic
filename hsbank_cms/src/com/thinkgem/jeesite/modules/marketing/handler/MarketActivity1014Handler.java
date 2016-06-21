/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author ydt
 * 
 * 四月邀请好友
 * 活动期间内好友注册并且单次投资满1000元，赠送推荐人现金奖励，每邀请1位好友奖10元现金，第5位及第5的倍数位奖50元现金
 *
 */
@Component
public class MarketActivity1014Handler extends MarketActivityBaseSupportHandler{
	
	@Override
	public Map<String, Object> investmentTender(Map<String, Object> para) {
		return para;
	}
}
