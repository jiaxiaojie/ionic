/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.AdPositionInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 广告位显示信息DAO接口
 * @author huangyuchen
 * @version 2016-05-17
 */
@MyBatisDao
public interface AdPositionInfoDao extends CrudDao<AdPositionInfo> {
    /**
     * 根据状态、终端和广告位类型查询出对应的广告位信息
     * @param adCode
     * @paramopTerm
     * @return
     */
    AdPositionInfo findAdPositionInfoByAdCodeAndTerminalType(@Param("adCode") String adCode, @Param("opTerm") String opTerm);
}