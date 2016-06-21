package com.thinkgem.jeesite.modules.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import java.lang.annotation.RetentionPolicy;

/**
 * 在api方法里使用这个注解的话，则访问这个方法必须要验证token，否则无法访问，
 * 验证token后，可以在方法里通过TokenUtils.getCurrentCustomerClientToken()方法得到token对象
 * @author 万端瑞
 *
 */
@Deprecated
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
public @interface Authenticate {
	
}
