package com.thinkgem.jeesite.modules;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordToken;

public abstract class AdminBaseTest extends BaseTest {

	@Autowired
	private SecurityManager securityManager;
	
	/**
	 * 初始化函数
	 */
	@Before
	public void before() {
		super.before();
		ThreadContext.bind(securityManager);
		setUp();
	}
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 */
	public void login(String username, String password) {
		Subject subject = new Subject.Builder(securityManager).buildSubject();
		subject.login(new UsernamePasswordToken(username, password.toCharArray(), false, "", "",false));
		ThreadContext.bind(subject);
	}
	
	/**
	 * 清理函数
	 */
	@After
	public void after() {
		super.after();
		clear();
	}

}
