/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.Gamble;

/**
 * 竞猜DAO接口
 * @author ydt
 * @version 2016-04-20
 */
@MyBatisDao
public interface GambleDao extends CrudDao<Gamble> {
	
	/**
	 * 根据activityId、accountId、opDt(可选)查询用户竞猜列表
	 * @param activityId
	 * @param accountId
	 * @param date
	 * @return
	 */
	List<Gamble> findListByActivityIdAndAccountId(@Param("activityId") Long activityId, @Param("accountId") Long accountId, @Param("opDt") Date opDt);

	/**
	 * 获取用户某活动某天的参赛记录
	 * @param activityId
	 * @param accountId
	 * @param opDt
	 * @return
	 */
	Gamble getByActivityIdAndAccountIdAndOpDate(@Param("activityId") Long activityId, @Param("accountId") Long accountId, @Param("opDt") Date opDt);
	
	/**
	 * 更新某活动某日的比赛结果信息
	 * @param activityId
	 * @param rightSide
	 * @param opDt
	 */
	void updateRightSideByActivityIdAndOpDt(@Param("activityId") Long activityId, @Param("rightSide") String rightSide, @Param("opDt") Date opDt);
	
	/**
	 * 根据id更新awardDt
	 * @param awardDt
	 * @param id
	 */
	void updateAwardDtById(@Param("awardDt") Date awardDt, @Param("id") Long id);

}