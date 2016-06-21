package com.thinkgem.jeesite.modules.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.entity.MessageCreateRule;
import com.thinkgem.jeesite.modules.message.service.MessageCreateRuleService;

public class MessageUtils {
	private static MessageCreateRuleService messageCreateRuleService = SpringContextHolder.getBean(MessageCreateRuleService.class);
	
	public static final String MESSAGE_CACHE = "messageCache";
	public static final String MESSAGE_CACHE_KEY = "messageCacheKey";
	
	/**
	 * 添加消息产生规则
	 * @param messageCreateRule
	 */
	public static void add(MessageCreateRule messageCreateRule) {
		if(messageCreateRule != null) {
			Map<String,MessageCreateRule> map = getMap();
			map.put(messageCreateRule.getId(), messageCreateRule);
			CacheUtils.put(MESSAGE_CACHE, MESSAGE_CACHE_KEY, map);
		}
	}
	
	/**
	 * 移除消息产生规则
	 * @param messageCreateRule
	 */
	public static void remove(MessageCreateRule messageCreateRule) {
		Map<String,MessageCreateRule> map = getMap();
		if(messageCreateRule != null && map.keySet().contains(messageCreateRule.getId())) {
			map.remove(messageCreateRule.getId());
		}
		CacheUtils.put(MESSAGE_CACHE, MESSAGE_CACHE_KEY, map);
	}
	
	/**
	 * 获取消息产生规则
	 * @param id
	 * @return
	 */
	public static MessageCreateRule get(String id) {
		Map<String,MessageCreateRule> map = getMap();
		return map.get(id);
	}
	
	/**
	 * 检查cache中的消息产生规则在此时间是否有效，若无效在cache中移除
	 */
	public static void check() {
		Map<String,MessageCreateRule> map = getMap();
		Iterator<Map.Entry<String,MessageCreateRule>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String,MessageCreateRule> entry = it.next();
			MessageCreateRule messageCreateRule = messageCreateRuleService.get(entry.getKey());
			//检查时间和状态
			if(messageCreateRule == null || !MessageConstant.MESSAGE_RULE_STATUS_REVIEW_PASS.equals(messageCreateRule.getStatus()) || !check(messageCreateRule)) {
				it.remove();
			}
		}
		CacheUtils.put(MESSAGE_CACHE, MESSAGE_CACHE_KEY, map);
	}

	/**
	 * 检查消息产生规则在此时间是否有效
	 * @param messageCreateRule
	 * @return
	 */
	public static boolean check(MessageCreateRule messageCreateRule) {
		return (new Date().getTime() >= messageCreateRule.getStartDate().getTime()) && (new Date().getTime() <= messageCreateRule.getEndDate().getTime())
				&& (DateUtils.getTime().compareTo(messageCreateRule.getStartTime()) >= 0) && (DateUtils.getTime().compareTo(messageCreateRule.getEndTime()) <= 0);
	}
	
	/**
	 * 更新cache中的消息产生规则，若cache中原本无此消息产生规则 则不做操作
	 * @param messageCreateRule
	 */
	public static void update(MessageCreateRule messageCreateRule) {
		Map<String,MessageCreateRule> map = getMap();
		if(map.keySet().contains(messageCreateRule.getId())) {
			map.put(messageCreateRule.getId(), messageCreateRule);
		}
		CacheUtils.put(MESSAGE_CACHE, MESSAGE_CACHE_KEY, map);
	}
	
	/**
	 * 获取消息产生规则map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,MessageCreateRule> getMap() {
		Map<String,MessageCreateRule> map = (Map<String,MessageCreateRule>)CacheUtils.get(MESSAGE_CACHE, MESSAGE_CACHE_KEY);
		if(map == null) {
			map = new HashMap<String,MessageCreateRule>();
			CacheUtils.put(MESSAGE_CACHE, MESSAGE_CACHE_KEY, map);
		}
		return map;
	}
	
	/**
	 * 获取消息产生规则list
	 * @return
	 */
	public static List<MessageCreateRule> getList() {
		Map<String,MessageCreateRule> map = getMap();
		List<MessageCreateRule> list = new ArrayList<MessageCreateRule>(map.values());
		return list;
	}
}
