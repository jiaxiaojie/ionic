/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerBase;

/**
 * 会员基本信息DAO接口
 * @author ydt
 * @version 2015-06-23
 */
@MyBatisDao
public interface CustomerBaseDao extends CrudDao<CustomerBase> {

	CustomerBase getByAccountId(Long accountId);

	CustomerBase getByMobile(@Param("mobile") String mobile);
	
	public int getCountByMobile(@Param("mobile") String mobile);

	CustomerBase getByEmail(@Param("email") String eamil);

	/**
	 * 根据accountId更新用户名与身份证信息
	 * @param customerBase
	 * @return
	 */
	void updateNameAndCertNum(CustomerBase customerBase);

	/**
	 * 根据accountId获取用户姓名
	 * @param accountId
	 * @return
	 */
	String getCustomerNameByAccountId(Long accountId);

	/**
	 * 用户修改其信息
	 * @param customerBase 
	 */
	void customerChangeHisInfo(CustomerBase customerBase);

	/**
	 * 修改手机号
	 * @param platformUserNo
	 * @param mobile
	 */
	void updateMobileByPlatformUserNo(@Param("platformUserNo") String platformUserNo, @Param("mobile") String mobile);
	
	/**
	 * 根据accountId选择更新
	 * @param customerBase
	 * @return
	 */
	public int updateCustomerBaseByAccountIdSelective(CustomerBase customerBase);

	/**
	 * 根据accountId，更改nameAuthCode
	 * @param accountId
	 * @param hasAuthed
	 */
	void updateNameAuthCodeByAccountId(@Param("accountId") long accountId, @Param("nameAuthCode") String nameAuthCode);
	/**
	 * 查询手机号
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectCoustmerMoboile(Map<String, Object> map);

	List<CustomerBase> findMobileList(Long accountId);

}