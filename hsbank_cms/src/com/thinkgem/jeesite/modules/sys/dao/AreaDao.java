/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.api.to.AreaResp;
import com.thinkgem.jeesite.modules.api.to.CityResp;
import com.thinkgem.jeesite.modules.api.to.ProvinceResp;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {

    List<Area> findByParentId(@Param("parentId") String parentId);
    
    List<ProvinceResp>queryProvice();
    
    List<CityResp>queryCity(@Param("parentId") String parentId);
    
    List<AreaResp>queryArea(@Param("parentId") String parentId);
    
}
