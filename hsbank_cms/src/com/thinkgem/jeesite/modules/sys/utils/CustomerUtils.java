/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.service.CustomerIntegralSnapshotService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealmFront.Principal;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class CustomerUtils {

	private static CustomerAccountDao customerAccountDao = SpringContextHolder.getBean(CustomerAccountDao.class);
	private static CustomerIntegralSnapshotService customerIntegralSnapshotService = SpringContextHolder.getBean(CustomerIntegralSnapshotService.class);

	public static final String CUSTOMER_CACHE = "customerCache";
	public static final String CUSTOMER_CACHE_ID_ = "id_";
	public static final String CUSTOMER_CACHE_LOGIN_NAME_ = "ln";

	/**
	 * 获取当前账号编号
	 * @return
	 */
	public static Long getCurrentAccountId() {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		return principal.getAccountId();
	}
	
	public static CustomerAccount get() {
		Principal principal = (Principal)CustomerUtils.getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		return get(accountId);
	}
	
	/**
	 * 根据ID获取用户
	 * @param accountId
	 * @return 取不到返回null
	 */
	public static CustomerAccount get(Long accountId){
		CustomerAccount customerAccount = (CustomerAccount)CacheUtils.get(CUSTOMER_CACHE, CUSTOMER_CACHE_ID_ + accountId);
		if (customerAccount ==  null){
			customerAccount = customerAccountDao.get(accountId + "");
			if (customerAccount == null){
				return null;
			}
			CacheUtils.put(CUSTOMER_CACHE, CUSTOMER_CACHE_ID_ + customerAccount.getAccountId(), customerAccount);
			CacheUtils.put(CUSTOMER_CACHE, CUSTOMER_CACHE_LOGIN_NAME_ + customerAccount.getAccountName(), customerAccount);
		}
		return customerAccount;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param accountName
	 * @return 取不到返回null
	 */
	public static CustomerAccount getByAccountName(String accountName){
		CustomerAccount customerAccount = (CustomerAccount)CacheUtils.get(CUSTOMER_CACHE, CUSTOMER_CACHE_LOGIN_NAME_ + accountName);
		if (customerAccount == null){
			customerAccount = customerAccountDao.getByLoginName(accountName);
			if (customerAccount == null){
				return null;
			}
			CacheUtils.put(CUSTOMER_CACHE, CUSTOMER_CACHE_ID_ + customerAccount.getAccountId(), customerAccount);
			CacheUtils.put(CUSTOMER_CACHE, CUSTOMER_CACHE_LOGIN_NAME_ + customerAccount.getAccountName(), customerAccount);
		}
		return customerAccount;
	}
	
	/**
	 * 清除指定用户缓存
	 * @param customerAccount
	 */
	public static void clearCache(CustomerAccount customerAccount){
		CacheUtils.remove(CUSTOMER_CACHE, CUSTOMER_CACHE_ID_ + customerAccount.getAccountId());
		CacheUtils.remove(CUSTOMER_CACHE, CUSTOMER_CACHE_LOGIN_NAME_ + customerAccount.getAccountName());
	}
	
	public static void refreshCache(long accountId) {
		CustomerAccount customerAccount = get(accountId);
		clearCache(customerAccount);
		get(accountId);
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static CustomerAccount getCustomerAccount(){
		Principal principal = getPrincipal();
		if (principal!=null){
			CustomerAccount customerAccount = get(principal.getAccountId());
			if (customerAccount != null){
				return customerAccount;
			}
			return new CustomerAccount();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new CustomerAccount();
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		Subject subject=null;
		try{
			subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}catch(Exception e){
			if(subject!=null){
				subject.logout();
			}
			return null;
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}

	public static Boolean customerCanSign() {
		if(getSubject().getPrincipal() == null) {
			return false;
		}
		Principal principal = (Principal)getSubject().getPrincipal();
		Long accountId = principal.getAccountId();
		return customerIntegralSnapshotService.canSign(accountId);
	};
	/**
	 * 根据登录名获取用户 优化登录
	 * @param accountName
	 * @return 取不到返回null
	 */
	public static CustomerAccount getByAccountNameNew(String accountName)throws Exception{
		CustomerAccount customerAccount = (CustomerAccount)CacheUtils.get(CUSTOMER_CACHE, CUSTOMER_CACHE_LOGIN_NAME_ + accountName);
		 
		if (customerAccount == null){
			customerAccount = customerAccountDao.findLoginName(accountName);
			if (customerAccount == null){
				return null;
			}
			CacheUtils.put(CUSTOMER_CACHE, CUSTOMER_CACHE_ID_ + customerAccount.getAccountId(), customerAccount);
			CacheUtils.put(CUSTOMER_CACHE, CUSTOMER_CACHE_LOGIN_NAME_ + accountName, customerAccount);
		}
		return customerAccount;
	}
		
}
