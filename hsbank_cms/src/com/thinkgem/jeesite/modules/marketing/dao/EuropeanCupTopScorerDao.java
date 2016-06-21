/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.marketing.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.EuropeanCupTopScorer;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 欧洲杯射手榜DAO接口
 * @author lzb
 * @version 2016-06-13
 */
@MyBatisDao
public interface EuropeanCupTopScorerDao extends CrudDao<EuropeanCupTopScorer> {

    /**
     * 根据账户、活动id获取信息
     * @param accountId
     * @param activityId
     * @return
     */
    EuropeanCupTopScorer getByAccountIdAndActivityId(@Param("accountId") Long accountId, @Param("activityId") Long activityId);


    /**
     * 更新用户进球总数
     * @param accountId
     * @param activityId
     * @param totalGoals
     */
    void updateTotalGoalsByAccountIdAndActivityId(@Param("accountId") long accountId,
                                                  @Param("activityId") long activityId, @Param("totalGoals") int totalGoals);

    /**
     *  更新用户刮奖次数
     * @param accountId
     * @param activityId
     * @param usedScratchTimes
     */
    void updateUsedScratchTimesByAccountIdAndActivityId(@Param("accountId") long accountId,
                                                        @Param("activityId") long activityId, @Param("usedScratchTimes") int usedScratchTimes);

    /**
     * 获取前10名射手数据
     * @param activityId
     * @param startDate
     * @param endDate
     * @return
     */
    List<EuropeanCupTopScorer> getTop10Scorer(@Param("activityId") long activityId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}