/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.carousel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.AdPositionShowTerm;
import com.thinkgem.jeesite.modules.entity.CustomMessageSendChannel;

import java.util.List;

/**
 * 广告位显示终端DAO接口
 * @author huangyuchen
 * @version 2016-05-17
 */
@MyBatisDao
public interface AdPositionShowTermDao extends CrudDao<AdPositionShowTerm> {

    /**
     * 根据id删除
     * @param id
     * @return
     */
     int deleteById(Long id);

    /**
     * 插入渠道
     * @param
     * @return
     */
     int insertBatch(AdPositionShowTerm term);

    AdPositionShowTerm findListByAdPositionId(String id);

    AdPositionShowTerm getByTermCode(AdPositionShowTerm adPositionShowTerm);


    List<AdPositionShowTerm> getAdPositionShowTermTermCodeByTermCode(String code);
}