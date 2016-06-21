/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.MobileAwardRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 手机号中奖记录DAO接口
 * @author ydt
 * @version 2016-05-05
 */
@MyBatisDao
public interface MobileAwardRecordDao extends CrudDao<MobileAwardRecord> {

	/**
	 * 获取活动未投资用户奖品类型为awardType的奖励数量
	 * @param activityId
	 * @param prizeType
	 */
	int getCountByActivityIdAndAwardType(@Param("activityId") long activityId, @Param("prizeType") String prizeType);

	/**
	 * 根据activityId、mobile、status获取奖励记录
	 * @param activityId
	 * @param mobile
	 * @param status 可为空
	 * @return
	 */
	MobileAwardRecord getByActivityIdAndMobileAndStatus(@Param("activityId") Long activityId, @Param("mobile") String mobile, @Param("status") String status);

	/**
	 * 根据activity、mobile获取获奖记录数量
	 * @param activityId
	 * @param mobile
	 * @return
	 */
	int getCountByActivityIdAndMobile(@Param("activityId") Long activityId, @Param("mobile") String mobile);


}