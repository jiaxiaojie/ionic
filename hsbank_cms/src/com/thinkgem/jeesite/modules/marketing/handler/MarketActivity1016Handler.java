/**
 * 
 */
package com.thinkgem.jeesite.modules.marketing.handler;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.customer.dao.CustomerIntegralSnapshotDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerIntegralSnapshotHandler;
import com.thinkgem.jeesite.modules.entity.CustomerIntegralSnapshot;
import com.thinkgem.jeesite.modules.entity.Gamble;
import com.thinkgem.jeesite.modules.marketing.MarketConstant;
import com.thinkgem.jeesite.modules.marketing.dao.GambleDao;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * @author hyc
 * 竞猜奖：竞猜每日团队胜负，比较当日团队累计投资金额，每用户每日仅可猜1次，每次耗费100花生豆，猜对奖励1000花生豆，胜负次日公布
 */
@Component("marketActivity1016Handler")
public class MarketActivity1016Handler extends MarketActivityBaseSupportHandler{
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private GambleDao gambleDao;
	@Autowired
	private CustomerIntegralSnapshotHandler customerIntegralSnapshotHandler;
	@Autowired
	private CustomerIntegralSnapshotDao customerIntegralSnapshotDao;
	
	/**
	 * 1.参加竞猜此为查看活动是否有效
	 * 2.检查单日是否已参加竞猜
	 * 3.添加竞猜记录
	 */
	@Override
	@Transactional(readOnly = false)
	public Map<String, Object> luckDraw(Map<String,Object> para) {
		if(!StringUtils.lowerCaseFirstLetter(this.getClass().getSimpleName()).equals(para.get(MarketConstant.HANDLER_PARA))) {
			return para;
		}
		long accountId = (long)para.get(MarketConstant.CUSTOMER_ACCOUNT_PARA);
		Long activityId = (long)para.get(MarketConstant.ACTICITY_PARA);
		String opTerm = (String)para.get(MarketConstant.CHANNEL_PARA);
		String choiceSide = (String)para.get("side");
		if(!(MarketConstant.MATCH_SIDE_RED).equals(choiceSide) && !(MarketConstant.MATCH_SIDE_BLUE).equals(choiceSide)){
			para.put("isSuccess", false);
			para.put("message", "请选择正确的队伍");
			return para;
		}
		
		int integralNumber = MarketConstant.MATCH_GUESSINGS_1016_POST_INTEGRAL_100;
		String remark = MarketConstant.MATCH_GUESSINGS_1016_POST_INTEGRAL_REMARK;
		
		/*
		 * 若活动有效则扣除用户100花生豆
		 * 检查花生豆是否充足
		 */
		CustomerIntegralSnapshot customerIntegralSnapshot = customerIntegralSnapshotDao.getByAccountId(accountId);
		Integer integralBalance = customerIntegralSnapshot.getIntegralBalance();
		if(integralBalance.intValue() < 100){
			para.put("isSuccess", false);
			para.put("message", "您的花生豆数量不足<br/>目前无法参与竞猜<br/>去投资赚更多花生豆吧！");
			return para;
		}
		customerIntegralSnapshotHandler.changeIntegralValue(accountId, integralNumber, ProjectConstant.CUSTOMER_INTEGRAL_CHANGE_TYPE_EXPENSE, remark, opTerm);
		Gamble gamble=new Gamble();
		gamble.setAccountId(accountId);
		gamble.setActivityId(activityId);
		gamble.setChoiceSide(choiceSide);
		gamble.setOpDt(new Date());
		gamble.setOpTerm(opTerm);
		gambleDao.insert(gamble);
		
		//查询用户当日是否参与了竞猜
		List<Gamble> gambleList = gambleDao.findListByActivityIdAndAccountId(activityId, accountId, new Date());
		if(gambleList.size() > 1){
			throw new ServiceException("系统异常，请稍后重试");
		}
		para.put("isSuccess", true);
		para.put("prizeType", 1);
		para.put("prizeTypeName", "花生豆");
		para.put("prize", MarketConstant.MATCH_GUESSINGS_1016_POST_INTEGRAL_100);
		return para;
	}

}
