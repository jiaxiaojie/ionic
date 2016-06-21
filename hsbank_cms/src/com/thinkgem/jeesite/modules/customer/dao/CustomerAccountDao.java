/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.customer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;

/**
 * 会员账号信息DAO接口
 * @author ydt
 * @version 2015-06-23
 */
@MyBatisDao
public interface CustomerAccountDao extends CrudDao<CustomerAccount> {

	CustomerAccount getByAccountName(CustomerAccount customerAccount);
	List<CustomerAccount> querySimpleList(CustomerAccount queryParas);
    
	List<CustomerAccount> findAlignmentList(Map<String, Object> map);
	CustomerAccount getByLoginName(@Param("loginName") String loginName);
	CustomerAccount getByExtendNo(@Param("extendNo") String extendNo);
	/**
	 * 更新用户登录信息
	 * @param customerAccount
	 */
	void updateSuccessLoginInfo(CustomerAccount customerAccount);
	/**
	 * 登录失败调用
	 * @param customerAccount
	 */
	void updateFailureLoginInfo(CustomerAccount customerAccount);
	/**
	 * 更具accountId获取customerAccount
	 * @param accountId
	 * @return
	 */
	CustomerAccount get(@Param("id") Long accountId);
	/**
	 * 更新用户的requestNo
	 * @param customerAccount
	 */
	void updateRequestNo(CustomerAccount customerAccount);
	
	/**
	 * 更新用户的头像
	 * @param customerAccount
	 */
	void updateAvatar(CustomerAccount customerAccount);
	
	/**
	 * 根据accountId更新customerAccount表hasOpenThirdAccount状态
	 * @param accountId
	 * @param hasOpenThirdAccount
	 */
	int updateHasOpenThirdAccount(@Param("accountId") long accountId, @Param("hasOpenThirdAccount") String hasOpenThirdAccount);
	
	/**
	 * 根据platUserNo获取accountId
	 * @param platformUserNo
	 * @return
	 */
	CustomerAccount getAccountIdByPlatformUserNo(@Param("platformUserNo") String platformUserNo);

	/**
	 * 根据platformUserNo获取用户信息
	 * @param platformUserNo
	 * @return
	 */
	CustomerAccount getByPlatformUserNo(@Param("platformUserNo") String platformUserNo);

	/**
	 * 根据开通易宝账号的requestNo获取用户信息
	 * @param platformUserNo
	 * @return
	 */
	CustomerAccount getByRequestNo(@Param("requestNo") String requestNo);
	
	/**
	 * 重置密码
	 * @param customerAccount
	 */
	void resetPassword(CustomerAccount customerAccount);
	
	/**
	 * 根据手机号查找customerAccount
	 * @param mobile
	 * @return
	 */
	CustomerAccount getByMobile(@Param("mobile") String mobile);
	CustomerAccount getByMobile1(@Param("mobile") String mobile);
	
	/**
	 * 根据昵称查询会员个数
	 * @param nickname
	 * @return
	 */
	public int getByNicknameCount(@Param("nickname") String nickname);
	
	/**
	 * 根据accountId选择更新
	 * @param accountId
	 * @param nickname
	 * @return
	 */
	public int updateByAccountIdSelective(CustomerAccount customerAccount);
	
	/**
	 * 修改手机号为mobile的会员状态为锁定状态（即为"1"）
	 * @param mobile
	 */
	void updateStatusToLockedByMobile(@Param("mobile") String mobile);
	
	/**
	 * 设置用户的platformUserNo
	 * @param accountId
	 * @param generatePlatformUserNo
	 */
	void setPlatformUserNo(@Param("accountId") Long accountId, @Param("platformUserNo") String platformUserNo);
	
	/**
	 * 根据推荐人id获取被推荐人列表
	 * @param customerAccount
	 * @return
	 */
	List<CustomerAccount> getByRecommendorAccountId(CustomerAccount customerAccount);
	
	/**
	 * 获取推荐人列表
	 * @param map
	 * @return
	 */
	List<CustomerAccount> getListByRecommendorAccountId(Map<String, Object> map);

	/**
	 * 已认证身份证号拥有账号的个数
	 * @param idCardNo
	 * @return
	 */
	int getHasAuthedIdCardNoHasAccountNumber(@Param("idCardNo") String idCardNo);
	
	/**
	 * 获取accountId用户的推荐人accountId
	 * @param accountId
	 * @return
	 */
	Long getRecommenderAccountIdByAccountId(Long accountId);
	/**
	 * //按用户查询用户在易宝的用户平台标示
	 * @param nickname
	 * @return
	 */
	String selectPlatformUserNoByAccountName(String accountName);
	/**
	 * 根据用户编号查询用户在易宝的平台表标示
	 * @param borrowerAccountId
	 */
	String selectPlatformUserNoByBorrowerAccountId(Long borrowerAccountId);
	/**
	 * 根据状态查询用户名
	 * @param customerAccount 
	 * @param accountType
	 * @return
	 */
	List<String> selectCustomerNameByAccountName(CustomerAccount customerAccount);
	/**
	 * 根据用户的平台编号查询用户的用户名
	 * @param platformUserNo
	 * @return
	 */
	String selectCustomerNameByPlatformUserNo(String platformUserNo);
	/**
	 * 根据用户类型查询用户名称
	 * @param customerAccount
	 * @return
	 */
	List<String> selectListByaccountType(CustomerAccount customerAccount);

	/**
	 * 获取推荐人总数量(API-我的邀请-好友分页列表)
	 * @param accountId
	 * @return
	 */
	long countRecommendorByAccountId(Long accountId);
	/**
	 * 
	 * <p>
	 * Description:登录<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年6月15日
	 * @param customerAccount
	 * @return
	 * @throws Exception
	 * CustomerAccount
	 */
	public CustomerAccount findLoginName(@Param("loginName") String loginName);
	
	public void updateLogin(CustomerAccount customerAccount);
	
	public CustomerAccount loginByMobile(@Param("loginName") String loginName);
	public CustomerAccount loginByEmail(@Param("loginName") String loginName);
	public CustomerAccount loginByAccountName(@Param("loginName") String loginName);

}