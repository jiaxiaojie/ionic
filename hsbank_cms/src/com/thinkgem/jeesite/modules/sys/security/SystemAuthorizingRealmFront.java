/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.UUIDUtils;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.client.ClientProperty;
import com.thinkgem.jeesite.modules.api.service.IndexService;
import com.thinkgem.jeesite.modules.customer.service.CustomerAccountService;
import com.thinkgem.jeesite.modules.customer.service.CustomerClientTokenService;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerClientToken;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-5
 */
@Service
public class SystemAuthorizingRealmFront extends AuthorizingRealm {
	@Autowired
	private IndexService indexService;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerClientTokenService customerClientTokenService;
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordTokenFront token = (UsernamePasswordTokenFront) authcToken;	 
		// 校验登录验证码
		if (indexService.imageAuth(token.getAccountName(), false, false)&&token.isNew()){
			if (token.getCaptcha() == null || !indexService.validateCaptcha(token.getClient(), true, false, token.getCaptcha())){
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		}
		
		// 校验用户名密码
		CustomerAccount customerAccount = customerAccountService.getCustomerAccount(token.getAccountName());
		 
		if (customerAccount != null) {
			if (Global.NO.equals(customerAccount.getLoginFlag())){
				throw new AuthenticationException("msg:该已账户禁止登录.");
			}
			//登录成功，设置用户令牌
			String loginToken=this.getLoginToken(token, customerAccount);
			token.setToken(loginToken);
			byte[] salt = Encodes.decodeHex(customerAccount.getAccountPwd().substring(0,16));
			return new SimpleAuthenticationInfo(new Principal(customerAccount, token.isMobileLogin()), 
					customerAccount.getAccountPwd().substring(16), ByteSource.Util.bytes(salt), getName());
		} else {
			throw new AuthenticationException("登录异常");
			 
		}
	}

	/**
	 * 登录成功，并向数据库中插入缓存信息
	 * @param token
	 * @param customerAccount
	 * @return
     */
    public String  getLoginToken(UsernamePasswordTokenFront token,CustomerAccount customerAccount){
        ClientProperty cProperty = ApiUtil.getClient(token.getClient());
        String opTerm = ApiUtil.getOperaChannel(cProperty.getType());
        //调用登录活动
        indexService.doLoginActivity(customerAccount, opTerm);
        //更新登陆时间和IP
        customerAccount.setLastLoginDt(new Date());
        customerAccount.setLastLoginIp(token.getHost());
        customerAccount.setLastLoginTermCode(opTerm);
        customerAccountService.updateLogin(customerAccount);
        
        CustomerClientToken clientToken = new CustomerClientToken();
        clientToken.setCustomerId(customerAccount.getAccountId());
        clientToken.setTermType(opTerm);
        clientToken.setToken(UUIDUtils.getToken());
        clientToken.setLastDt(new Date());
        String Logintoken = customerClientTokenService.operaCustomerClientTokenByPc(clientToken, opTerm);
        
        return Logintoken; 
    }
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
//	/**
//	 * 清空用户关联权限认证，待下次使用时重新加载
//	 */
//	public void clearCachedAuthorizationInfo(Principal principal) {
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
//		clearCachedAuthorizationInfo(principals);
//	}

	/**
	 * 清空所有关联认证
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
//		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//		if (cache != null) {
//			for (Object key : cache.keys()) {
//				cache.remove(key);
//			}
//		}
	}
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private Long accountId; // 编号
		private String accountName; // 登录名
//		private String name; // 姓名
		private boolean mobileLogin; // 是否手机登录
		
//		private Map<String, Object> cacheMap;

		public Principal(CustomerAccount customerAccount, boolean mobileLogin) {
			this.accountId = customerAccount.getAccountId();
			this.accountName = customerAccount.getAccountName();
			this.mobileLogin = mobileLogin;
		}

		public String getAccountName() {
			// TODO Auto-generated method stub
			return accountName;
		}

		public String getLoginName() {
			return accountName;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				return (String) CustomerUtils.getSession().getId();
			}catch (Exception e) {
				return "";
			}
		}
		
		@Override
		public String toString() {
			return "customer_" + accountId;
		}

		public Long getAccountId() {
			return accountId;
		}

	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection paramPrincipalCollection) {
		// TODO Auto-generated method stub
		return null;
	}
}
