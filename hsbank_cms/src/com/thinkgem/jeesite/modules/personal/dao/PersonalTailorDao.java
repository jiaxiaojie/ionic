/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personal.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.api.to.PersonalTailorResp;
import com.thinkgem.jeesite.modules.entity.PersonalTailor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 私人订制项目DAO接口
 * @author yubin
 * @version 2016-05-18
 */
@MyBatisDao
public interface PersonalTailorDao extends CrudDao<PersonalTailor> {
    /**
     * 查询分页列表
     * @param pageSize
     * @param pageNumber
     * @return
     * @throws Exception
     */
    public List<PersonalTailorResp> queryPersonalList(
            @Param("pageSize") int pageSize,
            @Param("pageNumber") int pageNumber) throws  Exception;

    /**
     * 统计总数
     * @return
     * @throws Exception
     */
    public Integer countPersonalList()throws  Exception;

    /**
     * 查看详情
     * @param id
     * @return
     */
    public PersonalTailorResp findByPersonalId(@Param("id") int id)throws  Exception;
    /**
     * 
     * <p>
     * Description:根据状态查询记录<br />
     * </p>
     * @author yubin
     * @version 0.1 2016年6月1日
     * @param id
     * @param state
     * @return
     * PersonalTailor
     */
    public PersonalTailor queryByState(@Param("id") int id, @Param("state") String state);

	/**
     * 获取可投资项目数量
     * @return
     */
    Integer getCanInvestmentCount();
    
    public PersonalTailor gePersonalTailorByName(@Param("name") String name);
    
}