package com.thinkgem.jeesite.modules.current.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectRedemptionApplyService;
import com.thinkgem.jeesite.modules.entity.CurrentProjectRedemptionApply;

/**
 * 自动将审批通过的赎回本金申请款项付用户
 * @author ydt
 *
 */
@Component
public class CurrentProjectAutoPayRedeemTask {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CurrentProjectRedemptionApplyService currentProjectRedemptionApplyService;

	/**
	 * 每半小时执行一次，将处于审批通过的赎回申请金额打款给用户
	 */
	public void job() {
		logger.info("=====CurrentProjectAutoPayRedeemTask start=====");
		List<CurrentProjectRedemptionApply> currentProjectRedemptionApplyList = currentProjectRedemptionApplyService.findListByStatus(CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REVIEW_PASS);
		currentProjectRedemptionApplyList = currentProjectRedemptionApplyList == null ? new ArrayList<CurrentProjectRedemptionApply>() : currentProjectRedemptionApplyList;
		for(CurrentProjectRedemptionApply currentProjectRedemptionApply : currentProjectRedemptionApplyList) {
			try {
				currentProjectRedemptionApplyService.doPayRedeem(currentProjectRedemptionApply);
			} catch(Exception e) {
				e.printStackTrace();
				logger.info("=====doPayRedeem error, currentProjectRedemptionApply id:" + currentProjectRedemptionApply.getId() + "=====");
			}
		}
		logger.info("=====CurrentProjectAutoPayRedeemTask end=====");
	}
}
