/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.marketing.handler.MarketActivityBaseSupportHandler;

/**
 * @author yangtao
 *
 */
@Service
public class MarketFacadeService {

	Logger logger = Logger.getLogger(this.getClass());
	

	/**
	 * 登录入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> login(Map<String, Object> para) {
		logger.info("market login begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_LOGIN;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.login(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market login end ...");
		return para;
	}

	/**
	 * 注册入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> register(Map<String, Object> para) {
		logger.info("market register begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_REGISTER;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA, activityInfo.getActicityId());
					baseSupportHandler.register(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market register end ...");
		return para;
	}

	/**
	 * 签到入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> sign(Map<String, Object> para) {
		logger.info("market sign begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_SIGN;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler =SpringContextHolder. getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.sign(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market sign end ...");
		return para;
	}

	/**
	 * 邀请入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> invite(Map<String, Object> para) {
		logger.info("market invite begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_INVITE;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.invite(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market invite end ...");
		return para;
	}

	/**
	 * 充值入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> recharge(Map<String, Object> para) {
		logger.info("market recharge begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_RECHARGE;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.recharge(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market recharge end ...");
		return para;
	}

	/**
	 * 开通第三方账号入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> openThirdParty(Map<String, Object> para) {
		logger.info("market openThirdParty begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_OPEN_THIRD_PARTY;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.openThirdParty(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market openThirdParty end ...");
		return para;
	}

	/**
	 * 绑定银行卡入口
	 * 
	 * @param para
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> bindBankCard(Map<String, Object> para) {
		logger.info("market bindBankCard begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_BIND_BANK_CARD;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler =SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.bindBankCard(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market bindBankCard end ...");
		return para;
	}

	/**
	 * 提现入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> withDraw(Map<String, Object> para) {
		logger.info("market withDraw begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_WITHDRAW;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.withDraw(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market withDraw end ...");
		return para;
	}

	/**
	 * 投标入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> investmentTender(Map<String, Object> para) {
		logger.info("market investmentTender begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.investmentTender(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market investmentTender end ...");
		return para;
	}

	/**
	 * 债权投资
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> investmentCreditAssgnment(
			Map<String, Object> para) {
		logger.info("market investmentCreditAssgnment begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_CREDIT_ASSIGNMENT;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.investmentCreditAssgnment(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market investmentCreditAssgnment end ...");
		return para;
	}

	/**
	 * 融资入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> loanMoney(Map<String, Object> para) {
		logger.info("market loanMoney begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_LOAN_MONRY;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.loanMoney(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market loanMoney end ...");
		return para;
	}

	/**
	 * 抽奖入口
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> luckDraw(Map<String, Object> para) {
		logger.info("market luckDraw begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_LUCK_DRAW;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler =SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.luckDraw(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market luckDraw end ...");
		return para;
	}

	/**
	 * 投标完成
	 * 
	 * @param para
	 * @return
	 */
	public Map<String, Object> investmentTenderOver(Map<String, Object> para) {
		logger.info("market investmentTenderOver begin ...");
		List<MarketingActivityInfo> activityInfoList = MarketingUtils.getList();
		String channelId = (String) para.get(MarketConstant.CHANNEL_PARA);
		String behaviorCode = MarketConstant.CUSTOMER_BEHAVIOR_INVESTMENT_TENDER_OVER;
		for (MarketingActivityInfo activityInfo : activityInfoList) {
			if (MarketingUtils.check(activityInfo)) {
				if (activityInfo.checkIsUse(behaviorCode, channelId)) {
					MarketActivityBaseSupportHandler baseSupportHandler = SpringContextHolder.getBean(activityInfo
							.getBizClassName());
					para.put(MarketConstant.ACTICITY_PARA,
							activityInfo.getActicityId());
					baseSupportHandler.investmentTenderOver(para);
				}
			} else {
				MarketingUtils.remove(activityInfo);
			}
		}
		logger.info("market investmentTenderOver end ...");
		return para;
	}
}
